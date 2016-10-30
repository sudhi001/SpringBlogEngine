var clickEditArticleIconBtn = function (articleId, baseUrl) {
   showError("");
   $("#addArticleIconDlg #addArticleIconForm #addIconArticleId").val(articleId);
   $("#addArticleIconDlg").modal("show");
   
   if (baseUrl != null) {
      $.ajax({
         type: "GET",
         url: baseUrl + "/admin/resources/articleImages?pageIdx=0",
         xhrFields: {
            withCredentials: true
         },
         success: function(data) {
            clearAllIcons();
            console.log(JSON.stringify(data));
            if (data != null) {
               if (data.currentPageIdx >= 0 &&
                  data.articleIconList != null &&
                  data.articleIconList.length > 0) {
                  addArticleIconToUI(articleId, data.articleIconList, baseUrl);
               }
            }
         },
         error: function(data) {
            showError("Something bad happened when trying to fetch article icons.");
         }
      });
   }
};

var showError = function (errorMsg) {
   if (errorMsg != null && errorMsg.length > 0) {
      $("#addArticleIconDlg #addArticleIconError #addArticleIconErrorMsg").html(errorMsg);
      $("#addArticleIconDlg #addArticleIconError").show();
   } else {
      $("#addArticleIconDlg #addArticleIconError #addArticleIconErrorMsg").html("");
      $("#addArticleIconDlg #addArticleIconError").hide();
   }
};

var addArticleIconToUI = function (articleId, articleIconList, baseUrl) {
   if (articleIconList != null && articleIconList.length > 0) {
      var articleIcons = [];
      for (var i = 0; i < articleIconList.length; i++) {
         var iconImg = $("<img>", {
            "src": baseUrl + "/secure/imgresource/" + articleIconList[i].resourceId,
            "width": "100%",
            "class": "img-responsive",
            "style": "max-width: 118px;",
            "alt":  articleIconList[i].resourceName
         });
         
         var iconHref = $("<a>", {
            "href": "#",
            "onclick": "setArticleIcon('" + articleId + "', '" + articleIconList[i].resourceId + "', '" + baseUrl + "')"
         }).append(iconImg);
         
         var imageDiv = $("<div>", {
            "class": "col-md-4 thumbnail",
            "style": "max-width: 140px; margin: 5px;"
         }).append(iconHref);
         
         articleIcons.push(imageDiv);
      }
      
      if (articleIcons.length > 0) {
         $("#addArticleIconDlg #addArticleIconForm #addArticleIconGallery").append(articleIcons);
      }
   }
};

var clearAllIcons = function () {
   $("#addArticleIconDlg #addArticleIconForm #addArticleIconGallery").empty();
};

var setArticleIcon = function (articleId, iconResourceId, baseUrl) {
   if (articleId != null && articleId.length > 0 &&
      iconResourceId != null &&  iconResourceId.length > 0) {
      $.ajax({
         type: "POST",
         url: baseUrl + "/admin/blog/setArticleIcon?articleId=" + articleId + "&articleIconId=" + iconResourceId,
         xhrFields: {
            withCredentials: true
         },
         success: function(data) {
            location.reload();
         },
         error: function(data) {
            showError("Something bad happened when trying to set icon for article.");
         }
      });
   }
}

var clickRemoveArticleIconBtn = function (articleId, baseUrl) {
   if (articleId != null && articleId.length > 0) {
      $.ajax({
         type: "DELETE",
         url: baseUrl + "/admin/blog/removeArticleIcon?articleId=" + articleId,
         xhrFields: {
            withCredentials: true
         },
         success: function(data) {
            location.reload();
         },
         error: function(data) {
            showError("Something bad happened when trying to remove icon for article.");
         }
      });
   }
}
