//package com.ayakashi;
//import java.util.LinkedList;
//import java.util.Random;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.util.Log;
//public class Stage1_data{
//	private int i,j;//,ii;//ループ用変数
//	private static final int map_x = 29;
//	private static final int map_y = 29;
//
//	private int phase;
//	private int killcnt[];//倒した数
//	private int touchcnt;
//
//	private boolean[] playerattackflg;
//	private boolean enemyattackimagedraw;
//	private boolean enemyattackflg;
//	private int intervalCnt=0;
//	private boolean clearflg;
//	private boolean player_deadFlg;
//
//	private Bitmap[] enemyImageVector;
//
//	private int movenum;
//	private int createnum;//追加生成までの間隔乱数ver
//	private int createnum2;//追加生成までの間隔乱数ver
//
//	// ---------- ---------- ---------- ---------- ----------
//	// ここから追加
//	// 部屋に関係する定数
//	private static final int MAPSIZE = 29;	// 部屋全体の大きさ(MAPSIZE*MAPSIZE)
//	private static final int FOW = 4;		// FoundOutWall:視界外壁
//
//	// ステージの状態を表す定数
//	private static final int WALL = 0;		// WALL:壁
//	private static final int FLOOR = 1;		// FLOOR:床
//	private static final int PASSAGE = 14;	// PASSAGE:通路
//	private static final int UP_STAIR = 8 ;	// UPLAYER:上り階段座標指定
//	//private static final int DOWN_STAIR = 16;	// DOWN_POINT:下り階段座標指定
//	//private static final int POWERSPOT = 11;// POWER_SPOT:地脈の座標指定
//	private static final int PLAYER = 9;	// PLAYER_POINT:プレイヤー出現座標指定
//	private static final int MONSTER[] = new int[5];
//
//	// 数値が変化する定数(ステージによって)
//	// getter()を使用して変更する
//	private int ROOM = 6;			// 部屋数
//
//	// 変数
//	private int map[][];		// 部屋全体配列[0-28](29*29)
//	private int room[][];	// 同一の部屋判定に使用する配列[0-28](29*29)
//	private int fx[];				// FirstX: X初期座標
//	private int fy[];				// FirstY: Y初期座標
//	private int lx[];				// LastX:  X最終座標
//	private int ly[];				// LastY:  Y最終座標
//	private int ran_roomX;	// ランダムな部屋座標X
//	private int ran_roomY;	// ランダムな部屋座標Y
//	private int ran_roomN[];	// ランダムな部屋番号
//	private int ran_roomT[];	// ランダムな部屋生成開始座標
//	private int exitx[];			// 通路を通すための出口座標
//	private int exity[];			// 通路を通すための出口座標
//	private int m_pointx[];			// モンスター出現座標X
//	private int m_pointy[];			// モンスター出現座標Y
//	private int floor;			// 階数(フロア)情報:今プレイヤーが何階にいるかの情報
//	private boolean br_flg;			// Break Flag:脱出フラグ
//
//	private Bitmap massimage;
//	private Bitmap wallimage;
//	private Bitmap enemyimage;
//	private Bitmap enemyimage2;
//	private Bitmap zangekiimage;
//	private Bitmap kaidanimage;
//	private Bitmap powerspot;
//
//	private Random rnd;
//	private Paint paint;
//	Player player;
//	cardData data;
//	LinkedList<Enemy> enemy;
//	int blank;
//	float height;
//	float var;
//
//	// ここまで追加
//	// ---------- ---------- ---------- ---------- ----------
//
//	// コンストラクタ
//	public Stage1_data(Context context,Player player){
//		data=new cardData();
//		for(int ii=0;ii<5;ii++){
//			MONSTER[ii] = ii+2;
//			enemy.add(new Enemy(player));
//		}
//		killcnt = new int[6];
//		enemyImageVector = new Bitmap[8];
//		playerattackflg = new boolean[5];
//		enemyattackflg = false;
//		phase = 0;
//		touchcnt = 0;
//		movenum = 100;
//		clearflg = false;
//		player_deadFlg = false;
//
//		Resources res = context.getResources();
//		massimage = BitmapFactory.decodeResource(res, R.drawable.floor);
//		wallimage = BitmapFactory.decodeResource(res, R.drawable.stonewall2);
//		enemyimage = BitmapFactory.decodeResource(res, R.drawable.shirobouzu);
//		enemyimage2 = BitmapFactory.decodeResource(res, R.drawable.biroon);
//		zangekiimage = BitmapFactory.decodeResource(res, R.drawable.zanngeki);
//		powerspot = BitmapFactory.decodeResource(res, R.drawable.powerspot);
//
//		massimage = mapScale(massimage);
//		wallimage = mapScale(wallimage);
//		enemyimage = mapScale(enemyimage);
//		enemyimage2 = mapScale(enemyimage2);
//		zangekiimage = mapScale(zangekiimage);
//		powerspot = mapScale(powerspot);
//
//		kaidanimage = BitmapFactory.decodeResource(res, R.drawable.stairs);
//		kaidanimage = mapScale(kaidanimage);
//
//		enemyImageVector[0] = BitmapFactory.decodeResource(res, R.drawable.enemy_0);
//		enemyImageVector[1] = BitmapFactory.decodeResource(res, R.drawable.enemy_1);
//		enemyImageVector[2] = BitmapFactory.decodeResource(res, R.drawable.enemy_2);
//		enemyImageVector[3] = BitmapFactory.decodeResource(res, R.drawable.enemy_3);
//		enemyImageVector[4] = BitmapFactory.decodeResource(res, R.drawable.enemy_4);
//		enemyImageVector[5] = BitmapFactory.decodeResource(res, R.drawable.enemy_5);
//		enemyImageVector[6] = BitmapFactory.decodeResource(res, R.drawable.enemy_6);
//		enemyImageVector[7] = BitmapFactory.decodeResource(res, R.drawable.enemy_7);
//		for(int i=0;i<8;i++){
//			enemyImageVector[i] = mapScale(enemyImageVector[i]);
//		}
//
//		blank = GameActivity.width - GameActivity.height;
//		height = GameActivity.height;
//		var = height / player.get_hp();
//
//		map_create();
//	}
//
//
//	// 初期化関数
//		public void init(){
//			rnd = new Random();	// newしないとエラー発生
//			paint = new Paint();
//			map = new int[MAPSIZE][MAPSIZE];
//			room = new int[MAPSIZE][MAPSIZE];
//			fx = new int[ROOM];
//			fy = new int[ROOM];
//			lx = new int[ROOM];
//			ly = new int[ROOM];
//			ran_roomN = new int[ROOM];
//			ran_roomT = new int[3];
//			exitx = new int[ROOM];
//			exity = new int[ROOM];
//			m_pointx = new int[ROOM];
//			m_pointy = new int[ROOM];
//			floor = 1;
//			br_flg = false;
//
//			// 左上の座標[4]or[11]or[18]を決めている
//			for(int ii=0;ii<3;ii++)	ran_roomT[ii]=FOW+7*ii;
//
//			// 全てを壁(0)で初期化
//			for(int ii=0;ii<MAPSIZE;ii++){
//				for(int jj=0;jj<MAPSIZE;jj++)	map[ii][jj] = WALL;
//			}
//			// 視界外壁(4)を配置
//			for(int ii=0;ii<MAPSIZE;ii++){
//				for(int jj=0;jj<MAPSIZE;jj++){
//					if(ii<FOW || jj<FOW)	map[ii][jj] = WALL;
//					if(ii>=MAPSIZE-FOW || jj>=MAPSIZE-FOW)map[ii][jj] = WALL;
//				}
//			}
//
//			blank = GameActivity.width - GameActivity.height;
//			height = GameActivity.height;
//			var = height / player.get_hp();
//		}
//	// マップ生成関数
//	public void map_create(){
//		random();
//		room_create();
//		monster_create();
//		player_create();
//		step_create(floor);
//		root_create();
//		for(int ii=0;ii<5;ii++){
//			room_check(ii);
//		}
//		for(int cc=0;cc<ROOM;cc++){
//			map[exitx[cc]][exity[cc]] = FLOOR;
//		}
//	}
//
//
//	public void map_recreate(){
//		floor++;
//		init();
//		map_create();
//		step_create(floor);
//		for(int ii=1;ii<6;ii++){
//			character_create(ii);
//			room_check(ii-1);
//		}
//	}
//	// ランダム部屋生成
//	public void random(){
//		for(int cc=0;cc<ROOM;cc++){
//			while(true){
//				ran_roomN[cc] = rnd.nextInt(9)+1;	// ran_roomN:[1-9]
//				if(cc==0)	br_flg=true;
//				else{
//					// 部屋を上書きしないようにするための処理
//					// 主に出入口を書き換えないようにしている
//					for(int ii=0;ii<cc;ii++){
//						// 部屋番号が同じ場合は書き換えない
//						if(ran_roomN[cc] == ran_roomN[ii]){
//							br_flg=false;
//							break;
//						}
//						else	br_flg=true;
//					}
//				}
//
//				// 部屋番号が重複していないことを確認
//				// 部屋番号による部屋生成開始位置座標の指定番号振り
//				if(br_flg==true){
//					if(ran_roomN[cc] == 1){
//						ran_roomX = 0;
//						ran_roomY = 0;
//					}
//					if(ran_roomN[cc] == 4){
//						ran_roomX = 0;
//						ran_roomY = 1;
//					}
//					if(ran_roomN[cc] == 7){
//						ran_roomX = 0;
//						ran_roomY = 2;
//					}
//					if(ran_roomN[cc] == 2){
//						ran_roomX = 1;
//						ran_roomY = 0;
//					}
//					if(ran_roomN[cc] == 5){
//						ran_roomX = 1;
//						ran_roomY = 1;
//					}
//					if(ran_roomN[cc] == 8){
//						ran_roomX = 1;
//						ran_roomY = 2;
//					}
//					if(ran_roomN[cc] == 3){
//						ran_roomX = 2;
//						ran_roomY = 0;
//					}
//					if(ran_roomN[cc] == 6){
//						ran_roomX = 2;
//						ran_roomY = 1;
//					}
//					if(ran_roomN[cc] == 9){
//						ran_roomX = 2;
//						ran_roomY = 2;
//					}
//					break;
//				}
//			}
//			// 生成位置に部屋が存在していない場合
//			// ランダムな部屋の生成位置を決めている
//
//			// 左上の座標fx,fy
//			fx[cc] = ran_roomT[ran_roomX];	// [4]or[11]or[18]
//			fy[cc] = ran_roomT[ran_roomY];	// [4]or[11]or[18]
//
//			// 右下の座標lx,ly
//			lx[cc] = fx[cc] + rnd.nextInt(3) + 4;	// [8-10]
//			ly[cc] = fy[cc] + rnd.nextInt(3) + 4;	// [8-10]
//
//			// 部屋の最小構成5*5
//			// 部屋の最大構成7*7
//			// 部屋の端は壁で覆うので3*3～5*5の部屋を生成する
//			// [5-7]*[5-7]の部屋が出来る
//
//			// 部屋の出入口位置を決めている
//			// 出入口は壁の位置に沿って生成する
//			int ran = rnd.nextInt(4);
//			if(ran==0){
//				exitx[cc] = rnd.nextInt(lx[cc]-fx[cc]-1)+(fx[cc]+1);
//				exity[cc] = fy[cc];
//			}
//			else if(ran==1){
//				exitx[cc] = fx[cc];
//				exity[cc] = rnd.nextInt(ly[cc]-fy[cc]-1)+(fy[cc]+1);
//			}
//			else if(ran==2){
//				exitx[cc] = rnd.nextInt(lx[cc]-fx[cc]-1)+(fx[cc]+1);
//				exity[cc] = ly[cc]-1;
//			}
//			else if(ran==3){
//				exitx[cc] = lx[cc]-1;
//				exity[cc] = rnd.nextInt(ly[cc]-fy[cc]-1)+(fy[cc]+1);
//			}
//
//		}
//	}
//
//	// 部屋内部生成
//	public void room_create(){
//		// ランダムな場所(9か所のどこか)に部屋を生成
//		// ①②③
//		// ④⑤⑥
//		// ⑦⑧⑨
//		for(int cc=0;cc<ROOM;cc++){
//			for(int ii=fx[cc]+1;ii<lx[cc];ii++){
//				for(int jj=fy[cc]+1;jj<ly[cc];jj++){
//					map[ii][jj] = FLOOR;
//				}
//
//			}
//		}
//	}
//
//	// 同一部屋判定
//	public void room_check(int num){
//		for(int cc=0;cc<ROOM;cc++){
//			for(int ii=fx[cc];ii<lx[cc];ii++){
//				for(int jj=fy[cc];jj<ly[cc];jj++){
//					room[ii][jj] = ran_roomN[cc];
//				}
//			}
//			if(room[player.get_x()][player.get_y()] == room[enemy.get(num).get_x()][enemy.get(num).get_y()]){
//				enemy.get(num).onsameFlg();
//			}
//			else{
//				enemy.get(num).offsameFlg();
//			}
//		}
//	}
//
//	// モンスター生成ポイントの作成
//	// ここでモンスターをセットしてほしい(リクリエイトする関係上)
//	public void monster_create(){// モンスター生成位置を決めている
//		// モンスター生成位置は部屋内に生成する(壁や出入口とは被らない)
//		for(int cc=0;cc<ROOM;cc++){
//			m_pointx[cc] = rnd.nextInt(lx[cc]-fx[cc]-2)+(fx[cc]+1);	// [8-10][13-15][18-20]
//			m_pointy[cc] = rnd.nextInt(ly[cc]-fy[cc]-2)+(fy[cc]+1); // [8-10][13-15][18-20]
//		}
//		for(int i=0;i<5;i++){
//			map[m_pointx[i+1]][m_pointy[i+1]] = MONSTER[i];// 部屋にモンスター出現ポイントを生成
//		}
//		map[m_pointx[0]][m_pointy[0]] = PLAYER;
//
//		//プレイヤークラスにセット
//		player.set_pos(m_pointx[0],m_pointy[0]);
//		player.set_param(10, 5000,5000);
//		for(int ii=0;ii<enemy.size();ii++){
//			enemy.get(ii).set_pos(m_pointx[ii+1], m_pointy[ii+1]);
//			if(ii < 4){
//				enemy.get(ii).set_param(5, 10);
//			}
//			else{
//				enemy.get(ii).set_param(7, 15);
//			}
//		}
//	}
//
//	public void character_create(int num){//numは１以上
////		Log.d("","追加生成"+num);
//		// モンスター生成位置を決めている
//		// モンスター生成位置は部屋内に生成する(壁や出入口とは被らない)
//		m_pointx[num] = rnd.nextInt(lx[num]-fx[num]-2)+(fx[num]+1);	// [8-10][13-15][18-20]
//		m_pointy[num] = rnd.nextInt(ly[num]-fy[num]-2)+(fy[num]+1); // [8-10][13-15][18-20]
//		map[m_pointx[num]][m_pointy[num]] = MONSTER[num-1];// 部屋にモンスター出現ポイントを生成
//
//		if(map[m_pointx[num]][m_pointy[num]] != PLAYER){
//			enemy.get(num-1).set_pos(m_pointx[num], m_pointy[num]);
//		}
//		if(num-1 < 4){
//			enemy.get(num-1).set_param(5, 10);
//			enemy.get(num-1).offdeadFlg();
//		}
//		else{
//			enemy.get(num-1).set_param(7, 15);
//			enemy.get(num-1).offdeadFlg();
//			}
//	}
//
//	// リクリエイト用にパラメータ引数を設けたほうがよいと思われる
//	public void player_create(){
//		// プレイヤークラスにセット
//		// モンスターの生成位置のうち1か所をプレイヤーの生成ポイントにする
//		//player.p_set_pos(m_pointx[0],m_pointy[0]);	// ポジションのセット
//		//player.p_set_param(10, 50, 50);				// パラメータのセット
//		player.set_pos(m_pointx[0],m_pointy[0]);	// ポジションのセット
//		player.set_param(10, 50, 50);				// パラメータのセット
//	}
//
//	// 階段生成
//	public void step_create(int floor){
//		// floor:階数情報変数[1-5]
//		// 階段生成ポイントをモンスターのポイントのどこかに上書き
//		if(floor==1){
//			map[m_pointx[ROOM-1]][m_pointy[ROOM-1]] = UP_STAIR;
//		}
//		else if(floor==5){
//			//map[m_pointx[0]][m_pointy[0]] = DOWN_STAIR;
//		}
//		else{
//			map[m_pointx[ROOM-1]][m_pointy[ROOM-1]] = UP_STAIR;
//			//map[m_pointx[0]][m_pointy[0]] = DOWN_STAIR;
//		}
//	}
//
//	// 通路生成
//	// 出入口同士を最短距離で繋げてその間に道を生成する
//	// ただし、斜め方向には繋げない
//	// X軸方向から先に探査する
//	public void root_create(){
//		for(int ii=0;ii<ROOM-1;ii++){// [0-5]
//			int jj=ii+1;// [1-6]
//			int cntx=0;
//			int cnty=0;
//
//			for(int cc=0;cc<MAPSIZE;cc++){
//				if(exitx[ii]-cntx > exitx[jj]){
//					map[exitx[ii]-cntx][exity[ii]] = PASSAGE;
//					cntx++;
//				}
//				else if(exitx[ii]+cntx < exitx[jj]){
//					map[exitx[ii]+cntx][exity[ii]] = PASSAGE;
//					cntx++;
//				}
//				else if(exitx[ii]-cntx==exitx[jj]){
//					if(exity[ii]-cnty > exity[jj]){
//						map[exitx[ii]-cntx][exity[ii]-cnty] = PASSAGE;
//					}
//					else if(exity[ii]+cnty < exity[jj]){
//						map[exitx[ii]-cntx][exity[ii]+cnty] = PASSAGE;
//					}
//					else if(exity[ii]-cnty==exity[jj]){
//						map[exitx[ii]-cntx][exity[ii]-cnty] = PASSAGE;
//						break;
//					}
//					else if(exity[ii]+cnty==exity[jj]){
//						map[exitx[ii]-cntx][exity[ii]+cnty] = PASSAGE;
//						break;
//					}
//					cnty++;
//				}
//				else if(exitx[ii]+cntx==exitx[jj]){
//					if(exity[ii]-cnty > exity[jj]){
//						map[exitx[ii]+cntx][exity[ii]-cnty] = PASSAGE;
//					}
//					else if(exity[ii]+cnty < exity[jj]){
//						map[exitx[ii]+cntx][exity[ii]+cnty] = PASSAGE;
//					}
//					else if(exity[ii]-cnty==exity[jj]){
//						map[exitx[ii]+cntx][exity[ii]-cnty] = PASSAGE;
//						break;
//					}
//					else if(exity[ii]+cnty==exity[jj]){
//						map[exitx[ii]+cntx][exity[ii]+cnty] = PASSAGE;
//						break;
//					}
//					cnty++;
//				}
//			}
//		}
//	}
//
//	public boolean onUpdate(){
//		if(phase == 0){
//			player_move();
//		}
//		if(phase == 1){
//			intervalCnt++;
//			if(intervalCnt==20){
////				for(int ii=0;ii<enemy.size();ii++){
////					findcheck(ii);
////				}
//				for(int ii=0;ii<enemy.size();ii++){
//					if(enemy.get(ii).get_hp() > 0){
//						intervalCnt=0;
//						chase(ii);	
//						
//					}
//				}
//				phase=2;
//			}
//		}
//		if(movenum == 100){
//			for(int ii=0;ii<enemy.size();ii++){
//				playerattackflg[ii] = false;
//			}
//		}
//
//		if(player.get_hp() <= 0){
//			player_dead();
//		}
//		for(int ii=0;ii<enemy.size();ii++){
//			if(enemy.get(ii).get_hp() <= 0){
//				map[enemy.get(ii).get_x()][enemy.get(ii).get_y()] = 1;
//				killcnt[ii] = 1;
//			}
////			sameroomcheck(ii);
//		}
//		killcnt[5] = killcnt[0] + killcnt[1] + killcnt[2] + killcnt[3] + killcnt[4];
////			step_create(1);
//		if(phase == 2){
//			phase = 0;
////			turn++;
//			enemyattackflg = false;
//		}
////		checkclear();
////		checkspot();
//
//		if(killcnt[5] > 0){
//			createnum = rnd.nextInt(40)+61;//60~100;
//			createnum2 = (rnd.nextInt(createnum)+1);
//			for(int ii=0;ii<5;ii++){
//				if(killcnt[ii] == 1){
////					createcnt[ii]++;
////					if(createcnt[ii] == 300){
//					if(createnum2 == 1){
//						killcnt[ii] = 0;
//						character_create(ii+1);
//					}
//				}
//			}
//		}
//
//		return true;
//	}
//	public void onDraw(Canvas canvas){
//		//プレイヤーから４マス範囲の情報を取得し、描画
//		for(i=0;i<9;i++){
//			for(j=0;j<9;j++){
//				//普通のマスだった場合
//				if(map[(player.get_x()-4)+i][(player.get_y()-4)+j] == FLOOR || map[(player.get_x()-4)+i][(player.get_p_y()-4)+j] == PASSAGE){
//					canvas.drawBitmap(massimage,data.drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
//				}
//				else if(map[(player.get_x()-4)+i][(player.get_y()-4)+j] == 7){
//					canvas.drawBitmap(powerspot,Data.drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
//				}
//				else if(map[(player.get_x()-4)+i][(player.get_y()-4)+j] == UP_STAIR){
//					canvas.drawBitmap(kaidanimage,Data.drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
//				}
//				//プレイヤーのマス
//				else if(map[(player.get_x()-4)+i][(player.get_y()-4)+j] == PLAYER){
//					canvas.drawBitmap(massimage,cardData.drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
//					if(enemyattackflg == true && player.get_hp() >= 0 && player.get_atcnt() > 1){
//						enemyattackimagedraw = true;
//					}
//					else{
//						enemyattackimagedraw = false;
//					}
//				}
//				//壁だった場合
//				else if(map[(player.get_p_x()-4)+i][(player.get_p_y()-4)+j] == WALL){
//					canvas.drawBitmap(wallimage,Data.drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
//				}
//				//敵のいるマスだった場合
//				for(int ii=0;ii<enemy.size();ii++){
//					if(map[(player.get_p_x()-4)+i][(player.get_p_y()-4)+j] == MONSTER[ii]){
//						canvas.drawBitmap(massimage,Data.drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
//						if(enemy.get(ii).get_hp() > 0){
//							if(ii < 4){
//								canvas.drawBitmap(enemyimage,Data.drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
//							}
//							if(ii == 4){
//								for(int lp=0;lp<8;lp++){
//									if(e_Vector.get(ii).get_E_Vec() == lp){
//										canvas.drawBitmap(enemyImageVector[lp],Data.drawmap_width+(int)(GameActivity.height/9)*i,(int)(GameActivity.height/9)*j,paint);
//									}
//								}
//							}
//							if(playerattackflg[ii] == true){
//								canvas.drawBitmap(zangekiimage, Data.drawmap_width+(int)(GameActivity.height/9)*i, (int)(GameActivity.height/9)*j, paint);
//							}
//						}
//					}
//				}
//			}
//		}
//
//		paint.setColor(Color.argb(80,0,0,255));
//		//簡易マップ描画
//		for(int i=0;i<MAPSIZE;i++){
//			for(int j=0;j<MAPSIZE;j++){
//				if(map[i][j] != WALL){
//					canvas.drawRect(((GameActivity.height/100)*i),(GameActivity.height/9)*2+(GameActivity.height/100)*j,
//							((GameActivity.height/100)*(i+1)),(GameActivity.height/9)*2+(GameActivity.height/100)*(j+1),paint);
//				}
//			}
//		}
//		//階段の位置を黄色の円で描画
//		paint.setColor(Color.YELLOW);
//		for(int i=0;i<map_x;i++){
//			for(int j=0;j<map_y;j++){
//				if(map[i][j] == UP_STAIR){
//					canvas.drawCircle((GameActivity.height/100)*20+GameActivity.height/200,
//							(GameActivity.height/9)*2+(GameActivity.height/100)*6+GameActivity.height/200,GameActivity.height/200,paint);
//				}
//			}
//		}
//		//ミニマップに敵を描画
//		paint.setColor(Color.RED);
//		for(int ii=0;ii<enemy.size();ii++){
//			if(enemy.get(ii).get_hp() > 0){
//				canvas.drawCircle((GameActivity.height/100)*enemy.get(ii).get_x()+GameActivity.height/200,
//						(GameActivity.height/9)*2+(GameActivity.height/100)*enemy.get(ii).get_y()+GameActivity.height/200,GameActivity.height/200,paint);
//			}
//		}
//		paint.setColor(Color.MAGENTA);
//		if(map[5][6] == 7){
//			canvas.drawCircle((GameActivity.height/100)*5+GameActivity.height/200,
//					(GameActivity.height/9)*2+(GameActivity.height/100)*6+GameActivity.height/200,GameActivity.height/200,paint);
//		}
//		//ミニマップにプレイヤーを丸で描画
//		paint.setColor(Color.CYAN);
//		canvas.drawCircle((GameActivity.height/100)*player.get_p_x()+GameActivity.height/200,
//				(GameActivity.height/9)*2+(GameActivity.height/100)*player.get_p_y()+GameActivity.height/200,GameActivity.height/200,paint);
//
//
//
//		//PLAYERのHP表示
//		paint.setColor(Color.GREEN);
//		canvas.drawRect(blank/3,0,(blank/3)+var*player.get_p_hp(),GameActivity.height/18,paint);
//		paint.setColor(Color.BLACK);
//		paint.setTextSize(GameActivity.height/20);
//		canvas.drawText(""+player.get_p_hp()+"/"+player.get_p_mhp(),(blank/3)+(GameActivity.height/2)-(GameActivity.height/20),GameActivity.height/18,paint);
////			for(int ii=0;ii<enemy.size();ii++){
////				canvas.drawText("find:"+findcheckFlg[ii], 200, 100+(ii*50), paint);
////				canvas.drawText("M_P:"+map[enemy.get(ii).get_x()][enemy.get(ii).get_y()], 300, 350+(ii*50), paint);
////			}
//		paint.setColor(Color.RED);
//		paint.setTextSize(15);
////			for(int ii=0;ii<map_x;ii++){
////				for(int jj=0;jj<map_y;jj++){
////					canvas.drawText("["+map[ii][jj]+"]", (ii)*35, (jj)*25, paint);//map番号の表示
////				}
////			}
//		paint.setColor(Color.BLUE);
//		paint.setTextSize(40);
//		for(int ii=0;ii<5;ii++){
////				canvas.drawText("EV:"+enemyImageVector[8], 150, 100, paint);
////				canvas.drawText("生成時間:"+createcnt[ii], 550, 100+(ii*50), paint);
////				canvas.drawText("生成乱数:"+createnum, 550, 100, paint);
////				canvas.drawText("生成乱数2:"+createnum2, 550, 150, paint);
////				canvas.drawText("EHP:"+enemy.get(ii).get_hp(), 150, 100+(ii*40), paint);
////				canvas.drawText("E:"+map[enemy.get(ii).get_x()][enemy.get(ii).get_y()], 350, 100+(ii*40), paint);
////				canvas.drawText("E:"+enemy.get(ii).get_deadFlg(), 100, 100+(ii*40), paint);
//		}
//	}
//
//
//	public void player_move(){
//		if(movenum == 0){//下
//			touchcnt++;
//			if(touchcnt % 4 == 0){
//				if(map[player.get_p_x()][player.get_p_y()+1] == FLOOR || map[player.get_p_x()][player.get_p_y()+1] == UP_STAIR || map[player.get_p_x()][player.get_p_y()+1] == 7
//						|| map[player.get_p_x()][player.get_p_y()+1] == PASSAGE){
//					map[player.get_p_x()][player.get_p_y()] = 1;
//					map[player.get_p_x()][player.get_p_y()+1] = PLAYER;
//					player.p_set_pos(player.get_p_x(), player.get_p_y()+1);
//					if(killcnt[5] != 5)phase = 1;
//				}
//				else if(map[player.get_p_x()][player.get_p_y()+1] == UP_STAIR){
//					map_recreate();
//				}
//				else{
//					map[player.get_p_x()][player.get_p_y()] = PLAYER;
//				}
//			}
//		}
//		if(movenum == 1){//左下
//			touchcnt++;
//			if(touchcnt % 4 == 0){
//				if(map[player.get_p_x()-1][player.get_p_y()+1] == FLOOR || map[player.get_p_x()-1][player.get_p_y()+1] == 8 || map[player.get_p_x()-1][player.get_p_y()+1] == 7
//						|| map[player.get_p_x()-1][player.get_p_y()+1] == PASSAGE){
//					map[player.get_p_x()][player.get_p_y()] = 1;
//					map[player.get_p_x()-1][player.get_p_y()+1] = PLAYER;
//					player.p_set_pos(player.get_p_x()-1, player.get_p_y()+1);
//					if(killcnt[5] != 5)phase = 1;
//				}
//				else if(map[player.get_p_x()-1][player.get_p_y()+1] == UP_STAIR){
//					map_recreate();
//				}
//				else{
//					map[player.get_p_x()][player.get_p_y()] = PLAYER;
//				}
//			}
//		}
//		if(movenum == 2){//左
//			touchcnt++;
//			if(touchcnt % 4 == 0){
//				if(map[player.get_p_x()-1][player.get_p_y()] == FLOOR || map[player.get_p_x()-1][player.get_p_y()] == 8 || map[player.get_p_x()-1][player.get_p_y()] == 7
//						|| map[player.get_p_x()-1][player.get_p_y()] == PASSAGE){
//					map[player.get_p_x()][player.get_p_y()] = 1;
//					map[player.get_p_x()-1][player.get_p_y()] = PLAYER;
//					player.p_set_pos(player.get_p_x()-1, player.get_p_y());
//					if(killcnt[5] != 5)phase = 1;
//				}
//				else if(map[player.get_p_x()-1][player.get_p_y()] == UP_STAIR){
//					map_recreate();
//				}
//				else{
//					map[player.get_p_x()][player.get_p_y()] = PLAYER;
//				}
//			}
//		}
//		if(movenum == 3){//左上
//			touchcnt++;
//			if(touchcnt % 4 == 0){
//				if(map[player.get_p_x()-1][player.get_p_y()-1] == FLOOR || map[player.get_p_x()-1][player.get_p_y()-1] == 8 || map[player.get_p_x()-1][player.get_p_y()-1] == 7
//						|| map[player.get_p_x()-1][player.get_p_y()-1] == PASSAGE){
//					map[player.get_p_x()][player.get_p_y()] = 1;
//					map[player.get_p_x()-1][player.get_p_y()-1] = PLAYER;
//					player.p_set_pos(player.get_p_x()-1, player.get_p_y()-1);
//					if(killcnt[5] != 5)phase = 1;
//				}
//				else if(map[player.get_p_x()-1][player.get_p_y()-1] == UP_STAIR){
//					map_recreate();
//				}
//				else{
//					map[player.get_p_x()][player.get_p_y()] = PLAYER;
//				}
//			}
//		}
//		if(movenum == 4){//上
//			touchcnt++;
//			if(touchcnt % 4 == 0){
//				if(map[player.get_p_x()][player.get_p_y()-1] == FLOOR || map[player.get_p_x()][player.get_p_y()-1] == 8 || map[player.get_p_x()][player.get_p_y()-1] == 7
//						|| map[player.get_p_x()][player.get_p_y()-1] == PASSAGE){
//					map[player.get_p_x()][player.get_p_y()] = 1;
//					map[player.get_p_x()][player.get_p_y()-1] = PLAYER;
//					player.p_set_pos(player.get_p_x(), player.get_p_y()-1);
//					if(killcnt[5] != 5)phase = 1;
//				}
//				else if(map[player.get_p_x()][player.get_p_y()-1] == UP_STAIR){
//					map_recreate();
//				}
//				else{
//					map[player.get_p_x()][player.get_p_y()] = PLAYER;
//				}
//			}
//		}
//		if(movenum == 5){//右上
//			touchcnt++;
//			if(touchcnt % 4 == 0){
//				if(map[player.get_p_x()+1][player.get_p_y()-1] == FLOOR || map[player.get_p_x()+1][player.get_p_y()-1] == 8 || map[player.get_p_x()+1][player.get_p_y()-1] == 7
//						|| map[player.get_p_x()+1][player.get_p_y()-1] == PASSAGE){
//					map[player.get_p_x()][player.get_p_y()] = 1;
//					map[player.get_p_x()+1][player.get_p_y()-1] = PLAYER;
//					player.p_set_pos(player.get_p_x()+1, player.get_p_y()-1);
//					if(killcnt[5] != 5)phase = 1;
//				}
//				else if(map[player.get_p_x()+1][player.get_p_y()-1] == UP_STAIR){
//					map_recreate();
//				}
//				else{
//					map[player.get_p_x()][player.get_p_y()] = PLAYER;
//				}
//			}
//		}
//		if(movenum == 6){//右
//			touchcnt++;
//			if(touchcnt % 4 == 0){
//				if(map[player.get_p_x()+1][player.get_p_y()] == FLOOR || map[player.get_p_x()+1][player.get_p_y()] == 8 || map[player.get_p_x()+1][player.get_p_y()] == 7
//						|| map[player.get_p_x()+1][player.get_p_y()] == PASSAGE){
//					map[player.get_p_x()][player.get_p_y()] = 1;
//					map[player.get_p_x()+1][player.get_p_y()] = PLAYER;
//					player.p_set_pos(player.get_p_x()+1, player.get_p_y());
//					if(killcnt[5] != 5)phase = 1;
//				}
//				else if(map[player.get_p_x()+1][player.get_p_y()] == UP_STAIR){
//					map_recreate();
//				}
//				else{
//					map[player.get_p_x()][player.get_p_y()] = PLAYER;
//				}
//			}
//		}
//		if(movenum == 7){//右下
//			touchcnt++;
//			if(touchcnt % 4 == 0){
//				if(map[player.get_p_x()+1][player.get_p_y()+1] == FLOOR || map[player.get_p_x()+1][player.get_p_y()+1] == 8 || map[player.get_p_x()+1][player.get_p_y()+1] == 7
//						|| map[player.get_p_x()+1][player.get_p_y()+1] == PASSAGE){
//					map[player.get_p_x()][player.get_p_y()] = 1;
//					map[player.get_p_x()+1][player.get_p_y()+1] = PLAYER;
//					player.p_set_pos(player.get_p_x()+1, player.get_p_y()+1);
//					if(killcnt[5] != 5)phase = 1;
//				}
//				else if(map[player.get_p_x()+1][player.get_p_y()+1] == UP_STAIR){
//					map_recreate();
//				}
//				else{
//					map[player.get_p_x()][player.get_p_y()] = PLAYER;
//				}
//			}
//		}
//		if(movenum == 8){
//			player_attack();
//		}
//	}
//	public void player_attack(){
//		for(int ii=0;ii<enemy.size();ii++){
//			if(map[player.get_p_x()-1][player.get_p_y()] == MONSTER[ii]){
//				if(vector.getVec() == 2){
//					playerattackflg[ii] = true;
//					enemy.get(ii).player_attack(player.get_p_at());
//					phase = 1;
//				}
//			}
//			if(map[player.get_p_x()+1][player.get_p_y()] == MONSTER[ii]){
//				if(vector.getVec() == 6){
//					playerattackflg[ii] = true;
//					enemy.get(ii).player_attack(player.get_p_at());
//					phase = 1;
//				}
//			}
//			if(map[player.get_p_x()][player.get_p_y()-1] == MONSTER[ii]){
//				if(vector.getVec() == 4){
//					playerattackflg[ii] = true;
//					enemy.get(ii).player_attack(player.get_p_at());
//					phase = 1;
//				}
//			}
//			if(map[player.get_p_x()][player.get_p_y()+1] == MONSTER[ii]){
//				if(vector.getVec() == 0){
//					playerattackflg[ii] = true;
//					enemy.get(ii).player_attack(player.get_p_at());
//					phase = 1;
//				}
//			}
//			if(map[player.get_p_x()-1][player.get_p_y()-1] == MONSTER[ii]){
//				if(vector.getVec() == 3){
//					playerattackflg[ii] = true;
//					enemy.get(ii).player_attack(player.get_p_at());
//					phase = 1;
//				}
//			}
//			if(map[player.get_p_x()+1][player.get_p_y()-1] == MONSTER[ii]){
//				if(vector.getVec() == 5){
//					playerattackflg[ii] = true;
//					enemy.get(ii).player_attack(player.get_p_at());
//					phase = 1;
//				}
//			}
//			if(map[player.get_p_x()-1][player.get_p_y()+1] == MONSTER[ii]){
//				if(vector.getVec() == 1){
//					playerattackflg[ii] = true;
//					enemy.get(ii).player_attack(player.get_p_at());
//					phase = 1;
//				}
//			}
//			if(map[player.get_p_x()+1][player.get_p_y()+1] == MONSTER[ii]){
//				if(vector.getVec() == 7){
//					playerattackflg[ii] = true;
//					enemy.get(ii).player_attack(player.get_p_at());
//					phase = 1;
//				}
//			}
//		}
//	}
//	public void chase(int num){
//		//プレイヤーが同じ部屋にいない場合に敵をランダムに動かすための乱数
//		int ran = (int)(Math.random()*8);
//		//敵の攻撃
//		if(map[enemy.get(num).get_x()-1][enemy.get(num).get_y()] == 9 && phase==1){
//			enemy.get(num).offmoveFlg();
//			player.p_damage(enemy.get(num).get_at());
//			enemy.get(num).attackFlg=true;
//			phase=2;
//			Log.d("","おいおいおいおいおいおいおいおいおいい１");
//		}
//		if(map[enemy.get(num).get_x()+1][enemy.get(num).get_y()] == 9&& phase==1){
//			player.p_damage(enemy.get(num).get_at());
//			enemy.get(num).attackFlg=true;
//			phase=2;
//			Log.d("","おいおいおいおいおいおいおいおいおいい１");
//		}
//		if(map[enemy.get(num).get_x()][enemy.get(num).get_y()-1] == 9&& phase==1){
//			enemy.get(num).offmoveFlg();
//			player.p_damage(enemy.get(num).get_at());
//			enemy.get(num).attackFlg=true;
//			phase=2;
//			Log.d("","おいおいおいおいおいおいおいおいおいい１");
//		}
//		if(map[enemy.get(num).get_x()][enemy.get(num).get_y()+1] == 9&& phase==1){
//			enemy.get(num).offmoveFlg();
//			player.p_damage(enemy.get(num).get_at());
//			enemy.get(num).attackFlg=true;
//			phase=2;
//			Log.d("","おいおいおいおいおいおいおいおいおいい１");
//		}
//		if(map[enemy.get(num).get_x()-1][enemy.get(num).get_y()-1] == 9&& phase==1){
//			enemy.get(num).offmoveFlg();
//			player.p_damage(enemy.get(num).get_at());
//			enemy.get(num).attackFlg=true;
//			phase=2;
//			Log.d("","おいおいおいおいおいおいおいおいおいい１");
//		}
//		if(map[enemy.get(num).get_x()+1][enemy.get(num).get_y()-1] == 9&& phase==1){
//			player.p_damage(enemy.get(num).get_at());
//			enemy.get(num).attackFlg=true;
//			phase=2;
//			Log.d("","おいおいおいおいおいおいおいおいおいい１");
//		}
//		if(map[enemy.get(num).get_x()-1][enemy.get(num).get_y()+1] == 9&& phase==1){
//			player.p_damage(enemy.get(num).get_at());
//			enemy.get(num).attackFlg=true;
//			phase=2;
//			Log.d("","おいおいおいおいおいおいおいおいおいい１");
//		}
//		if(map[enemy.get(num).get_x()+1][enemy.get(num).get_y()+1] == 9&& phase==1){
//			player.p_damage(enemy.get(num).get_at());
//			enemy.get(num).attackFlg=true;
//			phase=2;
//			Log.d("","おいおいおいおいおいおいおいおいおいい１");
//		}
//		/*
//		 * 敵の移動
//		 */
//
//		//敵がまだ移動していなかった場合
//		if(enemy.get(num).get_moveFlg() == false){
//			//プレイヤーと部屋が違う場合
//			//Playerと隣り合ったとき-
//			//if(enemy.get(num).get_sameFlg() == false){
//			//もし乱数が０且つ移動先が床だった場合（左）
//			if(map[enemy.get(num).get_x()-1][enemy.get(num).get_y()] == 1){
//				Log.d("","7777777777777777");
//				if(ran==0){
//					//現在いる敵のマップ情報を「床」に変更
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					//移動先のマップ情報を「敵」に変更
//					map[enemy.get(num).get_x()-1][enemy.get(num).get_y()] = MONSTER[num];
//					//敵の座標を移動先に確定
//					enemy.get(num).set_pos(enemy.get(num).get_x()-1, enemy.get(num).get_y());
//					//敵は移動後には攻撃させない
//					enemy.get(num).attackFlg=true;
//					//フェーズを2にする
//					phase = 2;
//					Log.d("","7777777777777777");
//				}
//				else{
//					ran=1;
//				}
//			}
//			//もし乱数が１且つ移動先が床だった場合（右）
//			else if(map[enemy.get(num).get_x()+1][enemy.get(num).get_y()] == 1){
//				if(ran==1){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()+1][enemy.get(num).get_y()] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()+1, enemy.get(num).get_y());
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//					Log.d("","7777777777777777");
//				}
//				else{
//					ran=2;
//				}
//			}
//			//もし乱数が２且つ移動先が床だった場合（上）
//			else if(map[enemy.get(num).get_x()][enemy.get(num).get_y()-1] == 1){//敵の上
//				if(ran==2){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()-1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x(), enemy.get(num).get_y()-1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//					Log.d("","7777777777777777");
//				}else{
//					ran=3;
//				}
//			}
//			//もし乱数が３且つ移動先が床だった場合（下）
//			else if(map[enemy.get(num).get_x()][enemy.get(num).get_y()+1] == 1){//敵の下
//				if(ran==3){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()+1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x(), enemy.get(num).get_y()+1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//					Log.d("","7777777777777777");
//				}else{
//					ran=4;
//				}
//			}
//			//もし乱数が４且つ移動先が床だった場合（左上）
//			else if(map[enemy.get(num).get_x()-1][enemy.get(num).get_y()-1] == 1){//敵の左上
//				if(ran==4){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()-1][enemy.get(num).get_y()-1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()-1, enemy.get(num).get_y()-1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//					Log.d("","7777777777777777");
//				}else{
//					ran=5;
//				}
//			}
//			//もし乱数が５且つ移動先が床だった場合（右上）
//			else if(map[enemy.get(num).get_x()+1][enemy.get(num).get_y()-1] == 1){//敵の右上
//				if(ran==5){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()+1][enemy.get(num).get_y()-1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()+1, enemy.get(num).get_y()-1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//					Log.d("","7777777777777777");
//				}else{
//					ran=6;
//				}
//			}
//			//もし乱数が６且つ移動先が床だった場合（左下）
//			else if(map[enemy.get(num).get_x()-1][enemy.get(num).get_y()+1] == 1){//敵の左下
//				if(ran==6){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()-1][enemy.get(num).get_y()+1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()-1, enemy.get(num).get_y()+1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//					Log.d("","7777777777777777");
//				}else{
//					ran=7;
//				}
//			}
//			//もし乱数が７且つ移動先が床だった場合（右下）
//			else if(map[enemy.get(num).get_x()+1][enemy.get(num).get_y()+1] == 1){//敵の右下
//				if(ran==7){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()+1][enemy.get(num).get_y()+1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()+1, enemy.get(num).get_y()+1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//					Log.d("","7777777777777777");
//				}else{
//					ran=0;
//				}
//			}
//		}
//		else{
//			if((enemy.get(num).get_x() < player.get_p_x()) && (enemy.get(num).get_y() < player.get_p_y())){
//				if(map[enemy.get(num).get_x()+1][enemy.get(num).get_y()+1] == 1){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()+1][enemy.get(num).get_y()+1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()+1, enemy.get(num).get_y()+1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//				}
//			}
//			else if((enemy.get(num).get_x() > player.get_p_x()) && (enemy.get(num).get_y() < player.get_p_y())){
//				if(map[enemy.get(num).get_x()-1][enemy.get(num).get_y()+1] == 1){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()-1][enemy.get(num).get_y()+1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()-1, enemy.get(num).get_y()+1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//				}
//			}
//			else if((enemy.get(num).get_x() < player.get_p_x()) && (enemy.get(num).get_y() > player.get_p_y())){
//				if(map[enemy.get(num).get_x()+1][enemy.get(num).get_y()-1] == 1){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()+1][enemy.get(num).get_y()-1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()+1, enemy.get(num).get_y()-1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//				}
//			}
//			else if((enemy.get(num).get_x() > player.get_p_x()) && (enemy.get(num).get_y() > player.get_p_y())){
//				if(map[enemy.get(num).get_x()-1][enemy.get(num).get_y()-1] == 1){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()-1][enemy.get(num).get_y()-1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()-1, enemy.get(num).get_y()-1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//				}
//			}
//			else if(enemy.get(num).get_x() < player.get_p_x()){//敵のx座標がPのx座標より左
//				if(map[enemy.get(num).get_x()+1][enemy.get(num).get_y()] == 1){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()+1][enemy.get(num).get_y()] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()+1, enemy.get(num).get_y());
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//				}
//			}
//			else if(enemy.get(num).get_x() > player.get_p_x()){//敵のx座標がPのx座標より右
//				if(map[enemy.get(num).get_x()-1][enemy.get(num).get_y()] == 1){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()-1][enemy.get(num).get_y()] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x()-1, enemy.get(num).get_y());
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//				}
//			}
//			else if(enemy.get(num).get_y() < player.get_p_y()){//敵のy座標がPのy座標より上
//				if(map[enemy.get(num).get_x()][enemy.get(num).get_y()+1] == 1){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()+1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x(), enemy.get(num).get_y()+1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//				}
//			}
//			else if(enemy.get(num).get_y() > player.get_p_y()){//敵のy座標がPのy座標より下
//				if(map[enemy.get(num).get_x()][enemy.get(num).get_y()-1] == 1){
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()] = 1;
//					map[enemy.get(num).get_x()][enemy.get(num).get_y()-1] = MONSTER[num];
//					enemy.get(num).set_pos(enemy.get(num).get_x(), enemy.get(num).get_y()-1);
//					enemy.get(num).attackFlg=true;
//					phase = 2;
//				}
//			}
//		}
//	}
//
//	public void set_room(int roomNumber){
//		ROOM = roomNumber;
//	}
//	public int get_room(){
//		return ROOM;
//	}
//	public int[][] get_map(){
//		return map;
//	}
//	public int get_mapsize(){
//		return MAPSIZE;
//	}
//
//	public static boolean get_clearflg(){
//		return clearflg;
//	}
//	public static boolean get_player_dead(){
//		return player_deadFlg;
//	}
//
//	public void set_phase(){
//		phase = 1;
//	}
//
//	public void player_dead(){
//		player_deadFlg = true;
//	}
//
//	public Bitmap mapScale(Bitmap bitmap){
//		bitmap=Bitmap.createScaledBitmap(bitmap,GameActivity.height/9,GameActivity.height/9,false);
//		return bitmap;
//	}
//}