package sorts;

import java.util.Arrays;

public class Sort {

	public int[] mergesort(int[] myArr) {
		if(myArr.length <2) return myArr;
		
		int[] newArr = mergesort(myArr, 0, myArr.length-1); 
		Main.printArr(newArr);
		return newArr;
	}
	
	
	
	// end should represent the index of the last element
	public int[] mergesort(int[] myArr, int start, int end){
		//System.out.println("\nCall spliting start = " + start + " end = " +end);
		int mid = (end-start)/2+start;

		if((end-start) > 1 ) {

			mergesort (myArr, start, mid);
			mergesort (myArr, mid+1, end);
		}
		
		return merge(myArr, start, mid+1, end); 
	}
	
	
	
	
	
	private int[] merge(int[] myArr, int start, int start2, int end) {
		int n = end-start;
		//System.out.println("\nmerging n = "+n +" start="+start+" start2="+start2+" end="+end);
		if(n==0) {
			return myArr;
		}
		
		//Main.printArr(myArr);
		int[] lArr = Arrays.copyOfRange(myArr, start, start2);
		int[] rArr = Arrays.copyOfRange(myArr, start2,  end+1);
		//Main.printArr(lArr);
		//Main.printArr(rArr);

		int L=0, R=0;
		//System.out.println("*** start = "+start +" -> "+start2 + " end = "+end);

		for(int k=0; k <= n; k++) {

			//System.out.println("FOR  L = " + L + " R = " + R);
			
			if (L >=lArr.length) { // If L is out of range then push R
				//System.out.println("L OUT OF RANGE");
				myArr[start+k]=rArr[R];
				R++;
			} else {
				if ( R<rArr.length) { // If both R and L are in range check
					//System.out.print("** in ranges ");

					if (lArr[L] < rArr[R] ){ // L < R push L
					
						//System.out.print("** LLLL **\t");
						//System.out.println( lArr[L] +"<"+ rArr[R]);
						myArr[start+k]=lArr[L];
						L++;
					} else{ // R < L push R
						//System.out.println("** RRRR **\t");
						//System.out.println( lArr[L] +">"+ rArr[R]);
						myArr[start+k]=rArr[R];
						R++;
					}
				}else { // R is out of range. push L
					//System.out.println("R OUT OF RANGE");
					myArr[start+k]=lArr[L];
					L++;
				}
			}					
			//System.out.print("\t");

			//Main.printArr(myArr);

		}
		//Main.printArr(myArr);
		return myArr;
	}
}











