package core.engine.data.collections;

import java.util.ArrayList;
import java.util.List;

import core.engine.data.types.Data;
import core.engine.utilities.ArrayUtilities;

public class DataChain
{
	
	private ArrayUtilities<Data>	arrUtil_Data	= new ArrayUtilities<Data>();
													
	private Data[]					chain;
	private int						length;
	private int						freq;
									
	public DataChain()
	{
	
	}
	
	public DataChain(Data dat)
	{
		chain = new Data[] { dat };
		length = 1;
		freq = dat.getFrequency();
	}
	
	public DataChain(DataChain d1, DataChain d2)
	{
		chain = new Data[d1.getLength() + d2.getLength()];
		System.arraycopy(d1.getChain(), 0, chain, 0, d1.getLength());
		System.arraycopy(d2.getChain(), 0, chain, d1.getLength(), d2.getLength());
		length = chain.length;
		freq = 0;
		for (Data d : chain)
		{
			freq += d.getFrequency();
		}
	}
	
	public DataChain(Data[] dat)
	{
		chain = dat.clone();
		length = dat.length;
		freq = 0;
		for (Data d : chain)
		{
			freq += d.getFrequency();
		}
	}
	
	public Data[] getChain()
	{
		return chain;
	}
	
	public byte[] getChainBytes()
	{
		byte[] ret = new byte[chain.length];
		int counter = 0;
		for (Data d : chain)
		{
			ret[counter++] = d.getValue();
		}
		return ret;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public int getFrequency()
	{
		return freq;
	}
	
	@Override
	public boolean equals(Object o)
	{
		return ((o instanceof DataChain) && chainsAreEqual(((DataChain) o).getChain()));
	}
	
	@Override
	public String toString()
	{
		String ret = new String();
		for (Data d : chain)
		{
			ret += d.toString() + " ";
		}
		return ret;
	}
	
	private boolean chainsAreEqual(Data[] c)
	{
		if (c.length != chain.length)
		{
			return false;
		}
		else
		{
			int co = 0;
			for (Data d : c)
			{
				if (!chain[co++].equals(d))
				{
					return false;
				}
			}
			return true;
		}
	}
	
	public List<Integer> indexOf(DataChain dc)
	{
		List<Integer> ret = new ArrayList<Integer>();
		List<Data> thisList = arrUtil_Data.toArrayList(getChain());
		int counter = 0;
		DataChain dTemp = new DataChain();
		while (counter <= (getLength() - dc.getLength()))
		{
			dTemp = new DataChain(arrUtil_Data.toArray(thisList.subList(counter, (counter + dc.getLength())), new Data()));
			if (dTemp.equals(dc))
			{
				ret.add(counter);
			}
			counter++;
		}
		return ret;
	}
	
}
