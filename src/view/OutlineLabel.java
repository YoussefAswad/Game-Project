package view;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class OutlineLabel extends JLabel {

    private Color outlineColor = Color.WHITE;
    private boolean isPaintingOutline = false;
    private boolean forceTransparent = false;
    private int b = 2;

    public OutlineLabel() {
        super();
    }

    public OutlineLabel(String text) {
        super(text);
    }

    public OutlineLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
        this.invalidate();
    }

    @Override
    public Color getForeground() {
        if ( isPaintingOutline ) {
            return outlineColor;
        } else {
            return super.getForeground();
        }
    }

    @Override
    public boolean isOpaque() {
        if ( forceTransparent ) {
            return false;
        } else {
            return super.isOpaque();
        }
    }

    @Override
    public void paint(Graphics g) {

        String text = getText();
        if ( text == null || text.length() == 0 ) {
            super.paint(g);
            return;
        }

        // 1 2 3
        // 8 9 4
        // 7 6 5

        if ( isOpaque() )
            super.paint(g);

        forceTransparent = true;
        isPaintingOutline = true;
        g.translate(-b, -b); super.paint(g); // 1 
        g.translate( b,  0); super.paint(g); // 2 
        g.translate( b,  0); super.paint(g); // 3 
        g.translate( 0,  b); super.paint(g); // 4
        g.translate( 0,  b); super.paint(g); // 5
        g.translate(-b,  0); super.paint(g); // 6
        g.translate(-b,  0); super.paint(g); // 7
        g.translate( 0, -b); super.paint(g); // 8
        g.translate( b,  0); // 9
        isPaintingOutline = false;

        super.paint(g);
        forceTransparent = false;

    }

	public void setB(int b) {
		this.b = b;
	}

	/*
	 * public static void main(String[] args) { JFrame w = new JFrame();
	 * w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); OutlineLabel label = new
	 * OutlineLabel("Test", OutlineLabel.CENTER); try { GraphicsEnvironment ge =
	 * GraphicsEnvironment.getLocalGraphicsEnvironment();
	 * ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new
	 * File("C:\\Users\\Asus\\eclipse-workspace\\GG\\fonts\\belwe.TTF"))); } catch
	 * (Exception e) { //Handle exception } label.setFont(new
	 * Font("Belwe Medium BT", Font.PLAIN, 50)); label.setOpaque(true);
	 * label.setForeground(Color.white); label.setOutlineColor(Color.black);
	 * w.setContentPane(new JPanel(new BorderLayout())); w.add(label,
	 * BorderLayout.CENTER); w.pack(); w.setVisible(true); }
	 */
}
