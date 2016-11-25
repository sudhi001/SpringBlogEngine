<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/default-skin/default-skin.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>
  
  <tiles:putAttribute name="body">
    <div class="content-box">
      <div class="box-header with-border">
        <div class="user-block">
          <div class="row">
            <div class="col-xs-1">
               <!--<c:choose>
                  <c:when test="${articleModel.authorIconId != null && articleModel.authorIconId.length() > 0}">
                     <img class="img-oval" src="${pageContext.request.contextPath}/public/imgresource/${articleModel.authorIconId}" alt="User Image">
                  </c:when>
                  <c:otherwise>
                     <img class="img-oval" src="${pageContext.request.contextPath}/assets/imgs/default-user.jpg" alt="User Image">
                  </c:otherwise>
               </c:choose>-->
            </div>
            <div class="col-xs-11">
              <span class="username">${articleModel.articleTitle}</span>
              <div class="row">
                 <!-- <div class="col-xs-6"><span class="description">By <a href="${pageContext.request.contextPath}/userProfile/${articleModel.authorId}">
                    <c:choose>
                       <c:when test="${articleModel.authorName != null && articleModel.authorName.length() > 0}">
                          ${articleModel.authorName}
                       </c:when>
                       <c:otherwise>
                          ${articleModel.authorUserName}
                       </c:otherwise>
                    </c:choose>
                 </a></span></div>-->
                 <div class="col-xs-6"><span class="description"><strong>Updated</strong> - ${articleModel.getArticleUpdateDateString()}</span></div>
              </div>
              <!--<c:if test="${articleModel.articleCategory != null && articleModel.articleCategory.length() > 0}">
                 <div class="row">
                    <div class="col-xs-12"><span class="description"><strong>Category:</strong> ${articleModel.articleCategory}</span></div>
                 </div>
              </c:if>
              <c:if test="${articleModel.articleKeywords != null && articleModel.articleKeywords.length() > 0}">
                 <div class="row">
                    <div class="col-xs-12"><span class="description"><strong>Keywords:</strong> ${articleModel.articleKeywords}</span></div>
                 </div>
              </c:if>-->
            </div>
          </div>
        </div>
      </div>

      <div class="box-body" style="display: block;">
        <div class="row">
          <!-- <div class="col-xs-12">
<c:out value="${articleModel.articleContent}" escapeXml = "false"/>
          </div> -->
        </div>
        <!-- <span class="pull-right text-muted">127 likes - 3 comments</span> -->
      </div>

      <div class="box-footer box-comments" style="display: block;">
          <!--<c:if test="${!articleModel.isPreviewMode() && articleComments != null && articleComments.size() > 0}">
          <c:forEach items="${articleComments}" var="commentItem">
          <div class="box-comment">
             <c:choose>
               <c:when test="${commentItem.commentUserIconId != null && commentItem.commentUserIconId.length() > 0}">
                  <c:choose>
                     <c:when test="${commentItem.commentUserProfileId != null && commentItem.commentUserProfileId.length() > 0}">
                        <a href="${pageContext.request.contextPath}/userProfile/${articleModel.authorId}"><img class="img-oval img-sm" src="${pageContext.request.contextPath}/public/imgresource/${commentItem.commentUserIconId}"></a>
                     </c:when>
                     <c:otherwise>
                        <img class="img-oval img-sm" src="${pageContext.request.contextPath}/public/imgresource/${commentItem.commentUserIconId}">
                     </c:otherwise>
                  </c:choose>
               </c:when>
               <c:otherwise>
                  <img class="img-oval img-sm" src="${pageContext.request.contextPath}/assets/imgs/default-user.jpg">
               </c:otherwise>
             </c:choose>
             <div class="comment-text">
                <span class="username">
<c:choose>
   <c:when test="${commentItem.commenterName != null && commentItem.commenterName.length() > 0}">
      ${commentItem.commenterName}
   </c:when>
   <c:when test="${commentItem.commentUserFullName != null && commentItem.commentUserFullName.length() > 0}">
      ${commentItem.commentUserFullName}
   </c:when>
   <c:when test="${commentItem.commentUserName != null && commentItem.commentUserName.length() > 0}">
      ${commentItem.commentUserName}
   </c:when>
   <c:otherwise>
      Anonymous User**
   </c:otherwise>
</c:choose>
                <span class="text-muted pull-right">${commentItem.getCommentCreateDateString()}</span></span>
                <hr class="margin-updown-tiny">
                <div class="row" id="prevComment_${commentItem.commentId}">
                </div>
                <div class="row">
                   <div class="col-xs-12">
<h4 class="margin-updown-tiny">${commentItem.commentTitle}</h4>
<p class="margin-updown-tiny">
${commentItem.commentContent}
</p>
                   </div>
                </div>
                <div class="row">
                   <div class="col-xs-12 text-right">
                      <c:if test="${commentItem.parentCommentId != null && commentItem.parentCommentId.length() > 0}">
                         <button class="btn btn-sm" onclick="loadArticleComment('${articleModel.articleId}', '${commentItem.parentCommentId}', '#prevComment_${commentItem.commentId}', '${pageContext.request.contextPath}')">Previous Comment/Reply</button>
                      </c:if>
                      <button class="btn btn-sm" onclick="handleClickAddCommentBtn('${articleModel.articleId}', '${commentItem.commentId}', '${pageContext.request.contextPath}')">Reply</button>
                   </div>
                </div>
             </div>
          </div>
          </c:forEach>
         </c:if>-->
      </div>

      <div class="box-footer" style="display: block;">
         <!--<c:if test="${!articleModel.isPreviewMode()}">
            <button class="btn btn-default" onclick="handleClickAddCommentBtn('${articleModel.articleId}', '', '${pageContext.request.contextPath}')">Add Comment</button>
         </c:if>-->
      </div>
    </div>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe-ui-default.min.js"></script>
    <script>
       var viewImage = function (imgUrl, imgWidth, imgHeight) {
          var pswpElement = document.querySelectorAll('.pswp')[0];
   
          var items = [{
               src: imgUrl,
               w: imgWidth,
               h: imgHeight
            }];
   
          var options = {
             index: 0
          };
      
          var gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items, options);
          gallery.init();
       }
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>