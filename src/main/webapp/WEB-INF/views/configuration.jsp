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
        <li class="active">[system] Configuration</li>
      </ol>

      <h1 class="page-header">[system] Configuration</h1>

      <form method="POST" action="<c:url value="/configuration/save" />" class="form-horizontal">
        <div class="form-group">
          <label for="config-binary" class="col-sm-2 control-label">Webtest binary</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="config-binary" name="binary" placeholder="Webtest binary absolute path" value="${config.binary == null ? param.binary : config.binary}">
          </div>
        </div>
        <div class="form-group">
          <label for="config-storage" class="col-sm-2 control-label">Webtest storage</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="config-storage" name="storage" placeholder="Webtest storage absolute path" value="${config.storage == null ? param.storage : config.storage}">
          </div>
        </div>
        <div class="form-group">
          <label for="config-proxyHost" class="col-sm-2 control-label">Proxy Host</label>
          <div class="col-sm-4">
            <input type="text" class="form-control" id="config-proxyHost" name="proxyHost" placeholder="Proxy Host" value="${config.proxyHost}">
          </div>
        </div>
        <div class="form-group">
          <label for="config-proxyPort" class="col-sm-2 control-label">Proxy Port</label>
          <div class="col-sm-4">
            <input type="text" class="form-control" id="config-proxyPort" name="proxyPort" placeholder="Proxy Port" value="${config.proxyPort}">
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Save</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>


<%@include file="includes/footer.jsp"%>