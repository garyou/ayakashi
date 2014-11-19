package com.ayakashi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Player{
	//マップ用座標管理変数１～２９
	private int x=0;
	private int y=0;
	//方向管理変数０～７
	private int vec=0;

	private int width=0;
	private int height=0;
	private int blank=0;

	//player画像描画用変数
	private int draw_x;
	private int draw_y;

	private int at=0;//攻撃力
	private int df=0;//防御力
	private int maxhp=0;//最大体力
	private int hp=0;//現在体力
	private int hpbar_length=0;//体力バーの幅サイズ
	private int hpbar_maxlength=0;//体力バーの最大幅サイズ
	private int key_width;//方向キー１個の横サイズ
	private int key_height;//方向キー１個の縦サイズ
	
	Paint paint=new Paint();
	Resources res;

	private Bitmap[] playerImage;
	private Bitmap key;
	private Bitmap zangekiimage;
	private Bitmap itemmenu;
	

	Map map_ins;
	Define define = new Define();
	public Player(Context context,int aa,int hh,int mhh){
//		at = aa;//攻撃力の設定
//		hp = hh;//体力の設定
//		maxhp = mhh;//最大体力の設定
		//プレイヤーを表示する位置は常に真ん中
		width=TitleActivity.width;
		height=TitleActivity.height;
		blank=width-height;
		hpbar_maxlength = height;
		hpbar_length = hpbar_maxlength / hh;
		key_width=(width-(height+(blank/3)))/3;
		key_height=((height/9)*4)/3;
		
		res = context.getResources();
		map_ins = new Map(context, this);

		playerImage = new Bitmap[8];

		//プレイヤーの画像
		playerImage[0] = BitmapFactory.decodeResource(res,R.drawable.player_0);
		playerImage[1] = BitmapFactory.decodeResource(res,R.drawable.player_1);
		playerImage[2] = BitmapFactory.decodeResource(res,R.drawable.player_2);
		playerImage[3] = BitmapFactory.decodeResource(res,R.drawable.player_3);
		playerImage[4] = BitmapFactory.decodeResource(res,R.drawable.player_4);
		playerImage[5] = BitmapFactory.decodeResource(res,R.drawable.player_5);
		playerImage[6] = BitmapFactory.decodeResource(res,R.drawable.player_6);
		playerImage[7] = BitmapFactory.decodeResource(res,R.drawable.player_7);
		for(int i=0;i<playerImage.length;i++){
			playerImage[i] = massScale(playerImage[i]);
		}
		//操作キー画像
		key = BitmapFactory.decodeResource(res,R.drawable.vector);
		key = keyScale(key);
		//アイテムメニューボタン画像
		itemmenu = BitmapFactory.decodeResource(res, R.drawable.item);
		itemmenu = itemmenuScale(itemmenu);
		//斬撃画像
		zangekiimage = BitmapFactory.decodeResource(res, R.drawable.zanngeki);
		zangekiimage = massScale(zangekiimage);
		set_pos(4,4);
	}
	public void onUpdate(){

	}
	public void onDraw(Canvas canvas){
		//プレイヤーを画面の中心に配置
		draw_x = (height/2)+(blank/3)-(playerImage[0].getWidth()/2);
		draw_y = (height/2)-(playerImage[0].getHeight()/2);
		//プレイヤーの向き画像
		switch(vec){
		case 0://下向きの画像
			canvas.drawBitmap(playerImage[0], draw_x, draw_y, paint);
			break;
		case 1://左下向きの画像
			canvas.drawBitmap(playerImage[1], draw_x, draw_y,paint);
			break;
		case 2://左向きの画像
			canvas.drawBitmap(playerImage[2], draw_x, draw_y, paint);
			break;
		case 3://左上向きの画像
			canvas.drawBitmap(playerImage[3], draw_x, draw_y, paint);
			break;
		case 4://上向きの画像
			canvas.drawBitmap(playerImage[4], draw_x, draw_y, paint);
			break;
		case 5://右上向きの画像
			canvas.drawBitmap(playerImage[5], draw_x, draw_y, paint);
			break;
		case 6://右向きの画像
			canvas.drawBitmap(playerImage[6], draw_x, draw_y, paint);
			break;
		case 7://右下向きの画像
			canvas.drawBitmap(playerImage[7], draw_x, draw_y, paint);
			break;
		default:
			break;
		}
		paint.setColor(Color.argb(80, 0, 0, 0));
		//右側（キー）描画
		canvas.drawRect(height+(blank/3),0,width,height,paint);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				canvas.drawBitmap(key,height+(blank/3),(height/9)*5,paint);
			}
		}
		//左側メニュー描画(アイテム、条件文、階層)
		canvas.drawBitmap(itemmenu, 0, 0, paint);
		paint.setTextSize(40);
		paint.setColor(Color.WHITE);
		canvas.drawText("player.x:"+x+"player.y:"+y, 0, 200, paint);
	}
	public int get_x(){
		return x;
	}
	public int get_y(){
		return y;
	}
	public int get_hp(){
		return hp;
	}
	/**
	 *
	 * @param 方向（int）
	 */
	public void set_vec(int vec){
		this.vec=vec;
	}
	public void set_pos(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void set_param(int at, int hp, int maxhp){
		this.at = at;
		this.hp = hp;
		this.maxhp = maxhp;
	}
	public void plus_hp(int hp){
		this.hp+=hp;
	}
	public void plus_at(int num){
		at += num;
	}
	public void plus_df(int num){
		df += num;
	}
	public Bitmap massScale(Bitmap bitmap){
		bitmap=Bitmap.createScaledBitmap(bitmap,height/9,height/9,false);
		return bitmap;
	}
	public Bitmap keyScale(Bitmap bitmap){
		bitmap=Bitmap.createScaledBitmap(bitmap,(width-(height+(blank/3))),((height/9)*4),false);
		return bitmap;
	}
	public Bitmap itemmenuScale(Bitmap bitmap){
		bitmap=Bitmap.createScaledBitmap(bitmap, (blank/3),(height/8),false);
		return bitmap;
	}
	public void player_move(int vv, int xx, int yy){//プレイヤーの移動処理
		if(vec == vv){
			if(map_ins.map[x + xx][y + yy] == define.FLOOR){//移動先が床の場合
				map_ins.map[x][y] = define.FLOOR;//居た場所を床に書き換え
				map_ins.map[x + xx][y + yy] = define.PLAYER;//移動先のマップ番号を書き換える
				set_pos(x + xx, y + yy);//移動先の座標をセット
			}
		}
	}
	public void attack(){
	}
	public void onTouchEvent(MotionEvent event,cardData data){
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			if(event.getX()>height+(blank/3) && event.getX()<height+(blank/3)+key_width){
				if(event.getY()>((height/9)*5)+key_height && event.getY()<((height/9)*5)+(key_height*2)){
					if(data.getItemFlg() == false){
						set_vec(2);
						player_move(2, -1,  0);
					}
				}
			}
			//上
			if(event.getX()>height+(blank/3)+key_width && event.getX()<height+(blank/3)+key_width*2){
				if(event.getY()>((height/9)*5)&& event.getY()<((height/9)*5)+key_height){
					if(data.getItemFlg() == false){
						set_vec(4);
						player_move(4,  0, -1);
					}
				}
			}
			//右
			if(event.getX()>height+(blank/3)+(key_width*2) && event.getX()<height+(blank/3)+(key_width*3)){
				if(event.getY()>((height/9)*5)+key_height && event.getY()<((height/9)*5)+(key_height*2)){
					if(data.getItemFlg() == false){
						set_vec(6);
						player_move(6,  1,  0);
					}
				}
			}
			//下
			if(event.getX()>height+(blank/3)+key_width && event.getX()<height+(blank/3)+(key_width*2)){
				if(event.getY()>((height/9)*5)+(key_height*2)&& event.getY()<((height/9)*5)+(key_height*3)){
					if(data.getItemFlg() == false){
						set_vec(0);
						player_move(0,  0,  1);
					}
				}
			}
			//左上
			if(event.getX()>height+(blank/3) && event.getX()<height+(blank/3)+key_width){
				if(event.getY()>(height/9)*5&& event.getY()<((height/9)*5)+key_height){
					if(data.getItemFlg() == false){
						set_vec(3);
						player_move(3, -1, -1);
					}
				}
			}
			//左下
			if(event.getX()>height+(blank/3) && event.getX()<height+(blank/3)+key_width){
				if(event.getY()>((height/9)*5)+(key_height*2)&& event.getY()<((height/9)*5)+(key_height*3)){
					if(data.getItemFlg() == false){
						set_vec(1);
						player_move(1, -1,  1);
					}
				}
			}
			//右上
			if(event.getX()>height+(blank/3)+(key_width*2) && event.getX()<height+(blank/3)+(key_width*3)){
				if(event.getY()>(height/9)*5&& event.getY()<((height/9)*5)+key_height){
					if(data.getItemFlg() == false){
						set_vec(5);
						player_move(5,  1, -1);
					}
				}
			}
			//右下
			if(event.getX()>height+(blank/3)+(key_width*2) && event.getX()<height+(blank/3)+(key_width*3)){
				if(event.getY()>((height/9)*5)+(key_height*2)&& event.getY()<((height/9)*5)+(key_height*3)){
					if(data.getItemFlg() == false){
						set_vec(7);
						player_move(7,  1,  1);
					}
				}
			}
			//中央
			if(event.getX() > height+(blank/3)+key_width && event.getX() < height+(blank/3)+(key_width*2)){
				if(event.getY() > ((height/9)*5)+key_height && event.getY() < ((height/9)*5)+(key_width*2)){
					if(data.getItemFlg() == false){
						attack();
					}
				}
			}
		}
	}
}