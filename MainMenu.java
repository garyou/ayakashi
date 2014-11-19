package com.ayakashi;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class MainMenu extends Activity{
	public int width;
	public int height;
	
	Bitmap back;
	
	Bitmap t_btn1;
	Bitmap t_btn2;
	Bitmap t_btn3;
	ImageView imageView;
	ImageButton[] button = new ImageButton[10];


	protected void onCreate(Bundle savedInstanceState) {
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
		Resources r = getResources();
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if(hour>=7 && hour<=19){
			back = BitmapFactory.decodeResource(r, R.drawable.spring_m);
			back=backScale(back);
		}
		else{
			back = BitmapFactory.decodeResource(r, R.drawable.spring_n);
			back=backScale(back);
		}
		imageView=new ImageView(this);
		imageView.setImageBitmap(back);
		imageView.setScaleType(ScaleType.FIT_XY);
		t_btn1 = BitmapFactory.decodeResource(r, R.drawable.menu_1);
		t_btn2 = BitmapFactory.decodeResource(r, R.drawable.menu_2);
		t_btn3 = BitmapFactory.decodeResource(r, R.drawable.menu_3);
		
		t_btn1 =tutomenuScale(t_btn1);
		t_btn2 =tutomenuScale(t_btn2);
		t_btn3 =tutomenuScale(t_btn3);
		//メニュー全体のレイアウト
		FrameLayout fl=new FrameLayout(this);
		LinearLayout ll1=new LinearLayout(this);
		LinearLayout ll2=new LinearLayout(this);
		LinearLayout ll3=new LinearLayout(this);
		button[0] = new ImageButton(this);
		button[0].setImageBitmap(t_btn1);
		button[0].setX(height/9);
		button[0].setY(height/9);
		button[0].setBackground(null);
		button[1] = new ImageButton(this);
		button[1].setImageBitmap(t_btn2);
		button[1].setX(height/2);
		button[1].setY(height/9);
		button[1].setBackground(null);
		ll1.addView(imageView);
		ll2.addView(button[0]);
		ll3.addView(button[1]);
		fl.addView(ll1);
		fl.addView(ll2);
		fl.addView(ll3);
		setContentView(fl);	
		//リスナー
		button[0].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				new AlertDialog.Builder(MainMenu.this)
				.setTitle("チュートリアル１に移行しますか？")
				.setNegativeButton("はい", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						Intent intent=new Intent(MainMenu.this,GameActivity.class);
						intent.putExtra("T_NUM",1);
						startActivity(intent);
					}
				}).setPositiveButton("いいえ",new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){

					}
				}).show();
			}
		});
		button[1].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				new AlertDialog.Builder(MainMenu.this)
				.setTitle("チュートリアル2に移行しますか？")
				.setNegativeButton("はい", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						Intent intent=new Intent(MainMenu.this,GameActivity.class);
						intent.putExtra("T_NUM",2);
						startActivity(intent);
					}
				}).setPositiveButton("いいえ",new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){

					}
				}).show();
			}
		});
	}
	public Bitmap menuScale(Bitmap bitmap){
		bitmap = Bitmap.createScaledBitmap(bitmap, width/6, height/6, false);
		return bitmap;
	}
	public Bitmap tutomenuScale(Bitmap bitmap){
		bitmap = Bitmap.createScaledBitmap(bitmap, width/6, height-(height/6)-(height/8), false);
		return bitmap;
	}
	public Bitmap backScale(Bitmap bitmap){
		bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		return bitmap;
	}
}