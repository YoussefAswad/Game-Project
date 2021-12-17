package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;

@SuppressWarnings("serial")
public class GameView extends JFrame {

	private ImagePanel background;
		private JPanel handPanel;
		private JPanel centralPanel;
			private JPanel opponentHeroPanel;
			private JPanel currentHeroPanel;
			private JPanel middlePanel;
				private JPanel field;
					private JPanel currentField;
					private JPanel opponentField;
				private JPanel endPanel;
				
	

	public GameView() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int wi = (int) screenSize.getWidth();
		int hi = (int) screenSize.getHeight();
		
		//Background Panel
		background = new ImagePanel(
				((ImageIcon) Controller.resizeIcon(new ImageIcon("images/Background.png"),Controller.cal(1920),Controller.cal(1080))).getImage());
		background.setLayout(new BorderLayout());
		
		//Hand Panel
		handPanel = new JPanel();
		handPanel.setOpaque(false);
		handPanel.setPreferredSize(new Dimension(calW(375), 0));
		
		
		//Central Panel
		centralPanel = new JPanel();
		centralPanel.setOpaque(false);
		FlowLayout layout = (FlowLayout)centralPanel.getLayout();
		layout.setVgap(0);
		//centralPanel.setLayout(new BorderLayout());
		
		
		//Opponent Hero Panel
		opponentHeroPanel = new JPanel();
		opponentHeroPanel.setOpaque(false);
		opponentHeroPanel.setBackground(new Color(1f,0f,0f,.5f));
		
		//Current Hero Panel
		
		
		currentHeroPanel = new JPanel();
		currentHeroPanel.setOpaque(false);
		currentHeroPanel.setBackground(new Color(0f,0f,0f,.5f));
		//currentHeroPanel.setLayout(new BorderLayout());
		
		//Middle Panel
		middlePanel = new JPanel();
		middlePanel.setOpaque(false);
		//field.setLayout(new GridLayout(1, 2));
		middlePanel.setPreferredSize(new Dimension(Controller.cal(1360),Controller.cal(393)));
		middlePanel.setLayout(new BorderLayout());
		
		//Opponent Field Panel
		opponentField = new JPanel();
		opponentField.setOpaque(false);
		opponentField.setBackground(new Color(0f,1f,0f,.5f));
		opponentField.setPreferredSize(new Dimension(0,Controller.cal(393/2)));
		//Current Hero Field Panel
		
		currentField = new JPanel();
		currentField.setOpaque(false);
		currentField.setBackground(new Color(0f,0f,1f,.5f));
		currentField.setPreferredSize(new Dimension(0,Controller.cal(393/2)));
		
		//End Button Panel
		endPanel = new JPanel();
		endPanel.setOpaque(false);
		//endPanel.setBackground(Color.blue);
		
		//Field Panel
		field = new JPanel();
		field.setPreferredSize(new Dimension(Controller.cal(1920)-(Controller.cal(190)+calW(375)),Controller.cal(393)));
		field.setLayout(new GridLayout(2, 1));
		field.setOpaque(false);
		
		JPanel s = new JPanel();
		JPanel n = new JPanel();
		JPanel s1 = new JPanel();
		JPanel n1 = new JPanel();
		
		n.setLayout(new BorderLayout());
		n1.setLayout(new BorderLayout());
		//s.setPreferredSize(new Dimension(0,(hi-Controller.cal(2*225)-Controller.cal(530))));
		s1.setPreferredSize(new Dimension(0,Controller.cal(76)));
		n1.setOpaque(false);
		n1.setPreferredSize(new Dimension(Controller.cal(1360),Controller.cal(225+76)));
		s.setOpaque(false);
		s1.setOpaque(false);
		n.setOpaque(false);
		s.setLayout(null);
		//Add
		
		field.add(opponentField);
		field.add(currentField);
		middlePanel.add(field,BorderLayout.WEST);
		n1.add(opponentHeroPanel,BorderLayout.SOUTH);
		n1.add(s1,BorderLayout.NORTH);
		n.add(currentHeroPanel, BorderLayout.NORTH);
		n.add(s,BorderLayout.SOUTH);
		centralPanel.add(n1);
		middlePanel.add(endPanel,BorderLayout.EAST);
		//centralPanel.add(currentHeroPanel, BorderLayout.SOUTH);
		centralPanel.add(middlePanel);
		centralPanel.add(n);
		
		background.add(centralPanel, BorderLayout.CENTER);
		//background.add(endPanel,BorderLayout.EAST);
		background.add(handPanel, BorderLayout.WEST);
		add(background);
		
		//GameView 
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0,wi,hi);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		revalidate();
		repaint();
		setVisible(true);

	}
	public JPanel getW() {
		return endPanel;
	}
	public void setW(JPanel w) {
		this.endPanel = w;
	}
	public static int calW(int w) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int wi = (int) screenSize.getWidth();
		return wi*w/1280;
	}
	public static int calH(int h) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int hi = (int) screenSize.getHeight();
		return hi*h/720;
	}
	public JPanel getH() {
		return handPanel;
	}

	public JPanel getR() {
		return centralPanel;
	}

	public JPanel getoH() {
		return opponentHeroPanel;
	}

	public JPanel getcH() {
		return currentHeroPanel;
	}

	public JPanel getF() {
		return middlePanel;
	}

	public JPanel getcF() {
		return currentField;
	}

	public JPanel getoF() {
		return opponentField;
	}
	public void setH(JPanel h) {
		this.handPanel = h;
	}

	

}