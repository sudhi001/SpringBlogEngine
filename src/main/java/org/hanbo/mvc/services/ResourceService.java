package org.hanbo.mvc.services;

import java.io.OutputStream;
import java.util.List;

import org.hanbo.mvc.models.ResourceListItemDataModel;
import org.hanbo.mvc.models.ResourceListPageDataModel;
import org.hanbo.mvc.models.json.ImageResourceJsonResponse;
import org.hanbo.mvc.models.json.TextResourceJsonResponse;
import org.hanbo.mvc.models.json.ResourcesListJsonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService
{
   void saveTextResource(
      String ownerId, String resourceName,
      String resourceSubType, String resourceValue
   );
   
   void updateTextResource(
      String ownerId, String resourceId, String resourceName,
      String resourceSubType, String resourceValue
   );
   
   void saveResourceFile(
      String ownerId,
      String resourceName,
      String subType, MultipartFile fileToSave
   );
   
   String saveResourceFileWithId(
      String ownerId,
      String resourceName,
      String subType, MultipartFile fileToSave
   );
  
   List<ResourceListItemDataModel> getOwnerResources(
      String ownerId, int pageIdx);

   ResourceListPageDataModel getOwnerResourcesPage(
      String ownerId, int pageIdx);
   
   boolean downloadResource(
      String resourceId, String resourceSubType,
      OutputStream outStream) throws Exception;
   
   void deleteResource(String resourceId, String ownerId);
   
   TextResourceJsonResponse getTextResource(String resourceId, String ownerId);
   
   TextResourceJsonResponse getFormattedTextResource(String resourceId, String ownerId);
   
   ImageResourceJsonResponse getFormattedImageResource(String resourceId, String ownerId);
   
   ResourcesListJsonResponse getTextResourcesList(String userId, int pageIdx);

   ResourcesListJsonResponse getImageResourcesList(String userId, int pageIdx);
}

