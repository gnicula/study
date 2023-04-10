import java.util.*;

// Dynamic programming solver
public class KnapsackDPSolver implements java.io.Closeable {
	private KnapsackInstance inst;
	private KnapsackSolution soln;
	private int[][] dptable;
	private boolean[][][] isTakenDP;

	public KnapsackDPSolver() {

	}

	public void FindDPSolution() {
		int itemCnt = inst.GetItemCnt();
		int capacity = inst.GetCapacity();
		int bestValWithoutItem;
		int bestValWithItem;
		int itemWeight;
		int remainingCapacity = capacity;

		for (int item = 1; item <= itemCnt; ++item) {
			for (int cap = 1; cap <= capacity; ++cap) {
				// This is available at the row above same capacity value.
				bestValWithoutItem = dptable[item - 1][cap];
				// Initialize this with 0 as we don't know yet if it fits.
				bestValWithItem = 0;

				itemWeight = inst.GetItemWeight(item);
				if (cap >= itemWeight) {
					// Knapsack can fit the current 'item', update bestValWithItem
					bestValWithItem = inst.GetItemValue(item);
					// Update remainingCapacity
					remainingCapacity = cap - itemWeight;
					// DP: add best value that can be obtained from this remaining
					// capacity and previous items.
					bestValWithItem += dptable[item - 1][remainingCapacity];
				}
				// Now we have two cases
				if (bestValWithoutItem >= bestValWithItem) {
					// The best value is without this item, put the value in the DP table.
					dptable[item][cap] = bestValWithoutItem;
					// Also copy the solution from cell above and set the 'item' as not taken.
					for (int i = 1; i < item; ++i) {
						isTakenDP[item][cap][i] = isTakenDP[item - 1][cap][i];
					}
					// isTakenDP[item][cap] = isTakenDP[item - 1][cap];
					isTakenDP[item][cap][item] = false;
				} else {
					// The best value includes this item, update the table.
					dptable[item][cap] = bestValWithItem;
					// Copy the solution from [item-1][remainingCapacity] and set the 'item' as
					// taken.
					for (int i = 1; i < item; ++i) {
						isTakenDP[item][cap][i] = isTakenDP[item - 1][remainingCapacity][i];
					}
					// isTakenDP[item][cap] = isTakenDP[item - 1][remainingCapacity];
					isTakenDP[item][cap][item] = true;
				}
			}
		}
		// The actual solution is taken from the bottom right cell at [itemCnt][capacity].
		// Copy the binary values from the 3D isTakenDP table to the KnapsackSolution object.
		for (int i = 1; i <= itemCnt; ++i) {
			if (isTakenDP[itemCnt][capacity][i]) {
				soln.TakeItem(i);
			}
		}
		// Done! Results are in 'soln' attribute.
	}

	public void close() {

	}

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_) {
		inst = inst_;
		soln = soln_;

		int itemCnt = inst_.GetItemCnt();
		int capacity = inst_.GetCapacity();

		// DP constructs a table of itemCnt x capacity where each cell at [item][cap]
		// keeps the best possible value for the subset of items up to 'item' and
		// a knapsack of capacity cap.
		// NOTE: There are columns for each possible capacity in this implementation.
		dptable = new int[itemCnt + 1][capacity + 1];
		// This is a table needed to keep 'solutions' (in the form of a binay array) per cell.
		isTakenDP = new boolean[itemCnt + 1][capacity + 1][itemCnt + 1];

		// Initialize the first row and first column in the table.
		// First row is 0 value because there are no items considered.
		for (int col = 0; col < capacity + 1; ++col) {
			dptable[0][col] = 0;
			for (int i = 0; i < itemCnt + 1; ++i) {
				isTakenDP[0][col][i] = false;
			}
		}
		// First column quirk: there might be items with 0 weight.
		for (int row = 0; row < itemCnt + 1; ++row) {
			if (row > 0 && inst.GetItemWeight(row) == 0) {
				dptable[row][0] = inst.GetItemValue(row);
			} else {
				dptable[row][0] = 0;
			}
			for (int i = 0; i < itemCnt + 1; ++i) {
				isTakenDP[row][0][i] = false;
			}
		}

		FindDPSolution();

		// System.out.print("DP table computed best solution: " + dptable[itemCnt][capacity] + "\n");
		// for (int item = 1; item <= itemCnt; ++item) {
		// 	for (int cap = 1; cap <= capacity; ++cap) {
		// 		System.out.print(dptable[item][cap] + " ");
		// 	}
		// 	System.out.print("\n");
		// }
	}
}