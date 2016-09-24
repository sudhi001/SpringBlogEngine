var showPostBlogForImage = function ()
{
   $("#imageBlogPostPanel").show();
   $("#ceateBlogForImageBtn").hide();
};

var resetForPostBlog = function(imageId)
{
   $("#imageBlogPostForm #postImageId").val(imageId);
   $("#imageBlogPostForm #blogTitle").val("");
   $("#imageBlogPostForm #blogKeywords").val("");
   $("#imageBlogPostForm #blogContent").val("");
   
   $("#imageBlogPostForm #imageBlogPostError #imageBlogPostErrorMsg").html("");
   $("#imageBlogPostForm #imageBlogPostError").hide();
   
   $("#imageBlogPostPanel").hide();
   $("#ceateBlogForImageBtn").show();
};

var validateBlogImagePost = function()
{
   var validateFailed = false;
   var errorMsg = "";
   var blogTitle = $("#imageBlogPostForm #blogTitle").val();
   if (!blogTitle || blogTitle.length <= 0)
   {
      validateFailed = true;
      errorMsg = "Blog title is null or empty string";
   }

   if (blogTitle && blogTitle.length > 128)
   {
      validateFailed = true;
      errorMsg = "Blog title has too many characters";
   }

   var blogKeywords = $("#imageBlogPostForm #blogKeywords").val();
   if (blogKeywords && blogKeywords.length > 128)
   {
      validateFailed = true;
      errorMsg = "Blog keywords has too many characters";
   }
   
   var blogContent = $("#imageBlogPostForm #blogContent").val();
   if (blogContent && blogContent.length > 256)
   {
      validateFailed = true;
      errorMsg = "Blog content only allows 256 characters";
   }
   
   if (!validateFailed)
   {
      $("#imageBlogPostForm")[0].submit();
      return true;
   }
   else
   {
      $("#imageBlogPostForm #imageBlogPostError #imageBlogPostErrorMsg").html(errorMsg);
      $("#imageBlogPostForm #imageBlogPostError").show();
      return false;
   }
}