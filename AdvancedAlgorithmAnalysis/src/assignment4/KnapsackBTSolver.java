package assignment4;

import java.util.*;

// Backtracking solver
public class KnapsackBTSolver extends KnapsackBFSolver {

	int itemCnt;

	public KnapsackBTSolver() {
		crntSoln = null;
	}

	public void close() {

	}

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_) {
		System.out.println("\n\n\t\t### BACK TRACK ###\n");
		inst = inst_;
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		itemCnt = inst.GetItemCnt();
		FindSolns(1);
		bestSoln.Print("BACK TRACK soln: ");
	}

	public void FindSolns(int itemNum) {
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
			return;
		}

		/**
		 * Recurse
		 */
		crntSoln.DontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.TakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.DontTakeItem(itemNum);
	}
	public String getName() {
		return "BackTrack";
	}
}
