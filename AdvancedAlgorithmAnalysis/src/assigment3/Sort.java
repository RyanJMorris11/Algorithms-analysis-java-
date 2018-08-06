package assigment3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Sort {
	final int MAX_SIZE = 100;

	// Set this to true if you wish the arrays to be printed.

	final static boolean OUTPUT_DATA = false;
	public static String sortAlg = null;
	public static int size = 0;
	public static boolean go = true;
	public static int swaps;
	public static int height1;
	public static int height2;
	public static int height3;
	static int maxH;
	public static Random rand = new Random();
	public static ArrayList<Integer> sortedTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> randomSortedTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> nearlySortedTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> reverseSortedTimes = new ArrayList<Integer>();

	public static void main(String[] args) {

		while (go) {
			readInput();

			if (sortAlg.equals("A")) {
				continue;
			}

			int[] data = new int[size];

			// GenerateSortedData(data, size);
			// Sort(data, size, "Sorted Data");

			// GenerateNearlySortedData(data, size);
			// Sort(data, size, "Nearly Sorted Data");

			// GenerateReverselySortedData(data, size);
			// Sort(data, size, "Reversely Sorted Data");
			//
			GenerateRandomData(data, size);
			Sort(data, size, "Random Data");

			System.out.println("\nProgram Completed Successfully.\n\n\n");
		}

	}

	@SuppressWarnings("resource")
	public static void readInput() {
		System.out.println("  I:\tInsertion Sort");
		System.out.println("  M:\tMergeSort");
		System.out.println("  Q:\tQuickSort");
		System.out.println("  L:\tLibSort");
		System.out.println("  C:\tCustomQuickSort");
		System.out.println("  S:\tCustomDualSort");

		System.out.println("Enter sorting algorithm:");
		Scanner reader = new Scanner(System.in);
		sortAlg = reader.next();
		System.out.println(sortAlg);
		String sortAlgName = "";

		if (sortAlg.equals("I"))
			sortAlgName = "Insertion Sort";
		else if (sortAlg.equals("M"))
			sortAlgName = "MergeSort";
		else if (sortAlg.equals("Q"))
			sortAlgName = "QuickSort";
		else if (sortAlg.equals("L"))
			sortAlgName = "LibSort";
		else if (sortAlg.equals("C"))
			sortAlgName = "CustomQuickSort";
		else if (sortAlg.equals("S"))
			sortAlgName = "CustomDualSort";
		else if (sortAlg.equals("stop")) {
			go = false;
			System.out.println("stopping");
			System.exit(1);
		} else {
			System.out.println("Unrecognized sorting algorithm Code: " + sortAlg + " \nTry again");
			sortAlg = "A";
			return;
		}
		System.out.println("Enter input size: ");
		size = reader.nextInt();
		System.out.println("\nSorting Algorithm: " + sortAlgName);
		System.out.println("\nInput Size = " + size);
	}

	/******************************************************************************/

	public static void GenerateSortedData(int data[], int size) {
		int i;

		for (i = 0; i < size; i++)
			data[i] = i * 3 + 5;
	}

	/*****************************************************************************/
	public static void GenerateNearlySortedData(int data[], int size) {
		int i;

		GenerateSortedData(data, size);

		for (i = 0; i < size; i++)
			if (i % 10 == 0)
				if (i + 1 < size)
					data[i] = data[i + 1] + 7;
	}

	/*****************************************************************************/

	public static void GenerateReverselySortedData(int data[], int size) {
		int i;

		for (i = 0; i < size; i++)
			data[i] = (size - i) * 2 + 3;
	}

	/*****************************************************************************/

	public static void GenerateRandomData(int data[], int size) {
		int i;
		for (i = 0; i < size; i++)
			data[i] = new Random().nextInt(10000000);
	}

	/*****************************************************************************/

	public static void Sort(int[] data, int size, String string) {

		System.out.print("\n" + string + ":");
		if (OUTPUT_DATA) {
			printData(data, size, "Data before sorting:");
		}

		// Sorting is about to begin ... start the timer!
		long start_time = System.nanoTime();
		if (sortAlg.equals("I")) {
			InsertionSort(data, size);
		} else if (sortAlg.equals("M")) {
			MergeSort(data, 0, size - 1);
		} else if (sortAlg.equals("Q")) {
			QuickSort(data, 0, size - 1);
		} else if (sortAlg.equals("L")) {
			LibSort(data, size);
		} else if (sortAlg.equals("C")) {
			customQuickSort(data, 0, size - 1);
		} else if (sortAlg.equals("S")) {
			customDualSort(data, 0, size - 1);
		} else {
			System.out.print("Invalid sorting algorithm!");
			System.out.print("\n");
			System.exit(1);
		}

		// Sorting has finished ... stop the timer!

		double elapsed = System.nanoTime() - start_time;
		elapsed = elapsed / 1000000;

		if (OUTPUT_DATA) {
			printData(data, size, "Data after sorting:");
		}

		if (IsSorted(data, size)) {
			System.out.print("\nCorrectly sorted ");
			System.out.print(size);
			System.out.print(" elements in ");
			System.out.print(elapsed);
			System.out.print("ms");
		} else {
			System.out.print("ERROR!: INCORRECT SORTING!");
			System.out.print("\n");
		}
		System.out.print("\n-------------------------------------------------------------\n");
	}

	/*****************************************************************************/

	public static boolean IsSorted(int data[], int size) {
		int i;

		for (i = 0; i < (size - 1); i++) {
			if (data[i] > data[i + 1])
				return false;
		}
		return true;
	}

	/*****************************************************************************/

	public static void InsertionSort(int[] data, int size) {
		myInsertionSort(data, 0, size - 1);
		return;
	}

	public static void myInsertionSort(int[] data, int low, int high) {

		int temp;
		// System.out.println("high = " + high + ", low = " + low);

		for (int i = low; i <= high; i++) {
			// System.out.println("i = " + i);
			temp = data[i];
			int j;
			for (j = i - 1; j >= 0; j--) {
				if (data[j] > temp) {
					data[j + 1] = data[j];
				} else
					break;
			}
			data[j + 1] = temp;
		}
		// System.out.println("InserionSort: " + low + " - " + high);
	}

	/*****************************************************************************/

	public static int[] MergeSort(int[] myArr, int start, int end) {
		// System.out.println("\nCall splitting start = " + start + " end = " +end);
		int mid = (end - start) / 2 + start;

		if ((end - start) > 1) {
			MergeSort(myArr, start, mid);
			MergeSort(myArr, mid + 1, end);
		}
		return merge(myArr, start, mid + 1, end);
	}

	private static int[] merge(int[] myArr, int start, int start2, int end) {
		int n = end - start;
		// System.out.println("\nmerging n = "+n +" start="+start+" start2="+start2+"
		// end="+end);
		if (n == 0) {
			return myArr;
		}
		// Main.printArr(myArr);
		int[] lArr = Arrays.copyOfRange(myArr, start, start2);
		int[] rArr = Arrays.copyOfRange(myArr, start2, end + 1);
		// Main.printArr(lArr);
		// Main.printArr(rArr);
		int L = 0, R = 0;
		// System.out.println("*** start = "+start +" -> "+start2 + " end = "+end);
		for (int k = 0; k <= n; k++) {

			// System.out.println("FOR L = " + L + " R = " + R);

			if (L >= lArr.length) { // If L is out of range then push R
				// System.out.println("L OUT OF RANGE");
				myArr[start + k] = rArr[R];
				R++;
			} else {
				if (R < rArr.length) { // If both R and L are in range check
					// System.out.print("** in ranges ");
					if (lArr[L] < rArr[R]) { // L < R push L
						// System.out.print("** LLLL **\t");
						// System.out.println( lArr[L] +"<"+ rArr[R]);
						myArr[start + k] = lArr[L];
						L++;
					} else { // R < L push R
						// System.out.println("** RRRR **\t");
						// System.out.println( lArr[L] +">"+ rArr[R]);
						myArr[start + k] = rArr[R];
						R++;
					}
				} else { // R is out of range. push L
					// System.out.println("R OUT OF RANGE");
					myArr[start + k] = lArr[L];
					L++;
				}
			}
			// System.out.print("\t");
			// Main.printArr(myArr);
		}
		// Main.printArr(myArr);
		return myArr;
	}

	/*****************************************************************************/

	public static void QuickSort(int[] data, int lo, int hi) {
		// swaps = 0;
		// System.out.println("\nQuickSort");
		int height = MyQuickSort(data, lo, hi, 1); // call AUX-quick_Sort
		System.out.println("swaps= " + swaps + " height= " + height);
		// printArr(data, lo, hi);
	}

	private static int MyQuickSort(int[] data, int low, int high, int height) {
		// printArr(data, low, high);
		if (low >= high) // if the array is size 1 or 0
			return height + 1;
		if (high - low <= 40) { // if the array is size 40 or less
			myInsertionSort(data, low, high);
			return height + 1;
		}
		int mid = Partition(data, low, high); // put correct value in mid and partition
		height1 = MyQuickSort(data, low, mid - 1, height + 1); // sort upper half
		height2 = MyQuickSort(data, mid + 1, high, height + 1); // sort lower half
		if (height1 > height2) {
			return height1;
		} else
			return height2;
	}

	/**
	 * i should move right to make room for elements that are smaSller than the
	 * pivot fix to the left of i
	 */
	private static int Partition(int[] data, int low, int high) {
		// medianOf3(data, low, high);
		// randomPivot(data, low, high);
		// optimizeArray(data, low, high);

		int pivot = data[high];
		int i = low - 1;
		for (int j = low; j <= high - 1; j++) {
			if (data[j] < pivot) {
				swap(data, j, i + 1); // move smaller number to before bigger number
				i++; // make room for smaller number behind where pivot will go
			}
		}
		swap(data, high, i + 1); // swap pivot with i+1
		return i + 1; // this is the mid

	}

	private static void randomPivot(int[] data, int low, int high) {
		if (low + 1 > high)
			return;
		// printArr(data, low, high);
		swap(data, high, (rand.nextInt(high - low) + low));
		// printArr(data, low, high);
	}

	private static void medianOf3(int[] data, int low, int high) {
		if (high - low < 6) {
			randomPivot(data, low, high);
			return;
		}
		// System.out.println();
		// printArr(data, low, high);
		int first, second, third;
		first = (rand.nextInt(high - low) + low);
		second = (rand.nextInt(high - low) + low);
		third = (rand.nextInt(high - low) + low);
		// System.out.println(first + " " + second + " " + third);
		// System.out.println(data[first] + " " + data[second] + " " + data[third]);

		if (data[first] > data[third]) { // data[first] > second
			swap(data, first, third);
		}
		if (data[second] > data[third]) { // data[second] > third
			swap(data, second, third);
		} // third now contains the highest value
		if (data[first] < data[second]) { // data[first]<data[second] AKA second is middle

			// if(data[first] > data[second] && data[second] > data[third]) {
			// System.out.println("ERROR swapping second");
			// }
			// System.out.println(data[first] + " " + data[second] + " " + data[third]);
			swap(data, second, high);
		} else { // data[first]>data[second] AKA first is middle
			// if(data[first] < data[second] && data[first] > data[third]) {
			// System.out.println("ERROR swapping first");
			// }
			// System.out.println(data[first] + " " + data[second] + " " + data[third]);
			swap(data, first, high);
		}
		// printArr(data, low, high);
	}

	/****************************************************************************/

	private static void customQuickSort(int[] data, int low, int high) {
		swaps = 0;
		System.out.println("\ncustomQuickSort");
		int height = myCustomQuickSort(data, low, high, 1);
		System.out.println("swaps= " + swaps);
		System.out.println("height= " + height);
		// printArr(data, low, high);
	}

	private static int myCustomQuickSort(int[] data, int low, int high, int height) {
		// printArr(data, low, high);
		if (low >= high) {// if the array is size 1 or 0
			return height;
		}
		// if (high - low == 1) { // if the array is size 2
		// if (data[low] > data[high]) {
		// swap(data, low, high);
		// }
		// }
		if (high - low < 40) {
			myInsertionSort(data, low, high);
			return height;
		}
		// System.out.println("lowHigh: " + low + " " + high);
		// System.out.println();
		int mid = customPartition(data, low, high); // put correct value in mid and partition
		height1 = myCustomQuickSort(data, low, mid - 1, height + 1); // sort upper half
		height2 = myCustomQuickSort(data, mid + 1, high, height + 1); // sort lower half
		if (height1 > height2) {
			return height1;
		} else
			return height2;

	}

	/**
	 * i should move right to make room for elements that are smaller than the pivot
	 * fix to the left of i
	 * 
	 */
	private static int customPartition(int[] data, int low, int high) {
		// printArr(data, low, high);
		optimizeArray(data, low, high);
		// printArr(data, low, high);
		int pivot = data[high];
		// System.out.println("Pivot = " + pivot + "\n");
		int i = low;
		int j = high - 1;
		while (i < j) {
			while (i < j && data[i] <= pivot) {
				i++;
			}
			while (j > i && data[j] >= pivot) {
				j--;
			}
			if (i >= j) {
				break;
			}
			swap(data, i, j);
		}
		if (data[j] > pivot) {
			swap(data, j, high);
		} else {
			return j + 1;
		}
		return j;
	}

	/**
	 * array must be have at least 3 elements
	 * 
	 */

	private static void optimizeArray(int[] data, int low, int high) {
		int mid = (high + low) / 2;
		// System.out.println("optimize\n\t [" + data[low] + " " + data[mid] + " " +
		// data[high] + "]");
		if (data[low] > data[mid]) {
			swap(data, low, mid);
		}

		if (data[high] < data[mid]) {
			if (data[high] < data[low]) {
				swap(data, high, low);
			}
			// else it's already optimized
		} else {
			swap(data, high, mid);
		}

	}

	/*****************************************************************************/

	private static void customDualSort(int[] data, int low, int high) {
		swaps = 0;
		System.out.println("\ncustomQuickSort");
		int height = myCustomDualSort(data, low, high, 1);
		System.out.println("swaps= " + swaps);
		System.out.println("height= " + height);
	}

	private static int myCustomDualSort(int[] data, int low, int high, int height) {
		// System.out.println("\nnew quickSort: ");
		// printArr(data, low, high);

		if (high - low < 1) {// if the array is size 1 or 0 negative
			return height;
		}
		// if (high - low == 1) {
		// if (data[low] > data[high]) {
		// // System.out.println("swaping two");
		// swap(data, low, high);
		// // printArr(data, low, high);
		// return height;
		// }
		// }
		if (high - low < 40) {
			// MergeSort(data, low, high);
			myInsertionSort(data, low, high);
			// printArr(data, low, high);
			return height;
		}
		// optimizeArray2(data, low, high);
		// randTwoPivot(data, low, high);
		if (data[low] > data[high]) {
			swap(data, low, high);
		}
		// printArr(data, low, high);

		int pivot1 = data[low];
		int pivot2 = data[high];
		// System.out.println("Pivots = " + pivot1 + " and " + pivot2 + "\n");
		int x = low; // partition lower than pivot1
		int y = high; // partition greater than pivot2
		int i = x + 1;
		while (i < y) {
			// System.out.println("while: i= " + i + " x= " + x + " y= " + y);
			// printArr(data, x + 1, y - 1);
			if (data[i] < pivot1) {
				swap(data, i, x + 1);
				i++;
				x++;
			} else if (data[i] > pivot2) {
				swap(data, i, y - 1);
				y--;
			} else { // it's between the pivots
				i++;
			}
		}
		// printArr(data, x + 1, y - 1);
		// System.out.println("Parting\n");
		swap(data, low, x);
		swap(data, high, y);
		// printArr(data, x + 1, y - 1);
		height1 = myCustomDualSort(data, low, x - 1, height + 1);
		height2 = myCustomDualSort(data, x + 1, y - 1, height + 1);
		height3 = myCustomDualSort(data, y + 1, high, height + 1);
		if (height1 < height2) {
			maxH = height2;
		} else
			maxH = height1;
		if (maxH < height3) {
			maxH = height3;
		}
		return maxH;
	}

	private static void optimizeArray2(int[] data, int low, int high) {
		myInsertionSort(data, low, low + 4);
		swap(data, low, low + 1);
		swap(data, high, low + 3);
	}

	private static void optimizeArray3(int[] data, int low, int high) {
		swap(data, high, (rand.nextInt(high - low) + low));
		swap(data, high, (rand.nextInt(high - low) + low));
	}

	/****************************************************************************/

	public static int[] LibSort(int[] data, int size) {
		// Your code should simply call the JAVA library sorting function
		// System.out.println("LibSort");
		Arrays.sort(data);
		return data;
	}

	/*****************************************************************************/

	public static void swap(int[] data, int x, int y) {
		swaps++;
		// System.out.println("swap: " + data[x] + " " + data[y]);
		int temp = data[x];
		data[x] = data[y];
		data[y] = temp;
	}

	/*****************************************************************************/

	public static void printData(int[] data, int size, String title) {
		int i;

		System.out.print("\n");
		System.out.print(title);
		System.out.print("\n");
		for (i = 0; i < size; i++) {
			System.out.print(data[i]);
			System.out.print(" ");
			if (i % 10 == 9 && size > 10) {
				System.out.print("\n");
			}
		}
	}

	public static void printArr(String me, int[] myArr) {
		System.out.print(me);
		printArr(myArr);
	}

	public static void printArr(int[] myArr) {
		if (myArr.length == 0) {
			System.out.println("[ ]");
			return;
		} else if (myArr.length == 1) {
			System.out.println("[ " + myArr[0] + " ]");
			return;
		} else {
			System.out.print("[ " + myArr[0]);
			for (int i = 1; i < myArr.length; i++) {
				System.out.print(", " + myArr[i]);
			}
			System.out.println(" ]");
		}
	}

	public static void printArr(int[] myArr, int start, int stop) {
		boolean endFound = false;
		boolean startFound = false;

		for (int i = 0; i < myArr.length; i++) {

			if (i == start) {
				System.out.print(" (" + myArr[i]);
				startFound = true;
			} else
				System.out.print(" " + myArr[i]);
			if (i == stop) {
				System.out.print(") ");
				endFound = true;
			}
		}

		if (startFound == false) {
			System.out.print("( ");
		}
		if (endFound == false) {
			System.out.print(")");
		}
		System.out.print("\tstart= " + start + " stop= " + stop + "\n");

	}

}
