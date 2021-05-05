package com.example.mastersupplier

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.*
import com.example.mastersupplier.models.AdsData
import com.example.mastersupplier.models.AdsItemHolder
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

import kotlinx.android.synthetic.main.activity_advertisement.*
import kotlinx.android.synthetic.main.ads_row.view.*

class Advertisement : AppCompatActivity() {
    companion object {
        var permissionGrant = false
        val PermissionCode = 1
        val security_pass = "QS12@"
    }

    lateinit var AdsName:String
    lateinit var resultUri: Uri
    val adapter= GroupAdapter<GroupieViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertisement)
        ads_recyclerView.adapter=adapter

        Addpiker_imageView.setOnClickListener {
            val permissionGet = PermissionGet(this, this@Advertisement)
            val permissionName = android.Manifest.permission.READ_EXTERNAL_STORAGE

            if (permissionGet.checkperrmission(permissionName)) {
                Log.d("Main", "Try to show photo selecter")

                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

            }
        }

        Add_submit_button.setOnClickListener {
            val spass = add_pass_adv.text.toString()
            AdsName=add_company_adv.text.toString()
            if (spass != security_pass) {
                add_pass_adv.error = "pass is incorrect"

            } else if (resultUri==null){

                Toast.makeText(this, "check image selection", Toast.LENGTH_SHORT).show()
            }
//            else if (addCompanyName.isNotEmpty()){
//                add_company_adv.error="name is not field"
//            }
            else{
                pussAddToStorage(AdsName)
            }
        }
        listenForMessages()

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


    fun pussAddToStorage(name:String) {
        val ref = FirebaseStorage.getInstance().getReference("/addImage/$name")
        ref.putFile(resultUri)
            .addOnSuccessListener {
                Log.d("Main", "image is uploaded sucessfully ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("Main", "File location :$it")

                    saveAdsToFirebase(it.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Image url is saving Failed", Toast.LENGTH_SHORT).show()
            }


    }

    private fun saveAdsToFirebase(adUrl:String) {
        val adsData= AdsData(adUrl,AdsName)
        val ref= FirebaseDatabase.getInstance().getReference("/ads/$AdsName")

        ref.setValue(adsData)
            .addOnSuccessListener {
                Log.d("Main","user detail is uploaded")
                add_company_adv.text=null
                Addpiker_imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_add_photo_alternate_24))

            }

            .addOnFailureListener {
                Log.d("Main","details re not uploaded :try again")
                Toast.makeText(this,"users detail not uploaded",Toast.LENGTH_SHORT).show()

            }
    }
    val latestAdsMap=HashMap<String, AdsData>()
    fun refresRecyclerView(){
        adapter.clear()
        latestAdsMap.values.forEach{

            adapter.add(AdsItemHolder(it))
       }

    }
    private fun listenForMessages() {
//        fromId=FirebaseAuth.getInstance().uid
//        toId=toUser?.uid

        val ref = FirebaseDatabase.getInstance().getReference("/ads")
        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
               p0.children.forEach{

                   Log.d("listen",it.toString())
                   val ads=it.getValue(AdsData::class.java)
                   if (ads!=null){
                       adapter.add(AdsItem(ads))
                   }

               }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}

class AdsItem(val adsData: AdsData):Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Picasso.get().load(adsData.adUrl).into(viewHolder.itemView.ads_img_row)

    }

    override fun getLayout(): Int {
     return R.layout.ads_row
    }

}
