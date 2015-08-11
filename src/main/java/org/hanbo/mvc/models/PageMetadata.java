package org.hanbo.mvc.models;

import java.util.Properties;

import org.hanbo.mvc.utilities.PropertiesLoader;

public class PageMetadata
{
   private static Properties siteInfo;
   
   private String siteTitle;
   private String pageTitle;
   
   static
   {
      siteInfo =
      PropertiesLoader.loadPropertiesFromClassPath(
         "/site.properties"
      );
   }
   
   protected static String getSiteInfoString(String keyName)
   {
      if (siteInfo == null)
      {
         throw new IllegalArgumentException(
            String.format("Site info properties file [%s] not found", "/site.properties")
         );
      }
      
      if (!siteInfo.containsKey(keyName))
      {
         throw new IllegalArgumentException(
            String.format("Keyname [%s] not found in the site info properies file", keyName));
      }
      
      return siteInfo.getProperty(keyName);
   }
   
   public PageMetadata()
   {
      this.siteTitle = getSiteInfoString("site.title");
   }

   public String getSiteTitle()
   {
      return siteTitle;
   }
   
   public String getPageTitle()
   {
      return pageTitle;
   }

   public void setPageTitle(String pageTitle)
   {
      this.pageTitle = pageTitle;
   }
}
