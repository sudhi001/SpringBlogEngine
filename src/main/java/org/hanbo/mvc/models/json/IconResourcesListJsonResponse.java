package org.hanbo.mvc.models.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class IconResourcesListJsonResponse
{
   private String ownerId;
   private int pageIdx;
   private List<IconResourceData> iconResourcesList;
   
   public IconResourcesListJsonResponse()
   {
      setIconResourcesList(new ArrayList<IconResourceData>());
   }

   public String getOwnerId()
   {
      return ownerId;
   }

   public void setOwnerId(String ownerId)
   {
      this.ownerId = ownerId;
   }
   
   public int getPageIdx()
   {
      return pageIdx;
   }

   public void setPageIdx(int pageIdx)
   {
      this.pageIdx = pageIdx;
   }

   public List<IconResourceData> getIconResourcesList()
   {
      return iconResourcesList;
   }

   public void setIconResourcesList(
      List<IconResourceData> iconResourcesList)
   {
      this.iconResourcesList = iconResourcesList;
   }
   
   public boolean isValid()
   {
      return !StringUtils.isEmpty(ownerId)
            && this.pageIdx >= 0
            && this.iconResourcesList.size() > 0;
   }
}
