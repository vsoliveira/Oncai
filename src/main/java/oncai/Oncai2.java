package oncai;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Oncai2 {
	static String oncaiBoard[][] = {
			/*0*//*1*//*2*//*3*//*4*/	
			/*0*/		{"j" ," " ," " ," " ," "},
			/*1*/		{" " ," " ," " ," " ," "},
			/*2*/		{" " ," " ," " ," " ," "},
			/*3*/		{" " ," " ," " ," " ," "},
			/*4*/		{" " ," " ," " ," " ," "},
			/*5*/		{"-" ," " ," " ," " ,"-"},
			/*6*/		{" " ,"-" ,"d" ,"-" ," "}
	};
	static int jaguarPosition;
	static int humanAsJaguar = -1; // 1 = human as Jaguar, 0 = computer as Jaguar
	static String  whosTime = "jaguar";
	static int globalDepth = 4;
	public static void main(String[] args) throws IOException, InterruptedException {
		while(!"j".equals(oncaiBoard[jaguarPosition/5][jaguarPosition%5])){jaguarPosition++;}

		JFrame f = new JFrame("Oncai - Jogo da On�a");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui = new UserInterface();
		f.add(ui);
		f.setSize(500, 500);
		f.setVisible(true);

		Object[] option = {"M�quina","Humano","Demonstra��o"};
		humanAsJaguar = JOptionPane.showOptionDialog(null,
				"Quem deve jogar com a on�a?",
				"Oncai - Op��es",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				option,
				option[2]);

		if (humanAsJaguar==0){		
			long startTime=System.currentTimeMillis();
			makeMove(alphaBeta(globalDepth, 1000000, -1000000, "", 0));
			long endTime=System.currentTimeMillis();
			System.out.println("Eu s� levei "+(endTime-startTime)+" milisegundos para pensar nisso! Voc� � muito lerdo...");
			flipPlayer();
			f.repaint();
		}
		if (humanAsJaguar==2){	
			do{
				makeMove(alphaBeta(globalDepth, 1000000, -1000000, "", 0));
				flipPlayer();
				f.repaint();
				Thread.sleep(1000);

				makeMove(alphaBeta(globalDepth, 1000000, -1000000, "", 0));
				flipPlayer();
				f.repaint();
				Thread.sleep(1000);

			} while (true);
		}
		System.out.println(posibleMoves());
	}

	//		System.out.println(alphaBeta(globalDepth, 1000000, -1000000, "", 0));

	//		for (int i = 0; i < 5; i++){
	//			System.out.println(Arrays.toString(oncaiBoard[i]));
	//		}



	public static String alphaBeta(int depth, int beta, int alpha, String move, int player) throws IOException{
		//x1, y1, x2, y2, capturePiece, scoreAlphaBeta e.g. 2232 3214214324
		String list = posibleMoves();	
		if (depth == 0 || list.length() == 0) {
			return move + (Rating.rating(list.length(), depth) * (player*2-1));
		}
		list=sortMoves(list);
		player  = 1-player;// 0 to dogs or 1 to jaguar
		for (int i = 0; i < list.length(); i+=7){
			makeMove(list.substring(i,i+7));
			flipPlayer();//think
			String returnString = alphaBeta(depth-1, beta, alpha, list.substring(i,i+7), player);
			int value = Integer.valueOf(returnString.substring(7));
			flipPlayer();//think
			undoMove(list.substring(i,i+7));
			if (player==0){
				if (value <= beta){ // check if is a good move
					beta = value;
					if (depth == globalDepth){ move = returnString.substring(0, 7);}
				}
			} else {
				if (value>alpha){ // pruning goes here
					alpha=value;
					if (depth == globalDepth){ move = returnString.substring(0, 7);}
				}
			}
			if (alpha>=beta) {
				if (player == 0){ return move+beta;} 
				else { return move+alpha;
				}
			}
		}
		if (player==0){	return move+beta;} 
		else{ return move+alpha;
		}
	}

	public static void flipPlayer(){
		if ("jaguar".equals(whosTime)){
			whosTime = "dog";
		} else {
			whosTime = "jaguar";
		}
	}

	public static void makeMove(String move){
		if (-1 == Character.getNumericValue(move.charAt(6))){
			oncaiBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = oncaiBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
			oncaiBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
		} else {
			oncaiBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = oncaiBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
			oncaiBoard[Character.getNumericValue(move.charAt(4))][Character.getNumericValue(move.charAt(5))] = " ";
			oncaiBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
		}
	}

	public static void undoMove(String move){
		if (-1 == Character.getNumericValue(move.charAt(6))){
			oncaiBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = oncaiBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
			oncaiBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = " ";
		}
		else {
			oncaiBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = oncaiBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
			oncaiBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = " ";
			oncaiBoard[Character.getNumericValue(move.charAt(4))][Character.getNumericValue(move.charAt(5))] = String.valueOf(move.charAt(6));
		}

	}

	public static String posibleMoves(){
		String listDog = "";
		String listJaguar = "";
		for (int i = 0; i < 35; i++){
			switch (oncaiBoard [i/5][i%5]) {
			case "j": listJaguar +=posibleJ(i);
			break;
			case "d": listDog +=posibleD(i);
			break;
			}
		}
		if ("jaguar".equals(whosTime)){
			return listJaguar;
		} else {
			return listDog;
		}
	}

	public static String posibleD(int i){
		String list = "", oldPiece;
		int r = i/5, c = i%5;
		int rc = r+c;
		if (r == 5 && c == 1){
			List<Integer> position = new ArrayList<Integer>();
			position.add(2);
			position.add(5);
			position.add(6);
			for (int l : position){
				if (l!=4) {
					try{
						if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
							//pega a pe�a que ser� "comida"
							oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
							//deixa livre a posi��o que est� a pe�a que ser� movida
							oncaiBoard[r][c] = " ";
							//coloca a pe�a movida onde a outra foi "comida"
							oncaiBoard[r-1+l/3][c-1+l%3] = "d";

							int jaguarTemp = jaguarPosition;
							jaguarPosition = i+(l/3) * 8 + l%3-9;
							if (dogSafe(r-1+l/3,c-1+l%3)){
								list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
							}
							oncaiBoard[r][c] = "d";
							oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
							jaguarPosition = jaguarTemp;
						}
					}
					catch(Exception e){
					}
				}
			}
		}else {
			if (r == 5 && c == 3){
				List<Integer> position = new ArrayList<Integer>();
				position.add(0);
				position.add(3);
				position.add(8);
				for (int l : position){
					if (l!=4) {
						try{
							if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
								//pega a pe�a que ser� "comida"
								oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
								//deixa livre a posi��o que est� a pe�a que ser� movida
								oncaiBoard[r][c] = " ";
								//coloca a pe�a movida onde a outra foi "comida"
								oncaiBoard[r-1+l/3][c-1+l%3] = "d";

								int jaguarTemp = jaguarPosition;
								jaguarPosition = i+(l/3) * 8 + l%3-9;
								if (dogSafe(r-1+l/3,c-1+l%3)){
									list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
								}
								oncaiBoard[r][c] = "d";
								oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
								jaguarPosition = jaguarTemp;
							}
						}
						catch(Exception e){
						}
					}
				}
			} else {
				if (r == 6 && c == 0){
					List<Integer> position = new ArrayList<Integer>();
					position.add(2);
					position.add(5);
					for (int l : position){
						if (l == 2){
							try{
								if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
									//pega a pe�a que ser� "comida"
									oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
									//deixa livre a posi��o que est� a pe�a que ser� movida
									oncaiBoard[r][c] = " ";
									//coloca a pe�a movida onde a outra foi "comida"
									oncaiBoard[r-1+l/3][c-1+l%3] = "d";

									int jaguarTemp = jaguarPosition;
									jaguarPosition = i+(l/3) * 8 + l%3-9;
									if (dogSafe(r-1+l/3,c-1+l%3)){
										list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
									}
									oncaiBoard[r][c] = "d";
									oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
									jaguarPosition = jaguarTemp;
								}

							}

							catch(Exception e){
							}
						} else {
							try{
								if ( oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals(" ")){
									//pega a pe�a que ser� "comida"
									oldPiece = oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)];
									//deixa livre a posi��o que est� a pe�a que ser� movida
									oncaiBoard[r][c] = " ";
									//coloca a pe�a movida onde a outra foi "comida"
									oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = "d";

									int jaguarTemp = jaguarPosition;
									jaguarPosition = i+(l/3) * 8 + l%3-9;
									if (dogSafe((r-2+(l/3)*2),(c-2+(l%3)*2))){
										list = list+r+c+((r-2+(l/3)*2))+((c-2+(l%3)*2))+"-"+"-"+oldPiece;
									}
									oncaiBoard[r][c] = "d";
									oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = oldPiece;
									jaguarPosition = jaguarTemp;
								}
							}
							catch(Exception e){
							}
						}

					}
				} else {
					if (r == 6 && c == 2){
						List<Integer> position = new ArrayList<Integer>();
						position.add(1);
						position.add(3);
						position.add(5);
						for (int l : position){
							if (l == 1){
								try{
									if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
										//pega a pe�a que ser� "comida"
										oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
										//deixa livre a posi��o que est� a pe�a que ser� movida
										oncaiBoard[r][c] = " ";
										//coloca a pe�a movida onde a outra foi "comida"
										oncaiBoard[r-1+l/3][c-1+l%3] = "d";

										int jaguarTemp = jaguarPosition;
										jaguarPosition = i+(l/3) * 8 + l%3-9;
										if (dogSafe(r-1+l/3,c-1+l%3)){
											list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
										}
										oncaiBoard[r][c] = "d";
										oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
										jaguarPosition = jaguarTemp;
									}
								}
								catch(Exception e){
								}
							} else {
								try{
									if ( oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals(" ")){
										//pega a pe�a que ser� "comida"
										oldPiece = oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)];
										//deixa livre a posi��o que est� a pe�a que ser� movida
										oncaiBoard[r][c] = " ";
										//coloca a pe�a movida onde a outra foi "comida"
										oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = "d";

										int jaguarTemp = jaguarPosition;
										jaguarPosition = i+(l/3) * 8 + l%3-9;
										if (dogSafe((r-2+(l/3)*2),(c-2+(l%3)*2))){
											list = list+r+c+((r-2+(l/3)*2))+((c-2+(l%3)*2))+"-"+"-"+oldPiece;
										}
										oncaiBoard[r][c] = "d";
										oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = oldPiece;
										jaguarPosition = jaguarTemp;
									}
								}
								catch(Exception e){
								}
							}
						}
					} else {
						if (r == 6 && c == 4){
							List<Integer> position = new ArrayList<Integer>();
							position.add(0);
							position.add(3);
							for (int l : position){
								if (l == 0){
									try{
										if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
											//pega a pe�a que ser� "comida"
											oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
											//deixa livre a posi��o que est� a pe�a que ser� movida
											oncaiBoard[r][c] = " ";
											//coloca a pe�a movida onde a outra foi "comida"
											oncaiBoard[r-1+l/3][c-1+l%3] = "d";

											int jaguarTemp = jaguarPosition;
											jaguarPosition = i+(l/3) * 8 + l%3-9;
											if (dogSafe(r-1+l/3,c-1+l%3)){
												list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
											}
											oncaiBoard[r][c] = "d";
											oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
											jaguarPosition = jaguarTemp;
										}
									}
									catch(Exception e){
									}
								} else {
									try{
										if ( oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals(" ")){
											//pega a pe�a que ser� "comida"
											oldPiece = oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)];
											//deixa livre a posi��o que est� a pe�a que ser� movida
											oncaiBoard[r][c] = " ";
											//coloca a pe�a movida onde a outra foi "comida"
											oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = "d";

											int jaguarTemp = jaguarPosition;
											jaguarPosition = i+(l/3) * 8 + l%3-9;
											if (dogSafe((r-2+(l/3)*2),(c-2+(l%3)*2))){
												list = list+r+c+((r-2+(l/3)*2))+((c-2+(l%3)*2))+"-"+"-"+oldPiece;
											}
											oncaiBoard[r][c] = "d";
											oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = oldPiece;
											jaguarPosition = jaguarTemp;
										}
									}
									catch(Exception e){
									}
								}
							}
						} else {

							if (rc % 2 != 0){
								for (int l = 0; l < 9; l++){
									if (l % 2 != 0) {
										try{
											if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
												//pega a pe�a que ser� "comida"
												oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
												//deixa livre a posi��o que est� a pe�a que ser� movida
												oncaiBoard[r][c] = " ";
												//coloca a pe�a movida onde a outra foi "comida"
												oncaiBoard[r-1+l/3][c-1+l%3] = "d";

												int jaguarTemp = jaguarPosition;
												jaguarPosition = i+(l/3) * 8 + l%3-9;
												if (dogSafe(r-1+l/3,c-1+l%3)){
													list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
												}
												oncaiBoard[r][c] = "d";
												oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
												jaguarPosition = jaguarTemp;
											}
										}
										catch(Exception e){
										}
									}
								}
								return list;
							} else {
								for (int l = 0; l < 9; l++){
									if (l!=4) {
										try{
											if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
												//pega a pe�a que ser� "comida"
												oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
												//deixa livre a posi��o que est� a pe�a que ser� movida
												oncaiBoard[r][c] = " ";
												//coloca a pe�a movida onde a outra foi "comida"
												oncaiBoard[r-1+l/3][c-1+l%3] = "d";

												int jaguarTemp = jaguarPosition;
												jaguarPosition = i+(l/3) * 8 + l%3-9;
												if (dogSafe(r-1+l/3,c-1+l%3)){
													list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
												}
												oncaiBoard[r][c] = "d";
												oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
												jaguarPosition = jaguarTemp;
											}
										}
										catch(Exception e){
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return list;
	}



	public static String posibleJ(int i){
		String list = "", oldPiece;
		int r = i/5, c = i%5;
		int rc = r+c;
		if (r == 5 && c == 1){
			List<Integer> position = new ArrayList<Integer>();
			position.add(2);
			position.add(5);
			position.add(6);
			for (int l : position){
				if (l!=4) {
					try{
						if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
							//pega a pe�a que ser� "comida"
							oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
							//deixa livre a posi��o que est� a pe�a que ser� movida
							oncaiBoard[r][c] = " ";
							//coloca a pe�a movida onde a outra foi "comida"
							oncaiBoard[r-1+l/3][c-1+l%3] = "j";

							int jaguarTemp = jaguarPosition;
							jaguarPosition = i+(l/3) * 8 + l%3-9;
							if (dogSafe(r-1+l/3,c-1+l%3)){
								list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
							}
							oncaiBoard[r][c] = "j";
							oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
							jaguarPosition = jaguarTemp;
						}
					}
					catch(Exception e){
					}
				}
			}
		}else {
			if (r == 5 && c == 3){
				List<Integer> position = new ArrayList<Integer>();
				position.add(0);
				position.add(3);
				position.add(8);
				for (int l : position){
					if (l!=4) {
						try{
							if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
								//pega a pe�a que ser� "comida"
								oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
								//deixa livre a posi��o que est� a pe�a que ser� movida
								oncaiBoard[r][c] = " ";
								//coloca a pe�a movida onde a outra foi "comida"
								oncaiBoard[r-1+l/3][c-1+l%3] = "j";

								int jaguarTemp = jaguarPosition;
								jaguarPosition = i+(l/3) * 8 + l%3-9;
								if (dogSafe(r-1+l/3,c-1+l%3)){
									list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
								}
								oncaiBoard[r][c] = "j";
								oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
								jaguarPosition = jaguarTemp;
							}
						}
						catch(Exception e){
						}
					}
				}
			} else {
				if (r == 6 && c == 0){
					List<Integer> position = new ArrayList<Integer>();
					position.add(2);
					position.add(5);
					for (int l : position){
						if (l == 2){
							try{
								if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
									//pega a pe�a que ser� "comida"
									oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
									//deixa livre a posi��o que est� a pe�a que ser� movida
									oncaiBoard[r][c] = " ";
									//coloca a pe�a movida onde a outra foi "comida"
									oncaiBoard[r-1+l/3][c-1+l%3] = "j";

									int jaguarTemp = jaguarPosition;
									jaguarPosition = i+(l/3) * 8 + l%3-9;
									if (dogSafe(r-1+l/3,c-1+l%3)){
										list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
									}
									oncaiBoard[r][c] = "j";
									oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
									jaguarPosition = jaguarTemp;
								}

							}

							catch(Exception e){
							}
						} else {
							try{
								if ( oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals(" ")){
									//pega a pe�a que ser� "comida"
									oldPiece = oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)];
									//deixa livre a posi��o que est� a pe�a que ser� movida
									oncaiBoard[r][c] = " ";
									//coloca a pe�a movida onde a outra foi "comida"
									oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = "j";

									int jaguarTemp = jaguarPosition;
									jaguarPosition = i+(l/3) * 8 + l%3-9;
									if (dogSafe((r-2+(l/3)*2),(c-2+(l%3)*2))){
										list = list+r+c+((r-2+(l/3)*2))+((c-2+(l%3)*2))+"-"+"-"+oldPiece;
									}
									oncaiBoard[r][c] = "j";
									oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = oldPiece;
									jaguarPosition = jaguarTemp;
								}
							}
							catch(Exception e){
							}
						}

					}
				} else {
					if (r == 6 && c == 2){
						List<Integer> position = new ArrayList<Integer>();
						position.add(1);
						position.add(3);
						position.add(5);
						for (int l : position){
							if (l == 1){
								try{
									if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
										//pega a pe�a que ser� "comida"
										oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
										//deixa livre a posi��o que est� a pe�a que ser� movida
										oncaiBoard[r][c] = " ";
										//coloca a pe�a movida onde a outra foi "comida"
										oncaiBoard[r-1+l/3][c-1+l%3] = "j";

										int jaguarTemp = jaguarPosition;
										jaguarPosition = i+(l/3) * 8 + l%3-9;
										if (dogSafe(r-1+l/3,c-1+l%3)){
											list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
										}
										oncaiBoard[r][c] = "j";
										oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
										jaguarPosition = jaguarTemp;
									}
								}
								catch(Exception e){
								}
							} else {
								try{
									if ( oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals(" ")){
										//pega a pe�a que ser� "comida"
										oldPiece = oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)];
										//deixa livre a posi��o que est� a pe�a que ser� movida
										oncaiBoard[r][c] = " ";
										//coloca a pe�a movida onde a outra foi "comida"
										oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = "j";

										int jaguarTemp = jaguarPosition;
										jaguarPosition = i+(l/3) * 8 + l%3-9;
										if (dogSafe((r-2+(l/3)*2),(c-2+(l%3)*2))){
											list = list+r+c+((r-2+(l/3)*2))+((c-2+(l%3)*2))+"-"+"-"+oldPiece;
										}
										oncaiBoard[r][c] = "j";
										oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = oldPiece;
										jaguarPosition = jaguarTemp;
									}
								}
								catch(Exception e){
								}
							}
						}
					} else {
						if (r == 6 && c == 4){
							List<Integer> position = new ArrayList<Integer>();
							position.add(0);
							position.add(3);
							for (int l : position){
								if (l == 0){
									try{
										if ( oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
											//pega a pe�a que ser� "comida"
											oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
											//deixa livre a posi��o que est� a pe�a que ser� movida
											oncaiBoard[r][c] = " ";
											//coloca a pe�a movida onde a outra foi "comida"
											oncaiBoard[r-1+l/3][c-1+l%3] = "j";

											int jaguarTemp = jaguarPosition;
											jaguarPosition = i+(l/3) * 8 + l%3-9;
											if (dogSafe(r-1+l/3,c-1+l%3)){
												list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
											}
											oncaiBoard[r][c] = "j";
											oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
											jaguarPosition = jaguarTemp;
										}
									}
									catch(Exception e){
									}
								} else {
									try{
										if ( oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals(" ")){
											//pega a pe�a que ser� "comida"
											oldPiece = oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)];
											//deixa livre a posi��o que est� a pe�a que ser� movida
											oncaiBoard[r][c] = " ";
											//coloca a pe�a movida onde a outra foi "comida"
											oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = "j";

											int jaguarTemp = jaguarPosition;
											jaguarPosition = i+(l/3) * 8 + l%3-9;
											if (dogSafe((r-2+(l/3)*2),(c-2+(l%3)*2))){
												list = list+r+c+((r-2+(l/3)*2))+((c-2+(l%3)*2))+"-"+"-"+oldPiece;
											}
											oncaiBoard[r][c] = "j";
											oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = oldPiece;
											jaguarPosition = jaguarTemp;
										}
									}
									catch(Exception e){
									}
								}
							}
						} else {
							if (rc % 2 != 0){
								for (int l = 0; l < 9; l++){
									if (l % 2 != 0) {
										if (l!=4) {
											try{
												if (oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
													//pega a posi��o a pe�a ser� movida
													oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
													//deixa livre a posi��o que est� a pe�a que ser� movida
													oncaiBoard[r][c] = " ";
													//coloca a pe�a na possivel desejada
													oncaiBoard[r-1+l/3][c-1+l%3] = "j";

													int jaguarTemp = jaguarPosition;
													jaguarPosition = i+(l/3) * 8 + l%3 - 9;
													if (jaguarSafe(r-1+l/3,c-1+l%3)){
														list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
													}
													oncaiBoard[r][c] = "j";
													oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
													jaguarPosition = jaguarTemp;
												}
												if (oncaiBoard[r-1+l/3][c-1+l%3].equals("d") && oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals(" ")){
													//pega a pe�a que ser� "comida"
													oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
													//deixa livre a posi��o que est� a pe�a que ser� movida
													oncaiBoard[r][c] = " ";
													//coloca a pe�a movida onde a outra foi "comida"
													oncaiBoard[r-1+l/3][c-1+l%3] = " ";
													oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = "j";

													int jaguarTemp = jaguarPosition;
													jaguarPosition = i+(l/3) * 8 + l%3-9;
													if (jaguarSafe((r-2+(l/3)*2),(c-2+(l%3)*2))){
														list = list+r+c+((r-2+(l/3)*2))+(c-2+(l%3)*2)+(r-1+l/3)+(c-1+l%3)+oldPiece;
													}
													oncaiBoard[r][c] = "j";
													oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
													oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = " ";
													jaguarPosition = jaguarTemp;
												}
											}
											catch(Exception e){
											}
										}
									}
								}
								return list;
							} else {
								for (int l = 0; l < 9; l++){
									if (l!=4) {
										try{
											if (oncaiBoard[r-1+l/3][c-1+l%3].equals(" ")){
												//pega a posi��o a pe�a ser� movida
												oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
												//deixa livre a posi��o que est� a pe�a que ser� movida
												oncaiBoard[r][c] = " ";
												//coloca a pe�a na possivel desejada
												oncaiBoard[r-1+l/3][c-1+l%3] = "j";

												int jaguarTemp = jaguarPosition;
												jaguarPosition = i+(l/3) * 8 + l%3 - 9;
												if (jaguarSafe(r-1+l/3,c-1+l%3)){
													list = list+r+c+(r-1+l/3)+(c-1+l%3)+"-"+"-"+oldPiece;
												}
												oncaiBoard[r][c] = "j";
												oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
												jaguarPosition = jaguarTemp;
											}
											if (oncaiBoard[r-1+l/3][c-1+l%3].equals("d") && oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals(" ")){
												//pega a pe�a que ser� "comida"
												oldPiece = oncaiBoard[r-1+l/3][c-1+l%3];
												//deixa livre a posi��o que est� a pe�a que ser� movida
												oncaiBoard[r][c] = " ";
												//coloca a pe�a movida onde a outra foi "comida"
												oncaiBoard[r-1+l/3][c-1+l%3] = " ";
												oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = "j";

												int jaguarTemp = jaguarPosition;
												jaguarPosition = i+(l/3) * 8 + l%3-9;
												if (jaguarSafe((r-2+(l/3)*2),(c-2+(l%3)*2))){
													list = list+r+c+((r-2+(l/3)*2))+(c-2+(l%3)*2)+(r-1+l/3)+(c-1+l%3)+oldPiece;
												}
												oncaiBoard[r][c] = "j";
												oncaiBoard[r-1+l/3][c-1+l%3] = oldPiece;
												oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)] = " ";
												jaguarPosition = jaguarTemp;
											}
										}
										catch(Exception e){
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return list;

	}


	public static boolean dogSafe(int r, int c){
		for (int l = 0, m = 8; l < 9 && m > 0; l++, m--){
			if (l!=4) {
				try{
					if (oncaiBoard[r-1+l/3][c-1+l%3].equals("j")){
						if (oncaiBoard[r-1+m/3][c-1+m%3].equals(" ")){
							return false;
						}
					}
				}
				catch (Exception e) {
				}
			}
		}
		return true;
	}



	public static boolean jaguarSafe(int r, int c){

		for (int l = 0; l < 9; l++){
			if (l!=4) {
				try{
					if ((oncaiBoard[r-1+l/3][c-1+l%3].equals("d") && oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals(" "))){
						if (oncaiBoard[(r-2+(l/3)*2)][(c-2+(l%3)*2)].equals("d")){
							return false;
						}
					}
				}
				catch (Exception e){
				}
			}
		}

		return true;
	}
	public static String sortMoves(String list) {
		int[] score=new int [list.length()/7];
		for (int i=0;i<list.length();i+=7) {
			makeMove(list.substring(i, i+7));
			score[i/7]=-Rating.rating(-1, 0);
			undoMove(list.substring(i, i+7));
		}
		String newListA="", newListB=list;
		for (int i=0;i<Math.min(6, list.length()/7);i++) {//first few moves only
			int max=-1000000, maxLocation=0;
			for (int j=0;j<list.length()/7;j++) {
				if (score[j]>max) {max=score[j]; maxLocation=j;}
			}
			score[maxLocation]=-1000000;
			newListA+=list.substring(maxLocation*7,maxLocation*7+7);
			newListB=newListB.replace(list.substring(maxLocation*7,maxLocation*7+7), "");
		}
		return newListA+newListB;
	}

}
