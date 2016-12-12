<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>${pageMetadata.pageTitle}</h3>
    <hr>
    
    <div class="post-list">
      <div class="row">
        <div class="col-md-3">
          <c:choose>
            <c:when test="${adminUserUpdates.totalElementsCount == 0}">
              <h4><span class="label label-info">${adminUserUpdates.totalElementsCount} updates available</span></h4>
            </c:when>
            <c:otherwise>
              <h4><span class="label label-info">${adminUserUpdates.totalElementsCount} updates and counting ...</span></h4>
            </c:otherwise>            
          </c:choose>
        </div>
        <div class="col-md-9 text-right">
          <p>
            <a href="${pageContext.request.contextPath}/admin/updates/addNewUpdate" class="btn btn-primary">New Update</a>
          </p>
        </div>
      </div>
      
      <c:choose>
         <c:when test="${!adminUserUpdates.isDataModelEmpty()}">
            <c:forEach items="${adminUserUpdates.getListItems()}" var="userUpdate">
            
            </c:forEach>
         </c:when>
         <c:otherwise>
            <p>You don't have any updates...</p>
         </c:otherwise>
      </c:choose>
    </div>
   <!--  
    <div class="modal fade" id="editUserUpdateDlg">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Edit Your Update</h4>
          </div>
          <form class="form-horizontal" id="editUserUpdateForm" onsubmit="" onreset="">
          <input type="hidden" id="userUpdateId" value="">
          <div class="modal-body">
             <div class="form-group">
                <label for="userUpdateTitle" class="col-xs-3 control-label">Title</label>
                <div class="col-xs-9">
                   <input type="text" class="form-control" id="userUpdateTitle" name="userUpdateTitle" placeholder="The title of your update">
                </div>
             </div>
             <div class="form-group">
                <label for="userUpdateContent" class="col-xs-3 control-label">Content</label>
                <div class="col-xs-9">
<textarea class="form-control" id="userUpdateContent" name="userUpdateContent" rows="4">
</textarea>
                </div>
             </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary">Close</button>
            <button type="submit" class="btn btn-default">Save Update</button>
          </div>
          </form>
        </div>
      </div>
    </div> -->
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <!-- <script type="text/javascript">
       var createNewUpdate = function (){
          resetUpdateDlgToEmpty();
          $("#editUserUpdateDlg").modal("show");
       };
       
       var resetUpdateDlgToEmpty = function (){
          $("#editUserUpdateDlg #editUserUpdateForm #userUpdateId").val("");
          $("#editUserUpdateDlg #editUserUpdateForm #userUpdateTitle").val("");
          $("#editUserUpdateDlg #editUserUpdateForm #userUpdateContent").val("");
       };
       
       var validateUserUpdateAndSubmit = function (saveUpdateUrl) {
          var updateId = $("#editUserUpdateDlg #editUserUpdateForm #userUpdateId").val();
          var updateTitle = $("#editUserUpdateDlg #editUserUpdateForm #userUpdateTitle").val();
          var updateContent = $("#editUserUpdateDlg #editUserUpdateForm #userUpdateContent").val();
          
          var showError = false;
          var errorMsgs = [];
          if (updateTitle == null || updateTitle.length <= 0)
          {
             errorMsgs.push("The title of your update is null or empty.");
          }
          
          if (updateTitle != null && updateTitle.length > 128)
          {
             errorMsgs.push("The title of your update is too long, max 128 characters.");
          }
          
          if (updateContent == null || updateContent.length <= 0)
          {
             errorMsgs.push("The content of your update is null or empty.");
          }
       };
    </script> -->
  </tiles:putAttribute>

</tiles:insertDefinition> 