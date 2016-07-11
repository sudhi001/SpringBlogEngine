<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css"></link>
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
     <p>This is the Index Page.</p>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="assets/js/jquery.min-1.11.1.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>