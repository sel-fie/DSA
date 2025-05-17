import java.util.*;

public class A3Q1 {
	// index 0 is the number of frogs necessary to eat all the food, zero if no food is reachable
	// index 1 is the number of food spots that could not be reached

	// . is food
	// (space) is absense of food
	// X represent a human barrier
	// A-W are entrances to the areas

	// i think BFS is the right choice for this question
	// each dot or space is a node that can be traversed
	// X or A-W cannot be traversed
	// any node that has the same i or j can access it, otherwise not
	// count the number of starts it takes to traverse every node, and if some nodes cannot be traversed, return the number
	public static int[] saving_frogs(String[][] board) {
		int[] ans = new int[2];
		int left = 0;
		int frogs = 0;
		int newtotal = 0;
		Integer[][] checked = new Integer[board.length][board[0].length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].equals(".")) {
					left += 1;
					checked[i][j] = 1;  // white
				}
				else if(board[i][j].equals(" ")) {
					checked[i][j] = 1; // white
				}
				else if(board[i][j].equals("X")) {
					checked[i][j] = 0; // black
				}
				else{
					checked[i][j] = 2; // entrance
				}
			}
		}
		// makes new array with white, grey and black values
		// plus counts the total number of food

		// top row
		for(int i = 0 ; i < board[0].length; i++) {
			if (i+ 1 < board[0].length && !board[0][i].equals("X") && !board[1][i].equals("X")) {
				//intiate BFS from i
				newtotal = BFS(board, 1, i, checked);
				if (newtotal > 0){
					left = left - newtotal;
					frogs += 1;
				}
			}
		}
		// bottom row
		for(int i = 0 ; i < board[0].length ; i++) {
			if (!board[board.length -1][i].equals("X") && !board[board.length-2][i].equals("X")) {
				//initiate BFS from i
				newtotal = BFS(board, board.length - 2, i, checked);
				if (newtotal > 0){
					left = left - newtotal;
					frogs += 1;
				}
			}
		}

		// left column
		for (int i = 0; i < board.length; i++) {
			if(!board[i][0].equals("X") && !board[i][1].equals("X")) {
				//initiate BFS from i
				newtotal= BFS(board, i, 1, checked);
				 if (newtotal > 0){
					 left = left - newtotal;
					 frogs += 1;
				 }
			}
		}

		//right column
		for(int i = 0 ; i < board.length ; i++) {
			if(!board[i][board[0].length-1].equals("X") && !board[i][board[0].length-2].equals("X")) {
				//initiate BFS from i
				newtotal= BFS(board, i, board[0].length-2, checked);
				if (newtotal > 0){
					left = left - newtotal;
					frogs += 1;
				}
			}
		}

		ans[0] = frogs;
		ans[1] = left;
		return ans;
	}

// row | column
	public static int BFS(String[][] board, int i, int j, Integer[][] checked) {
		Queue<int[]> queue = new LinkedList<int[]>();
		queue.add(new int[] {i, j});
		checked [i][j] = 3; // 3 is grey
		int newtotal = 0;
		while (!queue.isEmpty()) {
			int[] spot = queue.poll();
			if (board[spot[0]][spot[1]].equals(".")) {
				board[spot[0]][spot[1]] = " ";
				newtotal += 1;// erases the food
			}
			// different row, same column DOWN
			if (spot[0] + 1 < board.length && checked[spot[0] + 1][spot[1]] == 1) {
				checked[spot[0] + 1][spot[1]] = 3;
				queue.add(new int[] {spot[0] + 1, spot[1]});
			}
			// different row, same column UP
			if (spot[0] - 1 >= 0 && checked[spot[0] - 1][spot[1]] == 1) {
				checked[spot[0] - 1][spot[1]] = 3;
				queue.add(new int[] {spot[0] - 1, spot[1]});
			}
			// same row, different column RIGHT
			if (spot[1] + 1 < board[0].length && checked[spot[0]][spot[1] + 1] == 1) {
				checked[spot[0]][spot[1] + 1] = 3;
				queue.add(new int[] {spot[0] , spot[1] + 1});
			}
			// same row, different column LEFT
			if (spot[1] - 1 >= 0 && checked[spot[0]][spot[1] - 1] == 1) {
				checked[spot[0]][spot[1] - 1] = 3;
				queue.add(new int[] {spot[0], spot[1] - 1});
			}
			checked[spot[0]][spot[1]] = 0; // 0 is black
		}
		/*
		for (int k = 0 ; k < board.length ; k++) {
			for (int l = 0 ; l < board[k].length ; l++) {
				if (board[k][l].equals(".")) {
					newtotal += 1;
				}
			}
		} */
		return newtotal;

	}
	public static void main(String[] args) {
		String[][] board =
				{{"X", "X", "X"},
				{"X", ".", "X" },
				{"X", "X", "X"}};
		String[][] board2 =
				{
						{"X", "D", "X", "V", "X"},
						{"X", ".", "X", ".", "X"},
						{"X", "X", "X", "X", "X"}
				};
		String[][] board3 =
				{
						{"N", "A", "Q", "X", "X"},
						{"X", " ", "X", ".", "A"},
						{"X", "X", "X", "X", "X"}
				};
		int[] ans = saving_frogs(board);
		int[] ans2 = saving_frogs(board2);
		int[] ans3 = saving_frogs(board3);
		System.out.println(ans[0] + " " + ans[1]);
		System.out.println(ans2[0] + " " + ans2[1]);
		System.out.println(ans3[0] + " " + ans3[1]);
	}

}

