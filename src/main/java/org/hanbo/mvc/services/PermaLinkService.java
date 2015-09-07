package org.hanbo.mvc.services;

import org.hanbo.mvc.models.json.NewPermaLinkJsonRequest;

public interface PermaLinkService
{
   String setPermaLink(NewPermaLinkJsonRequest permaLinkJsonReq);
}
