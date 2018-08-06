package assignment4;

import java.util.*;

/**
 * Contains the knapsack problem: Number of Items Capacity int[] weights and
 * int[] values
 */

public class KnapsackInstance implements java.io.Closeable {
	private int itemCnt; // Number of items
	private int cap; // The capacity
	private int[] weights; // An array of weights
	private int[] values; // An array of values
	private boolean isSorted;
	private boolean isPremadeInst;
	ArrayList<Integer> savedWghts;
	private static Random rand = new Random();

	public KnapsackInstance(int itemCnt_) {
		itemCnt = itemCnt_;
		weights = new int[itemCnt + 1];
		values = new int[itemCnt + 1];
		isPremadeInst=false;
		cap = 0;
	}

	public KnapsackInstance(ArrayList<Integer> newSavedWghts) {
		savedWghts = newSavedWghts;
		itemCnt = savedWghts.size();
		weights = new int[itemCnt + 1];
		values = new int[itemCnt + 1];
		cap = 0;
		isPremadeInst=true;

		generatePremade();
		
	}

	public void close() {
		weights = null;
		values = null;
	}

	public void Generate() {
		if (isPremadeInst){
			return;
		}
		int i;
		int wghtSum;

		weights[0] = 0;
		values[0] = 0;
		isSorted = false;

		wghtSum = 0;
		for (i = 1; i <= itemCnt; i++) {
			weights[i] = Math.abs(rand.nextInt() % 100 )+ 1;
			values[i] = weights[i] + 10; // value is always 10 more than weight
			wghtSum += weights[i];
		}
		cap = wghtSum / 2; // half of the total weight of items can fit in the sack.
	}

	private void generatePremade() {
		int i;
		int wghtSum;

		weights[0] = 0;
		values[0] = 0;
		isSorted = false;

		wghtSum = 0;
		for (i = 1; i <= itemCnt; i++) {
			weights[i] = savedWghts.get(i-1);
			values[i] = weights[i] + 10; // value is always 10 more than weight
			wghtSum += weights[i];
		}
		cap = wghtSum / 2; // half of the total weight of items can fit in the sack.		
	}

	public int GetItemCnt() {
		return itemCnt;
	}

	public int GetItemWeight(int itemNum) {
		return weights[itemNum];
	}

	public int GetItemValue(int itemNum) {
		return values[itemNum];
	}

	public int GetCapacity() {
		return cap;
	}

	public void Print() {
		int i;

		System.out.printf("Number of items = %d, Capacity = %d\n", itemCnt, cap);
		System.out.print("Weights: ");
		for (i = 1; i <= itemCnt; i++) {
			System.out.printf("%d ", weights[i]);
		}
		System.out.print("\nValues: ");
		for (i = 1; i <= itemCnt; i++) {
			System.out.printf("%d ", values[i]);
		}
		System.out.print("\n");
	}

	public void Sort() {
		if(isSorted) 
			return;
		Arrays.sort(values);
		Arrays.sort(weights);
//		Print();

//		for (int i = 1; i < values.length / 2; i++) {
//			int temp = values[i];
//			values[i] = values[itemCnt+1 - i];
//			values[itemCnt +1- i] = temp;
//			weights[itemCnt +1- i] = values[itemCnt +1- i]-10;
//			weights[i] = values[i]-10;
//
//		}
//		weights[itemCnt/2+1] = values[itemCnt/2+1]-10;

		
		isSorted = true;
		Print();
		System.out.println();
	}
}