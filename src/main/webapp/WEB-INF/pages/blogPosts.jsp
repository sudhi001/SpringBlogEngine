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
    
    <hr>
    
    <c:choose>
      <c:when test="${articleListPageModel.getListItems().size() > 0}">
        <c:forEach var="articleModel" items="${articleListPageModel.getListItems()}">
          <div class="post-list">
            <div class="panel panel-default">
              <div class="panel-body">
                <c:choose>
                  <c:when test="${articleModel.articleIconResId != null && articleModel.articleIconResId.length() > 0}">
                    <div class="row">
                      <div class="col-md-2">
                        <a href="${pageContext.request.contextPath}/blog/view/${articleModel.articleId}">
                          <img class="media-object" src="${pageContext.request.contextPath}/public/imgresource/${articleModel.articleIconResId}" width="100%" height="100%">
                        </a>
                      </div>
                      <div class="col-md-10">
                        <h4><a href="${pageContext.request.contextPath}/blog/view/${articleModel.articleId}">
                           ${articleModel.articleTitle}
                        </a></h4>
                        <hr class="postlist-hr">
                        <div class="postlist-postsummary">
                          <p>
                          ${articleModel.articleSummary}
                          </p>
                        </div>
                        
                        <div class="thumbnail postlist-bottom-sidebar">
                          <div class="row">
                            <div class="col-md-3">
                              <span class="glyphicon glyphicon-calendar"></span>
                              ${articleModel.getArticleUpdateDateString()}
                            </div>
                            <div class="col-md-3">
                              <span class="glyphicon glyphicon-user"></span>
                              ${articleModel.getAuthorName()}
                            </div>             
                            <div class="col-md-2">
                            </div>                            
                            <div class="col-md-4 text-right">
                              <a href="${pageContext.request.contextPath}/blog/view/${articleModel.articleId}">Read More..</a>
                            </div>
                          </div>
                        </div>
                      </div>                      
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="row">
                      <div class="col-md-12">
                        <h4 class="media-heading">
                          <a href="${pageContext.request.contextPath}/blog/view/${articleModel.articleId}">${articleModel.articleTitle}</a>
                        </h4>
                        <hr class="postlist-hr">
                        <div class="postlist-postsummary">
                          <p>
                          ${articleModel.articleSummary}
                          </p>
                        </div>
                        
                        <div class="thumbnail postlist-bottom-sidebar">
                          <div class="row">
                            <div class="col-md-3">
                              <span class="glyphicon glyphicon-calendar"></span>
                              ${articleModel.getArticleUpdateDateString()}
                            </div>
                            <div class="col-md-3">
                              <span class="glyphicon glyphicon-user"></span>
                              ${articleModel.getAuthorName()}
                            </div>             
                            <div class="col-md-2">
                            </div>                            
                            <div class="col-md-4 text-right">
                              <a href="${pageContext.request.contextPath}/blog/view/${articleModel.articleId}">Read More...</a>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </c:otherwise>
                </c:choose>
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
