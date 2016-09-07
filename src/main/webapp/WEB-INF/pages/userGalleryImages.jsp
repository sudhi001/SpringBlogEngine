<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>Gallery: ${userGalleryImagesPageModel.getGalleryDetail().getGalleryTitle()}</h3>
    <hr>
    
    <div class="row">
       <div class="col-sm-12 col-md-6 col-lg-8">
          <span class="label label-info">You have ${userGalleryImagesPageModel.getTotalElementsCount()} image(s) in this gallery.</span>
       </div>
       <div class="col-sm-12 col-md-6 col-lg-4 text-right">
          <button class="btn btn-default" onclick="openImageUploadDlg('${userGalleryImagesPageModel.getGalleryDetail().getGalleryId()}')">Add Image</button>
       </div>
    </div>

    <div class="row">
       <div class="col-xs-12">
          <p><strong>Uploaded:</strong> ${userGalleryImagesPageModel.getGalleryDetail().getOwnerName()}</p>
          <p><strong>Modified Date:</strong> ${userGalleryImagesPageModel.getGalleryDetail().getUpdateDateString()}</p>
          <p><strong>Keywords:</strong> ${userGalleryImagesPageModel.getGalleryDetail().getGalleryKeywords()};</p>
          <p><strong>Status:</strong> <c:choose>
             <c:when test="${userGalleryImagesPageModel.getGalleryDetail().isGalleryActive()}">Active;</c:when>
             <c:otherwise>Inactive;</c:otherwise>
          </c:choose></p>
          <p><strong>Visible:</strong> <c:choose>
             <c:when test="${userGalleryImagesPageModel.getGalleryDetail().isGalleryVisible()}">Visible;</c:when>
             <c:otherwise>Invisible;</c:otherwise>
          </c:choose></p>
          
          <h4>Description:</h4>
          <p>${userGalleryImagesPageModel.getGalleryDetail().getGalleryDescription()}</p>
       </div>
    </div>
    
    <c:choose>
      <c:when test="${!userGalleryImagesPageModel.isDataModelEmpty()}">
        <div class="row">
          <c:forEach  items="${userGalleryImagesPageModel.getImagesPageList()}" var="imageItem">
          <div class="thumbnail col-xs-12 col-sm-6 col-md-4 col-lg-3">
            <a href="${pageContext.request.contextPath}/admin/image/${imageItem.getImageId()}">
              <img src="${pageContext.request.contextPath}/secure/image-thumb/${imageItem.getImageId()}" width="100%" height="100%">
            </a>
          </div>
          </c:forEach>
        </div>
      </c:when>
      <c:otherwise>
        <div class="row">
          <div class="col-md-12">
            <p>No images available.</p>
          </div>
        </div>
      </c:otherwise>
    </c:choose>
    
    <hr>
    <div class="row">
       <div class="col-xs-12 text-center">
          <nav aria-label="page-nav">
             <ul class="pager">
                <li>
                   <c:if test="${userGalleryImagesPageModel.isCanGoBack()}"><a href="${pageContext.request.contextPath}/admin/gallery/${userGalleryImagesPageModel.getGalleryDetail().getGalleryId()}/page/${userGalleryImagesPageModel.getPreviousPageIdx()}">Prev</a></c:if>
                </li>
                <li>
                   <c:if test="${userGalleryImagesPageModel.isHasMoreElement()}"><a href="${pageContext.request.contextPath}/admin/gallery/${userGalleryImagesPageModel.getGalleryDetail().getGalleryId()}/page/${userGalleryImagesPageModel.getNextPageIdx()}">Next</a></c:if>
                </li>
             </ul>
          </nav>
       </div>
    </div>
    
    <div id="uploadImageDlg" class="modal fade" tabindex="-1" role="dialog">
       <div class="modal-dialog">
          <div class="modal-content">
             <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Upload Image</h4>
             </div>
             <form id="uploadImageForm" class="form" name="uploadImageForm" method="POST"
                   enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/images/uploadImage">
                <input type="hidden" id="uploadImageGalleryId" name="uploadImageGalleryId" value="dummyValue" >
                <div class="modal-body">
                   <div class="row">
		              <div class="col-md-12">
			            <div class="form-group">
			              <label class="col-md-12 control-label">Title</label>
			              <div class="col-md-12">
			                <input id="imageTitle" name="imageTitle" class="form-control"/>
			              </div>
			            </div>
			            <div class="form-group">
			              <label class="col-md-12 control-label">Keywords</label>
			              <div class="col-md-12">
			                <input id="imageKeywords" name="imageKeywords" class="form-control"/>
			              </div>
			            </div>
			            <div class="form-group">
			              <label class="col-md-12 control-label">Image File</label>
			              <div class="col-md-12">
                             <div id="imageUploadControl" class="fileinput fileinput-new input-group" data-provides="fileinput">
                             <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                                <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="imageToUpload" id="imageToUpload"></span>
                                <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                             </div>
			              </div>
			              <div class="form-group row">
			                 <div class="col-xs-12">
                                <div class="alert alert-danger" id="uploadError" style="visible: hidden"></div>
			                 </div>
			              </div>
			            </div>
		              </div>
	               </div>
                </div>
                <div class="modal-footer">
                   <button type="button" class="btn btn-primary" onclick="validateUploadAndSubmit()">Add</button>
                   <button type="reset" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
             </form>
          </div>
       </div>
    </div>

  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/jasny/js/jasny-bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/custom/js/uploadModal.js"></script>
  </tiles:putAttribute>

</tiles:insertDefinition>