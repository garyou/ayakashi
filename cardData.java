package com.ayakashi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
public class cardData {
	//カード情報データ配列
	private int intItem[]=new int[10];
	private String stringData[] =new String[10];
	private String dataArray[]=new String[10];
	private int[] cardholder = {1,2,3,4,5,1,2,3,4,5};
	private int itemNum=100;//何番目のアイテムを選んだか
 	private int item_width;//アイテムの横サイズ
	private int item_height;//アイテムの縦サイズ
	private int item_blank1_x;//アイテム欄のX余白1
	private int item_blank1_y;//アイテム欄のY余白1
	private int item_blank2_x;//アイテム欄のX余白2
	private int item_blank2_y;//アイテム欄のX余白2
	private boolean itemFlg=false;
	private boolean itemSelectFlg=false;//アイテムを押したかのフラグ
	private boolean itemConfirmFlg=false;//アイテム実行画面かどうかのフラグ

	private int width;
	private int height;
	private int blank;

	Resources r;
	Context context;
	//カードに種類によってサイズを変える
	private Bitmap[] card;//アイテムウインドウに表示するカード画像
	private Bitmap[] usecard;//カード使用画面に表示するカード画像
	private Bitmap[] cardinfo;//アイテムの詳細を表示するときのカード画像

	private Paint paint=new Paint();

	public cardData(Context context){
		height = GameActivity.height;
		width = GameActivity.width;
		blank = width - height;
		item_width=(height/18)*2;
		item_height=(((height/9)*4)/8)*2;
		item_blank1_x=height/18;
		item_blank1_y=((height/9)*4)/8;
		item_blank2_x=(height/18)*2;
		item_blank2_y=(((height/9)*4)/8)*2;

		r = context.getResources();
		this.context=context;
		card = new Bitmap[5];
		usecard = new Bitmap[5];
		cardinfo = new Bitmap[5];

		card[0] = BitmapFactory.decodeResource(r, R.drawable.kaihuku1);//回復
		card[1] = BitmapFactory.decodeResource(r, R.drawable.kougeki1);//武器
		card[2] = BitmapFactory.decodeResource(r, R.drawable.icon_d);//防具
		card[3] = BitmapFactory.decodeResource(r, R.drawable.fire);//攻撃(炎)
		card[4] = BitmapFactory.decodeResource(r, R.drawable.teleport);//瞬間移動

		usecard[0] = BitmapFactory.decodeResource(r, R.drawable.kaihuku2);//回復
		usecard[1] = BitmapFactory.decodeResource(r, R.drawable.kougeki2);//武器
		usecard[2] = BitmapFactory.decodeResource(r, R.drawable.icon_d);//防具
		usecard[3] = BitmapFactory.decodeResource(r, R.drawable.fire2);//攻撃(炎)
		usecard[4] = BitmapFactory.decodeResource(r, R.drawable.teleport);//瞬間移動

		cardinfo[0] = BitmapFactory.decodeResource(r, R.drawable.cardinfo_cure);//回復アイテムの詳細表示画像
		cardinfo[1] = BitmapFactory.decodeResource(r, R.drawable.cardinfo_at);//武器の詳細表示画像
		cardinfo[2] = BitmapFactory.decodeResource(r, R.drawable.cardinfo_df);//防具の詳細表示画像
		cardinfo[3] = BitmapFactory.decodeResource(r, R.drawable.cardinfo_fire);//炎アイテムの詳細表示画像
		cardinfo[4] = BitmapFactory.decodeResource(r, R.drawable.cardinfo_telepo);//瞬間移動の詳細表示画像

		for(int i=0;i<card.length;i++){
			card[i] = cardScale_t(card[i]);
			usecard[i] = cardScale_f(usecard[i]);
			cardinfo[i] = cardinfo_Scale(cardinfo[i]);
		}
	}
	/**
	 * カード情報を配列で保存する関数
	 * @param context コンテキスト
	 * @param data カード情報配列（文字列）
	 * @param PrefKey 格納キー
	 */
	public void saveCard(Context context,int[] data,String PrefKey){
		StringBuffer buffer =new StringBuffer();

		blank = GameActivity.width-GameActivity.height;
		String stringItem=null;
		for(int i=0;i<data.length;i++){
			stringData[i]=Integer.toString(data[i]);
		}
		for(String item : stringData){
			buffer.append(item+",");
		}
		if(buffer !=null){
			String buf=buffer.toString();
			stringItem=buf.substring(0,buf.length()-1);
			SharedPreferences prefs1 =context.getSharedPreferences("Array",Context.MODE_PRIVATE);
			SharedPreferences.Editor editor=prefs1.edit();
			editor.putString(PrefKey,stringItem).commit();
		}
	}
	/**
	 * カード情報を出力する関数
	 * @param context コンテキスト
	 * @param PrefKey 格納キー
	 */
	public int[] getArray(Context context,String PrefKey){
		SharedPreferences prefs2=context.getSharedPreferences("Array",Context.MODE_PRIVATE);
		String stringItem=prefs2.getString(PrefKey,"");
		dataArray=stringItem.split(",");
		if(stringItem!=null && stringItem.length()!=0){
			for(int i=0;i<dataArray.length;i++){
				cardholder[i]=Integer.parseInt(dataArray[i]);
			}
			return cardholder;
		}
		else{
			return null;
		}
	}
	public void onDraw(Canvas canvas){
		if(itemFlg){
			//背景を暗くする
			canvas.drawColor(Color.argb(150,0,0,0));
			paint.setColor(Color.argb(255,255,255,255));
			canvas.drawRect(height+(blank/3),0,width,height/8,paint);
			paint.setColor(Color.BLACK);
			paint.setTextSize(height/9);
			canvas.drawText("戻る",height+(blank/3)+(height/10),height/10,paint);
			//カードウィンドウ領域
			paint.setColor(Color.argb(255,255,255,255));
			canvas.drawRect(blank/3,(height/9)*5,height+(blank/3),height,paint);
			paint.setColor(Color.argb(255,0,0,0));
			paint.setTextSize(height/18);
			//上五個
			if(itemSelectFlg==false){
				for(int i=0;i<5;i++){
					if(cardholder[i]==1){
						if(i==0){
							canvas.drawBitmap(usecard[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
						}
						else{
							canvas.drawBitmap(usecard[0],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y,paint);
						}
					}
					if(cardholder[i]==2){
						if(i==0){
							canvas.drawBitmap(usecard[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
						}
						else{
							canvas.drawBitmap(usecard[1],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y,paint);
						}
					}
					if(cardholder[i]==3){
						if(i==0){
							canvas.drawBitmap(usecard[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
						}
						else{
							canvas.drawBitmap(usecard[2],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y,paint);
						}
					}
					if(cardholder[i]==4){
						if(i==0){
							canvas.drawBitmap(usecard[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
						}
						else{
							canvas.drawBitmap(usecard[3],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y,paint);
						}
					}
					if(cardholder[i]==5){
						if(i==0){
							canvas.drawBitmap(usecard[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
						}
						else{
							canvas.drawBitmap(usecard[4],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y,paint);
						}
					}
				}
				//下五個
				for(int i=0;i<5;i++){
					if(cardholder[i+5]==1){
						if(i==0){
							canvas.drawBitmap(usecard[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
						else{
							canvas.drawBitmap(usecard[0],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
					}
					if(cardholder[i+5]==2){
						if(i==0){
							canvas.drawBitmap(usecard[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
						else{
							canvas.drawBitmap(usecard[1],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
					}
					if(cardholder[i+5]==3){
						if(i==0){
							canvas.drawBitmap(usecard[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
						else{
							canvas.drawBitmap(usecard[2],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
					}
					if(cardholder[i+5]==4){
						if(i==0){
							canvas.drawBitmap(usecard[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
						else{
							canvas.drawBitmap(usecard[3],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
					}
					if(cardholder[i+5]==5){
						if(i==0){
							canvas.drawBitmap(usecard[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
						else{
							canvas.drawBitmap(usecard[4],(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i,(height/9)*5+item_blank1_y+item_blank2_y+item_height,paint);
						}
					}
				}
			}
			switch(itemNum){
			case 0:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			case 1:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			case 2:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			case 3:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			case 4:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			case 5:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			case 6:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			case 7:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			case 8:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			case 9:
				itemSelectFlg=true;
				if(cardholder[itemNum]==1){
				     canvas.drawBitmap(card[0],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
				     canvas.drawBitmap(cardinfo[0],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
				       (height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==2){
					canvas.drawBitmap(card[1],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[1],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==3){
					canvas.drawBitmap(card[2],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[2],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==4){
					canvas.drawBitmap(card[3],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[3],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				else if(cardholder[itemNum]==5){
					canvas.drawBitmap(card[4],(blank/3)+item_blank2_x,(height/9)*5+item_blank1_y,paint);
					canvas.drawBitmap(cardinfo[4],(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
							(height/9)*5+item_blank1_y,paint);
				}
				canvas.drawRect((blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,(height/9)*7+item_blank1_y,
						(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4),height,paint);
				canvas.drawRect((blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,(height/9)*7+item_blank1_y,
						(blank/3)+height,height,paint);
				paint.setColor(Color.WHITE);
				paint.setTextSize(height/27);
				canvas.drawText("使用する",(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2,
						(height/9)*8+item_blank1_y,paint);
				canvas.drawText("使用しない",(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4,
						(height/9)*8+item_blank1_y,paint);
				break;
			default:
				break;
			}
		}
		else{
		
		}
	}
	//アイテムフラグ切り替え
	public void itemChangeFlg(){
		itemFlg=!itemFlg;
	}
	//アイテムフラグ取得関数
	public boolean getItemFlg(){
		return itemFlg;
	}
	//アイテム選択フラグ切り替え
	public void itemSelectChangeFlg(){
		itemSelectFlg=!itemSelectFlg;
	}
	//アイテム選択フラグ取得関数
	public boolean getItemSelectFlg(){
		return itemSelectFlg;
	}

	//アイテム選択フラグ切り替え
	public void itemConfirmChangeFlg(){
		itemConfirmFlg=!itemConfirmFlg;
	}
	//アイテム選択フラグ取得関数
	public boolean getItemConfirmFlg(){
		return itemConfirmFlg;
	}
	public void set_itemNum(){
		itemNum = 100;
	}
	public void onTouchEvent(MotionEvent event){
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			//アイテムポップアップ
			if(event.getX()>0 && event.getX()<blank/3){
				if(event.getY()>0 && event.getY()<height/8){
					itemChangeFlg();//itemFlgをtrueに
				}
			}
			//ポップアップ終了
			if(event.getX()>height+(blank/3) && event.getX()<width){
				if(event.getY()>0 && event.getY()<height/10){
					itemChangeFlg();//itemFlgをfalseに
					saveCard(context, cardholder, "CARD");
				}
			}
			//アイテムボタンを押した状態
			if(itemFlg){
				if(itemConfirmFlg==false){
					//上五個
					for(int i=0;i<5;i++){
						if(cardholder[i]!=100){
							if(i==0){
								if(event.getX()>(blank/3)+item_blank2_x && event.getX()<(blank/3)+item_blank2_x+item_width){
									if(event.getY()>(height/9)*5+item_blank1_y && event.getY()<(height/9)*5+item_blank1_y+item_height){
										itemNum=0;
										itemConfirmFlg=true;
									}
								}
							}
							else{
								if(event.getX()>(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i && event.getX()<(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*(i+1)){
									if(event.getY()>(height/9)*5+item_blank1_y && event.getY()<(height/9)*5+item_blank1_y+item_height){
										itemNum=i;
										itemConfirmFlg=true;
									}
								}
							}
						}
					}
					for(int i=0;i<5;i++){
						if(cardholder[i+5]!=100){
							if(i==0){
								if(event.getX()>(blank/3)+item_blank2_x && event.getX()<(blank/3)+item_blank2_x+item_width){
									if(event.getY()>(height/9)*5+item_blank1_y+item_blank2_y+item_height && event.getY()<(height/9)*5+item_blank1_y+item_blank2_y+(item_height*2)){
										itemConfirmFlg=true;
										itemNum=5;
									}
								}
							}
							else{
								if(event.getX()>(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*i && event.getX()<(blank/3)+(item_blank1_x*i)+item_blank2_x+item_width*(i+1)){
									if(event.getY()>(height/9)*5+item_blank1_y+item_blank2_y+item_height && event.getY()<(height/9)*5+item_blank1_y+item_blank2_y+(item_height*2)){
										itemConfirmFlg=true;
										itemNum=i+5;
									}
								}
							}
						}
					}
				}
				else{
					//カードを使用する
					if(event.getX()>(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2
							&& event.getX()<(blank/3)+(item_blank1_x*2)+item_blank2_x+item_width*2+((height/18)*4)){
						if(event.getY()>(height/9)*7+item_blank1_y && event.getY()<height){
							//カードを使った効果
							if(cardholder[itemNum]==1){
						    }
						    else if(cardholder[itemNum]==2){
						    }
						    else if(cardholder[itemNum]==3){
						    }
							cardholder[itemNum]=100;
						    for(int i=0;i<10;i++){
						    	//もし要素の中身が100だった場合
						        if(cardholder[i]==100){
						        	for(int j=i;j<9;j++){
						        		cardholder[j]=cardholder[j+1];
						        	}
						        	cardholder[9]=100;
						        }
						    }
						    itemFlg=false;
						    itemSelectFlg=false;
						    itemConfirmFlg=false;
						    itemNum=100;
						    saveCard(context,cardholder, "CARD");
						}
					}
					//カードを使用しない
					if(event.getX()>(blank/3)+(item_blank1_x*4)+item_blank2_x+item_width*4
							&& event.getX()<(blank/3)+height){
						if(event.getY()>(height/9)*7+item_blank1_y && event.getY()<height){
							itemSelectFlg=false;
							itemConfirmFlg=false;
							itemNum=100;
						}
					}
				}
			}
		}
	}

	public Bitmap cardScale_t(Bitmap bitmap){//カード使用確認画面のカードのスケール変更
		try{
			bitmap=Bitmap.createScaledBitmap(bitmap,(height/9)*2,(height/9)*3,false);
		}catch(Exception e){

		}
		return bitmap;
	}
	public Bitmap cardScale_f(Bitmap bitmap){//メニューに表示されるカード画像のスケール変更
		bitmap=Bitmap.createScaledBitmap(bitmap,(height/18)*2,(height/18)*2,false);
		return bitmap;
	}
	public Bitmap cardinfo_Scale(Bitmap bitmap){//カードの詳細表示画像のスケール変更
		bitmap=Bitmap.createScaledBitmap(bitmap,(height/9)*2,(height/9)*3,false);
		return bitmap;
	}
}