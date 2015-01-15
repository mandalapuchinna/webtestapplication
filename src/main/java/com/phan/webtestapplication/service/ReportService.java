package com.phan.webtestapplication.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.phan.webtestapplication.model.ReportResult;
import com.phan.webtestapplication.repository.ReportRepository;

public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<ReportResult> getResults(String projectId, String startdate, String enddate)
            throws NumberFormatException, ParseException {

        List<Object[]> rawResults = new ArrayList<Object[]>();
        List<ReportResult> results = new ArrayList<ReportResult>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Long id = null;
        Date start = null;
        Date end = null;

        if (projectId != null && !projectId.isEmpty()) {
            id = Long.parseLong(projectId);
            if (!startdate.isEmpty()) {
                calendar.setTime(df.parse(startdate));
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                start = calendar.getTime();
            }
            if (!enddate.isEmpty()) {
                calendar.setTime(df.parse(enddate));
                calendar.set(Calendar.HOUR, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                end = calendar.getTime();
            }

            if (start == null && end == null) {
                rawResults = reportRepository.findByProject(id);
            } else if (start == null && end != null) {
                rawResults = reportRepository.findByProjectAndEnddate(id, end);
            } else if (start != null && end == null) {
                rawResults = reportRepository.findByProjectAndStartdate(id, start);
            } else {
                rawResults = reportRepository.findByProjectAndStartdateAndEnddate(id, start, end);
            }
        }

        for (Object[] object : rawResults) {
            ReportResult reportResult = new ReportResult();
            reportResult.setScriptId(((BigInteger) object[0]).longValue());
            reportResult.setScriptName((String) object[1]);
            reportResult.setTotal(((BigInteger) object[2]).doubleValue());
            reportResult.setSuccess(((BigInteger) object[3]).doubleValue());
            reportResult.setFailure(((BigInteger) object[4]).doubleValue());
            reportResult.setRunning(((BigInteger) object[5]).doubleValue());

            if (reportResult.getTotal() > 0) {
                reportResult.setSuccess(reportResult.getSuccess() / reportResult.getTotal() * 100);
                reportResult.setFailure(reportResult.getFailure() / reportResult.getTotal() * 100);
                reportResult.setRunning(reportResult.getRunning() / reportResult.getTotal() * 100);
            }

            results.add(reportResult);
        }

        return results;
    }
}
