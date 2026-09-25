import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Tetramino {
	private final int BORDA = 3; // espessura do relevo 3D

	private int[][] formato;
	private int coluna_X;
	private int linha_Y;
	private Color cor;
	private static final int[][] formatoO = {
		{1, 1},
		{1, 1}
	};
	private static final int[][] formatoI = {
		{0, 0, 0, 0},
		{1, 1, 1, 1},
		{0, 0, 0, 0},
		{0, 0, 0, 0}
	};
	private static final int[][] formatoT = {
		{1, 1, 1},
		{0, 1, 0},
		{0, 0, 0}
	};
	private static final int[][] formatoJ = {
		{0, 1, 0},
		{0, 1, 0},
		{1, 1, 0}
	};
	private static final int[][] formatoL = {
		{0, 1, 0},
		{0, 1, 0},
		{0, 1, 1}
	};
	private static final int[][] formatoS = {
		{0, 1, 1},
		{1, 1, 0},
		{0, 0, 0}
	};
	private static final int[][] formatoZ = {
		{1, 1, 0},
		{0, 1, 1},
		{0, 0, 0}
	};


	public Tetramino(int[][] formato, int coluna_X, int linha_Y, Color cor) {
		this.formato = formato;
		this.coluna_X = coluna_X;
		this.linha_Y = linha_Y;
		this.cor = cor;
	}

	public Tetramino(int[][] formato, Color cor) {
		this(formato, (10 - formato[0].length) / 2, 0, cor);				//A sobrecarga de construtor chama o principal passando somente formato e cor
	}

	public Tetramino() {
		int[][] forma;
		Color c;
		Random random = new Random();
		int formatoNum = random.nextInt(7);
		switch (formatoNum) {
			case 1:
				forma = formatoT;
				break;
			case 2:
				forma = formatoO;
				break;
			case 3:
				forma = formatoJ;
				break;
			case 4:
				forma = formatoL;
				break;
			case 5:
				forma = formatoS;
				break;
			case 6:
				forma = formatoZ;
				break;
			default:
				forma = formatoI;
				break;
		}
		int corNum = random.nextInt(11);
		switch (corNum) {
			case 1:
				c = Color.WHITE;
				break;
			case 2:
				c = Color.RED;
				break;
			case 3:
				c = Color.GREEN;
				break;
			case 4:
				c = Color.BLUE;
				break;
			case 5:
				c = Color.YELLOW;
				break;
			case 6:
				c = Color.CYAN;
				break;
			case 7:
				c = Color.MAGENTA;
				break;
			case 8:
				c = Color.ORANGE;
				break;
			case 9:
				c = Color.PINK;
				break;
			default:
				c = Color.GRAY;
				break;
		}
		this(forma, c);				//A sobrecarga de construtor chama o principal passando somente formato
	}

	public int[][] getFormato() {return formato;}

	public int getColunaX() {return coluna_X;}

	public int getLinhaY() {return linha_Y;}

	public Color getColor() {return cor;}

	private boolean podeDescer() {
		for (int i = 0; i < formato.length; i++) {
			for (int j = 0; j < formato[i].length; j++) {
				if (formato[i][j] != 0) {
					if (linha_Y + i + 1 > 19) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean podeEsq() {
		for (int i = 0; i < formato.length; i++) {
			for (int j = 0; j < formato[i].length; j++) {
				if (formato[i][j] != 0) {
					if (coluna_X + j - 1 < 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
		
	private boolean podeDir() {
		for (int i = 0; i < formato.length; i++) {
			for (int j = 0; j < formato[i].length; j++) {
				if (formato[i][j] != 0) {
					if (coluna_X + j + 1 > 9) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean moverBaixo() {
		if (podeDescer()) {
			this.linha_Y++;
			return true;
		}
		else {
			return false;
		}
	}

	public void moverEsq() {
		if (podeEsq())
			this.coluna_X -= 1;
	}

	public void moverDir() {
		if (podeDir())
			this.coluna_X++;
	}

	public void bloco3D(Graphics g, int x, int y, Color base, int tam_cel) {
		Color claro = base.brighter().brighter(); // luz
		Color escuro = base.darker().darker();    // sombra


		// 1) Preenche o fundo com a cor base
		g.setColor(base);
		g.fillRect(x, y, tam_cel, tam_cel);

		// 2) Topo (mais claro)
		g.setColor(claro);
		g.fillPolygon(
			new int[]{x, x + BORDA, x + tam_cel - BORDA, x + tam_cel},
			new int[]{y, y + BORDA, y + BORDA, y},
			4
		);

		// 3) Lado esquerdo (mais claro)
		g.fillPolygon(
			new int[]{x, x + BORDA, x + BORDA, x},
			new int[]{y, y + BORDA, y + tam_cel - BORDA, y + tam_cel},
			4
		);

		// 4) Base (mais escuro)
		g.setColor(escuro);
		g.fillPolygon(
			new int[]{x, x + BORDA, x + tam_cel - BORDA, x + tam_cel},
			new int[]{y + tam_cel, y + tam_cel - BORDA, y + tam_cel - BORDA, y + tam_cel},
			4
		);

		// 5) Lado direito (mais escuro)
		g.fillPolygon(
			new int[]{x + tam_cel, x + tam_cel - BORDA, x + tam_cel - BORDA, x + tam_cel},
			new int[]{y, y + BORDA, y + tam_cel - BORDA, y + tam_cel},
			4
		);

		// 6) Brilho interno (opcional - dá um toque de vidro)
		g.setColor(new Color(255, 255, 255, 60));
		g.fillRect(x + BORDA, y + BORDA, tam_cel - 2 * BORDA, 3);

		// 7) Contorno sutil
		g.setColor(Color.BLACK);
		g.drawRect(x, y, tam_cel, tam_cel);
	}

	public void desenhar(Graphics g, int tamCel) {
		for (int i = 0; i < formato.length; i++) {
			for (int j = 0; j < formato[i].length; j++) {
				if (formato[i][j] != 0) {
					bloco3D(g, (coluna_X + j) * tamCel, (linha_Y + i) * tamCel, cor, tamCel);
				}
			}
		}
	}

	/*public static Color getColorTetraminos(int tipo) {
		switch (tipo) {
			case 1: return Color.MAGENTA;
			case 2: return Color.BLUE;
			case 3: return Color.BLACK;
			case 4: return Color.RED;
			case 5: return Color.GREEN;
			case 6: return Color.WHITE;
			default: return null;
		}
	}*/
}
