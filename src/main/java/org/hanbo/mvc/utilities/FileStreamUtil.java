package org.hanbo.mvc.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.hanbo.mvc.exceptions.WebAppException;

public class FileStreamUtil
{
   public static void directoryPathExists(String directoryPath)
   {
      File f = new File(directoryPath);
      if (!f.exists())
      {
         if (!f.mkdir())
         {
            throw new WebAppException(String.format("Unable to create directory [%s]", directoryPath), WebAppException.ErrorType.FUNCTIONAL);
         }
      }
   }
   
   public static void saveFileToServer(
      String fileNameToSave,
      InputStream dataStream
   ) throws Exception
   {
      File fileToSave = new File(fileNameToSave);
      FileOutputStream fos = new FileOutputStream(fileToSave);
      
      IOUtils.copy(dataStream, fos);

      fos.flush();
      fos.close();
   }
   
   public static void readFileToOutputStream(
      String imgFilePath, OutputStream respStream
   ) throws Exception
   {
      File imageFile = new File(imgFilePath);
      FileInputStream fis = new FileInputStream(imageFile);
         
      IOUtils.copy(fis, respStream);
      
      fis.close();
      respStream.close();
   }
   
}
