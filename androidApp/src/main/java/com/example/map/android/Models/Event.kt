package com.example.map.android

import android.os.Parcel
import android.os.Parcelable
import com.example.map.android.Models.Point
import com.example.map.android.Models.Category
import com.example.map.android.Models.User

data class Event (
    var id: Int = -1,
    var title: String? = "",
    var image: String? = "",
    var author: User? = User(),
    var category: Category? = Category(),
    var date: String? = "",
    var point: Point? = Point(),
    var address: String? = "",
    var description: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readParcelable(Category::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable(Point::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeParcelable(author, flags)
        parcel.writeParcelable(category, flags)
        parcel.writeString(date)
        parcel.writeParcelable(point, flags)
        parcel.writeString(address)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}