<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <div class="row">
      <div class="col-md-12 text-right">
        <h2>All My Blog Posts</h2>
      </div>
    </div>
    
    <c:choose>
      <c:when test="${articleListPageModel.getListItems().size() > 0}">
        <c:forEach var="articleModel" items="${articleListPageModel.getListItems()}">
          <div class="post-list">
          
            <div class="row">
              <div class="col-md-3"></div>
              <div class="col-md-9">
                <h3>${articleModel.articleTitle}</h3>
                <hr>
              </div>
            </div>
            <div class="row">
              <div class="col-md-3">
                <div class="text-center">
                  <img src="https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240">
                </div>
              </div>
              <div class="col-md-9">

                <div style="min-height: 200px;">
                  <p>
${articleModel.articleSummary}
                  </p>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <p class="text-right">
                      <a href="#">Read more...</a>
                    </p>
                  </div>
                </div>
                <div class="breadcrumb">
                  <div class="text-right">
                    <span class="glyphicon glyphicon-book"></span> ${articleModel.articleType}
                    <span class="glyphicon glyphicon-calendar"></span> ${articleModel.getArticleUpdateDateString()}
                    <span class="glyphicon glyphicon-user"></span> ${articleModel.authorName}
                  </div>
                </div>
              </div>
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
