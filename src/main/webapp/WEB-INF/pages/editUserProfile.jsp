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
             <form:form id="userProfileEditForm" class="form-horizontal" role="form" modelAttribute="editUserProfile" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/saveUserProfile">
                <form:hidden id="userProfileId" path="userProfileId" value="" />
                <div class="form-group">
                   <label class="col-md-2 control-label">User Icon</label>
                   <div id="userIconFile" class="fileinput fileinput-new input-group col-md-8" data-provides="fileinput">
                      <div class="form-control" data-trigger="fileinput"><i class="glyphicon glyphicon-file fileinput-exists"></i> <span class="fileinput-filename"></span></div>
                      <span class="input-group-addon btn btn-default btn-file"><span class="fileinput-new">Select file</span><span class="fileinput-exists">Change</span><input type="file" name="userIconToUpload" id="userIconToUpload"></span>
                      <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                   </div>
                </div>                
                <div class="form-group">
                   <label class="col-md-2 control-label">Last Name</label>
                   <div class="col-md-4">
                      <form:input class="form-control" id="userLastName" path="userLastName"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">First Name</label>
                   <div class="col-md-4">
                      <form:input class="form-control" id="userFirstName" path="userFirstName"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">User Email</label>
                   <div class="col-md-6">
                      <form:input class="form-control" id="userEmail" path="userEmail" readonly="true"/>
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
                   <label class="col-md-2 control-label">Profession</label>
                   <div class="col-md-10">
<form:textarea class="form-control" id="userIntroduction" style="height: 120px; max-height: 120px;" path="userIntroduction"></form:textarea>
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
    <script src="${pageContext.request.contextPath}/assets/jasny/js/jasny-bootstrap.min.js"></script>    <script type="text/javascript">
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>