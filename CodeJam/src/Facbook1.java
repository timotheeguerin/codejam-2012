import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Facbook1
{
	public static void main(String[] args)
	{
		File file = new File("input01.txt");
		Scanner scanner;
		try
		{
			scanner = new Scanner(file);
			int c = scanner.nextInt();
			for (int i = 0; i < c; i++)
			{
				if (solveTest(scanner))
				{
					System.out.println("yes");
				} else
				{
					System.out.println("no");
				}
			}

		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean solveTest(Scanner scanner)
	{
		boolean result = true;
		int n = scanner.nextInt();
		int k = scanner.nextInt();
		int q = scanner.nextInt();

		Map<int[], Integer> tests = new HashMap<int[], Integer>();

		for (int i = 0; i < q; i++)
		{
			int[] test = new int[k];
			for (int j = 0; j < k; j++)
			{
				test[j] = scanner.nextInt();
			}
			int value = scanner.nextInt();
			int counter = 0;
			for (Entry<int[], Integer> e : tests.entrySet())
			{
				int com = getNbInCommon(e.getKey(), test);
				counter += Math.abs(e.getValue() - com);
				if (value + counter > k)
				{
					result = false;
				}

			}
			tests.put(test, value);
		}
		return result;
	}

	public static int getNbInCommon(int[] array1, int[] array2)
	{
		int count = 0;
		for (int i = 0; i < array1.length; i++)
		{
			if (array1[i] == array2[i])
			{
				count++;
			}
		}
		return count;
	}
}
