import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class KingdomAndPassword
{

	// PERMUTATION_______________________________________________________________________________________________

	public static ArrayList<Long> permutation(String str, int[] restrictedDigits)
	{

		ArrayList<Long> permOfString = new ArrayList<Long>();
		permutation("", str, permOfString, restrictedDigits);
		return permOfString;

	}

	private static void permutation(String prefix, String str, ArrayList<Long> permOfString, int[] restrictedDigits)
	{
		// recursive approach to find all permutations
		int n = str.length();

		if (n == 0)
		{

			long newPassword = Long.parseLong(prefix);

			if (restrictD(restrictedDigits, newPassword))
			{
				System.out.println(newPassword);
				permOfString.add(newPassword);
			}

		} else
		{
			for (int i = 0; i < n; i++)
			{
				permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), permOfString,
						restrictedDigits);
			}
		}
	}

	public static ArrayList<Long> perm(long oldPassword, int[] restrictedDigits)
	{ // will return an array with all the possible permutations

		String oldSPassword = String.valueOf(oldPassword);

		ArrayList<Long> perm = permutation(oldSPassword, restrictedDigits);

		return perm;
	}

	// CONSTRAINTS________________________________________________________________________________________

	public static int[] parsePassword(long oldPassword)
	{
		// Here we parse the old password so we can look at each digit separatly
		char[] oldPasswordChar = Long.toString(oldPassword).toCharArray();
		int[] oldPasswordArray = new int[oldPasswordChar.length];

		for (int it = 0; it < oldPasswordChar.length; it++)
		{

			oldPasswordArray[it] = Character.getNumericValue(oldPasswordChar[it]);

		}
		return oldPasswordArray;

	}

	public static boolean restrictD(int[] restrictedDigits, long newPassword)
	{

		// here i check if a possible password work.
		boolean works = true;
		int[] newPasswordArray = parsePassword(newPassword);

		for (int i = 0; i < newPasswordArray.length; i++)
		{

			if ((newPasswordArray[i] == restrictedDigits[i]))
				works = false;

		}
		// if the current permutation is working with the restrictedDigits then
		return works;

	}

	public static long minimized(ArrayList<Long> possNewPass, long oldPassword)
	{

		// this will find the best newpassword within an array full of working
		// new passwords
		long min = Math.abs(oldPassword - possNewPass.get(0));
		long password = possNewPass.get(0);
		for (int i = 1; i < possNewPass.size(); i++)
		{
			// here we try to minimize |OldPassword-X|
			if (min > Math.abs(oldPassword - possNewPass.get(i)))
			{

				min = Math.abs(oldPassword - possNewPass.get(i));
				password = possNewPass.get(i);

			}
			// Here is when we have 2 solutions for |OldPassword-X|
			else if (min == Math.abs(oldPassword - possNewPass.get(i)))
			{
				password = Math.min(password, possNewPass.get(i));
			}
			// here we take out the passwords that are not optimal
			else
				possNewPass.remove(i);
		}
		return password;
	}

	public static int[] newPass(LinkedList<Integer> oldPass, int[] newPassword, ListIterator<Integer> smallestVal,
			ListIterator<Integer> restrict) throws Exception
	{
		System.out.println("oldPass: " + oldPass);

		while (!(oldPass.isEmpty()))
		{
			System.out.println("Size: " + oldPass.size());
			for (int i = 0; i < oldPass.size(); i++)
			{
				System.out.println("index: " + i);
				int smv = (Integer) smallestVal.next();
				if (smv != (Integer) restrict.next())
				{
					newPassword[i] = (Integer) smv;
					System.out.println("Val:  " + newPassword[i]);
					// smallestVal.remove();
					// restrict.remove();
				} else
				{
					if (!(smallestVal.hasNext()))
					{
						System.out.println("WTF");
						throw new Exception();
					} else
					{
						smallestVal.next();
					}
				}

			}

		}
		return newPassword;
	}

	public static void generateValues(int array[])
	{
		List<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < array.length; i++)
		{
			list.add(array[array.length - i - 1]);
		}
		generatePerm(list);
	}

	public static List<List<Integer>> generatePerm(List<Integer> original)
	{
		if (original.size() == 0)
		{
			List<List<Integer>> result = new ArrayList<List<Integer>>();
			result.add(new ArrayList<Integer>());
			return result;
		}
		Integer firstElement = original.remove(0);
		List<List<Integer>> returnValue = new ArrayList<List<Integer>>();
		List<List<Integer>> permutations = generatePerm(original);
		for (List<Integer> smallerPermutated : permutations)
		{
			for (int index = 0; index <= smallerPermutated.size(); index++)
			{
				List<Integer> temp = new ArrayList<Integer>(smallerPermutated);
				temp.add(index, firstElement);
				System.out.println("Val: " + temp);
				returnValue.add(temp);
			}
		}
		return returnValue;
	}

	// NEW
	// PASSWORD_________________________________________________________________________________________________

	public static long newPassword(long oldPassword, int[] restrictedDigits)
	{
		// Case where the newPassword is going to be equal to the oldPassword
		if (restrictD(restrictedDigits, oldPassword))
		{
			return oldPassword;
		}
		char[] oldPasswordChar = Long.toString(oldPassword).toCharArray();
		int[] oldPassSort = parsePassword(oldPassword);
		Arrays.sort(oldPassSort);
		generateValues(oldPassSort);

		LinkedList<Integer> oldPass = new LinkedList<Integer>();
		LinkedList<Integer> restrictD = new LinkedList<Integer>();
		for (int i = 0; i < oldPasswordChar.length; i++)
		{
			oldPass.add(oldPassSort[i]);
			restrictD.add(restrictedDigits[i]);
		}

		int[] newPassword = new int[oldPass.size()];
		ListIterator<Integer> smallestVal = oldPass.listIterator();
		ListIterator<Integer> restrict = restrictD.listIterator();

		try
		{
			newPassword = newPass(oldPass, newPassword, smallestVal, restrict);
		} catch (Exception e)
		{
			System.out.println("-1//");
		}
		/*
		 * ArrayList<Long> possiblePassword = perm(oldPassword,
		 * restrictedDigits);
		 * 
		 * //case where none of the permutation work
		 * if(possiblePassword.isEmpty()) return -1; //here we try to find the
		 * minimal password according to the constraints long password =
		 * minimized(possiblePassword, oldPassword);
		 */
		StringBuilder sb = new StringBuilder();
		for (int i : newPassword)
		{
			sb.append(i);
		}
		long password = Long.parseLong(sb.toString());

		return password;
	}

	public static void main(String[] args)
	{

		long oldPassword0 = 548;
		int[] restrictedDigits0 = { 5, 1, 8 };
		System.out.println(newPassword(oldPassword0, restrictedDigits0));
		// Returns: 485
		/*
		 * long oldPassword1 = 7777; int[] restrictedDigits1 = {4,7,4,7};
		 * System.out.println(newPassword(oldPassword1, restrictedDigits1));
		 * //Returns: -1
		 * 
		 * long oldPassword2 = 58; int[] restrictedDigits2 = {4,7};
		 * System.out.println(newPassword(oldPassword2, restrictedDigits2));
		 * //Returns: 58
		 * 
		 * long oldPassword3 = 172; int[] restrictedDigits3 = {4,7,4};
		 * System.out.println(newPassword(oldPassword3, restrictedDigits3));
		 * //Returns: 127
		 * 
		 * long oldPassword4 = 241529363573463L; int[] restrictedDigits4 = {1,
		 * 4, 5, 7, 3, 9, 8, 1, 7, 6, 3, 2, 6, 4, 5};
		 * System.out.println(newPassword(oldPassword4, restrictedDigits4));
		 * //Returns: -1
		 */
	}
}