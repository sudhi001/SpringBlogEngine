<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lightbox2/css/lightbox.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>All My Site Resources</h3>
    <hr>

    <div class="post-list">
      <div class="row">
        <div class="col-md-3">
          <c:choose>
            <c:when test="${articleListPageModel.totalElementsCount == 0}">
              <h4><span class="label label-info">${resourceListPageModel.totalElementsCount} resources available</span></h4>
            </c:when>
            <c:otherwise>
              <h4><span class="label label-info">${resourceListPageModel.totalElementsCount} resources and counting ...</span></h4>
            </c:otherwise>
          </c:choose>        
        </div>
        <div class="col-md-9 text-right">
          <p>
            <a href="#" class="btn btn-default" onclick="handleClickTextResources()">Add Text Resource</a>
            <a href="#" class="btn btn-default" onclick="handleClickFileResources()">Add New File</a>
          </p>
        </div>
      </div>

      <c:choose>
      <c:when test="${!resourceListPageModel.isDataModelEmpty()}">

      <table class="table table-hover">
        <tr>
          <th class="col-md-2">Type</th>
          <th class="col-md-5">Subject</th>
          <th class="col-md-3">Last Modified</th>
          <th class="col-md-2">Actions</th>
        </tr>
        <c:forEach items="${resourceListPageModel.getListOfResources()}" var="resourceInfo">
          <tr>
            <td>
              <p style="padding-top: 5px;">
                 <span class="glyphicon glyphicon-file"></span>
                 <c:choose>
                   <c:when test="${resourceInfo.resourceType.equals('file')}">
                   File (${resourceInfo.resourceSubType})
                   </c:when>
                   <c:when test="${resourceInfo.resourceType.equals('text')}">
                   Text (${resourceInfo.resourceSubType})
                   </c:when>
                 </c:choose>
              </p>
            </td>
            <td>
              <c:choose>
                <c:when test="${resourceInfo.resourceType.equals('file') && resourceInfo.resourceSubType.equals('image')}">
                  <a data-lightbox="imglighbox-${resourceInfo.resourceId}" href="${pageContext.request.contextPath}/secure/imgresource/${resourceInfo.resourceId}">
                    ${resourceInfo.resourceName}
                  </a>
                </c:when>
                <c:otherwise>
                  ${resourceInfo.resourceName}
                </c:otherwise>
              </c:choose>
              
            </td>  
            <td>
               <p style="padding-top: 5px;">
                  <span class="glyphicon glyphicon-calendar"></span> ${resourceInfo.getUpdateDateAsString()}
               </p>
            </td>
            <td>
              <c:choose>
                <c:when test="${resourceInfo.resourceType.equals('file')}">
                  <button class="btn btn-danger" onclick="handleClickDeleteResource('${resourceInfo.resourceId}')">Delete</button>
                </c:when>
                <c:when test="${resourceInfo.resourceType.equals('text')}">
                  <button class="btn btn-primary" onclick="handleClickEditTextResources('${resourceInfo.resourceId}')")>Update</button> 
                  <button class="btn btn-danger" onclick="handleClickDeleteResource('${resourceInfo.resourceId}')">Delete</button>
                </c:when>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
      </table>

      <div class="row">
        <div class="md-col-12 text-right">
          <nav>
            <ul class="pagination">
              <c:if test="${resourceListPageModel.canGoBack}">
              <li>
                <a href="${pageContext.request.contextPath}/admin/resources/allMyResources?pageIdx=${resourceListPageModel.previousPageIdx}" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              </c:if>
              <c:if test="${resourceListPageModel.hasMoreElement}">
              <li>
                <a href="${pageContext.request.contextPath}/admin/resources/allMyResources?pageIdx=${resourceListPageModel.nextPageIdx}" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
              </c:if>
            </ul>
          </nav>
        </div>
      </div>

      </c:when>
      <c:otherwise>
        <p>You don't have any resources</p>
      </c:otherwise>
    </c:choose>
    </div>
     
    <div id="addTextResDlg" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title"><span id="resDlgTitle">Add New Resource</span></h4>
          </div>
          <form id="addNewTextResDlgForm" action="${pageContext.request.contextPath}/admin/resources/addUpdateTextResource" method="post">
          <input type="hidden" id="resourceId" name="resourceId" value=""/>
          <div class="modal-body">
            <div class="form-group">
              <label for="resourceName">Resource Name</label>
              <input type="text" class="form-control" name="resourceName" id="resourceName" placeholder="Enter resource name...">
            </div>
            <div class="form-group">
              <label for="resourceSubType">Resource Type</label>
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
    <script src="${pageContext.request.contextPath}/assets/lightbox2/js/lightbox.min.js"></script>
    
    <script type="text/javascript">
       var handleClickFileResources = function() {
    	   clearFileResourceDlg();  
    	  $("#addFileResDlg").modal("show");
       };
       
       var handleClickTextResources = function() {
    	   clearTextResourceDlg();
           $("#addTextResDlg").modal("show");
       };

       var handleClickEditTextResources = function(resourceId) {
           clearTextResourceDlg();
           
           $.ajax({
               type: "GET",
               url: "${pageContext.request.contextPath}/admin/resources/getTextResource?resourceId=" + resourceId,
               xhrFields: {
                  withCredentials: true
               },
               success: function(data) {
                  $("#addTextResDlg #resDlgTitle").html("Edit Existing Resource");
                  $("#addNewTextResDlgForm #resourceId").val(data.resourceId);
                  $("#addNewTextResDlgForm #resourceName").val(data.resourceName);
                  $("#addNewTextResDlgForm #resourceSubType").val(data.subType);
                  $("#addNewTextResDlgForm #resourceValue").val(data.resourceValue);
                  $("#addTextResDlg").modal("show");
               },
               error: function() {
            	  $("#addTextResDlg").modal("hide");
               }
            });
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
       
       var handleClickDeleteResource = function (resourceId)
       {
           $.ajax({
               type: "DELETE",
               url: "${pageContext.request.contextPath}/admin/resources/deleteResource?resourceId=" + resourceId,
               xhrFields: {
                  withCredentials: true
               },
               success: function(data) {
                  location.reload(true);
               },
               error: function() {
                  location.reload(true);
               }
            });
       };
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>
