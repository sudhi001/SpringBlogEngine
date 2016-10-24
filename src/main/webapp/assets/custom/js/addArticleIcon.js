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
            console.log(JSON.stringify(data));
            if (data != null) {
               if (data.currentPageIdx >= 0 &&
                  data.articleIconList != null &&
                  data.articleIconList.length > 0) {
                  addArticleIconToUI(data.articleIconList, baseUrl);
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
}

var addArticleIconToUI = function (articleIconList, baseUrl) {
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
            "onclick": ""
         }).append(iconImg);
         
         var imageDiv = $("<div>", {
            "class": "col-xs-3",
            "style": "max-width: 118px;"
         }).append(iconHref);
         
         articleIcons.push(imageDiv);
      }
      
      if (articleIcons.length > 0) {
         $("#addArticleIconDlg #addArticleIconForm #addArticleIconGallery").append(articleIcons);
      }
   }
}
