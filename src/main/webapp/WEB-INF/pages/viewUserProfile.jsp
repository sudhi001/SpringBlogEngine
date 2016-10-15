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
                   <button class="btn btn-default" onclick="">Upload Profile Icon</button>
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
     
     
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/jasny/js/jasny-bootstrap.min.js"></script>    <script type="text/javascript">
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>