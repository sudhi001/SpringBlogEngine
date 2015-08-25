<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>Blog Posts</h3>
    <hr>
    
    <c:choose>
      <c:when test="${articleListPageModel.getListItems().size() > 0}">
        <c:forEach var="articleModel" items="${articleListPageModel.getListItems()}">
          <div class="post-list">
            <h4><a href="">${articleModel.articleTitle}</a></h4>
            <p>
              ${articleModel.articleSummary}
            </p>
            <div class="text-right">
              <span class="glyphicon glyphicon-book"></span> Post
              <span class="glyphicon glyphicon-calendar"></span> ${articleModel.getArticleUpdateDateString()}         
              <span class="glyphicon glyphicon-user"></span> ${articleModel.authorName}         
            </div>
          </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <div class="post-list">
          <p>No more posts</p>
        </div>
      </c:otherwise>
    </c:choose>
    
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>
