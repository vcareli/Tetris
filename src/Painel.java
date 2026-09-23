import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;

public class Painel extends JPanel {
	private final int TAM_CEL = 30;
	private final int ALTURA_TELA = 600;
	private final int LARGURA_TELA = 300;

	private int[][] matrix;

	public Painel() {
		this.setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
		this.matrix = new int[ALTURA_TELA / TAM_CEL][LARGURA_TELA / TAM_CEL];
		matrix[19][4] = 1;
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
		//g.setColor(Color.MAGENTA);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (matrix[i][j] != 0) {
					if (matrix[i][j] == 1) {				//peca I
						g.setColor(Color.MAGENTA);
					} else if (matrix[i][j] == 2) {			//peca II
						g.setColor(Color.BLUE);
					} else if (matrix[i][j] == 3) {			//peca III
						g.setColor(Color.BLACK);
					} else if (matrix[i][j] == 4) {			//peca IV
						g.setColor(Color.RED);
					}
					g.fillRect(j * TAM_CEL, i * TAM_CEL, TAM_CEL, TAM_CEL);
				}
			}
		}
	}
}
