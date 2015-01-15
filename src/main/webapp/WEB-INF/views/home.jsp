<%@include file="includes/header.jsp"%>

<div class="container-fluid">
  <div class="row">
    <%@include file="includes/sidebar.jsp"%>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <c:if test="${not empty errors}">
        <div class="alert alert-danger" role="alert">
          <ul class="list-unstyled">
            <c:forEach items="${errors}" var="error">
              <li><span class="glyphicon glyphicon-remove"></span> ${error.defaultMessage}</li>
              <c:if test="${error.field == 'name'}">
                <c:set var="error_name" value="true" />
              </c:if>
              <c:if test="${error.field == 'server'}">
                <c:set var="error_server" value="true" />
              </c:if>
            </c:forEach>
          </ul>
        </div>
      </c:if>

      <ol class="breadcrumb">
        <li class="active">Dashboard</li>
      </ol>

      <h1 class="page-header">[dashboard]</h1>

      <!-- 
      <div class="row placeholders">
        <div class="col-xs-6 col-sm-3">
          <div class="chart" data-bar-color="#d43f3a" data-percent="73">73%</div>
          <h4>Failed execution</h4>
          <span class="text-muted">During 1 week</span>
        </div>
        <div class="col-xs-6 col-sm-3 placeholder">
          <div class="chart" data-bar-color="#f0ad4e" data-percent="50">50%</div>
          <h4>Failed execution</h4>
          <span class="text-muted">During 1 month</span>
        </div>
        <div class="col-xs-6 col-sm-3 placeholder">
          <div class="chart" data-bar-color="#5cb85c" data-percent="89">89%</div>
          <h4>Label</h4>
          <span class="text-muted">Something else</span>
        </div>
        <div class="col-xs-6 col-sm-3 placeholder">
          <div class="chart" data-bar-color="#337ab7" data-percent="3">3%</div>
          <h4>Label</h4>
          <span class="text-muted">Something else</span>
        </div>
      </div>
       -->

      <h2 class="sub-header">
        Projects
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#new-project"><span class="glyphicon glyphicon-new-window"></span> New Project</button>
      </h2>
      <div class="table-responsive">
        <c:if test="${empty projects}">
          <p class="text-center">
            <em>There is no project created. <a href="#" data-toggle="modal" data-target="#new-project">Create new one</a></em>
          </p>
        </c:if>
        <c:if test="${not empty projects}">
          <table class="table table-striped">
            <thead>
              <tr>
                <th width="5%">#</th>
                <th width="15%">Name</th>
                <th width="15%">Server</th>
                <th>Description</th>
                <th width="15%" class="text-center">Created Date</th>
                <th width="15%" class="text-center">Updated Date</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${projects}" var="project">
                <tr>
                  <td>${project.id}</td>
                  <td><a href='<c:url value="/project/${project.id}" />'>${project.name}</a></td>
                  <td>${project.server}</td>
                  <td>${project.description}</td>
                  <td class="text-center"><fmt:formatDate value="${project.createdDate}" pattern="dd/MM/yyyy hh:mm a" /></td>
                  <td class="text-center"><fmt:formatDate value="${project.updatedDate}" pattern="dd/MM/yyyy hh:mm a" /></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          
          <nav class="text-center">
            <ul class="pagination">
              <li><a href="<c:url value="/" />"><span>&laquo;</span></a></li>
              <c:forEach begin="${pagination.begin}" end="${pagination.end}" var="page">
                <c:if test="${page == 1}">
                  <li><a href="<c:url value="/" />">${page}</a></li>
                </c:if>
                <c:if test="${page > 1}">
                  <li><a href="<c:url value="/?page=${page}" />">${page}</a></li>
                </c:if>
              </c:forEach>
              <c:if test="${pagination.last == 1}">
                <li><a href="<c:url value="/" />"><span>&raquo;</span></a></li>
              </c:if>
              <c:if test="${pagination.last > 1}">
                <li><a href="<c:url value="/?page=${pagination.last}" />"><span>&raquo;</span></a></li>
              </c:if>
            </ul>
          </nav>
        </c:if>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="new-project">
  <div class="modal-dialog">
    <div class="modal-content">
      <form class="form-horizontal" role="form" method="POST" action="<c:url value="/newproject" />">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
          </button>
          <h4 class="modal-title" id="myModalLabel">New Project</h4>
        </div>
        <div class="modal-body">
          <div class="form-group ${error_name ? 'has-error' : error_name}">
            <label for="project-name" class="col-sm-3 control-label">Name</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-name" name="name" placeholder="Name" value="${param.name}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-description" class="col-sm-3 control-label">Description</label>
            <div class="col-sm-9">
              <textarea class="form-control" id="project-description" name="description" placeholder="Description">${param.description}</textarea>
            </div>
          </div>
          <div class="form-group ${error_server ? 'has-error' : ''}">
            <label for="project-server" class="col-sm-3 control-label">Server</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-server" name="server" placeholder="http://host:port/context" value="${param.server}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dbclasspath" class="col-sm-3 control-label">JDBC Classpath</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dbclasspath" name="dbClasspath" placeholder="/path/to/jdbc-connector.jar" value="${param.dbClasspath}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dbdriver" class="col-sm-3 control-label">JDBC Driver</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dbdriver" name="dbDriver" placeholder="org.jdbc.driver.JDBCDriver" value="${param.dbDriver}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dburl" class="col-sm-3 control-label">JDBC URL</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dburl" name="dbUrl" placeholder="jdbc:database-url" value="${param.dbUrl}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dbuserid" class="col-sm-3 control-label">JDBC Username</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dbuserid" name="dbUserid" placeholder="JDBC Username" value="${param.dbUserid}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dbpassword" class="col-sm-3 control-label">JDBC Password</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dbpassword" name="dbPassword" placeholder="JDBC Password" value="${param.dbPassword}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-schedule" class="col-sm-3 control-label">Schedule</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-schedule" name="schedule" placeholder="Cron Expression" value="${param.schedule}">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary">Save</button>
        </div>
      </form>
    </div>
  </div>
</div>

<%@include file="includes/footer.jsp"%>