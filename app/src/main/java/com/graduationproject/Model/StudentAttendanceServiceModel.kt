package com.graduationproject.Model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class StudentAttendanceServiceModel(
    val image: Uri,
    val groupId: String,
    val attendanceId: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<Uri>(Uri::class.java.classLoader),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(image, 0)
        writeString(groupId)
        writeString(attendanceId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StudentAttendanceServiceModel> =
            object : Parcelable.Creator<StudentAttendanceServiceModel> {
                override fun createFromParcel(source: Parcel): StudentAttendanceServiceModel =
                    StudentAttendanceServiceModel(source)

                override fun newArray(size: Int): Array<StudentAttendanceServiceModel?> =
                    arrayOfNulls(size)
            }
    }
}