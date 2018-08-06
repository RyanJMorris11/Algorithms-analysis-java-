package assignment4;

import java.util.Arrays;

/**
 * Put the not taken items in a stack so that you can unDontTake the item later.
 * 
 * @author ryanmorris
 *
 */
public class CustomSolver1 implements KnapsackSolver {
	KnapsackSolution bestSoln;
	KnapsackSolution crntSoln;
	KnapsackInstance inst;
	int cap;
	int itemCnt;

	// // Fractional Knapsack needs
	float fracVal;
	float fracWght;
	int pointer;
	double pRatio; // from 0 to .99999 representing the amount of the slack Item that we've
	private long nodesVisited;
	// // taken so far.

	// boolean foundFirstSoln;

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_) {
		System.out.println("\n\n\t\t### Custom Solver ###\n");
		inst = inst_;
		itemCnt = inst.GetItemCnt();
		cap = inst.GetCapacity();
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		sortByRatio();

		// Frac declarations
		fracVal = 0;
		fracWght = 0;
		pointer = 1;
		pRatio = 0;
		// foundFirstSoln = true;
		nodesVisited = 0;
//		findLowerBound(); // sets the best soln to the lower bound.
		FindSolns(1);
		System.out.println("Nodes visited: " + nodesVisited);
		// bestSoln.Print("Custom soln: ");
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

	private void sortByRatio() {
		inst.Sort();
	}

	public void FindSolns(int itemNum) {
		nodesVisited++;
		// System.out.printf("\tItem Num: %d OF: %d \n", itemNum, itemCnt);

		/**
		 * if Sac is too big, just return
		 */
		if (crntSoln.GetWeight() > inst.GetCapacity()) {
			// System.out.printf("Sac is too big. don't even check it\n");
			// System.out.printf("item W: %d curr weight: %d Cap : %d\n",
			// inst.GetItemWeight(itemNum), crntSoln.GetWeight(),
			// inst.GetCapacity());
			return;
		}

		/**
		 * if at leaf, check the soln
		 */
		if (itemNum == itemCnt + 1) {
			// System.out.println("\nat Leaf. Checking solution ");
			CheckCrntSoln(); // see if this soln leaf is better than last
			fracWght = 0;
			fracVal = 0;
			pRatio = 0;
			return;
		} 
			// System.out.println(" \t\t# finding bound#");
			if (boundHere()) {
				// crntSoln.Print("## Bounded ##");
				// System.out.printf("capleft= %d Ratio= %f fracVal= %d UB =%d\n", (cap -
				// (int)fracWght), pRatio, (int)fracVal,((int)fracVal+ crntSoln.GetValue() ));
				return;
			} 

		/**
		 * Recurse
		 */

//		if (cap >= crntSoln.GetWeight() + inst.GetItemWeight(itemNum)) { // if item fits then take it.
			takeItem(itemNum);
			FindSolns(itemNum + 1);
			undoTakeItem(itemNum);
//		}

		dontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		undoDontTakeItem(itemNum);// save a fractional knapsack!!
	}

	private boolean boundHere() {
		findFrac(); //
		if (fracVal + crntSoln.GetValue() > bestSoln.GetValue()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Take more items until you can't anymore
	 * 
	 * @param pointerItemNum
	 */
	private void findFrac() {
		// System.out.printf("ln 119: pointer=%d fracWght=%d cap=%d fracVal= %d UB
		// =%d\n" ,pointer, (int)fracWght, cap, (int)fracVal,((int)fracVal+
		// crntSoln.GetValue()) );

		if (fracWght + crntSoln.GetWeight() >= cap) { // if frac wght is too big then return.
			// System.out.printf("At max Cap: %d + %d = %d \n", (int)fracWght,
			// crntSoln.GetWeight(), (int)fracWght + crntSoln.GetWeight());
			return;
		}

		if (pointer >= itemCnt + 1) { // If the pointer is past the end then we've already taken as much as we can
			// System.out.printf("Can't take anymore: fracVal= %d, TakenVal= %d, Ub=%d",
			// (int) fracVal, crntSoln.GetValue(), (int)fracVal+ crntSoln.GetValue());
			return;
		}

		if (pRatio != 0) { // if we need to take part of an item that's already been partially taken

			double itemWghtTaken = pRatio * inst.GetItemWeight(pointer);
			double itemValTaken = pRatio * inst.GetItemValue(pointer);
			double wghtToGrab = cap - (fracWght + crntSoln.GetWeight());

			if (itemWghtTaken - inst.GetItemWeight(pointer) <= wghtToGrab) { // the rest fits

				fracWght += inst.GetItemWeight(pointer) - itemWghtTaken;
				fracVal += inst.GetItemValue(pointer) - itemValTaken;
				pointer++;
				pRatio = 0;

				findFrac();

				// System.out.printf("capleft= %d Ratio= %f fracVal= %d UB =%d\n", (cap -
				// (int)fracWght), pRatio, (int)fracVal,((int)fracVal+ crntSoln.GetValue() ));
				return;
			} else { // rest doesn't fit
				// Just knock off the fraction of the item we had before, then add the whole
				// fraction we want to take
				pRatio = wghtToGrab + (inst.GetItemWeight(pointer) * pRatio) / inst.GetItemWeight(pointer);
				fracWght -= itemWghtTaken;
				fracWght += inst.GetItemWeight(pointer) * pRatio;

				fracVal -= itemValTaken;
				fracWght += inst.GetItemValue(pointer) * pRatio;

				// new_pRatio = ItemTaken/TotalItem

//				 System.out.printf("pointer=%ditemWght= %d capleft= %d NewRatio= %f fracVal=
//				 %d UB =%d\n", pointer, inst.GetItemWeight(pointer), (cap - (int)fracWght),
//				 pRatio, (int)fracVal, ((int)fracVal+ crntSoln.GetValue()) );
				return;
			}
		}

		if (inst.GetItemWeight(pointer) > cap - (fracWght + crntSoln.GetWeight())) { // if can't fit all the way
			addPartOfPointerItemToFrac();
			return;
		} else { // the whole Item fits

			fracVal += inst.GetItemValue(pointer);
			fracWght += inst.GetItemWeight(pointer);
			pointer++;
			pRatio = 0;
			findFrac();
			return;
		}

	}

	private void takeItem(int itemNum) {

		// System.out.println("takeItem "+ itemNum);
		crntSoln.TakeItem(itemNum);

		fracVal -= inst.GetItemValue(itemNum);
		fracWght -= inst.GetItemWeight(itemNum);
		// findFrac();

	}

	private void undoTakeItem(int itemNum) {

		// System.out.println("undoTakeItem "+ itemNum);
		crntSoln.DontTakeItem(itemNum);

		fracVal += inst.GetItemValue(itemNum);
		fracWght += inst.GetItemWeight(itemNum);
		// findFrac();
	}

	private void dontTakeItem(int itemNum) {

		// System.out.println("## DontTakeItem "+ itemNum);
		crntSoln.DontTakeItem(itemNum);

		// System.out.printf( "## Ln 199: fracWght %d fracVal %d ItemWght %d ItemVal %d
		// solnWght %d solnVal %d\n" ,(int)fracWght, (int)fracVal,
		// inst.GetItemWeight(itemNum), inst.GetItemValue(itemNum),
		// crntSoln.GetWeight(), crntSoln.GetValue() );

		fracWght -= inst.GetItemWeight(itemNum);
		fracVal -= inst.GetItemValue(itemNum);

		if (pointer <= itemNum) {
			// System.out.printf( "ItemNum > pointer" );
			pointer = itemNum + 1;
			fracWght = 0;
			fracVal = 0;
			pRatio = 0;
		}
		findFrac();

		// System.out.printf( "## Ln 211: fracWght %d fracVal %d ItemWght %d ItemVal %d
		// solnWght %d solnVal %d\n" ,(int)fracWght, (int)fracVal,
		// inst.GetItemWeight(itemNum), inst.GetItemValue(itemNum),
		// crntSoln.GetWeight(), crntSoln.GetValue() );

	}

	private void undoDontTakeItem(int itemNum) {

		// System.out.println("undoDontTakeItem "+ itemNum);

		// Adding these back to fractional will break the fractional
		fracWght += inst.GetItemWeight(itemNum);
		fracVal += inst.GetItemValue(itemNum);
		fixFrac();

	}

	/**
	 * knock items out of the fracSack until Fractional is small enough then will
	 * call find FracSac to fix over-correction
	 * 
	 */
	private void fixFrac() {
		// System.out.println("Fixing:");

		if (cap >= fracWght + crntSoln.GetWeight()) {
			findFrac();
			return;
		}
		// System.out.printf("Fixing: pointer=%d fracWght=%d Crnt.wght =%d fracVal= %d
		// cap = %d UB =%d\n" ,pointer, (int)fracWght, crntSoln.GetWeight() ,
		// (int)fracVal, cap, ((int)fracVal+ crntSoln.GetValue()) );

		if (fracWght + crntSoln.GetWeight() > cap) {// if is too much
			removeAllOfPointerItemFromFrac();
		}

		fixFrac();

	}

	/**
	 * Adds part of the next item to the frac sac The pointer doesn't change because
	 * this is the item that will be taken care of next time
	 * 
	 */
	private void addPartOfPointerItemToFrac() {

		pRatio = (double) (cap - (fracWght + crntSoln.GetWeight())) / inst.GetItemWeight(pointer);

		fracVal += pRatio * inst.GetItemValue(pointer);
		fracWght += pRatio * inst.GetItemWeight(pointer);
		// System.out.printf("capleft= %d Ratio= %f fracVal= %d UB =%d\n", (cap -
		// (int)fracWght), pRatio, (int)fracVal,((int)fracVal+ crntSoln.GetValue() ));
	}

	/**
	 * 
	 * 
	 */
	private void removeAllOfPointerItemFromFrac() {
		// If there is still some of this Item here, then the pointer
		// is already pointing at the next item that will be taken
		// System.out.println("droping pRatio = "+pRatio+" pointer = "+ pointer);
		if (pRatio != 0) {
			// System.out.println("Throw part of item " + pointer);

			fracVal -= pRatio * inst.GetItemValue(pointer);
			fracWght -= pRatio * inst.GetItemWeight(pointer);
			pRatio = 0;
		} else { // the pointer needs to move down because the pointer is ahead of the last item
					// taken
			pointer--;
			fracVal -= inst.GetItemValue(pointer);
			fracWght -= inst.GetItemWeight(pointer);
		}
	}

	@Override
	public String getName() {
		return "Custom1";
	}

	public void CheckCrntSoln() {
		int crntVal = crntSoln.ComputeValue();
		// System.out.println("\nChecking solution ");
		// crntSoln.Print("crnt");
		// bestSoln.Print("best");

		if (crntVal == DefineConstants.INVALID_VALUE) {
			return;
		}

		// The first solution is initially the best solution
		if (bestSoln.GetValue() == DefineConstants.INVALID_VALUE) {
			bestSoln.Copy(crntSoln);
			// bestSoln.Print("# NEW BEST #");
		} else {
			if (crntVal > bestSoln.GetValue()) {
				bestSoln.Copy(crntSoln);
				// bestSoln.Print("# NEW BEST #");
			}
		}
	}
}
