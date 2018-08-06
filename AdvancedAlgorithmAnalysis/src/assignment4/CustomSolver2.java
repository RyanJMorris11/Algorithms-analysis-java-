package assignment4;

public class CustomSolver2 implements KnapsackSolver {
	KnapsackSolution bestSoln;
	KnapsackSolution crntSoln;
	KnapsackInstance inst;
	QuickFracSack qFrac;
	int cap;
	int itemCnt;
	private long nodesVisited;

	@Override
	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_) {
		System.out.println("\n\n\t\t### Custom Solver 2 ###\n");
		inst = inst_;
		itemCnt = inst.GetItemCnt();
		cap = inst.GetCapacity();
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		qFrac = new QuickFracSack(inst, crntSoln);

		sortByRatio();

		nodesVisited = 0;
		 findLowerBound(); // sets the best soln to the lower bound.
		FindSolns(1);
		System.out.println("?? Nodes visited: " + nodesVisited+" ??");
	}

	public void FindSolns(int itemNum) {
		nodesVisited++;
		 System.out.printf("\tItem Num: %d OF: %d \n", itemNum, itemCnt);

		/**
		 * if Sac is too big, just return
		 */
		if (crntSoln.GetWeight() > inst.GetCapacity()) {
			System.out.printf("Sac is too big. don't even check it\n");
//			 System.out.printf("item W: %d curr weight: %d Cap : %d\n",
			// inst.GetItemWeight(itemNum), crntSoln.GetWeight(),
			// inst.GetCapacity());
			return;
		}
		/**
		 * if at leaf, check the soln
		 */
		if (itemNum == itemCnt + 1) {
			System.out.println("\nat Leaf. Checking solution ");
			qFrac.atLeaf(itemNum);
			CheckCrntSoln(); // see if this soln leaf is better than last
			return;
		}
		// System.out.println(" \t\t# finding bound#");
		if (boundHere(itemNum)) {
			 System.out.println("## Bounded ##");
			 System.out.printf("Ratio= %f fracVal= %d crntSolnVal=%d UB =%d\n", qFrac.pRatio, (int)qFrac.fracVal, crntSoln.GetValue(), ((int)qFrac.fracVal + crntSoln.GetValue() ));
			
			 return;
		}

		/**
		 * Recurse
		 */
		// if (cap >= crntSoln.GetWeight() + inst.GetItemWeight(itemNum)) { // if item
		// fits then take it.
		takeItem(itemNum);
		FindSolns(itemNum + 1);
		undoTakeItem(itemNum);
		// }

		dontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		undoDontTakeItem(itemNum);
	}

	private boolean boundHere(int itemNum) {
		int fracUB = qFrac.getFracUB(itemNum);
		
		System.out.println("\t\t## frac  UB= " + fracUB+ " crntSolnVal "+ crntSoln.GetValue());
		System.out.println("\t\t## Total UB= " + ((int)crntSoln.GetValue()+fracUB));
		System.out.println("\t\t## BEST Soln= " + ((int)bestSoln.GetValue()));

		if(fracUB + crntSoln.GetValue() > qFrac.maxUB) {
			System.out.println("ERROR UB IS TOO BIG" );
			System.exit(1);
		}
		
		if (fracUB + crntSoln.GetValue() > bestSoln.GetValue()) {
			return false;
		} else {
			return true;
		}
	}

	/********************************************************/
	// takes and such etc
	/********************************************************/

	private void takeItem(int itemNum) {

		System.out.println("takeItem " + itemNum);
		crntSoln.TakeItem(itemNum);
		qFrac.takeItem(itemNum);
	}

	private void undoTakeItem(int itemNum) {

		System.out.println("undoTakeItem " + itemNum);
		crntSoln.DontTakeItem(itemNum);
		qFrac.undoTake(itemNum);
	}

	private void dontTakeItem(int itemNum) {

		System.out.println("## DontTakeItem " + itemNum);
		crntSoln.DontTakeItem(itemNum);
		qFrac.dontTakeItem(itemNum);
	}

	private void undoDontTakeItem(int itemNum) {
		System.out.println("## undoDontTakeItem " + itemNum);
		qFrac.undoDontTake(itemNum);
	}

	/********************************************************/

	public void CheckCrntSoln() {
		int crntVal = crntSoln.ComputeValue();
		 System.out.println("\nChecking solution ");
		 crntSoln.Print("crnt");
		 bestSoln.Print("best");

		if (crntVal == DefineConstants.INVALID_VALUE) {
			return;
		}

		// The first solution is initially the best solution
		if (bestSoln.GetValue() == DefineConstants.INVALID_VALUE) {
			bestSoln.Copy(crntSoln);
			 bestSoln.Print("# NEW BEST #");
		} else {
			if (crntVal > bestSoln.GetValue()) {
				bestSoln.Copy(crntSoln);
				bestSoln.Print("# NEW BEST #");
			}
		}
	}

	private void sortByRatio() {
		inst.Sort();
	}

	@Override
	public String getName() {
		return "#  Custom2 #";
	}

	private void findLowerBound() {
		KnapsackSolution lowBSoln = new KnapsackSolution(inst);
		int i = 0;

		while (i <= itemCnt && lowBSoln.GetWeight() + inst.GetItemWeight(i) <= cap) {
			lowBSoln.TakeItem(i);
			i++;
		}
		bestSoln.Copy(lowBSoln);
		bestSoln.Print("Lower Bound");
	}
}
