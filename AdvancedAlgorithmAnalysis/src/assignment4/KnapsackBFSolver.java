package assignment4;

import java.util.*;

// Brute-force solver
public class KnapsackBFSolver implements KnapsackSolver {
	protected KnapsackInstance inst;
	protected KnapsackSolution crntSoln;
	protected KnapsackSolution bestSoln;
	int itemCnt;

	public void FindSolns(int itemNum) {
		itemCnt = inst.GetItemCnt();
		if (itemNum == itemCnt + 1) {
			CheckCrntSoln();
			return;
		}
		
		
		crntSoln.DontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.TakeItem(itemNum);
		FindSolns(itemNum + 1);
	}

	public void CheckCrntSoln() {
		int crntVal = crntSoln.ComputeValue();
//		 System.out.print("\nChecking solution ");
//		 crntSoln.Print(" ");

		if (crntVal == DefineConstants.INVALID_VALUE) {
			return;
		}

		// The first solution is initially the best solution
		if (bestSoln.GetValue() == DefineConstants.INVALID_VALUE){
			bestSoln.Copy(crntSoln);
		} else {
			if (crntVal > bestSoln.GetValue()) {
				bestSoln.Copy(crntSoln);
			}
		}
	}

	// Constructor sets crntSoln to null
	public KnapsackBFSolver() {
		crntSoln = null;
	}

	public void close() {
		if (crntSoln != null) {
			crntSoln = null;
		}
	}

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_) {
		System.out.println("\n\t\t### brute force ###");
		inst = inst_;
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		FindSolns(1);
		bestSoln.Print("brute force soln: ");
	}
	public String getName() {
		return "Brute Force";
	}
	
}







