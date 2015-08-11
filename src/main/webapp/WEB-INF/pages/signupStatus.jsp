<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <div class="page-header">
      <h1>Welcome ${userInfo.userName}</h1>
    </div>
    <p>
    Thank you for sign up. Please use your power wisely.
    </p>
    <p>
    If you have any questions, please <a href="${pageContext.request.contextPath}/signin">sign in</a>. And send the inquiry to the administrators.
    </p>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="assets/js/jquery.min-1.11.1.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>