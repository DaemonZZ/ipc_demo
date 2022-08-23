package com.thangdn6.aidlserver.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Student() :Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0;
    var name:String = "";
    var age:Int = 0;
    var math:Float = 0f
    var physic:Float = 0f
    var chemistry:Float = 0f
    var english:Float = 0f
    var literature:Float = 0f

    constructor(parcel: Parcel) : this() {
        readFromParcel(parcel)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeFloat(math)
        parcel.writeFloat(physic)
        parcel.writeFloat(chemistry)
        parcel.writeFloat(english)
        parcel.writeFloat(literature)
    }

    fun readFromParcel(parcel:Parcel){
        id = parcel.readInt()
        name = parcel.readString()?: ""
        age = parcel.readInt()
        math = parcel.readFloat()
        physic = parcel.readFloat()
        chemistry = parcel.readFloat()
        english = parcel.readFloat()
        literature = parcel.readFloat()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}