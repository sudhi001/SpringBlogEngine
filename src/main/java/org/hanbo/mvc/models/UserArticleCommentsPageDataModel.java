package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class UserArticleCommentsPageDataModel
   extends ItemListPageDataModel
{
   private List<ArticleCommentDataModel> commentsList;
   
   public UserArticleCommentsPageDataModel()
   {
      setCommentsList(new ArrayList<ArticleCommentDataModel>());
   }

   public List<ArticleCommentDataModel> getCommentsList()
   {
      return commentsList;
   }

   public void setCommentsList(List<ArticleCommentDataModel> commentsList)
   {
      this.commentsList = commentsList;
   }
}
