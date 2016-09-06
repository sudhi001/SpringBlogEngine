<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
     <div class="content-box">
       <div class="box-header with-border">
         <div class="user-block">
           <span class="username">${imageDetail.getImageTitle()}</span>
           <span class="description"></span>
         </div>
       </div>
       <div class="box-body" style="display: block;">
         <p><strong>Keywords:</strong> ${imageDetail.getImageKeywords()}; <strong>Status:</strong>
            <c:choose>
               <c:when test="${imageDetail.isImageActive()}">
                  Active
               </c:when>
               <c:otherwise>
                  Inactive
               </c:otherwise>
            </c:choose>
         </p>
       
         <img class="img-responsive pad" style="text-align: center;" width="100%" height="100%" src="${pageContext.request.contextPath}/secure/image-full/${imageDetail.getImageId()}" alt="Photo">
         
         <div class="row" style="margin-top: 10px; margin-bottom: 10px;">
            <div class="col-xs-12">
               <button class="btn btn-default" id="editImageDetailsBtn" onclick="editImageDetails()">Edit Detail</button>&nbsp;
               <button class="btn btn-default" id="cancelEditDetailsBtn" style="display: none;" onclick="cancelEditDetails()">Cancel Editing</button>&nbsp;
               <button class="btn btn-default">Write Blog Post</button>
            </div>
         </div>
         <div class="panel panel-default" id="editImageDetailsPanel" style="display: none;">
            <div class="panel-body">
               <form id="editImageDetailsDlg" class="form" action="${pageContext.request.contextPath}/admin/image/editDetails"
                     method="POST" onsubmit="return validateEditImageDetails()"
                     onreset="resetEditImageDetailsDlg()">
                  <legend>Edit Image Detail</legend>
                  <input type="hidden" id="imageId" name="imageId" value="${imageDetail.getImageId()}" >
                  <div class="form-group">
                     <label class="col-xs-12 control-label">Title</label>
			         <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
			            <input id="imageTitle" name="imageTitle" class="form-control" value="${imageDetail.getImageTitle()}" />
			         </div>
                  </div>
                  <div class="form-group">
                     <label class="col-xs-12 control-label">Keywords</label>
			         <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
			            <input id="imageKeywords" name="imageKeywords" class="form-control" value="${imageDetail.getImageKeywords()}" />
			         </div>
                  </div>
                  <div class="checkbox col-xs-12 col-sm-12 col-md-6 col-lg-4">
                     <label>
                        <c:choose>
                           <c:when test="${imageDetail.isImageActive()}">
                              <input type="checkbox" id="imageActive" name="imageActive" checked/> Active
                           </c:when>
                           <c:otherwise>
                              <input type="checkbox" id="imageActive" name="imageActive" /> Active
                           </c:otherwise>
                        </c:choose>
                     </label>
                  </div>
                  <div class="row margin-updown" id="editImageDetailsError" style="display: none;">
                     <div class="col-xs-12">
                        <div class="warning-block" id="editImageDetailsErrorMsg">
                           test test
                        </div>
                     </div>
                  </div>
                  <div class="form-group">
			         <div class="col-xs-12 text-center">
			            <button type="submit" class="btn btn-default">Save</button>&nbsp;
			            <button type="reset" class="btn btn-default">Reset</button>&nbsp;			            
			         </div>
                  </div>
               </form>
            </div>
         </div>
       </div>
     </div>
          
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        var editImageDetails = function()
        {
           $("#editImageDetailsPanel").show();
           $("#editImageDetailsBtn").hide();
           $("#cancelEditDetailsBtn").show();
        }
        
        var cancelEditDetails = function()
        {
           $("#editImageDetailsPanel").hide();
           $("#editImageDetailsBtn").show();
           $("#cancelEditDetailsBtn").hide();
           resetEditImageDetailsDlg();
        }
        
        var resetEditImageDetailsDlg = function()
        {
           $("#editImageDetailsDlg #imageTitle").val("${imageDetail.getImageTitle()}");
           $("#editImageDetailsDlg #imageKeywords").val("${imageDetail.getImageKeywords()}");
           
           displayError(null);
        }
        
        var validateEditImageDetails = function() 
        {
           var imageTitle = $("#editImageDetailsDlg #imageTitle").val();
           var imageKeywords = $("#editImageDetailsDlg #imageKeywords").val();    	
           var errorMsg = null;
           var dataInvalid = false;
           
           if (imageTitle == null || imageTitle.length <= 0)
           {
              dataInvalid = true;
        	  errorMsg = "Image title is null or empty string.";
           }
           else if (imageTitle.length > 96)
           {
              dataInvalid = true;
              errorMsg = "Image title exceeds 96 characters";
           }
           
           if (imageKeywords != null && imageKeywords.length > 128)
           {
        	  dataInvalid = true;
              errorMsg = "Image keywords exceeds 128 characters";
           }
           
           if (!dataInvalid)
           {
        	   $("#editImageDetailsDlg")[0].submit();
        	   return true;
           }
           else
           {
              displayError(errorMsg);
              return false;
           }
        }
        
        var displayError = function(errorMsg)
        {
           if (errorMsg == null || errorMsg.length <= 0)
           {
        	   $("#editImageDetailsDlg #editImageDetailsError #editImageDetailsErrorMsg").html("");
        	   $("#editImageDetailsDlg #editImageDetailsError").hide();
           }
           else
           {
               $("#editImageDetailsDlg #editImageDetailsError #editImageDetailsErrorMsg").html(errorMsg);
        	   $("#editImageDetailsDlg #editImageDetailsError").show();
           }
        }
     </script>
    
  </tiles:putAttribute>
</tiles:insertDefinition>