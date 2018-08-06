package assignment4;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class FractionalSack {
	float value;
	float sacWght;
	int pointer;
	int itemCnt;
	double pRatio;
	int cap;
	KnapsackInstance inst;
	 Deque<Integer> stack = new ArrayDeque<Integer>();
	 
	public FractionalSack(int newCap, KnapsackInstance newInst) {
		value = 0;
		sacWght = 0;
		pointer = 1;
		pRatio = 0;
		this.cap = newCap;
//		skipItem = new Boolean[cap + 1];
//		Arrays.fill(skipItem, false);
		this.inst = newInst;
		this.itemCnt = inst.GetItemCnt();
	}

//	public int findFrac() {
//		value = 0;
//		pointer = 1;
//		pRatio = 0;
//		value = 0;
//		sacWght = 0;
//		pRatio = 0;
//		cap = inst.GetCapacity();
//
//		recFractional();
//		// for (int i = 1; i < itemCnt + 1; i++) {
//		//
//		// if (inst.GetItemWeight(i) <= cap) {
//		//
//
//		// } else {
//		//
////		if (sacWght != cap) {
////			System.out.println("ERROR, FRACTIONAL IS WRONG: wght= " + sacWght + " cap= " + cap);
////		}
//		// }
//		// }
//		return (int)value;
//	}

	/**
	 * Finds a fractional sack on the remaining items with a certain Cap left.
	 * 
	 * @param capLeft
	 * @param itemNum
	 * @return
	 */
	public float findFracAt(int capLeft, int itemNum) {
//		System.out.println("Find  FRACTIONAL at item= " + itemNum);

		value = 0;
		sacWght = 0;
		pointer = itemNum;
		pRatio = 0;
		this.cap = capLeft;
//		System.out.println("cap= " +cap);

		recFractional();
		// for (int i = 1; i < itemCnt + 1; i++) {
		//
		// if (inst.GetItemWeight(i) <= cap) {
		//
		// } else {
		//
		if (sacWght != cap && pointer >= itemCnt + 1) {
//			System.out.println("ERROR, FRACTIONAL IS WRONG: Sac Weight= " + (int)sacWght + " cap= " + cap);
		}else {
//			System.out.println("Correct Wght: Sac Weight= " + sacWght + " cap= " + cap +" Item Num "+ itemNum+" Frac Bound= " + value);
		}
		// }
		// }
		
		return value;
	}

	/**
	 * Take items until you can't anymore
	 * 
	 * @param pointerItemNum
	 */
	private void recFractional() {

		if (sacWght >= cap) {
//			System.out.println("cap= " +cap +" SacWght+ "+ (int)sacWght);
			return;
		}
		if(pointer >= itemCnt + 1) {
//			System.out.println("pointer= " +pointer);
			return;
		}

		if (inst.GetItemWeight(pointer) > cap - sacWght) { // if can't fit all the way

			pRatio = (double) (cap - sacWght) / inst.GetItemWeight(pointer);
			
			value += (int)(pRatio * (double)inst.GetItemValue(pointer));
			sacWght += (int)(pRatio * (double)inst.GetItemWeight(pointer));
			
//			System.out.printf("itemWght= %d capleft= %d Ratio= %f \n", inst.GetItemWeight(pointer), (cap - (int)sacWght),
//					pRatio);
			return;
		} else { // the whole Item fits

			value += inst.GetItemValue(pointer);
			sacWght += inst.GetItemWeight(pointer);
			pointer++;
			pRatio = 0;
//			System.out.println("ln 116: cap= " +cap);
			recFractional();
		}

	}
	/*
	 * if (inst.GetItemWeight(pointer) > cap - (fracWght +crntSoln.GetWeight() )) { // if can't fit all the way
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
	 */

	public void removeFromFrac(int i) {
		sacWght -= inst.GetItemWeight(i);
		if (pRatio != 0) { // get rid of a taken fraction and start over

			// Don't decrement pointer here(we take this item next)

			sacWght -= pRatio * inst.GetItemValue(pointer); // remove any of pointer from fractional
			value -= pRatio * inst.GetItemValue(pointer);
			pRatio = 0;
		}
		recFractional();
	}

//	public void skipItem(int item) {
//		skipItem[item] = true;
//	}
//
//	public void undoSkipItem(int item) {
//		skipItem[item] = false;
//
//	}

}
