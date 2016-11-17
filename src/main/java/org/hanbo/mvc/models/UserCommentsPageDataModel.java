package org.hanbo.mvc.models;

import java.util.List;

public class UserCommentsPageDataModel
   extends ItemListPageDataModel
{
   private List<VisitorCommentDataModel> userComments;

   public List<VisitorCommentDataModel> getUserComments()
   {
      return userComments;
   }

   public void setUserComments(List<VisitorCommentDataModel> userComments)
   {
      this.userComments = userComments;
   }
}
