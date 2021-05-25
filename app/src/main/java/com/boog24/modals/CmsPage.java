package com.boog24.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CmsPage {

@SerializedName("cms_page_id")
@Expose
private String cmsPageId;
@SerializedName("page_key")
@Expose
private String pageKey;
@SerializedName("description")
@Expose
private String description;

public String getCmsPageId() {
return cmsPageId;
}

public void setCmsPageId(String cmsPageId) {
this.cmsPageId = cmsPageId;
}

public String getPageKey() {
return pageKey;
}

public void setPageKey(String pageKey) {
this.pageKey = pageKey;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

}