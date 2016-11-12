handleClickAddCommentBtn = function (articleId, parentCommentId, baseUrl) {
   resetCommentEditing();
   $("#addCommentDlg #addCommentForm #articleId").val(articleId);
   $("#addCommentDlg #addCommentForm #parentCommentId").val(parentCommentId);
   
   $("#addCommentDlg").modal("show");   
} 

validateAndSubmitComment = function (baseUrl) {
   var errorMsg = [];
   var showError = false;
   var textVal = $("#addCommentDlg #addCommentForm #articleId").val();
   if (textVal == null || textVal.length <= 0) {
      errorMsg.push("Related article id is null or empty.");
      showError = true;
   }
   
   textVal = $("#addCommentDlg #addCommentForm #commentTitle").val();
   if (textVal == null || textVal.length <= 0) {
      errorMsg.push("Comment title is null or empty.");
      showError = true;
   }
   
   if (textVal != null && textVal.length > 128) {
      errorMsg.push("Comment title has too many characters.");
      showError = true;
   }

   textVal = $("#addCommentDlg #addCommentForm #commenterName").val();
   if (textVal != null && textVal.length > 96) {
      errorMsg.push("Commenter's name has too many characters.");
      showError = true;
   }
   
   textVal = $("#addCommentDlg #addCommentForm #commenterEmail").val();
   if (textVal != null && textVal.length > 96) {
      errorMsg.push("Commenter's email has too many characters.");
      showError = true;
   }
   
   if (textVal != null && textVal.length > 0) {
      if (!validateEmail(textVal)) {
         errorMsg.push("Commenter's email is not valid.");
         showError = true;
      }
   }
   
   textVal = $("#addCommentDlg #addCommentForm #commentContent").val();
   if (textVal == null || textVal.length <= 0) {
      errorMsg.push("Comment conent is null or empty.");
      showError = true;
   }
   
   if (textVal != null && textVal.length > 512) {
      errorMsg.push("Comment conent has too many characters.");
      showError = true;
   }
   
   if (!showError && (errorMsg == null || errorMsg.length <= 0)) {
      var articleId = $("#addCommentDlg #addCommentForm #articleId").val();
      var parentCommentId = $("#addCommentDlg #addCommentForm #parentCommentId").val();
      var commentTitle = $("#addCommentDlg #addCommentForm #commentTitle").val();
      var commenterName = $("#addCommentDlg #addCommentForm #commenterName").val();
      var commenterEmail = $("#addCommentDlg #addCommentForm #commenterEmail").val();
      var commentContent = $("#addCommentDlg #addCommentForm #commentContent").val();
      var commentPrivate = $("#addCommentDlg #addCommentForm #commentPrivate").prop("checked") === true;
      
      var articleCommentObj = {
         originId: articleId,
         commentPrivate: commentPrivate,
         parentCommentId: parentCommentId,
         commentTitle: commentTitle,
         commentContent: commentContent, 
         commenterName: commenterName,
         commenterEmail: commenterEmail
      };
      
      if (baseUrl != null) {
         resetCommentEditingErrorDisplay();
         
         $.ajax({
            type: "POST",
            url: baseUrl + "/public/comments/addArticleComment",
            xhrFields: {
               withCredentials: true
            },
            data: JSON.stringify(articleCommentObj),
            dataType: "json",
            contentType: "application/json",
            async:false
         })
         .done(function(data) {
            $("#addCommentDlg #addCommentForm #addCommentFormSuccess #addCommentFormSuccessMsg").html("Comments are successfuly posted, pending approval.");
            $("#addCommentDlg #addCommentForm #addCommentFormSuccess").show();
         }).fail(function() {
            $("#addCommentDlg #addCommentForm #addCommentFormError #addCommentFormErrorMsg").html("Server error occured when posting comment.");
            $("#addCommentDlg #addCommentForm #addCommentFormError").show();
         });
      }
   } else {
      if (errorMsg != null && errorMsg.length > 0) {
         resetCommentEditingErrorDisplay();
         $("#addCommentDlg #addCommentForm #addCommentFormError #addCommentFormErrorMsg").html(errorMsg[0]);
         $("#addCommentDlg #addCommentForm #addCommentFormError").show();
      }
   }
};

var resetCommentEditing = function () {
   $("#addCommentDlg #addCommentForm #commentTitle").val("");
   $("#addCommentDlg #addCommentForm #commenterName").val("");
   $("#addCommentDlg #addCommentForm #commenterEmail").val("");
   $("#addCommentDlg #addCommentForm #commentContent").val("");
   $("#addCommentDlg #addCommentForm #commentPrivate").prop("checked", false);
   
   resetCommentEditingErrorDisplay();
} 

var resetCommentEditingErrorDisplay = function () {
   $("#addCommentDlg #addCommentForm #addCommentFormError #addCommentFormErrorMsg").html("");
   $("#addCommentDlg #addCommentForm #addCommentFormError").hide();
   
   $("#addCommentDlg #addCommentForm #addCommentFormSuccess #addCommentFormSuccessMsg").html("");
   $("#addCommentDlg #addCommentForm #addCommentFormSuccess").hide();
}

var loadParentComment = function (articleId, parentCommentId, divId, baseUrl) {
  if (articleId != null && articleId.length > 0 &&
     parentCommentId != null && parentCommentId.length > 0 &&
     divId != null && divId.length > 0)
  {
     $.ajax({
        type: "GET",
        url: baseUrl + "/public/comments/loadParentComment/" + parentCommentId,
        xhrFields: {
           withCredentials: true
        },
        dataType: "json",
        contentType: "application/json",
        async:false
     })
     .done(function(data) {
        
     }).fail(function() {
     });
  }
   
}