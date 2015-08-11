package org.hanbo.mvc.utilities;

public class ImageFileNameUtil
{
   public static String imageFileName(String originalFileName)
   {
      int fileextpos = originalFileName.lastIndexOf(".");
      if (fileextpos >= 0 && fileextpos < originalFileName.length())
      {
         String ext = originalFileName.substring(fileextpos);
         String filename = "image-" + DateToString.currentDateString("yyyyMMdd-HHmmssSSS") + ext;
         
         return filename;
      }
      
      throw new IllegalArgumentException(
         "Invalid image file name [" +  originalFileName + "]"
      );
   }
   
   public static String imageFileNameAddSuffix(String originalFileName, String suffix)
   {
      int fileextpos = originalFileName.lastIndexOf(".");
      if (fileextpos >= 0 && fileextpos < originalFileName.length())
      {
         String prefix = originalFileName.substring(0, fileextpos-1);
         String filename = prefix + "-" + suffix;
         
         return filename;
      }
      
      throw new IllegalArgumentException(
         "Invalid image file name [" +  originalFileName + "]"
      );
   }

   public static String imageThumbnailName(String imgFileName)
   {
      int fileextpos = imgFileName.lastIndexOf(".");
      if (fileextpos >= 0 && fileextpos < imgFileName.length())
      {
         String filename = imgFileName.substring(0, fileextpos);
         filename = filename + "_thumb.png";
         
         return filename;
      }
      
      throw new IllegalArgumentException(
         "Invalid image file name [" +  imgFileName + "]"
      );
   }
   
   public static String imageFilePath(
      String baseDir,
      String userName,
      String galleryDir,
      String imageName
   )
   {
      StringBuilder sb = new StringBuilder();
      sb.append(baseDir);
      sb.append("/");
      sb.append(userName);
      sb.append("/");
      sb.append(galleryDir);
      sb.append("/");
      sb.append(imageName);
      
      return sb.toString();
   }   
}
