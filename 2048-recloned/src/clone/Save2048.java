package clone;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Save2048 {
	private int[][] model = new int[4][4]; //these are 4 columns from left to right
	private boolean lost = false;	
	Show2048 view = new Show2048(this);
	private int score = 0;

	public Save2048() {
		view.setListener(
				new MovementListener() { //UP
					@Override
					protected int[][] getLines() {
						return model;
					}

					@Override
					protected int[][] exportLines(int[][] lines) {
						return lines;
					}
				},
				new MovementListener() { //DOWN
					@Override
					protected int[][] getLines() {
						int[][] lines = new int[4][4];

						for(int i=0; i<4; i++) {
							lines[i][0] = model[i][3];
							lines[i][1] = model[i][2];
							lines[i][2] = model[i][1];
							lines[i][3] = model[i][0];
						}
						
						return lines;
					}

					@Override
					protected int[][] exportLines(int[][] lines) {
						int[][] newLines = new int[4][4];
						
						for(int i=0; i<4; i++) {
							newLines[i][0] = lines[i][3];
							newLines[i][1] = lines[i][2];
							newLines[i][2] = lines[i][1];
							newLines[i][3] = lines[i][0];
						}
						
						return newLines;
					} 
				},
				new MovementListener() { //LEFT
					@Override
					protected int[][] getLines() {
						int[][] lines = new int[4][4];
						
						for(int i1 = 0; i1<4; i1++) {
							for(int i2 = 0; i2<4; i2++) {
								lines[i1][i2] = model[i2][i1];
							}
						}
						return lines;
					}

					@Override
					protected int[][] exportLines(int[][] lines) {
						int[][] newLines = new int[4][4];
						
						for(int i1 = 0; i1<4; i1++) {
							for(int i2 = 0; i2<4; i2++) {
								newLines[i1][i2] = lines[i2][i1];
							}
						}
						return newLines;
					} 
				},
				new MovementListener() {  //RIGHT
					@Override
					protected int[][] getLines() {
						int[][] lines = new int[4][4];
						
						for(int i=0; i<4; i++) {
							lines[i][0] = model[3][i];
							lines[i][1] = model[2][i];
							lines[i][2] = model[1][i];
							lines[i][3] = model[0][i];
						}
						
						return lines;
					}

					@Override
					protected int[][] exportLines(int[][] lines) {
						int[][] newLines = new int[4][4];

						for(int i=0; i<4; i++) {
							newLines[0][i] = lines[i][3];
							newLines[1][i] = lines[i][2];
							newLines[2][i] = lines[i][1];
							newLines[3][i] = lines[i][0];
						}
						
						return newLines;
					}
				}				
		);
		
		spawnPiece();
		view.update();
	}
	
	private void spawnPiece() {
		int freeFields = 0;
		
		//how many free fields are there?
		for(int[] list:model) {
			for(int i:list) {
				if(i == 0) {
					freeFields++;
				}
			}
		}
		
		//if none the game is lost
		if(freeFields == 0) {
			lost = true;
			return;
		}
		
		//if not we have to decide on which a new "2" (saved as 1, because 2^1=2) is going to be spawned
		int random = (int) (Math.random() * (freeFields-1));
		
		//count the free fields and spawn 
		for(int index=0; index<4; index++) {
			for(int i=0; i<4; i++) {
				if(model[index][i] == 0) {
					if(random == 0) {
						model[index][i] = 1;	//here we spawn the new piece
						return; //so we do not need to rest here
					}
					random--; //if we couldn't spawn we need to say that we came nearer the spawn place
				}
			}
		}
	}

	abstract class MovementListener implements ActionListener {
		protected abstract int[][] getLines();
		protected abstract int[][] exportLines(int[][] lines);
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			model = exportLines(collapseLines(getLines()));
			
			spawnPiece();
			view.update();
		}
		
		private int[][] collapseLines(int[][] lines) {
			for(int[] line: lines) {
				//remove space
				boolean found = true;
				while(found) {
					found = false;
					
					if(line[3] != 0 && line[2] == 0) {
						line[2] = line[3];
						line[3] = 0;
						found = true;
					}
					if(line[2] != 0 && line[1] == 0) {
						line[1] = line[2];
						line[2] = 0;
						found = true;
					}
					if(line[1] != 0 && line[0] == 0) {
						line[0] = line[1];
						line[1] = 0;
						found = true;
					}
				}
				
				
				//let them flow into each other
				if(line[0] != 0 && line[0] == line[1]) {
					line[0] = line[0]+1;
					score += Math.pow(2, line[0]);
					line[1] = line[2];
					line[2] = line[3];
					line[3] = 0;
					
					if(line[1] != 0 && line[1] == line[2]) {
						line[1] = line[1]+1;
						score += Math.pow(2, line[1]);
						line[2] = 0;
					}
				} else {
					if(line[1] != 0 && line[1] == line[2]) {
						line[1] = line[1]+1;
						score += Math.pow(2, line[1]);
						line[2] = line[3];
						line[3] = 0;
					} else {
						if(line[2] != 0 && line[2] == line[3]) {
							line[2] = line[2]+1;
							score += Math.pow(2, line[2]);
							line[3] = 0;
						}
					}
				}
			}
			return lines;
		}
	}
	
	public static void main(String[] args) {
		new Save2048();
	}

	public int[][] getData() {
		return model;
	}

	public int getScore() {
		return score;
	}

	public boolean isLost() {
		return lost;
	}
}
