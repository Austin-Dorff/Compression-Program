package core.starter;

import java.io.IOException;

import core.engine.compressor.Compressor;
import core.engine.decompressor.Decompressor;
import core.engine.files.reader.BinaryIn;
import core.engine.reference.Reference;

public class Runner
{
	public static void main(String[] args) throws IOException
	{
		double start = System.currentTimeMillis();
		Compressor c = new Compressor();
		BinaryIn bi = new BinaryIn(Reference.FILE_THREE);
		Decompressor d = new Decompressor(bi.getFileBits(), c.getTree());
		double end = System.currentTimeMillis();
		double totalTime = (end - start);
		System.out.println(totalTime + "ms");
	}
}
