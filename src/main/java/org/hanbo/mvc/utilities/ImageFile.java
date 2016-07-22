package org.hanbo.mvc.utilities;

import org.apache.log4j.*;

public class ImageFile
{
   private Logger _logger = LogManager.getLogger(ImageFile.class);
  
   private ImageFileProcessingUtil processingUtil;
   
   private String originalFile;
   private String resizedFile;
   
   private String imageFormat;
   private int resizedImageWidth;
   
   private int originalFileWidth;
   private int originalFileHeight;
   private int resizedFileWidth;
   private int resizedFileHeight;
   private float aspectRatio;
   
   public ImageFile(ImageFileProcessingUtil processingUtil)
   {
      this.processingUtil = processingUtil;
   }

   public void setOriginalFile(String filename)
   {
      this.originalFile = filename;
   }
   
   public void setResizeFile(String filename)
   {
      this.resizedFile = filename;
   }
   
   public void setResizedImageWidth(int newWidth)
   {
      this.resizedImageWidth = newWidth;
   }
   
   public int getOriginalImageFileSizeX()
   {
      return originalFileWidth;
   }
   
   public int getOriginalImageFileSizeY()
   {
      return originalFileHeight;
   }
   
   public int getResizedFileSizeX()
   {
      return this.resizedFileWidth;
   }
   
   public int getResizedFileSizeY()
   {
      return this.resizedFileHeight;
   }

   public void imageFileDimensions()
      throws Exception
   {
      originalFileWidth = 0;
      originalFileHeight = 0;
      
      if (processingUtil != null)
      {
         String imageInfo 
            = processingUtil.getImageInfo(this.originalFile);
         if (imageInfo != null && imageInfo.length() > 0)
         {
            String[] infoParts = imageInfo.split("\\s");
            if (infoParts.length >= 3)
            {
               imageFormat = infoParts[0];
               originalFileWidth = tryParseInt(infoParts[1]);
               originalFileHeight = tryParseInt(infoParts[2]);
            }
         }
      }
      _logger.debug(String.format(" +++ orig img dimension: %d %d", this.originalFileWidth, this.originalFileHeight));
   }
   
   public void determineAspectRatio()
   {
      if (originalFileWidth > originalFileHeight)
      {
         this.aspectRatio
            = ((float)this.originalFileWidth / (float)this.originalFileHeight);
      }
      else
      {
         this.aspectRatio
            = ((float)this.originalFileHeight / (float)this.originalFileWidth);
      }
      
      _logger.debug(String.format("orig img aspect: %f", this.aspectRatio));
   }
   
   public void calculateNewFileDimensions(boolean useAspectRatio)
   {
      this.resizedFileWidth = this.resizedImageWidth;
      this.resizedFileHeight = useAspectRatio?
         (int)(this.resizedFileWidth / this.aspectRatio)
         : this.resizedImageWidth;

      _logger.debug(String.format("orig img dimension: %d %d", this.resizedFileWidth, this.resizedFileHeight));
   }
   
   public boolean resizeImageFile()
   {
      boolean retVal = false;
      if (this.processingUtil != null)
      {
         retVal = processingUtil.resizeImage(
            originalFile, resizedFile,
            true, true, 250, 250);
      }
      
      
      return retVal;
   }

   public String getImageFormat()
   {
      return imageFormat;
   }

   public void setImageFormat(String imageFormat)
   {
      this.imageFormat = imageFormat;
   }
   
   private static int tryParseInt(String intStrVal)
   {
      int retVal;
      try
      {
         retVal = Integer.parseInt(intStrVal);
      }
      catch(Exception e)
      {
         retVal = 0;
      }
      return retVal;
   }
}
