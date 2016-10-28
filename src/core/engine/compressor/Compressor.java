package core.engine.compressor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import core.engine.data.collections.DataChain;
import core.engine.data.types.Data;
import core.engine.encoder.Node;
import core.engine.encoder.tree.DorffTree;
import core.engine.files.writer.BitWriter;
import core.engine.reference.Reference;
import core.engine.utilities.ArrayUtilities;

public class Compressor
{
	private ArrayUtilities<Byte>	arrUtil_Byte	= new ArrayUtilities<Byte>();
	private ArrayUtilities<Data>	arrUtil_Data	= new ArrayUtilities<Data>();
													
	private byte[]					bytes			= Files.readAllBytes(Paths.get(Reference.FILE_TWO));
													
	private Map<Byte, Integer>		byteFrequencies	= new HashMap<Byte, Integer>();
													
	private DorffTree				tree;
									
	public Compressor() throws IOException
	{
		byteFrequencies = arrUtil_Byte.getOneOfEveryInstance(ArrayUtils.toObject(bytes));
		tree = new DorffTree(getNodes(bytes));
		int windowLength = 10000;
		int reps = (int) Math.ceil((bytes.length / windowLength));
		int rem = (bytes.length - (reps * windowLength));
		BitWriter bw = new BitWriter(new File(Reference.FILE_THREE));
		for (int i = 0; i < reps; i++)
		{
			bw.writeEncodedData(compressSequences(getDataArray((i * windowLength), windowLength), tree));
		}
		if (rem != 0)
		{
			bw.writeEncodedData(compressSequences(getDataArray(0, bytes.length), tree));
		}
		bw.flush();
	}
	
	private String compressSequences(Data[] arr, DorffTree tree) throws IOException
	{
		String enc = new String();
		for (int i = 0; i < arr.length; i++)
		{
			enc += tree.getEncoded(new DataChain(arr[i]));
		}
		return enc;
	}
	
	private ArrayList<Node> getNodes(byte[] arr)
	{
		byteFrequencies = arrUtil_Byte.getOneOfEveryInstance(ArrayUtils.toObject(arr));
		ArrayList<Node> ret = new ArrayList<Node>();
		for (Byte i : byteFrequencies.keySet())
		{
			ret.add(new Node(new Data(i.byteValue(), byteFrequencies.get(i))));
		}
		return ret;
	}
	
	private Data[] getDataArray(int start, int length)
	{
		ArrayList<Data> ret = new ArrayList<Data>();
		for (int i = start; i < (start + length); i++)
		{
			ret.add(new Data(bytes[i], byteFrequencies.get(Byte.valueOf(bytes[i]))));
		}
		return arrUtil_Data.toArray(ret, new Data());
	}
	
	public DorffTree getTree()
	{
		return tree;
	}
	
}
