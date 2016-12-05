var addArticleVisitorLike = function(articleLikeUrl, likeIt) {
   if (articleLikeUrl != null) {
      var likeItReq = {
         like: likeIt
      };
      
      $.ajax({
         type: "POST",
         url: articleLikeUrl,
         xhrFields: {
            withCredentials: true
         },
         data: likeItReq,
         async: false,
         success: function(data) {
            $("#likeSpan").html(data.likeCount + " like(s),")
            $("#dislikeSpan").html(data.dislikeCount + " dislike(s),")
         },
         error: function(data) {
         }
      });
   }
};