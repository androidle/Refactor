package com.thinkcoo.mobile.model.entity.serverresponse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Leevin
 * CreateTime: 2016/5/26  18:40
 */
public class Person implements Parcelable {
    private int age;
    private String name;
    private String gender;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeString(this.name);
        dest.writeString(this.gender);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
        this.gender = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
