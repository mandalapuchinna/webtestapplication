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
            </c:forEach>
          </ul>
        </div>
      </c:if>

      <ol class="breadcrumb">
        <li class="active">Reports</li>
      </ol>

      <h1 class="page-header">[reports]</h1>

      <form class="form-horizontal" action="<c:url value="/reports" />" method="GET">
        <div class="form-group">
          <label for="project" class="col-sm-2 control-label">Project</label>
          <div class="col-sm-10">
            <select class="form-control" name="projectId">
              <option></option>
              <c:forEach items="${projects}" var="project">
                <option value="${project.id}" ${project.id == param.projectId ? "selected='selected'": ""}>${project.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label for="startdate" class="col-sm-2 control-label">Start Date</label>
          <div class="col-sm-4">
            <input type="text" class="form-control datepicker" id="startdate" name="startdate" value="${param.startdate}">
          </div>
          <label for="enddate" class="col-sm-2 control-label">End Date</label>
          <div class="col-sm-4">
            <input type="text" class="form-control datepicker" id="enddate" name="enddate" value="${param.enddate}">
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Submit</button>
          </div>
        </div>
      </form>

      <div class="table-responsive">
        <c:if test="${empty results}">
          <p class="text-center">
            <em>There is no results found.</em>
          </p>
        </c:if>
        <c:if test="${not empty results}">
          <table class="table" data-toggle="table" data-classes="table">
            <thead>
              <tr>
                <th data-sortable="true" data-width="50">#</th>
                <th data-sortable="true">Name</th>
                <th data-sortable="true" data-width="150" class="text-center">Total Execution</th>
                <th data-sortable="true" data-width="150" class="text-center" data-sorter="percentSorter">Success</th>
                <th data-sortable="true" data-width="150" class="text-center" data-sorter="percentSorter">Failed</th>
                <th data-sortable="true" data-width="150" class="text-center" data-sorter="percentSorter">Running</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${results}" var="result">
                <tr class="${result.success > 80 ? "bg-success" : result.failure > 50 ? "bg-danger": "bg-warning"}">
                  <td>${result.scriptId}</td>
                  <td>${result.scriptName}</td>
                  <td class="text-center"><fmt:formatNumber value="${result.total}" pattern="0.##"></fmt:formatNumber></td>
                  <td class="text-center"><fmt:formatNumber value="${result.success}" pattern="0.##"></fmt:formatNumber>%</td>
                  <td class="text-center"><fmt:formatNumber value="${result.failure}" pattern="0.##"></fmt:formatNumber>%</td>
                  <td class="text-center"><fmt:formatNumber value="${result.running}" pattern="0.##"></fmt:formatNumber>%</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:if>
      </div>
    </div>
  </div>
</div>

<%@include file="includes/footer.jsp"%>