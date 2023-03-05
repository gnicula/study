import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

public class Sorting {
	final int MAX_SIZE = 10000000;

	// Set this to true if you wish the arrays to be printed.
	final static boolean OUTPUT_DATA = false;
	
	public static String sortAlg= null;
	public static  int size = 0;
	public static Random randIntGen = new Random();
	
	public static void main(String[] args) {
		readInput();
		int [] data = new int[size];
		GenerateSortedData(data, size);
		Sort(data, size,"Sorted Data");

		GenerateNearlySortedData(data, size);
		Sort(data, size,"Nearly Sorted Data");
		
		GenerateReverselySortedData(data, size);
		Sort(data, size,"Reversely Sorted Data");
		
		GenerateRandomData(data, size);
		Sort(data, size,"Random Data");
			
		System.out.println("\nProgram Completed Successfully.");
		
	}
	
	@SuppressWarnings("resource")
	public static void readInput(){
		System.out.println("  I:\tInsertion Sort");
		System.out.println("  M:\tMergeSort");
		System.out.println("  Q:\tQuickSort");
		System.out.println("  S:\tSTLSort");
	    System.out.println("Enter sorting algorithm:");
	    Scanner reader = new Scanner(System.in);
	    sortAlg = reader.next();
	    System.out.println(sortAlg);
		String sortAlgName = "";
		
		if(sortAlg.equals("I"))
			sortAlgName = "Insertion Sort";
		else if(sortAlg.equals("M"))
			sortAlgName = "MergeSort";
		else if(sortAlg.equals("Q"))
			sortAlgName = "QuickSort";
		else if(sortAlg.equals("S"))
			sortAlgName = "STLSort";
		else {
			System.out.println("Unrecognized sorting algorithm Code:"+sortAlg);
			System.exit(1);
		}
		System.out.println("Enter input size: ");
	    size = reader.nextInt();
		System.out.println("\nSorting Algorithm: " + sortAlgName);
        System.out.println("\nInput Size = "  + size);
	}
	
	/******************************************************************************/

	public static void GenerateSortedData(int data[], int size)
	{
		int i;
		
		for(i=0; i<size; i++)
			data[i] = i * 3 + 5;
	}
	/*****************************************************************************/
	public static void GenerateNearlySortedData(int data[], int size)
	{
		int i;
		
		GenerateSortedData(data, size);
		
		for(i=0; i<size; i++)
			if(i % 10 == 0)
				if(i+1 < size)
					data[i] = data[i+1] + 7;
	}
	/*****************************************************************************/

	public static void GenerateReverselySortedData(int data[], int size)
	{
		int i;
		
		for(i = 0; i < size; i++)
			data[i] = (size-i) * 2 + 3;
	}
	/*****************************************************************************/

	public static void GenerateRandomData(int data[], int size)
	{
		int i;
		for(i = 0; i < size; i++)
			data[i] = new Random().nextInt(10000000);
//		int data_bad[] = new int[] {2871684, 9760106, 4510918, 4660545,
//		  276628, 6753049, 5867792, 2693707, 456969, 1802116 };
//		for(i = 0; i < size; i++)
//			data[i] = data_bad[i]; 
			
		
	}
	/*****************************************************************************/

	
	public static void Sort(int[] data, int size,  String string)
	{

		System.out.print("\n"+string+":");
		if (OUTPUT_DATA)
		{
			printData(data, size, "Data before sorting:");
		}

		// Sorting is about to begin ... start the timer!
		long start_time = System.nanoTime();
			if (sortAlg.equals("I"))
			{
			InsertionSort(data, size);
			}
			else if (sortAlg.equals("M"))
			{
			MergeSort(data, 0, size-1);
			}
			else if (sortAlg.equals("Q"))
			{
			QuickSort(data, 0, size-1);
			}
			else if (sortAlg.equals("S"))
			{
			STLSort(data, size);
			}
		else
		{
			System.out.print("Invalid sorting algorithm!");
			System.out.print("\n");
			System.exit(1);
		}

		// Sorting has finished ... stop the timer!
		
		double elapsed = System.nanoTime()-start_time;
		elapsed=elapsed/1000000;


		if (OUTPUT_DATA)
		{
			printData(data, size, "Data after sorting:");
		}


		if (IsSorted(data, size))
		{
			System.out.print("\nCorrectly sorted ");
			System.out.print(size);
			System.out.print(" elements in ");
			System.out.print(elapsed);
			System.out.print("ms");
		}
		else
		{
			System.out.print("ERROR!: INCORRECT SORTING!");
			System.out.print("\n");
		}
		System.out.print("\n-------------------------------------------------------------\n");
	}
	
	/*****************************************************************************/

	public static boolean IsSorted(int data[], int size)
	{
		int i;
		
		for(i=0; i<(size-1); i++)
		{
			if(data[i] > data[i+1])
				return false;
		}
		return true;
	}
	
	/*****************************************************************************/

	public static void InsertionSort(int data[], int size)
	{
		//Write your code here
		//System.out.println("InserionSort");
		
	}
	/*****************************************************************************/

	public static void MergeSort(int data[], int lo, int hi)
	{
		//Write your code here
		//You may create other functions if needed 
		//System.out.println("MergeSort");
	}
	/*****************************************************************************/
	public static int ChoosePivot(int data[], int lo, int hi, int pivotCase)
	{
		int pivot = hi;
		switch(pivotCase)
		{
			case 0: 
//				pivot = hi;
				break;
			case 1:
				pivot = lo + randIntGen.nextInt(hi-lo+1);
				break;
			case 2:
				int first = lo + randIntGen.nextInt(hi-lo+1);
				int second = lo + randIntGen.nextInt(hi-lo+1);
				int third = lo + randIntGen.nextInt(hi-lo+1);
				
				pivot = second;
				
				if ((data[second] <= data[first] && data[second] >= data[third]) || 
						data[second] >= data[first] && data[second] <= data[third])
				{
					pivot = second;
				} else if ((data[first] >= data[third] && data[first] <= data[second]) ||
						(data[first] <= data[third] && data[first] >= data[second]))
				{
					pivot = first;
				} else if ((data[third] >= data[first] && data[third] <= data[second]) ||
						(data[third] <= data[first] && data[third] >= data[second]))
				{
					pivot = third;
				}
		}
		return pivot;
	}
	/*****************************************************************************/
	
	public static int Partition(int data[], int lo, int hi, int pivotCase)
	{
		//System.out.println("lo: " + lo + " hi: " + hi);
		int i = lo - 1;
		
//		if(randPivot)
//		{
//			int randPivotIndex = lo + new Random().nextInt(hi-lo+1);
//			swap(randPivotIndex, hi, data);
//		}
		
		int pivotIndex = ChoosePivot(data, lo, hi, pivotCase);
		swap(pivotIndex, hi, data);
		
		int x = data[hi];
		//System.out.println("data[]: " + Arrays.toString(data) + "pivot: " + x);
		for (int j = lo; j < hi; ++j)
		{
			if(data[j] < x)
			{
				swap(j, (i+1), data);
				++i;
			}
		}
		swap(hi, (i+1), data);
		return i+1;
	}
	/*****************************************************************************/
	
	public static void QuickSort(int data[], int lo, int hi)
	{
		//System.out.println("QuickSort");
		if (hi - lo >= 1) 
		{
			int pivot = Partition(data, lo, hi, 1);
			QuickSort(data, lo, pivot-1);
			QuickSort(data, pivot+1, hi);
		}
		
	}
	/*****************************************************************************/

	public static void STLSort(int data[], int size)
	{
		//Write your code here
		//Your code should simply call the STL sorting function  
		//System.out.println("STLSort");
		Arrays.sort(data);
		
	}
	/*****************************************************************************/
	
	public static void swap(int x , int y ,int data[])
	{
		int temp = data[x];
		data[x] = data[y];
	    data[y] = temp;
	}
	
	/*****************************************************************************/
	
	public static void printData(int[] data, int size, String title)
	{
		int i;

		System.out.print("\n");
		System.out.print(title);
		System.out.print("\n");
		for (i = 0; i < size; i++)
		{
			System.out.print(data[i]);
			System.out.print(" ");
			if (i % 10 == 9 && size > 10)
			{
				System.out.print("\n");
			}
		}
	}

}
