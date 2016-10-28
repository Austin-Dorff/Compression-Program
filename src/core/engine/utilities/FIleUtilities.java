package core.engine.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import core.engine.reference.Reference;

public class FIleUtilities
{
	
	public static void createMassiveFile(int size) throws IOException
	{
		File f = new File(Reference.FILE_FOUR);
		FileWriter fw = new FileWriter(f);
		int rand = 0;
		for (int i = 0; i < size; i++)
		{
			rand = (int) (Math.random() * 127) - 256;
			fw.write(String.valueOf((char) ((byte) rand)));
		}
		fw.close();
	}
	
}
