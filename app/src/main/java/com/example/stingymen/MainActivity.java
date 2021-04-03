package com.example.stingymen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button generateID, profilePic;
    public static EditText editTextName, editTextPosition, editTextBranch;
    public static ImageView profileImage;
    private static int RESULT_LOAD_IMAGE = 1;
    public static Uri selectedImage;
    Uri imageData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateID = findViewById(R.id.btn_generateID);
        profilePic = findViewById(R.id.btn_profilePic);
        editTextName = findViewById(R.id.edtText_Name);
        editTextPosition = findViewById(R.id.edtText_Position);
        editTextBranch = findViewById(R.id.edtText_Branch);
        profileImage = findViewById(R.id.image_profile);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);

            }
        });
        generateID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(editTextPosition.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "please enter your position", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (editTextBranch.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "please enter your branch", Toast.LENGTH_SHORT).show();
                    return;
                }
              Intent intent = new Intent(MainActivity.this, PrintID.class);
              startActivity(intent);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //ImageView imageView = (ImageView)findViewById(R.id.imgUpload);
            profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            imageData=data.getData();
            //final Uri imageData = data.getData();
        }
    }

}