import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
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

		//Desenho da SCORE
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("Score: " + score, 20, 30);
		
		//Desenho do G. O. 
		/*if (gameOver) {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			// Como medir a largura do texto "GAME OVER":
			FontMetrics fm = g.getFontMetrics();
    		int larguraTexto = fm.stringWidth("GAME OVER");
			int xCentro = (LARGURA_TELA - larguraTexto) / 2;
			int yCentro = ALTURA_TELA / 2;
			g.drawString("GAME OVER", xCentro, yCentro);
		}*/
		// Desenho do Game Over - Feito com ajuda
		if (gameOver) {
		    Graphics2D g2 = (Graphics2D) g.create();
			// Suavização
			g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
			);
			// Escurece o tabuleiro
			g2.setColor(new Color(0, 0, 0, 180));
			g2.fillRect(0, 0, LARGURA_TELA, ALTURA_TELA);
			// Dimensões do painel
			int painelLargura = 380;
			int painelAltura = 200;
			int painelX = (LARGURA_TELA - painelLargura) / 2;
			int painelY = (ALTURA_TELA - painelAltura) / 2;
			// Sombra do painel
			g2.setColor(new Color(0, 0, 0, 200));
			g2.fillRoundRect(painelX + 8, painelY + 8,painelLargura, painelAltura, 20, 20);
			// Fundo do painel
			g2.setColor(new Color(35, 35, 35));
			g2.fillRoundRect(painelX, painelY, painelLargura, painelAltura, 20, 20);
			// Borda
			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(painelX, painelY, painelLargura, painelAltura, 20, 20);
			// -------------------------
			// GAME OVER
			// -------------------------
			String texto = "GAME OVER";
			g2.setFont(new Font("Arial", Font.BOLD, 42));
			FontMetrics fm = g2.getFontMetrics();
			int textoX = painelX + (painelLargura - fm.stringWidth(texto)) / 2;
			int textoY = painelY + 60;
			// Sombra do texto
			g2.setColor(Color.BLACK);
			g2.drawString(texto, textoX + 3, textoY + 3);
			// Texto
			g2.setColor(Color.RED);
			g2.drawString(texto, textoX, textoY);
			// -------------------------
			// SCORE
			// -------------------------
			String scoreTexto = "Score: " + score;
			g2.setFont(new Font("Arial", Font.BOLD, 22));
			fm = g2.getFontMetrics();
			int scoreX = painelX + (painelLargura - fm.stringWidth(scoreTexto)) / 2;
			g2.setColor(Color.WHITE);
			g2.drawString(scoreTexto, scoreX, painelY + 105);
			// -------------------------
			// RECOMEÇAR
			// -------------------------
			String restart = "Pressione ENTER para jogar novamente";
			g2.setFont(new Font("Arial", Font.PLAIN, 16));
			fm = g2.getFontMetrics();
			int restartX = painelX + (painelLargura - fm.stringWidth(restart)) / 2;
			g2.setColor(Color.LIGHT_GRAY);
			g2.drawString(restart, restartX, painelY + 155);
			g2.dispose();
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
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOver == true) {
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
			timer.setDelay(tempo);
			for (int i = 0; i < matrix.length; i++) {
				matrix[i] = new int[10];
			}
			bloco = new Tetramino();
			timer.start();

	}
}