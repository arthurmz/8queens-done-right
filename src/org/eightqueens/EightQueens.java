package org.eightqueens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class EightQueens {
	
	private int POPULATION_SIZE = 10;
	private double crossoverOdds = 0.10;
	private double mutationMoveOdds = 0.30;
	private double mutationSwapOdds = 0.10;
	
	private Random random = new Random();
	
	List<BoardIndividual> fillPopulation(){
		List<BoardIndividual> pop = new ArrayList<BoardIndividual>();
		for(int i = 0; i < POPULATION_SIZE; i++) {
			pop.add(new BoardIndividual(random));
		}
		return pop;
	}
	
	private boolean searchForWinner(List<BoardIndividual> population){
		for(BoardIndividual obj : population) {
			if(obj.getFitness() == 0) {
				System.out.println("Good!");
				System.out.println(obj.toString());
				obj.getFitness();
				return true;
			}
		}
		return false;
	}
	
	private BoardIndividual chose(List<BoardIndividual> pop, int totalFitness, int[] pesos) {
		int chosenOne = (int) (random.nextDouble() * totalFitness);
		int found = 0;
		int peso = 0;
		for(int i = 0; i < POPULATION_SIZE ; i++) {
			peso += pesos[i];
			if(peso >= chosenOne) {
				found = i;
				break;
			}
		}
		
		BoardIndividual idv = pop.get(found);
		return idv;
	}
	

	
	private void crossover(List<BoardIndividual> pop, List<BoardIndividual> dest) {
		int[] pesos = new int[POPULATION_SIZE];
		int x = 0;
		int totalFitness = 0;
		for(BoardIndividual bi : pop) {
			pesos[x] = bi.getFitness();
			totalFitness += bi.getFitness();
			x++;
		}
		
		while(dest.size() < POPULATION_SIZE) {
			BoardIndividual idv1 = chose(pop, totalFitness, pesos);
			BoardIndividual idv2 = chose(pop, totalFitness, pesos);
			
			double doCrossover = random.nextDouble();
			if(doCrossover > crossoverOdds ) {
				//Between 1 and 6
				int crossoverPoint = (int) (1 + (random.nextDouble() * 5));
				idv2.crossover(idv1, crossoverPoint);
			}
			dest.add(idv1);
			dest.add(idv2);
		}
	}
	
	private void mutate(List<BoardIndividual> pop) {
		for(BoardIndividual bi : pop) {
			double mutationSwapProb = random.nextDouble();
			if(mutationSwapProb > mutationSwapOdds) {
				int line1 = random.nextInt(BoardIndividual.BOARD_SIZE);
				int line2 = random.nextInt(BoardIndividual.BOARD_SIZE);
				bi.swapLines(line1, line2);
			}
			double mutationMoveProb = random.nextDouble();
			if(mutationMoveProb > mutationMoveOdds) {
				int line = random.nextInt(BoardIndividual.BOARD_SIZE);
				boolean forward = random.nextBoolean();
				bi.moveQueen(line, forward);
			}
		}
	}
	
	
	private void computeFitness(List<BoardIndividual> pop) {
		for(BoardIndividual bi : pop) {
			bi.computeFitness();
		}
	}
	
	
	public void solve() {
		System.out.println("Computing...");
		List<BoardIndividual> pop1 = fillPopulation();
		List<BoardIndividual> pop2 = new ArrayList<BoardIndividual>();
		
		int iteration = 0;
		while(true) {
			iteration++;
			
			computeFitness(pop1);
			
			if(searchForWinner(pop1)) {
				System.out.println("Iterations: " + iteration);
				return;
			}
			
			crossover(pop1, pop2);
			mutate(pop2);
			
			//Population Swap
			pop1.clear();
			List<BoardIndividual> popTemp = pop1;
			pop1 = pop2;
			pop2 = popTemp;
		}
	}

}
