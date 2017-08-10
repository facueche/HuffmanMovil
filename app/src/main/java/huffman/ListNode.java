package huffman;

import node.HuffmanListNode;
import node.HuffmanNode;

public class ListNode implements I_ListNode{
	
	//Atributos
	private HuffmanListNode top;
	private int size;
	
	//Constructor
	public ListNode() {
		CleanList();
	}
	
	//Post-Condicion --> La lista debe estar vacia
	/**
	 * Limpia la lista
	 */
	public void CleanList(){
		this.top = null;
		this.size = 0;
	}

	//Pre-Condicion --> node debe ser un ListNode && != null
	//Post-Condicion --> el tamaño de la lista debe incrementarse
	/**
	 * @param node ListNode : Se agrega el nodo al final de la lista
	 */
	@Override
	public void Add(Object node) {
		if (size == 0){
			this.top = (HuffmanListNode)node;
			this.size++;
		}else{
			this.Add(node, this.size);
		}
		
	}
	
	/* Pre-Condicion --> nodo tiene que ser un ListNode && != null
	 * Pre-Condicion --> pos debe estar entre 0 y size
	 * Post-Condicion --> el tamaño de la lista debe incrementarse
	 */
	/**
	 * @param node ListNode : nodo que se agregara a la lista
	 * @param pos int : posicion en la que se agregara el nodo en la lista
	 */
	@Override
	public void Add(Object node, int pos) {
		if(pos == 0){
			((HuffmanListNode)node).setNext(top);
			if(this.top != null)
				this.top.setPrev((HuffmanListNode)node);
			this.top = ((HuffmanListNode)node);
			size++;
		}else{
			HuffmanListNode temp = (HuffmanListNode) GetElement(pos-1);
			if(temp != null){
				((HuffmanListNode)node).setNext(temp.getNext());
				temp.setNext((HuffmanListNode)node);
				((HuffmanListNode)node).setPrev(temp);
				if(((HuffmanListNode)node).getNext() != null)
					((HuffmanListNode)node).getNext().setPrev((HuffmanListNode) node);
				this.size++;
		}
		
		}
	}

	//Pre-condicion --> La lista debe exitir
	//Post-Condicion --> La lista debe estar ordenada
	/*
	 * Ordena la lista dependiendo la forma en que se implementa el metodo GratterThan
	 * en la clase HuffmanNode
	 */
	@Override
	public void Sort() {
		if(!isEmpty()){
			int i = 0;
			while(i<Size()-1){
				int j = i+1;
				while(j<Size()){
					if(((HuffmanNode)GetElement(i)).isGratterThan((HuffmanNode)GetElement(j))){
	                    HuffmanListNode aux1 = (HuffmanListNode)GetElement(i);
	                    HuffmanListNode aux2 = (HuffmanListNode)GetElement(j);
	                    SuprElement(j);
	                    Add(aux2, i);
	                    SuprElement(i+1);
	                    Add(aux1, j);
	                }
					j++;
				}
	            i++;   
			}
	            
		}else
			System.out.println("No hay lista para ordenar");
	}
	

	//Pre-Condicion --> La Lista debe existir
	//Pre-Condicion --> pos tiene que se un numero entre 0 y size-1
	//Post-Condicion --> el objeto que devuelve el metodo es un ListNode
	/**
	 * @param pos int : Posicion del objeto de la lista que se desea obtener
	 * @return Un objeto de la lista, en este caso un ListNode
	 */
	@Override
	public Object GetElement(int pos) {
		HuffmanListNode temp = top;
		int count = 0;
		if (pos > this.size-1){
			System.out.println("fuera de rango");
			return null;
		}else{
			while (count < pos){
				temp = temp.getNext();
				count++;
			}
			return temp;
		}
		
	}

	//Pre-Condicion --> pos tiene que se un numero entre 0 y size-1
	//Post-Condicion --> el tamaño de la lista debe decrementarse
	/**
	 * @param pos int : Posicion del objeto de la lista que se desea eliminar
	 */
	@Override
	public void SuprElement(int pos) {
		if(pos == 0){
			this.top = top.getNext();
			if(this.top != null){
				this.top.getPrev().setNext(null);
				this.top.setPrev(null);
			}
			size--;
		}else{
			HuffmanListNode temp = (HuffmanListNode)GetElement(pos-1);
			if (temp != null){
				temp.setNext(temp.getNext().getNext());
				if(temp.getNext() != null)
					temp.getNext().setPrev(temp);
				this.size--;
			}
		}
	}
	
	/**
	 * 
	 * @param char : caracter a determinar si ya se agrego a la lista
	 * @return -1 si no se encontraba en la lista o un numero entre 0 y size-1 indicando la posicion en la que se encuentra
	 */
	public int ExistOnList(char a){
		int i = 0;
		while(i<this.Size() && ((HuffmanNode)this.GetElement(i)).getLetter() != a){    // == o != ? tiene que salir cuando lo encuentre
			i++;
		}
		if (i == this.Size())
			return -1;
		else{
			return i;
		}
	}

	/*
	 * Getter de Size
	 */
	public int Size() {
		return size;
	}

	/**
	 * @return true si la lista esta vacia, de lo contrario false
	 */
	@Override
	public boolean isEmpty() {
		return Size() == 0;
	}
	
	/**
	 * Clona esta lista en la lista recibida por parametro
	 * @param list ListNode : lista donde se clonara
	 */
	public void Clone(ListNode list){
		list.CleanList();
		for(int i = 0 ; i < Size() ; i++){
			HuffmanListNode node = new HuffmanListNode();
			node.setCodeWord(((HuffmanListNode)this.GetElement(i)).getCodeWord());
			node.setFrequence(((HuffmanListNode)this.GetElement(i)).getFrequence());
			node.setLetter(((HuffmanListNode)this.GetElement(i)).getLetter());
			list.Add(node);
		}
	}
	
}
