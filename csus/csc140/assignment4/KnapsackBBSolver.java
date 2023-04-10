
// Branch-and-Bound solver
public class KnapsackBBSolver extends KnapsackBFSolver
{
	protected UPPER_BOUND ub;
	private int totalSum;

	public KnapsackBBSolver(UPPER_BOUND ub_)
	{
		super();
		ub = ub_;
	}

	public void close()
	{
    
	}

	public void FindSolnsUB1(int itemNum)
	{
		crntSoln.Print("BBSolver1 solution: \n");

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		
		// If current best solution greater equal than the Upper Bound, stop.
		if (totalSum - crntSoln.getUntakenValue() <= bestSoln.GetValue()) {
			return;
		}
		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB1(itemNum + 1);
		crntSoln.undoDontTakeItem(itemNum);
		crntSoln.TakeItem(itemNum);
		FindSolnsUB1(itemNum + 1);
	}

	public void FindSolnsUB2(int itemNum) {
		crntSoln.Print("BBSolver2 solution: \n");

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		
		if (crntSoln.GetValue() + crntSoln.sumOfUndecidedThatFit(itemNum) <= bestSoln.GetValue()) {
			System.out.print("Left side: " + crntSoln.GetValue() + " " + crntSoln.sumOfUndecidedThatFit(itemNum));
			System.out.print("Right side: " + bestSoln.GetValue());
			if (crntSoln.GetValue() == DefineConstants.INVALID_VALUE) {
				CheckCrntSoln();
			}
			return;
		}
		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB2(itemNum + 1);
		if (crntSoln.GetValue() != DefineConstants.INVALID_VALUE) {
			crntSoln.undoDontTakeItem(itemNum);
		}
		crntSoln.TakeItem(itemNum);
		FindSolnsUB2(itemNum + 1);	
		if (crntSoln.GetValue() != DefineConstants.INVALID_VALUE) {
			crntSoln.UndoTakeItem(itemNum);	
		}
	}

	public void FindSolnsUB3(int itemNum) {
		crntSoln.Print("BBSolver3 solution: \n");

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		
		if (crntSoln.GetValue() + inst.Fractional(itemNum, crntSoln.getRemainingCap()) <= bestSoln.GetValue()) {
			System.out.print("Left side: " + crntSoln.GetValue() + " " + inst.Fractional(itemNum, crntSoln.getRemainingCap()));
			System.out.print("Right side: " + bestSoln.GetValue());
			return;
		}
		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB3(itemNum + 1);
		if (crntSoln.GetValue() != DefineConstants.INVALID_VALUE) {
			crntSoln.undoDontTakeItem(itemNum);
		}
		crntSoln.TakeItem(itemNum);
		FindSolnsUB3(itemNum + 1);
		if (crntSoln.GetValue() != DefineConstants.INVALID_VALUE) {
			crntSoln.UndoTakeItem(itemNum);
		}
	}

	@Override
	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		// Compute total value of items just once.
		totalSum = 0;
		for (int i = 1; i <= inst_.GetItemCnt(); ++i) {
			totalSum += inst_.GetItemValue(i);
		}

		inst = inst_;
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		if (ub == UPPER_BOUND.UB1) {
			FindSolnsUB1(1);
		} else if (ub == UPPER_BOUND.UB2) {
			FindSolnsUB2(1);
		} else if (ub == UPPER_BOUND.UB3) {
			FindSolnsUB3(1);
		}
	}

}