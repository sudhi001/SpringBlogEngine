package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.FileResource;
import org.hanbo.mvc.entities.Resource;

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
   
   void deleteResource(Resource resource);
   
   Resource getArticleIcon(String articleId);
   
   List<FileResource> getIconResources(
      String ownerId, int pageIdx, int itemsCount);
   
   void setIconToArticle(String articleId, String resourceId);
   
   boolean deleteIconToArticle(String articleId);
}
