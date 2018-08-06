package assignment4;

public class QuickFracSack {
	KnapsackInstance inst;
	KnapsackSolution curSoln;

	int cap;
	float fracVal;
	float fracWght;
	// float fracWghtAllowed // this is always cap - soln.getWght();
	int pointer;
	double pRatio; // 0-.99999 / 0 means we can take the whole pointer item
	boolean maxUBFound;
	public float maxUB;

	QuickFracSack(KnapsackInstance newInst, KnapsackSolution newCurSoln) {
		this.inst = newInst;
		this.curSoln = newCurSoln;

		cap = inst.GetCapacity();

		fracVal = 0;
		fracWght = 0;
		pointer = 1;
		pRatio = 0; // 0-.99999 / 0 means we can take the whole pointer item
		maxUBFound = false;

	}

	/**
	 * There are only 4 possibilities 
	 * 
	 * 1 - fracSack + crntSoln wght is too big. / so
	 * we drop items until it's not too big anymore
	 * 
	 * 2 - frackSack + crntColn is too small. We need to add more wght / two types
	 * of small: 1. can fit the next item 2. can only fit some of the next item
	 * 
	 * 3 - it's just right
	 * 
	 * 4 - there is nothing more for the fracSack
	 * 
	 */
	public int getFracUB(int itemNum) {
		int totalWght = (int) (fracWght + curSoln.GetWeight());

		if(pointer > inst.GetItemCnt()) { // if pointer is too high, then set the pointer to the right itemNum. pValue must be 0 here
			System.out.println("The pointer is too high at: " + pointer + " wght=: " + fracWght + " frac starts at: "+itemNum);
			pointer--;
		}
		
		System.out.println("Finding Frac UB Total Wght: " + totalWght + " vs cap: " + cap + " ItemNum: "+itemNum);

		if (totalWght > cap) {
			System.out.println("Too Much Weight Total Wght: " + totalWght + " vs cap: " + cap);
			reduceFrac(itemNum);
		}

		if (cap - totalWght > 1) { // if more than 1 unit of wght can still fit, then we take it.

			addToFrac(itemNum);

		}

//		
		System.out.printf("	pointer= %d fracCap= %d NewRatio= %f fracVal=%d UB =%d\n", 
		pointer, (cap - (int) fracWght), pRatio, (int) fracVal, ((int) fracVal + curSoln.GetValue()));

//		System.out.printf("");
		return (int)fracVal;
	}

	/**
	 * Three steps: 1. If we already have a fraction of the pointer, just drop it -
	 * we'll add it back in an instant 2. Add whole items if they fit 3. Add a
	 * fraction of the item that doesn't fit
	 * 
	 */
	private void addToFrac(int itemNum) {

		if (pRatio != 0) { // if we have a fraction then just drop it
			reduceFrac(itemNum);
		}

		// if the next item fits, keep taking.
		while (fracWght + curSoln.GetWeight() + inst.GetItemWeight(pointer) <= cap) {

			fracWght += inst.GetItemWeight(pointer);
			fracVal += inst.GetItemValue(pointer);
			System.out.println("\tAdded item: " + pointer + " to fracSac");
			pointer++;
			
			if(pointer > inst.GetItemCnt() ) { // frac sac wants to hold more, but there's nothing left to get
				pRatio  =0;
				return;
			}
		}

		// Now we probably have a fraction of an item left so take the fraction

		pRatio = (cap - (fracWght + curSoln.GetWeight())) / inst.GetItemWeight(pointer);
		fracWght += inst.GetItemWeight(pointer) * pRatio;
		fracVal += inst.GetItemValue(pointer) * pRatio;
		
		if(maxUBFound==false) {
			maxUB =fracVal;
			System.out.println("!! MAX UB = " +maxUB + " !!");
		}
		maxUBFound=true;
		
		
	}

	/**
	 * knock items out of the fracSack until Fractional is small enough then will
	 * call find FracSac to fix over-correction
	 * 
	 */
	private void reduceFrac(int itemNum) {
		System.out.println("reducing frac");

		if (pRatio != 0) {
			System.out.println("dropped a fraction of item: " + pointer);

			fracWght -= inst.GetItemWeight(pointer) * pRatio;
			fracVal -= inst.GetItemValue(pointer) * pRatio;
			pRatio = 0;
		}

//		System.out.println("Too Much Weight Total Wght: " + fracWght + curSoln.GetWeight() + " vs cap: " + cap);

		// if the wght is too great, keep dropping.
		while (fracWght + curSoln.GetWeight() >= cap) {

			if (itemNum >= pointer) {
				pointer = itemNum + 1;
				System.out.println("\tfracSac is nothing with pointer= " + pointer);
				return;
			}

			fracWght -= inst.GetItemWeight(pointer);
			fracVal -= inst.GetItemValue(pointer);
			System.out.println("\tBooted item: " + pointer + " from fracSac");

			decPoint();
		}
	}

	/***********************************************************************/
	// Should result in no net Change of UB b/c curSoln will have the extra val and
	// wght so total wght won't change
	public void takeItem(int itemNum) {
		fracWght -= inst.GetItemWeight(itemNum);
		fracVal -= inst.GetItemValue(itemNum);
	}

	// Should result in no net Change of UB b/c curSoln will relieve the extra val
	// and wght so total wght won't change
	public void undoTake(int itemNum) {
		fracWght += inst.GetItemWeight(itemNum);
		fracVal += inst.GetItemValue(itemNum);
	}

	/***********************************************************************/

	/*
	 * This will result in a change b/c the wght and val is not taken by the curSoln
	 * and will be added to the frac next time
	 * 
	 * two possibilities 1. The item is not the slack item 2. The item is the slack
	 * item
	 * 
	 */
	public void dontTakeItem(int itemNum) {

		/*
		 * this will happen at a leaf node where the item was not taken. but the UB
		 * shouldn't be calculated at leaf nodes in the first place.
		 * 
		 * Slack item is dropped, so we need to pointer++ because the next item needs to
		 * be taken now
		 */
		if (itemNum >= pointer) {

			if (pRatio != 0) {

				fracWght = 0;
				fracVal = 0;
				pRatio = 0;

			} // else {
				// there is no fraction already taken, but the pointer is being dropped so our
				// frac is empty

			pointer = itemNum + 1; // pointer always i ahead of the solnEnd
//			System.out.println("f dntTake: new Pointer = " +pointer + " item Num " + itemNum);
		} // end if pointer

		/*
		 * now since the slack item isn't being dropped, then the slack(pointer) item will
		 * still be taken next time
		 * 
		 */
		else {
			fracWght -= inst.GetItemWeight(itemNum);
			fracVal -= inst.GetItemValue(itemNum);
		}

	}// end don't take

	/**
	 * This method should result in the fracWght + solnWght > cap It will need to be
	 * corrected at the next iteration of reduceFrac
	 * 
	 * our soln doesn't automatically take this wght and value.
	 * rather
	 * 
	 * @param itemNum
	 */
	public void undoDontTake(int itemNum) {
		if (pointer == itemNum + 1 && pRatio==0) { 	// If the frac is empty then the pointer 
			//needs to point 1 after the last itemNum
			System.out.println("fracSack is empty with: " + fracWght + " fracWght");

			pointer=itemNum;
			return;
		}
		fracWght += inst.GetItemWeight(itemNum);
		fracVal += inst.GetItemValue(itemNum);
		System.out.println("new fracSack: " + fracWght + " fracWght");


	} // end undo don't take
	private void decPoint(){
		pointer--;
		System.out.println("Pointer--   = "+pointer);

	}
	public void atLeaf(int itemNum) { //when we're at a leaf, the fracsack is empty and the pointer is out of bounds. 
		fracWght=0;
		fracVal=0;
		pRatio=0;
		pointer = itemNum;
	}

}// end class
