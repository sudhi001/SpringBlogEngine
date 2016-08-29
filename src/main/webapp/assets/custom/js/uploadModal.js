var openImageUploadDlg = function (galleryId)
{
   resetAddImageDlg();
   $("#uploadImageDlg #uploadImageGalleryId").val(galleryId);
   $("#uploadImageDlg").modal("show");
};
   
$('#uploadImageDlg').on('hidden.bs.modal', function () {
   resetAddImageDlg();
});
   
var resetAddImageDlg = function ()
{
   $("#uploadImageDlg #uploadError").html("");
   $("#uploadImageDlg #uploadError").hide();
      
   $("#uploadImageDlg #uploadImageGalleryId").val("");
   $("#uploadImageDlg #imageTitle").val("");
   $("#uploadImageDlg #imageKeywords").val("");
   $("#uploadImageDlg #imageUploadControl").fileinput("clear");
};
   
var validateUploadAndSubmit = function()
{
   var isValid = true;
   var errorMsg = "";
   var imageTitleVal = $("#uploadImageDlg #imageTitle").val();
   if (imageTitleVal == null || imageTitle.length <= 0)
   {
     errorMsg = "the title of the image is empty";
     isValid = false;
   }
   else if (imageTitleVal != null && imageTitle.length > 96)
   {
     errorMsg = "the title of the image is too long";
     isValid = false;
   }
   
   var imageKeywords = $("#uploadImageDlg #imageKeywords").val();
   if (imageKeywords != null && imageKeywords.length > 128)
   {
      errorMsg = "the keywords of the image is too long";
      isValid = false;
   }
    
   var imageToUpload = $("#uploadImageDlg #imageToUpload").val();
   if (imageToUpload == null || imageToUpload.length <= 0)
   {
      errorMsg = "the file name of the image is empty";
      isValid = false;
   }
    
   if (!isValid)
   {
      $("#uploadImageDlg #uploadError").html(errorMsg);
      $("#uploadImageDlg #uploadError").show();
   }
   else
   {
      $("#uploadImageDlg #uploadImageForm")[0].submit();
   }
};
