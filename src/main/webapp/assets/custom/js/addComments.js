validateAndSubmitComment = function (baseUrl) {
   var errorMsg = [];
   var showError = false;
   var textVal = $("#addCommentForm #articleId").val();
   if (textVal == null || textVal.length <= 0) {
      errorMsg.push("Related article id is null or empty.");
      showError = true;
   }
   
   textVal = $("#addCommentForm #commentTitle").val();
   if (textVal == null || textVal.length <= 0) {
      errorMsg.push("Comment title is null or empty.");
      showError = true;
   }
   
   if (textVal != null && textVal.length > 128) {
      errorMsg.push("Comment title has too many characters.");
      showError = true;
   }

   textVal = $("#addCommentForm #commenterName").val();
   if (textVal != null && textVal.length > 96) {
      errorMsg.push("Commenter's name has too many characters.");
      showError = true;
   }
   
   textVal = $("#addCommentForm #commenterEmail").val();
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
   
   textVal = $("#addCommentForm #commentContent").val();
   if (textVal == null || textVal.length <= 0) {
      errorMsg.push("Comment conent is null or empty.");
      showError = true;
   }
   
   if (textVal != null && textVal.length > 512) {
      errorMsg.push("Comment conent has too many characters.");
      showError = true;
   }
   
   if (!showError && (errorMsg == null || errorMsg.length <= 0)) {
      var articleId = $("#addCommentForm #articleId").val();
      var parentCommentId = $("#addCommentForm #parentCommentId").val();
      var commentTitle = $("#addCommentForm #commentTitle").val();
      var commenterName = $("#addCommentForm #commenterName").val();
      var commenterEmail = $("#addCommentForm #commenterEmail").val();
      var commentContent = $("#addCommentForm #commentContent").val();
      var commentPrivate = $("#addCommentForm #commentPrivate").prop("checked") === true;
      
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
         $("#addCommentForm #addCommentFormSuccess #addCommentFormSuccessMsg").html("Comments are successfuly posted, pending approval.");
         $("#addCommentForm #addCommentFormSuccess").show();

         /*$.ajax({
            type: "POST",
            url: baseUrl + "/public/comments/addArticleComment",
            xhrFields: {
               withCredentials: true
            },
            data: articleCommentObj,
            dataType: "json",
            async:false,
            success: function(data) {
               $("#addCommentForm #addCommentFormSuccess #addCommentFormSuccessMsg").html("Comments are successfuly posted, pending approval.");
               $("#addCommentForm #addCommentFormSuccess").show();
            },
            error: function() {
               $("#addCommentForm #addCommentFormError #addCommentFormErrorMsg").html("Server error occured when posting comment.");
               $("#addCommentForm #addCommentFormError").show();
            }
         });*/
      }
   } else {
      if (errorMsg != null && errorMsg.length > 0) {
         resetCommentEditingErrorDisplay();
         $("#addCommentForm #addCommentFormError #addCommentFormErrorMsg").html(errorMsg[0]);
         $("#addCommentForm #addCommentFormError").show();
      }
   }
};

var resetCommentEditing = function () {
   $("#addCommentForm #commentTitle").val("");
   $("#addCommentForm #commenterName").val("");
   $("#addCommentForm #commenterEmail").val("");
   $("#addCommentForm #commenterContent").val("");
   
   resetCommentEditingErrorDisplay();
} 

var resetCommentEditingErrorDisplay = function () {
   $("#addCommentForm #addCommentFormError #addCommentFormErrorMsg").html("");
   $("#addCommentForm #addCommentFormError").hide();
   
   $("#addCommentForm #addCommentFormSuccess #addCommentFormSuccessMsg").html("");
   $("#addCommentForm #addCommentFormSuccess").hide();
}