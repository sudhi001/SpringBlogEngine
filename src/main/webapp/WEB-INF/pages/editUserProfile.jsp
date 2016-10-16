<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <div class="content-box">
      <div class="box-header with-border">
        <div class="user-block">
          <div class="row">
            <div class="col-xs-12">
              <span class="username">User Profile: ${editUserProfile.userName}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="box-body" style="display: block;">
        <div class="row">
          <div class="col-xs-12">
             <form:form id="userProfileEditForm" class="form-horizontal" role="form" modelAttribute="editUserProfile" method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/saveUserProfile"
                 onsubmit="return validateUserProfileEdit();" onreset="resetUserProfileEdit();">
                <form:hidden id="userProfileId" path="userProfileId" value="" />              
                <div class="form-group">
                   <label class="col-md-2 control-label">First Name</label>
                   <div class="col-md-4">
                      <form:input class="form-control" id="userFirstName" path="userFirstName"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Last Name</label>
                   <div class="col-md-4">
                      <form:input class="form-control" id="userLastName" path="userLastName"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">User Email</label>
                   <div class="col-md-6">
                      <form:input class="form-control" id="userEmail" path="userEmail" readonly="true"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">User Gender</label>
                   <div class="radio" style="padding-left: 10px;">
                      <label>
                         <form:radiobutton path="userGender" id="genderMale" value="male"></form:radiobutton> Male
                      </label>
                      <label>
                         <form:radiobutton path="userGender" id="genderFemale" value="female"></form:radiobutton> Female
                      </label>
                    </div>
				</div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Age</label>
                   <div class="col-md-3">
                      <form:input class="form-control" id="userAge" path="userAge"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Location</label>
                   <div class="col-md-4">
                      <form:input class="form-control" id="userLocation" path="userLocation"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Profession</label>
                   <div class="col-md-8">
                      <form:input class="form-control" id="userProfession" path="userProfession"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Introduction</label>
                   <div class="col-md-10">
<form:textarea class="form-control" id="userIntroduction" style="height: 120px; max-height: 120px;" path="userIntroduction"></form:textarea>
                   </div>
                </div>
                <div class="row margin-updown" id="editUserProfileError" style="display: none;">
                   <div class="col-xs-12">
                      <div class="warning-block" id="editUserProfileErrorMsg">
                         test test
                      </div>
                   </div>
                </div>
                <div class="row text-center">
                   <div class="col-xs-12 col-sm-6 col-md-3 col-md-offset-3">
                      <button class="btn btn-default form-control" type="submit">Save</button>
                   </div>
                   <div class="col-xs-12 col-sm-6 col-md-3">
                      <button class="btn btn-default form-control" type="reset">Clear</button>
                   </div>
                </div>
             </form:form>
          </div>
        </div>
      </div>

      <div class="box-footer box-comments" style="display: block;">
      </div>

      <div class="box-footer" style="display: block;">
      </div>
    </div>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/custom/js/editUserProfile.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>