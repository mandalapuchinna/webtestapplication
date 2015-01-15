<%@include file="includes/header.jsp"%>

<div class="container-fluid">
  <div class="row">
    <%@include file="includes/sidebar.jsp"%>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <ol class="breadcrumb">
        <li><a href="<c:url value="/"/>">Dashboard</a></li>
        <li><a href="<c:url value="/project/${project.id}"/>">[project] ${project.name}</a></li>
        <li><a href="<c:url value="/project/${project.id}/${script.id}"/>">[script] ${script.name}</a></li>
        <li class="active">[execution] ${scriptExecution.id}</li>
      </ol>
      
      <pre class="log" data-status="${scriptExecution.status}">${scriptExecution.log == null ? 'Executing...' : scriptExecution.log}</pre>
    </div>
  </div>
</div>

<%@include file="includes/footer.jsp"%>