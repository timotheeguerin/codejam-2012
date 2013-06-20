import static java.lang.Math.*;

public class KingdomAndTrees
{

	public static int minLevel(int[] heights)
	{
		int x = heights.length / 2;

		System.out.println(x);
		int level = 0;
		for (int i = 0; i < x; i++)
		{
			int a = heights[i];
			int b = heights[heights.length - i - 1];
			if (a > b - (heights.length - 2 * i))
			{
				int diff = a - b;
				if (diff > level)
				{
					level = diff;
				}
			}

		}
		return level;
	}

	public static int minLevel2(int[] heights)
	{
		int lev = 0;
		int[] diffs = new int[heights.length];

		for (int i = 1; i < heights.length; ++i)
		{
			if (heights[i] - lev > heights[i - 1] + diffs[i - 1])
			{
				diffs[i] = -lev;
			} else if (heights[i] + lev > heights[i - 1] + diffs[i - 1])
			{
				diffs[i] = heights[i - 1] + diffs[i - 1] + 1 - heights[i];
			} else
			{
				int dl = (heights[i - 1] + diffs[i - 1] + 2 - heights[i] - lev) / 2;

				if (heights[i - 1] + diffs[i - 1] - dl >= i)
				{
					lev += dl;
					diffs[i] = heights[i - 1] + diffs[i - 1] - dl + 1 - heights[i];
				} else
				{
					lev = max(lev, i + 1 - heights[i]);
					diffs[i] = i + 1 - heights[i];
				}
			}
		}

		return lev;
	}

	public static void main(String args[])
	{
		int[] heights = { 9, 5, 8 };
		System.out.println(minLevel2(heights));

	}

}
