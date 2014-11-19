package com.ayakashi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class TitleActivity extends Activity{
	public static int width;
	public static int height;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// タイトルバーを消す
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ステータスバーを消す
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//ディスプレイサイズを取得する
		Display display = getWindowManager().getDefaultDisplay();
		Point p = new Point();
		display.getSize(p);
		width=p.x;
		height=p.y;
		setContentView(new TitleView(this));
	}
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			Intent intent=new Intent(TitleActivity.this,MainMenu.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		return true;
	}
	public void onDestroy(){
		super.onDestroy();
		System.gc();
	}
}