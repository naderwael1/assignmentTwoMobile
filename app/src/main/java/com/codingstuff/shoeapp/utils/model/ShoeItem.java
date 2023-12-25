package com.codingstuff.shoeapp.utils.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ShoeItem implements Parcelable {

    private String shoeName, shoeBrandName, color,color1,color2, size, available;
    private int shoeImage, number;
    private double shoePrice;

    public ShoeItem(String shoeName, String shoeBrandName, int shoeImage, double shoePrice, String color, String color1, String color2, String size, String available, int number) {
        this.shoeName = shoeName;
        this.shoeBrandName = shoeBrandName;
        this.shoeImage = shoeImage;
        this.shoePrice = shoePrice;
        this.color = color;
        this.size = size;
        this.available = available;
        this.number = number;
        this.color1 = color1;
        this.color2=color2;
    }

    protected ShoeItem(Parcel in) {
        shoeName = in.readString();
        shoeBrandName = in.readString();
        color = in.readString();
        color1 = in.readString();
        color2 = in.readString();

        size = in.readString();
        shoeImage = in.readInt();
        shoePrice = in.readDouble();
        available = in.readString();
        number = in.readInt();
    }

    public static final Creator<ShoeItem> CREATOR = new Creator<ShoeItem>() {
        @Override
        public ShoeItem createFromParcel(Parcel in) {
            return new ShoeItem(in);
        }

        @Override
        public ShoeItem[] newArray(int size) {
            return new ShoeItem[size];
        }
    };

    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public String getShoeBrandName() {
        return shoeBrandName;
    }

    public void setShoeBrandName(String shoeBrandName) {
        this.shoeBrandName = shoeBrandName;
    }

    public int getShoeImage() {
        return shoeImage;
    }

    public void setShoeImage(int shoeImage) {
        this.shoeImage = shoeImage;
    }

    public double getShoePrice() {
        return shoePrice;
    }

    public void setShoePrice(double shoePrice) {
        this.shoePrice = shoePrice;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String getColor() {
        return color;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor2() {
        return color2;
    }



    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public int getAnum() {
        return number;
    }

    public void setAnum(int Anum) {
        this.number = Anum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(shoeName);
        parcel.writeString(shoeBrandName);
        parcel.writeString(color);
        parcel.writeString(color2);
        parcel.writeString(color1);
        parcel.writeString(size);
        parcel.writeInt(shoeImage);
        parcel.writeDouble(shoePrice);
        parcel.writeString(available);
        parcel.writeInt(number);
    }
}
