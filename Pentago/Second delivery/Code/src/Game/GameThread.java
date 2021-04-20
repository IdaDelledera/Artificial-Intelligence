package Game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Interface.GameFrame;

public class GameThread extends Thread {
	private volatile boolean isFinished = false;
	private GameType type;
	private GameFrame frame;
	private GameBoard board;
	private GameBot bot;

	public enum GameType {
		humanHuman, humanBot, botBot;
	}

	public GameThread(GameBoard board, int depth) {
		super();
		this.board = board;
		this.setGameType();
		
		if (board.isBlackBot() || board.isWhiteBot())
			this.bot = new GameBot(board, depth);
		else
			this.bot = null;
	}
	
	public GameFrame getFrame() {
		return frame;
	}
	
	public GameBot getBot() {
		return bot;
	}

	public void setGameType() {
		if (!board.isBlackBot() && !board.isWhiteBot())
			this.type = GameType.humanHuman;
		else if (board.isBlackBot() && board.isWhiteBot())
			this.type = GameType.botBot;
		else
			this.type = GameType.humanBot;
		
	}

	public void setFrame(GameFrame frame) {
		this.frame = frame;
	}

	public GameBoard getBoard() {
		return board;
	}

	public void run() {
		if (type == GameType.humanHuman)
			this.typeHumanHuman();
		else if (type == GameType.humanBot)
			this.typeHumanBot();
		else
			this.typeBotBot();
	}

	public void typeHumanHuman() {
		while(!isFinished) {
			if (board.isGameOver() != -1) {
				frame.setMouseLock(true);
				this.endGame(board.isGameOver());
			}
		}
	}
	
	public void typeHumanBot() {
		while(!isFinished) {
			if (board.isGameOver() != -1) {
				frame.setMouseLock(true);
				this.endGame(board.isGameOver());
			} else if (board.isActualPlayerBot()) {
				frame.setMouseLock(true);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int[] move = bot.makeTheMove();
				
				board.makeMove(move[1], move[2], move[3], move[4], board.getActualPlayer());
				
				board.changePlayers();
				frame.repaint();
				
				frame.setMouseLock(false);
			}
		} 
	}
	
	public void typeBotBot() {
		while(!isFinished) {
			if (board.isGameOver() != -1) {
				frame.setMouseLock(true);
				this.endGame(board.isGameOver());
			}
		}
	}
	
	public void endGame(int gameOver) {
		String message;
		
		if (gameOver == 1)
			message = "Con cinque biglie di fila, il BLACK PLAYER vince!";
		else if (gameOver == 2)
			message = "Con cinque biglie in fila, il WHITE PLAYER vince!";
		else
			message = "La partita finisce con un pareggio!";

		JOptionPane.showMessageDialog(new JFrame(), message, "Hai perso",
				JOptionPane.INFORMATION_MESSAGE);

		isFinished = true;
	}
}