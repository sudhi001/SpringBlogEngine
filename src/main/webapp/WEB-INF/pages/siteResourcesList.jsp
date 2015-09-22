<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>All My Site Resources</h3>
    <hr>

    <div class="post-list">
      <div class="row">
        <div class="col-md-3">
        </div>
        <div class="col-md-9 text-right">
          <p>
            <a href="#" class="btn btn-default" onclick="handleClickTextResources()">Add Text Resource</a>
            <a href="#" class="btn btn-default" onclick="handleClickFileResources()">Add New File</a>
          </p>
        </div>
      </div>
    </div>
     
    <div id="addTextResDlg" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Add New Resource</h4>
          </div>
          <form id="addNewTextResDlgForm" action="${pageContext.request.contextPath}/admin/resources/addNewTextResource" method="post">
          <div class="modal-body">
            <div class="form-group">
              <label for="resourceName">Resource Name</label>
              <input type="text" class="form-control" name="resourceName" id="resourceName" placeholder="Enter resource name...">
            </div>
            <div class="form-group">
              <label for="resourceName">Resource Type</label>
              <div class="row">
                <div class="col-md-4">
                  <select class="form-control" name="resourceSubType" id="resourceSubType">
                    <option value="">-- Select type --</option>
                    <option value="html">Html</option>
                    <option value="quote">Quote</option>
                    <option value="snippet">Code Snippet</option>
                    <option value="text">Text</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label for="resourceValue">Text based Resource</label>
              <textarea class="form-control" name="resourceValue" id="resourceValue" rows="8"></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save changes</button>
          </div>
          </form>
        </div>
      </div>
    </div>
    
    <div id="addFileResDlg" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Add New Resource</h4>
          </div>
          <form id="addNewFileResDlgForm" action="${pageContext.request.contextPath}/admin/resources/addNewFileResource" enctype="multipart/form-data" method="post">
          <div class="modal-body">
            <div class="form-group">
              <label for="resourceName">Resource Name</label>
              <input type="text" class="form-control" name="resourceName" id="resourceName" placeholder="Enter resource name...">
            </div>
            <div class="form-group">
              <label for="resourceName">Resource Type</label>
              <div class="row">
                <div class="col-md-4">
                  <select class="form-control" name="resourceSubType" id="resourceSubType">
                    <option value="">-- Select type --</option>
                    <option value="image">Image</option>
                    <option value="mp3">MP3 Sound</option>
                    <option value="pdf">PDF Document</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label for="resourceName">Resource File</label>
              <!-- <input type="text" class="form-control" name="resourceName" id="resourceName" placeholder="Enter resource name..."> -->
              <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                  <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="fileToUpload" id="fileToUpload"></span>
                  <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save changes</button>
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
    <script type="text/javascript">
       var handleClickFileResources = function() {
    	   clearFileResourceDlg();  
    	  $("#addFileResDlg").modal("show");
       };
       
       var handleClickTextResources = function() {
    	   clearTextResourceDlg();
           $("#addTextResDlg").modal("show");
       };
       
       var clearTextResourceDlg = function()
       {
           $("#addTextResDlg #resourceName").val("");
           $("#addTextResDlg #resourceSubType").prop('selectedIndex',0);
           $("#addTextResDlg #resourceValue").val("");
       };
       
       var clearFileResourceDlg = function()
       {
           $("#addFileResDlg #resourceName").val("");
           $("#addFileResDlg #resourceSubType").prop('selectedIndex',0);
           $("#addFileResDlg .fileinput").fileinput("clear");
       };
       
       
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>
