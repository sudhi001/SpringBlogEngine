var openAddGalleryDlg = function ()
{
   resetAddGalleryDlg();
   $("#addGalleryDlg").modal("show");
};

$('#addGalleryDlg').on('hidden.bs.modal', function () {
   resetAddGalleryDlg();
});

var resetAddGalleryDlg = function ()
{
   $("#addGalleryDlg #addGalleryError").html("");
   $("#addGalleryDlg #addGalleryError").hide();
 
   $("#addGalleryDlg #galleryTitle").val("");
   $("#addGalleryDlg #galleryKeywords").val("");
   $("#addGalleryDlg #galleryDesc").val("");
}

var validateAddGalleryAndSubmit = function()
{
   var isValid = true;
   var errorMsg = "";
   var gallerName = $("#addGalleryDlg #galleryTitle").val();
   if (gallerName == null || gallerName.length <= 0)
   {
      errorMsg = "the title of the gallery is empty";
      isValid = false;
   }
   else if (gallerName != null && gallerName.length > 96)
   {
      errorMsg = "the title of the gallery is too long";
      isValid = false;
   }
 
   var galleryDesc = $("#addGalleryDlg #galleryDesc").val();
   if (galleryDesc != null && galleryDesc.length > 3072)
   {
      errorMsg = "the description of the gallery is too long";
      isValid = false;
   }
  
   var galleryKeywords = $("#addGalleryDlg #galleryKeywords").val();
   if (galleryKeywords != null && galleryKeywords.length > 128)
   {
      errorMsg = "the keywords for gallery is too long";
      isValid = false;
   }
  
   if (!isValid)
   {
      $("#addGalleryDlg #addGalleryError").html(errorMsg);
      $("#addGalleryDlg #addGalleryError").show();
   }
   else
   {
      $("#addGalleryDlg #addGalleryForm")[0].submit();
   }
};

var showGallery = function (baseuri, galleryId, visibility) 
{
   if (galleryId == null || visibility == null)
   {
      return;
   }
 
   $.ajax({
      type: "GET",
      url: baseuri + "/admin/galleries/showGallery?galleryId=" + galleryId + "&show=" + visibility,
      xhrFields: {
         withCredentials: true
      },
      success: function(data) {
         location.reload(true);
      },
      error: function() {
      }
   });
}

var setGalleryActive = function (baseuri, galleryId, active) 
{
   if (galleryId == null || active == null)
   {
      return;
   }
 
   $.ajax({
      type: "GET",
      url: baseuri + "/admin/galleries/setGalleryActive?galleryId=" + galleryId + "&enable=" + active,
      xhrFields: {
         withCredentials: true
      },
      success: function(data) {
         location.reload(true);
      },
      error: function() {
      }
   });
}