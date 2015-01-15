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
        <li><a href="<c:url value="/project/${project.id}"/>">[project] ${project.name}</a></li>
        <li class="active">[script] ${script.name}</li>
      </ol>

      <h1 class="page-header">
        <span>[script] ${script.name}</span> <span class="pull-right">
          <button type="button" class="btn btn-warning" data-toggle="collapse" data-target="#edit-script">
            <span class="glyphicon glyphicon-edit"></span> Edit
          </button>
          <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#delete-script">
            <span class="glyphicon glyphicon-remove-sign"></span> Delete
          </button>
        </span>
      </h1>
      
      <div class="collapse in" id="edit-script">
        <div class="well">
          <form method="POST" action="<c:url value="/project/${project.id}/${script.id}/edit" />">
            <div class="col-sm-6">
              <div class="form-group">
                <label for="script-name">Script name</label>
                <input type="text" class="form-control" id="script-name" name="name" placeholder="Script Name" value="${script.name}"/>
              </div>
            </div>
            <div class="col-sm-6">
              <div class="form-group">
                <label for="script-schedule">Schedule</label>
                <input type="text" class="form-control" id="script-schedule" name="schedule" placeholder="Cron Expression" value="${script.schedule}">
              </div>
            </div>
            <div class="col-sm-12">
              <div class="form-group">
                <label for="script-content">Script content</label>
                <div id="oeditor" class="oeditor" data-editor-theme="<c:url value="/resources/orion/themes/eclipse"/>"></div>
                <textarea name="content" id="script-content" class="hidden">${script.content}</textarea>
              </div>
            </div>
            <div class="form-group clearfix">
              <div class="col-xs-4">
                <label for="exampleInputFile">Or import from file</label>
                <input type="file" id="script-upload">
                <br/>
                <button type="submit" class="btn btn-primary">Save</button>
              </div>
              <div class="col-xs-8">
                <div class="panel panel-success">
                  <div class="panel-heading">
                    <h3 class="panel-title">Quick hints:</h3>
                  </div>
                  <div class="panel-body">
                    <table class="table table-bordered" style="line-height: 22px;">
                      <tr>
                        <td width="13%"><kbd>CTRL</kbd> <kbd>D</kbd></td>
                        <td width="20%">Delete a line</td>
                        <td width="13%"><kbd>CTRL</kbd> <kbd>F</kbd></td>
                        <td width="20%">Find and replace</td>
                        <td width="13%"><kbd>CTRL</kbd> <kbd>S</kbd></td>
                        <td width="20%">Save</td>
                      </tr>
                      <tr>
                        <td><kbd>ALT</kbd> <kbd>&uarr;</kbd> <kbd>&darr;</kbd></td>
                        <td>Move line(s) up/down</td>
                        <td style="line-height: 22px;"><kbd>TAB</kbd></td>
                        <td>Indent</td>
                        <td><kbd>Double Click</kbd></td>
                        <td>Set annotation</td>
                      </tr>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>

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
        Statistic <span class="pull-right"> <a target="_blank" href="<c:url value="/project/${project.id}/${script.id}/execute" />" class="btn btn-success" onClick="setTimeout(function() {location.reload()}, 2000)"><span class="glyphicon glyphicon-play-circle"></span> Execute</a>
        </span>
      </h2>

      <div class="table-responsive">
        <c:if test="${empty script.scriptExecutions}">
          <p class="text-center">
            <em>The script is never executed. <a target="_blank" href="<c:url value="/project/${project.id}/${script.id}/execute" />" onClick="setTimeout(function() {location.reload()}, 2000)">Execute now</a></em>
          </p>
        </c:if>
        <c:if test="${not empty script.scriptExecutions}">
          <table class="table table-striped">
            <thead>
              <tr>
                <th width="5%">#</th>
                <th width="15%" class="text-center">Date Executed</th>
                <th width="15%" class="text-center">Status / Result</th>
                <th>Note</th>
              </tr>
            </thead>
            <tfoot>
              <tr>
                <th colspan="4"></th>
              </tr>
            </tfoot>
            <tbody>
              <c:forEach items="${scriptExecutions}" var="scriptExecution">
                <tr>
                  <td>${scriptExecution.id}</td>
                  <td class="text-center"><fmt:formatDate value="${scriptExecution.executedDate}" pattern="dd/MM/yyyy hh:mm a" /></td>
                  <td class="text-center">
                    <span class="glyphicon ${scriptExecution.status == 0 ? 'glyphicon-refresh text-info' : scriptExecution.status == 1 ?'glyphicon-remove text-danger' : 'glyphicon-ok text-success'}"></span> &nbsp; &nbsp;
                    <a target="_blank" href="<c:url value="/results/${project.id}/${script.id}/${scriptExecution.id}/output" />"><span class="glyphicon glyphicon-share-alt"></span></a> &nbsp; &nbsp;
                    <a target="_blank" href="<c:url value="/project/${project.id}/${script.id}/${scriptExecution.id}" />" class="text-info"><span class="glyphicon glyphicon-list-alt"></span></a>
                  </td>
                  <td>
                    <c:if test="${scriptExecution.comment != null}">
                      <pre>${scriptExecution.comment}</pre>
                    </c:if>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>

          <nav class="text-center">
            <ul class="pagination">
              <li><a href="<c:url value="/project/${project.id}/${script.id}" />"><span>&laquo;</span></a></li>
              <c:forEach begin="${pagination.begin}" end="${pagination.end}" var="page">
                <c:if test="${page == 1}">
                  <li><a href="<c:url value="/project/${project.id}/${script.id}" />">${page}</a></li>
                </c:if>
                <c:if test="${page > 1}">
                  <li><a href="<c:url value="/project/${project.id}/${script.id}/?page=${page}" />">${page}</a></li>
                </c:if>
              </c:forEach>
              <c:if test="${pagination.last == 1}">
                <li><a href="<c:url value="/project/${project.id}/${script.id}" />"><span>&raquo;</span></a></li>
              </c:if>
              <c:if test="${pagination.last > 1}">
                <li><a href="<c:url value="/project/${project.id}/${script.id}/?page=${pagination.last}" />"><span>&raquo;</span></a></li>
              </c:if>
            </ul>
          </nav>
        </c:if>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="delete-script">
  <div class="modal-dialog">
    <div class="modal-content">
      <form class="form-horizontal" role="form" method="POST" action="<c:url value="/project/${project.id}/${script.id}/delete" />">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
          </button>
          <h4 class="modal-title" id="myModalLabel">Delete Script</h4>
        </div>
        <div class="modal-body">
          <p class="text-center">Are you sure?</p>
        </div>
        <div class="modal-footer">
          <input type="hidden" class="form-control" name="id" value="${script.id}">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-danger">Delete</button>
        </div>
      </form>
    </div>
  </div>
</div>


<%@include file="includes/footer.jsp"%>