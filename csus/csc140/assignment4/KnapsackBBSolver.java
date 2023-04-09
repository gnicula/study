
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

	// public void FindSolns(int itemNum)
	// {
	// 	crntSoln.Print("BBSolver solution: \n");

	// 	int itemCnt = inst.GetItemCnt();
    
	// 	if (itemNum == itemCnt + 1)
	// 	{
	// 		CheckCrntSoln();
	// 		return;
	// 	}
		
	// 	if (totalSum - crntSoln.getUntakenValue() <= bestSoln.GetValue()) {
	// 		return;
	// 	}
	// 	crntSoln.DontTakeItem(itemNum);
	// 	FindSolns(itemNum + 1);
	// 	crntSoln.TakeItem(itemNum);
	// 	FindSolns(itemNum + 1);
	// }

/* 
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
		FindSolns(1);
	}
*/
}