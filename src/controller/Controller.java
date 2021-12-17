package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.ShadowWordDeath;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import view.GameView;
import view.OutlineLabel;
import view.gameover;

public class Controller implements GameListener, ActionListener, MouseListener {

	private GameView view;
	private Game model;
	private Card attacker;
	private boolean heroPower;
	private OutlineLabel ex;
	public Controller(Hero P1, Hero P2) throws FullHandException, CloneNotSupportedException, NotYourTurnException,
			NotEnoughManaException, FullFieldException {
		model = new Game(P1, P2);
		view = new GameView();

		ex = new OutlineLabel();
		ex.setForeground(Color.white);
		ex.setB(3);
		ex.setOutlineColor(Color.black);
		// view.add(ex,BorderLayout.EAST);
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/BelweBoldBT.TTF")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/belwe.TTF")));
		} catch (Exception e) {
			// Handle exception
		}
		// Hand

		drawHand();

		drawField();
		drawHeros();

		view.revalidate();
		view.repaint();

	}

	public void createCardHand(JButton c, Card b) {
		c.setPreferredSize(new Dimension(cal720(120), cal720(168)));
		if (!(b instanceof ShadowWordDeath)) {
			ImageIcon icon = new ImageIcon("images/Hand/" + b.getName() + ".png");
			c.setIcon(resizeIcon(icon, cal720(120), cal720(168)));
		} else {
			c.setIcon(resizeIcon(new ImageIcon("images/Hand/Shadow Word.png"), cal720(120), cal720(168)));
		}
		if (b instanceof Minion) {
			//drawHP(c, b);
			drawAttack(c, b);
			drawMana(c, b);
			drawHP(c, b);
			
		
		}
		//c.setText(b.getName());
		c.addMouseListener(this);
		c.setHorizontalTextPosition(SwingConstants.CENTER);
		c.setOpaque(false);
		c.setContentAreaFilled(false);
		c.setBorderPainted(false);
	}
	
	public void createCardField(JButton c, Card b) {
		
		c.setIcon(resizeIcon(new ImageIcon("images/Field/" + b.getName() + ".png"),cal(151),cal(196)));
		
		
		drawSleep(c, (Minion)b);
		//drawMana(c, b);
		drawDivine(c, (Minion)b);
		drawAttackF(c, b);
		drawHPF(c, b);
		c.setHorizontalTextPosition(SwingConstants.CENTER);
		c.setOpaque(false);
		c.setContentAreaFilled(false);
		c.setBorderPainted(false);
	}

	public static void createHero(JButton c, Hero b) {
		c.setPreferredSize(new Dimension(cal(180), cal(187)));
		ImageIcon icon = new ImageIcon("images/Heros/" + b.getName() + ".png");
		c.setIcon(resizeIcon(icon, cal(180), cal(187)));
		c.setHorizontalTextPosition(SwingConstants.CENTER);
		c.setOpaque(false);
		c.setContentAreaFilled(false);
		c.setBorderPainted(false);
	}
	

	public static JButton createPower(Hero b) {
		JButton c = new JButton();
		c.setPreferredSize(new Dimension(cal(100), cal(108)));
		ImageIcon icon = new ImageIcon("images/Heros/Power/" + b.getName() + " Hero Power.png");
		c.setIcon(resizeIcon(icon, cal(100), cal(108)));
		c.setHorizontalTextPosition(SwingConstants.CENTER);
		c.setOpaque(false);
		c.setContentAreaFilled(false);
		c.setBorderPainted(false);
		return c;

	}

	public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
		Image img = icon.getImage();
		Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);
	}

	public static int cal720(int w) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int wi = (int) screenSize.getWidth();
		return wi * w / 1280;
	}

	public static int cal(int w) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int wi = (int) screenSize.getWidth();
		return wi * w / 1920;
	}

	public static int cal(Icon icon, int o) {
		return cal(icon.getIconWidth() * o / cal720(120));
	}

	// Draw Methods
	public void drawHand() {
		// System.out.println("Hand Update");
		view.getH().removeAll();
		for (int i = 0; i < model.getCurrentHero().getHand().size(); i++) {
			JButton c = new JButton();

			c.addActionListener(this);
			createCardHand(c, model.getCurrentHero().getHand().get(i));
			
			//drawAttack(c, model.getCurrentHero().getHand().get(i));
			//drawMana(c, model.getCurrentHero().getHand().get(i));
			//drawHP(c, model.getCurrentHero().getHand().get(i));
			view.getH().add(c);
			view.revalidate();
			view.repaint();
		}

	}

	public void drawField() {

		drawCurrentField();
		drawOpponentField();
		view.revalidate();
		view.repaint();
	}

	public void drawCurrentField() {
		view.getcF().removeAll();
		for (int i = 0; i < model.getCurrentHero().getField().size(); i++) {
			JButton c = new JButton();
			createCardField(c, model.getCurrentHero().getField().get(i));
			System.out.print(c.getText());
			c.addActionListener(this);
			c.addMouseListener(this);
			//drawHP(c, model.getCurrentHero().getField().get(i));
			view.getcF().add(c);
		}
	}

	public void drawOpponentField() {
		view.getoF().removeAll();
		for (int i = 0; i < model.getOpponent().getField().size(); i++) {
			JButton c = new JButton();
			createCardField(c, model.getOpponent().getField().get(i));
			c.addActionListener(this);
			c.addMouseListener(this);
			//drawHP(c, model.getOpponent().getField().get(i));
			view.getoF().add(c);
		}
	}

	public void drawHeros() {
		view.getcH().removeAll();
		view.getoH().removeAll();
		view.getcH().setLayout(null);
		view.getcH().setPreferredSize(new Dimension(cal(1360),cal(225+95)));
		JButton currentHero = new JButton();
		currentHero.setActionCommand("Current Hero");
		currentHero.addActionListener(this);
		currentHero.addMouseListener(this);
		createHero(currentHero, model.getCurrentHero());
		drawHeroHP(currentHero, model.getCurrentHero());
		Insets i = view.getcH().getInsets();
		currentHero.setBounds(i.left+cal(538),i.top+cal(35),cal(180),cal(187));
		view.getcH().add(currentHero);
		JButton p = createPower(model.getCurrentHero());
		p.setActionCommand("Use Power");
		p.addActionListener(this);
		p.addMouseListener(this);
		view.getcH().add(p);
		p.setBounds(cal(750),cal(90),cal(100),cal(108));
		JButton m = drawHeroMana(model.getCurrentHero());
		m.setBounds(i.left+cal(400),i.top+cal(90),cal(100), cal(100));
		view.getcH().add(m);
		drawEnd();
		JLabel n = new JLabel(resizeIcon(new ImageIcon("images/Heros/name/"+model.getCurrentHero().getName()+" name.png"),cal(300),cal(123)));
		view.getcH().add(n);
		n.setBounds(cal(100),cal(100),cal(289),cal(163));

		
		view.getoH().setLayout(null);
		view.getoH().setPreferredSize(new Dimension(cal(1360),cal(225)));
		JButton opponent = new JButton();
		//opponent.setText(model.getOpponent().getName());
		opponent.addActionListener(this);
		createHero(opponent, model.getOpponent());
		opponent.setActionCommand("Opponent Hero");
		opponent.addMouseListener(this);
		Insets ii = view.getoH().getInsets();
		JButton p1 = createPower(model.getOpponent());
		p1.setActionCommand("Use Power");
		p1.addMouseListener(this);
		view.getoH().add(p1);
		p1.setBounds(cal(750),cal(70),cal(100),cal(108));
		JButton m1 = drawHeroMana(model.getOpponent());
		m1.setBounds(cal(400),cal(70),cal(100), cal(100));
		view.getoH().add(m1);
		JLabel n1 = new JLabel(resizeIcon(new ImageIcon("images/Heros/name/"+model.getOpponent().getName()+" name.png"),cal(300),cal(123)));
		view.getoH().add(n1);
		n1.setBounds(cal(100),cal(80),cal(289),cal(163));
		opponent.setBounds(ii.left+cal(541),i.top,cal(180),cal(187));
		drawHeroHP(opponent, model.getOpponent());
		ex.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(35)));
		//ex.setPreferredSize(new Dimension(cal(100), cal(120)));
		ex.setBounds(cal(1000),cal(10),cal(600),cal(60));
		ex.setOpaque(false);
		
		
		 
		view.getcH().add(ex);
		view.getoH().add(opponent);
		 if(!(ex.getText().equals(""))) { 
			 JLabel e =new JLabel(resizeIcon(new ImageIcon("images/error.png"),cal(30),cal(30))); view.getcH().add(e);
			 e.setBounds(cal(965),cal(28),cal(30),cal(30)); 
		 }
		view.revalidate();
		view.repaint();
	}

	public void drawHP(JButton b, Card c) {
		if (c instanceof Minion) {
			if (((Minion) c).getCurrentHP() < 10) {
				ImageIcon i = new ImageIcon("images/HP/HP" + ((Minion) c).getCurrentHP() + ".png");
				ImageIcon i2 = (ImageIcon) resizeIcon(i, b.getIcon().getIconWidth() - 2, b.getIcon().getIconHeight());
				JLabel hp = new JLabel(i2);
				b.setLayout(new BorderLayout());
				b.setMargin(new Insets(0, 0, 0, 0));
				b.add(hp, BorderLayout.CENTER);
			} else {
				b.setMargin(new Insets(0, 0, 0, 0));
				b.setLayout(new BorderLayout());
				JLabel hp = new JLabel();
				hp.setOpaque(false);
				hp.setIcon(resizeIcon(new ImageIcon("images/HP/HPO.png"), b.getIcon().getIconWidth() - 2,
						b.getIcon().getIconHeight()));
				OutlineLabel s = new OutlineLabel("" + ((Minion) c).getCurrentHP(), OutlineLabel.CENTER);
				s.setB(1);
				s.setForeground(Color.white);
				s.setOutlineColor(Color.black);
				s.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(b.getIcon(), 37)));
				s.setBounds(cal(b.getIcon(), 133), cal(b.getIcon(), 197), cal(b.getIcon(), 45), cal(b.getIcon(), 45));
				hp.add(s);
				b.add(hp, BorderLayout.CENTER);
			}
		}
	}
	public void drawDivine(JButton b, Minion c) {
		if(c.isDivine()) {
			JLabel d = new JLabel(resizeIcon((new ImageIcon("images/Divine.png")),cal(151),cal(196)));
			b.add(d);
			d.setBounds(0,0,cal(151),cal(196));
			
		}
	}
	
	public void drawHPF(JButton b, Card c) {
		if (c instanceof Minion) {
			
				b.setMargin(new Insets(0, 0, 0, 0));
				b.setLayout(null);
				b.setPreferredSize(new Dimension(cal(151),cal(196)));
				//JLabel hp = new JLabel();
				//hp.setOpaque(false);
				//hp.setPreferredSize(new Dimension(cal(151),cal(196)));
				//hp.setIcon(resizeIcon(new ImageIcon("images/HP/HPF.png"), b.getIcon().getIconWidth() - 2,
						//b.getIcon().getIconHeight()));
				OutlineLabel s = new OutlineLabel("" + ((Minion) c).getCurrentHP(), OutlineLabel.CENTER);
				s.setB(1);
				s.setForeground(Color.white);
				s.setOutlineColor(Color.black);
				s.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(b.getIcon(), 45)));
				s.setBounds(cal(b.getIcon(), 107), cal(b.getIcon(), 140), cal(b.getIcon(), 55), cal(b.getIcon(), 55));
				//hp.add(s);
				//b.add(hp, BorderLayout.CENTER);
				b.add(s);
			
		}
	}
	

	public void drawMana(JButton b, Card m) {
		b.setLayout(null);
		b.setMargin(new Insets(0, 0, 0, 0));
		JButton mana = new JButton(
				resizeIcon(new ImageIcon("images/Mana/M.png"), cal(b.getIcon(), 45), cal(b.getIcon(), 45)));
		mana.setMargin(new Insets(0, 0, 0, 0));
		mana.setOpaque(false);
		mana.setContentAreaFilled(false);
		mana.setBorderPainted(false);
		mana.setLayout(null);
		if (m.getRarity().equals(Rarity.LEGENDARY))
			mana.setBounds(cal(b.getIcon(), 6), cal(b.getIcon(), 14), cal(b.getIcon(), 45), cal(b.getIcon(), 45));
		else
			mana.setBounds(cal(b.getIcon(), 6), cal(b.getIcon(), 4), cal(b.getIcon(), 45), cal(b.getIcon(), 45));
		OutlineLabel s = new OutlineLabel("" + m.getManaCost(), OutlineLabel.CENTER);
		s.setB(1);
		s.setForeground(Color.white);
		s.setOutlineColor(Color.black);

		if (m.getManaCost() == 10) {
			s.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(b.getIcon(), 38)));
			s.setBounds(0, 0, cal(b.getIcon(), 40), cal(b.getIcon(), 40));
		} else {
			s.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(40)));
			s.setBounds(cal(b.getIcon(), 4), 0, cal(b.getIcon(), 40), cal(b.getIcon(), 40));
		}
		mana.add(s);
		b.add(mana);
	}

	public void drawManaFE(JButton b, Card m) {
		b.setLayout(null);
		b.setMargin(new Insets(0, 0, 0, 0));
		JButton mana = new JButton(resizeIcon(new ImageIcon("images/Mana/M.png"), cal(45), cal(45)));
		mana.setMargin(new Insets(0, 0, 0, 0));
		mana.setOpaque(false);
		mana.setContentAreaFilled(false);
		mana.setBorderPainted(false);
		mana.setLayout(null);
		if (m.getRarity().equals(Rarity.LEGENDARY))
			mana.setBounds(cal(6), cal(14), cal(45), cal(45));
		else
			mana.setBounds(cal(6), cal(4), cal(45), cal(45));
		b.add(mana);
	}

	public static void drawAttack(JButton b, Card m) {
		if (m instanceof Minion) {
			// Minion m = (Minion) c;
			b.setLayout(null);
			b.setMargin(new Insets(0, 0, 0, 0));
			JButton attack = new JButton();
			attack.setOpaque(false);
			attack.setContentAreaFilled(false);
			attack.setBorderPainted(false);
			attack.setLayout(null);
			if (((Minion) m).getAttack() <= 10) {
				attack.setBounds(0, cal(b.getIcon(), 190), cal(b.getIcon(), 60), cal(b.getIcon(), 60));
				attack.setIcon(resizeIcon(new ImageIcon("images/Attack/attack" + ((Minion) m).getAttack() + ".png"),
						cal(b.getIcon(), 60), cal(b.getIcon(), 60)));
			} else {
				attack.setIcon(resizeIcon(new ImageIcon("images/Attack/attack.png"), cal(b.getIcon(), cal720(38)),
						cal(b.getIcon(), cal720(38))));
				attack.setBounds(0, cal(b.getIcon(), 194), cal(b.getIcon(), cal720(38)), cal(b.getIcon(), cal720(38)));
				OutlineLabel s = new OutlineLabel("" + ((Minion) m).getAttack(), OutlineLabel.CENTER);
				s.setB(1);
				s.setForeground(Color.white);
				s.setOutlineColor(Color.black);
				if (((Minion) m).getAttack() >= 10) {
					s.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(37)));
					s.setBounds(cal(b.getIcon(), 5), cal(b.getIcon(), 8), cal(b.getIcon(), 40), cal(b.getIcon(), 40));
				} else {
					s.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(b.getIcon(), cal(39))));
					s.setBounds(cal(b.getIcon(), 10), cal(b.getIcon(), 8), cal(b.getIcon(), 40), cal(b.getIcon(), 40));
				}
				attack.add(s);
			}

			b.add(attack);
		}
	}
	
	public static void drawAttackF(JButton b, Card m) {
		b.setLayout(null);
		b.setMargin(new Insets(0, 0, 0, 0));
	OutlineLabel s = new OutlineLabel("" + ((Minion) m).getAttack(), OutlineLabel.CENTER);
	s.setB(1);
	s.setForeground(Color.white);
	s.setOutlineColor(Color.black);
	s.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(b.getIcon(), cal(45))));
	s.setBounds(cal(b.getIcon(), 15), cal(b.getIcon(), 142), cal(b.getIcon(), 55), cal(b.getIcon(), 55));
	b.add(s);
	}
	public void drawSleep(JButton b, Minion m) {
		if(m.isSleeping()) {
		b.setMargin(new Insets(0, 0, 0, 0));
		b.setLayout(null);
		JLabel s = new JLabel();
		s.setOpaque(false);
		s.setIcon(resizeIcon(new ImageIcon("images/Sleep.png"), cal(55), cal(55)));
		b.add(s);
		s.setBounds(cal(97), 0, cal(55), cal(55));
		}
	}
	
	
	
	
	public void drawHeroHP(JButton b, Hero h) {
		b.setMargin(new Insets(0, 0, 0, 0));

		JLabel hp = new JLabel();
		hp.setOpaque(false);

		hp.setIcon(resizeIcon(new ImageIcon("images/HP/HHP.png"), cal(180), cal(187)));

		OutlineLabel s = new OutlineLabel("" + h.getCurrentHP(), OutlineLabel.CENTER);
		;
		s.setB(1);
		s.setForeground(Color.white);
		s.setOutlineColor(Color.black);
		s.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(38)));

		s.setFont(new Font("Belwe Bd BT", Font.PLAIN, cal(40)));
		s.setBounds(cal(126), cal(133), cal(45), cal(45));

		hp.add(s);
		b.add(hp);
	}

	public JButton drawHeroMana(Hero h) {
		ImageIcon i = (ImageIcon) resizeIcon(new ImageIcon("images/Mana/M.png"), cal(100), cal(100));
		JButton b = new JButton(i);
		b.setPreferredSize(new Dimension(cal(100), cal(100)));
		b.setHorizontalTextPosition(SwingConstants.CENTER);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.setLayout(null);
		OutlineLabel mana = new OutlineLabel("" + h.getCurrentManaCrystals(), OutlineLabel.CENTER);

		mana.setForeground(Color.white);
		mana.setOutlineColor(Color.black);
		mana.setFont(new Font("Belwe Medium BT", Font.PLAIN, cal(75)));
		b.add(mana);
		mana.setBounds(0, 0, cal(105), cal(93));
		return b;
	}

	public void drawEnd() {
		view.getW().removeAll();
		view.getW().setLayout(null);
		view.getW().setPreferredSize(new Dimension(cal(200),cal(393)));
		JButton end = new JButton();
		end.setFont(new Font("Comic Sans MS", Font.PLAIN, 1));
		end.setText("end");
		end.addActionListener(this);
		OutlineLabel e = new OutlineLabel("End Turn", OutlineLabel.CENTER);
		e.setForeground(Color.white);
		e.setOutlineColor(Color.black);
		e.setFont(new Font("Belwe Medium BT", Font.PLAIN, cal(27)));
		end.setIcon(resizeIcon(new ImageIcon("images/End.png"), cal(164), cal(89)));
		end.addMouseListener(this);
		end.setHorizontalTextPosition(SwingConstants.CENTER);
		end.setLayout(null);
		end.add(e);
		end.setOpaque(false);
		end.setContentAreaFilled(false);
		end.setBorderPainted(false);
		e.setBounds(cal(40), cal(28), cal(133), cal(67));
		end.setPreferredSize(new Dimension(cal(213), cal(127)));
		end.setBounds(cal(-20),cal(117),cal(213), cal(127));
		view.getW().add(end);
		
	}

	public void selectHand(JButton b, Card c) {
		System.out.println("Selected");
		if (c instanceof Minion) {

			System.out.println(" Minion Selected");
			b.removeAll();
			b.setPreferredSize(new Dimension(cal720(100), cal720(140)));
			b.setIcon(resizeIcon((ImageIcon) b.getIcon(), cal720(100), cal720(140)));
			drawAttack(b, c);
			drawMana(b, c);
			drawHP(b, c);

		} else if (c instanceof Spell) {

			System.out.println("Spell Selected");
			b.removeAll();
			b.setPreferredSize(new Dimension(cal720(100), cal720(140)));
			b.setIcon(resizeIcon((ImageIcon) b.getIcon(), cal720(100), cal720(140)));
			drawMana(b, c);
		}

	}

	public void selectField(JButton b, Minion c) {

		b.removeAll();
		b.setPreferredSize(new Dimension(cal720(100), cal720(140)));
		b.setIcon(resizeIcon((ImageIcon) b.getIcon(), cal720(100), cal720(140)));
		drawAttack(b, c);
		drawMana(b, c);
		drawHP(b, c);

	}

	public void update() {
		System.out.println("Update");
		drawHand();
		drawField();
		drawHeros();
		onGameOver();
	}

	@Override
	public void onGameOver() {
		
		if (model.getCurrentHero().getCurrentHP() == 0 || model.getOpponent().getCurrentHP() == 0) {
			view.dispose();
			new gameover();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) (e.getSource());
		ex.setText(null);
		
		//String soundName = "C:/Users/Asus/Desktop/yy.wav";    
		//AudioInputStream audioInputStream;
		//try {
			//audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			//Clip clip = AudioSystem.getClip();
			//clip.open(audioInputStream);
			//clip.start();
		//} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
			
			//e2.printStackTrace();
			//System.out.print("Cannot");
		//}
		
		/*
		 * ImageIcon icon = new ImageIcon("images/" + "select" + ".png");
		 * 
		 * ImageIcon icon1 = (ImageIcon) b.getIcon(); b.setIcon(resizeIcon(icon,
		 * cal720(119), cal720(160))); b.setIcon(resizeIcon(icon1, cal720(117),
		 * cal720(155)));
		 */

		if (b.getActionCommand().equals("end")) {

			try {
				model.endTurn();
			} catch (FullHandException | CloneNotSupportedException e1) {

				ex.setText("The Hand is Full");

			}
			attacker = null;
			heroPower = false;

			update();
			if(model.getCurrentHero().getHand().size()==10 && ex.getText().equals("The Hand is Full"))
			{
				Card c = model.getCurrentHero().getDeck().get(0);
				JButton d = new JButton();
				createCardHand(d, c);
				//d.setPreferredSize(new Dimension(cal(100), cal(120)));
				d.setBounds(cal(1060),cal(70),cal(180), cal(252));
				//d.setOpaque(false);
				
				view.getcH().add(d);
				
			}
		}
		if (b.getActionCommand().equals("Use Power")) {
			
			if (model.getCurrentHero() instanceof Hunter || model.getCurrentHero() instanceof Paladin
					|| model.getCurrentHero() instanceof Warlock) {
				try {
					model.getCurrentHero().useHeroPower();
				} catch (NotEnoughManaException | NotYourTurnException | CloneNotSupportedException e1) {
					
					e1.printStackTrace();
					ex.setText("Not Enough Mana");

				} catch (FullFieldException e1) {
					ex.setText("The Field is Full");
				} catch (FullHandException e1) {
					ex.setText("The Hand is Full");
				} catch (HeroPowerAlreadyUsedException e1) {
					ex.setText("Already Used");
				}
			} else if (model.getCurrentHero() instanceof Mage || model.getCurrentHero() instanceof Priest) {
				heroPower = true;
				System.out.println(heroPower);
			}
			update();

		}
		for (int i = 0; i < model.getCurrentHero().getHand().size(); i++) {

			if (b.getIcon().equals(((AbstractButton) view.getH().getComponent(i)).getIcon())) {

				if (model.getCurrentHero().getHand().get(i) instanceof Minion) {
					try {
						model.getCurrentHero().playMinion((Minion) model.getCurrentHero().getHand().get(i));
					} catch (NotYourTurnException | FullFieldException e1) {
						ex.setText("The Field is Full");
					} catch (NotEnoughManaException e1) {
						ex.setText("Not Enough Mana");
					}

				} else {

					if (model.getCurrentHero().getHand().get(i) instanceof FieldSpell) {
						try {
							model.getCurrentHero().castSpell((FieldSpell) model.getCurrentHero().getHand().get(i));
						} catch (NotYourTurnException | NotEnoughManaException e1) {
						
							e1.printStackTrace();
							ex.setText("not enough mana");
						}

					} else if (model.getCurrentHero().getHand().get(i) instanceof MinionTargetSpell) {
						attacker = model.getCurrentHero().getHand().get(i);

					} else if (model.getCurrentHero().getHand().get(i) instanceof HeroTargetSpell) {
						attacker = model.getCurrentHero().getHand().get(i);

					}

					else if (model.getCurrentHero().getHand().get(i) instanceof AOESpell) {
						try {
							model.getCurrentHero().castSpell((AOESpell) model.getCurrentHero().getHand().get(i),
									model.getOpponent().getField());
						} catch (NotYourTurnException | NotEnoughManaException e1) {
							
							e1.printStackTrace();
							ex.setText("not enough mana");
						}

					}
				}
				System.out.println("GSuahuidiahaihsdgqaikxjs");
				update();
				System.out.println(b.getIcon().getIconHeight());
				// selectHand(b,model.getCurrentHero().getHand().get(i));
				b.setIcon(resizeIcon((ImageIcon) b.getIcon(), cal720(100), cal720(140)));
				System.out.println(b.getIcon().getIconHeight());
				view.revalidate();
				view.repaint();
				/*
				 * try { Thread.sleep(3000); } catch(InterruptedException ex) {
				 * System.out.println("bbbbbbbbbbbbb"); }
				 */
			}
		}

		if (attacker == null & !heroPower) {

			for (int i = 0; i < model.getCurrentHero().getField().size(); i++) {

				if (b.getIcon().equals(((AbstractButton) view.getcF().getComponent(i)).getIcon())) {
					attacker = (Minion) model.getCurrentHero().getField().get(i);
					this.drawField();
					//selectField(b, (Minion) attacker);

				}
			}
		} else {
			if (attacker instanceof Minion
					&& (b.getActionCommand().equals("Current Hero") || b.getActionCommand().equals("Opponent Hero"))) {

				if (b.getActionCommand().equals("Current Hero")) {
					try {
						model.getCurrentHero().attackWithMinion((Minion) attacker, model.getCurrentHero());
					} catch (CannotAttackException | NotYourTurnException e1) {
						
						e1.printStackTrace();
						ex.setText("can not attack");
					} catch (InvalidTargetException e1) {
						ex.setText("Invaild target");
					} catch (TauntBypassException e1) {
						ex.setText("taunt in the way");
					} catch (NotSummonedException e1) {
						ex.setText("must be in the field");
					}
					attacker = null;
					update();
					System.out.println(model.getCurrentHero().getCurrentHP());
				} else if (b.getActionCommand().equals("Opponent Hero")) {

					try {
						model.getCurrentHero().attackWithMinion((Minion) attacker, model.getOpponent());
					} catch (CannotAttackException | NotYourTurnException e1) {
						
						e1.printStackTrace();
						ex.setText("can not attack");
					} catch (InvalidTargetException e1) {
						ex.setText("Invaild target");
					} catch (TauntBypassException e1) {
						ex.setText("taunt in the way");
					} catch (NotSummonedException e1) {
						ex.setText("must be in the field");
					}
					attacker = null;
					update();
				}

			} else if (attacker instanceof HeroTargetSpell
					&& (b.getActionCommand().equals("Current Hero") || b.getActionCommand().equals("Opponent Hero"))) {

				System.out.println("bom he5a");
				if (b.getActionCommand().equals("Current Hero")) {
					try {
						model.getCurrentHero().castSpell((HeroTargetSpell) attacker, model.getCurrentHero());
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						
						ex.setText("not enough mana");
					}
					attacker = null;
					update();
				} else if (b.getActionCommand().equals("Opponent Hero")) {
					System.out.println("bom bema");

					try {
						model.getCurrentHero().castSpell((HeroTargetSpell) attacker, model.getOpponent());
					} catch (NotYourTurnException e1) {
						e1.printStackTrace();
					} catch (NotEnoughManaException e1) {
						ex.setText("not enough mana");
					}
					attacker = null;
					update();

				}

			} else if (heroPower
					&& (b.getActionCommand().equals("Current Hero") || b.getActionCommand().equals("Opponent Hero"))) {
				System.out.println("Hero Power");
				if (b.getActionCommand().equals("Current Hero")) {
					if (model.getCurrentHero() instanceof Mage) {
						try {
							((Mage) model.getCurrentHero()).useHeroPower(model.getCurrentHero());
						} catch (NotEnoughManaException | NotYourTurnException | CloneNotSupportedException e1) {
						
							e1.printStackTrace();
							ex.setText("not enough mana");
						} catch (FullFieldException e1) {
							ex.setText("the field is full");
						} catch (FullHandException e1) {
							ex.setText("the hand is full");
						} catch (HeroPowerAlreadyUsedException e1) {
							ex.setText("already used");
						}
						heroPower = false;
						update();
					} else if (model.getCurrentHero() instanceof Priest) {
						try {
							((Priest) model.getCurrentHero()).useHeroPower(model.getCurrentHero());
						} catch (NotEnoughManaException | NotYourTurnException | CloneNotSupportedException e1) {
						
							e1.printStackTrace();
							ex.setText("not enough mana");
						} catch (FullFieldException e1) {
							ex.setText("the field is full");
						} catch (FullHandException e1) {
							ex.setText("the hand is full");
						} catch (HeroPowerAlreadyUsedException e1) {
							ex.setText("already used");
						}
						heroPower = false;
						update();
					}
				} else if (b.getActionCommand().equals("Opponent Hero")) {
					if (model.getCurrentHero() instanceof Mage) {
						try {
							((Mage) model.getCurrentHero()).useHeroPower(model.getOpponent());
						} catch (NotEnoughManaException | NotYourTurnException | CloneNotSupportedException e1) {
							
							e1.printStackTrace();
							ex.setText("not enough mana");
						} catch (FullFieldException e1) {
							ex.setText("the field is full");
						} catch (FullHandException e1) {
							ex.setText("the hand is full");
						} catch (HeroPowerAlreadyUsedException e1) {
							ex.setText("already used");
						}
						heroPower = false;
						update();
					} else if (model.getCurrentHero() instanceof Priest) {
						try {
							((Priest) model.getCurrentHero()).useHeroPower(model.getOpponent());
						} catch (NotEnoughManaException | NotYourTurnException | CloneNotSupportedException e1) {
							
							e1.printStackTrace();
							ex.setText("not enough mana");
						} catch (FullFieldException e1) {
							ex.setText("the field is full");
						} catch (FullHandException e1) {
							ex.setText("the hand is full");
						} catch (HeroPowerAlreadyUsedException e1) {
							ex.setText("already used");
						}
						heroPower = false;
						update();
					}
				}

			}

			for (int i = 0; i < model.getCurrentHero().getField().size(); i++) {
				if (attacker instanceof MinionTargetSpell
						&& b.getIcon().equals(((AbstractButton) view.getcF().getComponent(i)).getIcon())) {

					try {
						model.getCurrentHero().castSpell((MinionTargetSpell) attacker,
								model.getCurrentHero().getField().get(i));
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						
						e1.printStackTrace();
						ex.setText("not enough mana");
					} catch (InvalidTargetException e1) {
						ex.setText("invaild target");
					}
					attacker = null;
					update();

				} else if (heroPower && b.getIcon().equals(((AbstractButton) view.getcF().getComponent(i)).getIcon())) {
					if (model.getCurrentHero() instanceof Mage) {
						try {
							((Mage) model.getCurrentHero()).useHeroPower(model.getCurrentHero().getField().get(i));
						} catch (NotEnoughManaException | NotYourTurnException | CloneNotSupportedException e1) {
							
							e1.printStackTrace();
							ex.setText("not enough mana");
						} catch (FullFieldException e1) {
							ex.setText("the field is full");
						} catch (FullHandException e1) {
							ex.setText("the hand is full");
						} catch (HeroPowerAlreadyUsedException e1) {
							ex.setText("already used");
						}
						heroPower = false;
						update();
					} else if (model.getCurrentHero() instanceof Priest) {
						try {
							((Priest) model.getCurrentHero()).useHeroPower(model.getCurrentHero().getField().get(i));
						} catch (NotEnoughManaException | NotYourTurnException | CloneNotSupportedException e1) {
							
							e1.printStackTrace();
							ex.setText("not enough mana");
						} catch (FullFieldException e1) {
							ex.setText("the field is full");
						} catch (FullHandException e1) {
							ex.setText("the hand is full");
						} catch (HeroPowerAlreadyUsedException e1) {
							ex.setText("already used");
						}
						heroPower = false;
						update();
					}
				}

			}

			for (int i = 0; i < model.getOpponent().getField().size(); i++) {
				if (b.getIcon().equals(((AbstractButton) view.getoF().getComponent(i)).getIcon())) {

					if (attacker instanceof Minion) {
						try {
							model.getCurrentHero().attackWithMinion((Minion) attacker,
									(Minion) model.getOpponent().getField().get(i));
						} catch (CannotAttackException | NotYourTurnException e1) {
							
							e1.printStackTrace();
							ex.setText("can not attack");
						} catch (InvalidTargetException e1) {
							ex.setText("Invaild target");
						} catch (TauntBypassException e1) {
							ex.setText("taunt in the way");
						} catch (NotSummonedException e1) {
							ex.setText("must be in the field");
						}
						update();
						attacker = null;

					} else if (attacker instanceof MinionTargetSpell) {
						try {
							model.getCurrentHero().castSpell((MinionTargetSpell) attacker,
									model.getOpponent().getField().get(i));
						} catch (NotYourTurnException | NotEnoughManaException e1) {
							
							e1.printStackTrace();
							ex.setText("not enough mana");
						} catch (InvalidTargetException e1) {
							ex.setText("invaild target");
						}
						update();
						attacker = null;

					} else if (attacker instanceof LeechingSpell) {
						try {
							model.getCurrentHero().castSpell((LeechingSpell) attacker,
									model.getOpponent().getField().get(i));
						} catch (NotYourTurnException | NotEnoughManaException e1) {
							
							e1.printStackTrace();
							ex.setText("not enough mana");
						}
						update();
						attacker = null;
					} else if (heroPower) {
						if (model.getCurrentHero() instanceof Mage) {
							try {
								((Mage) model.getCurrentHero()).useHeroPower(model.getOpponent().getField().get(i));
							} catch (NotEnoughManaException | NotYourTurnException | CloneNotSupportedException e1) {
								
								e1.printStackTrace();
								ex.setText("not enough mana");
							} catch (FullFieldException e1) {
								ex.setText("the field is full");
							} catch (FullHandException e1) {
								ex.setText("the hand is full");
							} catch (HeroPowerAlreadyUsedException e1) {
								ex.setText("already used");
							}
							update();
							heroPower = false;
						} else if (model.getCurrentHero() instanceof Priest) {
							try {
								((Priest) model.getCurrentHero()).useHeroPower(model.getOpponent().getField().get(i));
							} catch (NotEnoughManaException | NotYourTurnException | CloneNotSupportedException e1) {
								
								e1.printStackTrace();
								ex.setText("not enough mana");
							} catch (FullFieldException e1) {
								ex.setText("the field is full");
							} catch (FullHandException e1) {
								ex.setText("the hand is full");
							} catch (HeroPowerAlreadyUsedException e1) {
								ex.setText("already used");
							}
							update();
							heroPower = false;
						}

					}

				}

			}
		}
	}

	public static void main(String[] args) throws IOException, CloneNotSupportedException, FullHandException,
			NotYourTurnException, NotEnoughManaException, FullFieldException, FontFormatException {
		new Controller2();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		

	}

	@Override
	public void mousePressed(MouseEvent e) {
		

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton b = (JButton) (e.getSource());

		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	}

	@Override
	public void mouseExited(MouseEvent e) {
		

	}

}