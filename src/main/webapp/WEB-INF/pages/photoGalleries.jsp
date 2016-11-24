<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/default-skin/default-skin.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>
  
  <tiles:putAttribute name="body">
    <h2>${pageMetadata.pageTitle}</h2>
    <hr>
    <c:choose>
      <c:when test="${galleriesList != null && !galleriesList.isDataModelEmpty()}">
         <c:forEach items="${galleriesList.getGalleryList()}" var="galleryDetail">
            <div class="panel panel-default">
               <div class="panel-body">
                  <h4>${galleryDetail.getGalleryTitle()}</h4>
                  <hr class="photogalleries-hr">
                  <div class="row">
                     <div class="col-xs-12">
                        <p><strong>Last Modified: </strong> ${galleryDetail.getUpdateDateString()}</p>
                        <strong>Description</strong>
                        <p>${galleryDetail.getGalleryDescription()}</p>
                     </div>
                  </div>
                  <div class="row">
                     <c:choose>
                        <c:when test="${galleryDetail.getSampleImages() != null && galleryDetail.getSampleImages().size() > 0}">
                           <c:forEach items="${galleryDetail.getSampleImages()}" var="imageDetail">
                              <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
                                 <div class="thumbnail gallery-image">
                                    <a href="#" onclick="viewImage('${pageContext.request.contextPath}/public/image/${imageDetail.imageId}', ${imageDetail.imageWidth}, ${imageDetail.imageHeight})">
                                       <img style="img-responsive" src="${pageContext.request.contextPath}/public/image-thumb/${imageDetail.imageId}" width="100%">
                                    </a>
                                 </div>
                              </div>
                           </c:forEach>
                           <div class="col-xs-12 text-right">
                              <a href="${pageContext.request.contextPath}/gallery/${galleryDetail.galleryId}" class="btn btn-sm btn-primary">See All Images</a>
                           </div>
                        </c:when>
                        <c:otherwise>
                           <div class="col-xs-12">
                              <div class="warning-block">
                                 No images available. Need to make this gallery invisible.
                              </div>
                           </div>
                        </c:otherwise>                        
                     </c:choose>
                   </div>
                </div>
            </div>
         </c:forEach>
         <div class="row">
            <div class="col-xs-12 text-center">
               <nav aria-label="page-nav">
                  <ul class="pager">
                     <li>
                        <c:if test="${galleriesList.isCanGoBack()}"><a href="${pageContext.request.contextPath}/galleries/${galleriesList.getPreviousPageIdx()}">Prev</a></c:if>
                     </li>
                     <li>
                        <c:if test="${galleriesList.isHasMoreElement()}"><a href="${pageContext.request.contextPath}/galleries/${galleriesList.getNextPageIdx()}">Next</a></c:if>
                     </li>
                  </ul>
               </nav>
            </div>
         </div>
      </c:when>
      <c:otherwise>
         <p>No gallery available.</p>
      </c:otherwise>
    </c:choose>
    
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