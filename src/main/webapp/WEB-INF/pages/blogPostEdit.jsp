<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>${articleEditingTitle}</h3>
    <hr>
    
    <form:form class="form-horizontal" id="newPostForm" role="form" modelAttribute="newPostModel" method="POST" action="${pageContext.request.contextPath}/admin/blog/${actionName}">
      <form:hidden path="articleId"/>
      <div class="form-group">
        <label class="col-sm-2 control-label">Title</label>
        <div class="col-md-8">
          <form:input path="articleTitle" class="form-control"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Article Type</label>
        <div class="col-md-2">
          <form:select class="col-sm-2 form-control" path="articleType" items="${articleTypeList}" />
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Category</label>
        <div class="col-md-5">
          <form:input path="articleCategory" class="form-control"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Keywords</label>
        <div class="col-md-5">
          <form:input path="articleKeywords" class="form-control"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Summary</label>
        <div class="col-md-8">
          <form:textarea path="articleSummary" class="form-control" rows="10"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Content</label>
        <div class="col-md-8">
          <form:textarea path="articleContent" class="form-control" rows="18"/>
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-2"></div>
        <label class="col-sm-2 control-label">
          <form:checkbox path="articlePublished"/>Publish this article
        </label>
      </div>
      <div class="form-group">
        <div class="col-md-6"></div>
        <div class="col-md-6">
          <input class="btn btn-default" name="newPostPreview" type="submit" value="Preview"/>
          <input class="btn btn-primary" name="newPostSaveDraft" type="submit" value="Save/Update"/>
          <input class="btn btn-danger" type="reset" value="Clear"/>
        </div>
      </div>
    </form:form>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>