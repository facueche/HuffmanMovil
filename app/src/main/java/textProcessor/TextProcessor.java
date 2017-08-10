package textProcessor;

import huffman.ListNode;

import java.io.IOException;
import java.io.InputStreamReader;

import node.HuffmanListNode;
import node.HuffmanNode;

/**
 * 
 * @author Daniel Echenique
 * Pequeña clase que sirve para procesar un archivo de texto
 *
 */
public class TextProcessor {
	
	//Post-Condicion --> Devuelve el un String
	/**
	 * 
	 * @param buffer BufferedReader : buffer donde esta cargado el texto leido del archivo de texto
	 * @return Un String con el texto del buffer
	 */
	public String ReadTextBuffer(InputStreamReader buffer){
		String inputText = "";
	    try {
			while (buffer.ready()) {
				inputText += (char)buffer.read();
			 }
		    buffer.close();
			System.out.println("TEXT: "+inputText);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return inputText;
	}
	
	/**
	 * 
	 * @param list ListNode : Lista donde se cargaran los caracteres con sus frecuecias
	 * @param inputText String : Texto del cual se obtendran caracteres y frecuencias
	 * 
	 */
	public void ProcessText(ListNode list, String inputText){
		for(int i = 0 ; i<inputText.length(); i++){
			int j = list.ExistOnList(inputText.charAt(i));
			if(j!=-1){
				((HuffmanNode)list.GetElement(j)).AddFrequence();
			}else{
				HuffmanListNode node= new HuffmanListNode(inputText.charAt(i));
				list.Add(node);
			}
		}
	}
	
	/**
	 * metodo para debugear el programa...
	 * @param list ListNode : lista que se mostrara
	 * 
	 */
	public void Show(ListNode list){
		for(int i = 0 ; i<list.Size() ; i++){
			System.out.println("Letra "+i+" : "+((HuffmanListNode)list.GetElement(i)).getLetter()+"  Frecuencia : "+String.valueOf(((HuffmanListNode)list.GetElement(i)).getFrequence()));
		}
		
	}
}
