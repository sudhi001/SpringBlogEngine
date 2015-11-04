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
          <strong>Your previewing this post! <a href="${pageContext.request.contextPath}/admin/blog/editPost/${articleModel.articleId}">Go back to editing</a></strong>
        </div>
      </div>
    </div>
    </c:if>
    <div class="row">
      <div class="col-md-12">
        <div class="thumbnail">
          <div class="row">
            <div class="col-md-4">
              <div class="breadcrumb"><strong>Category:</strong> ${articleModel.articleCategory}</div>
            </div>
          </div>
          
          <div class="post-summary">
            <div class="row">
              <div class="col-md-12">
                <h2>${articleModel.articleTitle}</h2>
              </div>
            </div>
            <div class="row">
              <div class="col-md-3"><strong>Author:</strong> ${articleModel.authorName}</div>
              <div class="col-md-9 text-right"><strong>Last Modified:</strong> ${articleModel.getArticleUpdateDateString()}</div>            
            </div>
            <br/>        
<c:out value="${articleModel.articleContent}" escapeXml = "false"/>
          </div>
          
          <div class="row post-placeholder"></div>
          
          <div class="row">
            <div class="col-md-12">
              <div class="breadcrumb post-bottom-margin">
                <strong>Keywords:</strong> 
                <c:forEach var="keyword" items="${articleModel.getArticleKeywordList()}">
                  <span class="label label-info">${keyword}</span>
                </c:forEach>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>