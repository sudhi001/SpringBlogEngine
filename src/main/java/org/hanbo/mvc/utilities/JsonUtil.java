package org.hanbo.mvc.utilities;

import com.google.gson.Gson;

public class JsonUtil
{
   public static String convertObjectToJson(Object obj)
   {
      Gson gson = new Gson();
      String retVal = gson.toJson(obj);
      
      return retVal;
   }
   
}
