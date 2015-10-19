package com.sky.gifengine;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.gifengine.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		GifSurfaceView gif = (GifSurfaceView) findViewById(R.id.gif);
		gif.setPath("lala.gif");
		gif.setScale(2);
	}

}
