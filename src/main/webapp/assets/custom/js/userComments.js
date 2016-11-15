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
};

var approveComment = function (articleId, commentId, baseUrl) {
   var approveCommentParam = {
      articleId: articleId,
      commentId: commentId
   };
   $.ajax({
      type: "PUT",
      url: baseUrl + "/admin/comments/approveComment",
      xhrFields: {
         withCredentials: true
      },
      data: JSON.stringify(approveCommentParam),
      dataType: "json",
      contentType: "application/json",
      async:false
   })
   .done(function(data) {
      if (data != null) {
         location.reload();
      }
   }).fail(function() {
   });
};

var adminDeleteComment = function (articleId, commentId, baseUrl) {
   var deleteCommentParam = {
      articleId: articleId,
      commentId: commentId
   };
   $.ajax({
      type: "DELETE",
      url: baseUrl + "/admin/comments/deleteComment",
      xhrFields: {
         withCredentials: true
      },
      data: JSON.stringify(deleteCommentParam),
      dataType: "json",
      contentType: "application/json",
      async:false
   })
   .done(function(data) {
      if (data != null) {
         location.reload();
      }
   }).fail(function() {
   });
};