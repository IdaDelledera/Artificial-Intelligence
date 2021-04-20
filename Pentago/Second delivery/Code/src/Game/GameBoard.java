package Game;

import java.util.Arrays;

public class GameBoard {
	public int[][] board;
	private int actualPlayer;
	private boolean blackBot, whiteBot;

	public GameBoard(boolean blackBot, boolean whiteBot) {
		board = new int[6][6];
		this.blackBot = blackBot;
		this.whiteBot = whiteBot;
		this.actualPlayer = 1;
	}

	public GameBoard(int[][] board) {
		this.board = board;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public int getActualPlayer() {
		return actualPlayer;
	}

	public void changePlayers() {
		if (actualPlayer == 1)
			actualPlayer = 2;
		else
			actualPlayer = 1;
	}

	public boolean isBlackBot() {
		return blackBot;
	}

	public boolean isWhiteBot() {
		return whiteBot;
	}

	public boolean isActualPlayerBot() {
		if (actualPlayer == 1 && blackBot)
			return true;
		else if (actualPlayer == 2 && whiteBot)
			return true;
		else
			return false;
	}

	public int[][] returnSmallBoard(int number) {
		int[][] result = new int[3][3];
		if (number == 1) {
			for(int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					result[i][j] = board[i][j];
				}
		} else if (number == 2) {
			for (int i = 0; i < 3; i++)
				for (int j = 3; j < 6; j++) {
					result[i][j - 3] = board[i][j];
				}
		} else if (number == 3) {
			for (int i = 3; i < 6; i++)
				for (int j = 0; j < 3; j++) {
					result[i - 3][j] = board[i][j];
				}
		} else if (number == 4) {
			for (int i = 3; i < 6; i++)
				for (int j = 3; j < 6; j++) {
					result[i - 3][j - 3] = board[i][j];
				}
		}

		return result;
	}

	private void updateSmallBoard(int[][] oldBoard, int number) {
		if (number == 1) {
			for(int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					board[i][j] = oldBoard[i][j];
				}
		} else if (number == 2) {
			for (int i = 0; i < 3; i++)
				for (int j = 3; j < 6; j++) {
					board[i][j] = oldBoard[i][j - 3];
				}
		} else if (number == 3) {
			for (int i = 3; i < 6; i++)
				for (int j = 0; j < 3; j++) {
					board[i][j] = oldBoard[i - 3][j];
				}
		} else if (number == 4) {
			for (int i = 3; i < 6; i++)
				for (int j = 3; j < 6; j++) {
					board[i][j] = oldBoard[i - 3][j - 3];
				}
		}
	}

	public void rotateBoard(int numberOfBoard, String direction) {

		int [][] smallBoard = this.returnSmallBoard(numberOfBoard);


		if (direction == "CW")
			smallBoard = GameUtilities.rotateMatrixClockwise(smallBoard);
		else if (direction == "CCW")
			smallBoard = GameUtilities.rotateMatrixCounterclockwise(smallBoard);


		this.updateSmallBoard(smallBoard, numberOfBoard);
	}

	public void placePiece(int x, int y, int piece) {
		board[x][y] = piece;
	}

	public int[][] makePlay(int x, int y, int tab, int dir, int piece) {
		board[x][y] = piece;

		if (dir == 1)
			this.rotateBoard(tab, "CW");
		else if (dir == 2)
			this.rotateBoard(tab, "CCW");

		return Arrays.copyOf(board, board.length);
	}
	
	public void makeMove(int x, int y, int tab, int dir, int piece) {
		placePiece(x, y, piece);
		
		if (dir == 1)
			this.rotateBoard(tab, "CW");
		else if (dir == 2)
			this.rotateBoard(tab, "CCW");
	}

	public void unmakePlay(int x, int y, int tab, int dir, int piece) {
		if (dir == 1)
			rotateBoard(tab, "CCW");
		else if (dir == 2)
			rotateBoard(tab, "CW");

		placePiece(x, y, 0);
	}

	public void unmakeMove(int x, int y, int tab, int dir) {
		if (dir == 1)
			rotateBoard(tab, "CCW");
		else if (dir == 2)
			rotateBoard(tab, "CW");
		
		placePiece(x, y, 0);
	}
	
	public void rotate(String type) {
		if (type.equals("0"))
			rotateBoard(1, "CW");
		else if (type.equals("1"))
			rotateBoard(1, "CCW");
		else if (type.equals("2"))
			rotateBoard(2, "CCW");
		else if (type.equals("3"))
			rotateBoard(2, "CW");
		else if (type.equals("4"))
			rotateBoard(3, "CCW");
		else if (type.equals("5"))
			rotateBoard(3, "CW");
		else if (type.equals("6"))
			rotateBoard(4, "CW");
		else if (type.equals("7"))
			rotateBoard(4, "CCW");
	}

	public int getPiece(int x, int y) {
		return board[x][y];
	}

	public int isGameOver() {
		int cb = 0, cw = 0;


		// horizontal
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (board[i][j] == 1) {
					cb++;
					cw = 0;
				}
				else if (board[i][j] == 2) {
					cw++;
					cb = 0;
				}
				else {
					cb = 0;
					cw = 0;
				}

				if (cb == 5)
					return 1;
				else if (cw == 5)
					return 2;
			}

			cb = 0;
			cw = 0;
		}

		// vertical
		int[][] tempMatrix = GameUtilities.rotateMatrixClockwise(board);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (tempMatrix[i][j] == 1) {
					cb++;
					cw = 0;
				}
				else if (tempMatrix[i][j] == 2) {
					cw++;
					cb = 0;
				}
				else {
					cb = 0;
					cw = 0;
				}

				if (cb == 5)
					return 1;
				else if (cw == 5)
					return 2;
			}

			cb = 0;
			cw = 0;
		}

		// diagonal
		for (int i = 0; i < 6; i++) {
			if (board[i][i] == 1) {
				cb++;
				cw = 0;
			} else if (board[i][i] == 2) {
				cw++;
				cb = 0;
			} else {
				cb = 0;
				cw = 0;
			}

			if (cb == 5)
				return 1;
			else if (cw == 5)
				return 2;
		}

		cb = 0;
		cw = 0;

		for (int i = 0; i < 6; i++) {	
			if (board[i][5 - i] == 1) {
				cb++;
				cw = 0;
			} else if (board[i][5 - i] == 2) {
				cw++;
				cb = 0;
			} else {
				cb = 0;
				cw = 0;
			}

			if (cb == 5)
				return 1;
			else if (cw == 5)
				return 2;
		}

		if (board[0][1] == 1 && board[1][2] == 1 && board[2][3] == 1 && board[3][4] == 1 && 
				board[4][5] == 1)
			return 1;
		else if (board[0][1] == 2 && board[1][2] == 2 && board[2][3] == 2 && board[3][4] == 2 && 
				board[4][5] == 2)
			return 2;
		else if (board[1][0] == 1 && board[2][1] == 1 && board[3][2] == 1 && board[4][3] == 1 && 
				board[5][4] == 1)
			return 1;
		else if (board[1][0] == 2 && board[2][1] == 2 && board[3][2] == 2 && board[4][3] == 2 && 
				board[5][4] == 2)
			return 2;
		else if (board[0][4] == 1 && board[1][3] == 1 && board[2][2] == 1 && board[3][1] == 1 && 
				board[4][0] == 1)
			return 1;
		else if (board[0][4] == 2 && board[1][3] == 2 && board[2][2] == 2 && board[3][1] == 2 && 
				board[4][0] == 2)
			return 2;
		else if (board[1][5] == 1 && board[2][4] == 1 && board[3][3] == 1 && board[4][2] == 1 && 
				board[5][1] == 1)
			return 1;
		else if (board[1][5] == 2 && board[2][4] == 2 && board[3][3] == 2 && board[4][2] == 2 && 
				board[5][1] == 2)
			return 2;

		if (GameUtilities.isFull(board))
			return 0;

		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameBoard other = (GameBoard) obj;
		
		if (!GameUtilities.areEquals(board, other.board)) {	
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + actualPlayer;
		result = prime * result + (blackBot ? 1231 : 1237);
		result = prime * result + Arrays.deepHashCode(board);
		result = prime * result + (whiteBot ? 1231 : 1237);
		return result;
	}
}