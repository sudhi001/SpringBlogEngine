var editImageDetails = function()
{
   $("#editImageDetailsPanel").show();
   $("#editImageDetailsBtn").hide();
   $("#cancelEditDetailsBtn").show();
}

var cancelEditDetails = function(imageTitle, imageKeywords)
{
   $("#editImageDetailsPanel").hide();
   $("#editImageDetailsBtn").show();
   $("#cancelEditDetailsBtn").hide();
   resetEditImageDetailsDlg(imageTitle, imageKeywords);
}

var resetEditImageDetailsDlg = function(imageTitle, imageKeywords)
{
   $("#editImageDetailsDlg #imageTitle").val(imageTitle);
   $("#editImageDetailsDlg #imageKeywords").val(imageKeywords);
   
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
