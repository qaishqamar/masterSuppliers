package com.example.mastersupplier.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

 @Parcelize
 class AdsData(val adUrl: String, val adsName: String): Parcelable {
    constructor():this("","")
}
//class User( val uid:String,val username:String, val profileImageUrl: String,val aboutUser:String,val email:String,val phoneNo:String):Parcelable{
//    constructor():this("","","","","","")
//}