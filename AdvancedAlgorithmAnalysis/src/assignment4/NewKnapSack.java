package assignment4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NewKnapSack {

	private static int itemCnt;
	static boolean go;
	static long startTime;
	static long elapsed;
	static ArrayList<Long> myTimes = new ArrayList<Long>();
	static Scanner reader = new Scanner(System.in);

	static boolean justRunTheThingOnce;
	static boolean useSaveInst;
	static ArrayList<Integer> savedWghts;

	// Times
	static float speedup;

	// Solns
	static KnapsackInstance inst;
	static KnapsackSolution keySoln;
	static KnapsackSolution nextSoln;

	// solvers
	static KnapsackDPSolver DPSolver;
	static KnapsackBFSolver BFSolver;
	static KnapsackBTSolver BTSolver;

	static KnapsackBBSolver BBSolver1;
	static KnapsackBBSolver BBSolver2;
	static KnapsackBBSolver BBSolver3;

	static CustomSolver2 customSolver1;
	static KnapsackSolver keySolver;

	public static void main(String[] args) {
		System.out.println("Starting the Knapsack Thing");
		justRunTheThingOnce = true;
		useSaveInst = false;
//		savedWghts = new ArrayList<Integer>(Arrays.asList(9, 28, 45, 95, 99));
		savedWghts = new ArrayList<Integer>(Arrays.asList(36,59,82,98,100));


		itemCnt = 700;

		instantiateEverything();

		keySolver = DPSolver;

		go = true;
		while (go) {
			instantiateEverything();

			KnapsackSolver nextSolver = readInput();
			newInstWithKey(keySolver);

			if (nextSolver != null) {
				SolveWithAndCheck(nextSolver);
			}

			System.out.print("\n\nTrial Completed Successfully\n");

			if (justRunTheThingOnce)
				return;
		}

	}

	private static void instantiateEverything() {
		if (!useSaveInst) {
			inst = new KnapsackInstance(itemCnt);
		} else {
			inst = new KnapsackInstance(savedWghts);
		}
		// Solns
		keySoln = new KnapsackSolution(inst);
		nextSoln = new KnapsackSolution(inst);

		// Solvers
		DPSolver = new KnapsackDPSolver(); // dynamic programming solver
		BFSolver = new KnapsackBFSolver(); // brute-force solver
		BTSolver = new KnapsackBTSolver(); // backtracking solver
		BBSolver1 = new KnapsackBBSolver(UPPER_BOUND.UB1); // branch-and-bound solver with UB1
		BBSolver2 = new KnapsackBBSolver(UPPER_BOUND.UB2); // branch-and-bound solver with UB2
		BBSolver3 = new KnapsackBBSolver(UPPER_BOUND.UB3); // branch-and-bound solver with UB3

		customSolver1 = new CustomSolver2();
	}

	/**
	 * creates the key soln
	 * 
	 * @param solver
	 */
	private static void solveKeyWith(KnapsackSolver solver) {
		startTime = System.nanoTime();
		solver.Solve(inst, keySoln);
		elapsed = System.nanoTime() - startTime;
		myTimes.add(0, (Long) (elapsed / 1000000));
		System.out.println("\n\nSolved using " + solver.getName() + " in " + getKeyTime() + "ms Optimal value = "
				+ keySoln.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT) {
			keySoln.Print("" + keySolver.getName());
		}
	}

	private static void SolveWith(KnapsackSolver solver) {
		startTime = System.nanoTime();
		solver.Solve(inst, nextSoln);
		elapsed = System.nanoTime() - startTime;
		myTimes.add((Long) (elapsed / 1000000));
		System.out.println("\n\nSolved using " + solver.getName() + " in " + getRecentTime() + "ms Optimal value = "
				+ nextSoln.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT) {
			nextSoln.Print("" + solver.getName());
		}
	}

	private static void SolveWithAndCheck(KnapsackSolver solver) {
		nextSoln = new KnapsackSolution(inst);
		startTime = System.nanoTime();
		solver.Solve(inst, nextSoln); // always use nextSoln
		elapsed = System.nanoTime() - startTime;
		myTimes.add(1, (Long) (elapsed / 1000000));

		System.out.println("\n\nSolved using the " + solver.getName() + " algorithm in " + getTime()
				+ "ms Optimal value = " + nextSoln.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT) {
			nextSoln.Print("" + solver.getName() + " Solution");
//			System.out.print("\n(items might not match if sort was used)\n");
			keySoln.Print("KEY: " + keySolver.getName() + " Solution");

		}
		if (nextSoln.equalsTo(keySoln)) {
			System.out.print("\nSUCCESS: " + solver.getName() + " and " + keySolver.getName() + " solutions MATCH: "
					+ nextSoln.GetValue() + " and " + keySoln.GetValue());
		} else {
			System.out.print("\nERROR: " + solver.getName() + " and " + keySolver.getName() + " solutions MISMATCH: "
					+ nextSoln.GetValue() + " and " + keySoln.GetValue());
		}
		speedup = (float) ((float) getTime() == 0 ? 0 : 100.0 * (getKeyTime() - getTime()) / (float) getKeyTime());
		System.out.printf("\nSpeedup of " + solver.getName() + " relative to " + keySolver.getName() + " is " + speedup
				+ " percent\n");
	}

	private static long getRecentTime(){
		return myTimes.get(myTimes.size()-1);
	}
	private static long getTime() {
		return myTimes.get(1);
	}

	private static long getKeyTime() {
		return myTimes.get(0);
	}

	@SuppressWarnings("resource")
	public static KnapsackSolver readInput() {
		System.out.println("\nD - Dynamic  ");
		System.out.println("F - brute FORCE ");
		System.out.println("T - back TRACK ");
		System.out.println("1 - UB1  ");
		System.out.println("2 - UB2  ");
		System.out.println("3 - UB3  ");
		System.out.println("C - Custom  ");
		System.out.println("ZZZ - Run Average ");

		System.out.println("Enter which algorithm:");

		String nextIn = reader.next();
		System.out.println(nextIn);

		if (nextIn.equals("d")) {
			return DPSolver;
		} else if (nextIn.equals("f")) {
			return BFSolver;
		} else if (nextIn.equals("t")) {
			return BTSolver;
		} else if (nextIn.equals("1")) {
			return BBSolver1;
		} else if (nextIn.equals("2")) {
			return BBSolver2;

		} else if (nextIn.equals("3")) {
			return BBSolver3;

		} else if (nextIn.equals("c")) {
			return customSolver1;
		} else if (nextIn.equals("zzz")) {
			getAvgOf();
			return null;
		}

		else if (nextIn.equals("stop")) {
			go = false;
			System.out.println("stopping");
			System.exit(1);
		} else {
			System.out.println("Unrecognized sorting algorithm Code: " + nextIn + " \nTry again");
			return null;
		}
		// System.out.println("Enter Knapsack size: ");
		// int size = reader.nextInt();
		// System.out.println("\nKnapsack Algorithm: " + KnapsackAlg);
		// System.out.println("\nKnapsack Size = " + itemCnt);
		return null;
	}

	public static void getAvgOf() {
		myTimes.clear();
		System.out.println("\nInput the number trials to average: ");
		int num = reader.nextInt();
		KnapsackSolver nextSolver = readInput();
		int i = num;
		while (i > 0) {
			newInst();
			SolveWith(nextSolver);
			i--;
		}
		findAverage(num);
	}

	private static void findAverage(int num) {
		int stopIndex = (myTimes.size() - 1) - num;
		int i = myTimes.size() - 1;
		Long total = (long) 0;
		while (i > stopIndex) {
			total += myTimes.get(i);
			i--;
		}
		long average = total / num;
		System.out.println("\n### The average of " + num + " trials is: " + average + "ms### \n\n");
		System.exit(1);
	}

	private static void newInst() {
		instantiateEverything();
//		if (!useSaveInst) {
			inst.Generate();
			inst.Print();
//		}
	}

	private static void newInstWithKey(KnapsackSolver solver) {
		instantiateEverything();
//		if (!useSaveInst) {
			inst.Generate();
			inst.Print();
//		}
		solveKeyWith(solver);
	}
}
