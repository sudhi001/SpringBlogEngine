package org.hanbo.mvc.models.json;

public class SearchUserPhotoResponse
{
   private String imageId;
   private String imageTitle;
   private int imageSizeX;
   private int imageSizeY;
   private float widthToHeightRatio;
   
   public String getImageId()
   {
      return imageId;
   }
   
   public void setImageId(String imageId)
   {
      this.imageId = imageId;
   }
   
   public String getImageTitle()
   {
      return imageTitle;
   }
   
   public void setImageTitle(String imageTitle)
   {
      this.imageTitle = imageTitle;
   }
   
   public int getImageSizeX()
   {
      return imageSizeX;
   }
   
   public void setImageSizeX(int imageSizeX)
   {
      this.imageSizeX = imageSizeX;
   }
   
   public int getImageSizeY()
   {
      return imageSizeY;
   }
   
   public void setImageSizeY(int imageSizeY)
   {
      this.imageSizeY = imageSizeY;
   }
   
   public float getWidthToHeightRatio()
   {
      return widthToHeightRatio;
   }
   
   public void setWidthToHeightRatio(float widthToHeightRatio)
   {
      this.widthToHeightRatio = widthToHeightRatio;
   }
}
