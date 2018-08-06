package assignment4;

// Branch-and-Bound solver
public class KnapsackBBSolver extends KnapsackBFSolver {
	protected UPPER_BOUND ub;
	private int maxPossibleValue;
	private int fracBound;
	// private int TakenValue;
	private int untakenValue;
	private static FractionalSack myFrac;
	private static int capacity;
	private static long nodesCalculated;

	public KnapsackBBSolver(UPPER_BOUND ub_) {
		ub = ub_;
	}

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_) {
		System.out.println("\n\n\t\t### BRANCH AND BOUND " + ub + " ###\n");
		inst = inst_;
		itemCnt = inst.GetItemCnt();
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		maxPossibleValue(inst);
		untakenValue = 0;
		capacity = inst.GetCapacity();
		nodesCalculated = 0;
		FindSolns(1);
//		System.out.println("?? Nodes visited: " + nodesCalculated+" ??");
	}

	public void FindSolns(int itemNum) {
		nodesCalculated++;
		// System.out.printf("\tItem Num: %d OF: %d \n", itemNum, itemCnt);
		/**
		 * if Sac is too big, just return
		 */
		if (crntSoln.GetWeight() > inst.GetCapacity()) {

//			 System.out.printf("Sac is too big. don't even check: \n");
			// System.out.printf("item W: %d curr weight: %d Cap : %d\n",
			// inst.GetItemWeight(itemNum), crntSoln.GetWeight(),
			// inst.GetCapacity());
			return;
		}

		/**
		 * if at leaf, check the soln
		 */
		if (itemNum == itemCnt + 1) {
//			 System.out.println("\nat Leaf. Checking solution ");
			CheckCrntSoln(); // see if this soln leaf is better than last
			return;
		}

		if (boundHere(itemNum)) {
//			 System.out.println("Bounded");
			return;
		}

		/**
		 * Recurse
		 */
		takeItem(itemNum);
		FindSolns(itemNum + 1);
		undoTakeItem(itemNum);

		dontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		undoDontTakeItem(itemNum);
	}

	private void takeItem(int itemNum) {
//		System.out.println("takeItem " + itemNum);
		crntSoln.TakeItem(itemNum);

	}

	private void dontTakeItem(int itemNum) {
//		System.out.println("## DontTakeItem " + itemNum);
		crntSoln.DontTakeItem(itemNum);
		untakenValue += inst.GetItemValue(itemNum);
	}

	private void undoTakeItem(int itemNum) {
//		System.out.println("undoTakeItem " + itemNum);
		crntSoln.DontTakeItem(itemNum);
	}

	private void undoDontTakeItem(int itemNum) {
//		System.out.println("## undoDontTakeItem " + itemNum);
		untakenValue -= inst.GetItemValue(itemNum);

	}

	// ************************************************************
	// functional methods

	private boolean boundHere(int itemNum) {
		boolean boundHere = false;

		switch (ub.getValue()) {
		case 0:
			if (maxPossibleValue - untakenValue <= bestSoln.GetValue())
				return true;

			break;
		case 1:

			int temp = itemNum;
			if (UB2bound(temp, 0) + crntSoln.GetValue() > bestSoln.GetValue()) {
				return false;
			} else {
				return true;
			}
			// break;
		case 2:
			if (fractionalBoundHere(itemNum)) {
				return true;
			} else {
				return false;
			}
			// break;
		}
		return boundHere;
	}

	private int UB2bound(int itNum, int UB) {

		if (itNum == itemCnt + 1) {
			return UB;
		}
		if (inst.GetItemWeight(itNum) <= capacity - crntSoln.GetWeight()) {
			UB += inst.GetItemValue(itNum);
		}
		return UB2bound(itNum + 1, UB);

	}

	private boolean fractionalBoundHere(int itemNum) {
		myFrac = new FractionalSack(capacity, inst);
		inst.Sort();
		int capLeft = capacity - crntSoln.GetWeight();
		// crntSoln.Print("Cur soln");
//		 System.out.printf("\t\tCap: %d crntSoln Sack wght: %d capLeft: %d Item Num: %d\n"
//		 ,capacity,crntSoln.GetWeight(), capLeft, itemNum);

		fracBound = (int) myFrac.findFracAt(capLeft, itemNum);

		if (fracBound + crntSoln.GetValue() > bestSoln.GetValue()) {
			return false;
		} else {
			return true;
		}
	}

	private void maxPossibleValue(KnapsackInstance inst) {

		for (int i = 1; i - 1 < inst.GetItemCnt(); i++) {
			maxPossibleValue += inst.GetItemValue(i);
		}
		// System.out.println("Max Poss Value = " + maxPossibleValue);
	}

	public String getName() {
		return "BB-" + ub;
	}

	@Override
	public void CheckCrntSoln() {
		int crntVal = crntSoln.ComputeValue();
		// System.out.print("\nChecking solution ");
		// crntSoln.Print(" ");

		if (crntVal == DefineConstants.INVALID_VALUE) {
			return;
		}

		// The first solution is initially the best solution
		if (bestSoln.GetValue() == DefineConstants.INVALID_VALUE) {
			bestSoln.Copy(crntSoln);
//			 bestSoln.Print("# NEW BEST #");
		} else {
			if (crntVal > bestSoln.GetValue()) {
				bestSoln.Copy(crntSoln);
//				 bestSoln.Print("# NEW BEST #");
			}
		}
	}

}