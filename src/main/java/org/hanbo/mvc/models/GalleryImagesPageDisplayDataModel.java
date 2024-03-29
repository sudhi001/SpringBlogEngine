package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class GalleryImagesPageDisplayDataModel
   extends ItemListPageDataModel
{
   private GalleryDisplayDetail galleryDetail;
   
   private List<ImageDisplayDetail> imagesPageList;
   
   public GalleryImagesPageDisplayDataModel()
   {
      setImagesPageList(new ArrayList<ImageDisplayDetail>());
   }

   public GalleryDisplayDetail getGalleryDetail()
   {
      return galleryDetail;
   }

   public void setGalleryDetail(GalleryDisplayDetail galleryDetail)
   {
      this.galleryDetail = galleryDetail;
   }
   
   public List<ImageDisplayDetail> getImagesPageList()
   {
      return imagesPageList;
   }

   public void setImagesPageList(List<ImageDisplayDetail> imagesPageList)
   {
      this.imagesPageList = imagesPageList;
   }
}
