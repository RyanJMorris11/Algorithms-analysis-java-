package assignment4;

import java.util.*;

/**
 * knapsack solution is an object class that gets defined with the solution to
 * the knapsack problem.
 * 
 */
public class KnapsackSolution implements java.io.Closeable {
	private boolean[] isTaken;
	
	private int value;
	private int wght; // wght in this soln so far
	private KnapsackInstance inst;

	public KnapsackSolution(KnapsackInstance inst_) {
		int i;
		int itemCnt = inst_.GetItemCnt();

		inst = inst_;
		isTaken = new boolean[itemCnt + 1];
		value = DefineConstants.INVALID_VALUE;
		;
		
		for (i = 1; i <= itemCnt; i++) {
			isTaken[i] = false;
		}
	}

	public void close() {
		isTaken = null;
	}

	/**
	 * 
	 * @param itemNum
	 *            //this int represents how far down we are towards the leaf
	 */
	public void TakeItem(int itemNum) {
		isTaken[itemNum] = true;
		wght += inst.GetItemWeight(itemNum);
		value += inst.GetItemValue(itemNum);
//		System.out.printf("Item %d Taken\n", itemNum);
		
		// check to see if it fits
		if (wght + inst.GetItemWeight(itemNum) > inst.GetCapacity()) { // check to see if it fits
//			System.out.printf("!! WARNING: Sac is too big with Item %d !!\n", itemNum);
		} 

	}
	


	/**
	 * 
	 * @param itemNum
	 *            //this int represents how far down we are towards the leaf
	 */
	public void DontTakeItem(int itemNum) {
		
		if (isTaken[itemNum] == true) { // if the Item was already taken, untake it
			isTaken[itemNum] = false;
			wght -= inst.GetItemWeight(itemNum);
			value -= inst.GetItemValue(itemNum);
//			System.out.printf("untake item %d \n", itemNum);

		} else {// it's not already taken, but might be null so set it to false for later.
			isTaken[itemNum] = false;
//			System.out.printf("Don't take item %d \n", itemNum);
		}

	}


	public int ComputeValue() {
		int i;
		int itemCnt = inst.GetItemCnt();
		int weight = 0;
		value = 0;
		for (i = 1; i <= itemCnt; i++) {
			if (isTaken[i] == true) {
				weight += inst.GetItemWeight(i);
				if (weight > inst.GetCapacity()) {
					value = DefineConstants.INVALID_VALUE;
					break;
				}
				value += inst.GetItemValue(i);
			}
		}
		return value;
	}

	public int GetValue() {
		return value;
	}

	public int GetWeight() {
		return wght;
	}

	public void Print(String title) {
		int i;
		int itemCnt = inst.GetItemCnt();

		System.out.printf("\n%s: ", title);
		for (i = 1; i <= itemCnt; i++) {
			if (isTaken[i] == true) {
				System.out.printf("%d ", i);
			}
		}
		System.out.printf("\nWeight = %d", wght);

		System.out.printf("\nValue = %d\n", value);
	}

	public void Copy(KnapsackSolution otherSoln) {
		int i;
		int itemCnt = inst.GetItemCnt();

		for (i = 1; i <= itemCnt; i++) {
			isTaken[i] = otherSoln.isTaken[i];
		}
		wght = otherSoln.wght;
		value = otherSoln.value;
	}

	public boolean equalsTo(KnapsackSolution otherSoln) {
		return value == otherSoln.value;
	}

	public void dispose() {
		isTaken = null;
	}

	public void makeAllUntaken() {

		for (int i = 1; i < isTaken.length; i++) {
			System.out.println("line 133: Length of is Taken: "+isTaken.length);
			isTaken[i] = false;
		}

	}
	
	public void sortKnapsack() {
		
	}
	public void validateKnapsac() {
		value=0;
	}
	public boolean getIsTaken(int itemNum) {
		return isTaken[itemNum];
	}

}
