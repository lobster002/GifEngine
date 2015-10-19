package com.sky.gifengine;

import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

public class GifSurfaceView extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder holder = null;
	private String path = null;
	private Movie movie = null;

	private float MSCALE = 1.0f;
	private Handler handler = null;

	public void setPath(String path) {
		this.path = path;
	}

	public GifSurfaceView(Context context) {
		super(context);
		initParam();
	}

	public void setScale(float scale){
		this.MSCALE = scale;
	}
	
	public GifSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initParam();
	}

	public GifSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initParam();
	}

	private void initParam() {
		holder = getHolder();
		holder.addCallback(this);
		handler = new Handler();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 加载gif图片
		if (null != path) {
			try {
				InputStream stream = getContext().getAssets().open(path);
				movie = Movie.decodeStream(stream);
				setMeasuredDimension((int)(movie.width()*MSCALE),(int)( movie.height()*MSCALE));
				handler.post(this);// 执行gif动画
			} catch (IOException e) {
				ShowToast(e.toString());
			}
		}
	}

	private void ShowToast(String str) {
		Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		handler.removeCallbacks(this);// 关闭线程
	}

	@Override
	public void run() {
		Canvas mCanvas = holder.lockCanvas();// 对画布加锁
		mCanvas.save();
		mCanvas.scale(MSCALE, MSCALE);
		movie.draw(mCanvas, 0, 0);
		movie.setTime((int) (System.currentTimeMillis() % movie.duration())); // 逐帧绘制
		mCanvas.restore();
		holder.unlockCanvasAndPost(mCanvas); // 解锁
		handler.postDelayed(this, 32);
	}

}
