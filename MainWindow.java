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
				
				fr.setSize(960,1020);
				fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fr.add(grid);
				fr.setVisible(true);

				//generate maze
				Maze maze = new Maze(rows,cols);
				maze.initDual(grid);
				
				maze.generateMaze(grid);
				
				/*Other maze generation algos:
				  maze.dfsi(grid,maze.origin,null);
				  maze.dfsgenerator(grid);*/
				
				//Solver.bfs(grid, maze,maze.origin,maze.end);
				
			}
		});

	}

}
