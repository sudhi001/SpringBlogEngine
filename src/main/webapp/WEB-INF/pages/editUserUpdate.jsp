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
          <div class="row">
             <div class="col-xs-12"><h3>${pageMetadata.pageTitle}</h3></div>
          </div>
        </div>
      </div>

      <form class="form-horizontal" id="editUserUpdateForm"
            action="${pageContext.request.contextPath}/admin/updates/addEditUserUpdate" method="POST"
            onsubmit="return validateUserUpdateAndSubmit();" onreset="resetUpdateDlgToEmpty()">
         <input type="hidden" id="userUpdateId" name="userUpdateId" value="${userUpdate.userUpdateId}">
      <div class="box-body" style="display: block;">
         <div class="form-group">
            <label for="userUpdateTitle" class="col-xs-3 control-label">Title</label>
            <div class="col-xs-9">
               <input type="text" class="form-control" id="userUpdateTitle" name="userUpdateTitle" placeholder="The title of your update" value="${userUpdate.userUpdateTitle}">
            </div>
         </div>
         <div class="form-group">
            <label for="userUpdateContent" class="col-xs-3 control-label">Content</label>
            <div class="col-xs-9">
<textarea class="form-control" id="userUpdateContent" name="userUpdateContent" rows="4">
${userUpdate.userUpdateContent}
</textarea>
            </div>
         </div>
         <div class="row">
            <div class="col-xs-9 col-xs-offset-3">
               <div class="checkbox">
                  <label>
                     <c:choose>
                     <c:when test="${userUpdate.isUserUpdatePublished()}">
                        <input type="checkbox" id="userUpdateViewable" name="userUpdateViewable" checked="checked"> Publish this update
                     </c:when>
                     <c:otherwise>
                        <input type="checkbox" id="userUpdateViewable" name="userUpdateViewable"> Publish this update
                     </c:otherwise>
                     </c:choose>
                  </label>
               </div>
            </div>
         </div>
         
         <div class="row margin-updown" id="editUserUpdateError" style="display: none;">
            <div class="col-xs-12">
               <div class="warning-block" id="editUserUpdateErrorMsg">
                  test test
               </div>
            </div>
         </div>     
      </div>
      <div class="box-footer box-comments" style="display: block; margin-bottom: 15px;">
         <div class="row">
            <div class="col-xs-12 text-right">
               <button class="btn btn-danger btn-xs" type="reset">Clear</button>
               <button class="btn btn-primary btn-xs" type="submit">Save Update</button>
            </div>
         </div>
      </div>
      </form>
      <div class="box-footer text-right" style="display: block;">
      </div>
    </div>
    
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script type="text/javascript">
       var resetUpdateDlgToEmpty = function (){
          $("#editUserUpdateError #editUserUpdateErrorMsg").html("");
          $("#editUserUpdateError").hide();
          $("#editUserUpdateForm")[0].reset();
       };
       
       var validateUserUpdateAndSubmit = function () {
          var updateId = $("#editUserUpdateForm #userUpdateId").val();
          var updateTitle = $("#editUserUpdateForm #userUpdateTitle").val();
          var updateContent = $("#editUserUpdateForm #userUpdateContent").val();
          
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
          
          if (errorMsgs != null && errorMsgs.length > 0)
          {
             $("#editUserUpdateError #editUserUpdateErrorMsg").html(errorMsgs[0]);
             $("#editUserUpdateError").show();
             return false;
          }
          else
          {
             $("#editUserUpdateForm")[0].submit();
             return true;
          }
       };
    </script>
  </tiles:putAttribute>

</tiles:insertDefinition> 