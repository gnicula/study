import java.io.IOError;
import java.util.*;

public class KnapsackSolution implements java.io.Closeable
{
	private boolean [] isTaken;
	private int value;
	private int wght;
	private KnapsackInstance inst;
	private int untakenValue;
	
	public KnapsackSolution(KnapsackInstance inst_)
	{
		int i;
		int itemCnt = inst_.GetItemCnt();
    
		inst = inst_;
		isTaken = new boolean[itemCnt + 1];
		value = DefineConstants.INVALID_VALUE;
		wght = 0;
    
		for (i = 1; i <= itemCnt; i++)
		{
			isTaken[i] = false;
		}
	}
	
	public void close()
	{
		isTaken = null;
	}

	public void TakeItem(int itemNum)
	{
		isTaken[itemNum] = true;
		wght += inst.GetItemWeight(itemNum);
		if (value == DefineConstants.INVALID_VALUE) {
			value = inst.GetItemValue(itemNum);
		} else {
			value += inst.GetItemValue(itemNum);
		}
	}

	public void UndoTakeItem(int itemNum)
	{
		wght -= inst.GetItemWeight(itemNum);
		value -= inst.GetItemValue(itemNum);
	}

	public void DontTakeItem(int itemNum)
	{
		isTaken[itemNum] = false;
		if (value == DefineConstants.INVALID_VALUE) {
			value = 0;
		} else {
			value -= inst.GetItemValue(itemNum);
		}
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
				if (weight > inst.GetCapacity())
				{
					value = DefineConstants.INVALID_VALUE;
					break;
				}
				value += inst.GetItemValue(i);
			}
		}

		wght = weight;
		return value;
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