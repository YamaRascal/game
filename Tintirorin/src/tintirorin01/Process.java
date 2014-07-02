//プレイヤークラスを作ってデータをまとめる

package tintirorin01;

import java.util.Scanner;
public class Process {
	Scanner scan = new Scanner(System.in);
	int num =3;					//サイコロの数
	int menberNum;				//参加人数
	int []dice = new int[3];	//プレイヤーのサイコロの出目

	int cpuPrice=1000;	//cpuの持ち点

	Process(int menber,boolean cpu){	//コンストラクタで参加人数の代入

			menberNum = menber;
	}


	public void playerScan(String[][] playerName,boolean cpu) {
		// TODO Auto-generated method stub
		//////////////////////////////////////////////////////////////////////プレイヤー情報の入力//

		for(int i=0;i < menberNum;i++){		//プレイヤーの名前を入力するfor文
			if(i==0){
				if(cpu==true){
					System.out.println("コンピュータとの対決を行います");
					playerName[i][0] = "親のcpu";
				}else{
					System.out.print("親になる人の名前を入力してください>");	////////////////配列０番が親
					playerName[i][0] = "親の"+scan.next();}
			}else{
				if(cpu==true){
					System.out.print("あなたの名前を入力してください>");
					playerName[i][0] = scan.next();
				}else{
					System.out.print("子の名前を入力してください>");		////////////////子の名前の入力
					playerName[i][0] = scan.next();}}
		}
	}
	public void priceScan(String[][] playerName,int[][] price,boolean cpu){	/////////////////////////////所持金の入力
		for(int i=0;i < menberNum;i++){
			if(i==0){
				if(cpu==true){
					System.out.println(playerName[0][0]+"所持金は"+cpuPrice+"です");
					price[i][0] = cpuPrice;
				}else{
					System.out.print(playerName[0][0]+"の所持金を入力してください>");	////////////////配列０番が親
					price[i][0] = scan.nextInt();
				}}else{
					System.out.print(playerName[i][0]+"の所持金を入力してください>");	////////////////子供
					price[i][0] = scan.nextInt();
				}
		}
	}
	public void bet (String[][] playerName,int[][] price,boolean cpu){
		for(int i=0;i < menberNum;i++){
			if(i==0){
				if(cpu==true){	/////cpuの賭け金の設定
					if(price[0][0]>cpuPrice*0.4){		//４割を切るまでは
						
						price[0][1] =(int)(price[0][0]*0.2);//１０分の１を賭ける
					}else{
						price[0][1] =price[0][0];			//４割を切ると全賭け
					}
					System.out.println(playerName[0][0]+"の賭け金は"+price[0][1]+"です");	////////////////配列０番が親
				}else{
					System.out.print(playerName[0][0]+"の賭け金を入力してください>");	////////////////配列０番が親
					price[0][1] = scan.nextInt();
					if(price[0][1]>price[0][0]){
						System.out.print(playerName[0][0]+"の所持金が足りません");
						i--;
						continue;
					}}
			}else{
				System.out.print(playerName[i][0]+"の賭け金を入力してください>");	////////////////子供
				price[i][1] = scan.nextInt();
				if(price[i][1]>price[i][0]){
					System.out.print(playerName[i][0]+"の所持金が足りません");
					i--;
					continue;
				}
			}
		}
	}
	public  void dicedata(int[][] mydice,int i){	//////////////////////サイコロをふる処理（ふった値をメインに返す）
		for(int j=0;j<3;j++){
			mydice[i][j] = (int)(Math.random()*6)+1;	//サイコロで１〜６の目を出す
		}
		//showDate();					//代入終了後にデータの表示、メソッドの呼び出し
	}

	public void showDate(int[][]mydice,int i){				//サイコロの値の表示
		for(int j=0;j<num;j++){
			System.out.print(mydice[i][j]+(j!=num-1?"　・　":""));
		}
		System.out.println();
		//System.out.print("よって");
	}
	public void masterOutcome(String[][] playerName,int[][]mydice,boolean[][] res,int i){		//親の
		res[0][0]=false;
		res[0][1]=false;
		role_deme(playerName,mydice,i,res);
		//////////////////////////////////////////////////////////////親の役の処理
		if(mydice[0][0]==4||mydice[0][1]==4||mydice[0][2]==4){
			if(mydice[0][0]==5||mydice[0][1]==5||mydice[0][2]==5){
				if(mydice[0][0]==6||mydice[0][1]==6||mydice[0][2]==6){
					//親が４５６だから即刻、親の勝ち[親が勝ちの処理を入力]
					res[0][0]=true;
					playerName[0][1]="役４５６";
				}
			}
		}else
			if(mydice[0][0]==1||mydice[0][1]==1||mydice[0][2]==1){
				if(mydice[0][0]==2||mydice[0][1]==2||mydice[0][2]==2){
					if(mydice[0][0]==3||mydice[0][1]==3||mydice[0][2]==3){
						//親が１２３だから、親の即負け[親が負けの処理を入力]
						res[0][1]=true;
						playerName[0][1]="役１２３";
					}
				}
			}
		//////////////////////////////////////////////////////////////親の値の処理

	}

	public void playerDate(String[][] playerName,int[][]mydice,int i,boolean[][] res){

		//////////////////////////////////////////////////////////////子の値の処理
		role_deme(playerName,mydice,i,res);
	}
	public void standardDiceJudge(int[][]mydice,int i,boolean[][] res){				//サイコロの値の入れ替え（０番をチンチロリンの値に）
		int es;

		if(mydice[i][0]==mydice[i][1]){
			es=mydice[i][0];
			mydice[i][0]=mydice[i][2];
			mydice[i][2]=es;
			res[i][2]=true;
		}
		if(mydice[i][0]==mydice[i][2]){
			es=mydice[i][0];
			mydice[i][0]=mydice[i][1];
			mydice[i][1]=es;
			res[i][2]=true;
		}
		if(mydice[i][1]==mydice[i][2]){
			//00に00を入れる処理になるので書かなくていい
			res[i][2]=true;
		}
	}


	public void DiceJudgeSYONBEN(int[][]mydice,int i,boolean[][] res){			//ションベン
		//////////////////////////////////////////////////////////////////////目無しによる負け
		if(mydice[i][0]!=mydice[i][1] 
				&& mydice[i][0]!=mydice[i][2] 
						&& mydice[i][1]!=mydice[i][2]){	//全てのサイコロの目が違ったら目がないので負け
			res[i][2]=false;
		}
	}

	public void Arashi(int[][]mydice,int i,boolean[][] res){					//アラシ(子の時だけ)
		if(mydice[i][0]==mydice[i][1] 
				&& mydice[i][0]==mydice[i][2] 
						&& mydice[i][1]==mydice[i][2]){
			res[i][3]=true;
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////役の判定
	public void role_deme(String[][] playerName,int[][]mydice,int i,boolean[][] res){
		standardDiceJudge(mydice,i,res);
		DiceJudgeSYONBEN(mydice,i,res);
		Arashi(mydice,i,res);

		if(res[i][3]==true){
			playerName[i][1]="アラシ役";
		}
		if(res[i][2]==true){
			playerName[i][1]="数字役";
		}
		if(res[i][2]==false){
			playerName[i][1]="しょんべん役";
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////データの判定　勝敗
	public int victory_decision(String[][] playerName,int[][]mydice,int i,boolean[][] res){
		//////////////////////////////////////////////////////////////////////////////////////////ションベン判定
		if(res[i][2]==false){
			res[0][3]=true;
			res[i][3]=false;
			return 1;	//子がションベンのため親の勝ち
		}
		//////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////アラシ役の勝敗、判定
		//ここからres[i][3]を勝敗判定にするtrueなら勝利
		//////////////////////////////falseなら敗北
		//res[0][3]==true&&res[i][3]==trueなら引き分け
		if(playerName[0][1]=="アラシ役"){				/////親がアラシだった場合
			if(playerName[0][1]=="アラシ役"){	////子供もアラシなら
				if(mydice[0][0]>=2&&mydice[i][0]>=2){
					if(mydice[0][0]>mydice[i][0]){			////サイコロデータの[0]番はデータ処理のときに使えるデータに入れ替えられている
						res[0][3]=true;
						res[i][3]=false;
						return 1;	//アラシ役vsアラシ役のため、数値対決
					}else 
						if(mydice[0][0]==mydice[i][0]){
							res[0][3]=true;
							res[i][3]=true;
							return 3;	//数値一緒のため引き分け
						}else{
							res[0][3]=false;
							res[i][3]=true;
							return 2;	//子の勝ち
						}
				}else 
					if(mydice[0][0]==mydice[i][0]){
						res[0][3]=true;
						res[i][3]=true;
						return 3;	//数値一緒のため引き分け
					}else 
						if(mydice[0][0]==1){
							res[0][3]=true;
							res[i][3]=false;
							return 1;
						}else 
							if(mydice[i][0]==1){
								res[0][3]=false;
								res[i][3]=true;
								return 2;
							}
			}else{
				res[0][3]=true;
				res[i][3]=false;
				return 1;	//親がアラシ役で子がアラシじゃない、よって親の勝ち
			}
		}
		else if(playerName[0][1]=="アラシ役"){
			res[0][3]=false;
			res[i][3]=true;
			return 2;	//親がアラシ役じゃなくて、子だけがアラシ役、よって子の勝ち
		}
		////////////////////////////////////////////////////////////////////////////////////////////////ここまでアラシ役

		//////////////////////////////////////////////////////////////////////////////////////////数値対決
		if(mydice[0][0]>mydice[i][0]){
			res[0][3]=true;
			res[i][3]=false;
			return 1;
		}else 
			if(mydice[0][0]==mydice[i][0]){
				res[0][3]=true;
				res[i][3]=true;
				return 3;	//数値一緒のため引き分け
			}else{
				res[0][3]=false;
				res[i][3]=true;
				return 2;	//子の勝ち
			}
		//////////////////////////////////////////////////////////////////////////////////////////

	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////勝敗ー出力
	public void master_win(String[][] playerName,int[][]mydice,int i,boolean[][] res,int[][] price){
		System.out.println(playerName[0][0]+"の\n"
				+mydice[0][0]+"・"+mydice[0][1]+"・"+mydice[0][2]+"の"+playerName[0][1]+(res[0][2]==false?"":mydice[0][0])+"\n\n対\n\n"
				+playerName[i][0]+"の\n"+
				+mydice[i][0]+"・"+mydice[i][1]+"・"+mydice[i][2]+"の"+playerName[i][1]+(res[i][2]==false?"":mydice[i][0])+
				"よって"+playerName[0][0]+"の勝ち\n\n");
		betSettle(playerName, mydice, i, res,price);	////賭け金精算
	}

	public void player_win(String[][] playerName,int[][]mydice,int i,boolean[][] res,int[][] price){
		System.out.println(playerName[0][0]+"の\n"
				+mydice[0][0]+"・"+mydice[0][1]+"・"+mydice[0][2]+"の"+playerName[0][1]+(res[0][2]==false?"":mydice[0][0])+"\n\n対\n\n"
				+playerName[i][0]+"の\n"+
				+mydice[i][0]+"・"+mydice[i][1]+"・"+mydice[i][2]+"の"+playerName[i][1]+(res[i][2]==false?"":mydice[i][0])+
				"よって"+playerName[i][0]+"の勝ち\n\n");
		betSettle(playerName, mydice, i, res,price);	////賭け金精算
	}

	public void drow(String[][] playerName,int[][]mydice,int i,boolean[][] res,int[][] price){
		System.out.println(playerName[0][0]+"の\n"
				+mydice[0][0]+"・"+mydice[0][1]+"・"+mydice[0][2]+"の"+playerName[0][1]+(res[0][2]==false?"":mydice[0][0])+"\n\n対\n\n"
				+playerName[i][0]+"の\n"+
				+mydice[i][0]+"・"+mydice[i][1]+"・"+mydice[i][2]+"の"+playerName[i][1]+(res[i][2]==false?"":mydice[i][0])+
				"よって"+"引き分け\n\n");
		betSettle(playerName, mydice, i, res,price);	////賭け金精算
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void betSettle(String[][] playerName,int[][]mydice,int i,boolean[][] res,int[][] price){//////////通常の賭け金計算
		System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊"
				+ "\n                              賭け金精算                                  \n");
		if(res[0][3]==true&&res[i][3]==false){

			System.out.println("サイコロ"+mydice[0][0]+"の"+playerName[0][1]+"\nよって\n");
			if(playerName[0][1]=="アラシ役"){
				if(mydice[0][0]==1){
					mydice[0][0]=10;
				}
				System.out.println(playerName[0][0]+"所持金"+price[0][0]+"に\n"
						+"親の賭け金"+price[0][1]+"×"+mydice[0][0]+"倍が加えられ"
						+ "\n\n計"+(price[0][0]+(price[0][1]*mydice[0][0]))+"になる\n"
						+ "逆に"+playerName[i][0]+"の所持金は"
						+"親の賭け金"+price[0][1]+"×"+mydice[0][0]+"倍が引かれ"
						+"\n\n計"+(price[i][0]-(price[0][1]*mydice[0][0]))+"になる");
				price[0][0] +=price[0][1]*mydice[0][0];
				price[i][0] -=price[0][1]*mydice[0][0];

			}else{
				System.out.println(playerName[0][0]+"の所持金"+price[0][0]+"に\n"
						+playerName[i][0]+"の賭け金"+price[i][1]+"が加えられ"
						+ "\n\n計"+(price[0][0]+price[i][1])+"になる\n"
						+ "逆に"+playerName[i][0]+"の所持金は"
						+"自分の賭け金"+price[i][1]+"が引かれ"
						+"\n\n計"+(price[i][0]-price[i][1])+"になる");
				price[0][0] +=price[i][1];
				price[i][0] -=price[i][1];

			}
		}
		if(res[0][3]==false&&res[i][3]==true){

			System.out.println("サイコロ"+mydice[i][0]+"の"+playerName[i][1]+"\nよって\n");
			if(playerName[i][1]=="アラシ役"){
				if(mydice[i][0]==1){
					mydice[i][0]=10;
				}
				System.out.println(playerName[i][0]+"の所持金"+price[i][0]+"に\n"
						+"自分の賭け金"+price[i][1]+"×"+mydice[i][0]+"倍が加えられ"
						+ "\n\n計"+(price[i][0]+(price[i][1]*mydice[i][0]))+"になる\n"
						+ "逆に"+playerName[0][0]+"の所持金は\n"+playerName[i][0]
								+"の賭け金"+price[i][1]+"×"+mydice[i][0]+"倍が引かれ"
								+"\n\n計"+(price[0][0]-(price[i][1]*mydice[i][0]))+"になる");
				price[i][0] +=price[i][1]*mydice[i][0];
				price[0][0] -=price[i][1]*mydice[i][0];
			}else{
				System.out.println(playerName[i][0]+"の所持金"+price[i][0]+"に\n"
						+playerName[0][0]+"の賭け金"+price[0][1]+"が加えられ"
						+ "\n\n計"+(price[i][0]+price[0][1])+"になる\n"
						+ "逆に"+playerName[0][0]+"の所持金は"
						+"自分の賭け金"+price[0][1]+"が引かれ"
						+"\n\n計"+(price[0][0]-price[0][1])+"になる");
				price[i][0] +=price[0][1];
				price[0][0] -=price[0][1];
			}
		}
		if(res[0][3]==true&&res[i][3]==true){
			System.out.println("\n引き分けのため賭け金のやり取りは無し\n\n");
		}
		System.out.println("*********************************************************************************");
	}

	///////////////////////////////////////////////////////////////////////親の即勝ち、即負け用(よって全員徴収、全員払い)
	public void betSettle_now(String[][] playerName,int[][]mydice,int i,boolean[][] res,int[][] price){
		System.out.println("*********************************************************************************"
				+ "\n                              賭け金精算                                  \n");
		if(res[0][0]==true){				//////////判定結果による結果その１（４５６で即勝ち） 
			System.out.println("子供からの総取り");
			for(int cnt=1;cnt<menberNum;cnt++){
				System.out.println(playerName[0][0]+"の所持金"+price[0][0]+"に\n子の"+
						playerName[cnt][0]+"の賭け額"+price[cnt][1]+"を巻き上げた");
				price[0][0] +=price[cnt][1];
				price[cnt][0] -=price[cnt][1];
			}
		}else
			if(res[0][1]==true){			//////////判定結果による結果その２（１２３で即負け）
				System.out.println("子供への総払い");
				for(int cnt=1;cnt<menberNum;cnt++){
					System.out.println(playerName[cnt][0]+"の所持金"+price[cnt][0]+"に\n"+
										playerName[cnt][0]+"の賭け額"+price[cnt][1]+"を支払った");
					price[cnt][0] +=price[cnt][1];
					price[0][0] -=price[cnt][1];
				}
			}else
				if(res[0][2]==false){			/////////目無し
					System.out.println("子供への総払い");
					for(int cnt=1;cnt<menberNum;cnt++){
						System.out.println(playerName[cnt][0]+"の所持金"+price[cnt][0]+"に\n"+
											playerName[cnt][0]+"の賭け額"+price[cnt][1]+"を支払った");
						price[cnt][0] +=price[cnt][1];
						price[0][0] -=price[cnt][1];
					}
				}else
					if(mydice[0][0]==6){			//親の最高数値
						System.out.println("子供からの総取り");
						for(int cnt=1;cnt<menberNum;cnt++){
							System.out.println(playerName[0][0]+"の所持金"+price[0][0]+"に\n子の"+
									playerName[cnt][0]+"の賭け額"+price[cnt][1]+"を巻き上げた");
							price[0][0] +=price[cnt][1];
							price[cnt][0] -=price[cnt][1];
						}
					}else

						if(mydice[0][0]==1){			//////////親の最低値
							System.out.println("子供への総払い");
							for(int cnt=1;cnt<menberNum;cnt++){
								System.out.println(playerName[cnt][0]+"の所持金"+price[cnt][0]+"に\n"+
													playerName[cnt][0]+"の賭け額"+price[cnt][1]+"を支払った");
								price[cnt][0] +=price[cnt][1];
								price[0][0] -=price[cnt][1];
							}
						}
		System.out.println("*********************************************************************************");
	}
}