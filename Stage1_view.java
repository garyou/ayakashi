package com.ayakashi;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class Stage1_view extends SurfaceView implements SurfaceHolder.Callback,Runnable{

	private SurfaceHolder holder;
	private Thread thread;
	private Paint paint = new Paint();

	Resources r;
	//端末サイズ変数系
	private int width;//端末の横サイズ
	private int height;//端末の縦サイズ
	private int blank;//ゲーム描画領域以外の横サイズ合計
	private int key_width;//方向キー１個の横サイズ
	private int key_height;//方向キー１個の縦サイズ
	private Bitmap window;
	private Bitmap juzu;

	cardData data;//カードデータクラス
	Enemy enemy;
	Player player;
	Map map;

	private int intentcnt;

	public Stage1_view(Context context) {
		super(context);
		getHolder().addCallback(this);
		r=context.getResources();
		player=new Player(context,5,50,50);
		enemy=new Enemy(player);//プレイヤーのインスタンス情報を与える
		data=new cardData(context);
		map = new Map(context, player);
		intentcnt = 0;

		width=GameActivity.width;
		height=GameActivity.height;
		blank=width-height;
		key_width=(width-(height+(blank/3)))/3;
		key_height=((height/9)*4)/3;

		juzu = BitmapFactory.decodeResource(r, R.drawable.juzu);
		juzu = juzuScale(juzu);
		window = BitmapFactory.decodeResource(r, R.drawable.tutorialwindow);
		window = window(window);
	}
	public void surfaceCreated(SurfaceHolder holder) {
		this.holder=holder;
		thread=new Thread(this);
		thread.start();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.gc();
		thread=null;
	}

	public void run() {
		while(thread!=null){
			player.onUpdate();
			onDraw(getHolder());
		}
	}

	public void onDraw(SurfaceHolder holder){
		Canvas canvas=holder.lockCanvas();
		if(canvas==null){
			return;
		}
		canvas.drawColor(Color.GRAY);
		//各クラスのonDrawを呼び出す
		map.onDraw(canvas);
		player.onDraw(canvas);
		enemy.onDraw(canvas);
		data.onDraw(canvas);
		
		paint.setColor(Color.RED);
		paint.setTextSize(40);
		holder.unlockCanvasAndPost(canvas);
	}

	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			player.onTouchEvent(event, data);
			data.onTouchEvent(event);
		}
		return true;
	}
	public Bitmap window(Bitmap bitmap){
		bitmap = Bitmap.createScaledBitmap(bitmap, height, height/3, false);
		return bitmap;
	}
	public Bitmap juzuScale(Bitmap bitmap){
		bitmap = Bitmap.createScaledBitmap(bitmap, (blank/3), (blank/3), false);
		return bitmap;
	}
	public void gameclear(SurfaceHolder holder){
		Intent intent = new Intent(getContext(),MainMenu.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		getContext().startActivity(intent);
	}
	public void gameover(SurfaceHolder holder){
		Intent intent = new Intent(getContext(),MainMenu.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		getContext().startActivity(intent);
	}
}
