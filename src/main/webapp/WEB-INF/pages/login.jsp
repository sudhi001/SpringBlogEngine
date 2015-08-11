<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 
<tiles:insertDefinition name="loginTemplate">
  <tiles:putAttribute name="body">
 
    <div class="container">
      <div class="panel panel-default panel-signin">
        <div class="panel-body">
        <form class="form-signin" role="form" method="POST" action="${pageContext.request.contextPath}/j_spring_security_check">
          <h2 class="form-signin-heading">Please sign in</h2>
          <input name="j_username" class="form-control" placeholder="User Name" required autofocus >
          <br/>
          <input name="j_password" type="password" class="form-control" placeholder="Password" >
          <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
        </div>
      </div>
    </div>
 
  </tiles:putAttribute>
  
    <tiles:putAttribute name="javascriptContent">
 
    <script src="assets/js/jquery.min-1.11.1.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    
  </tiles:putAttribute>
</tiles:insertDefinition>