<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/default-skin/default-skin.css"> 
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
                 <div class="col-xs-6"><span class="description">By <a href="${pageContext.request.contextPath}/userProfile/${articleModel.authorId}">
                    <c:choose>
                       <c:when test="${articleModel.authorName != null && articleModel.authorName.length() > 0}">
                          ${articleModel.authorName}
                       </c:when>
                       <c:otherwise>
                          ${articleModel.authorUserName}
                       </c:otherwise>
                    </c:choose>
                 </a></span></div>
                 <div class="col-xs-6"><span class="description"><strong>Updated</strong> - ${articleModel.getArticleUpdateDateString()}</span></div>
              </div>
              <c:if test="${articleModel.articleCategory != null && articleModel.articleCategory.length() > 0}">
                 <div class="row">
                    <div class="col-xs-12"><span class="description"><strong>Category:</strong> ${articleModel.articleCategory}</span></div>
                 </div>
              </c:if>
              <c:if test="${articleModel.articleKeywords != null && articleModel.articleKeywords.length() > 0}">
                 <div class="row">
                    <div class="col-xs-12"><span class="description"><strong>Keywords:</strong> ${articleModel.articleKeywords}</span></div>
                 </div>
              </c:if>
            </div>
          </div>
        </div>
      </div>

      <div class="box-body" style="display: block;">
        <div class="row">
          <div class="col-xs-12" style="padding-left:100px; padding-rght: 100px;">
<c:out value="${articleModel.articleContent}" escapeXml = "false"/>
          </div>
        </div>
        <!-- <span class="pull-right text-muted">127 likes - 3 comments</span> -->
      </div>

      <div class="box-footer box-comments" style="display: block;">
      </div>

      <div class="box-footer" style="display: block;">
         <form class="form-horizontal">
            <legend>Add your comment</legend>
            <div class="form-group">
               <label class="col-xs-12 col-sm-3 control-label" for="commentTitle">Subject</label>
               <div class="col-xs-12 col-sm-9">
                  <input class="form-control input-sm" type="text" id="commentTitle" name="commentTitle">
               </div>
            </div>
            <div class="form-group">
               <label class="col-xs-12 col-sm-3 control-label" for="commentTitle">Your Name</label>
               <div class="col-xs-12 col-sm-6">
                  <input class="form-control input-sm" type="text" id="commenterName" name="commenterName">
               </div>
            </div>
            <div class="form-group">
               <label class="col-xs-12 col-sm-3 control-label" for="commentTitle">Your Email</label>
               <div class="col-xs-12 col-sm-6">
                  <input class="form-control input-sm" type="text" id="commenterEmail" name="commenterEmail">
               </div>
            </div>
            <div class="form-group">
               <label class="col-xs-12 col-sm-3 control-label" for="commentContent">Your Comment</label>
               <div class="col-xs-12 col-sm-9">
<textarea class="form-control input-sm" row="6" id="commenterContent" name="commenterContent">
</textarea>
               </div>
            </div>
         </form>
      </div>
    </div>
    
    <!-- PhotoSwipe elements -->
      <div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="pswp__bg"></div>
          <div class="pswp__scroll-wrap">

              <div class="pswp__container">
                  <div class="pswp__item"></div>
                  <div class="pswp__item"></div>
                  <div class="pswp__item"></div>
              </div>

              <div class="pswp__ui pswp__ui--hidden">

                  <div class="pswp__top-bar">
                      <div class="pswp__counter"></div>
                      <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>
                      <button class="pswp__button pswp__button--share" title="Share"></button>
                      <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>
                      <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>

                      <div class="pswp__preloader">
                          <div class="pswp__preloader__icn">
                            <div class="pswp__preloader__cut">
                              <div class="pswp__preloader__donut"></div>
                            </div>
                          </div>
                      </div>
                  </div>

                  <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
                      <div class="pswp__share-tooltip"></div> 
                  </div>

                  <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
                  </button>

                  <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
                  </button>

                  <div class="pswp__caption">
                      <div class="pswp__caption__center"></div>
                  </div>
              </div>
          </div>
      </div>
          
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe-ui-default.min.js"></script>
    <script type="text/javascript">
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