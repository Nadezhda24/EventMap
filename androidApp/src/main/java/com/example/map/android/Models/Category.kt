package com.example.map.android.Models

import android.os.Parcel
import android.os.Parcelable

data class Category(
    var id: Int = 0,
    var name: String? = "",
    var color: String? = "",
    var text_color: String? = "",
    var isCheck: Boolean =  false

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(color)
        parcel.writeString(text_color)
        parcel.writeByte(if (isCheck) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}