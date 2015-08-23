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
   
   public static String simpleErrorMessage(String errMsg)
   {
      String retVal = String.format(
         "{ 'ErrorMessage' : '%s' }", errMsg
      );
      
      return retVal;
   }
   
}
