<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
     <div class="content-box">
       <div class="box-header with-border">
         <div class="user-block">
           <span class="username">User Profile: ${userProfileToView.getUserName()}</span>
           <span class="description"></span>
         </div>
       </div>
       <div class="box-body" style="display: block;">
          <c:if test="${userProfileToView != null && userProfileToView.getUserIconId() != null && userProfileToView.getUserIconId().length() > 0}">
          <div class="row">
             <div class="col-md-3">
                <div class="thumbnail" style="width: 100px; max-width: 100px;">
                   <img src="${pageContext.request.contextPath}/public/imgresource/${userProfileToView.getUserIconId()}" width="100%">
                </div>
             </div>         
          </div>
          </c:if>
          <div class="row">
             <div class="col-md-2">
                <label>First Name:</label>
             </div>
             <div class="col-md-3" style="background-color: #f3f3f3;">
                ${userProfileToView.getUserFirstName()}
             </div>             
          </div>
          <div class="row">
             <div class="col-md-2">
                <label>Last Name:</label>
             </div>
             <div class="col-md-3" style="background-color: #f3f3f3;">
                ${userProfileToView.getUserLastName()}
             </div>
          </div>
          <div class="row">
             <div class="col-md-2">
                <label>Gender:</label>
             </div>
             <div class="col-md-2" style="background-color: #f3f3f3;">
                ${userProfileToView.getUserGender().equals("male")? "Male" : "Female"}
             </div>
          </div>
          <div class="row">
             <div class="col-md-2">
                <label>Age:</label>
             </div>
             <div class="col-md-2" style="background-color: #f3f3f3;">
                ${userProfileToView.getUserAge()}
             </div>
          </div>
          <div class="row">
             <div class="col-md-2">
                <label>Current Location:</label>
             </div>
             <div class="col-md-6" style="background-color: #f3f3f3;">
                ${userProfileToView.getUserLocation()}
             </div>
          </div>
          <div class="row">
             <div class="col-md-2">
                <label>Profession:</label>
             </div>
             <div class="col-md-6" style="background-color: #f3f3f3;">
                ${userProfileToView.getUserProfession()}
             </div>
          </div>
          <div class="row">
             <div class="col-md-2">
                <label>Introduction:</label>
             </div>
             <div class="col-md-6" style="background-color: #f3f3f3;">
<c:out value="${userProfileToView.getUserIntroduction()}" escapeXml = "false"/>
             </div>
          </div>
          <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_STAFF">
             <div class="row">
                <div class="col-md-12" style="padding-top: 25px;">
                   <a href="${pageContext.request.contextPath}/admin/editUserProfile" class="btn btn-default">Edit Profile</a>&nbsp;
                   <button class="btn btn-default" onclick="handleClickChangeUserIconBtn()">Upload Profile Icon</button>
                </div>
             </div>
          </sec:authorize>
       </div>
       <div class="box-footer box-comments" style="display: block;">
          <div class="row">
             <div class="col-xs-12 text-right">
                 <label>Last Modified:</label>
                 ${userProfileToView.getProfileUpdateDateString()}
             </div>
          </div>
       </div>

       <div class="box-footer" style="display: block;">
       </div>
     </div>
     
     <div id="addUserIconDlg" class="modal fade">
        <div class="modal-dialog">
           <div class="modal-content">
              <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                 <h4 class="modal-title">Add/Change User Icon</h4>
              </div>
              <form id="addUserIconForm" class="form-horizontal" action="${pageContext.request.contextPath}/admin/changeUserIcon" enctype="multipart/form-data" method="post"
                    onsubmit="return validateUserIconData()" onreset="resetChangeUserIconDlg()">
              <input type="hidden" id="userProfileId" name="userProfileId" value="${userProfileToView.getUserProfileId()}">
              <div class="modal-body">
                 <div class="form-group">
                    <label for="userIconName" class="col-xs-3 control-label text-right">Icon Name</label>
                    <div class="col-xs-6">
                       <input class="form-control" type="text" name="userIconName" id="userIconName" placeholder="Enter name of user's icon">
                    </div>
                 </div>
                 <div class="form-group">
                    <label for="userIconToUpload" class="control-label col-xs-3 text-right">Resource File</label>
                    <div class="col-xs-9">
                       <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                          <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                          <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="userIconToUpload" id="userIconToUpload"></span>
                          <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                       </div>
                    </div>
                 </div>
                 <div class="row margin-updown" id="addUserIconError" style="display: none;">
                    <div class="col-xs-12">
                       <div class="warning-block" id="addUserIconErrorMsg">
                          test test
                       </div>
                    </div>
                 </div>
              </div>
              <div class="modal-footer">
                 <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                 <button type="submit" class="btn btn-primary">Upload</button>
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
    <script src="${pageContext.request.contextPath}/assets/custom/js/changeUserIcon.js"></script>  </tiles:putAttribute>
</tiles:insertDefinition>