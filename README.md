# WebtestApplication
WebtestApplication is the web-based application that's used to simplify Canoo Webtest scripts creation and execution progress. 

Canoo Webtest is a great automation test tool, but it's not friendly, espescially with Testers or QAs who have none or less technical skill. WebtestApplication provide an User Interface for peoppe who works with Canoo, to manage test scripts and execute them.

WebtestApplication has a standalone version which contains `apache-tomcat-7.0.55`, `hsqldb-2.3.2`, `webtest-3.0.1826`. Just double click at `start.bat` or `start.sh` and your application is up.

## Main features
* Support scheduling execution (with Quazrt cron expression) for entire project or particular scripts.
* Execution logs and Webtest results are all stored so you can refer back to execution history anytime.
* Easy to install by using standalone version.

## Hints
* Use **Share Scripts** as much as possible, it will help you to simplify your main script a lot.
* Instead of writing `<sql class="..." driver="...">`, you can just make it simple `<sql>`
* To assign a value that fetch from database by using `<sql>`, please use this syntax `<sql>SELECT 'a_variable' || field FROM table WHERE id=1</sql>`. From now on you can access to `${a_variable}` anytime.

## Screenshots
![alt tag](https://raw.githubusercontent.com/pltchuong/WebtestApplication/master/screenshot/1.png)
![alt tag](https://raw.githubusercontent.com/pltchuong/WebtestApplication/master/screenshot/2.png)
![alt tag](https://raw.githubusercontent.com/pltchuong/WebtestApplication/master/screenshot/3.png)
![alt tag](https://raw.githubusercontent.com/pltchuong/WebtestApplication/master/screenshot/4.png)
![alt tag](https://raw.githubusercontent.com/pltchuong/WebtestApplication/master/screenshot/5.png)
![alt tag](https://raw.githubusercontent.com/pltchuong/WebtestApplication/master/screenshot/6.png)

## Troubleshooting
#### Could not find artifact com.atlassian.jira:jira-rest-java-client:jar:1.0
WebtestApplication supports Jira API connection, however the library is not available at default Maven repositoty, so you will have to add it manually into your maven config `~/.m2/settings.xml`
```
<profiles>
  <profile>
    <id>activeProfile</id>
    <repositories>
      <repository>
        <id>atlassian-public</id>
        <url>https://m2proxy.atlassian.com/repository/public</url>
        <snapshots>
          <enabled>true</enabled>
          <updatePolicy>daily</updatePolicy>
          <checksumPolicy>warn</checksumPolicy>
        </snapshots>
        <releases>
          <enabled>true</enabled>
          <checksumPolicy>warn</checksumPolicy>
        </releases>
      </repository>
    </repositories>
    <pluginRepositories>
      <pluginRepository>
        <id>atlassian-public</id>
        <url>https://m2proxy.atlassian.com/repository/public</url>
        <releases>
        <enabled>true</enabled>
        <checksumPolicy>warn</checksumPolicy>
        </releases>
          <snapshots>
            <checksumPolicy>warn</checksumPolicy>
          </snapshots>
      </pluginRepository>
    </pluginRepositories>
  </profile>
</profiles>
<activeProfiles>
  <activeProfile>activeProfile</activeProfile>
</activeProfiles>
```
