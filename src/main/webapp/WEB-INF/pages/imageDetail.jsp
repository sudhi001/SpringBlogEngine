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
               <c:choose>
                  <c:when test="${viewableImage.ownerIconFileId != null && viewableImage.ownerIconFileId.length() > 0}">
                     <img class="img-oval" src="${pageContext.request.contextPath}/public/imgresource/${viewableImage.ownerIconFileId}" alt="User Image">
                  </c:when>
                  <c:otherwise>
                     <img class="img-oval" src="${pageContext.request.contextPath}/assets/imgs/default-user.jpg" alt="User Image">
                  </c:otherwise>
               </c:choose>
            </div>
            <div class="col-xs-11">
              <span class="username">${viewableImage.imageTitle}</span>
              <div class="row">
                 <div class="col-xs-6"><span class="description">By <a href="${pageContext.request.contextPath}/userProfile/${viewableImage.ownerId}">
                    <c:choose>
                       <c:when test="${viewableImage.ownerUserFullName != null && viewableImage.ownerUserFullName.length() > 0}">
                          ${viewableImage.ownerUserFullName}
                       </c:when>
                       <c:otherwise>
                          ${viewableImage.ownerName}
                       </c:otherwise>
                    </c:choose>
                 </a></span></div>
                 <div class="col-xs-6"><span class="description"><strong>Uploaded Date:</strong> - ${viewableImage.getUpdloadDateString()}</span></div>
              </div>
              <c:if test="${viewableImage.imageKeywords != null && viewableImage.imageKeywords.length() > 0}">
                 <div class="row">
                    <div class="col-xs-12"><span class="description"><strong>Keywords:</strong> ${viewableImage.imageKeywords}</span></div>
                 </div>
              </c:if>
            </div>
          </div>
        </div>
      </div>

      <div class="box-body" style="display: block;">
         <div class="row">
           <c:choose>
              <c:when test="${viewableImage.imageWidth < viewableImage.imageHeight}">
                 <div class="col-xs-12 col-md-8 col-md-offset-2">
                    <div class="thumbnail">
                       <a href="#" onclick="viewImage('${pageContext.request.contextPath}/public/image/${viewableImage.imageId}', ${viewableImage.imageWidth}, ${viewableImage.imageHeight})">
                          <img class="img-responsive" src="${pageContext.request.contextPath}/public/image/${viewableImage.imageId}" width="100%">
                       </a>
                    </div>
                 </div>
              </c:when>
              <c:otherwise>
                 <div class="col-xs-12">
                    <div class="thumbnail">
                       <a href="#" onclick="viewImage('${pageContext.request.contextPath}/public/image/${viewableImage.imageId}', ${viewableImage.imageWidth}, ${viewableImage.imageHeight})">
                          <img class="img-responsive" src="${pageContext.request.contextPath}/public/image/${viewableImage.imageId}" width="100%">
                       </a>
                    </div>
                 </div>
              </c:otherwise>
           </c:choose>
         </div>
      </div>

      <div class="box-footer box-comments" style="display: block;">
         <div class="row">
            <div class="col-xs-12">
            </div>
         </div>
      </div>

      <div class="box-footer" style="display: block;">
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