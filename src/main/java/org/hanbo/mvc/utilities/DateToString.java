package org.hanbo.mvc.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToString
{
   public static String dateToString(Date dateObj, String formatPattern)
   {
      SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
      return dateFormat.format(dateObj);
   }
   
   public static String currentDateString(String formatPattern)
   {
      Date date = new Date();
      return dateToString(date, formatPattern);
   }
}
