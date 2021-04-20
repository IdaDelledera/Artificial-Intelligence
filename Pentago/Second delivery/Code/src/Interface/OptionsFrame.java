package Interface;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Game.GameThread;
import Game.GameBoard;;import static java.lang.Integer.parseInt;

public class OptionsFrame extends JFrame {
	private static final long serialVersionUID = -3367328642501208248L;
	private Box box;
	private JButton startButton, menuButton;
	private JPanel downLine;
	private JComboBox<String> levelComboBox, typeComboBox, algorithmComboBox;

	private class OptionsBox extends Box {
		private static final long serialVersionUID = 5427769975055181858L;
		private final Font bigFont = new Font("Aharoni", Font.BOLD, 23);

		public OptionsBox() {
			super(BoxLayout.Y_AXIS);

			JLabel typeLabel = new JLabel("Tipo di gioco:", JLabel.CENTER);
			typeLabel.setFont(bigFont);
			JLabel levelLabel = new JLabel("Intelligenza Artificiale:",
					JLabel.CENTER);
			levelLabel.setFont(bigFont);
			JLabel algorithmLabel = new JLabel("Minmax profondità:",
					JLabel.CENTER);
			algorithmLabel.setFont(bigFont);


			typeLabel.setAlignmentX(CENTER_ALIGNMENT);
			algorithmLabel.setAlignmentX(CENTER_ALIGNMENT);
			levelLabel.setAlignmentX(CENTER_ALIGNMENT);

			String[] typeValues = { "SCEGLI MODALITA' DI GIOCO",
					"Umano vs Computer", "Umano vs Umano" };
			String[] algorithmValues = { "SCEGLI UNA PROFONDITA'", "1",
					"2", "3", "4" };		
			String[] levelValues = {"Minimax con potatura alpha-beta"};

			typeComboBox = new JComboBox<>(typeValues);		
			algorithmComboBox = new JComboBox<>(algorithmValues);		
			levelComboBox = new JComboBox<>(levelValues);

			algorithmComboBox.setEnabled(false);
			levelComboBox.setEnabled(false);

			typeComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (typeComboBox.getSelectedItem().equals("Umano vs Computer") ||
							typeComboBox.getSelectedItem().equals("Umano vs Umano")) {
						levelComboBox.setEnabled(true);
					}
					else {
						algorithmComboBox.setEnabled(false);
						levelComboBox.setEnabled(false);
					}
				}
			});
			
			levelComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (levelComboBox.getSelectedItem().equals("Minimax con potatura alpha-beta"))
						algorithmComboBox.setEnabled(true);
					else
						algorithmComboBox.setEnabled(false);
				}
			});

			Box decisionOneBox = Box.createHorizontalBox();
			Box decisionTwoBox = Box.createHorizontalBox();
			Box decisionThreeBox = Box.createHorizontalBox();

			decisionOneBox.add(new JLabel("      "));
			decisionOneBox.add(typeComboBox);
			decisionOneBox.add(new JLabel("      "));

			decisionTwoBox.add(new JLabel("      "));
			decisionTwoBox.add(levelComboBox);
			decisionTwoBox.add(new JLabel("      "));

			decisionThreeBox.add(new JLabel("      "));
			decisionThreeBox.add(algorithmComboBox);
			decisionThreeBox.add(new JLabel("      "));

			add(typeLabel);
			add(new JLabel(" "));
			add(decisionOneBox);
			add(new JLabel(" "));
			add(levelLabel);
			add(new JLabel(" "));
			add(decisionTwoBox);
			add(new JLabel(" "));
			add(algorithmLabel);
			add(new JLabel(" "));
			add(decisionThreeBox);
			add(new JLabel(" "));

			setFocusable(true);
		}
	}

	public OptionsFrame() {
		box = Box.createVerticalBox();
		box.add(new OptionsBox());

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new StartFrame();
			}
		});

		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				    String title = null;
					String gameType = (String) typeComboBox.getSelectedItem();
					title = "Pentago - " + gameType;

					String depth = (String) algorithmComboBox.getSelectedItem();
					int depthInt = 0;

					if (depth != "SCEGLI")
						depthInt = parseInt(depth);

					dispose();
					if (typeComboBox.getSelectedItem().equals("Umano vs Umano"))
						new GameFrame(new GameThread(new GameBoard(true, false), depthInt-1), title);
					else
						new GameFrame(new GameThread(new GameBoard(false, true), depthInt), title);

			}
		});


		downLine = new JPanel(new FlowLayout());
		downLine.setBackground(Color.WHITE);
		downLine.add(startButton);


		box.add(downLine);

		setContentPane(box);
		setTitle("Opzioni");
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}