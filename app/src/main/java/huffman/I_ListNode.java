package huffman;

public interface I_ListNode {
	//Intrface para una clase Lista
	public void Add(Object node);
	public void Add(Object node, int pos);
	public void Sort();
	public Object GetElement(int pos);
	public void SuprElement(int pos);
	public boolean isEmpty();
}
