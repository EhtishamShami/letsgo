package com.shami.daniyalproject.activities.loginregister

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.shami.daniyalproject.R
import com.shami.daniyalproject.activities.BaseActivity
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.databinding.LayoutRegisterBinding
import io.vrinda.kotlinpermissions.PermissionCallBack
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream




/**
 * Created by Shami on 3/4/2018.
 */

class Register:BaseActivity<LayoutRegisterBinding>(),RegisterClickListeners {


    private lateinit var mRegisterViewModel:RegisterViewModel


    //keep track of camera capture intent
    val CAMERA_CAPTURE = 1
    val PIC_CROP = 2

    //captured picture uri
    private lateinit var  picUri: Uri



    private lateinit var cropedImageBitMap:Bitmap


    override fun init(savedInstanceState: Bundle?) {

        mRegisterViewModel= ViewModelProviders.of(this)[RegisterViewModel::class.java]

        viewDataBinding.apply {

            viewmodel=mRegisterViewModel
            listeners=this@Register
        }


        subscribe()

    }




    override fun setLayout(): Int {
        return R.layout.layout_register
    }


    override fun register(view: View) {

        cropedImageBitMap?.let {

           val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), getRealPathFromURI(getImageUriFromBitMap(this,cropedImageBitMap)))
            mRegisterViewModel.uploadImage(requestFile,"myName.png",myFile(cropedImageBitMap))
        }

    }



    fun subscribe()
    {

        val showLoading=object:Observer<Boolean>
        {
            override fun onChanged(t: Boolean?) {

                t?.let{

                    if(t)
                    {
                        showLoading()
                    }
                    else
                    {
                        hideLoading()
                    }
                }

            }

        }

        val user= object : Observer<User>{
            override fun onChanged(t: User?) {
                t?.let {
                    Toast.makeText(this@Register,"Driver Registered Sucessfully",Toast.LENGTH_SHORT).show()
                }
            }
        }


        mRegisterViewModel.isLoading.observe(this@Register,showLoading)
        mRegisterViewModel.getUser().observe(this@Register,user)


    }


    fun getImageUriFromBitMap(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun myFile(myBitmap: Bitmap):File
    {
        val f = File(this.getCacheDir(), "MyPicture");
        f.createNewFile();

        val bos =ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        val bitmapdata = bos.toByteArray();

//write the bytes in file
        val fos = FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        return f;
    }


    override fun captureImage(view: View) {


        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission( this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission( this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED  ) {

            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA), object : PermissionCallBack {
                override fun permissionGranted() {
                    super.permissionGranted()
                    takePicture()
                }

                override fun permissionDenied() {
                    super.permissionDenied()
                }
            })

        }
        else {
            takePicture()

        }





    }



    fun takePicture()
    {

        try {
            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(captureIntent, CAMERA_CAPTURE);
        }
        catch (ex: ActivityNotFoundException)
        {
            Toast.makeText(applicationContext,"Your Device doesnt support Camera", Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode==CAMERA_CAPTURE) {

            data?.let {

                val photo = data.extras.get("data") as Bitmap

                picUri = getImageUri(applicationContext, photo)
                performCrop()
            }
        }
        else if(resultCode == RESULT_OK &&requestCode == PIC_CROP){

            data?.let {

                val extras =data.extras
                val thePic = extras.getParcelable<Bitmap>("data")

                cropedImageBitMap=thePic
                viewDataBinding.profilePhotoIV.setImageBitmap(thePic)

            }

        }


    }

    fun performCrop() {

        try {

            //call the standard crop action intent (the user device may not support it)
            val cropIntent = Intent("com.android.camera.action.CROP")
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*")
            //set crop properties
            cropIntent.putExtra("crop", "true")
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            //indicate output X and Y
//            cropIntent.putExtra("outputX", 275)
//            cropIntent.putExtra("outputY", 300)
            //retrieve data on return
            cropIntent.putExtra("return-data", true)
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP)


        } catch (ex: ActivityNotFoundException) {

            val errorMessage = "Whoops - your device doesn't support the crop action!"
            val toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
            toast.show()
        }


    }


    fun toGrayscale(bmpOriginal: Bitmap): Bitmap {
        val width: Int
        val height: Int
        height = bmpOriginal.height
        width = bmpOriginal.width

        val bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmpGrayscale)
        val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        val f = ColorMatrixColorFilter(cm)
        paint.setColorFilter(f)
      //  c.drawBitmap(bmpOriginal, 0, 0, paint)
        return bmpGrayscale
    }



    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri): String {
        val cursor = getContentResolver().query(uri, null, null, null, null)
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }




}