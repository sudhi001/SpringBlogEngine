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
    
    <div class="content-box">
      <div class="box-header with-border">
        <div class="user-block">
          <div class="row">
            <div class="col-xs-1">
               <c:if test="${articleModel.authorIconId != null && articleModel.authorIconId.length() > 0}">
                  <img class="img-oval" src="${pageContext.request.contextPath}/public/imgresource/${articleModel.authorIconId}" alt="User Image">
               </c:if>
            </div>
            <div class="col-xs-11">
              <span class="username">${articleModel.articleTitle}</span>
              <div class="row">
                 <div class="col-xs-6"><span class="description">By <a href="${pageContext.request.contextPath}/userProfile/${articleModel.authorId}">${articleModel.authorName}</a></span></div>
                 <div class="col-xs-6"><span class="description"><strong>Updated</strong> - ${articleModel.getArticleUpdateDateString()}</span></div>
              </div>
              <div class="row">
                 <div class="col-xs-12"><span class="description"><strong>Category:</strong> ${articleModel.articleCategory}</span></div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-body" style="display: block;">
        <div class="row">
          <div class="col-xs-12">
<c:out value="${articleModel.articleContent}" escapeXml = "false"/>
          </div>
        </div>
        <button type="button" class="btn btn-default btn-xs"><i class="fa fa-share"></i> Share</button>
        <button type="button" class="btn btn-default btn-xs"><i class="fa fa-thumbs-o-up"></i> Like</button>
        <!-- <span class="pull-right text-muted">127 likes - 3 comments</span> -->
      </div>

      <div class="box-footer box-comments" style="display: block;">
      </div>

      <div class="box-footer" style="display: block;">
        <form action="#" method="post">
          <!-- <img class="img-responsive img-oval img-sm" src="http://bootdey.com/img/Content/avatar/avatar1.png" alt="Alt Text"> -->
          <div class="img-push row">
            <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10">
               <input type="text" class="form-control input-sm" placeholder="Post your comments">
            </div>
            <div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
               <button type="submit" class="btn btn-default btn-sm">Comment</button>
            </div> 
          </div>
        </form>
      </div>
    </div>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>