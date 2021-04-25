package com.example.mastersupplier

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionGet(val context: Context,val activity: Activity) {
    fun checkperrmission(permissionName:String):Boolean{
        val permission= ContextCompat.checkSelfPermission(context,
            permissionName)


        if(permission== PackageManager.PERMISSION_GRANTED)
        {

            return true
        }
        else
        {
            ActivityCompat.requestPermissions(activity, arrayOf(permissionName), Advertisement.PermissionCode)

            return Advertisement.permissionGrant
        }
    }







}
