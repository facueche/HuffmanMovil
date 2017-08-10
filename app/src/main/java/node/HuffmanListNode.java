package node;


public class HuffmanListNode extends HuffmanNode{

	//Atributos
	private HuffmanListNode next, prev;
	
	//Cosntructor #1
	public HuffmanListNode(char lett){
		super(lett);
		this.setNext(null);
		this.setPrev(null);
	}
	
	//Constructor #2
	public HuffmanListNode() {
		this.setNext(null);
		this.setPrev(null);
	}

	//Getteres y Setters
	public HuffmanListNode getNext() {
		return next;
	}

	public void setNext(HuffmanListNode next) {
		this.next = next;
	}

	public HuffmanListNode getPrev() {
		return prev;
	}

	public void setPrev(HuffmanListNode prev) {
		this.prev = prev;
	}
	
	
}
