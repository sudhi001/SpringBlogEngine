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
            console.log("Call successful");
         },
         error: function(data) {
            alert(JSON.stringify(data));
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


