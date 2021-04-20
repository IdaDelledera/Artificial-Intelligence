package Interface;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartFrame extends JFrame {
	private static final long serialVersionUID = 595631430863981603L;
	private Box box;
	private JButton gameButton,exitButton;
	private JPanel downLine;

	private class MainContainer extends Container {
		private static final long serialVersionUID = 6915709672157377950L;
		private final int spaceExcess = 20;
		private Image pentago;
		private Dimension size;

		public MainContainer() {
			pentago = new ImageIcon("Second delivery/Code/img/pentago.jpg").getImage();


			size = new Dimension(926- spaceExcess,
					 499- spaceExcess);

			this.setPreferredSize(size);
			this.setBackground(Color.BLACK);
			this.setFocusable(true);
			this.setVisible(true);
		}

		public void paint(Graphics g) {
			g.drawImage(pentago, 0, 0, null);
		}
	}

	public StartFrame() {
		box = Box.createVerticalBox();
		box.add(new MainContainer());

		gameButton = new JButton("Play");
		gameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new OptionsFrame();
			}
		});

		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		downLine = new JPanel(new FlowLayout());
		downLine.setBackground(Color.BLACK);
		downLine.add(gameButton);
		downLine.add(exitButton);

		box.add(downLine);

		this.setContentPane(box);
		this.setTitle("P E N T A G O - Approccio MinMax");
		this.setVisible(true);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}



	public static void main(String[] args) {
		new StartFrame();
	}
}