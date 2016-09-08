var editGalleryDetails  = function()
{
   $("#editDetailsBtn").hide();
   $("#cancelEditDetailsBtn").show();
   $("#editGalleryDetailsDlg").show();
}

var cancelEditingGalleryDetails  = function(
   galleryTitle,
   galleryKeywords,
   galleryDesc,
   isActive,
   isVisible
)
{
   $("#editDetailsBtn").show();
   $("#cancelEditDetailsBtn").hide();
   $("#editGalleryDetailsDlg").hide();
   
   resetEditGalleryDetailsForm(
      galleryTitle,
      galleryKeywords,
      galleryDesc,
      isActive,
      isVisible);
}

var validateEditingGalleryDetailsAndSubmit = function()
{
   var validateFailed = false;
   var errorMsg = null;
   var galleryTitle = $("#editGalleryDetailsForm #galleryTitle").val();
   if (galleryTitle == null || galleryTitle.length <= 0)
   {
      validateFailed = true;
      errorMsg = "Gallery title is null or empty string.";
   }
   
   if (galleryTitle != null && galleryTitle.length > 96)
   {
	  validateFailed = true;
	  errorMsg = "Gallery title is longer than 96 characters.";
   }
   
   var gallerykeywords = $("#editGalleryDetailsForm #galleryKeywords").val();
   if (gallerykeywords != null && gallerykeywords.length > 128)
   {
	  validateFailed = true;
	  errorMsg = "Gallery keywords is longer than 128 characters.";
   }
   
   var galleryDesc = $("#editGalleryDetailsForm #galleryDesc").val();
   if (galleryDesc != null && galleryDesc.length > 3072)
   {
      validateFailed = true;
      errorMsg = "Gallery description is longer than 3072 characters.";
   }
   
   if (validateFailed)
   {
      displayGalleryEditingError(errorMsg);
      return false;
   }
   else
   {
      $("#editGalleryDetailsForm")[0].submot();
      return true;
   }
}

var resetEditGalleryDetailsForm = function(
   galleryTitle,
   galleryKeywords,
   galleryDesc,
   isActive,
   isVisible
)
{
   $("#editGalleryDetailsForm #galleryTitle").val(galleryTitle);
   $("#editGalleryDetailsForm #galleryKeywords").val(galleryKeywords);
   $("#editGalleryDetailsForm #galleryDesc").val(galleryDesc);
   
   if (isActive)
   { 
	   $("#editGalleryDetailsForm #galleryActive").prop('checked', true);
   }
   else
   {
	   $("#editGalleryDetailsForm #galleryActive").prop('checked', false);	   
   }
   
   if (isVisible)
   { 
	   $("#editGalleryDetailsForm #galleryVisible").prop('checked', true);
   }
   else
   {
	   $("#editGalleryDetailsForm #galleryVisible").prop('checked', false);	   
   }
}

var displayGalleryEditingError = function(errorMsg)
{
    if (errorMsg == null || errorMsg.length <= 0)
    {
 	   $("#editGalleryDetailsForm #editGalleryDetailsError #editGalleryDetailsErrorMsg").html("");
 	   $("#editGalleryDetailsForm #editGalleryDetailsError").hide();
    }
    else
    {
  	   $("#editGalleryDetailsForm #editGalleryDetailsError #editGalleryDetailsErrorMsg").html(errorMsg);
 	   $("#editGalleryDetailsForm #editGalleryDetailsError").show();
    }
}