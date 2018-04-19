package com.shami.daniyalproject.fragments

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
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.shami.daniyalproject.R
import com.shami.daniyalproject.activities.loginregister.RegisterClickListeners
import com.shami.daniyalproject.activities.mainactivity.MainActivity
import com.shami.daniyalproject.api.pojo.response.User
import com.shami.daniyalproject.databinding.LayoutRegisterStudentBinding
import io.vrinda.kotlinpermissions.PermissionCallBack
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Ehitshamshami on 3/26/2018.
 */

class RegisterStudent:BaseFragment<LayoutRegisterStudentBinding>(),RegisterClickListeners
{



    //keep track of camera capture intent
    val CAMERA_CAPTURE = 1
    val PIC_CROP = 2

    //captured picture uri
    private lateinit var  picUri: Uri



   private lateinit var ImageBitMap: Bitmap


    private lateinit var registerViewModel:com.shami.daniyalproject.fragments.RegisterViewModel


    override fun init(view: View, savedInstanceState: Bundle?) {

        registerViewModel= ViewModelProviders.of(this@RegisterStudent)[com.shami.daniyalproject.fragments.RegisterViewModel::class.java]


        viewDataBinding.apply {

            viewmodel=registerViewModel
            listeners=this@RegisterStudent
        }
        subscribe()

    }

    override fun setLayout(): Int {

        return R.layout.layout_register_student
    }



    override fun register(view: View) {


        ImageBitMap?.let {

        //    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), getRealPathFromURI(getImageUriFromBitMap((activity as MainActivity),it)))
            registerViewModel.uploadImage(myFile(ImageBitMap))
        }

    }



    fun subscribe()
    {

        val showLoading=object: Observer<Boolean>
        {
            override fun onChanged(t: Boolean?) {

                t?.let{

                    if(t)
                    {
                        (activity as MainActivity).showLoading()
                    }
                    else
                    {
                        (activity as MainActivity).hideLoading()
                    }
                }

            }

        }

        val user= object : Observer<User> {
            override fun onChanged(t: User?) {
                t?.let {
                    Toast.makeText((activity as MainActivity),"User Registered Sucessfully", Toast.LENGTH_SHORT).show()
                }
            }
        }


        registerViewModel.isLoading().observe((activity as MainActivity),showLoading)
        registerViewModel.getUser().observe((activity as MainActivity),user)


    }


    fun getImageUriFromBitMap(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun myFile(myBitmap: Bitmap): File
    {
        val filename = picUri.getLastPathSegment()+".png"
        val f = File((activity as MainActivity).getCacheDir(), filename);
        f.createNewFile();

        val bos = ByteArrayOutputStream()
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


        if ( ContextCompat.checkSelfPermission( (activity as MainActivity), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission( (activity as MainActivity), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission( (activity as MainActivity), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED  ) {

            (activity as MainActivity).requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA), object : PermissionCallBack {
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
            Toast.makeText((activity as MainActivity).applicationContext,"Your Device doesnt support Camera", Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode==CAMERA_CAPTURE) {

            data?.let {

                val photo = data.extras.get("data") as Bitmap
                ImageBitMap=photo
                picUri = getImageUri((activity as MainActivity).applicationContext, photo)
                viewDataBinding.profilePhotoIV.setImageBitmap(photo)
             //   performCrop()
            }
        }
        else if(resultCode == AppCompatActivity.RESULT_OK &&requestCode == PIC_CROP){

            data?.let {

                val extras =data.extras
                val thePic = extras.getParcelable<Bitmap>("data")

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

           ex.printStackTrace()
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
        val cursor = (activity as MainActivity).getContentResolver().query(uri, null, null, null, null)
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }


    companion object {
        val RegisterStudent="RegisterStudent"
        val newInstance= RegisterStudent()
    }




}