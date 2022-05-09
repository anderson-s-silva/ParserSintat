// link video youtube: https://youtu.be/mrDNysup8U8

package AnalisadorLexico;

public class Mainlex{
  public static void main(String[] args){
	 GyhLex lex = new GyhLex();
	 lex.ident_tipotoken("C:\\Users\\Anderson S. Silva\\lista2\\projeto\\GyhLex\\src\\programa99.gyh");
	 
	 ParserSintat teste = new ParserSintat();
	 teste.parser(lex.getArraytoken());
  
  }
}

/**
 * problema com palavra vazia programa 77 e erro desconhecido no programa11
 * */
 