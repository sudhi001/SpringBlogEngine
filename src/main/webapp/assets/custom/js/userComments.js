var displayCommentDetail = function(articleId, commentId, baseUrl) {
   if (articleId != null && articleId.length > 0 &&
      commentId != null && commentId.length > 0)
   {
      var loadCommentParam = {
         articleId: articleId,
         commentId: commentId
      };
      $.ajax({
         type: "GET",
         url: baseUrl + "/public/comments/loadComment/",
         xhrFields: {
            withCredentials: true
         },
         data: loadCommentParam,
         dataType: "json",
         contentType: "application/json",
         async:false
      })
      .done(function(data) {
         if (data != null) {
            $("#commentDetailDlg #commentTitle").html(data.commentTitle);
            $("#commentDetailDlg #commentContent").html(data.commentContent);
            $("#commentDetailDlg").modal("show");
         }
      }).fail(function() {
      });
   }
}