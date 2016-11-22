<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>
  
  <tiles:putAttribute name="body">
    <h3>${pageMetadata.pageTitle}</h3>
    <hr>
    <c:choose>
      <c:when test="${galleriesList != null && !galleriesList.isDataModelEmpty}">
      </c:when>
      <c:otherwise>
         <p>No gallery available.</p>
      </c:otherwise>
    </c:choose>
    Nothing
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script>
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>