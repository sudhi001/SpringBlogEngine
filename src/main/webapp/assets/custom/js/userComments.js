var displayCommentDetail = function(commentId, baseUrl) {
   if (commentId != null && commentId.length > 0)
   {
      var loadCommentParam = {
         commentId: commentId
      };
      $.ajax({
         type: "GET",
         url: baseUrl + "/admin/comments/loadComment",
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
            $("#commentDetailDlg #commenterName").html(data.commenterName);
            $("#commentDetailDlg #commenterEmail").html(data.commenterEmail);
            $("#commentDetailDlg #commentContent").html(data.commentContent);
            $("#commentDetailDlg").modal("show");
         }
      }).fail(function() {
      });
   }
};

var approveComment = function (commentId, baseUrl) {
   var approveCommentParam = {
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

var adminDeleteComment = function (commentId, baseUrl) {
   var deleteCommentParam = {
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