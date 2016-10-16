var validateUserProfileEdit = function(){
   var errorMsg = [];
   var showError = false;
   
   var firstName = $("#userProfileEditForm #userFirstName").val();
   if (firstName == null || firstName.length <= 0) {
      showError = true;
      errorMsg.push("User first name is null or empty");
   }
   
   if (firstName != null && firstName.length > 64) {
      showError = true;
      errorMsg.push("User first name is too long, max 64 characters");      
   }
   
   var lastName = $("#userProfileEditForm #userLastName").val();
   if (lastName == null || lastName.length <= 0) {
      showError = true;
      errorMsg.push("User last name is null or empty");
   }
   
   if (lastName != null && lastName.length > 64) {
      showError = true;
      errorMsg.push("User last name is too long, max 64 characters");
   }
    
   if ($("#userProfileEditForm #genderMale").prop("checked") !== true &&
       $("#userProfileEditForm #genderFemale").prop("checked") !== true) {
      showError = true;
      errorMsg.push("You must select a gender value");
   }
   
   var userAge = $("#userProfileEditForm #userAge").val();
   if (userAge != null) {
      var ageIntVal = parseInt(userAge);
      if (ageIntVal <= 18 || ageIntVal > 120) {
         showError = true;
         errorMsg.push("User age is invalid. Must be between 18 and 120");
      }
   }
   
   var userLocation = $("#userProfileEditForm #userLocation").val();
   if (userLocation != null && userLocation.length > 128) {
      showError = true;
      errorMsg.push("User location is too long, max 128 characters");
   }
   
   var userProfession = $("#userProfileEditForm #userProfession").val();
   if (userProfession != null && userProfession.length > 128) {
      showError = true;
      errorMsg.push("User profession is too long, max 128 characters");
   }
   
   var userIntroduction = $("#userProfileEditForm #userIntroduction").val();
   if (userIntroduction != null && userIntroduction.length > 4096) {
      showError = true;
      errorMsg.push("User introduction is too long, max 4096 characters");
   }
   
   if (showError) {
      if (errorMsg && errorMsg.length > 0) {
         $("#userProfileEditForm #editUserProfileError #editUserProfileErrorMsg").html(errorMsg[0]);
      }
      else {
         $("#userProfileEditForm #editUserProfileError #editUserProfileErrorMsg").html("Unknown error occurred.");
      }
      $("#userProfileEditForm #editUserProfileError").show();
      return false;
   } else {
      $("#userProfileEditForm")[0].submit();
      return true;
   }
};

var resetUserProfileEdit = function() {
   $("#userProfileEditForm #editUserProfileError #editUserProfileErrorMsg").html("");
   $("#userProfileEditForm #editUserProfileError").show();
   
   $("#userProfileEditForm")[0].reset();
};