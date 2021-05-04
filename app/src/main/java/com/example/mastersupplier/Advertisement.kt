package com.example.mastersupplier

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_advertisement.*

class Advertisement : AppCompatActivity() {
    companion object{
        var permissionGrant=false
        val PermissionCode=1
    }
    var selected_photo_uri: Uri? =null
    var  resultUri:Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertisement)

        Addpiker_imageView.setOnClickListener {
            val permissionGet= PermissionGet(this,this@Advertisement)
            val permissionName=android.Manifest.permission.READ_EXTERNAL_STORAGE

            if (permissionGet.checkperrmission(permissionName)){
                Log.d("Main", "Try to show photo selecter")

                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //proceed and check what thee image is selected

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.d("Main", "image is selected")
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                resultUri = result.uri

                Addpiker_imageView.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermissionCode -> {
                if (grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show()
                    permissionGrant = true
                    Log.d("phone no", "Contact no:- trying to reed contacts")
                } else {
                    Toast.makeText(this, "permission Denied", Toast.LENGTH_SHORT).show()
                    permissionGrant = false
                }
            }

        }
    }

}