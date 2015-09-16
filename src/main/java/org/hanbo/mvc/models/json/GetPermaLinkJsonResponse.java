package org.hanbo.mvc.models.json;

public class GetPermaLinkJsonResponse
   extends PermaLinkJsonObject
{
   private boolean valid;

   public boolean isValid()
   {
      return valid;
   }

   public void setValid(boolean valid)
   {
      this.valid = valid;
   }
}
