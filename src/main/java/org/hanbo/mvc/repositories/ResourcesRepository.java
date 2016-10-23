package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.FileResource;
import org.hanbo.mvc.entities.Resource;
import org.hibernate.Session;

public interface ResourcesRepository
{
   void saveResource(Resource resToSave);
   
   List<Resource> getResourcesByOwnerId(
      String ownerId, int pageIdx, int itemsCount);
   
   long getResourceCountByOwnerId(String ownerId);
   
   Resource getResourceByOwner(String resourceId, String ownerId);
   
   List<Resource> getResourcesByTypeAndOwnerId(
      String ownerId, String resourceType,
      int pageIdx, int itemsCount);
   
   List<FileResource> getImageResourcesByOwnerId(
      String ownerId, int pageIdx, int itemsCount);
   
   FileResource getImageResourceById(
      Session session, String ownerId, String fileId);
   
   FileResource getImageResourceById(
      Session session, String fileId);
   
   void deleteResource(Resource resource);

   List<FileResource> getUserSquareImageReosurces(String ownerId, int pageIdx, int itemsCountVal);
}
