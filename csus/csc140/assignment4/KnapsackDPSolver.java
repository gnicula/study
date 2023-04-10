import java.util.*;

// Dynamic programming solver
public class KnapsackDPSolver implements java.io.Closeable
{
	private KnapsackInstance inst;
	private KnapsackSolution soln;
	private int[][] dptable;

	public KnapsackDPSolver()
	{
    
	}

	public void close()
	{
    
	}
	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		inst = inst_;
		soln = soln_;

		int itemCnt = inst_.GetItemCnt();
		int capacity = inst_.GetCapacity();

		dptable = new int[inst_.GetItemCnt() + 1][inst_.GetCapacity() + 1];
		for (int col = 0; col < capacity + 1; ++col) {
            dptable[0][col] = 0;
        }
        for (int row = 0; row < itemCnt + 1; ++row) {
            dptable[row][0] = 0;
        }

		for (int item = 1; item <= itemCnt; ++item) {
            for (int cap = 1; cap <= capacity; ++cap) {
                int maxValWithoutCurr = dptable[item - 1][capacity]; // This is guaranteed to exist
                int maxValWithCurr = 0; // We initialize this value to 0
                
                int weightOfCurr = inst.GetItemWeight(item);
                if (cap >= weightOfCurr) { // We check if the knapsack can fit the current item
                    maxValWithCurr = inst.GetItemValue(item); // If so, maxValWithCurr is at least the value of the current item
                    
                    int remainingCapacity = cap - weightOfCurr; // remainingCapacity must be at least 0
                    maxValWithCurr += dptable[item - 1][remainingCapacity]; // Add the maximum value obtainable with the remaining capacity
                }
                
                dptable[item][cap] = Math.max(maxValWithoutCurr, maxValWithCurr); // Pick the larger of the two
            }
        }
		System.out.print("DP table computed sol: " + dptable[itemCnt][capacity]);

	}
}