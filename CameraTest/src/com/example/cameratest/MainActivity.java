package com.example.cameratest;
// This project is adopted from https://eclass.srv.ualberta.ca/mod/resource/view.php?id=1136415
import java.io.File;
import java.net.URI;

import com.example.cameratest.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

    Uri imageFileUri;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v){
                takeAPhoto();
            }
        };
        button.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 12345;
    
    //This method creates an intent. 
    //It is told that we need camera action, and the results should be saved in a location that is sent to the intent.
    public void takeAPhoto() {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCameraTest";
		File folder = new File(path);
		if (!folder.exists())	
			folder.mkdir();
		String imagePathAndFileName = path + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg"; //creating filename from path and time and jpg extension
		File imageFile = new File(imagePathAndFileName); // create the new file
		imageFileUri = Uri.fromFile(imageFile); // what does this do?
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri); //sending the image file URI to this intent
		
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE); //define request code - comparing results from activity with this code
		// getting textview and can have results back from activity result
		
			
			
    }
    
    //This method is run after returning back from camera activity:
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //we want to show what happened in the camera activity, to show the user in the text view
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			TextView tv = (TextView)findViewById(R.id.status);
			
			if (resultCode == RESULT_OK){ // result back from camera activitiy
				tv.setText("Photo completed!");
				ImageButton ib = (ImageButton)findViewById(R.id.TakeAPhoto);
				ib.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath())); //get stored image and put into image button
			}
			else
				if (resultCode == RESULT_CANCELED){
					tv.setText("Photo was canceled!");
				}
				else
					tv.setText("What happened?!!");
		}
    }
}
