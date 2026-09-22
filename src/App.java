import javax.swing.JFrame;

public class App {
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("Deby Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Painel());
		frame.pack();			// Ajusta o tamanho de acordo com as configuracoes do Painel
		frame.setVisible(true);
	}
}
