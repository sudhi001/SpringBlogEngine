package org.hanbo.mvc.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

public class FileStreamUtil
{
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
