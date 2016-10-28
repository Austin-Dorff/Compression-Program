package core.engine.files.reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class BinaryIn
{
	
	private BufferedInputStream	in;
	private int					buffer;
	private int					n;
	private static final int	EOF	= -1;
									
	public BinaryIn(String name)
	{
		try
		{
			File file = new File(name);
			if (file.exists())
			{
				FileInputStream fis = new FileInputStream(file);
				in = new BufferedInputStream(fis);
				fillBuffer();
				return;
			}
			URL url = getClass().getResource(name);
			if (url == null)
			{
				url = new URL(name);
			}
			URLConnection site = url.openConnection();
			InputStream is = site.getInputStream();
			in = new BufferedInputStream(is);
			fillBuffer();
		}
		catch (IOException ioe)
		{
			System.err.println("Could not open " + name);
		}
	}
	
	private void fillBuffer()
	{
		try
		{
			buffer = in.read();
			n = 8;
		}
		catch (IOException e)
		{
			System.err.println("EOF");
			buffer = EOF;
			n = -1;
		}
	}
	
	public boolean exists()
	{
		return in != null;
	}
	
	public boolean isEmpty()
	{
		return buffer == EOF;
	}
	
	public int readInt()
	{
		int x = 0;
		for (int i = 0; i < 4; i++)
		{
			char c = readChar();
			x <<= 8;
			x |= c;
		}
		return x;
	}
	
	public int readInt(int r)
	{
		if (r < 1 || r > 32)
		{
			throw new RuntimeException("Illegal value of r = " + r);
		}
		if (r == 32)
		{
			return readInt();
		}
		int x = 0;
		for (int i = 0; i < r; i++)
		{
			x <<= 1;
			boolean bit = readBoolean();
			if (bit)
			{
				x |= 1;
			}
		}
		return x;
	}
	
	public boolean readBoolean()
	{
		if (isEmpty())
		{
			throw new RuntimeException("Reading from empty input stream");
		}
		n--;
		boolean bit = ((buffer >> n) & 1) == 1;
		if (n == 0)
		{
			fillBuffer();
		}
		return bit;
	}
	
	public char readChar()
	{
		if (isEmpty())
		{
			throw new RuntimeException("Reading from empty input stream");
		}
		if (n == 8)
		{
			int x = buffer;
			fillBuffer();
			return (char) (x & 0xff);
		}
		int x = buffer;
		x <<= (8 - n);
		int oldN = n;
		fillBuffer();
		if (isEmpty())
		{
			throw new RuntimeException("Reading from empty input stream");
		}
		n = oldN;
		x |= (buffer >>> n);
		return (char) (x & 0xff);
	}
	
	public char readChar(int r)
	{
		if (r < 1 || r > 16)
			throw new RuntimeException("Illegal value of r = " + r);
			
		if (r == 8)
			return readChar();
			
		char x = 0;
		for (int i = 0; i < r; i++)
		{
			x <<= 1;
			boolean bit = readBoolean();
			if (bit)
				x |= 1;
		}
		return x;
	}
	
	public String getFileBits()
	{
		String ret = new String();
		while (!isEmpty())
		{
			ret += String.valueOf(readInt(1));
		}
		return ret;
	}
}
