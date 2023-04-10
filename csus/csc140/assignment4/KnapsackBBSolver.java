
// Branch-and-Bound solver
public class KnapsackBBSolver extends KnapsackBFSolver {
	private UPPER_BOUND ub;
	private int totalSum;

	public KnapsackBBSolver(UPPER_BOUND ub_) {
		super();
		ub = ub_;
	}

	public void close() {

	}

	public void FindSolnsUB1(int itemNum) {
		// crntSoln.Print("BBSolver1 solution: " + String.valueOf(itemNum) + "\n");
		int itemCnt = inst.GetItemCnt();

		if (itemNum == itemCnt + 1) {
			CheckCrntSoln();
			return;
		}

		// If current best solution greater equal than the Upper Bound, stop.
		if (totalSum - crntSoln.getUntakenValue() <= bestSoln.GetValue()) {
			return;
		}

		if (USE_DONT_TAKE_FIRST) {
			crntSoln.DontTakeItem(itemNum);
			FindSolnsUB1(itemNum + 1);
			crntSoln.undoDontTakeItem(itemNum);
			crntSoln.TakeItem(itemNum);
			FindSolnsUB1(itemNum + 1);
			// NOTE: not really needed
			crntSoln.UndoTakeItem(itemNum);
		} else {
			crntSoln.TakeItem(itemNum);
			FindSolnsUB1(itemNum + 1);
			crntSoln.UndoTakeItem(itemNum);
			crntSoln.DontTakeItem(itemNum);
			FindSolnsUB1(itemNum + 1);
			crntSoln.undoDontTakeItem(itemNum);
		}
	}

	public void FindSolnsUB2(int itemNum) {
		// crntSoln.Print("BBSolver2 solution: " + String.valueOf(itemNum) + "\n");
		int itemCnt = inst.GetItemCnt();

		if (itemNum == itemCnt + 1) {
			CheckCrntSoln();
			return;
		}

		if (crntSoln.GetValue() + crntSoln.sumOfUndecidedThatFit(itemNum) <= bestSoln.GetValue()) {
			// System.out.print("Left side: " + crntSoln.GetValue() + " " +
			// crntSoln.sumOfUndecidedThatFit(itemNum));
			// System.out.print(" Right side: " + bestSoln.GetValue() + "\n");
			return;
		}

		if (USE_DONT_TAKE_FIRST) {
			crntSoln.DontTakeItem(itemNum);
			FindSolnsUB2(itemNum + 1);
			crntSoln.undoDontTakeItem(itemNum);
			crntSoln.TakeItem(itemNum);
			FindSolnsUB2(itemNum + 1);
			crntSoln.UndoTakeItem(itemNum);
		} else {
			crntSoln.TakeItem(itemNum);
			FindSolnsUB2(itemNum + 1);
			crntSoln.UndoTakeItem(itemNum);
			crntSoln.DontTakeItem(itemNum);
			FindSolnsUB2(itemNum + 1);
			crntSoln.undoDontTakeItem(itemNum);
		}
	}

	public void FindSolnsUB3(int itemNum) {
		// crntSoln.Print("BBSolver3 solution: " + String.valueOf(itemNum) + "\n");

		int itemCnt = inst.GetItemCnt();

		if (itemNum == itemCnt + 1) {
			CheckCrntSoln();
			return;
		}

		if (crntSoln.GetValue() + inst.Fractional(itemNum, crntSoln.getRemainingCap()) <= bestSoln.GetValue()) {
			// System.out.print("Left side: " + crntSoln.GetValue() + " " +
			// inst.Fractional(itemNum, crntSoln.getRemainingCap()));
			// System.out.print(" Right side: " + bestSoln.GetValue() + "\n");
			return;
		}

		if (USE_DONT_TAKE_FIRST) {
			crntSoln.DontTakeItem(itemNum);
			FindSolnsUB3(itemNum + 1);
			crntSoln.undoDontTakeItem(itemNum);
			crntSoln.TakeItem(itemNum);
			FindSolnsUB3(itemNum + 1);
			crntSoln.UndoTakeItem(itemNum);
		} else {
			crntSoln.TakeItem(itemNum);
			FindSolnsUB3(itemNum + 1);
			crntSoln.UndoTakeItem(itemNum);
			crntSoln.DontTakeItem(itemNum);
			FindSolnsUB3(itemNum + 1);
			crntSoln.undoDontTakeItem(itemNum);
		}
	}

	// Extra work - optimized UB3 with BackTracking and O(1) FractionalFast
	public void FindSolnsUBExtra(int itemNum) {
		// crntSoln.Print("BBSolverExtra solution: " + String.valueOf(itemNum) + "\n");

		int itemCnt = inst.GetItemCnt();

		if (itemNum == itemCnt + 1) {
			CheckCrntSoln();
			return;
		}

		// Return early similar to back tracking
		if (crntSoln.getWeight() > inst.GetCapacity()) {
			// crntSoln.Print("BTSolver solution weight is: " + String.valueOf(crntSoln.getWeight()) + "\n");
			return;
		}
		
		// Return if fractional upper bound is too small
		if (crntSoln.GetValue() + inst.FractionalFast(itemNum, crntSoln.getRemainingCap()) <= bestSoln.GetValue()) {
			// System.out.print("Left side: " + crntSoln.GetValue() + " " +
			// inst.FractionalFast(itemNum, crntSoln.getRemainingCap()));
			// System.out.print(" Right side: " + bestSoln.GetValue() + "\n");
			return;
		}

		if (USE_DONT_TAKE_FIRST) {
			crntSoln.DontTakeItem(itemNum);
			FindSolnsUBExtra(itemNum + 1);
			crntSoln.undoDontTakeItem(itemNum);
			crntSoln.TakeItem(itemNum);
			FindSolnsUBExtra(itemNum + 1);
			crntSoln.UndoTakeItem(itemNum);
		} else {
			crntSoln.TakeItem(itemNum);
			FindSolnsUBExtra(itemNum + 1);
			crntSoln.UndoTakeItem(itemNum);
			crntSoln.DontTakeItem(itemNum);
			FindSolnsUBExtra(itemNum + 1);
			crntSoln.undoDontTakeItem(itemNum);
		}

	}

	@Override
	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_) {
		inst = inst_;
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		// Compute total value of items just once.
		// Needed by UB1.
		totalSum = inst_.GetAllItemsValue();

		if (ub == UPPER_BOUND.UB1) {
			FindSolnsUB1(1);
		} else if (ub == UPPER_BOUND.UB2) {
			FindSolnsUB2(1);
		} else if (ub == UPPER_BOUND.UB3) {
			FindSolnsUB3(1);
		} else if (ub == UPPER_BOUND.UBEXTRA) {
			FindSolnsUBExtra(1);
		} else {
			throw new IllegalArgumentException("Invalid UB algorithm choice.");
		}
	}
}
