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

////////////////

var openImagesUploadDlg = function (galleryId)
{
   resetAddImagesDlg();
   $("#uploadImagesDlg #uploadImagesGalleryId").val(galleryId);
   $("#uploadImagesDlg").modal("show");
};

$('#uploadImagesDlg').on('hidden.bs.modal', function () {
   resetAddImagesDlg();
});

var resetAddImagesDlg = function ()
{
   $("#uploadImagesDlg #uploadImagesError").html("");
   $("#uploadImagesDlg #uploadImagesError").hide();
      
   $("#uploadImagesDlg #uploadImagesGalleryId").val("");
   $("#uploadImagesDlg #image1UploadControl").fileinput("clear");
   $("#uploadImagesDlg #image2UploadControl").fileinput("clear");
   $("#uploadImagesDlg #image3UploadControl").fileinput("clear");
   $("#uploadImagesDlg #image4UploadControl").fileinput("clear");
   $("#uploadImagesDlg #image5UploadControl").fileinput("clear");
};

var validateUploadsAndSubmit = function()
{
   var errorMsg = "";

   var fileUploadValidCount = 0;
   for (var i = 1; i <= 5; i++)
   {
      var fileUploadId = "#uploadImageDlg #image" + i + "ToUpload";
	  var imageToUpload = $("#uploadImageDlg #imageToUpload").val();
	  
      if (imageToUpload != null && imageToUpload.length > 0)
      {
         fileUploadValidCount = i;
      }
   }
   
   if (fileUploadValidCount <= 0)
   {
      errorMsg = "You must enter at least one file for upload.";
      $("#uploadImageDlg #uploadError").html(errorMsg);
      $("#uploadImageDlg #uploadError").show();
   }
   else
   {
	  $("#uploadImagesDlg #uploadImagesForm")[0].submit();
   }
};

