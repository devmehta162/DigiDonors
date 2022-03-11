package com.dm.digidonors.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.net.URL;

public class RecentlyAddedNgoModel implements Parcelable {

    private String ngoImageUrl;
    private String ngoName;
    private String ngoAddress;
    private String ngoDetails;
    private String email;
    private String ngoId;
    private String phonenumber;
    private String videoid;
    private String websitelink;

    public RecentlyAddedNgoModel(String ngoImageUrl, String ngoName, String ngoAddress, String ngoDetails) {
        this.ngoImageUrl = ngoImageUrl;
        this.ngoName = ngoName;
        this.ngoAddress = ngoAddress;
        this.ngoDetails = ngoDetails;
    }

    public RecentlyAddedNgoModel() {
    }

    protected RecentlyAddedNgoModel(Parcel in) {
        ngoImageUrl = in.readString();
        ngoName = in.readString();
        ngoAddress = in.readString();
        ngoDetails = in.readString();
        email = in.readString();
        ngoId = in.readString();
        phonenumber = in.readString();
        videoid = in.readString();
        websitelink = in.readString();
    }

    public static final Creator<RecentlyAddedNgoModel> CREATOR = new Creator<RecentlyAddedNgoModel>() {
        @Override
        public RecentlyAddedNgoModel createFromParcel(Parcel in) {
            return new RecentlyAddedNgoModel(in);
        }

        @Override
        public RecentlyAddedNgoModel[] newArray(int size) {
            return new RecentlyAddedNgoModel[size];
        }
    };

    public String getNgoImageUrl() {
        return ngoImageUrl;
    }

    public void setNgoImageUrl(String ngoImageUrl) {
        this.ngoImageUrl = ngoImageUrl;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public String getNgoAddress() {
        return ngoAddress;
    }

    public void setNgoAddress(String ngoAddress) {
        this.ngoAddress = ngoAddress;
    }

    public String getNgoDetails() {
        return ngoDetails;
    }

    public void setNgoDetails(String ngoDetails) {
        this.ngoDetails = ngoDetails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgoId() {
        return ngoId;
    }

    public void setNgoId(String ngoId) {
        this.ngoId = ngoId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getWebsitelink() {
        return websitelink;
    }

    public void setWebsitelink(String websitelink) {
        this.websitelink = websitelink;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ngoImageUrl);
        dest.writeString(ngoName);
        dest.writeString(ngoAddress);
        dest.writeString(ngoDetails);
        dest.writeString(email);
        dest.writeString(ngoId);
        dest.writeString(phonenumber);
        dest.writeString(videoid);
        dest.writeString(websitelink);
    }
}
