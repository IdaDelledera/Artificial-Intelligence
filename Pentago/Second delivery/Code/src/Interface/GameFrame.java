package Interface;
import Game.GameBoard;
import Game.GamePiece;
import Game.GameThread;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 5551955481304037628L;
	private GameBoard board;
	private GameThread gameThread;
	public int arrowControl = 0;
	private boolean mouseLock = false;

	public GameFrame(GameThread gameThread, String frameTitle) {
		this.gameThread = gameThread;
		this.board = gameThread.getBoard();
		gameThread.setFrame(this);
		gameThread.start();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				new StartFrame();
			}
		});

		GamePanel gamePanel = new GamePanel();
		this.setContentPane(gamePanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle(frameTitle);
	}

	public void setMouseLock(boolean mouseLock) {
		this.mouseLock = mouseLock;
	}

	private class GamePanel extends JPanel implements MouseListener,
	MouseMotionListener {
		private static final long serialVersionUID = 91113099161373536L;
		private Image gameBackground, logo, human, computer;
		private final int HEIGHT = 750, WIDTH = 800, EXCESS = 12, ARC = 10;
		private final int REC1X = 126, REC1Y = 126, REC1H = 220, REC1W = 220,
				REC2X = 361, REC2Y = 126, REC2H = 220, REC2W = 220,
				REC3X = 126, REC3Y = 361, REC3H = 220, REC3W = 220,
				REC4X = 361, REC4Y = 361, REC4H = 220, REC4W = 220,

				HUMANX = 100, HUMANY = 400, COMPX = 170, COMPY = 700;
		private Vector<GamePiece> pieces = new Vector<GamePiece>();
		private Vector<BasicArrowButton> arrows = new Vector<BasicArrowButton>();

		public GamePanel() {
			setPreferredSize(new Dimension(HEIGHT - EXCESS, WIDTH - EXCESS));
			addMouseListener(this);
			addMouseMotionListener(this);
			createPieces();
			createArrows();
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.clearRect(0, 0, HEIGHT, WIDTH);
			this.drawBoard(g2d);

			if (arrowControl == 1) {
				for (BasicArrowButton arrow : arrows) {
					arrow.setVisible(true);
				}
			} else {
				for (BasicArrowButton arrow : arrows) {
					arrow.setVisible(false);
				}
			}

			if (board.isBlackBot() && board.isWhiteBot())
				g2d.drawImage(computer, HUMANX, HUMANY, null);
			else if (!board.isBlackBot() && !board.isWhiteBot())
				g2d.drawImage(human, COMPX, COMPY, null);
			else if (board.getActualPlayer() == 1)
				g2d.drawImage(human, HUMANX, HUMANY, null);
			else if (board.getActualPlayer() == 2)
				g2d.drawImage(computer, COMPX, COMPY, null);

			this.drawMessages(g2d);
		}

		public void unpaint() {
			for (GamePiece c: pieces)
				if (c.getColor().equals(Color.decode("#e83088")))
					c.changeColor(Color.decode("#f58400"));
		}

		public void drawBoard(Graphics2D g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setStroke(new BasicStroke(12,
					BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

			g2d.setColor(Color.BLACK);
			g2d.drawRoundRect(REC1X, REC1Y, REC1H, REC1W, ARC, ARC);
			g2d.drawRoundRect(REC2X, REC2Y, REC2H, REC2W, ARC, ARC);
			g2d.drawRoundRect(REC3X, REC3Y, REC3H, REC3W, ARC, ARC);
			g2d.drawRoundRect(REC4X, REC4Y, REC4H, REC4W, ARC, ARC);

			g2d.setColor(Color.decode("#2A9D8F"));
			g2d.fillRoundRect(REC1X, REC1Y, REC1H, REC1W, ARC, ARC);
			g2d.fillRoundRect(REC2X, REC2Y, REC2H, REC2W, ARC, ARC);
			g2d.fillRoundRect(REC3X, REC3Y, REC3H, REC3W, ARC, ARC);
			g2d.fillRoundRect(REC4X, REC4Y, REC4H, REC4W, ARC, ARC);

			g2d.setColor(Color.WHITE);


			for (int i = 0; i < pieces.size(); i++) {
				g2d.setStroke(new BasicStroke(3,
						BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

				int lx = pieces.get(i).getPosX();
				int ly = pieces.get(i).getPosY();

				if (board.getPiece(lx, ly) == 1)
					g2d.setColor(Color.BLACK);
				else if (board.getPiece(lx, ly) == 2)
					g2d.setColor(Color.WHITE);
				else
					g2d.setColor(pieces.get(i).getColor());


				g2d.fill(pieces.get(i));
				g2d.setColor(Color.BLACK);
				g2d.draw(pieces.get(i));
			}
		}

		public void drawMessages(Graphics2D g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			Font font1 = new Font("Serif", Font.PLAIN, 36);
			g2d.setFont(font1);

			if (board.getActualPlayer() == 1)
				g2d.drawString("Turno della biglia nera:", 10, 30);
			else
				g2d.drawString("Turno della biglia bianca:", 10, 30);

			Font font2 = new Font("Serif", Font.PLAIN, 20);
			g2d.setFont(font2);

			g2d.drawString("\u2192 metti una biglia.", 10, 50);
			g2d.drawString("\u2192 ruota la tavola.", 10, 70);
		}

		public void createPieces() {
			Color c = Color.decode("#66b229");
			int radius = 55, interv = 73;

			for (int i = 135; i <= 284; i += interv)
				for (int j = 135; j <= 284; j += interv)
					pieces.add(new GamePiece(i, j, radius, radius, c));

			for (int i = 135; i <= 284; i += interv)
				for (int j = 370; j <= 519; j += interv)
					pieces.add(new GamePiece(i, j, radius, radius, c));

			for (int i = 370; i <= 519; i += interv)
				for (int j = 135; j <= 284; j += interv)
					pieces.add(new GamePiece(i, j, radius, radius, c));

			for (int i = 370; i <= 519; i += interv)
				for (int j = 370; j <= 519; j += interv)
					pieces.add(new GamePiece(i, j, radius, radius, c));

			pieces = GamePiece.createPieces(pieces);
		}

		public void createArrows() {
			for (int i = 0; i < 8; i++) {
				final BasicArrowButton arrow = new BasicArrowButton(BasicArrowButton.EAST, 
						Color.decode("#d3c29b"), Color.BLACK, Color.BLACK, Color.BLACK);

				arrow.setVisible(false);
				arrow.setName(Integer.toString(i));

				switch (i) {
				case 0:
				case 4:
					arrow.setDirection(BasicArrowButton.EAST);
					break;
				case 1:
				case 3:
					arrow.setDirection(BasicArrowButton.SOUTH);
					break;
				case 2:
				case 6:
					arrow.setDirection(BasicArrowButton.WEST);
					break;
				case 5:
				case 7:
					arrow.setDirection(BasicArrowButton.NORTH);
					break;
				}

				arrow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (board.isGameOver() == -1) {
							board.rotate(arrow.getName());
							unpaint();
							gameThread.getFrame().repaint();
							arrowControl = 0;
							mouseLock = false;
							board.changePlayers();
						}
					} 
				} );
				arrows.add(arrow);
			}

			Insets insets = this.getInsets();
			Dimension size = new Dimension(100, 30);

			arrows.get(0).setBounds(130 + insets.left, 85 + insets.top,
					size.width, size.height);
			arrows.get(1).setBounds(85 + insets.left, 130 + insets.top,
					size.height, size.width);
			arrows.get(2).setBounds(477 + insets.left, 85 + insets.top,
					size.width, size.height);
			arrows.get(3).setBounds(592 + insets.left, 130 + insets.top,
					size.height, size.width);
			arrows.get(4).setBounds(130 + insets.left, 592 + insets.top,
					size.width, size.height);
			arrows.get(5).setBounds(85 + insets.left, 475 + insets.top,
					size.height, size.width);
			arrows.get(6).setBounds(477 + insets.left, 592 + insets.top,
					size.width, size.height);
			arrows.get(7).setBounds(592 + insets.left, 475 + insets.top,
					size.height, size.width);

			this.setLayout(null);

			for (BasicArrowButton b : arrows) {
				this.add(b);
			}
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
		}

		@Override
		public void mouseMoved(MouseEvent m) {
			if (!mouseLock) {
				GamePiece c = null;

				for (int i = 0; i < pieces.size(); i++)
					if (pieces.get(i).contains(m.getX(), m.getY()))
						c = pieces.get(i);

				if (c == null) {
					for (int j = 0; j < pieces.size(); j++)
						if (pieces.get(j).getColor().equals(Color.decode("#bdae8b")))
							pieces.get(j).changeColor(Color.decode("#d3c29b"));
				} else if (c.getColor().equals(Color.decode("#d3c29b")))
					c.changeColor(Color.decode("#bdae8b"));
				
				repaint();
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {	
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent m) {
			if (!mouseLock) {
				for (int i = 0; i < pieces.size(); i++) {
					if (pieces.get(i).contains(new Point(m.getX(), m.getY()))) {
						if (board.getPiece(pieces.get(i).getPosX(), pieces.get(i).getPosY()) == 0) {
							int player = board.getActualPlayer();
							arrowControl = 1;
							board.placePiece(pieces.get(i).getPosX(), pieces.get(i).getPosY(), player);
							mouseLock = true;
						}

						repaint();
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
}