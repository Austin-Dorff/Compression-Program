package core.engine.utilities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ArrayUtilities<T>
{
	
	@SuppressWarnings("unchecked")
	public T[] toArray(List<T> arr, T t)
	{
		return arr.toArray((T[]) Array.newInstance(t.getClass(), 0));
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<T> toArrayList(T[] arr)
	{
		List<T> tt = new ArrayList<T>();
		Collections.addAll(tt, arr);
		return new ArrayList<T>(tt);
	}
	
	public HashMap<T, Integer> getOneOfEveryInstance(List<T> arr)
	{
		HashMap<T, Integer> hs = new HashMap<T, Integer>();
		Integer val = 0;
		for (T s : arr)
		{
			val = hs.get(s);
			if (val != null)
			{
				hs.put(s, val + 1);
			}
			else
			{
				hs.put(s, 1);
			}
		}
		return hs;
	}
	
	public HashMap<T, Integer> getOneOfEveryInstance(T[] arr)
	{
		HashMap<T, Integer> hs = new HashMap<T, Integer>();
		Integer val = 0;
		for (T s : arr)
		{
			val = hs.get(s);
			if (val != null)
			{
				hs.put(s, val + 1);
			}
			else
			{
				hs.put(s, 1);
			}
		}
		return hs;
	}
	
	public List<T> reverse(List<T> arr)
	{
		List<T> ret = new ArrayList<T>(arr);
		Collections.reverse(ret);
		return ret;
	}
}
