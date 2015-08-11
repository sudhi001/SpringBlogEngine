package org.hanbo.mvc.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader
{
   public static Properties loadPropertiesFromClassPath(String propFileName)
   {
      InputStream inStream = null;
      try
      {
          Properties retProp = new Properties();
          inStream = PropertiesLoader.class.getResourceAsStream(propFileName);
          retProp.load(inStream);
          
          return retProp;
      }
      catch (FileNotFoundException e)
      {
         return null;
      }
      catch (IOException e)
      {
         return null;
      }
   }  
}
