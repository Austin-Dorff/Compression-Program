package core.engine.data.types;

public class Data implements Comparable<Data>
{
	private byte	value;
	private int		frequency;
					
	public Data()
	{
	
	}
	
	public Data(Data d)
	{
		value = d.getValue();
		frequency = d.getFrequency();
	}
	
	public Data(byte val, int freq)
	{
		frequency = freq;
		value = val;
	}
	
	public byte getValue()
	{
		return value;
	}
	
	public int getFrequency()
	{
		return frequency;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return ((obj instanceof Data) && (((Data) obj).getValue() == getValue()) && (((Data) obj).getFrequency() == getFrequency()));
	}
	
	@Override
	public String toString()
	{
		return Byte.toString(value) + " x" + frequency;
	}
	
	@Override
	public int compareTo(Data o)
	{
		return getFrequency() - o.getFrequency();
	}
	
}
