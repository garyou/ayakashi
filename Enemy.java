package com.ayakashi;

import android.graphics.Canvas;

public class Enemy {
	private int x,y;//座標管理変数
	private int hp,at;//体力,攻撃力管理変数
	private int vec;//敵の方向
	private boolean deadflg;//死亡状況確認変数―true:死亡―false:活動中
	private boolean moveflg;//移動状況確認変数―true:移動中―false:停止中
	private boolean sameflg;
	private boolean damageflg;

	private int[][] map;
	Player player;

	public Enemy(Player player){//変数の初期化コンストラクタ
		this.player=player;
		x = y = hp = at = vec =0;
		deadflg = false;//生きている状態から始まる
		moveflg = true;//移動している状態から始まる
		sameflg=false;
		damageflg=false;
	//	map = Tutorial2.get_map();
	}
	//------------------------------------------------------------------------
	public void set_param(int at, int hp){
		this.at = at;
		this.hp = hp;
	}
	public void set_pos(int x, int y){//座標のセット関数
		this.x = x;
		this.y = y;
	}
	//------------------------------------------------------------------------
	public int get_hp(){//HPのゲット関数
		return hp;
	}
	public int get_at(){//ATのゲット関数
		return at;
	}
	public int get_x(){//X座標のゲット関数
		return x;
	}
	public int get_y(){//Y座標のゲット関数
		return y;
	}
	public void onmoveflg(){
		moveflg = true;
	}
	public void offmoveflg(){
		moveflg = false;
	}
	public void onsameflg(){
		sameflg = true;
	}
	public void offsameflg(){
		sameflg = false;
	}
	public boolean get_moveflg(){
		return moveflg;
	}
	public boolean get_sameflg(){
		return sameflg;
	}

	public void ondeadflg(){
		deadflg = true;
	}
	public boolean get_deadflg(){
		return deadflg;
	}
	public void onmoveFlg(){
		moveflg = true;
	}
	public void offmoveFlg(){
		moveflg = false;
	}
	public boolean get_moveFlg(){
		return moveflg;
	}
	public void onsameFlg(){
		sameflg = true;
	}
	public void offsameFlg(){
		sameflg = false;
	}
	public boolean get_sameFlg(){
		return sameflg;
	}
	
	public void ondamageFlg(){
		damageflg = true;
	}
	public void offdamageFlg(){
		damageflg = false;
	}
	public boolean get_damageFlg(){
		return damageflg;
	}
	public void ondeadFlg(){
		deadflg = true;
	}
	public void offdeadFlg(){
		deadflg = false;
	}
	public void set_vec(int vec){
		this.vec=vec;
	}
	//------------------------------------------------------------------------
	public void move(int xhoukou, int yhoukou, int syurui){//移動関数(-1～1, -1～1, 敵の種類番号)
		//第一引数：-1のときX座標は左に1マス,1のときX座標は右に1マス,0は真ん中
		//第二引数：-1のときY座標は上に1マス,1のときY座標は下に1マス,0は真ん中
		if(map[x+xhoukou][y+yhoukou] == 0){//周囲1マスが床のとき(移動可のとき)
			map[x+xhoukou][y+yhoukou] = syurui;//引数に応じた場所に移動する
			map[x][y] = 0;//元居た場所を床に変える
		}
	}
	public void chase_move(){
		if(x < player.get_x() && y < player.get_y()){//Pが右下にいる
			move(1,1,2);
		}
		else if(x > player.get_x() && y < player.get_y()){//Pが左下にいる
			move(-1,1,2);
		}
		else if(x < player.get_x() && y > player.get_y()){//Pが右上にいる
			move(1,-1,2);
		}
		else if(x > player.get_x() && y > player.get_y()){//Pが左上にいる
			move(-1,-1,2);
		}
		else if(x < player.get_x()){//Pが右にいる
			move(1,0,2);
		}
		else if(x > player.get_x()){//Pが左にいる
			move(-1,0,2);
		}
		else if(y < player.get_y()){//Pが下にいる
			move(0,1,2);
		}
		else if(y > player.get_y()){//Pが上にいる
			move(0,-1,2);
		}
	}
	public void rundom_move(int xhoukou, int yhoukou, int num, int syurui){
		int ran = (int)Math.random()*8;
		if(map[x+xhoukou][y+yhoukou] == 1 && ran == num){
			map[x][y] = 1;
			map[x+xhoukou][y+yhoukou] = syurui;
			x = x + xhoukou;
			y = y + yhoukou;
		}
	
	}
	public void onDraw(Canvas canvas){
			
	}
}
