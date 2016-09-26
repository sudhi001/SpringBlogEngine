package org.hanbo.mvc.models;

public class ImageSizeDataModel
{
   private String imageId;
   private int imageSizeX;
   private int imageSizeY;
   
   private float widthToHeightRatio;
   
   public ImageSizeDataModel()
   {
      setImageId("");
      setImageSizeX(0);
      setImageSizeY(0);
      setWidthToHeightRatio(0.0f);
   }

   public String getImageId()
   {
      return imageId;
   }

   public void setImageId(String imageId)
   {
      this.imageId = imageId;
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
