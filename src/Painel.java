import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;

public class Painel extends JPanel {
	private final int TAMANHO_CELULA = 30;
	private final int ALTURA_TELA = 600;
	private final int LARGURA_TELA = 300;

	public Painel() {
		this.setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i <= 10; i++) {
			g.drawLine(i * TAMANHO_CELULA, 0, i * TAMANHO_CELULA, ALTURA_TELA);
		}
		for (int i = 0; i <= 20; i++) {
			g.drawLine(0, i * TAMANHO_CELULA, LARGURA_TELA, i * TAMANHO_CELULA);
		}
	}
}
