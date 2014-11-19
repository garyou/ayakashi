package com.ayakashi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class GameActivity extends Activity{
	public static int width;
	public static int height;
	FrameLayout fl;
	private int t_num;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// タイトルバーを消す
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ステータスバーを消す
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Display display = getWindowManager().getDefaultDisplay();
		Point p = new Point();
		display.getSize(p);
		width=p.x;
		height=p.y;
		fl=new FrameLayout(this);
		Intent intent=getIntent();
		t_num = intent.getIntExtra("T_NUM",0);
		//メインメニューで選ばれたステージナンバーに応じて画面遷移を変える。
		switch(t_num){
		case 1:
			Stage1_view t1 = new Stage1_view(this);
			fl.addView(t1);
			setContentView(fl);
			break;
		case 2:
			Stage1_view t2 = new Stage1_view(this);
			fl.addView(t2);
			setContentView(fl);
			break;
		default:
			break;
		}
	}
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//	    if(keyCode == KeyEvent.KEYCODE_BACK){
//	    	return true;
//	        //return super.onKeyDown(keyCode, event);
//	    }else{
//	        return false;
//	    }
//	}
	public void onDestroy(){
		super.onDestroy();
		System.gc();
	}
}