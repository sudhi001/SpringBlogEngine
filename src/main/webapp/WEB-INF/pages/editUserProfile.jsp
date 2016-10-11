<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
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
             <form:form id="userProfileEditForm" class="form" role="form" modelAttribute="editUserProfile" action="${pageContext.request.contextPath}/admin/saveUserProfile">
                <form:hidden id="userProfileId" path="userProfileId" value="" />
                <div class="form-group">
                   <label class="col-md-2 control-label">Last Name</label>
                   <div class="col-md-12">
                      <form:input class="form-control" id="userLastName" path="userLastName"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">First Name</label>
                   <div class="col-md-12">
                      <form:input class="form-control" id="userFirstName" path="userFirstName"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">User Email</label>
                   <div class="col-md-12">
                      <form:input class="form-control" id="userEmail" path="userEmail"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Age</label>
                   <div class="col-md-12">
                      <form:input class="form-control" id="userAge" path="userAge"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Location</label>
                   <div class="col-md-12">
                      <form:input class="form-control" id="userLocation" path="userLocation"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Profession</label>
                   <div class="col-md-12">
                      <form:input class="form-control" id="userProfession" path="userProfession"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Profession</label>
                   <div class="col-md-12">
<form:textarea class="form-control" id="userIntroduction" style="height: 120px; max-height: 120px;" path="userIntroduction"></form:textarea>
                   </div>
                </div>
                <hr>
                <div class="row text-center">
                   <button class="btn btn-default col-md-3" type="submit">Save</button>
                   <button class="btn btn-default col-md-3" type="reset">Clear</button>
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
    <script type="text/javascript">
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>