package org.eightqueens;

import java.util.ArrayList;
import java.util.List;

/**
 * 8Queens problem solved with a Simple Genetic Algorithm.
 * @author arthur
 *
 */
public class Main {
	
	public static void main(String args[]) {
		long startTime = System.nanoTime();
		EightQueens eq = new EightQueens();
		eq.solve();
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		System.out.println("Execution time in milliseconds : " + 
				timeElapsed / 1000000);
	}

}
