package com.droid4you.util.cropimage;

import java.io.File;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    
    private static final int CAMERA_RESULT = 1;
    private Uri mUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	Button b = (Button)findViewById(R.id.button);
	b.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		doTakePhotoAction();

	    }
	});
    }

    private void doTakePhotoAction() {
	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
		"pic_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
	intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);

	try {
	    intent.putExtra("return-data", true);
	    startActivityForResult(intent, CAMERA_RESULT);
	} catch (ActivityNotFoundException e) {
	    e.printStackTrace();
	}
    }

    protected void onActivityResult(int requestCode, 
	    int resultCode, Intent data) {
	if (resultCode != RESULT_OK) {
	    return;
	}
	if (requestCode == CAMERA_RESULT) {
	    Intent intent = new Intent(this, CropImage.class);
	    // here you have to pass absolute path to your file
	    intent.putExtra("image-path", mUri.getPath());
	    intent.putExtra("scale", true);
	    startActivity(intent);
	}
    }
}