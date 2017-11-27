package com.example.dan.totalfishing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private ImageButton mSelectImage;
    private EditText Specie;
    private EditText Weight;
    private EditText DateTime;
    private EditText Method;
    private Button saveBtn;
    private Button location;
    private String latLng = "";

    private Uri imageUri = null;

    private static final int GALLERY_REQUEST = 1;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mProgress = new ProgressDialog(this);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fishing");

        mSelectImage = (ImageButton) findViewById(R.id.imageSelect);

        Specie = (EditText) findViewById(R.id.Specie);
        Weight = (EditText) findViewById(R.id.Weight);
        DateTime = (EditText) findViewById(R.id.DateTime);
        Method = (EditText) findViewById(R.id.Method);

        saveBtn = (Button) findViewById(R.id.SaveData);
        location = (Button) findViewById(R.id.location);


        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLog();
            }
        });


        location.setOnClickListener(new View.OnClickListener() {

            int mapLocation = 0;

            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostActivity.this, MapsActivity.class);
                startActivityForResult(i, 1000);
            }
        });
    }

    private void saveLog(){

        mProgress.setMessage("Storing your catch now!");

        final String specie_val = Specie.getText().toString().trim();
        final String weight_val = Weight.getText().toString().trim();
        final String datetime_val = DateTime.getText().toString().trim();
        final String method_val = Method.getText().toString().trim();
        final String location_val = latLng.trim();

        if (!TextUtils.isEmpty(specie_val)&&!TextUtils.isEmpty(weight_val)&&!TextUtils.isEmpty(datetime_val)&&!TextUtils.isEmpty(method_val)&&imageUri!=null){
            StorageReference filepath = mStorage.child("Fishing_Images").child(imageUri.getLastPathSegment());

            mProgress.show();

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();

                    newPost.child("specie").setValue(specie_val);
                    newPost.child("weight").setValue(weight_val);
                    newPost.child("datetime").setValue(datetime_val);
                    newPost.child("method").setValue(method_val);
                    newPost.child("location").setValue(location_val);
                    newPost.child("image").setValue(downloadUrl.toString());
                    mProgress.dismiss();

                    startActivity(new Intent(PostActivity.this, MainActivity.class));
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            imageUri = data.getData();
            mSelectImage.setImageURI(imageUri);
        }
        else if (requestCode==1000) {
            super.onActivityResult(requestCode, resultCode, data);
            latLng = data.getStringExtra("latLng");


        }
    }
}
