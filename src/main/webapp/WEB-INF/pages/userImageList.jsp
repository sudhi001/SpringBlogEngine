<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lightbox/css/magnific-popup.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>All My Images</h3>
    <hr>
    
    <div class="row">
       <div class="col-md-8">
          <span class="label label-info">You have ${userImagesListPageModel.getTotalElementsCount()} image(s)</span>
       </div>
       <div class="col-md-4 text-right">
          <button class="btn btn-default" onclick="openImageUploadDlg()">Add Image</button>
       </div>
    </div>
    
    <c:choose>
      <c:when test="${!userImagesListPageModel.isDataModelEmpty()}">
        <div class="row">
          <c:forEach  items="${userImagesListPageModel.getListItems()}" var="imageItem">
          <div class="col-md-3">
            <img src="${pageContext.request.contextPath}/secure/image-thumb/${imageItem.getImageId()}" width="100%" height="100%">
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
    
    <c:if test="${!userImagesListPageModel.isDataModelEmpty()}">

    </c:if>
    
    <div id="uploadImageDlg" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <form id="addNewImageForm" name="addNewImageForm" method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/images/addImage">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Add a New Image</h4>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label for="imageTitle">Subject</label>
              <input type="text" class="form-control" id="imageTitle" name="imageTitle" placeholder="Title of the Image">
            </div>
            <div class="form-group">
              <label for="imageKeywords">Keywords</label>
              <input type="text" class="form-control" id="imageKeywords" name="imageKeywords" placeholder="Keywords of this image">
            </div>
            <div class="form-group">
              <label for="imageToUpload">Image File to Upload</label>
              <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="imageToUpload" id="imageToUpload"></span>
                <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
              </div>
            </div>
            <div class="form-group">
              <label for="imageToUpload">Snapshot File Upload</label>
              <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="snapshotToUpload" id="snapshotToUpload"></span>
                <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">Save changes</button>
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
    <script src="${pageContext.request.contextPath}/assets/lightbox/js/jquery.magnific-popup.min.js"></script>
    
    <script type="text/javascript">
       var openImageUploadDlg = function ()
       {
          $("#uploadImageDlg").modal("show");
       };
    </script>
  </tiles:putAttribute>

</tiles:insertDefinition>