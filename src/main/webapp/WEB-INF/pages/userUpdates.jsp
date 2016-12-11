<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/default-skin/default-skin.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>
  
  <tiles:putAttribute name="body">
    <h2 class="text-right">${pageMetadata.pageTitle}</h2>
    <hr>
    
    <c:choose>
       <c:when test="userUpdates != null && !userUpdates.isDataModelEmptry()">
          <c:forEach var="userUpdate" items="${userUpdates.getUserUpdates()}">
             <div class="content-box">
               <div class="box-header with-border">
                 <div class="user-block">
                   <div class="row">
                     <div class="col-xs-1">
                        <c:choose>
                           <c:when test="${userUpdate.statusOwnerUserIconId != null && userUpdate.statusOwnerUserIconId.length() > 0}">
                              <img class="img-oval" src="${pageContext.request.contextPath}/public/imgresource/${userUpdate.statusOwnerUserIconId}" alt="User Image">
                           </c:when>
                           <c:otherwise>
                              <img class="img-oval" src="${pageContext.request.contextPath}/assets/imgs/default-user.jpg" alt="User Image">
                           </c:otherwise>
                        </c:choose>
                     </div>
                     <div class="col-xs-11">
                       <span class="username">${userUpdate.updateTitle}</span>
                       <div class="row">
                          <div class="col-xs-6"><span class="description">By <a href="${pageContext.request.contextPath}/userProfile/${userUpdate.statusOwnerId}">
                             <c:choose>
                                <c:when test="${userUpdate.statusOwnerUserFullName != null && userUpdate.statusOwnerUserFullName.length() > 0}">
                                   ${userUpdate.statusOwnerUserFullName}
                                </c:when>
                                <c:otherwise>
                                   ${userUpdate.statusOwnerUserName}
                                </c:otherwise>
                             </c:choose>
                          </a></span></div>
                          <div class="col-xs-6"><span class="description"><strong>Updated</strong> - ${userUpdate.getStatusUpdateDateString()}</span></div>
                       </div>
                     </div>
                   </div>
                 </div>
               </div>

               <div class="box-body" style="display: block;">
                 <div class="row">
                   <div class="col-xs-12">
<c:out value="${userUpdate.statusContent}" escapeXml = "false"/>
                   </div>
                 </div>
               </div>

               <div class="box-footer box-comments" style="display: block; margin-bottom: 15px;">
                  <div class="row">
                     <div class="col-xs-6 col-sm-9 col-md-9 text-right">
                        <!-- <button class="btn btn-danger btn-xs" onclick="addArticleVisitorLike('${pageContext.request.contextPath}/likes/article/${articleModel.articleId}', false)"><span class="glyphicon glyphicon-thumbs-down"></span></button>
                        <button class="btn btn-primary btn-xs" onclick="addArticleVisitorLike('${pageContext.request.contextPath}/likes/article/${articleModel.articleId}', true)"><span class="glyphicon glyphicon-thumbs-up"></span></button> -->
                     </div>
                     <div class="col-xs-6 col-sm-3 col-md-3 text-center">
                        <div class="pull-right text-muted"><span id="likeSpan">${userUpdate.visitorLikesCount} like(s),</span> <span id="dislikeSpan">${userUpdate.visitorDislikesCount} dislike(s),</span> 
                        <span id="commentsCount">${userUpdate.commentsCount} comments</span></div>
                     </div>
                  </div>
               </div>
               
               <div class="box-footer box-comments" style="display: block;">
               </div>

               <div class="box-footer" style="display: block;">
               </div>
             </div>
          </c:forEach>
       </c:when>
       <c:otherwise>
          <p>There is no updates about this site.</p>
       </c:otherwise>
    </c:choose>
    
    
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe-ui-default.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>