package org.hanbo.mvc.models.json;

public class CommentJsonDataModel
{
   private String commentTitle;
   
   private String commentContent;

   public String getCommenTitle()
   {
      return commentTitle;
   }

   public void setCommenTitle(String commentTitle)
   {
      this.commentTitle = commentTitle;
   }

   public String getCommentContent()
   {
      return commentContent;
   }

   public void setCommentContent(String commentContent)
   {
      this.commentContent = commentContent;
   }
}
