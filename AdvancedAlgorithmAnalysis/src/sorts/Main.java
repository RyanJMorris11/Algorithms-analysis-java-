package sorts;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {
	
	
	public static void main(String []args) {
		
		int[] myArr = {6,3,4,9,5,2,1,0};
		int[] myArr2 = {6,3,2,1};

		Sort MS = new Sort();
		
		//printArr(MS.mergesort(myArr));	
		//printArr(MS.mergesort(myArr2));	
		testSortingMethod(MS::mergesort);
	} 
	
	
	
	
	
	
	
	static Random rand = new Random();
	
	public static boolean checkSorted(int[] myArr) {
		
		for(int i=0; i<= myArr.length-2 ; i++) {
			if(myArr[i] > myArr[i+1]) {
				System.out.println("ERROR- NOT SORTED");
				return false;
			}		
		}				
		System.out.println("CORRECTLY SORTED");
		return true;
	}
		
	
	public static int[] genRandArr(int n) {
		
		
		int[] newArr = new int[n];
		if (n==0) {
			return newArr;
		}
		int nextnum=1;
		
		for (int i=1; i<n; i++) {
			nextnum += rand.nextInt(n*n/(n*2))+1;
			newArr[i]=nextnum;	
		}
		
	shuffle(newArr);
	printArr("ShuffledRandomArray\n",newArr);
	return newArr;
	}
	
	public static void printArr(String me,int[] myArr) {
	System.out.print(me);
	printArr(myArr);
	}
	
	public static void printArr(int[] myArr) {
		if(myArr.length == 0) {
			System.out.println("[ ]");
			return;
		} else if (myArr.length == 1) {
			System.out.println("[ " +myArr[0]+ " ]");
			return;
		}else {
			System.out.print("[ " + myArr[0]);
			for(int i=1 ; i< myArr.length ; i++) {
				System.out.print(", " + myArr[i]);
			}
			System.out.println(" ]");
		}
	}
	
	public static int[] shuffle(int[] myArr) {
		int temp;
		int swapInd;
		for(int i=0 ; i<myArr.length ; i++) {
			swapInd = rand.nextInt(myArr.length-1);
			temp = myArr[i];
			myArr[i] = myArr[swapInd];
			myArr[swapInd] = temp; 
		}
		return myArr;
	}
	

	@SuppressWarnings("unchecked")
	public static boolean testSortingMethod(Function<int[], int[]>  mysort){
		int n;
		
		for (int i=0; i<=15 ; i++) {
			System.out.println("check =" +(i=1));
			n = rand.nextInt(i*i+1);
			int[] x= genRandArr(n);
			
			if (checkSorted(mysort.apply(x)) ) {
				return false;
			}
		}
		
		
		System.out.println("No problems found.");
		return true;
	
	}	
}



















