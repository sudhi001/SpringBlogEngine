<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>Manage my Galleries</h3>
    <hr>
    
    <div class="row">
       <div class="col-md-8">
          <span class="label label-info">You have ${userGalleriesListPageModel.getTotalElementsCount()} gallery(ies)</span>
       </div>
       <div class="col-md-4 text-right">
          <button class="btn btn-default" onclick="openAddGalleryDlg()">Add Gallery</button>&nbsp;
       </div>
    </div>
    
    <c:choose>
      <c:when test="${!userGalleriesListPageModel.isDataModelEmpty()}">
      <table class="table table-hover">
        <tr>
          <th class="col-md-1"></th>
          <th class="col-md-6">Gallery Name</th>
          <th class="col-md-2">Last Modified</th>
          <th class="col-md-3">Actions</th>
        </tr>
        <c:forEach items="${userGalleriesListPageModel.getListItems()}" var="galleryDetail">
          <tr>
            <td>
               <span class="glyphicon glyphicon-folder-open"></span>
            </td>
            <td>
               <p>
                  <a href="${pageContext.request.contextPath}/admin/gallery/${galleryDetail.getGalleryId()}/page/0">${galleryDetail.getGalleryTitle()}</a>
               </p>
            </td>
            <td>
               <p>
                  ${galleryDetail.getCreateDateString()}
               </p>
            </td>
            <td>
              <div class="row">
                 <button class="btn btn-sm btn-default" onclick="openImageUploadDlg('${galleryDetail.getGalleryId()}')">Add Image</button>
                 &nbsp;
                 <c:choose>
                    <c:when test="${galleryDetail.isGalleryVisible()}">
                 	   <button class="btn btn-sm btn-danger" onclick="showGallery('${pageContext.request.contextPath}',  '${galleryDetail.getGalleryId()}', false)"><span class="glyphicon glyphicon-eye-close"></span></button>
                 	</c:when>
                 	<c:otherwise>
                 	   <button class="btn btn-sm btn-success" onclick="showGallery('${pageContext.request.contextPath}', '${galleryDetail.getGalleryId()}', true)"><span class="glyphicon glyphicon-eye-open"></span></button>
                 	</c:otherwise>
                 </c:choose>
                 &nbsp;
                 <c:choose>
                    <c:when test="${galleryDetail.isGalleryActive()}">
                 	   <button class="btn btn-sm btn-danger" onclick="setGalleryActive('${pageContext.request.contextPath}', '${galleryDetail.getGalleryId()}', false)"><span class="glyphicon glyphicon-remove-circle"></span></button>
                 	</c:when>
                 	<c:otherwise>
                 	   <button class="btn btn-sm btn-success" onclick="setGalleryActive('${pageContext.request.contextPath}', '${galleryDetail.getGalleryId()}', true)"><span class="glyphicon glyphicon-ok-circle"></span></button>
                 	</c:otherwise>
                 </c:choose>
              </div>
            </td>
          </tr>
        </c:forEach>
      </table>

      </c:when>
      <c:otherwise>
        <div class="row">
          <div class="col-md-12">
            <p>No galleries available.</p>
          </div>
        </div>
      </c:otherwise>
    </c:choose>
    
    <div class="row">
      <div class="md-col-12 text-right">
        <nav>
          <ul class="pagination">
            <c:if test="${userGalleriesListPageModel.canGoBack}">
            <li>
              <a href="${pageContext.request.contextPath}/admin/galleries/allMyGalleries/${userGalleriesListPageModel.previousPageIdx}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
              </a>
            </li>
            </c:if>
            <c:if test="${userGalleriesListPageModel.hasMoreElement}">
            <li>
              <a href="${pageContext.request.contextPath}/admin/galleries/allMyGalleries/${userGalleriesListPageModel.nextPageIdx}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
              </a>
            </li>
            </c:if>
          </ul>
        </nav>
      </div>
    </div>
      
    <div id="addGalleryDlg" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Add Gallery</h4>
          </div>
          <form id="addGalleryForm" class="form" name="addGalleryForm" method="POST" action="${pageContext.request.contextPath}/admin/galleries/addGallery">
          <div class="modal-body">
	            <div class="row">
		            <div class="col-md-12">
			            <div class="form-group">
			              <label class="col-md-12 control-label">Title</label>
			              <div class="col-md-12">
			                <input id="galleryTitle" name="galleryTitle" class="form-control"/>
			              </div>
			            </div>
			            <div class="form-group">
			              <label class="col-md-12 control-label">Keywords</label>
			              <div class="col-md-12">
			                <input id="galleryKeywords" name="galleryKeywords" class="form-control"/>
			              </div>
			            </div>
			            <div class="form-group">
			              <label class="col-md-12 control-label">Description</label>
			              <div class="col-md-12">
			                <textarea class="form-control" id="galleryDesc" name="galleryDesc">
			                </textarea>
			              </div>
			            </div>
			            <div class="form-group row">
			              <div class="col-xs-12">
                             <div class="alert alert-danger" id="addGalleryError" style="visible: hidden"></div>
			              </div>
			            </div>
		            </div>
	            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" onclick="validateAddGalleryAndSubmit()">Add</button>
            <button type="reset" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
          </form>
        </div>
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
    
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/jasny/js/jasny-bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/custom/js/uploadModal.js"></script>
    <script src="${pageContext.request.contextPath}/assets/custom/js/addGalleryModal.js"></script>
  </tiles:putAttribute>

</tiles:insertDefinition>