package node;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HuffmanNode extends AbstractNode implements Serializable{

	protected char letter = '\0';
	protected String codeWord = "";
	protected int frequence = 0;
	protected HuffmanNode left = null, right = null;
	
	public HuffmanNode(char letter) {
		this.letter = letter;
		this.frequence++;
	}
	
	public HuffmanNode() {}
	
	//Pre-Condicion --> node tiene que ser un HuffmanNode
	/**
	 * Si este nodo es mayor que el recibido se devolvera true, de lo contrario se devolvera false
	 * @param node HuffmanNode: se compara este nodo el nodo recibido.
	 */
	@Override
	public boolean isGratterThan(Object node) {
		return this.frequence > ((HuffmanNode)node).getFrequence();
	}
	
	/**
	 * Cuando se llama a este metodo, la frecuencia se incrementa en 1
	 */
	public void AddFrequence(){
		this.frequence++;
	}
	
	//Pre-Condicion --> node tiene que ser un HuffmanNode
	/**
	 * @param node HuffmanNode: se comprara este nodo con el nodo recibido y si son iguales
	 * se devuelve true, de lo contrario se devolvera falso
	 */
	@Override
	public boolean Equals(Object o){
		return this.equals((HuffmanNode)o);
	}
	
	/*
	 * Getters y Setters de los atributos
	 */
	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public String getCodeWord() {
		return codeWord;
	}

	public void setCodeWord(String codeWord) {
		this.codeWord = codeWord;
	}

	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public HuffmanNode getLeft() {
		return left;
	}

	public void setLeft(HuffmanNode left) {
		this.left = left;
	}

	public HuffmanNode getRight() {
		return right;
	}

	public void setRight(HuffmanNode right) {
		this.right = right;
	}
}
