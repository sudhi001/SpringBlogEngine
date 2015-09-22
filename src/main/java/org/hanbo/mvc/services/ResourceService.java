package org.hanbo.mvc.services;

import java.io.InputStream;

public interface ResourceService
{
   void saveTextResource(
      String ownerId, String resourceName,
      String resourceSubType, String resourceValue
   );
   
   //String resourcePath(String type, String resourceId);

   //String resourceFileName(String type, String resourceId, String fileExt);
   
   //void saveToFile(String fileName, InputStream uploadFileStream);
   
   //void saveResource();
}

