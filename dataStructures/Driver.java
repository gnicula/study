package bubblesort;
import bubblesort.Sort;

class Sort {
	
	public static void swapRows(int[][] toSwap, int row1, int row2)
	{
		int[] temp = toSwap[row1];
		toSwap[row1] = toSwap[row2];
		toSwap[row2] = temp;
	}
	
	public static void swapColumns(int[][] toSwap, int col1, int col2)
	{
		for(int i = 0; i < toSwap.length; ++i)
		{
			int temp = toSwap[i][col1];
			toSwap[i][col1] = toSwap[i][col2];
			toSwap[i][col2] = temp;
		}
	}
	
	public static void bubbleSort(int[][] toSort)
	{
		for(int i = 0; i < toSort.length-1; ++i)
		{
			for(int j = 0; j < toSort.length-1 - i; ++j)
			{
				if(toSort[j][0] > toSort[j+1][0])
				{
					swapRows(toSort, j, j+1);
				}
			}
		}
	}
	
	public static void selectionSort(int[][] toSort)
	{
		for(int i = 0; i < toSort.length-1; ++i)
		{
			int indexOfHighest = i;
			for(int j = i + 1; j < toSort.length; ++j)
			{
				if(toSort[j][1] > toSort[indexOfHighest][1])
				{
					indexOfHighest = j;
				}
			}
			swapRows(toSort, i, indexOfHighest);
		}
	}
	
	public static void shellSort(int[][] toSort, int[] gapValues)
	{
		for(int i = 0; i < gapValues.length; ++i)
		{
			for(int j = 0; j < gapValues[i]; ++j)
			{
				insertionForShell(toSort, gapValues[i], j);
			}
		}
	}
	
	public static void insertionForShell(int[][] gapped, int gapValue, int startIndex)
	{
		int j = 0;
		
		for(int i = gapValue + startIndex; i < gapped.length; i += gapValue)
		{
			j = i;
			while(j - gapValue >= startIndex && gapped[j][2] < gapped[j-gapValue][2])
			{
				swapRows(gapped, j, j-gapValue);
				j -= gapValue;
			}
		}
	}
	
	public static void insertionSort(int[][] toSort)
	{
		for(int i = 1; i < toSort[0].length; ++i)
		{
			int j = i;
			while(j > 0 && toSort[4][j] < toSort[4][j-1])
			{
				swapColumns(toSort, j, j-1);
				--j;
			}
		}
	}
	
	public static void toPrint(int[][] toPrint)
	{
		for(int i = 0; i < toPrint.length;	++i)
		{
			for(int j = 0; j < toPrint[i].length; ++j)
			{
				System.out.print(toPrint[i][j] + " ");
			}
			System.out.println();
		}
	}
}

public class Driver {
	private static final int[][] TEST = {
		{5, 3, 2, 16}, 
		{9, 8, 10, 17},
		{4, 7, 11, 18},
		{2, 5, 9, 12},
		{7, 9, 4, 10}
	};
	
	private static final int[] val = {2, 1};
		
	public static void main(String[]args)
	{
		int[][] toSort = TEST.clone();
		//Sort.bubbleSort(toSort);
		//Sort.toPrint(toSort);
		
		//toSort = TEST.clone();
		//Sort.selectionSort(toSort);
		//Sort.toPrint(toSort);
		
		//toSort = TEST.clone();
		//Sort.insertionSort(toSort);
		//Sort.toPrint(toSort);
		
		toSort = TEST.clone();
		Sort.shellSort(toSort, val);
		Sort.toPrint(toSort);
	}
}


