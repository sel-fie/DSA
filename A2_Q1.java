import java.util.ArrayList;

public class A2_Q1 {
	
	public static int[] game(String[][] board){
		int[] array = {0,0};
		int totalNum = 0;
		for (int i = 0 ; i < board.length ; i++){
			for (int j = 0 ; j < board[i].length ; j++){ // go through 2d array
				if (board[i][j].equals("o")) {
					totalNum ++;
				}
			}
		} // finds total number of balls in the starting map
		int balls = totalNum;
		int moves = 0; // starting values

		ArrayList<int []> answers = new ArrayList<>(); // for optimization purposes
		perform(board, answers , balls, moves);

		for (int i = 0; i < answers.size() ; i++){ // checks for minimum number of balls
			if (answers.get(i)[0] <= balls){
				balls = answers.get(i)[0];
				moves = answers.get(i)[1];
			}
		}
		for (int i = 0; i < answers.size() ; i++){ // checks for minimum number of moves
			if(answers.get(i)[0] == balls && answers.get(i)[1] <= moves){
				moves = answers.get(i)[1];
			}
		}
		array[0] = balls;
		array[1] = moves;

		return array;
	}

	public static void perform(String[][] board, ArrayList<int []> answers, int balls,  int moves){
		ArrayList<Integer> direction = new ArrayList<>();
		for (int i = 0 ; i < board.length ; i++){
			for (int j = 0 ; j < board[0].length ; j++){
				if (board[i][j].equals("o")) {
					is_move(board, i, j, direction);
					for (int k = 0; k < direction.size() ; k++){
						if (direction.get(k) != 0){
							doMove(board, i, j, direction.get(k));
							moves = moves + 1;
							balls = balls - 1;
							int[] array = {balls, moves};
							answers.add(array);
							perform(board, answers, balls, moves);

							moves = moves - 1;
							balls = balls + 1;
							undoMove(board, i, j, direction.get(k));
						}
					}
					direction.clear();
				}
			}
		}
	}

	public static void is_move(String[][] board, int i , int j, ArrayList<Integer> direction){ // helper function to see what moves exist
		// go through 2d array
		if (i + 2 < board.length && board[i + 1][j].equals("o") && board[i + 2][j].equals(".")) {
			direction.add(1);
		}
		if (i - 2 >= 0 && board[i - 1][j].equals("o") && board[i - 2][j].equals(".")) {
			direction.add(2);
		}
		if (j + 2 < board[i].length && board[i][j + 1].equals("o") && board[i][j + 2].equals(".")) {
			direction.add(3);
		}
		if (j - 2 >= 0 && board[i][j - 1].equals("o") && board[i][j - 2].equals(".")) {
			direction.add(4);
		}
    }

	public static void doMove(String[][] board, int i, int j, int direction){ //helper function to do the move
		if (direction == 1){
			board[i][j]= ".";
			board[i + 1][j] = ".";
			board[i + 2][j] = "o";
		}
		else if (direction == 2){
			board[i][j] =".";
			board[i-1][j]= ".";
			board[i - 2][j] = "o";
		}
		else if (direction == 3){
			board[i][j] = ".";
			board[i][j + 1]= ".";
			board[i][j + 2] = "o";
		}
		else if (direction == 4){
			board[i][j] = ".";
			board[i][j - 1]= ".";
			board[i][j - 2] = "o";

		}
	}
	public static void undoMove(String[][] board,int i, int j, int direction){ // helper function to undo the move
		if (direction == 1){
			board[i][j] = "o"; // reverse
			board[i+1][j] = "o";
			board[i + 2][j] = ".";
		}
		else if (direction == 2){
			board[i][j] = "o"; // reverse
			board[i-1][j] = "o";
			board[i - 2][j] = ".";
		}
		else if (direction == 3){
			board[i][j] = "o"; // reverse
			board[i][j + 1] = "o";
			board[i][j + 2] = ".";
		}
		else if (direction == 4){
			board[i][j] = "o"; // reverse
			board[i][j - 1] = "o";
			board[i][j - 2] = ".";
		}
	}
}