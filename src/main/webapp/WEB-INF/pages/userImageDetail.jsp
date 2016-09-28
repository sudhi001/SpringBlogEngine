<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
     <div class="content-box">
       <div class="box-header with-border">
         <div class="user-block">
           <span class="username">${imageDetail.getImageTitle()}</span>
           <span class="description"></span>
         </div>
       </div>
       <div class="box-body" style="display: block;">
         <p><strong>Keywords:</strong> ${imageDetail.getImageKeywords()}; <strong>Status:</strong>
            <c:choose>
               <c:when test="${imageDetail.isImageActive()}">
                  Active;
               </c:when>
               <c:otherwise>
                  Inactive;
               </c:otherwise>
            </c:choose>
         <strong>NSFW:</strong>
            <c:choose>
               <c:when test="${imageDetail.isImageNotSafeForWork()}">
                  Yes;
               </c:when>
               <c:otherwise>
                  No;
               </c:otherwise>
            </c:choose>
         </p>

         <c:choose>
            <c:when test="${imageDetail.getImageWidth() > imageDetail.getImageHeight()}">
              <div class="row">
                 <div class="col-xs-12 thumbnail">
                    <img class="img-responsive pad" style="text-align: center;" width="100%" src="${pageContext.request.contextPath}/secure/image-full/${imageDetail.getImageId()}" alt="Photo">
                 </div>
              </div>
            </c:when>
            <c:otherwise>
              <div class="row">
                 <div class="col-xs-8 col-xs-offset-2 thumbnail">
                    <img class="img-responsive pad" style="text-align: center;" width="100%" src="${pageContext.request.contextPath}/secure/image-full/${imageDetail.getImageId()}" alt="Photo">
                 </div>
              </div>
            </c:otherwise>
         </c:choose>       
         
         <div class="row" style="margin-top: 10px; margin-bottom: 10px;">
            <div class="col-xs-12">
               <button class="btn btn-default" id="editImageDetailsBtn" onclick="editImageDetails()">Edit Detail</button>&nbsp;
               <button class="btn btn-default" id="cancelEditDetailsBtn" style="display: none;" onclick="cancelEditDetails('${imageDetail.getImageTitle()}', '${imageDetail.getImageKeywords()}')">Cancel Editing</button>&nbsp;
               <button class="btn btn-default" id="ceateBlogForImageBtn" onclick="showPostBlogForImage()">Write Blog Post</button>
            </div>
         </div>
         <div class="panel panel-default" id="editImageDetailsPanel" style="display: none;">
            <div class="panel-body">
               <form id="editImageDetailsDlg" class="form" action="${pageContext.request.contextPath}/admin/image/editDetails"
                     method="POST" onsubmit="return validateEditImageDetails()"
                     onreset="resetEditImageDetailsDlg('${imageDetail.getImageTitle()}', '${imageDetail.getImageKeywords()}')">
                  <legend>Edit Image Detail</legend>
                  <input type="hidden" id="imageId" name="imageId" value="${imageDetail.getImageId()}" >
                  <div class="form-group">
                     <label class="col-xs-12 control-label">Title</label>
			         <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
			            <input id="imageTitle" name="imageTitle" class="form-control" value="${imageDetail.getImageTitle()}" />
			         </div>
                  </div>
                  <div class="form-group">
                     <label class="col-xs-12 control-label">Keywords</label>
			         <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
			            <input id="imageKeywords" name="imageKeywords" class="form-control" value="${imageDetail.getImageKeywords()}" />
			         </div>
                  </div>
                  <div class="checkbox col-xs-12">
                     <label>
                        <c:choose>
                           <c:when test="${imageDetail.isImageActive()}">
                              <input type="checkbox" id="imageActive" name="imageActive" checked/> Active
                           </c:when>
                           <c:otherwise>
                              <input type="checkbox" id="imageActive" name="imageActive" /> Active
                           </c:otherwise>
                        </c:choose>
                     </label>
                  </div>
                  <div class="checkbox col-xs-12">
                     <label>
                        <c:choose>
                           <c:when test="${imageDetail.isImageNotSafeForWork()}">
                              <input type="checkbox" id="imageNotSafeForWork" name="imageNotSafeForWork" checked/> Image NSFW
                           </c:when>
                           <c:otherwise>
                              <input type="checkbox" id="imageNotSafeForWork" name="imageNotSafeForWork" /> Image NSFW
                           </c:otherwise>
                        </c:choose>
                     </label>
                  </div>
                  <div class="row margin-updown" id="editImageDetailsError" style="display: none;">
                     <div class="col-xs-12">
                        <div class="warning-block" id="editImageDetailsErrorMsg">
                           test test
                        </div>
                     </div>
                  </div>
                  <div class="form-group">
			         <div class="col-xs-12 text-center">
			            <button type="submit" class="btn btn-default">Save</button>&nbsp;
			            <button type="reset" class="btn btn-default">Clear</button>&nbsp;			            
			         </div>
                  </div>
               </form>
            </div>
         </div>
         <div class="panel panel-default" id="imageBlogPostPanel" style="display: none;">
            <div class="panel-body">
               <form id="imageBlogPostForm" class="form" action="${pageContext.request.contextPath}/admin/image/postForBlog"
                     method="POST" onsubmit="return validateBlogImagePost()"
                     onreset="resetForPostBlog('${imageDetail.getImageId()}')">
                  <legend>Create Blog Post</legend>
                  <input type="hidden" id="postImageId" name="postImageId" value="${imageDetail.getImageId()}" >
                  <div class="form-group">
                     <label class="col-xs-12 control-label">Post Title</label>
			         <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
			            <input id="blogTitle" name="blogTitle" class="form-control" value="" />
			         </div>
                  </div>
                  <div class="form-group">
                     <label class="col-xs-12 control-label">Post Keywords</label>
			         <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
			            <input id="blogKeywords" name="blogKeywords" class="form-control" value="" />
			         </div>
                  </div>
                  <div class="form-group">
                     <label class="col-xs-12 control-label">Post Content</label>
			         <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
<textarea id="blogContent" name="blogContent" class="form-control">
</textarea>
			         </div>
                  </div>
                  <div class="row margin-updown" id="imageBlogPostError" style="display: none;">
                     <div class="col-xs-12">
                        <div class="warning-block" id="imageBlogPostErrorMsg">
                           test test
                        </div>
                     </div>
                  </div>
                  <div class="form-group">
			         <div class="col-xs-12 text-center">
			            <button type="submit" class="btn btn-default">Post</button>&nbsp;
			            <button type="reset" class="btn btn-default">Clear</button>&nbsp;			            
			         </div>
                  </div>
               </form>
            </div>
         </div>
       </div>
     </div>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/custom/js/editImageDetails.js"></script>
    <script src="${pageContext.request.contextPath}/assets/custom/js/imageBlogPost.js"></script>
    <script>
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>