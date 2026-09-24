import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Painel extends JPanel implements KeyListener {
	private final int TAM_CEL = 30;
	private final int ALTURA_TELA = 600;
	private final int LARGURA_TELA = 300;

	private Tetramino bloco = new Tetramino();
	Timer timer = new Timer(500, e -> {
		bloco.moverBaixo();
		repaint();
	});

	public Painel() {
		this.setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
		setFocusable(true);
		addKeyListener(this);
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		//Desenho da grade do tetris em cinza claro
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i <= 10; i++) {
			g.drawLine(i * TAM_CEL, 0, i * TAM_CEL, ALTURA_TELA);
		}
		for (int i = 0; i <= 20; i++) {
			g.drawLine(0, i * TAM_CEL, LARGURA_TELA, i * TAM_CEL);
		}
		//Desenho Tetramino
		bloco.desenhar(g, TAM_CEL);
	}

	public void keyTyped(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			bloco.moverBaixo();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			bloco.moverDir();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			bloco.moverEsq();
		}
		repaint();
	}
}