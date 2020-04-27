package com.graduationproject.Model

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import okhttp3.MultipartBody
import okhttp3.RequestBody


data class SendVideoServiceModel(
    val name: String,
    val email: String,
    val department: String,
    val video: Uri
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readParcelable<Uri>(Uri::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(email)
        writeString(department)
        writeParcelable(video, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SendVideoServiceModel> =
            object : Parcelable.Creator<SendVideoServiceModel> {
                override fun createFromParcel(source: Parcel): SendVideoServiceModel =
                    SendVideoServiceModel(source)

                override fun newArray(size: Int): Array<SendVideoServiceModel?> = arrayOfNulls(size)
            }
    }
}