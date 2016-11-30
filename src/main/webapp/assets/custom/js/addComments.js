handleClickAddCommentBtn = function (refObjectId, parentCommentId, baseUrl) {
   resetCommentEditing();
   $("#addCommentDlg #addCommentForm #refObjectId").val(refObjectId);
   $("#addCommentDlg #addCommentForm #parentCommentId").val(parentCommentId);
   
   $("#addCommentDlg").modal("show");   
} 

validateAndSubmitComment = function (addCommentUrl) {
   var errorMsg = [];
   var showError = false;
   var textVal = $("#addCommentDlg #addCommentForm #refObjectId").val();
   if (textVal == null || textVal.length <= 0) {
      errorMsg.push("Related refObject id is null or empty.");
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
      var refObjectId = $("#addCommentDlg #addCommentForm #refObjectId").val();
      var parentCommentId = $("#addCommentDlg #addCommentForm #parentCommentId").val();
      var commentTitle = $("#addCommentDlg #addCommentForm #commentTitle").val();
      var commenterName = $("#addCommentDlg #addCommentForm #commenterName").val();
      var commenterEmail = $("#addCommentDlg #addCommentForm #commenterEmail").val();
      var commentContent = $("#addCommentDlg #addCommentForm #commentContent").val();
      var commentPrivate = $("#addCommentDlg #addCommentForm #commentPrivate").prop("checked") === true;
      
      var commentObj = {
         originId: refObjectId,
         commentPrivate: commentPrivate,
         parentCommentId: parentCommentId,
         commentTitle: commentTitle,
         commentContent: commentContent, 
         commenterName: commenterName,
         commenterEmail: commenterEmail
      };
      
      if (addCommentUrl != null) {
         resetCommentEditingErrorDisplay();
         
         $.ajax({
            type: "POST",
            url: addCommentUrl,
            xhrFields: {
               withCredentials: true
            },
            data: JSON.stringify(commentObj),
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

var loadArticleComment = function (articleId, parentCommentId, divId, baseUrl) {
  if (articleId != null && articleId.length > 0 &&
     parentCommentId != null && parentCommentId.length > 0 &&
     divId != null && divId.length > 0)
  {
     var pCommentId = $(divId + " input:hidden").val();
     if (pCommentId != null && pCommentId === parentCommentId)
     {
        $(divId).empty();
     }
     else
     {
        var loadCommentParam = {
           articleId: articleId,
           commentId: parentCommentId
        };
        
        $.ajax({
           type: "GET",
           url: baseUrl + "/public/comments/loadArticleComment/",
           xhrFields: {
              withCredentials: true
           },
           data: loadCommentParam,
           dataType: "json",
           contentType: "application/json",
           async:false
        })
        .done(function(data) {
           if (data) {
              var hidden = $("<input>", {
                 "type": "hidden",
                 "value": data.commentId
              });
              var h3 = $("<h4>", {
                 "class": "margin-updown-tiny"
              }).append(data.commentTitle)
              var commentDate = $("<span>", { 
                 "class": "text-muted pull-right"
              }).append(data.commentUpdateDate);
              var author = $("<strong>");
              if (data.commentUserFullName != null && data.commentUserFullName.length > 0)
              {
                 author.append(data.commentUserFullName);
              }
              else if (data.commentUserName != null && data.commentUserName.length > 0)
              {
                 author.append(data.commentUserName);
              }
              else if (data.commenterName != null && data.commenterName.length > 0)
              {
                 author.append(data.commenterName);
              }
              author.append(commentDate);
              author.append($("<hr>", { "class": "margin-updown-tiny"}))
              var p = $("<p>", {
                 "class": "margin-updown-tiny"
              }).append(data.commentContent);
              var panelBody = $("<div>", {
                 "class": "panel-body panelbody-updown-paddingtiny"
              }).append(hidden).append(author).append(h3).append(p);
              var panelDiv = $("<div>", {
                 "class": "panel panel-default"
              }).append(panelBody);
              var divCol = $("<div>", {
                 "class": "col-xs-12"
              }).append(panelDiv);
              $(divId).append(divCol);
           }
        }).fail(function() {
        });
     }
  }
}