package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
@SuppressWarnings("serial")
public class gameover extends JFrame {
	private JLabel f;
public gameover() {
//	f=new JLabel();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int wi = (int) screenSize.getWidth();
	int hi = (int) screenSize.getHeight();
//	
//	ImageIcon i = (ImageIcon) resizeIcon(new ImageIcon("images/gameover.png"), 100, 100);
//	f.setIcon(resizeIcon(i, calW(120), calH(168)));
//	//f.setBounds(0, 0,wi,hi);
//	this.add(f,BorderLayout.CENTER);
//	this.setBounds(0, 0,wi,hi);
//	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//	this.setVisible(true);
//	this.revalidate();
//	this.repaint();
//	
	f=new JLabel();
	ImageIcon i = (ImageIcon) new ImageIcon("images/gameover.png");
	setExtendedState(JFrame.MAXIMIZED_BOTH);
	f.setIcon(resizeIcon(i, 1920, 1080));
	this.add(f,BorderLayout.CENTER);
	this.setBounds(0, 0,wi,hi);
	this.setVisible(true);
	this.revalidate();
	this.repaint();
	
}
private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
	Image img = icon.getImage();
	Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
	return new ImageIcon(resizedImage);
}
public static int calW(int w) {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int wi = (int) screenSize.getWidth();
	return wi * w / 1280;
}

public static int calH(int h) {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int hi = (int) screenSize.getHeight();
	return hi * h / 720;
}
public static void main(String[] args) {
	new gameover();
}
}
