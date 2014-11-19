package com.ayakashi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Map{
	public int[][] map;
	private int height = 0;
	private int width = 0;
	private int blank = 0;
	private int drawmap_width;

	private Bitmap[] mapImage;

	Define define = new Define();
	Paint paint = new Paint();
	Player player;
	Resources res;

	public Map(Context context, Player player){
		height = GameActivity.height;
		width = GameActivity.width;
		blank = width - height;
		drawmap_width = blank / 3;

		res = context.getResources();
		this.player = player;

		map = new int[define.TBLSIZE][define.TBLSIZE];
		mapImage = new Bitmap[4];//床、壁、階段、地脈、

		mapImage[0] = BitmapFactory.decodeResource(res, R.drawable.wall);
		mapImage[1] = BitmapFactory.decodeResource(res, R.drawable.floor);
		mapImage[2] = BitmapFactory.decodeResource(res, R.drawable.stairs);
		mapImage[3] = BitmapFactory.decodeResource(res, R.drawable.powerspot);

		for(int i=0;i<mapImage.length;i++){
			mapImage[i] = massScale(mapImage[i]);
		}

		//固定マップの定義
		for(int i=0;i<define.TBLSIZE;i++){
			for(int j=0;j<define.TBLSIZE;j++){
				if(i < 4 || i > 24)map[i][j] = define.WALL;
				else if(j < 4 || j > 24)map[i][j] = define.WALL;
				else map[i][j] = define.FLOOR;
			}
		}
	}

	public void onDraw(Canvas canvas){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(map[(player.get_x()-4)+i][(player.get_y()-4)+j] == define.WALL){//壁の場合
						canvas.drawBitmap(mapImage[0],drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
				}
				else if(map[(player.get_x()-4)+i][(player.get_y()-4)+j] == define.FLOOR){//床の場合
					canvas.drawBitmap(mapImage[1],drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
				}
			}
		}
	}

	public Bitmap massScale(Bitmap bitmap){//1マスサイズにスケール変更
		bitmap=Bitmap.createScaledBitmap(bitmap,height/9,height/9,false);
		return bitmap;
	}
}