package com.example.sihan.restaurantrecommendation.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sihan.restaurantrecommendation.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;


public class Register extends AppCompatActivity {
    private EditText newAccount, newPassword;
    private Button confirm, cancel;
    private String SignAccount;
    private String SignPassword;
    private String objectID;

    private Bitmap photoBit;
    private ImageView a;
    private static boolean isCamera;
    SQLiteOpenHelper helper;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int CHOOSE_FROM_ALBUM =3;
    private ImageButton takephoto;
    private ImageView picture;
    private Uri imageUri;
    private ImageButton chooseFromAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        takephoto = (ImageButton) findViewById(R.id.camera);
        picture = (ImageView) findViewById(R.id.userPictureRegister);
        chooseFromAlbum = (ImageButton) findViewById(R.id.albumn);

        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CROP_PHOTO);
            }
        });

        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentBc.setData(imageUri);
        this.sendBroadcast(intentBc);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;

            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }


    public void save() {
        final AVQuery<AVObject> query = new AVQuery<>("Useraccount");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                boolean validate = false;
                for (AVObject avObject : list) {
                    String acc = avObject.getString("SignAccount");
                    Toast.makeText(getApplicationContext(), " soso " + acc,
                            Toast.LENGTH_SHORT).show();
                    if (newAccount.getText().toString().equals(acc)) {
                        validate = true;
                        Toast.makeText(getApplicationContext(), "The account is already existed, please input new !",
                                Toast.LENGTH_SHORT).show();

                    }
                }
                if (!validate) {
                    final AVObject useraccount = new AVObject("Useraccount");
                    useraccount.put("SignAccount", newAccount.getText().toString());
                    useraccount.put("SignPassword", newPassword.getText().toString());
                    // useraccount.put("UserImage",picture);
                    useraccount.setFetchWhenSave(true);
                    useraccount.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Sign successfully!", Toast.LENGTH_SHORT).show();
                                objectID = useraccount.getObjectId();
                            } else {

                                // 失败的话，请检查网络环境以及 SDK 配置是否正确
                            }
                        }
                    });

                }
            }
        });


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isCamera) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    Bundle extras = data.getExtras();
                    photoBit = (Bitmap) extras.get("data");
                    //    a=(ImageView)findViewById(R.id.userPictureRegister);
                    a.setImageBitmap(photoBit);
                }

            }
        } else{
            if (resultCode == RESULT_OK && requestCode == 1 && null!=data) {
                Uri selectImage = data.getData();
                String [] filePathColumns ={MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(selectImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picutrPath =c.getString(columnIndex);
                c.close();
                //ImageZip.zipImage(picutrPath);
                a.setImageBitmap(BitmapFactory.decodeFile(picutrPath));
            }
        }
    }


    public  static String createFileName(){
        String fileName = "";
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyymmdd_HHmmss");
        fileName = dateFormat.format(date) + ".jpg";
        return  fileName;
    }

    public  static  void  savePhotoToSDCard(String path,String photoName,Bitmap photoBitmap){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File dir = new File(path);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File photoFile = new File(path,photoName);
            FileOutputStream fileOutputStream=null;
            try{
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap !=null ){
                    if (photoBitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream)){
                        fileOutputStream.flush();;
                        fileOutputStream.close();
                    }

                }
            }catch (FileNotFoundException e){
                photoFile.delete();
                e.printStackTrace();
            }catch (IOException e){
                photoFile.delete();
                e.printStackTrace();
            }finally {
                try{
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }


    }*/

        class sureListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    save();
                } catch (SQLiteException e) {
                    Toast.makeText(getApplicationContext(), "Sign Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        class quListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Register.this, Login.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", objectID);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
    }
}