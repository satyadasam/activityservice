package com.  .activity.model;

import javax.validation.constraints.NotNull;

/**
 * Created by thatikonda on 10/27/16.
 */
public class ActivityResultUpdate {

    @NotNull
    private int activityResultId;
    @NotNull
    private String userAttribute;
    @NotNull
    private String attributeValue;

    public int getActivityResultId() {
        return activityResultId;
    }

    public void setActivityResultId(int activityResultId) {
        this.activityResultId = activityResultId;
    }

    public String getUserAttribute() {
        return userAttribute.toUpperCase();
    }

    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }


    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
