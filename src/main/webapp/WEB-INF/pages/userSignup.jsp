<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<tiles:insertDefinition name="loginTemplate">
  <tiles:putAttribute name="body">
 
    <div class="container">
      <div class="panel panel-default panel-signup">
        <div class="panel-body">
        <form:form class="form-signup" id="userSignupForm" role="form" modelAttribute="userSignupModel" method="POST" action="${pageContext.request.contextPath}/handleUserSignup">
          <h2 class="form-signin-heading">${pageMetadata.pageTitle}</h2>
          <div class="form-group">
            <label>User Name</label>
            <form:input path="userName" id="userName" class="form-control" placeholder="User Name" />
          </div>
          <div class="form-group">
            <label>User Email</label>
            <form:input path="userEmail" id="userEmail" type="email" class="form-control" placeholder="User Email" />
          </div>
          <div class="form-group">
            <label>Password</label>
            <form:input path="userPass1" id="userPass1" type="password" class="form-control" placeholder="User Password" />
          </div>
          <div class="form-group">
          <label>Retype your password</label>
          <form:input path="userPass2" id="userPass2" type="password" class="form-control" placeholder="User Password" />
          </div>
          <div class="form-group">
             <div class="alert alert-danger" id="inputError" style="display: none"  role="alert">error goes here</div>
          </div>
          <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
        </form:form>
        </div>
      </div>
    </div>
 
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
 
    <script src="assets/js/jquery.min-1.11.1.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    function validateFormInput()
    {
       hideError();
       
       var textVal = $("#userName").val();
       if (textVal === null || textVal.length <= 0)
       {
          showError("User name is null or empty.");
          return false;
       }
       
       if (textVal.length > 64)
       {
          showError("User name has chars more than 64.");
          return false;
       }
       
       textVal = $("#userEmail").val();
       if (textVal === null || textVal.length <= 0)
       {
          showError("User email is null or empty.");
          return false;
       }
       
       if (textVal.length > 64)
       {
          showError("User email has chars more than 64.");
          return false;
       }
       
       textVal = $("#userPass1").val();
       if (textVal === null || textVal.length <= 0)
       {
          showError("Entered password is null or empty.");
          return false;
       }
       
       if (textVal.length > 24)
       {
          showError("Entered password has chars more than 24.");
          return false;
       }
       
       var textVal2 = $("#userPass2").val();
       if (textVal2 === null || textVal2.length <= 0)
       {
    	  showError("Re-entered password is null or empty.");
          return false;
       }
       
       if (textVal2.length > 24)
       {
          showError("Re-entered password has chars more than 24.");
          return false;
       }
       
       if (textVal !== textVal2)
       {
          showError("The entered passwords mismatch.");
          return false;
       }
       
       return true;
    }
    
    function hideError()
    {
       $("#inputError").html("");
       $("#inputError").hide();
    }
    
    function showError(errMsg)
    {
       $("#inputError").html(errMsg);
       $("#inputError").show();
       
       $("#inputError").fadeOut(10000);
    }
    
    $("#userSignupForm").submit(
       function(event)
       {
          hideError();
          if (!validateFormInput())
          {
             event.preventDefault();
          }
       }
    );
    </script>
    
  </tiles:putAttribute>
</tiles:insertDefinition>