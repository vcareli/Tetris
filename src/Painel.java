import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Painel extends JPanel implements KeyListener {
	private final int TAM_CEL = 30;
	private final int ALTURA_TELA = 600;
	private final int LARGURA_TELA = 300;
	private int tempo = 500;
	private int score = 0;
	private boolean gameOver = false;
	int[][] piece;
	int[][] matrix = new int[ALTURA_TELA / TAM_CEL][LARGURA_TELA / TAM_CEL];

	private Tetramino bloco = new Tetramino();
	Timer timer = new Timer(tempo, e -> {
		if(bloco.moverBaixo(matrix)) {
			repaint();
		} else {
			carimbar();
			bloco = new Tetramino();
			repaint();
		}
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

		//Desenho dos blocos carimbados
		for (int i = 0; i < matrix.length; i++) {
    		for (int j = 0; j < matrix[i].length; j++) {
        		if (matrix[i][j] != 0) {
            		bloco.bloco3D(g, j * TAM_CEL, i * TAM_CEL, Color.DARK_GRAY, TAM_CEL);
        		}
    		}
		}

		//Desenho da pontuacao
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString("Score: " + score, 20, 30);

		//Desenho do G. O.
		if (gameOver) {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			// Como medir a largura do texto "GAME OVER":
			FontMetrics fm = g.getFontMetrics();
    		int larguraTexto = fm.stringWidth("GAME OVER");
			int xCentro = (LARGURA_TELA - larguraTexto) / 2;
			int yCentro = ALTURA_TELA / 2;
			g.drawString("GAME OVER", xCentro, yCentro);
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			bloco.moverBaixo(matrix);
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			bloco.rotation90(matrix);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			bloco.moverDir(matrix);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			bloco.moverEsq(matrix);
		} else if (e.getKeyCode() == KeyEvent.VK_R) {
			restart();
		}
		repaint();
	}

	private void apagarLinha(int lin) {
		for (int k = lin; k > 0; k--) {
			matrix[k] = matrix[k -1];
			score += 20;
		}
		matrix[0] = new int[10];
		tempo -= 5;
		if (tempo >= 100) {
			timer.setDelay(tempo);
		} else {timer.setDelay(100);}
	}

	private void verificarLinhas() {
		for (int i = 0; i < matrix.length; i++) {
			int lin = 0;
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != 0) {
					lin++;
				}
			}
			if (lin == 10) {
				apagarLinha(i);
			}
		}
	}

	private void carimbar() {
		piece = bloco.getFormato();
		int lin = bloco.getLinhaY();
		int col = bloco.getColunaX();
		for (int i = 0; i < piece.length; i++) {
			for (int j = 0; j < piece[i].length; j++) {
				if (piece[i][j] != 0) {
					matrix[lin + i][col + j] = piece[i][j];
					if (lin + i == 0) {
						timer.stop();
						gameOver = true;
					}
				}
			}
		}
		verificarLinhas();
	}

	private void restart() {
			gameOver = false;
			score = 0;
			tempo = 500;
			for (int i = 0; i < matrix.length; i++) {
				matrix[i] = new int[10];
			}
			timer.setDelay(tempo);
			timer.start();

	}
}