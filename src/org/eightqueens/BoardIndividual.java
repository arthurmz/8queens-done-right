package org.eightqueens;

import java.util.Random;

public class BoardIndividual {
	
	public static int BOARD_SIZE = 8;
	
	public boolean[][] matrix = new boolean [BOARD_SIZE][BOARD_SIZE];
	
	private int allColumns;
	private int halfSoutheastDiagonals;
	private int secondHalfSoutheastDiagonals;
	private int halfNortheastDiagonals;
	private int secondHalfNortheastDiagonals;
	private int fitness;
	
	public BoardIndividual(Random random) {
		for(int i = 0; i < BOARD_SIZE; i++) {
			int column = random.nextInt(BOARD_SIZE);
			matrix[i][column] = true;
		}
	}
	
	public void computeFitness() {
		allColumns = 0;
		halfSoutheastDiagonals = 0;
		secondHalfSoutheastDiagonals = 0;
		halfNortheastDiagonals = 0;
		secondHalfNortheastDiagonals = 0;
		
		//All columns
		for(int j = 0; j < BOARD_SIZE; j++)
			allColumns += collision(0, j, 1, 0);
		
		//Half Southeast diagonals
		for(int j = 0; j < BOARD_SIZE; j++)
			halfSoutheastDiagonals += collision(0, j, 1, 1);
		
		//Second Half Southeast diagonals
		for(int i = 1; i < BOARD_SIZE; i++)
			secondHalfSoutheastDiagonals += collision(i, 0, 1, 1);
		
		//Half Northeast diagonals
		for(int i = BOARD_SIZE-1; i >= 0; i--)
			halfNortheastDiagonals += collision(i, 0, -1, 1);
		
		//Second Half Northeast diagonals
		for(int j = 1; j < BOARD_SIZE; j++)
			halfNortheastDiagonals += collision(BOARD_SIZE-1, j, -1, 1);
		
		fitness = allColumns + halfSoutheastDiagonals + secondHalfSoutheastDiagonals
				+halfNortheastDiagonals + secondHalfNortheastDiagonals;
	}
	
	public int getFitness() {
		return fitness;
	}
	
	private int collision(int fixedStartI, int fixedStartJ, int incrementI, int incrementJ) {
		int found = 0;
		int i = fixedStartI;
		int j = fixedStartJ;
		
		while(insideBoard(i, j)) {
			if(matrix[i][j])
				found++;
			
			i += incrementI;
			j += incrementJ;
		}
		return (found > 1) ? found-1 : 0;
	}
	
	private boolean insideBoard(int i, int j) {
		return (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE);
	}

	public void crossover(BoardIndividual idv1, int crossoverPoint) {
		for(int i = 0; i < crossoverPoint; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				boolean temp = matrix[i][j];
				matrix[i][j] = idv1.matrix[i][j];
				idv1.matrix[i][j] = temp;
			}
		}
		
	}

	public void swapLines(int line1, int line2) {
		for(int j = 0; j < BOARD_SIZE; j++) {
			boolean temp = matrix[line2][j];
			matrix[line2][j] = matrix[line1][j];
			matrix[line1][j] = temp;
		}
	}

	public void moveQueen(int line, boolean forward) {
		for(int j = 0; j < BOARD_SIZE; j++) {
			if(matrix[line][j]) {
				if(forward && insideBoard(line, j+1)) {
					matrix[line][j] = false;
					matrix[line][j+1] = true;
				}
				else if(insideBoard(line, j-1)) {
					matrix[line][j] = false;
					matrix[line][j-1] = true;
				}
				break;
			}
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				if(matrix[i][j])
					sb.append('1');
				else
					sb.append('0');
				sb.append(' ');
			}
			sb.append('\n');
		}
		sb.append("FO: "+ fitness + '\n');
		sb.append("All Columns: " + allColumns + '\n');
		sb.append("halfSoutheastDiagonals: " + halfSoutheastDiagonals + '\n');
		sb.append("secondHalfSoutheastDiagonals: " + secondHalfSoutheastDiagonals + '\n');
		sb.append("halfNortheastDiagonals: " + halfNortheastDiagonals + '\n');
		sb.append("secondHalfNortheastDiagonals: " + secondHalfNortheastDiagonals + '\n');
		return sb.toString();
	}
}
