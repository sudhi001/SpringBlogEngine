package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class ViewableGalleriesPageDataModel
   extends ItemListPageDataModel
{
   private List<ViewableGalleryDisplayDetail> galleryList;
   
   public ViewableGalleriesPageDataModel() 
   {
      super();
      setGalleryList(new ArrayList<ViewableGalleryDisplayDetail>());
   }

   public List<ViewableGalleryDisplayDetail> getGalleryList()
   {
      return galleryList;
   }

   public void setGalleryList(List<ViewableGalleryDisplayDetail> galleryList)
   {
      this.galleryList = galleryList;
   }
}
