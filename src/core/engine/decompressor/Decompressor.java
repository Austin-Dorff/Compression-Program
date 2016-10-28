package core.engine.decompressor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import core.engine.encoder.tree.DorffTree;
import core.engine.reference.Reference;

public class Decompressor
{
	
	public Decompressor(String bits, DorffTree t) throws IOException
	{
		File f = new File(Reference.FILE_DECOMPRESSED);
		if (f.createNewFile())
		{
		
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		System.out.println("YES");
		byte[] tq = t.readTree(bits);
		System.out.println("DONE");
		bw.write(new String(tq, StandardCharsets.UTF_8));
		bw.close();
	}
	
}
