import java.util.*;

// Backtracking solver
public class KnapsackBTSolver extends KnapsackBFSolver
{

	public KnapsackBTSolver()
	{
		super();
	}

	@Override
	public void FindSolns(int itemNum)
	{
		// crntSoln.Print("BTSolver solution: \n");
		// crntSoln.GetValue();
		if (crntSoln.getWeight() > inst.GetCapacity()) {
			return;
		}

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		crntSoln.DontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.TakeItem(itemNum);
		FindSolns(itemNum + 1);
	}

	// public void close()
	// {
    
	// }

	// public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	// {
	// 	if ()
    
	// }
}