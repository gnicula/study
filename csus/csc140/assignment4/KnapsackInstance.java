import java.util.*;

public class KnapsackInstance implements java.io.Closeable
{
	private int itemCnt; //Number of items
	private int cap; //The capacity
	private int[] weights; //An array of weights
	private int[] values; //An array of values
	private int[] sortedIndexesOfValuesPerWeight; 

	public KnapsackInstance(int itemCnt_)
	{
		itemCnt = itemCnt_;

		weights = new int[itemCnt + 1];
		values = new int[itemCnt + 1];
		cap = 0;
	}
	public void close()
	{
		weights = null;
		values = null;
	}

	public void Generate()
	{
		int i;
        int wghtSum;

		weights[0] = 0;
		values[0] = 0;

		wghtSum = 0;
		for(i=1; i<= itemCnt; i++)
		{
			weights[i] = Math.abs(RandomNumbers.nextNumber()%100 + 1);
			values[i] = weights[i] + 10;
			wghtSum += weights[i];
		}
		cap = wghtSum/2;

		setupSortedValuesPerWeight();
	}

	// public void Generate()
	// {
	// 	int i;
    //     int wghtSum;

	// 	weights[0] = 0;
	// 	values[0] = 0;

	// 	weights[1] = 10;
	// 	weights[2] = 10;
	// 	// weights[3] = 10;

	// 	values[1] = 30;
	// 	values[2] = 20;
	// 	// values[3] = 10;

	// 	wghtSum = 0;
	// 	for(i=1; i<= itemCnt; i++)
	// 	{
	// 		wghtSum += weights[i];
	// 	}
	// 	cap = 10;
	// }

	public int GetItemCnt()
	{
		return itemCnt;
	}

	public int GetItemWeight(int itemNum)
	{
		return weights[itemNum];
	}

	public int GetItemValue(int itemNum)
	{
		return values[itemNum];
	}

	public float GetItemValuePerWeight(int itemNum) {
		return (float)(values[itemNum]) / weights[itemNum];
	}

	public int Fractional(int itemNum, int remainingCap) {
		int bestSum = 0;
		int rCap = remainingCap;
		for (int j = 0; j < itemCnt; ++j) {
			int i = sortedIndexesOfValuesPerWeight[j];
			if (i >= itemNum) {
				if (weights[i] <= rCap) {
					bestSum += values[i];
					rCap -= weights[i];
				} else if (rCap > 0) {
					bestSum += (int)(Math.ceil(rCap * GetItemValuePerWeight(i)));
					rCap = 0;
				}
				if (rCap == 0) {
					break;
				}
			}
		}
		return bestSum;
	}

	public int GetCapacity()
	{
		return cap;
	}

	private void setupSortedValuesPerWeight() {
		Map<Float, Integer> valuesToIndexes = new TreeMap<Float, Integer>();
		for (int i = 1; i <= itemCnt; ++i) {
			valuesToIndexes.put(GetItemValuePerWeight(i), i);
		}
	
		int size = valuesToIndexes.size();
		sortedIndexesOfValuesPerWeight = new int[size]; 
		Collection<Integer> vT = valuesToIndexes.values();
		int i = size - 1;
		for (Integer ind: vT) {
			sortedIndexesOfValuesPerWeight[i] = ind;
			System.out.println("Sorted indexes: " + sortedIndexesOfValuesPerWeight[i]);
			--i;
		}
	}
 
	public void Print()
	{
		int i;

		System.out.printf("Number of items = %d, Capacity = %d\n",itemCnt, cap);
		System.out.print("Weights: ");
		for (i = 1; i <= itemCnt; i++)
		{
			System.out.printf("%d ",weights[i]);
		}
		System.out.print("\nValues: ");
		for (i = 1; i <= itemCnt; i++)
		{
			System.out.printf("%d ",values[i]);
		}
		System.out.print("\n");
	}
}
