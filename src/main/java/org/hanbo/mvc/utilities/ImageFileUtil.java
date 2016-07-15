package org.hanbo.mvc.utilities;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.im4java.process.ProcessStarter;
import org.apache.log4j.*;

public class ImageFileUtil
{
   // local test setting: 
   private static String imageMagickPath = "/apache/ImageMagick-7.0.2-4";
   
   // prod setting: 
   //private static String imageMagickPath = "/usr/bin";
   
   private Logger _logger = LogManager.getLogger(ImageFileUtil.class);
  
   private String originalFile;
   private String resizedFile;
   
   private int resizedImageWidth;
   
   private int originalFileWidth;
   private int originalFileHeight;
   private int resizedFileWidth;
   private int resizedFileHeight;
   private float aspectRatio;
   
   public ImageFileUtil()
   {
      ProcessStarter.setGlobalSearchPath(imageMagickPath);
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
      Info imageInfo = new Info(this.originalFile,true);
      this.originalFileWidth = imageInfo.getImageWidth();
      this.originalFileHeight = imageInfo.getImageHeight();
      
      _logger.debug(String.format("orig img dimension: %d %d", this.originalFileWidth, this.originalFileHeight));
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
   
   public void resizeImageFile()
      throws Exception
   {
      ConvertCmd cmd = new ConvertCmd();

      IMOperation op = new IMOperation();
      op.addImage(this.originalFile);
      op.resize(this.resizedFileWidth, this.resizedFileHeight);
      op.background("none");
      op.gravity("center");
      op.extent(this.resizedFileWidth, this.resizedFileHeight);
      op.addImage(this.resizedFile);

      // execute the operation
      cmd.run(op);
   }
}
