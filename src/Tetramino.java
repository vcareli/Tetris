import java.awt.Color;
import java.awt.Graphics;

public class Tetramino {
	private final int BORDA = 3; // espessura do relevo 3D

	private int[][] formato;
	private int coluna_X;
	private int linha_Y;
	private Color cor;

	public Tetramino(int[][] formato, int coluna_X, int linha_Y, Color cor) {
		this.formato = formato;
		this.coluna_X = coluna_X;
		this.linha_Y = linha_Y;
		this.cor = cor;
	}

	public Tetramino(int[][] formato, Color cor) {
		this(formato, 4, 0, cor);				//A sobrecarga de construtor chama o principal passando somente formato e cor
	}


	public static void bloco3D(Graphics g, int x, int y, Color base, int tam_cel) {
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
}
