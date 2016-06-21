package oncai;

public class Rating {
	private static int r , c;

	public static int rating(int list, int depth){
		int counter = 0, material = rateMaterial();
		counter+=rateAttack();
		counter+=material;
		counter+=rateMoveability(list,depth,material);
		counter+=ratePositional();
		return -(counter+depth*50);
	}
	public static int rateAttack(){
		int counter = 0;
		for (int i = 0; i < 35; i++){
			switch (Oncai2.oncaiBoard[i/5][i%5]){
			case "d": counter+=100;
			break;
			case "j": counter+=900;
					  r = i/5;
					  c = i%5;
			break;
			}
		}
		return counter;
	}
	public static int rateMaterial(){
		int counter = 0;
		for (int i = 0; i < 35; i++){
			switch (Oncai2.oncaiBoard[i/5][i%5]){
			case "d": counter+=100;
			break;
			case "j": counter+=900;
			break;
			}
		}
		return counter;
	}
	public static int rateMoveability(int listLength, int depth, int material){
		int counter = 0;
		counter+=listLength;//7 pointer per valid move
		if (listLength==0){//current side is in checkmate or stalemate
			if (!Oncai2.jaguarSafe(r, c)){
				counter+=-200000*depth;
			}
		}
		return counter;
	}
	public static int ratePositional(){
		return 0;
	}
	
}
