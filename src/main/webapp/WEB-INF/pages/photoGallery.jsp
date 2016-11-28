<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/default-skin/default-skin.css"> -->
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>
  
  <tiles:putAttribute name="body">
    <div class="content-box">
      <div class="box-header with-border">
        <div class="user-block">
          <div class="row">
            <div class="col-xs-1">
               <c:choose>
                  <c:when test="${galleryImagesPage.ownerIconFileId != null && galleryImagesPage.ownerIconFileId.length() > 0}">
                     <img class="img-oval" src="${pageContext.request.contextPath}/public/imgresource/${galleryImagesPage.ownerIconFileId}" alt="User Image">
                  </c:when>
                  <c:otherwise>
                     <img class="img-oval" src="${pageContext.request.contextPath}/assets/imgs/default-user.jpg" alt="User Image">
                  </c:otherwise>
               </c:choose>
            </div>
            <div class="col-xs-11">
              <span class="username">${galleryImagesPage.galleryTitle}</span>
              <div class="row">
                 <div class="col-xs-6"><span class="description">By <a href="${pageContext.request.contextPath}/userProfile/${galleryImagesPage.ownerId}">
                    <c:choose>
                       <c:when test="${galleryImagesPage.ownerUserFullName != null && galleryImagesPage.ownerUserFullName.length() > 0}">
                          ${galleryImagesPage.ownerUserFullName}
                       </c:when>
                       <c:otherwise>
                          ${galleryImagesPage.ownerName}
                       </c:otherwise>
                    </c:choose>
                 </a></span></div>
                 <div class="col-xs-6"><span class="description"><strong>Updated</strong> - ${galleryImagesPage.getUpdateDateString()}</span></div>
              </div>
              <c:if test="${galleryImagesPage.galleryKeywords != null && galleryImagesPage.galleryKeywords.length() > 0}">
                 <div class="row">
                    <div class="col-xs-12"><span class="description"><strong>Keywords:</strong> ${galleryImagesPage.galleryKeywords}</span></div>
                 </div>
              </c:if>
            </div>
          </div>
        </div>
      </div>

      <div class="box-body" style="display: block;">
         <c:if test="${galleryImagesPage.sampleImagesDisplayPage != null && !galleryImagesPage.sampleImagesDisplayPage.isDataModelEmpty()}">
         <div class="row">
            <c:forEach  items="${galleryImagesPage.sampleImagesDisplayPage.getListItems()}" var="imageDetail">
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center">
               <div class="thumbnail gallery-image">
                  <a href="${pageContext.request.contextPath}/images/imageDetail/${imageDetail.imageId}">
                     <img style="img-responsive" src="${pageContext.request.contextPath}/public/image-thumb/${imageDetail.imageId}" width="100%">
                  </a>
               </div>
            </div>
            </c:forEach>
         </div>
         </c:if>
      </div>

      <div class="box-footer box-comments" style="display: block;">
         <c:if test="${galleryImagesPage.sampleImagesDisplayPage != null}">
            <div class="row">
               <div class="col-xs-12">
                  <span class="pull-right text-muted">
                  Total ${galleryImagesPage.sampleImagesDisplayPage.totalElementsCount} images, Displaying Page ${galleryImagesPage.sampleImagesDisplayPage.pageIdx}
                  </span>
               </div>
            </div>
         </c:if>
      </div>

      <div class="box-footer" style="display: block;">
         <c:if test="${galleryImagesPage.sampleImagesDisplayPage != null && !galleryImagesPage.sampleImagesDisplayPage.isDataModelEmpty() && (galleryImagesPage.sampleImagesDisplayPage.isCanGoBack() || galleryImagesPage.sampleImagesDisplayPage.isHasMoreElement())}">
         <div class="row">
            <div class="col-xs-12 text-center">
               <nav aria-label="page-nav">
                  <ul class="pager">
                     <li>
                        <c:if test="${galleryImagesPage.sampleImagesDisplayPage.isCanGoBack()}"><a href="${pageContext.request.contextPath}/gallery/${galleryImagesPage.galleryId}/page/${galleryImagesPage.sampleImagesDisplayPage.getPreviousPageIdx()}">Prev</a></c:if>
                     </li>
                     <li>
                        <c:if test="${galleryImagesPage.sampleImagesDisplayPage.isHasMoreElement()}"><a href="${pageContext.request.contextPath}/gallery/${galleryImagesPage.galleryId}/page/${galleryImagesPage.sampleImagesDisplayPage.getNextPageIdx()}">Next</a></c:if>
                     </li>
                  </ul>
               </nav>
            </div>
         </div>
         </c:if>
      </div>
    </div>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <!-- <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe-ui-default.min.js"></script> -->
  </tiles:putAttribute>
</tiles:insertDefinition>