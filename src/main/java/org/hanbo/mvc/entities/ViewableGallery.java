package org.hanbo.mvc.entities;

import java.util.ArrayList;
import java.util.List;

public class ViewableGallery
{
   private Gallery gallery;

   private List<Image> sampleImages;
   
   public ViewableGallery()
   {
      sampleImages = new ArrayList<Image>();
   }

   public Gallery getGallery()
   {
      return gallery;
   }

   public void setGallery(Gallery gallery)
   {
      this.gallery = gallery;
   }

   public List<Image> getSampleImages()
   {
      return sampleImages;
   }

   public void setSampleImages(List<Image> sampleImages)
   {
      this.sampleImages = sampleImages;
   }
}
