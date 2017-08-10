package huffman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import node.HuffmanListNode;
import node.HuffmanNode;
import textProcessor.TextProcessor;


public class HuffmanTree{

	//Atributos
	private String inputText , code = ""; 
	private HuffmanNode tree = null;
	private ListNode list = new ListNode();
	final private byte BIT = 8;
	private String encoding;
	
	//Constructor
	public HuffmanTree(String textPath, boolean code) {
		FileInputStream path;
		try {
			path = new FileInputStream(textPath);
			this.encoding = new InputStreamReader(path).getEncoding();
		} catch (FileNotFoundException e) {}
		Huffman(textPath, code);
	}
	
	public HuffmanTree(String textPath, boolean code, String coding) {
		this.encoding = coding;
		Huffman(textPath, code);
	}
	/**
	 * @param textPath String : Direccion del archivo de texto
	 * @param code boolean : true para comprimir, false para descomprimir
	 */
	public void Huffman(String textPath, boolean code) {
		InputStreamReader bf = null;
		FileInputStream path = null;
		try {
			//se carga en el InputStream el archivo de texto y se guarda su codificacion
			path = new FileInputStream(textPath);
			bf = new InputStreamReader(path, encoding);
			System.out.println("ENCODING: "+bf.getEncoding());		//DEBUG
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(code){
			//Procesa Texto
			ProcessText(bf);
			//clona list en clonedList
			ListNode clonedList = new ListNode();
			list.Clone(clonedList);
			//Arma el arbol de Huffman
			BuildTree(new TextProcessor());		//Borrar el parametro (solo para debug)
			//Asigna el codigo binario a cada letra
			AssignBinary(clonedList, this.tree, "");
			//DEBUG
			System.out.println("\n\n\nLISTA DE CODIGOS");
			for(int i = 0 ; i<clonedList.Size() ; i++){
				System.out.println("Letra : "+((HuffmanListNode)clonedList.GetElement(i)).getLetter()+"  Code : "+((HuffmanListNode)clonedList.GetElement(i)).getCodeWord());
			}
			//Codifica el texto
			Code(clonedList);
			//Guarda el archivo comprimido
			try {
				Save(textPath,clonedList);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			Decode(bf);
			GenerateListNodeAndCode();
			System.out.println("ORIGINAL : "+GenerateOriginalStringFile());		//DEBUG
			WriteFile(GenerateOriginalStringFile(), RemoveExtension(textPath));
		}
	}
	
	
	private void ReadText(InputStreamReader buffer, TextProcessor processor){
		//a inputText se le asigna el valor devuelto de la lectura del buffer (se carga el texto en un String del archivo de texto leido)
		this.inputText = processor.ReadTextBuffer(buffer);
	}
	
	
	
////////////////////////////////////////////////////////CODE///////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////CODE///////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////CODE///////////////////////////////////////////////////////////////////////////////////

	
	
	/**
	 * @param buffer BufferedReader : Buffer donde se almacena el archivo a ser leido
	 */
	private void ProcessText(InputStreamReader buffer){
		//procesor es un objeto que realiza el manejo del texto
		TextProcessor processor = new TextProcessor();
		ReadText(buffer, processor);
		//Se carga la lista con los caracteres (sin repetir) con sus correspondientes frecuencias
		processor.ProcessText(list, inputText);
		//Debug
		processor.Show(list);
		//Ordena la lista
		this.list.Sort();
		//DEBUG
		System.out.println("\n\n");
		processor.Show(list);
		System.out.println("\n\n");
	}
	
	//Pre-Condicion --> La lista no debe estar vacia
	/**
	 * Arma el arbol de Huffman
	 */
	private void BuildTree(TextProcessor processor){
		if(!this.list.isEmpty()){
			while (((HuffmanListNode)this.list.GetElement(0)).getNext() != null){
				HuffmanNode node = new HuffmanListNode();
				node.setLeft((HuffmanNode)this.list.GetElement(0));
				node.setRight((HuffmanNode)this.list.GetElement(1));
				node.setFrequence(node.getLeft().getFrequence()+node.getRight().getFrequence());
				this.list.SuprElement(0);
				this.list.SuprElement(0);
				this.list.Add(node, 0);
				this.list.Sort();
				//DEBUG
				processor.Show(list);
				System.out.println("\n\n");
			}
			this.tree = (HuffmanNode)this.list.GetElement(0);
		}
	}
	
	/**
	 * Cuando se encuentre un nodo hoja en tree, se buscara la letra correspondiente en list y se le asignara el codigo que se va
	 * almacenando recursivamente en code
	 * @param list ListNode : Lista donde se guardaran los codeWords
	 * @param tree HuffmanNode : Arbol de Huffman
	 * @param code String : codigo binario que se le asignara a la letra
	 * 
	 */
	private void AssignBinary(ListNode list, HuffmanNode tree, String code){
		if(tree.getLeft() == null && tree.getRight() == null){
			((HuffmanNode)list.GetElement(list.ExistOnList(tree.getLetter()))).setCodeWord(code);
		}else{
			AssignBinary(list, tree.getLeft(), code+"0");
			AssignBinary(list, tree.getRight(), code+"1");
		}
	}
	
	/**
	 * Genera el codigo binario de todo el texto
	 * @param list ListNode : Lista donde se encuentran los caracteres 
	 */
	private void Code(ListNode list){
		for(int i = 0 ; i < this.inputText.length() ; i++){
			this.code += ((HuffmanNode)list.GetElement(list.ExistOnList(this.inputText.charAt(i)))).getCodeWord();
		}
		
		System.out.println("Codigo!!! : "+this.code);		//DEBUG
	}
	
	/**
	 * Metodo que devulevo el caracter correspondiente en el codigo ASCII al numero (en binario) recibido
	 * Ej: El metodo recibe el valor "01000001" que equivale a 65
	 * -> Devuelve el caracter en ascii equivalente a 65, que es A
	 * @param bin String : Recibe un numero en binario (base 2) 
	 * @return Devuelve un caracter
	 */
	private char GetLetterFromBinary(String bin){
		System.out.println("BINARIO : "+Integer.parseInt(bin));
		System.out.println("DECIMAL : "+Integer.parseInt(bin,2));
		System.out.println("LETRA: "+(char)Integer.parseInt(bin,2));
//		return ascii.getChar(Integer.parseInt(bin,2));
		return (char)Integer.parseInt(bin,2);
	}
	
	
	/**
	 * Se recibe un codigo en binario y se devuelve su equivalente en ascii UTF-8 (caracteres de 8 bits)
	 * @param code String : Recibe un numero en binario (base 2)
	 * @return Devuelve un String
	 */
	private String BinToWord(String code){
		int c = 0;
		while(code.length()%(BIT) != 0){
			code+="0";
			c++;
		}
		System.out.println(code.length());
		String temp = "";
		while(code.length() > 0){
			System.out.println("BIN : "+code.substring(0, (BIT)));		//DEBUG
			System.out.println("TAMA�O : "+code.length());		//DEBUG
			temp+=String.valueOf(GetLetterFromBinary(code.substring(0, (BIT))));
			code = code.substring(BIT, code.length());
		}
		temp+=String.valueOf(c);
		return temp;
	}
	
	/**
	 * @param path String : Direccion donde se guardare el archivo
	 * @param list ListNode : Lista donde se encuentra el alfabeto
	 */
	private void Save(String path,ListNode list) throws IOException{
		System.out.println(this.code);						//DEBUG
		String dataCode ="";
		System.out.println(dataCode);						//DEBUG
		for(int j = 0 ; j < list.Size() ; j++){
			String temp;
			if(j == list.Size()-1)
				temp = "e\n";
			else
				temp = "\n";
			dataCode += ((HuffmanNode)list.GetElement(j)).getLetter()+((HuffmanNode)list.GetElement(j)).getCodeWord()+temp;
		}
		dataCode+= BinToWord(this.code);
		System.out.println(dataCode);
		WriteFile(dataCode, AddExtension(path,".dbz"));
		
	}
	
	/**
//	 * @param String : Datos que seran escritos en un nuevo archivo
	 * @param path : Direccion donde se guardara
	 */
	private void WriteFile(String dataToWrite, String path){
		Writer out;
		try {
			out = new OutputStreamWriter(new FileOutputStream(path), this.encoding);
			out.write(dataToWrite);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param path String : Direccion donde se encuenta el archivo
	 * @param extension String : Extension que se le agregara. Ej: ".txt"
	 * @return
	 */
	private String AddExtension(String path, String extension){
		return path+extension;
	}
	
	/**
	 * @param path String : Direccion donde se encuenta el archivo
	 * @return Direccion del archivo sin su extension
	 */
	private String RemoveExtension(String path){
		return path.substring(0, path.length()-4);
	}
	
	
	
//////////////////////////////////////////////////////DECODE///////////////////////////////////////////////////////////////////////////////////
	
//////////////////////////////////////////////////////DECODE///////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////DECODE///////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * @param buffer BufferedReader : Buffer donde se almacena el archivo a ser leido
	 */
	private void Decode(InputStreamReader buffer){
		//procesor es un objeto que realiza el manejo del texto
		TextProcessor processor = new TextProcessor();
		ReadText(buffer, processor);
	}
	
	/**
	 * Metodo que genera la lista con el alfabeto y el codigo binario que debe decodificar
	 */
	private void GenerateListNodeAndCode(){
		this.list.CleanList();
		if(this.inputText.length() != 0){
			int i = 0;
			boolean flag = true;
			while(true){
				if(flag){
					this.list.Add(new HuffmanListNode(this.inputText.charAt(i)));
					i++;
					flag = false;
				}else{
					int temp = i;
					while("0".equals(String.valueOf(this.inputText.charAt(i+1))) || "1".equals(String.valueOf(this.inputText.charAt(i+1)))){
						i++;
					}
					if(!list.isEmpty())
						((HuffmanNode)list.GetElement(list.Size()-1)).setCodeWord(inputText.substring(temp, i+1));
					if("e\n".equals(String.valueOf(inputText.charAt(i+1))+String.valueOf(inputText.charAt(i+2)))){
						int quitBits = Integer.parseInt(String.valueOf(inputText.charAt(inputText.length()-1)));
						System.out.println(quitBits);	//DEBUG
						this.code = GenerateBinCode(inputText.substring(i+3, inputText.length()-1));
						for (int j = 0 ; j < quitBits ; j++){
							this.code = this.code.substring(0, this.code.length()-1);
						}
						break;
					}
					i+=2;
					flag = true;
				}
			}
		}
		//DEBUG
		for(int i = 0; i< list.Size(); i++){
			System.out.println("Letra : "+((HuffmanListNode)list.GetElement(i)).getLetter()+"  Code : "+((HuffmanListNode)list.GetElement(i)).getCodeWord());
		}
		System.out.println(this.code);
	}
	
	/**
	 * @param code String : Codigo binario que convertira a caracter
	 * @return String : palabra obtendia del codigo recibido
	 */
	private String GenerateBinCode(String code){
		System.out.println("CODIGO A DESCOMPRESAR: "+code);		//DEBUG
		String temp = "";
		for(int i = 0 ; i < code.length() ; i++){
			String temp2 = Integer.toBinaryString((int)(code.charAt(i)));
			System.out.println(temp2);		//DEBUG
			while(temp2.length() < BIT){
				temp2 = "0"+temp2;
			}
			System.out.println("TEMP2: "+temp2);		//DEBUG
			temp+=temp2;
		}
		return temp;
	}
	
	/**
	 * @return Devuelve el texto original desde el archivo comprimido
	 */
	private String GenerateOriginalStringFile(){
		String temp = "", code = "";
		ListNode tempList = new ListNode();
		this.list.Clone(tempList);
		for(int i = 0 ; i < this.code.length() ; i++){
			temp+=this.code.charAt(i);
			int j = 0;
			while(j < tempList.Size()){
				if(temp.length() <= ((HuffmanNode)tempList.GetElement(j)).getCodeWord().length()){
					if(!temp.equals(((HuffmanNode)tempList.GetElement(j)).getCodeWord().substring(0, temp.length()))){
						tempList.SuprElement(j);
					}else{
						j++;
					}
				}else{
					tempList.SuprElement(j);
				}
				
			}
			if(tempList.Size() == 1){
				System.out.println("TEMP: "+temp);
				code+=((HuffmanNode)tempList.GetElement(0)).getLetter();
				temp = "";
				this.list.Clone(tempList);
			}
		}
		return code;
	}
	
	//Getter del Arbol 
	public HuffmanNode getTree() {
		return tree;
	}

	//Getter del codigo en binario
	public String getCode() {
		return code;
	}
}