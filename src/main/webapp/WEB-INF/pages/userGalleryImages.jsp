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
       <div class="col-xs-12">
          <span class="label label-info">You have ${userGalleryImagesPageModel.getTotalElementsCount()} image(s) in this gallery.</span>
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
       <div class="col-xs-12">
          <button class="btn btn-default" onclick="openImageUploadDlg('${userGalleryImagesPageModel.getGalleryDetail().getGalleryId()}')">Add Image</button>&nbsp;
          <button class="btn btn-default" onclick="openImagesUploadDlg('${userGalleryImagesPageModel.getGalleryDetail().getGalleryId()}')">Add Images</button>&nbsp;
          <button class="btn btn-default" id="editDetailsBtn" onclick="editGalleryDetails()">Edit Detail</button>&nbsp;
          <button class="btn btn-default" id="cancelEditDetailsBtn" onclick="cancelEditingGalleryDetails('${userGalleryImagesPageModel.getGalleryDetail().getGalleryTitle()}', '${userGalleryImagesPageModel.getGalleryDetail().getGalleryKeywords()}', '${userGalleryImagesPageModel.getGalleryDetail().getGalleryDescription()}', ${userGalleryImagesPageModel.getGalleryDetail().isGalleryActive()}, ${userGalleryImagesPageModel.getGalleryDetail().isGalleryVisible()})" style="display: none;">Cancel Editing</button>
       </div>
    </div>
    <div class="panel panel-default margin-updown" id="editGalleryDetailsDlg" style="display: none;">
       <div class="panel-body">
          <form class="form" id="editGalleryDetailsForm" action="${pageContext.request.contextPath}/admin/gallery/editDetails" method="POST"
                onsubmit="return validateEditingGalleryDetailsAndSubmit()"
                onreset="cancelEditingGalleryDetails('${userGalleryImagesPageModel.getGalleryDetail().getGalleryTitle()}', '${userGalleryImagesPageModel.getGalleryDetail().getGalleryKeywords()}', '${userGalleryImagesPageModel.getGalleryDetail().getGalleryDescription()}', ${userGalleryImagesPageModel.getGalleryDetail().isGalleryActive()}, ${userGalleryImagesPageModel.getGalleryDetail().isGalleryVisible()})">
             <legend>Edit Gallery Detail</legend>
             <input type="hidden" id="galleryId" name="galleryId" value="${userGalleryImagesPageModel.getGalleryDetail().getGalleryId()}" >
             <div class="form-group">
                <label class="col-xs-12 control-label">Title</label>
	            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
	               <input id="galleryTitle" name="galleryTitle" class="form-control" value="${userGalleryImagesPageModel.getGalleryDetail().getGalleryTitle()}" />
	            </div>
             </div>
             <div class="form-group">
                <label class="col-xs-12 control-label">Keywords</label>
	            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
	               <input id="galleryKeywords" name="galleryKeywords" class="form-control" value="${userGalleryImagesPageModel.getGalleryDetail().getGalleryKeywords()}" />
	            </div>
             </div>
             <div class="form-group">
                <label class="col-xs-12 control-label">Description</label>
	            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
<textarea id="galleryDesc" name="galleryDesc" class="form-control">
${userGalleryImagesPageModel.getGalleryDetail().getGalleryDescription()}
</textarea>
	            </div>
             </div>
             <div class="checkbox col-xs-6">
                <label>
                   <c:choose>
                      <c:when test="${userGalleryImagesPageModel.getGalleryDetail().isGalleryActive()}">
                         <input type="checkbox" id="galleryActive" name="galleryActive" checked/> Active
                      </c:when>
                      <c:otherwise>
                         <input type="checkbox" id="galleryActive" name="galleryActive" /> Active
                      </c:otherwise>
                   </c:choose>
                </label>
             </div>
             <div class="checkbox col-xs-6">
                <label>
                   <c:choose>
                      <c:when test="${userGalleryImagesPageModel.getGalleryDetail().isGalleryVisible()}">
                         <input type="checkbox" id="galleryVisible" name="galleryVisible" checked/> Visible
                      </c:when>
                      <c:otherwise>
                         <input type="checkbox" id="galleryVisible" name="galleryVisible" /> Visible
                      </c:otherwise>
                   </c:choose>
                </label>
             </div>
             <div class="row margin-updown" id="editGalleryDetailsError" style="display: none;">
                <div class="col-xs-12">
                   <div class="warning-block" id="editGalleryDetailsErrorMsg">
                      test test
                   </div>
                </div>
             </div>
             <div class="row">
                <div class="col-xs-12 text-center">
                   <button type="submit" class="btn btn-default">Save</button>
                   <button type="reset" class="btn btn-default">Reset</button>
                </div>
             </div>
          </form>
       </div>
    </div>
    <hr>
    
    <c:choose>
      <c:when test="${!userGalleryImagesPageModel.isDataModelEmpty()}">
        <div class="row">
          <c:forEach  items="${userGalleryImagesPageModel.getImagesPageList()}" var="imageItem">
          <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 text-center">
            <div class="thumbnail gallery-image">
               <a href="${pageContext.request.contextPath}/admin/image/${imageItem.getImageId()}">
                  <img style="img-responsive" src="${pageContext.request.contextPath}/secure/image-thumb/${imageItem.getImageId()}" width="100%">
               </a>
            </div>
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
                          <div class="checkbox col-md-12">
                             <label>
                                <input id="imageNotSafeForWork" name="imageNotSafeForWork" type="checkbox"> Image NSFW
                             </label>
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
    
    <div id="uploadImagesDlg" class="modal fade" tabindex="-1" role="dialog">
       <div class="modal-dialog">
          <div class="modal-content">
             <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Upload Image</h4>
             </div>
             <form id="uploadImagesForm" class="form" name="uploadImagesForm" method="POST"
                   enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/images/uploadImages"
                   onsubmit="return validateUploadsAndSubmit();" onreset="resetAddImagesDlg()">
                <input type="hidden" id="uploadImagesGalleryId" name="uploadImagesGalleryId" value="dummyValue" >
                <div class="modal-body">
                   <div class="row">
		              <div class="col-md-12">
			            <div class="form-group">
                          <label class="col-md-12 control-label">Image File</label>
			              <div class="col-md-12">
                             <div id="image1UploadControl" class="fileinput fileinput-new input-group" data-provides="fileinput">
                             <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                                <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="imageToUpload" id="image1ToUpload"></span>
                                <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                             </div>
			              </div>
                          <label class="col-md-12 control-label">Image File</label>
			              <div class="col-md-12">
                             <div id="image2UploadControl" class="fileinput fileinput-new input-group" data-provides="fileinput">
                             <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                                <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="imageToUpload" id="image2ToUpload"></span>
                                <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                             </div>
			              </div>
                          <label class="col-md-12 control-label">Image File</label>
			              <div class="col-md-12">
                             <div id="image3UploadControl" class="fileinput fileinput-new input-group" data-provides="fileinput">
                             <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                                <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="imageToUpload" id="image3ToUpload"></span>
                                <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                             </div>
			              </div>
                          <label class="col-md-12 control-label">Image File</label>
			              <div class="col-md-12">
                             <div id="image4UploadControl" class="fileinput fileinput-new input-group" data-provides="fileinput">
                             <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                                <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="imageToUpload" id="image4ToUpload"></span>
                                <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                             </div>
			              </div>
                          <label class="col-md-12 control-label">Image File</label>
			              <div class="col-md-12">
                             <div id="image5UploadControl" class="fileinput fileinput-new input-group" data-provides="fileinput">
                             <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                                <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="imageToUpload" id="image5ToUpload"></span>
                                <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                             </div>
			              </div>
                          <div class="row margin-updown" id="uploadImagesError" style="display: none;">
                             <div class="col-xs-12">
                                <div class="warning-block" id="uploadImagesErrorMsg">
                                   test test
                                </div>
                             </div>
                          </div>
			            </div>
		              </div>
	               </div>
                </div>
                <div class="modal-footer">
                   <button type="submit" class="btn btn-primary">Add</button>
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
    <script src="${pageContext.request.contextPath}/assets/custom/js/editGalleryDetails.js"></script>
  </tiles:putAttribute>

</tiles:insertDefinition>