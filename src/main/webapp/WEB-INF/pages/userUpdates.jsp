<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/photo-swipe/default-skin/default-skin.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>
  
  <tiles:putAttribute name="body">
    <h2 class="text-right">${pageMetadata.pageTitle}</h2>
    <hr>
    
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/photo-swipe/photoswipe-ui-default.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>