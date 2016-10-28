package core.engine.files.writer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitWriter
{
	
	private BufferedOutputStream	out;
	private int						buffer;
	private int						n;
	int								counter;
	private byte[]					data	= new byte[0];
											
	public BitWriter(File f)
	{
		try
		{
			OutputStream os = new FileOutputStream(f);
			out = new BufferedOutputStream(os);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeEncodedData(String enc)
	{
		for (int i = 0; i < enc.length(); i++)
		{
			writeBit(enc.charAt(i) == '1');
		}
		if (enc.length() % 8 != 0)
		{
			int lim = 8, mult = 1;
			while (lim < enc.length())
			{
				lim = (8 * (mult++));
			}
			lim = lim - enc.length();
			for (int i = 0; i < lim; i++)
			{
				writeBit(true);
			}
		}
	}
	
	private void writeBit(boolean x)
	{
		buffer <<= 1;
		if (x)
		{
			buffer |= 1;
		}
		n++;
		if (n == 8)
		{
			clearBuffer();
		}
	}
	
	private void clearBuffer()
	{
		if (n == 0)
		{
			return;
		}
		if (n > 0)
		{
			buffer <<= (8 - n);
		}
		try
		{
			out.write(buffer);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		n = 0;
		buffer = 0;
	}
	
	public void flush() throws IOException
	{
		out.flush();
	}
	
}
