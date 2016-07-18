<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <c:if test="${articleModel.isPreviewMode()}">
    <div class="row">
      <div class="col-md-12">
        <div class="alert alert-info" role="alert">
          <strong>Your previewing this page! <a href="${pageContext.request.contextPath}/admin/blog/editPost/${articleModel.articleId}">Go back to editing</a></strong>
        </div>
      </div>
    </div>
    </c:if>
    
    <div class="content-box">
      <div class="box-body" style="display: block;">
        <div class="row">
          <div class="col-xs-12">
<c:out value="${articleModel.articleContent}" escapeXml = "false"/>
          </div>
        </div>
        <button type="button" class="btn btn-default btn-xs"><i class="fa fa-share"></i> Share</button>
        <button type="button" class="btn btn-default btn-xs"><i class="fa fa-thumbs-o-up"></i> Like</button>
        <span class="pull-right text-muted">127 likes</span>
      </div>
    </div>
    
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>