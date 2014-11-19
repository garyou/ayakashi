package com.ayakashi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class TitleView extends View{
	Resources res = this.getContext().getResources();
	Paint paint=new Paint();
	int width;
	int height;
	private Bitmap title;
	public TitleView(Context context) {
		super(context);
		width = TitleActivity.width;
		height = TitleActivity.height;
		title=BitmapFactory.decodeResource(res,R.drawable.title2);
		title = titleScale(title);
		
	}
	public void onDraw(Canvas c) {
		c.drawBitmap(title, 0, 0, paint);
	}
	//タイトル画面用関数
	public Bitmap titleScale(Bitmap bitmap){
		bitmap=Bitmap.createScaledBitmap(bitmap,width,height,false);
		return bitmap;
	}	
}