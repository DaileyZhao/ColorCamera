package com.zcm.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Person  implements Parcelable {

    private String name;
    private int psw;

    public Person(String name, int psw){
        this.name=name;
        this.psw=psw;
    }
    protected Person(Parcel in) {
        this.name=in.readString();
        this.psw=in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(psw);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", psw=" + psw +
                '}';
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
