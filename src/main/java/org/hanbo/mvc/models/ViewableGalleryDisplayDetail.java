package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class ViewableGalleryDisplayDetail
   extends GalleryDisplayDetail
{
   private List<ImageDisplayDetail> sampleImages;
   
   private ImageDisplayPageDataModel sampleImagesDisplayPage;
   
   public ViewableGalleryDisplayDetail()
   {
      super();
      setSampleImages(new ArrayList<ImageDisplayDetail>());
   }

   public List<ImageDisplayDetail> getSampleImages()
   {
      return sampleImages;
   }

   public void setSampleImages(List<ImageDisplayDetail> sampleImages)
   {
      this.sampleImages = sampleImages;
   }

   public ImageDisplayPageDataModel getSampleImagesDisplayPage()
   {
      return sampleImagesDisplayPage;
   }

   public void setSampleImagesDisplayPage(ImageDisplayPageDataModel sampleImagesDisplayPage)
   {
      this.sampleImagesDisplayPage = sampleImagesDisplayPage;
   }
}
