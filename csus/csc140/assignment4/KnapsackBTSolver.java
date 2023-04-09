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
		// crntSoln.Print("BTSolver solution when itemNum is: " + String.valueOf(itemNum) + "\n");
		// int n = crntSoln.GetValue();
		// crntSoln.Print("BTSolver solution value is: " + String.valueOf(n) + "\n");
		if (crntSoln.getWeight() > inst.GetCapacity()) {
			// crntSoln.Print("BTSolver solution weight is: " + String.valueOf(crntSoln.getWeight()) + "\n");
			return;
		}

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}

		crntSoln.DontTakeItem(itemNum);
		this.FindSolns(itemNum + 1);
		crntSoln.TakeItem(itemNum);
		this.FindSolns(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);
	}

}