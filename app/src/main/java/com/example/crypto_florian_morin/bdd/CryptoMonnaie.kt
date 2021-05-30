package com.example.crypto_florian_morin.bdd

import android.os.Parcel
import android.os.Parcelable

data class CryptoMonnaie(var ID: String?, var Name: String?, var Symbol: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ID)
        parcel.writeString(Name)
        parcel.writeString(Symbol)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CryptoMonnaie> {
        override fun createFromParcel(parcel: Parcel): CryptoMonnaie {
            return CryptoMonnaie(parcel)
        }

        override fun newArray(size: Int): Array<CryptoMonnaie?> {
            return arrayOfNulls(size)
        }
    }

}
