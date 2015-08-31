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
      <div class="row">
        <div class="col-md-3">
          <c:choose>
            <c:when test="${articleListPageModel.totalElementsCount == 0}">
              <h4><span class="label label-info">${articleListPageModel.totalElementsCount} articles available</span></h4>
            </c:when>
            <c:otherwise>
              <h4><span class="label label-info">${articleListPageModel.totalElementsCount} articles and counting ...</span></h4>
            </c:otherwise>            
          </c:choose>
        </div>
        <div class="col-md-9 text-right">
          <p>
            <a href="${pageContext.request.contextPath}/admin/blog/newPost" class="btn btn-default">New Post</a>
          </p>
        </div>
      </div>

      <c:choose>
      <c:when test="${!articleListPageModel.isDataModelEmpty()}">

      <table class="table table-hover">
        <tr>
          <th class="col-md-1">Type</th>
          <th class="col-md-4">Subject</th>
          <th class="col-md-2">Author</th>
          <th class="col-md-3">Last Modified</th>
          <th class="col-md-2">Actions</th>
        </tr>
        <c:forEach items="${articleListPageModel.getListItems()}" var="postInfo">
          <tr>
            <td>
              <c:choose>
                <c:when test="${postInfo.articleType.equals('post')}">
                  <span class="glyphicon glyphicon-book"></span> Post
                </c:when>
                <c:otherwise>
                  <span class="glyphicon glyphicon-file"></span> Page
                </c:otherwise>
              </c:choose>
            </td>
            <td><a href="${pageContext.request.contextPath}/admin/blog/editPost/${postInfo.articleId}" class="btn btn-primary"><span class="glyphicon glyphicon-pencil"></span></a>
              <a href="${pageContext.request.contextPath}/admin/blog/previewPost/${postInfo.articleId}"><strong>${postInfo.articleTitle}</strong></a>
            </td>
            <td><span class="glyphicon glyphicon-user"></span> ${postInfo.authorName}</td>
            <td><span class="glyphicon glyphicon-calendar"></span> ${postInfo.getArticleUpdateDateString()}</td>
            <td>
              <button class="btn btn-danger" onclick="deletePostJson('${postInfo.articleId}')"><span class="glyphicon glyphicon-floppy-remove"></span></button>
              <c:choose>
                <c:when test="${!postInfo.articlePublished}">
                  <button class="btn btn-success" onclick="publishPostJson('${postInfo.articleId}', 'true')"><span class="glyphicon glyphicon-file"></span></button>
                </c:when>
                <c:otherwise>
                  <button class="btn btn-default" onclick="publishPostJson('${postInfo.articleId}', 'false')"><span class="glyphicon glyphicon-file"></span></button>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>        
      </table>

      <div class="row">
        <div class="md-col-12 text-right">
          <nav>
            <ul class="pagination">
              <c:if test="${articleListPageModel.canGoBack}">
              <li>
                <a href="${pageContext.request.contextPath}/admin/blog/allMyPosts?pageIdx=${articleListPageModel.previousPageIdx}" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              </c:if>
              <c:if test="${articleListPageModel.hasMoreElement}">
              <li>
                <a href="${pageContext.request.contextPath}/admin/blog/allMyPosts?pageIdx=${articleListPageModel.nextPageIdx}" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
              </c:if>
            </ul>
          </nav>
        </div>
      </div>

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
    <script type="text/javascript">       
        var deletePostJson = function (articleId)
        {
           $.ajax({
              type: "DELETE",
              url: "${pageContext.request.contextPath}/admin/blog/deletePost?articleId=" + articleId,
              xhrFields: {
                 withCredentials: true
              },
              success: function(data) {
                 location.reload(true);
              },
              error: function() {
              }
           });
        }
        
        var publishPostJson = function (articleId, toPublish)
        {
           $.ajax({
              type: "PUT",
              url: "${pageContext.request.contextPath}/admin/blog/publishPost?articleId=" + articleId + "&" + "toPublish=" + toPublish,
              xhrFields: {
                 withCredentials: true
              },
              success: function(data) {
                 location.reload(true);
              },
              error: function() {
              }
           });
        }
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>
