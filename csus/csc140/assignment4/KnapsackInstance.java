import java.util.*;

public class KnapsackInstance implements java.io.Closeable
{
	private int itemCnt; //Number of items
	private int cap; //The capacity
	private int[] weights; //An array of weights
	private int[] values; //An array of values
	// Fractional needs to pick most 'valuable' items.
	// Keep sorted items by value per weight.
	private final int[] sortedIndexesOfValuesPerWeight;
	// Extra work - add a table for FractionalFast computation
	public int[][] fractionals;

	public KnapsackInstance(int itemCnt_)
	{
		itemCnt = itemCnt_;

		weights = new int[itemCnt + 1];
		values = new int[itemCnt + 1];

		sortedIndexesOfValuesPerWeight = new int[itemCnt];
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
		precomputeFractionals();
	}

	// This method can be used to set up a specific input
	// NOTE: there must be itemCnt weights and values and 0 at 0 index
	public void GenerateDebug()
	{
		int[] debugWeights = new int[] {3, 45, 61, 77, 77};
		int[] debugValues = new int[] {13, 55, 71, 87, 87};
		weights[0] = 0;
		values[0] = 0;

		for(int i=1; i<= itemCnt; ++i)
		{
			weights[i] = debugWeights[i-1];
			values[i] = debugValues[i-1];
		}

		cap = 131;
		setupSortedValuesPerWeight();
	}

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

	public int GetAllItemsValue()
	{
		int totalValue = 0;
		for (int i = 1; i <= GetItemCnt(); ++i) {
			totalValue += GetItemValue(i);
		}
		return totalValue;
	}

	public float GetItemValuePerWeight(int itemNum) {
		if (weights[itemNum] > 0) {
			return (float)(values[itemNum]) / weights[itemNum];	
		}
		return Float.MAX_VALUE;
	}

	// Uses sortedIndexesOfValuesPerWeight to get through the most
	// 'valuable' items first when computing the Fractional value of
	// the rest of the items starting at itemNum inclusive.
	public int Fractional(int itemNum, int remainingCap) {
		int bestSum = 0;
		int rCap = remainingCap;
		// System.out.print("Fractional rCap: " + rCap + "\n");
		for (int j = 0; j < itemCnt && rCap >= 0; ++j) {
			int i = sortedIndexesOfValuesPerWeight[j];
			if (i >= itemNum) {
				if (weights[i] <= rCap) {
					bestSum += values[i];
					rCap -= weights[i];
				} else if (rCap > 0) {
					// Floating point arithmetic: computes exact solution
					// bestSum += (int)(Math.floor(rCap * GetItemValuePerWeight(i)));

					// EXTRA WORK
					// Avoid floating-point arithmetic and use integer arithmetic
					// only in the Fractional Knapsack algorithm.
					// First multiply remaining capacity with value of item and
					// then do integer division.
					bestSum += ((rCap * values[i]) / weights[i]);
					rCap = 0;
					break;
				}
			}
		}
		return bestSum;
	}

	public int FractionalFast(int itemNum, int remainingCap) {
		if (remainingCap < 0) {
			return 0;
		}
		return fractionals[itemNum][remainingCap];
	}

	public int GetCapacity()
	{
		return cap;
	}

	// Basically an argsort - creates and stores and array of item ids sorted
	// in the order of their corresponding value per weight from high to low.
	private void setupSortedValuesPerWeight() {
		final Integer[] sorted_idx = new Integer[itemCnt];
		final float[] value_per_weight = new float[itemCnt];
		
		for (int i=0; i<itemCnt; ++i) {
			sorted_idx[i] = i+1;
			value_per_weight[i] = GetItemValuePerWeight(i+1);
		}

		Arrays.sort(sorted_idx, new Comparator<Integer>() {
			@Override public int compare(final Integer left, final Integer right) {
				return Float.compare(value_per_weight[right-1], value_per_weight[left-1]);
			}
		});
		// Store the sorted items by value/weight to use with Fractional.
		for (int i=0; i<itemCnt; ++i) {
			sortedIndexesOfValuesPerWeight[i] = sorted_idx[i];
		}
	}

	private void precomputeFractionals() {
		fractionals = new int[itemCnt+1][cap+1];
		for (int i=1; i<=itemCnt; ++i) {
			for (int j=0; j<=cap; ++j) {
				fractionals[i][j] = Fractional(i, j);
			}
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
		System.out.print("\nSorted by v/w: ");
		for (i=0; i<itemCnt; ++i) {
			System.out.print(sortedIndexesOfValuesPerWeight[i] + " ");
		}
		System.out.print("\nUsing USE_DONT_TAKE_FIRST: " + KnapsackBFSolver.USE_DONT_TAKE_FIRST);
		System.out.print("\n");
	}
}
