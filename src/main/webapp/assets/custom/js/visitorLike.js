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
            if (data != null) {
               $("#likeSpan").html(data.likeCount + " like(s),");
               $("#dislikeSpan").html(data.dislikeCount + " dislike(s),");
            }
         },
         error: function(data) {
         }
      });
   }
};

var getArticleVisitorLikesCount = function(articleLikeUrl) {
   if (articleLikeUrl != null) {
      $.ajax({
         type: "GET",
         url: articleLikeUrl,
         xhrFields: {
            withCredentials: true
         },
         async: false,
         success: function(data) {
            if (data != null) {
               $("#likeSpan").html(data.likeCount + " like(s),");
               $("#dislikeSpan").html(data.dislikeCount + " dislike(s),");
            }
         },
         error: function(data) {
         }
      });
   }
};

var addImageVisitorLike = function(imageLikeUrl, likeIt) {
   if (imageLikeUrl != null) {
      var likeItReq = {
         like: likeIt
      };
      
      $.ajax({
         type: "POST",
         url: imageLikeUrl,
         xhrFields: {
            withCredentials: true
         },
         data: likeItReq,
         async: false,
         success: function(data) {
            if (data != null) {
               $("#likeSpan").html(data.likeCount + " like(s),");
               $("#dislikeSpan").html(data.dislikeCount + " dislike(s),");
            }
         },
         error: function(data) {
         }
      });
   }
};

var getImageVisitorLikesCount = function(imageLikeUrl) {
   if (imageLikeUrl != null) {
      $.ajax({
         type: "GET",
         url: imageLikeUrl,
         xhrFields: {
            withCredentials: true
         },
         async: false,
         success: function(data) {
            if (data != null) {
               $("#likeSpan").html(data.likeCount + " like(s),");
               $("#dislikeSpan").html(data.dislikeCount + " dislike(s),");
            }
         },
         error: function(data) {
         }
      });
   }
};