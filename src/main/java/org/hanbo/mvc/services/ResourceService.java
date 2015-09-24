package org.hanbo.mvc.services;

import org.springframework.web.multipart.MultipartFile;

public interface ResourceService
{
   void saveTextResource(
      String ownerId, String resourceName,
      String resourceSubType, String resourceValue
   );
   
   void saveResourceFile(
      String ownerId,
      String resourceName,
      String subType, MultipartFile fileToSave
   );
   
   //String resourcePath(String type, String resourceId);

   //String resourceFileName(String type, String resourceId, String fileExt);
   
   //void saveToFile(String fileName, InputStream uploadFileStream);
   
   //void saveResource();
}

