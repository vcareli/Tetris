import java.awt.Color;
import java.awt.Graphics;

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
		this(formatoO, Color.BLUE);				//A sobrecarga de construtor chama o principal passando somente formato
	}

	public int[][] getFormato() {return formato;}

	public int getColunaX() {return coluna_X;}

	public int getLinhaY() {return linha_Y;}

	public Color getColor() {return cor;}

	public void moverBaixo() {
		this.linha_Y++;
	}

	public void moverEsq() {
		this.coluna_X -= 1;
	}

	public void moverDir() {
		this.coluna_X++;
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
}
