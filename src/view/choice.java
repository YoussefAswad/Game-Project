package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;

@SuppressWarnings("serial")
public class choice extends JFrame {
	private JPanel p1Heros = new JPanel();
	private JPanel p2Heros = new JPanel();
	private JButton play = new JButton();
	private JPanel background;
	private JPanel versus;
	private JButton p1=new JButton();
	private JButton p2= new JButton();
	
	//private JPanel c;
	public choice()
	{
		versus = new JPanel();
		
		
		background = new ImagePanel(
				((ImageIcon) Controller.resizeIcon(new ImageIcon("images/Start.png"),Controller.cal(1920),Controller.cal(1020))).getImage());
		background.setLayout(null);
		
		p1Heros.setBackground(Color.cyan);
		//h.setPreferredSize(new Dimension(calW(375), 0));
		//h.setPreferredSize(new Dimension(100, 400));
		p2Heros.setBackground(Color.red);
		//r.setPreferredSize(new Dimension(calW(375), 0));
		//r.setPreferredSize(new Dimension(100, 700));
		play.setName("Play");
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.setIcon(Controller.resizeIcon(new ImageIcon("images/Buttons/fff.png"),Controller.cal(211),Controller.cal(138)));
		p2Heros.setLayout(new GridLayout(0,5));
		p1Heros.setLayout(new GridLayout(0,5));
		setLayout(new FlowLayout());
		//background.add(chooseP1);
		background.add(p1Heros);
		p1Heros.setBounds(Controller.cal(50),Controller.cal(180),Controller.cal(1200),Controller.cal(316));
		//add(c, BorderLayout.CENTER);
		//background.add(chooseP2);
		background.add(p2Heros);
		p2Heros.setBounds(Controller.cal(50),Controller.cal(600),Controller.cal(1200),Controller.cal(316));
		background.add(play);
		play.setBounds(Controller.cal(1440),Controller.cal(774),Controller.cal(211),Controller.cal(138));
		background.add(versus);
		versus.setLayout(null);
		versus.add(p1);
		p1.setOpaque(false);
		p1.setContentAreaFilled(false);
		p1.setBorderPainted(false);
		p1.setBounds(0,0,Controller.cal(240),Controller.cal(316));
		versus.add(p2);
		p2.setOpaque(false);
		p2.setContentAreaFilled(false);
		p2.setBorderPainted(false);
		p2.setBounds(Controller.cal(360),0,Controller.cal(240),Controller.cal(316));
		versus.setOpaque(false);
		versus.setBounds(Controller.cal(1235),Controller.cal(350),Controller.cal(600),Controller.cal(300));
		add(background);
		//background.add(new JButton("Helo"));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		p1Heros.setOpaque(false);
		p2Heros.setOpaque(false);
		this.setSize(new Dimension(Controller.cal(1920),Controller.cal(1020)));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.revalidate();
		this.repaint();
		this.setVisible(true);

	}
	public JPanel getP1Heros() {
		return p1Heros;
	}
	public JPanel getP2Heros() {
		return p2Heros;
	}
//	public static void main(String[] args) {
//		new choice();
//	}

public JButton getPlay() {
		return play;
	}
public JPanel getVersus() {
	return versus;
}
public JButton getP1() {
	return p1;
}
public JButton getP2() {
	return p2;
}


}
