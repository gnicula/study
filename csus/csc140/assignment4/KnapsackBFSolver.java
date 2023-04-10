import java.util.*;

// Brute-force solver
public class KnapsackBFSolver implements java.io.Closeable
{
	/**
	 *
	 */
	protected KnapsackInstance inst;
	protected KnapsackSolution crntSoln;
	protected KnapsackSolution bestSoln;

	// Constant to switch between TakeItem and DontTakeItem first
	// If true, DontTakeItem will be used first in the recursion.
	// Note: Used by all Solvers extending KnapsackBFSolver.
	public static final boolean USE_DONT_TAKE_FIRST = false;

	public KnapsackBFSolver()
	{
		crntSoln = null;
	}

	public void FindSolns(int itemNum)
	{
		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		
		if (USE_DONT_TAKE_FIRST)
		{
			crntSoln.DontTakeItem(itemNum);
			FindSolns(itemNum + 1);
			crntSoln.TakeItem(itemNum);
			FindSolns(itemNum + 1);
		}
		else
		{
			crntSoln.TakeItem(itemNum);
			FindSolns(itemNum + 1);
			crntSoln.DontTakeItem(itemNum);
			FindSolns(itemNum + 1);
		}

	}

	public void CheckCrntSoln()
	{
		int crntVal = crntSoln.ComputeValue();
		// System.out.print("\nChecking solution ");
		// crntSoln.Print(" ");

		if (crntVal == DefineConstants.INVALID_VALUE)
		{
			crntSoln.FixValue();
			return;
		}
     	//The first solution is initially the best solution
		if (bestSoln.GetValue() == DefineConstants.INVALID_VALUE)
		{
			bestSoln.Copy(crntSoln);
		}
		else
		{
			if (crntVal > bestSoln.GetValue())
			{
				bestSoln.Copy(crntSoln);
			}
		}
	}

	public void close()
	{
		if (crntSoln != null)
		{
			crntSoln = null;
		}
	}

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		inst = inst_;
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		FindSolns(1);
	}
}