package controller;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import view.choice;

public class Controller2 implements ActionListener,MouseListener {
	private choice view;
	private static Hero p1;
	private static Hero p2;
	private JButton b;
	private Icon i;
	private JButton br;
	private Icon ir;

	public Controller2() throws IOException, CloneNotSupportedException {
		view = new choice();
		
		
		
		
		Hero m = new Mage();
		JButton c = new JButton();
		createHero(c, m);
		c.addActionListener(this);
		c.addMouseListener(this);
		c.setName("hero1");
		view.getP1Heros().add(c);
		c = new JButton();
		createHero(c, m);
		c.setName("opponent1");
		c.addActionListener(this);
		c.addMouseListener(this);
		view.getP2Heros().add(c);

		Hero p = new Paladin();
		c = new JButton();
		createHero(c, p);
		c.setName("hero2");
		c.addActionListener(this);
		c.addMouseListener(this);
		view.getP1Heros().add(c);
		c = new JButton();
		createHero(c, p);
		c.setName("opponent2");
		c.addActionListener(this);
		c.addMouseListener(this);
		view.getP2Heros().add(c);

		Hero h = new Hunter();

		c = new JButton();
		createHero(c, h);
		c.setName("hero3");
		c.addActionListener(this);
		c.addMouseListener(this);
		view.getP1Heros().add(c);
		c = new JButton();
		createHero(c, h);
		c.setName("opponent3");
		c.addActionListener(this);
		c.addMouseListener(this);
		view.getP2Heros().add(c);

		Hero pr = new Priest();
		c = new JButton();
		createHero(c, pr);
		c.setName("hero4");
		c.addActionListener(this);
		c.addMouseListener(this);
		view.getP1Heros().add(c);
		c = new JButton();
		createHero(c, pr);
		c.setName("opponent4");
		c.addActionListener(this);
		c.addMouseListener(this);
		view.getP2Heros().add(c);

		Hero w = new Warlock();
		c = new JButton();
		createHero(c, w);
		c.setName("hero5");
		c.addActionListener(this);
		c.addMouseListener(this);
		view.getP1Heros().add(c);
		c = new JButton();
		createHero(c, w);
		c.setName("opponent5");
		c.addActionListener(this);
		c.addMouseListener(this);
		view.getP2Heros().add(c);
		
		
//		c= new JButton();
//		c.setBackground(Color.black);
		// vev.getH().add(c, 3);
		view.getPlay().addActionListener(this);
		view.getPlay().addMouseListener(this);
		view.revalidate();
		view.repaint();

	}

	public choice getVev() {
		return view;
	}

	public static Hero getSa() {
		return p1;
	}

	public static Hero getYa() {
		return p2;
	}
	public static void createHero(JButton c, Hero b) {
		c.setPreferredSize(new Dimension(Controller.cal(240), Controller.cal(316)));
		ImageIcon icon = new ImageIcon("images/Heros/full/" + b.getName() + ".png");
		c.setIcon(Controller.resizeIcon(icon, Controller.cal(240), Controller.cal(316)));
		c.setHorizontalTextPosition(SwingConstants.CENTER);
		c.setOpaque(false);
		c.setContentAreaFilled(false);
		c.setBorderPainted(false);
	}

	// public JButton getAttackerb() {
	// return attackerb;
	// }

	
	/*
	 * public static void createHeroFull(JButton c, Hero b) { c.setPreferredSize(new
	 * Dimension(Controller.cal(180), Controller.cal(237))); ImageIcon icon = new
	 * ImageIcon("images/Heros/full" + b.getName() + ".png");
	 * c.setIcon(Controller.resizeIcon(icon, Controller.cal(180),
	 * Controller.cal(237))); c.setHorizontalTextPosition(SwingConstants.CENTER);
	 * c.setOpaque(false); c.setContentAreaFilled(false); c.setBorderPainted(false);
	 * }
	 */

	
	/*
	 * public static void createHero(JButton c, Hero b) { c.setPreferredSize(new
	 * Dimension(Controller.cal(180), Controller.cal(237))); ImageIcon icon = new
	 * ImageIcon("images/" + b.getName() + ".png"); c.setIcon(resizeIcon(icon,
	 * calW(120), calH(168))); c.setHorizontalTextPosition(SwingConstants.CENTER);
	 * c.setOpaque(false); c.setContentAreaFilled(false); c.setBorderPainted(false);
	 * }
	 */
	 

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) (e.getSource());
		
		/*
		 * if(this.b != null) { view.getVersus().add(this.b); } if(this.br != null) {
		 * view.getVersus().add(br); }
		 */
		
		if (b.getName().equals("hero1")) {
			re(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Jaina Proudmoore select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p1 = new Mage();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		} else if (b.getName().equals("hero2")) {
			re(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Uther Lightbringer select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p1 = new Paladin();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		} else if (b.getName().equals("hero3")) {
			re(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Rexxar select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p1 = new Hunter();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		} else if (b.getName().equals("hero4")) {
			re(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Anduin Wrynn select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p1 = new Priest();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		} else if (b.getName().equals("hero5")) {
			re(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Gul'dan select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p1 = new Warlock();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println(p1.getName());
		if (b.getName().equals("opponent1")) {
			rr(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Jaina Proudmoore select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p2 = new Mage();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		} else if (b.getName().equals("opponent2")) {
			rr(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Uther Lightbringer select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p2 = new Paladin();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		} else if (b.getName().equals("opponent3")) {
			rr(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Rexxar select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p2 = new Hunter();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		} else if (b.getName().equals("opponent4")) {
			rr(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Anduin Wrynn select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p2 = new Priest();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		} else if (b.getName().equals("opponent5")) {
			rr(b);
			b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Gul'dan select.png"),Controller.cal(240), Controller.cal(316)));
			try {
				p2 = new Warlock();
			} catch (IOException | CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println(p2.getName());
		if (!p1.equals(null) && !p2.equals(null)) {
			System.out.println(p1.getName());
			System.out.println(p2.getName());
			if (b.getName().equals("Play")) {
				view.getPlay().setIcon(Controller.resizeIcon(new ImageIcon("images/Buttons/fff select.png"),Controller.cal(211),Controller.cal(138)));
				view.dispose();
				try {
					new Controller(p1, p2);
				} catch (FullHandException | NotYourTurnException | NotEnoughManaException | FullFieldException
						| CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			}
		}

	}
	public void re(JButton b) {
		
		if(this.b != null) {
		this.b.setIcon(i);
	}
		view.getP1().setIcon(b.getIcon());
	i = b.getIcon();
	this.b = b;
	
	b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Rexxar select.png"),Controller.cal(240), Controller.cal(316)));
	}
	
	public void rr(JButton b) {
		if(this.br != null) {
			this.br.setIcon(ir);
		}
		view.getP2().setIcon(b.getIcon());
		ir = b.getIcon();
		this.br = b;
		
		b.setIcon(Controller.resizeIcon(new ImageIcon("images/Heros/Rexxar select.png"),Controller.cal(240), Controller.cal(316)));
		}
	public static void main(String[] args) {
		try {
			new Controller2();
		} catch (IOException | CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton b = (JButton) (e.getSource());

		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if(b.getName().equals("Play")) {
			view.getPlay().setIcon(Controller.resizeIcon(new ImageIcon("images/Buttons/fff select.png"),Controller.cal(211),Controller.cal(138)));
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton b = (JButton) (e.getSource());
		if(b.getName().equals("Play")) {
			view.getPlay().setIcon(Controller.resizeIcon(new ImageIcon("images/Buttons/fff.png"),Controller.cal(211),Controller.cal(138)));
		}
		
	}
}
