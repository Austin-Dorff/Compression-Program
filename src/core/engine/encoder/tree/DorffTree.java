package core.engine.encoder.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import core.engine.data.collections.DataChain;
import core.engine.encoder.Node;
import core.engine.utilities.ArrayUtilities;

public class DorffTree
{
	
	private ArrayUtilities<Byte>	arrUtil_Byte	= new ArrayUtilities<Byte>();
	private ArrayUtilities<Node>	arrUtil_Node	= new ArrayUtilities<Node>();
													
	private Map<Node, DataChain>	treeLocations	= new HashMap<Node, DataChain>();
	private Map<DataChain, Node>	treeLocations2	= new HashMap<DataChain, Node>();
													
	private Node					top;
	private Node					right;
	private Node					bottom;
	private Node					left;
									
	private int						length;
									
	public DorffTree(List<Node> nodes)
	{
		Collections.sort(nodes);
		this.length = nodes.size();
		if (length >= 12)
		{
			create4(nodes);
		}
		else
		{
			top = create1(nodes);
		}
	}
	
	private Node create1(List<Node> nodes)
	{
		List<Node> temp = new ArrayList<Node>(nodes);
		ArrayList<Node> temp2 = new ArrayList<Node>();
		int rem = 0, lim = 0, counter = 0;
		while (temp.size() > 1)
		{
			temp2.clear();
			lim = (temp.size() / 2);
			if ((temp.size() != nodes.size()) && (temp.size() % 2 == 0) && (temp.size() != 2))
			{
				lim--;
			}
			rem = (temp.size() - (lim * 2));
			counter = 0;
			for (int i = 0; i < lim; i++)
			{
				Node t = new Node(temp.get(counter++), temp.get(counter++));
				temp2.add(t);
			}
			for (int i = rem; i > 0; i--)
			{
				temp2.add(new Node(temp.get(temp.size() - i)));
			}
			temp = new ArrayList<Node>(temp2);
		}
		addNodesToTreeLocations(temp.get(0));
		return temp.get(0);
	}
	
	private void addNodesToTreeLocations(Node n)
	{
		ArrayList<Node> arr = n.getAllSubNodes();
		for (Node i : arr)
		{
			treeLocations2.put(i.getDataChain(), i);
			treeLocations.put(i, i.getDataChain());
		}
	}
	
	private void create4(List<Node> nodes)
	{
		int counter = 0;
		int check = (nodes.size() / 4);
		int placeHolder = 0;
		while (check > 0)
		{
			check -= (4 * (int) Math.pow(2, counter++));
		}
		int sum = 0;
		int tempI = counter;
		while (tempI > 0)
		{
			sum += (4 * (int) Math.pow(2, tempI));
			tempI--;
		}
		if (sum != nodes.size())
		{
			counter++;
		}
		int numPerLayer = ((int) Math.pow(2, counter));
		ArrayList<Node> topNodes = new ArrayList<Node>();
		counter = 0;
		placeHolder = numPerLayer;
		Main:
		while (numPerLayer > 0)
		{
			for (int z = 0; z < numPerLayer; z++)
			{
				if ((z * 4 + counter) >= nodes.size())
				{
					break Main;
				}
				topNodes.add(nodes.get(z * 4 + counter));
			}
			counter += numPerLayer * 4;
			numPerLayer /= 2;
		}
		top = create1(topNodes);
		ArrayList<Node> rightNodes = new ArrayList<Node>();
		counter = 1;
		numPerLayer = placeHolder;
		Main:
		while (numPerLayer > 0)
		{
			for (int z = 0; z < numPerLayer; z++)
			{
				if ((z * 4 + counter) >= nodes.size())
				{
					break Main;
				}
				rightNodes.add(nodes.get(z * 4 + counter));
			}
			counter += numPerLayer * 4;
			numPerLayer /= 2;
		}
		right = create1(rightNodes);
		ArrayList<Node> bottomNodes = new ArrayList<Node>();
		counter = 2;
		numPerLayer = placeHolder;
		Main:
		while (numPerLayer > 0)
		{
			for (int z = 0; z < numPerLayer; z++)
			{
				if ((z * 4 + counter) >= nodes.size())
				{
					break Main;
				}
				bottomNodes.add(nodes.get(z * 4 + counter));
			}
			counter += numPerLayer * 4;
			numPerLayer /= 2;
		}
		bottom = create1(bottomNodes);
		ArrayList<Node> leftNodes = new ArrayList<Node>();
		counter = 3;
		numPerLayer = placeHolder;
		Main:
		while (numPerLayer > 0)
		{
			for (int z = 0; z < numPerLayer; z++)
			{
				if ((z * 4 + counter) >= nodes.size())
				{
					break Main;
				}
				leftNodes.add(nodes.get(z * 4 + counter));
			}
			counter += numPerLayer * 4;
			numPerLayer /= 2;
		}
		left = create1(leftNodes);
		top.appendEncoding('0');
		top.appendEncoding('0');
		right.appendEncoding('1');
		right.appendEncoding('0');
		bottom.appendEncoding('1');
		bottom.appendEncoding('1');
		left.appendEncoding('0');
		left.appendEncoding('1');
	}
	
	public Map<DataChain, Node> getDataChainNodeMap()
	{
		return treeLocations2;
	}
	
	public Map<Node, DataChain> getNodeDataChainMap()
	{
		return treeLocations;
	}
	
	public Node getTop()
	{
		return top;
	}
	
	public Node getRight()
	{
		return right;
	}
	
	public Node getBottom()
	{
		return bottom;
	}
	
	public Node getLeft()
	{
		return left;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public String getEncoded(DataChain dc)
	{
		for (DataChain i : treeLocations2.keySet())
		{
			if (i.equals(dc))
			{
				return treeLocations2.get(i).getEncoded();
			}
		}
		throw new IllegalArgumentException();
	}
	
	public byte[] readTree(String enc)
	{
		ArrayList<Byte> ret = new ArrayList<Byte>();
		if (length >= 12)
		{
			if (enc.length() < 3)
			{
				return null;
			}
			else
			{
				char first = ' ';
				char second = ' ';
				int counter = 0;
				Node node = null;
				char i2 = ' ';
				Main:
				while (true)
				{
					if (counter >= (enc.length() - 4))
					{
						break;
					}
					first = enc.charAt(counter++);
					second = enc.charAt(counter++);
					if (first == '0')
					{
						if (second == '0')
						{
							node = top;
							while (!node.isLeaf() && (counter < enc.length()))
							{
								i2 = enc.charAt(counter++);
								if ((i2 != '0') && (i2 != '1'))
								{
									throw new IllegalArgumentException();
								}
								else
								{
									if (i2 == '0')
									{
										node = node.getLeft();
									}
									else
									{
										node = node.getRight();
									}
								}
							}
							if (node.isLeaf())
							{
								ret.addAll(Arrays.asList(ArrayUtils.toObject(node.getDataChain().getChainBytes())));
								continue Main;
							}
						}
						else if (second == '1')
						{
							node = right;
							while (!node.isLeaf() && (counter < enc.length()))
							{
								i2 = enc.charAt(counter++);
								if ((i2 != '0') && (i2 != '1'))
								{
									throw new IllegalArgumentException();
								}
								else
								{
									if (i2 == '0')
									{
										node = node.getLeft();
									}
									else
									{
										node = node.getRight();
									}
								}
							}
							if (node.isLeaf())
							{
								ret.addAll(Arrays.asList(ArrayUtils.toObject(node.getDataChain().getChainBytes())));
								continue Main;
							}
						}
					}
					else if (first == '1')
					{
						if (second == '0')
						{
							node = left;
							while (!node.isLeaf() && (counter < enc.length()))
							{
								i2 = enc.charAt(counter++);
								if ((i2 != '0') && (i2 != '1'))
								{
									throw new IllegalArgumentException();
								}
								else
								{
									if (i2 == '0')
									{
										node = node.getLeft();
									}
									else
									{
										node = node.getRight();
									}
								}
							}
							if (node.isLeaf())
							{
								ret.addAll(Arrays.asList(ArrayUtils.toObject(node.getDataChain().getChainBytes())));
								continue Main;
							}
						}
						else if (second == '1')
						{
							node = bottom;
							while (!node.isLeaf() && (counter < enc.length()))
							{
								i2 = enc.charAt(counter++);
								if ((i2 != '0') && (i2 != '1'))
								{
									throw new IllegalArgumentException();
								}
								else
								{
									if (i2 == '0')
									{
										node = node.getLeft();
									}
									else
									{
										node = node.getRight();
									}
								}
							}
							if (node.isLeaf())
							{
								ret.addAll(Arrays.asList(ArrayUtils.toObject(node.getDataChain().getChainBytes())));
								continue Main;
							}
						}
					}
				}
			}
		}
		else
		{
			if (enc.length() < 1)
			{
				return null;
			}
			else
			{
				Node node = top;
				char i2 = ' ';
				int counter = 0;
				Main:
				while (true)
				{
					node = top;
					if (counter >= (enc.length() - 1))
					{
						break;
					}
					while (!node.isLeaf() && (counter < enc.length()))
					{
						i2 = enc.charAt(counter++);
						if ((i2 != '0') && (i2 != '1'))
						{
							throw new IllegalArgumentException();
						}
						else
						{
							if (i2 == '0')
							{
								node = node.getLeft();
							}
							else
							{
								node = node.getRight();
							}
						}
					}
					if (node.isLeaf())
					{
						ret.addAll(Arrays.asList(ArrayUtils.toObject(node.getDataChain().getChainBytes())));
						continue Main;
					}
				}
			}
		}
		return ArrayUtils.toPrimitive(arrUtil_Byte.toArray(ret, new Byte("127")));
	}
	
}
