package com.phan.webtestapplication.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.phan.webtestapplication.Constants;
import com.phan.webtestapplication.model.Configuration;
import com.phan.webtestapplication.model.Project;
import com.phan.webtestapplication.model.Script;
import com.phan.webtestapplication.model.ScriptExecution;
import com.phan.webtestapplication.repository.ConfigurationRepository;
import com.phan.webtestapplication.repository.ScriptExecutionRepository;
import com.phan.webtestapplication.repository.ScriptRepository;
import com.phan.webtestapplication.util.GeneralUtil;

public class ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private ScriptExecutionRepository scriptExecutionRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ProjectService projectService;

    @Transactional
    public Long execute(Long scriptId) {

        Script script = scriptRepository.findOne(scriptId);
        String webtest = configurationRepository.findConfig().getBinary();

        ScriptExecution scriptExecution = new ScriptExecution();
        scriptExecution.setExecutedDate(new Date());
        scriptExecution.setStatus(Constants.EXECUTION_STATUS_RUNNING);
        scriptExecution.setScript(script);
        scriptExecutionRepository.save(scriptExecution);

        script.getScriptExecutions().add(scriptExecution);
        scriptRepository.save(script);

        final Long scriptExecutionId = scriptExecution.getId();

        try {
            generateDtd(scriptExecution);
            generateIncludes(scriptExecution);
            generateTest(scriptExecution);
        } catch (Exception e) {
            scriptExecution.setLog(GeneralUtil.getStackTrace(e));
            scriptExecution.setStatus(Constants.EXECUTION_STATUS_FAILED);
            scriptExecutionRepository.save(scriptExecution);
            return scriptExecutionId;
        }

        ProcessBuilder ps = new ProcessBuilder();
        ps.redirectErrorStream(true);
        ps.command(new String[] {
                webtest + "/bin/webtest."
                        + (System.getProperty("os.name").toLowerCase().contains("win") ? "bat" : "sh"), "-f",
                webtest + "/resources/webtestsRunner-customized.xml", "-Dwebtest.testfile", "/build.xml",
                "-Dwebtest.testdir", getTestdir(scriptExecution) });

        try {
            final Process process = ps.start();

            new Thread() {
                public void run() {
                    ScriptExecution scriptExecution = scriptExecutionRepository.findOne(scriptExecutionId);
                    StringBuilder stringBuilder = new StringBuilder();
                    try {
                        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = input.readLine()) != null) {
                            if (line.startsWith("BUILD FAILED")) {
                                scriptExecution.setStatus(Constants.EXECUTION_STATUS_FAILED);
                            }

                            stringBuilder.append(HtmlUtils.htmlEscape(line)).append("\r\n");
                            scriptExecution.setLog(stringBuilder.toString());
                            scriptExecutionRepository.save(scriptExecution);
                        }
                        if (scriptExecution.getStatus() == Constants.EXECUTION_STATUS_RUNNING) {
                            scriptExecution.setStatus(Constants.EXECUTION_STATUS_SUCCESS);
                            scriptExecutionRepository.save(scriptExecution);
                        }
                    } catch (Exception e) {
                        scriptExecution.setLog(GeneralUtil.getStackTrace(e));
                        scriptExecution.setStatus(Constants.EXECUTION_STATUS_FAILED);
                        scriptExecutionRepository.save(scriptExecution);
                    }
                }
            }.start();
        } catch (Exception e) {
            scriptExecution.setLog(GeneralUtil.getStackTrace(e));
            scriptExecution.setStatus(Constants.EXECUTION_STATUS_FAILED);
            scriptExecutionRepository.save(scriptExecution);
        }

        return scriptExecutionId;
    }

    private String getTestdir(ScriptExecution scriptExecution) {
        return configurationRepository.findConfig().getStorage() + "/"
                + scriptExecution.getScript().getProject().getId() + "/" + scriptExecution.getScript().getId() + "/"
                + scriptExecution.getId();
    }

    private void generateDtd(ScriptExecution scriptExecution) throws Exception {
        List<Script> sharedScripts = scriptRepository.findByProjectAndShared(scriptExecution.getScript().getProject(),
                true);

        File dtdFile = new File(getTestdir(scriptExecution) + "/dtd/Project.dtd");
        dtdFile.getParentFile().mkdirs();
        PrintStream dtdout = null;
        try {
            dtdout = new PrintStream(new FileOutputStream(dtdFile));
            dtdout.print("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\r\n\r\n");
            for (Script sharedScript : sharedScripts) {
                dtdout.print("<!ENTITY " + sharedScript.getName().replaceFirst("\\..*", "").replaceAll("\\W", "__")
                        + " SYSTEM \"../includes/" + sharedScript.getId() + ".xml\">\r\n");
            }
        } finally {
            if (dtdout != null) {
                dtdout.close();
            }
        }
    }

    private void generateIncludes(ScriptExecution scriptExecution) throws Exception {
        List<Script> sharedScripts = scriptRepository.findByProjectAndShared(scriptExecution.getScript().getProject(),
                true);

        File includesdir = new File(getTestdir(scriptExecution) + "/includes");
        includesdir.mkdirs();

        for (Script sharedScript : sharedScripts) {
            File includesfile = new File(includesdir.getAbsolutePath() + "/" + sharedScript.getId() + ".xml");
            PrintStream out = null;
            try {
                out = new PrintStream(new FileOutputStream(includesfile));
                out.print(filter(sharedScript.getContent()));
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    private void generateTest(ScriptExecution scriptExecution) throws Exception {
        Configuration configuration = configurationRepository.findConfig();
        String proxyHost = configuration.getProxyHost();
        String proxyPort = configuration.getProxyPort();

        Script script = scriptExecution.getScript();
        Project project = script.getProject();

        URL projectServer = projectService.getProjectServer(project);

        String testfile = getTestdir(scriptExecution) + "/build.xml";
        PrintStream out = null;
        try {
            File file = new File(testfile);
            file.getParentFile().mkdirs();
            out = new PrintStream(new FileOutputStream(file));
            out.print("<!DOCTYPE project SYSTEM \"dtd/Project.dtd\">\r\n");
            out.print("<project name=\"" + project.getName() + "\" basedir=\".\" default=\"wt.full\">\r\n");
            if (proxyHost != null && !proxyHost.isEmpty() && proxyPort != null && !proxyPort.isEmpty()) {
                out.print("  <setproxy proxyhost=\"" + proxyHost + "\" proxyport=\"" + proxyPort + "\"/>\r\n");
            }
            out.print("  <import file=\"${webtest.home}/webtest.xml\"/>\r\n");
            out.print("  <target name=\"wt.testInWork\">\r\n");
            out.print("    <property name=\"server\" value=\"" + project.getServer() + "\"/>\r\n");
            out.print("    <property name=\"dbClasspath\" value=\"" + project.getDbClasspath() + "\"/>\r\n");
            out.print("    <property name=\"dbDriver\" value=\"" + project.getDbDriver() + "\"/>\r\n");
            out.print("    <property name=\"dbUrl\" value=\"" + project.getDbUrl() + "\"/>\r\n");
            out.print("    <property name=\"dbUserid\" value=\"" + project.getDbUserid() + "\"/>\r\n");
            out.print("    <property name=\"dbPassword\" value=\"" + project.getDbPassword() + "\"/>\r\n");
            out.print("    <webtest name=\"" + script.getName() + "\">\r\n");
            out.print("      <config\r\n");
            out.print("        protocol=\"" + projectServer.getProtocol() + "\" \r\n");
            out.print("        host=\"" + projectServer.getHost() + "\" \r\n");
            out.print("        port=\"" + (projectServer.getPort() > 0 ? projectServer.getPort() : 80) + "\" \r\n");
            out.print("        basepath=\"" + projectServer.getPath() + "\"\r\n");
            out.print("        >\r\n");
            out.print("        <option name=\"ThrowExceptionOnScriptError\" value=\"false\"/>\r\n");
            out.print("      </config>\r\n\r\n");
            out.print(filter(script.getContent()) + "\r\n\r\n");
            out.print("    </webtest>\r\n");
            out.print("  </target>\r\n");
            out.print("</project>");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private String filter(String script) {
        if (script == null) {
            return "";
        } else {
            // restore sql tags
            script = script
                    .replaceAll(
                            "<sql>",
                            "<sql classpath=\"\\${dbClasspath}\" driver=\"\\${dbDriver}\" url=\"\\${dbUrl}\" userid=\"\\${dbUserid}\" password=\"\\${dbPassword}\" output=\"temp.properties\" print=\"yes\" showheaders=\"false\" showtrailers=\"false\" expandProperties=\"true\">");
            script = script.replaceAll("</sql>",
                    "</sql>\r\n<property file=\"temp.properties\"/>\r\n<delete file=\"temp.properties\" />");

            return script;
        }
    }
}
