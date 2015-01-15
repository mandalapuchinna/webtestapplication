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
            </c:forEach>
          </ul>
        </div>
      </c:if>

      <ol class="breadcrumb">
        <li><a href="<c:url value="/"/>">Dashboard</a></li>
        <li class="active">[project] ${project.name}</li>
      </ol>

      <h1 class="page-header">
        [project] ${project.name} <span class="pull-right">
          <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#edit-project">
            <span class="glyphicon glyphicon-edit"></span> Edit
          </button>
          <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#delete-project">
            <span class="glyphicon glyphicon-remove-sign"></span> Delete
          </button>
        </span>
      </h1>
      <p>${project.description}</p>
      
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

      <form method="POST" action="<c:url value="/project/${project.id}/execute" />">
        <h2 class="sub-header">
          Scripts <span class="pull-right">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#new-script">
              <span class="glyphicon glyphicon-new-window"></span> New Script
            </button>
            <button type="submit" class="btn btn-success">
              <span class="glyphicon glyphicon-play-circle"></span> Execute
            </button>
          </span>
        </h2>

        <div class="table-responsive">
          <c:if test="${empty normalScripts}">
            <p class="text-center">
              <em>There is no script created. <a href="#" data-toggle="modal" data-target="#new-script">Create new one</a></em>
            </p>
          </c:if>
          <c:if test="${not empty normalScripts}">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th width="5%">#</th>
                  <th>Name</th>
                  <th width="15%" class="text-center">Last<br />Status/Result</th>
                  <th width="15%" class="text-center">Last Executed Date</th>
                  <th width="15%" class="text-center">Created date</th>
                  <th width="15%" class="text-center">Updated date</th>
                  <th width="15%" class="text-center">Execute <input type="checkbox" id="execute-all" /></th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${normalScripts}" var="script">
                  <tr>
                    <td>${script.id}</td>
                    <td><a href="<c:url value="/project/${project.id}/${script.id}" />">${script.name}</a></td>
                    <td class="text-center">
                      <c:set var="latestScriptExecution" value="${script.scriptExecutions[script.scriptExecutions.size() - 1]}"></c:set>
                      <span class="glyphicon ${latestScriptExecution.status == 0 ? 'glyphicon-refresh text-info' : latestScriptExecution.status == 1 ?'glyphicon-remove text-danger' : latestScriptExecution.status == 2 ? 'glyphicon-ok text-success' : ''}"></span> &nbsp; &nbsp;
                    </td>
                    <td class="text-center"><fmt:formatDate value="${latestScriptExecution.executedDate}" pattern="dd/MM/yyyy hh:mm a" /></td>
                    <td class="text-center"><fmt:formatDate value="${script.createdDate}" pattern="dd/MM/yyyy hh:mm a" /></td>
                    <td class="text-center"><fmt:formatDate value="${script.updatedDate}" pattern="dd/MM/yyyy hh:mm a" /></td>
                    <td class="text-center"><input type="checkbox" name="scriptId[]" value="${script.id}" /></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
            
            <nav class="text-center">
              <ul class="pagination">
                <li><a href="<c:url value="/project/${project.id}" />"><span>&laquo;</span></a></li>
                <c:forEach begin="${pagination1.begin}" end="${pagination1.end}" var="page">
                  <c:if test="${page == 1}">
                    <li><a href="<c:url value="/project/${project.id}" />">${page}</a></li>
                  </c:if>
                  <c:if test="${page > 1}">
                    <li><a href="<c:url value="/project/${project.id}/?page=${page}" />">${page}</a></li>
                  </c:if>
                </c:forEach>
                <c:if test="${pagination1.last == 1}">
                  <li><a href="<c:url value="/project/${project.id}" />"><span>&raquo;</span></a></li>
                </c:if>
                <c:if test="${pagination1.last > 1}">
                  <li><a href="<c:url value="/project/${project.id}?page=${pagination1.last}" />"><span>&raquo;</span></a></li>
                </c:if>
              </ul>
            </nav>
          </c:if>
        </div>
      </form>
      
      <h2 class="sub-header">
        Shared Scripts <span class="pull-right">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#new-shared-script">
            <span class="glyphicon glyphicon-new-window"></span> New Shared Script
          </button>
        </span>
      </h2>
      <div class="table-responsive">
        <c:if test="${empty sharedScripts}">
          <p class="text-center">
            <em>There is no script created. <a href="#" data-toggle="modal" data-target="#new-script">Create new one</a></em>
          </p>
        </c:if>
        <c:if test="${not empty sharedScripts}">
          <table class="table table-striped">
            <thead>
              <tr>
                <th width="5%">#</th>
                <th>Name</th>
                <th width="15%" class="text-center">Created date</th>
                <th width="15%" class="text-center">Updated date</th>
                <th width="15%" class="text-center">&nbsp;</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${sharedScripts}" var="script">
                <tr>
                  <td>${script.id}</td>
                  <td><a href="<c:url value="/project/${project.id}/${script.id}" />">${script.name}</a></td>
                  <td class="text-center"><fmt:formatDate value="${script.createdDate}" pattern="dd/MM/yyyy hh:mm a" /></td>
                  <td class="text-center"><fmt:formatDate value="${script.updatedDate}" pattern="dd/MM/yyyy hh:mm a" /></td>
                  <td class="text-center">&nbsp;</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:if>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="edit-project">
  <div class="modal-dialog">
    <div class="modal-content">
      <form class="form-horizontal" role="form" method="POST" action="<c:url value="/project/${project.id}/edit" />">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
          </button>
          <h4 class="modal-title" id="myModalLabel">Edit Project</h4>
        </div>
        <div class="modal-body">
          <div class="form-group ${error_name ? 'has-error' : ''}">
            <label for="project-name" class="col-sm-3 control-label">Name</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" name="name" placeholder="Name" value="${project.name}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-description" class="col-sm-3 control-label">Description</label>
            <div class="col-sm-9">
              <textarea class="form-control" name="description" placeholder="Description">${project.description}</textarea>
            </div>
          </div>
          <div class="form-group ${error_server ? 'has-error' : ''}">
            <label for="project-server" class="col-sm-3 control-label">Server</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" name="server" placeholder="Server" value="${project.server}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dbclasspath" class="col-sm-3 control-label">JDBC Classpath</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dbclasspath" name="dbClasspath" placeholder="/path/to/jdbc-connector.jar" value="${project.dbClasspath}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dbdriver" class="col-sm-3 control-label">JDBC Driver</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dbdriver" name="dbDriver" placeholder="org.jdbc.driver.JDBCDriver" value="${project.dbDriver}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dburl" class="col-sm-3 control-label">JDBC URL</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dburl" name="dbUrl" placeholder="jdbc:database-url" value="${project.dbUrl}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dbuserid" class="col-sm-3 control-label">JDBC Username</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dbuserid" name="dbUserid" placeholder="JDBC Username" value="${project.dbUserid}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-dbpassword" class="col-sm-3 control-label">JDBC Password</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-dbpassword" name="dbPassword" placeholder="JDBC Password" value="${project.dbPassword}">
            </div>
          </div>
          <div class="form-group">
            <label for="project-schedule" class="col-sm-3 control-label">Schedule</label>
            <div class="col-sm-9">
              <input type="text" class="form-control" id="project-schedule" name="schedule" placeholder="Cron Expression" value="${project.schedule}">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <input type="hidden" class="form-control" name="id" value="${project.id}">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary">Save</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="delete-project">
  <div class="modal-dialog">
    <div class="modal-content">
      <form class="form-horizontal" role="form" method="POST" action="<c:url value="/project/${project.id}/delete" />">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
          </button>
          <h4 class="modal-title" id="myModalLabel">Delete Project</h4>
        </div>
        <div class="modal-body">
          <p class="text-center">Are you sure?</p>
        </div>
        <div class="modal-footer">
          <input type="hidden" class="form-control" name="id" value="${project.id}">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-danger">Delete</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="new-script">
  <div class="modal-dialog">
    <div class="modal-content">
      <form class="form-horizontal" role="form" method="POST" action="<c:url value="/project/${project.id}/newscript" />">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
          </button>
          <h4 class="modal-title" id="myModalLabel">New Script</h4>
        </div>
        <div class="modal-body">
          <div class="form-group ${error_name ? 'has-error' : ''}">
            <label for="script-name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" name="name" placeholder="Name">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <input type="hidden" name="shared" value="false" />
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary">Save</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="new-shared-script">
  <div class="modal-dialog">
    <div class="modal-content">
      <form class="form-horizontal" role="form" method="POST" action="<c:url value="/project/${project.id}/newscript" />">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
          </button>
          <h4 class="modal-title" id="myModalLabel">New Shared Script</h4>
        </div>
        <div class="modal-body">
          <div class="form-group ${error_name ? 'has-error' : ''}">
            <label for="script-name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" name="name" placeholder="Name">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <input type="hidden" name="shared" value="true" />
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary">Save</button>
        </div>
      </form>
    </div>
  </div>
</div>

<%@include file="includes/footer.jsp"%>