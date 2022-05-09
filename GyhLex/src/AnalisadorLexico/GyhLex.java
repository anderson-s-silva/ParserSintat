//Ideias da utilizacao da tabela ASCII reusada do grupo do Cristian Veggian
package AnalisadorLexico;

import java.util.ArrayList;

public class GyhLex {
	ArrayList<Token> arraytoken = new ArrayList<Token>(); //armazenar todos os tokens
	ArrayList<String> arraylinhas = new ArrayList<String>(); //armazenar linha por linha de todo o arquivo
	public int cont_linhas = 0; //contar as linhas do arquivo
	public LeitorArquivo ldat;
	public GyhLex() {
		//constructor
	}
	public void ident_tipotoken(String arquivo) {
		LeitorArquivo novoarq = new LeitorArquivo();
		this.arraylinhas = novoarq.lerLinhas(arquivo); //This array is getting the entire file
		String lexema = ""; //uma palavra inteira
		String flexoes = ""; //unidade do lexema
		for(int j = 0; j < this.arraylinhas.size(); j++) { //percorre o array e detem os itens
			flexoes = this.arraylinhas.get(j);//flexoes recebe o item(linha) do array
			cont_linhas++; // contador de linhas
			for(int i = 0; i < flexoes.length(); i++) {//Start for  //percorre os caracteres da linha
				/////////////////////////////////////////////////////////////////////////////////////////////
				///////////////ANALISA AS OPERACOES QUE TEM O CARACTERE '='//////////////////////////////////
				if(i != flexoes.length() - 1 && flexoes.charAt(i+1) == '=') {//if // verificar se o proximo caractere é um '='
					if(flexoes.charAt(i) == '=') { //analisa o caractere anterior ao '='
						this.arraytoken.add(new Token(TipoToken.OpRelIgual, "==", this.cont_linhas));
						i++;
					}else if(i != flexoes.length() - 2 && flexoes.charAt(i+2) == '=') {
						this.arraytoken.add(new Token(TipoToken.OpRelIgual, "==", this.cont_linhas));
						i = i+2;	
					}else if(flexoes.charAt(i) == ':') {
						this.arraytoken.add(new Token(TipoToken.Atrib, ":=", this.cont_linhas));
						i++;
					}else if(flexoes.charAt(i) == '!') {
						this.arraytoken.add(new Token(TipoToken.OpRelDif, "!=", this.cont_linhas));
						i++;
					}else if(flexoes.charAt(i) == '>') {
						this.arraytoken.add(new Token(TipoToken.OpRelMaiorIgual, ">=", this.cont_linhas));
						i++;
					}else if(flexoes.charAt(i) == '<') {
						this.arraytoken.add(new Token(TipoToken.OpRelMenorIgual, "<=", this.cont_linhas));
						i++;
					}else {
						System.out.println("=" + "ERROR: caractere inválido. Linha: " + this.cont_linhas + " Caractere:"+i);// erro retorna a linha e o caractere inicial do erro
						System.exit(1);
					}
				}//end if
				/////////////////////////////////////////////////////////////////////////////////////////////
				///////////////ANALISA OS CARACTERES DA LINGUAGEM////////////////////////////////////////////
				else if(flexoes.charAt(i) == '<') {
					this.arraytoken.add(new Token(TipoToken.OpRelMenor, "<", this.cont_linhas));
				}else if(flexoes.charAt(i) == '>') {
					this.arraytoken.add(new Token(TipoToken.OpRelMaior, ">", this.cont_linhas));
				}else if(flexoes.charAt(i) == '*') {
					this.arraytoken.add(new Token(TipoToken.OpAritMult, "*", this.cont_linhas));
				}else if(flexoes.charAt(i) == '/') {
					this.arraytoken.add(new Token(TipoToken.OpAritDiv, "/", this.cont_linhas));
				}else if(flexoes.charAt(i) == '+') {
					this.arraytoken.add(new Token(TipoToken.OpAritSoma, "+", this.cont_linhas));
				}else if(flexoes.charAt(i) == '-') {
					this.arraytoken.add(new Token(TipoToken.OpAritSub, "-", this.cont_linhas));
				}else if(flexoes.charAt(i) == ':') {
					this.arraytoken.add(new Token(TipoToken.Delim, ":", this.cont_linhas));
				}else if(flexoes.charAt(i) == '(') {
					this.arraytoken.add(new Token(TipoToken.AbrePar, "(", this.cont_linhas));
				}else if(flexoes.charAt(i) == ')') {
					this.arraytoken.add(new Token(TipoToken.FechaPar, ")", this.cont_linhas));
				}else if(flexoes.charAt(i) == ' ') { // espacos sao ignorados
					continue;
				}else if(flexoes.charAt(i) == '#') { //se encontrar um comentario passa para a proxima linha
					i = flexoes.length();
				/////////////////////////////////////////////////////////////////////////////////////////////
				///////////////ANALISA OS NUMEROS REAIS E INTEIROS///////////////////////////////////////////
				}else if(flexoes.charAt(i) >= 48 && flexoes.charAt(i) <= 57) {//numbers //verificar se os numeros estao entre 0 e 9 de acordo com a tabela ASCII
					int contadorPontos = 0; //contador d epontos para auxiliar na verificacao dos numeros reais
					while(i < flexoes.length()) { //enquanto o i for menor que a linha
						if(flexoes.charAt(i) >= 48 && flexoes.charAt(i) <= 57) { // se o i ainda pertencer aos numeros que estao entre 0 e 9 de acordo com a tabela ASCII
							lexema = lexema.concat(Character.toString(flexoes.charAt(i))); //concatenamos os numeros encontrados na linha de modo que se forme o numero digitado
							i++;
						}else if(flexoes.charAt(i) == '.') { //se encontrar um ponto
							if(contadorPontos < 2) { //os pontos podem ser 0 ou 1 apenas, nao existem numertos com mais de 1 ponto
								lexema = lexema.concat(Character.toString(flexoes.charAt(i))); //concatena o ponto
								i++;
								contadorPontos++;
							}else {//se houver mais de um ponto, retorna erro
								System.out.println(lexema + "ERROR: palavra inválida. Linha: " + this.cont_linhas);// erro retorna a linha 
								System.exit(1);
							}
						}else break;
						
					}//while()
					i--;
					if(lexema.contains(".")) {//real numbers // se houver um ponto eh caracterizado como um numero real 
						this.arraytoken.add(new Token(TipoToken.NumReal, lexema, this.cont_linhas));
						lexema = "";//esvazia o lexema
					}else {//integers numbers // se nao houver pontos eh caracterizado como numero inteiro 
						this.arraytoken.add(new Token(TipoToken.NumInt, lexema, this.cont_linhas));
						lexema = "";//esvazia o lexema
					}
				}//end if numbers
				/////////////////////////////////////////////////////////////////////////////////////////////
				///////////////ANALISA A CADEIA DE CARACTERES////////////////////////////////////////////////
				else if(flexoes.charAt(i) == '"') {//chain // se i for igual a uma aspas dupla
					lexema = lexema.concat("\"");//concatena a aspas dupla
					i++;
					while(i < flexoes.length()) {// enquanto i menor que a linha
						if(flexoes.charAt(i) != '"') {// se i for diferente de uma aspas dupla
							lexema = lexema.concat(Character.toString(flexoes.charAt(i))); //concatena todos os caracteres dentro da aspas 
							
							i++;
						}else break;
					}
					if(i < flexoes.length() && flexoes.charAt(i) == '"') {
						lexema = lexema.concat("\"");//concatena a aspas dupla final
					}else {
						System.out.println(lexema + " ERROR: palavra inválida. Linha: " + this.cont_linhas);// erro retorna a linha 
						System.exit(1);
					}
					this.arraytoken.add(new Token(TipoToken.Cadeia, lexema, this.cont_linhas));
					lexema = "";//esvazia o lexema
					//end chain
				/////////////////////////////////////////////////////////////////////////////////////////////
				///////////////ANALISA AS PALAVRAS CONSIDERADAS VARIAVEIS////////////////////////////////////
				}else if(flexoes.charAt(i) >= 97 && flexoes.charAt(i) <= 122) {//variables // se o i estiver entre 'a' minusculo e 'z' minusculo, pois deve começar com uma letra minuscula
					while(i < flexoes.length()) {// enquanto i menor que o tamanho da linha 
						if(flexoes.charAt(i) >= 97 && flexoes.charAt(i) <= 122) {//se i estiver entre 'a' minusculo e 'z' minusculo
							lexema = lexema.concat(Character.toString(flexoes.charAt(i)));//concatena a letra minusculoa
						}else if(flexoes.charAt(i) >= 65 && flexoes.charAt(i) <= 90) {//se i estiver entre 'A' maiusculo e 'Z' maiusculo
							lexema = lexema.concat(Character.toString(flexoes.charAt(i)));// concatena a letra maiuscula
						}else if(flexoes.charAt(i) >= 48 && flexoes.charAt(i) <= 57) {// se i estive entre 0 e 9
							lexema = lexema.concat(Character.toString(flexoes.charAt(i)));// concatena o numero
						}else break;
						i++;
					}//end while
					i--;
					this.arraytoken.add(new Token(TipoToken.Var, lexema, this.cont_linhas));
					lexema = "";//esvazia o lexema
				}//end variables
				/////////////////////////////////////////////////////////////////////////////////////////////
				///////////////ANALISA AS PALAVRAS CONSIDERADAS RESERVADAS///////////////////////////////////
				else if(flexoes.charAt(i) >= 65 && flexoes.charAt(i) <= 90) {//reserved words // se i estiver entre 'A' maiusculo e 'Z' maiusculo, pois as palavras reservadas começam com letra maiuscula
					
					while(i < flexoes.length()) {// enquanto i menor que a linha
						
						if(flexoes.charAt(i) >= 65 && flexoes.charAt(i) <= 90) {// se i estiver entre 'A' maiusculo e 'Z' maiusculo
							lexema = lexema.concat(Character.toString(flexoes.charAt(i)));// concatena a letra maiuscula
							i++;
						}else break;//as palavras reservadas so possuem letras maiusculas
					}//while
					i--;
					if(lexema.equals("E")) {
						this.arraytoken.add(new Token(TipoToken.OpBoolE, lexema, this.cont_linhas));
					}else if(lexema.equals("OU")) {
						this.arraytoken.add(new Token(TipoToken.OpBoolOu, lexema, this.cont_linhas));
					}else if(lexema.equals("DEC")) {
						this.arraytoken.add(new Token(TipoToken.PCDec, lexema, this.cont_linhas));
					}else if(lexema.equals("PROG")) {
						this.arraytoken.add(new Token(TipoToken.PCProg, lexema, this.cont_linhas));
					}else if(lexema.equals("INT")) {
						this.arraytoken.add(new Token(TipoToken.PCInt, lexema, this.cont_linhas));
					}else if(lexema.equals("LER")) {
						this.arraytoken.add(new Token(TipoToken.PCLer, lexema, this.cont_linhas));
					}else if(lexema.equals("REAL")) {
						this.arraytoken.add(new Token(TipoToken.PCReal, lexema, this.cont_linhas));
					}else if(lexema.equals("IMPRIMIR")) {
						this.arraytoken.add(new Token(TipoToken.PCImprimir, lexema, this.cont_linhas));
					}else if(lexema.equals("SE")) {
						this.arraytoken.add(new Token(TipoToken.PCSe, lexema, this.cont_linhas));
					}else if(lexema.equals("SENAO")) {
						this.arraytoken.add(new Token(TipoToken.PCSenao, lexema, this.cont_linhas));
					}else if(lexema.equals("ENTAO")) {
						this.arraytoken.add(new Token(TipoToken.PCEntao, lexema, this.cont_linhas));
					}else if(lexema.equals("ENQTO")) {
						this.arraytoken.add(new Token(TipoToken.PCEnqto, lexema, this.cont_linhas));
					}else if(lexema.equals("INI")) {
						this.arraytoken.add(new Token(TipoToken.PCIni, lexema, this.cont_linhas));
					}else if(lexema.equals("FIM")) {
						this.arraytoken.add(new Token(TipoToken.PCFim, lexema, this.cont_linhas));
					}else {
						System.out.println(lexema + " ERROR: palavra inválida. Linha: " + this.cont_linhas);
						System.exit(1);
					}
					lexema = "";// esvazia o lexema
				}//end reserved words
				else {//erro se nao entrou em nenhuma condicao valida para a linguagem.
					System.out.println(Character.toString(flexoes.charAt(i))+" ERROR: caractere inválido. Linha: " + this.cont_linhas + " Caractere: "+i);// erro retorna a linha e o caractere inicial do erro
					System.exit(1);
				}
			}//end for
		}//end big for
		this.arraytoken.add(new Token(TipoToken.FimArq, "EOF", this.cont_linhas)); //indica fim do arquivo
		System.out.println(this.arraytoken);//exibir todos os tokens se nao houver erros
		//System.exit(0);// success
	}
	public ArrayList<Token> getArraytoken(){
		return arraytoken;
	}
	public Token getnome(int i) {
		return arraytoken.get(i);
	}
}


