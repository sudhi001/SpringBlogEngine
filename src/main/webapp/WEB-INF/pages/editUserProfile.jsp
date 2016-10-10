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
              <span class="username">User Profile: ${userInfo.userName}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="box-body" style="display: block;">
        <div class="row">
          <div class="col-xs-12">
             <form id="userProfileEditForm" class="form" action="${pageContext.request.contextPath}/admin/saveUserProfile">
                <input type="hidden" id="userProfileId" name="userProfileId" value="">
                <div class="form-group">
                   <label class="col-md-2 control-label">First Name</label>
                   <div class="col-md-4">
                      <input id="userFirstName" name="userFirstName" class="form-control"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Last Name</label>
                   <div class="col-md-4">
                      <input id="userLastName" name="userLastName" class="form-control"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">User Email</label>
                   <div class="col-md-8">
                      <input id="userEmail" name="userEmail" class="form-control"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Age</label>
                   <div class="col-md-3">
                      <input id="userAge" name="userAge" class="form-control"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Location</label>
                   <div class="col-md-6">
                      <input id="userLocation" name="userLocation" class="form-control"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Profession</label>
                   <div class="col-md-6">
                      <input id="userProfession" name="userProfession" class="form-control"/>
                   </div>
                </div>
                <div class="form-group">
                   <label class="col-md-2 control-label">Profession</label>
                   <div class="col-md-12">
<textarea class="form-control" id="userIntroduction" name="userIntroduction"></textarea>
                   </div>
                </div>
                <div class="form-group">
                   <div class="row">
                      <div class="col-md-3">
                         <button type="submit" class="form-control">Save</button>
                      </div>
                      <div class="col-md-3">
                         <button type="reset" class="form-control">Clear</button>
                      </div>
                   </div>
                </div>
             </form>
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