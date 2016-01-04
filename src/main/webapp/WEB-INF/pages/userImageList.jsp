<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>All My Images</h3>
    <hr>
    
    <div class="row">
       <div class="col-md-8">
          <span class="label label-info">You have ${userImagesListPageModel.getTotalElementsCount()} image(s)</span>
       </div>
       <div class="col-md-4 text-right">
          <button class="btn btn-default">Add Image</button>
       </div>
    </div>
    
    <c:if test="${!userImagesListPageModel.isDataModelEmpty()}">
       <div class="row">
          <c:forEach  items="${userImagesListPageModel.getListItems()}" var="imageItem">
          <div class="col-md-3">
             <img src="${pageContext.request.contextPath}/secure/image-thumb/${imageItem.getImageId()}" width="100%" height="100%">
          </div>
          </c:forEach>
       </div>
    </c:if>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    </script>
  </tiles:putAttribute>

</tiles:insertDefinition>