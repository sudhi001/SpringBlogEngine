var handleClickChangeUserIconBtn = function() {
   resetChangeUserIconDlg();
   $("#addUserIconDlg").modal("show");
};

$('#addUserIconDlg').on('hidden.bs.modal', function () {
   resetChangeUserIconDlg();
});

var resetChangeUserIconDlg = function() {
   $("#addUserIconDlg #addUserIconForm #addUserIconErrorMsg").html("");
   $("#addUserIconDlg #addUserIconForm #addUserIconError").hide();
   $("#addUserIconDlg #addUserIconForm")[0].reset();
};

var validateUserIconData = function() {
   var errorMsgs = [];
   var showError = false;
   
   var iconName = $("#addUserIconDlg #addUserIconForm #userIconName").val();
   if (iconName == null || iconName.length <= 0) {
      errorMsgs.push("User icon name is null or empty");
      showError = true;
   }
   
   if (iconName != null && iconName.length > 96) {
      errorMsgs.push("User icon name is too long, max 96 characters");
      showError = true;      
   }
   
   var userIconFile = $("#addUserIconDlg #addUserIconForm #userIconToUpload").val();
   if (userIconFile == null || userIconFile.length <= 0) {
      errorMsgs.push("User icon file is empty");
      showError = true;
   }
   
   if (showError) {
      if (errorMsgs && errorMsgs.length > 0) {
         $("#addUserIconDlg #addUserIconForm #addUserIconErrorMsg").html(errorMsgs[0]);
      }
      else {
         $("#addUserIconDlg #addUserIconForm #addUserIconErrorMsg").html("Unknown error occurred");
      }
      $("#addUserIconDlg #addUserIconForm #addUserIconError").show();
      
      return false;
   }
   else {
      $("#addUserIconDlg #addUserIconForm")[0].submit();
      return true;
   }
}