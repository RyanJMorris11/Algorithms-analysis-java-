package assignment4;

import java.io.IOException;
import java.util.*;

// Dynamic programming solver

/**
 * The table one that was on exam.
 * 
 * @author ryanmorris
 *
 */
public class KnapsackDPSolver implements KnapsackSolver{
	protected KnapsackInstance inst;
	protected KnapsackSolution bestSoln;
	int itemCnt;
	int capacity;
	int[][] myT;

	public KnapsackDPSolver() {
	}

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_) {
		System.out.println("\n\t\t### Dynamic Approach ###\n");
		
		inst = inst_;
		bestSoln = soln_;
		itemCnt=inst.GetItemCnt();
		capacity = inst.GetCapacity();
		myT = new int[itemCnt+1][capacity+1];
		
		bestSoln.validateKnapsac();
		FindSoln();
		traceBack();
//		bestSoln.Copy(crntSoln);
		bestSoln.Print("Dynamic soln: ");
	}


	private void traceBack() {
		int i = itemCnt;
		int j = capacity;
		
		while(i > 0  && j > 0) {
			while(i > 0  && j > 0 && myT[i][j] == myT[i-1][j]) {
				i--;
				if( i<1 ) 
					return;
			}	
			bestSoln.TakeItem(i);
//			bestSoln.Print("Working Dynamic soln: ");
//			System.out.println("Take item value: " + inst.GetItemValue(i));
			j-=inst.GetItemWeight(i);
			i--;
			
		}
	}

	private void FindSoln() {

		for(int i=1 ; i< itemCnt+1 ; i++) {
			for(int j =0; j <capacity+1 ; j++) {
				if(inst.GetItemWeight(i)>j) {
					myT[i][j]=myT[i-1][j];
				}else
					myT[i][j] = Math.max(   inst.GetItemValue(i) + myT[i-1] [j-inst.GetItemWeight(i)]  , myT[i-1][j]);
			}
		}
//		printTable();
	}

	
	
	








	private void printTable() {
		
		System.out.println(Arrays.deepToString(myT).replace("], ", "]\n"));	
		}
	public String getName() {
		return "Dynamic Algorithm";
	}
}