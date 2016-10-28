package core.engine.encoder;

import java.util.ArrayList;

import core.engine.data.collections.DataChain;
import core.engine.data.types.Data;

public class Node implements Comparable<Node>
{
	
	private Node		left	= null;
	private Node		right	= null;
	private DataChain	dc		= null;
	private int			length	= 0;
	private String		encoded	= new String();
								
	public Node(Node n)
	{
		dc = n.getDataChain();
		left = n.getLeft();
		right = n.getRight();
		length = n.getLength();
		encoded = n.getEncoded();
	}
	
	public Node(DataChain dP)
	{
		dc = dP;
		length = 1;
	}
	
	public Node(Data dP)
	{
		dc = new DataChain(dP);
		length = 1;
	}
	
	public Node(Node n1, Node n2)
	{
		dc = new DataChain(n1.getDataChain(), n2.getDataChain());
		right = n1;
		right.appendEncoding('1');
		left = n2;
		left.appendEncoding('0');
		length = n1.getLength() + n2.getLength();
	}
	
	public Node getLeft()
	{
		return left;
	}
	
	public Node getRight()
	{
		return right;
	}
	
	public DataChain getDataChain()
	{
		return dc;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public String getEncoded()
	{
		return encoded;
	}
	
	public boolean isLeaf()
	{
		return ((left == null) && (right == null));
	}
	
	public void appendEncoding(char appnd)
	{
		if (appnd == '0' || appnd == '1')
		{
			String temp = new String();
			temp += appnd;
			temp += encoded;
			encoded = new String(temp);
			if (left != null)
			{
				left.appendEncoding(appnd);
			}
			if (right != null)
			{
				right.appendEncoding(appnd);
			}
		}
		else
		{
			throw new IllegalArgumentException();
		}
		
	}
	
	public ArrayList<Node> getAllSubNodes(Node n)
	{
		return n.getAllSubNodes();
	}
	
	public ArrayList<Node> getAllSubNodes()
	{
		ArrayList<Node> ret = new ArrayList<Node>();
		if (isLeaf())
		{
			ret.add(this);
		}
		else
		{
			ret.addAll(getAllSubNodes(getLeft()));
			ret.addAll(getAllSubNodes(getRight()));
		}
		return ret;
	}
	
	public Node getSubNode(String enc)
	{
		char[] arr = enc.toCharArray();
		Node ret = this;
		if (isLeaf())
		{
			return ret;
		}
		for (char i : arr)
		{
			if ((i != '0') || (i != '1'))
			{
				throw new IllegalArgumentException();
			}
			else
			{
				if (ret.isLeaf())
				{
					return ret;
				}
				else
				{
					if (i == '0')
					{
						ret = new Node(getLeft());
					}
					else
					{
						ret = new Node(getRight());
					}
				}
			}
		}
		return ret;
	}
	
	@Override
	public boolean equals(Object o)
	{
		return (o instanceof Node) && (getDataChain().equals(((Node) o).getDataChain()));
	}
	
	@Override
	public String toString()
	{
		return getDataChain().toString();
	}
	
	@Override
	public int compareTo(Node o)
	{
		return dc.getFrequency() - o.getDataChain().getFrequency();
	}
	
}
