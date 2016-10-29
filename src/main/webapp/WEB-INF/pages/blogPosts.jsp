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
                <div class="row">
                  <div class="col-md-12">
                    <h4 class="media-heading">
                      <a href="${pageContext.request.contextPath}/blog/view/${articleModel.articleId}">${articleModel.articleTitle}</a>
                    </h4>
                    <hr class="postlist-hr">
                    <c:if test="${articleModel.articleSummary != null && articleModel.articleSummary.length() > 0}">
                       <div class="row">
                          <div class="col-xs-4 col-sm-2 col-md-2 col-lg-3 text-center">
                             <div class="thumbnail gallery-image">
                                <c:choose>
                                   <c:when test="${articleModel.articleIconId != null && articleModel.articleIconId.length() > 0}">
                                       <img src="${pageContext.request.contextPath}/public/imgresource/${articleModel.articleIconId}" width="100%">
                                   </c:when>
                                   <c:otherwise>
                                       <img src="${pageContext.request.contextPath}/assets/imgs/default-user.jpg" width="100%">
                                   </c:otherwise>
                                </c:choose>
                             </div>
                         </div>
                          <div class="col-xs-12 col-sm-10 col-md-10 col-lg-9">
                             <c:if test="${articleModel.articleSummary != null && articleModel.articleSummary.length() > 0}">
                             <div class="postlist-postsummary">
                                <p>
                                ${articleModel.articleSummary}
                                </p>
                             </div>
                             </c:if>
                          </div>
                       </div>
                    </c:if>
                    
                    <div class="thumbnail postlist-bottom-sidebar">
                      <div class="row">
                        <div class="col-md-3">
                          <span class="glyphicon glyphicon-calendar"></span>
                          ${articleModel.getArticleUpdateDateString()}
                        </div>
                        <div class="col-md-3">
                          <span class="glyphicon glyphicon-user"></span>
                          <a href="${pageContext.request.contextPath}/userProfile/${articleModel.authorId}">
                          <c:choose>
                             <c:when test="${articleModel.authorName != null && articleModel.authorName.length() > 0}">
                             ${articleModel.authorName}
                             </c:when>
                             <c:otherwise>
                             ${articleModel.authorUserName}
                             </c:otherwise>
                          </c:choose>
                          </a>
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
