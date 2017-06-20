package uk.ac.cam.dsjw2.MazeAI;


import javax.swing.JFrame;

public class MainWindow {

	public static void main(String[] args) {
		int rows = 30;
		int cols = 30;
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				Grid grid = new Grid(rows,cols);
				JFrame fr = new JFrame("The Maze");
				
				fr.setSize(1100,1100);
				fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fr.add(grid);
				fr.setVisible(true);
			}
		});

	}

}
