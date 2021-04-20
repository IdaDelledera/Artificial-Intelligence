package Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameBot {
	private GameBoard board;
	public int myColor, oppColor;
	private int depth;

	private class Move {
		public int x, y, tab, rot, player;

		public Move(int x, int y, int tab, int rot, int player) {
			this.x = x;
			this.y = y;
			this.tab = tab;
			this.rot = rot;
			this.player = player;
		}

		public String toString() {
			String tabs, rots;

			if (tab == 1)
				tabs = "tab1";
			else if (tab == 2)
				tabs = "tab2";
			else if (tab == 2)
				tabs = "tab3";
			else
				tabs = "tab4";

			if (rot == 1)
				rots = "CW";
			else
				rots = "CCW";

			return x + " " + y + " " + tabs + " " + rots + " " + player + " ";
		}
	}

	public GameBot(GameBoard board, int depth) {
		this.board = board;

		if (board.isBlackBot()) {
			myColor = 1;
			oppColor = 2;
		} else {
			myColor = 2;
			oppColor = 1;
		}
		
		this.setDepth(depth);
	}

	public int[][] copyMatrix(int[][] matrix) {
		int[][] result = new int[6][6];

		for (int i = 0; i < matrix.length; i++)
			result[i] = Arrays.copyOf(matrix[i], matrix[i].length);

		return result;
	}

	public List<Move> nextPossibleMoves(int player) {
		ArrayList<Move> result = new ArrayList<Move>();

		if (board.isGameOver() == -1) {

			// first adds every possible move
			for (int i = 0; i < 6; i++)
				for (int j = 0; j < 6; j++)
					if (board.getBoard()[i][j] == 0)
						for (int k = 1; k < 5; k++)
							for (int l = 1; l < 3; l++)
								result.add(new Move(i, j, k, l, player));

			Set<GameBoard> states = new HashSet<GameBoard>();

			for (int i = 0; i < result.size(); i++) {
				int[][] newElement = copyMatrix(board.makePlay(result.get(i).x, result.get(i).y, result.get(i).tab,
						result.get(i).rot, result.get(i).player));

				board.unmakePlay(result.get(i).x, result.get(i).y, result.get(i).tab, result.get(i).rot,
						result.get(i).player);

				int beforeSize = states.size();

				states.add(new GameBoard(Arrays.copyOf(newElement, newElement.length)));

				if (beforeSize == states.size()) {
					result.remove(i);
					i--;
				}
			}

			Collections.shuffle(result);

			return result;
		} else
			return result;
	}
	public int[] makeTheMove() {
		if (depth == 0)
			return randomMove();
		else
			return minimaxAB(depth, myColor, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	public int[] randomMove() {
		List<Move> results = nextPossibleMoves(myColor);
		Move result = results.get(0);
		
		return new int[] {-1, result.x, result.y, result.tab, result.rot};
	}


	public int[] minimaxAB(int depth, int player, int alpha, int beta) {

		List<Move> possibleMoves = nextPossibleMoves(player);

		int p1;

		if (player == 1)
			p1 = 2;
		else
			p1 = 1;
		int score;
		int cx = -1, cy = -1, ct = -1, cd = -1;
		if (possibleMoves.isEmpty() || depth == 0)
			return new int[] { evaluate(p1), cx, cy, ct, cd };
		else

			for (Move move : possibleMoves) {
				board.makeMove(move.x, move.y, move.tab, move.rot, move.player);
				if (player == myColor) {
					score = minimaxAB(depth - 1, oppColor, alpha, beta)[0];
					if (score > alpha) {
						alpha = score;
						cx = move.x;
						cy = move.y;
						ct = move.tab;
						cd = move.rot;
					}
				} else {
					score = minimaxAB(depth - 1, myColor, alpha, beta)[0];
					if (score < beta) {
						beta = score;
						cx = move.x;
						cy = move.y;
						ct = move.tab;
						cd = move.rot;
					}
				}
				board.unmakeMove(move.x, move.y, move.tab, move.rot);
				if (alpha >= beta)
					break;
			}
		return new int[] { (player == myColor) ? alpha : beta, cx, cy, ct, cd };
	}

	public int evaluate(int player) {
		int value = 0;

		for (int i = 0; i < board.getBoard().length; i++)
			if (player == 2)
				value += evaluate(board.getBoard()[i], player);
			else {
				value -= evaluate(board.getBoard()[i], player);
			}


		return value;
	}

	public int evaluate(int[] a, int w) {
		int s = 3;


		int[] p51 = { w, w, w, w, w, s };
		int[] p52 = { s, w, w, w, w, w };


		int[] p41 = { w, w, w, w, 0, s };
		int[] p42 = { w, w, w, 0, w, s };	
		int[] p43 = { w, w, 0, w, w, s };
		int[] p44 = { w, 0, w, w, w, s };	
		int[] p45 = { 0, w, w, w, w, s };
		int[] p46 = { s, w, w, w, w, 0 };
		int[] p47 = { s, w, w, w, 0, w };
		int[] p48 = { s, w, w, 0, w, w };
		int[] p49 = { s, w, 0, w, w, w };
		int[] p410 = { s, 0, w, w, w, w };


		int[] p31 = { w, w, w, 0, 0, s };
		int[] p32 = { w, w, 0, w, 0, s };
		int[] p33 = { w, w, 0, 0, w, s };
		int[] p34 = { w, 0, w, 0, w, s };
		int[] p35 = { w, 0, 0, w, w, s };
		int[] p36 = { 0, w, 0, w, w, s };
		int[] p37 = { 0, 0, w, w, w, s };
		int[] p38 = { s, w, w, w, 0, 0 };
		int[] p39 = { s, w, w, 0, w, 0 };
		int[] p310 = { s, w, w, 0, 0, w };
		int[] p311 = { s, w, 0, w, 0, w };
		int[] p312 = { s, w, 0, 0, w, w };
		int[] p313 = { s, 0, w, 0, w, w };
		int[] p314 = { s, 0, 0, w, w, w };
		int[] p315 = { s, 0, w, w, 0, w };
		int[] p316 = { 0, w, w, 0, w, s };
		int[] p317 = { s, w, 0, w, w, 0 };
		int[] p318 = { w, 0, w, w, 0, s };
		int[] p319 = { s, 0, w, w, w, 0 };
		int[] p320 = { 0, w, w, w, 0, s };
		

		int[] p21 = { w, w, 0, 0, 0, s };
		int[] p22 = { w, 0, w, 0, 0, s };
		int[] p23 = { w, 0, 0, w, 0, s };
		int[] p24 = { w, 0, 0, 0, w, s };
		int[] p25 = { 0, w, 0, 0, w, s };
		int[] p26 = { 0, 0, w, 0, w, s };
		int[] p27 = { 0, 0, 0, w, w, s };
		int[] p28 = { 0, 0, w, w, 0, s };
		int[] p29 = { 0, w, w, 0, 0, s };
		int[] p210 = { w, w, 0, 0, 0, s };


		if (specialEquals(a, p51) || specialEquals(a, p52))
			return 500000;
		else if (specialEquals(a, p41) || specialEquals(a, p42) || specialEquals(a, p43) || specialEquals(a, p44)
				|| specialEquals(a, p45) || specialEquals(a, p46) || specialEquals(a, p47) || specialEquals(a, p48)
				|| specialEquals(a, p49) || specialEquals(a, p410))
			return 40000;
		else if (specialEquals(a, p31) || specialEquals(a, p32) || specialEquals(a, p33) || specialEquals(a, p34)
				|| specialEquals(a, p35) || specialEquals(a, p36) || 
				specialEquals(a, p37) || specialEquals(a, p38) ||  specialEquals(a, p39) || 
				specialEquals(a, p310) ||  specialEquals(a, p311) || specialEquals(a, p312) ||
				 specialEquals(a, p313) || specialEquals(a, p314) || specialEquals(a, p315) ||
				 specialEquals(a, p316) || specialEquals(a, p317) ||  specialEquals(a, p318) 
				 || specialEquals(a, p319) || specialEquals(a, p320))
			return 3000;
		else if (specialEquals(a, p21) || specialEquals(a, p22) || specialEquals(a, p23) || specialEquals(a, p24)
				|| specialEquals(a, p25) || specialEquals(a, p26) || specialEquals(a, p27) || specialEquals(a, p28)
				|| specialEquals(a, p29) || specialEquals(a, p210))
			return 200;
		else
			return 0;
	}

	public boolean specialEquals(int[] a, int[] b) {
		for (int i = 0; i < a.length; i++) {
			if (b[i] == 3)
				continue;
			if (a[i] != b[i])
				return false;
		}
		return true;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
}
