package tintirorin01;
import java.util.Scanner;
public class Tintiro_Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		for(;;){
			System.out.print("プレイヤー人数を入力してください>");
			int menberNum = scan.nextInt();
			boolean cpu = false;
			if(menberNum==1){
				cpu=true;
				menberNum +=1;
			}

			Process p = new Process(menberNum,cpu);

			String[][] playerName = new String[menberNum][2];		//プレイヤーの名前を収納するString型の配列
			int [][]price = new int[menberNum][2];					//プレイヤーの[0]==持ち金,と[1]==賭け金
			//mydice[]←プレイヤー番号、mydice[][]←サイコロの目


			p.playerScan(playerName,cpu);
			p.priceScan(playerName,price,cpu);

			for(;;){
				int [][]mydice = new int[menberNum][3];					//プレイヤーのサイコロの出目を収納するint型の配列
				boolean[][] res=new boolean[menberNum][4] ;	//親の場合は即勝ち(==[0][0]＝＝true)、
				//////////////////////////////////////////////即負け(==[0][1]==true)、
				//////////////////////////////////////////////データの使用可（==[i][2]==true）判定
				//////////////////////////////////////////////アラシ役の判定（==[i][3]==true)			判定
				p.bet(playerName, price,cpu);
				for(int i=0;i<menberNum;i++){/////////
					for(int j=4;j>0;j--){
						System.out.println(playerName[i][0]+"の\nサイコロの出目は");
						int x=0;
						p.dicedata(mydice,i);						//サイコロを３個ふり、出た目も表示させる
						p.showDate(mydice,i);

						//p.judge(mydice[i][3],menberNum);				//表示後、勝負時に使用するサイコロの目を判定し表示する
						//System.out.println("\n"+(mydice[i]==0?"『出目がありません』":"あなたの出目は"+mydice[i]+"です"));

						////////////////////////////////////////////////////////ふり直し

						if(j>1){
							System.out.print("今の値を捨てて、ふり直しますか？(あと"+(j-1)+"回まで)\nふり直すなら　１　を\n進むなら　２　を\n入力>");
							if(i==0&&cpu==true){	////////////////////////////////////////////cpuのふり直し判定
								p.masterOutcome(playerName,mydice,res,i);

								if(playerName[0][1]=="役４５６"||playerName[0][1]=="アラシ役"||playerName[0][1]=="数字役"&&mydice[0][0]>1){
									x=2;
								}else{x=1;
								}
								/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							}else{
								x =scan.nextInt();}
						}else{
							System.out.println("ふり直しの最大回数を過ぎました\n\n先に進みます\n\n");
						}

						if(x!=2&&j>1){
							System.out.println("ふり直します\n");
						}

						if(x==2){
							System.out.println("ふり直しませんでした.\n先に進みます\n\n");
							break;}

						////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					}

					if(i==0){////////////////////////////////////////////////////////親の即勝ち・即負けルール
						p.masterOutcome(playerName,mydice,res,i);//////////////////////////////////............................親の値の判定

						if(res[0][0]==true){				//////////判定結果による結果その１（４５６で即勝ち） 
							System.out.println("\n\n親が");
							p.showDate(mydice, i);
							System.out.println(playerName[0][0]+"は\n"
									+mydice[0][0]+"・"+mydice[0][1]+"・"+mydice[0][2]+"の\n"+playerName[0][1]+"を出したため親の即勝ちです");
							p.betSettle_now(playerName,mydice,i,res,price);
							break;
						}
						if(res[0][1]==true){			//////////判定結果による結果その２（１２３で即負け）
							System.out.println("\n\n親が");
							p.showDate(mydice, i);
							System.out.println(playerName[0][0]+"は\n"
									+mydice[0][0]+"・"+mydice[0][1]+"・"+mydice[0][2]+"の\n"+playerName[0][1]+"を出したため親の即負けです");
							p.betSettle_now(playerName,mydice,i,res,price);
							break;
						}
						if(res[0][2]==false){			/////////親の目無し
							System.out.println("\n\n親が");
							p.showDate(mydice, i);
							System.out.println(playerName[0][0]+"は\n"
									+mydice[0][0]+"・"+mydice[0][1]+"・"+mydice[0][2]+"の\n"+playerName[0][1]+"を出したため親の即負けです");
							p.betSettle_now(playerName,mydice,i,res,price);
							break;
						}

						if(mydice[0][0]==6){			//親の最高数値
							System.out.println("\n\n親が");
							p.showDate(mydice, i);
							System.out.println(playerName[0][0]+"は\n"
									+mydice[0][0]+"・"+mydice[0][1]+"・"+mydice[0][2]+"の\n最高数値役"+mydice[0][0]+"を出したため親の即勝ちです");
							p.betSettle_now(playerName,mydice,i,res,price);
							break;
						}

						if(mydice[0][0]==1){			//////////判定結果による結果その２（１２３、またはしょんべんで即負け）
							System.out.println("\n\n親が");
							p.showDate(mydice, i);
							System.out.println(playerName[0][0]+"は\n"
									+mydice[0][0]+"・"+mydice[0][1]+"・"+mydice[0][2]+"の\n最低数値役"+mydice[0][0]+"を出したため親の即負けです");
							p.betSettle_now(playerName,mydice,i,res,price);
							break;
						}
						continue;}///////////////////////////////////即勝ち、即負けが無かったので子の番へ

					p.playerDate(playerName,mydice,i,res);					///////////子供のデータまとめ

					//////////////////////////////////////////////////////////////////////////ここまでで親と子のデータのまとめが　終わる
					//////////////////////////////////////////ここから判定に入る

					int result = p.victory_decision(playerName,mydice,i,res);

					if(result==1){
						p.master_win(playerName, mydice, i, res,price);
					}
					if(result==2){
						p.player_win(playerName, mydice, i, res,price);
					}
					if(result==3){
						p.drow(playerName, mydice, i, res,price);
					}

					if(price[0][0]<=0||price[i][0]<=0)
					{
						if(price[0][0]<=0){
							System.out.print(playerName[0][0]+"の所持金が底をついたのでゲームを終了します");
							break;
						}else if(price[i][0]<=0){
							System.out.print(playerName[i][0]+"の所持金が底をついたのでゲームを終了します");
							break;}
					}else{
						System.out.print("プレイヤーと所持金をこのままで再度続けたい場合は　１　を\nゲームをやめたい場合は1以外の整数キーを入力してください＞");
						int bre = scan.nextInt();
						if(bre!=1){
							break;
						}
					}

				}
				System.out.println("本当にゲームをやめますか？\n続けるなら　１　を\nやめるなら1以外の整数キーを入力してください＞");
				int x =scan.nextInt();
				if(x!=1){
					break;
				}
			}
		}
	}
}