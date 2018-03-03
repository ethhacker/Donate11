package com.example.gm.donate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class blog_Activity extends AppCompatActivity {
private ImageButton imageButton;
private static final int GALLERY_REQUEST=1;
private EditText name;
private EditText desc;
private  Uri imageUri=null;
private StorageReference storageReference;
private ProgressDialog progressDialog;
private Button button;
private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_);

        button=(Button) findViewById(R.id.button4);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("blog");
    imageButton=(ImageButton) findViewById(R.id.imageButton2);
    name=(EditText) findViewById(R.id.editText);
    desc=(EditText) findViewById(R.id.editText2);

   progressDialog= new ProgressDialog(this);
    imageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent my=new Intent(Intent.ACTION_GET_CONTENT);
            my.setType("image/*");
            startActivityForResult(my,GALLERY_REQUEST);
        }
    });


    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            start();
        }
    });


    }


    private void start(){

        progressDialog.setMessage("Posting to Blog...");
        progressDialog.show();
        final String title=name.getText().toString().trim();
        final String desc_val=desc.getText().toString().trim();
        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc_val)&& imageUri!=null){

            StorageReference filepath=storageReference.child("Blog image").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri Download =taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost=databaseReference.push();
                    newPost.child("title").setValue(title);
                    newPost.child("desc").setValue(desc_val);
                    newPost.child("image").setValue(Download.toString());


                    progressDialog.dismiss();
                    startActivity(new Intent(blog_Activity.this,ProfileActivity.class) );
                }
            });
        }


    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
            imageUri=data.getData();
            imageButton.setImageURI(imageUri);
        }
    }

}
