<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>All My Posts</h3>
    <hr>
    
    <div class="post-list">
    <c:choose>
      <c:when test="${!articleListPageModel.isDataModelEmpty()}">
        <c:forEach items="${articleListPageModel.getListItems()}" var="postInfo">
          <div class="panel panel-default">
            <div class="panel-body">
              <a href="${pageContext.request.contextPath}/admin/blog/editPost/${postInfo.articleId}"><h4>${postInfo.articleTitle}</h4></a>
              <div class="post-summary">
                <p>${postInfo.articleSummary}</p>
              </div>
              <div class="text-right"><button class="btn btn-primary">Awesome!</button></div>
            </div>
          </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <p>No posts found</p>
      </c:otherwise>
    </c:choose>
    </div>
    
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>
