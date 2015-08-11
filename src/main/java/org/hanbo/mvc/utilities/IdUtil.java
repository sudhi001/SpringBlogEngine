package org.hanbo.mvc.utilities;

import java.util.UUID;

public class IdUtil
{
   public static String generateUuid()
   {
      UUID userId = UUID.randomUUID();
      return userId.toString();
   }
}

