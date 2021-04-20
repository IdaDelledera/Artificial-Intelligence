package Game;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

public class GamePiece extends Ellipse2D.Double {
	private static final long serialVersionUID = -3508996992748851141L;
	private Color color;
	private int posX, posY;

	public GamePiece(int x, int y, int arcx, int arcy, Color color) {
		super(x, y, arcx, arcy);
		this.color = color;
	}

	public void setPositions(int posY, int posX) {
		this.posX = posX;
		this.posY = posY;
	}

	public Color getColor() {
		return color;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void changeColor(Color color) {
		this.color = color;
	}
	
	public static Vector<GamePiece> createPieces(Vector<GamePiece> pieces) {
		pieces.get(0).setPositions(0, 0);
		pieces.get(1).setPositions(0, 1);
		pieces.get(2).setPositions(0, 2);
		pieces.get(3).setPositions(1, 0);
		pieces.get(4).setPositions(1, 1);
		pieces.get(5).setPositions(1, 2);
		pieces.get(6).setPositions(2, 0);
		pieces.get(7).setPositions(2, 1);
		pieces.get(8).setPositions(2, 2);
		pieces.get(9).setPositions(0, 3);
		pieces.get(10).setPositions(0, 4);
		pieces.get(11).setPositions(0, 5);
		pieces.get(12).setPositions(1, 3);
		pieces.get(13).setPositions(1, 4);
		pieces.get(14).setPositions(1, 5);
		pieces.get(15).setPositions(2, 3);
		pieces.get(16).setPositions(2, 4);
		pieces.get(17).setPositions(2, 5);
		pieces.get(18).setPositions(3, 0);
		pieces.get(19).setPositions(3, 1);
		pieces.get(20).setPositions(3, 2);
		pieces.get(21).setPositions(4, 0);
		pieces.get(22).setPositions(4, 1);
		pieces.get(23).setPositions(4, 2);
		pieces.get(24).setPositions(5, 0);
		pieces.get(25).setPositions(5, 1);
		pieces.get(26).setPositions(5, 2);
		pieces.get(27).setPositions(3, 3);
		pieces.get(28).setPositions(3, 4);
		pieces.get(29).setPositions(3, 5);
		pieces.get(30).setPositions(4, 3);
		pieces.get(31).setPositions(4, 4);
		pieces.get(32).setPositions(4, 5);
		pieces.get(33).setPositions(5, 3);
		pieces.get(34).setPositions(5, 4);
		pieces.get(35).setPositions(5, 5);
		
		return pieces;
	}
}