import java.io.IOError;
import java.util.*;

public class KnapsackSolution implements java.io.Closeable
{
	private boolean [] isTaken;
	private int value;
	private int wght;
	private KnapsackInstance inst;
	private int untakenValue;
	private int remainingCap;

	public KnapsackSolution(KnapsackInstance inst_)
	{
		int i;
		int itemCnt = inst_.GetItemCnt();
    
		inst = inst_;
		isTaken = new boolean[itemCnt + 1];
		value = DefineConstants.INVALID_VALUE;
		wght = 0;
		untakenValue = 0;
		// NOTE: the KnapsackInstances passed to solutions constructors have 0 capacity
		remainingCap = inst.GetCapacity();
		// System.out.println("Knapsack solution remaining cap: " + remainingCap);
    
		for (i = 1; i <= itemCnt; i++)
		{
			isTaken[i] = false;
		}
	}
	
	public void close()
	{
		isTaken = null;
	}

	// Update the current load 'wght', remaining capacity and value in θ(1) time at each node.
	public void TakeItem(int itemNum)
	{
		// System.out.print("Take " + itemNum + " value " + value + " rcap " + remainingCap + "\n");
		isTaken[itemNum] = true;
		wght += inst.GetItemWeight(itemNum);
		remainingCap -= inst.GetItemWeight(itemNum);
		if (value == DefineConstants.INVALID_VALUE) {
			value = inst.GetItemValue(itemNum);
		} else {
			value += inst.GetItemValue(itemNum);
		}
		// System.out.print("Take " + itemNum + " value " + value + " rcap " + remainingCap + "\n");
	}

	// Update the current load 'wght', remaining capacity and value in θ(1) time at each node.
	public void UndoTakeItem(int itemNum)
	{
		// System.out.print("UndoTake " + itemNum + " value " + value + " rcap " + remainingCap + "\n");
		wght -= inst.GetItemWeight(itemNum);
		remainingCap += inst.GetItemWeight(itemNum);
		value -= inst.GetItemValue(itemNum);
		// System.out.print("UndoTake " + itemNum + " value " + value + " rcap " + remainingCap + "\n");
	}

	// Update the current untaken value in θ(1) time for UB1.
	public void DontTakeItem(int itemNum)
	{
		isTaken[itemNum] = false;
		untakenValue += inst.GetItemValue(itemNum);
		if (value == DefineConstants.INVALID_VALUE) {
			value = 0;
		} else {
			// value -= inst.GetItemValue(itemNum);
		}
	}
	
	// Update the current untaken value in θ(1) time for UB1.
	public void undoDontTakeItem(int itemNum) {
		untakenValue -= inst.GetItemValue(itemNum);
	}

	public int ComputeValue()
	{
		int i;
		int itemCnt = inst.GetItemCnt();
		int weight = 0;
    
		value = 0;
		for (i = 1; i <= itemCnt; i++)
		{
			if (isTaken[i] == true)
			{
				weight += inst.GetItemWeight(i);
				value += inst.GetItemValue(i);
			}
		}
		if (weight > inst.GetCapacity())
		{
			value = DefineConstants.INVALID_VALUE;
		}

		wght = weight;
		remainingCap = inst.GetCapacity() - weight;
		return value;
	}

	public void FixValue()
	{
		int i;
		int itemCnt = inst.GetItemCnt();
		value = 0;
		for (i = 1; i <= itemCnt; i++)
		{
			if (isTaken[i] == true)
			{
				value += inst.GetItemValue(i);
			}
		}
	}

	public int GetValue()
	{
		return value;
	}

	public int getWeight()
	{
		return wght;
	}

	public int getUntakenValue() {
		return untakenValue;
	}

	public int getRemainingCap() {
		return remainingCap;
	}

	// Straightforward O(n) time per node implementation.
	// Check all left undecided items that individually fit and compute sum.
	public int sumOfUndecidedThatFit(int itemNum) {
		
		int sum = 0;
		for (int i = itemNum; i <= inst.GetItemCnt(); ++i) {
			if (inst.GetItemWeight(i) <= remainingCap) {
				sum += inst.GetItemValue(i);
			}
		}
		return sum;
	}

	public void Print(String title)
	{
		int i;
		int itemCnt = inst.GetItemCnt();
    
		System.out.printf("\n%s: ",title);
		for (i = 1; i <= itemCnt; i++)
		{
			if (isTaken[i] == true)
			{
				System.out.printf("%d ",i);
			}
		}
		System.out.printf("\nValue = %d\n",value);
    
	}
	
	public void Copy(KnapsackSolution otherSoln)
	{
		int i;
		int itemCnt = inst.GetItemCnt();
    
		for (i = 1; i <= itemCnt; i++)
		{
			isTaken[i] = otherSoln.isTaken[i];
		}
		value = otherSoln.value;
	}
	
	public boolean equalsTo (KnapsackSolution otherSoln)
	{
		return value == otherSoln.value;
	}
	
	public void dispose()
	{
		isTaken = null;
	}	
}