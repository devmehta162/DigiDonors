package com.dm.digidonors.models;

public class OnGoingCampaignModel {

    private String campaignImageUrl;
    private String campaignHeading;
    private String campaignDetails;

    public OnGoingCampaignModel(String campaignImageUrl, String campaignHeading, String campaignDetails) {
        this.campaignImageUrl = campaignImageUrl;
        this.campaignHeading = campaignHeading;
        this.campaignDetails = campaignDetails;
    }

    public OnGoingCampaignModel() {
    }

    public String getCampaignImageUrl() {
        return campaignImageUrl;
    }

    public void setCampaignImageUrl(String campaignImageUrl) {
        this.campaignImageUrl = campaignImageUrl;
    }

    public String getCampaignHeading() {
        return campaignHeading;
    }

    public void setCampaignHeading(String campaignHeading) {
        this.campaignHeading = campaignHeading;
    }

    public String getCampaignDetails() {
        return campaignDetails;
    }

    public void setCampaignDetails(String campaignDetails) {
        this.campaignDetails = campaignDetails;
    }
}
