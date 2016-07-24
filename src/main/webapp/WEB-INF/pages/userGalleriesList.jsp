<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lightbox2/css/lightbox.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>Manage my Galleries</h3>
    <hr>
    
    <div class="row">
       <div class="col-md-8">
          <span class="label label-info">You have  gallery(ies)</span>
       </div>
       <div class="col-md-4 text-right">
          <button class="btn btn-default" onclick="openAddGalleryDlg()">Add Gallery</button>&nbsp;
       </div>
    </div>
    
    <c:choose>
      <c:when test="${!userImagesListPageModel.isDataModelEmpty()}">

      </c:when>
      <c:otherwise>
        <div class="row">
          <div class="col-md-12">
            <p>No galleries available.</p>
          </div>
        </div>
      </c:otherwise>
    </c:choose>

    <div id="addGalleryDlg" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Add Gallery</h4>
          </div>
          <form id="addGalleryForm" class="form" name="addGalleryForm" method="POST" action="${pageContext.request.contextPath}/admin/galleries/addGallery">
          <div class="modal-body">
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
    <script src="${pageContext.request.contextPath}/assets/lightbox2/js/lightbox.min.js"></script>
    
    <script type="text/javascript">
       /*var openImageUploadDlg = function ()
       {
          $("#uploadImageDlg").modal("show");
       };
       
       $('#uploadImageDlg').on('hidden.bs.modal', function () {
          resetAddImageDlg();
       });
       var resetAddImageDlg = function ()
       {
       };
       */

       var openAddGalleryDlg = function ()
       {
          $("#addGalleryDlg").modal("show");
       };
       
       $('#addGalleryDlg').on('hidden.bs.modal', function () {
           resetAddGalleryDlg();
       });
       
       var resetAddGalleryDlg = function ()
       {
          $("#addGalleryDlg #galleryTitle").val("");
          $("#addGalleryDlg #galleryKeywords").val("");
          $("#addGalleryDlg #galleryDesc").val("");
       }
    </script>
  </tiles:putAttribute>

</tiles:insertDefinition>