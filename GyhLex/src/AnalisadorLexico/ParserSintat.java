package AnalisadorLexico;

import java.util.ArrayList;

public class ParserSintat {
	boolean palavraVazia = true;
	public ParserSintat() {}

	public void parser(ArrayList<Token> arraytoken) {
		ArrayList<Token> arrayCompare = new ArrayList<Token>();
		ArrayList<Token> arrayListaComandos = new ArrayList<Token>();
		int aux = 0;
		if(arraytoken.get(0).getnome() == TipoToken.Delim) {
			System.out.println("VALIDOU primeiro DELIM");
		}else{
			System.out.println("ERROR primeiro DELIM na linha ");
			System.exit(1);
		}
		if(arraytoken.get(1).getnome() == TipoToken.PCDec) {
			System.out.println("VALIDOU primeiro PCDEC");
		}else{
			System.out.println("ERROR primeiro PCDEC");
			System.exit(1);
		}
		if(arraytoken.get(2).getnome() == TipoToken.Delim){
			System.out.println("ERROR: sem declaracoes na linha " + Integer.toString(arrayListaComandos.get(0).getlinha()));
			System.exit(1);
		}
		for(int i = 2; i < arraytoken.size(); i++) { 
			/////////////////LISTA DE DECLARACOES/////////////////////////////////////
			if(arraytoken.get(i).getnome() == TipoToken.Delim && arraytoken.get(i+1).getnome() == TipoToken.PCProg) {
				System.out.println("VALIDOU DELIM");
				System.out.println("VALIDOU PCPROG");
				aux = i;
				for(int j = 2; j < aux; j++) arrayCompare.add(arraytoken.get(j));
			}			
			
		}
		if(aux +1 == arraytoken.size()) {
			System.out.println("ERROR: sem LISTACOMANDOS na linha " + Integer.toString(arrayListaComandos.get(0).getlinha()));
			System.exit(1);
		}
		for(int i = aux+2; i < arraytoken.size(); i++) {
			arrayListaComandos.add(arraytoken.get(i));
		}
		if(listaDeclaracoes(arrayCompare) == false){
			System.out.println("ERROR: listaDeclaracao na linha " + Integer.toString(arrayCompare.get(0).getlinha()));
			System.exit(1);
		}else {
			System.out.println("VALIDOU LISTADECLARACAO");
		}
		if(listaComandos(arrayListaComandos) == false) {
			System.out.println("ERROR: listaComandos na linha " + Integer.toString(arrayListaComandos.get(0).getlinha()));
			System.exit(1);
		}else {
			System.out.println("VALIDOU LISTACOMANDOS");
		}	
	}
	
	public boolean listaDeclaracoes(ArrayList<Token> arrayCompare) {
		ArrayList<Token> arrayDeclaracao = new ArrayList<Token>();
		boolean retorno = false;
		if(arrayCompare.isEmpty()) return true;
		for(int i = 0; i < 3; i++) {
			arrayDeclaracao.add(arrayCompare.get(0));
			arrayCompare.remove(0);
		}
		if(declaracao(arrayDeclaracao)) {
			if(listaDeclaracoes(arrayCompare))
				retorno = true;
			System.out.println("VALIDOU DECLARACAO");
		}
		return retorno;
	}
	
	public boolean declaracao(ArrayList<Token> arrayDeclaracao) {
		boolean retorno = false;
		if(arrayDeclaracao.get(0).getnome() == TipoToken.Var) {
			if(arrayDeclaracao.get(1).getnome() == TipoToken.Delim) {
				if(arrayDeclaracao.get(2).getnome() == TipoToken.PCReal || arrayDeclaracao.get(2).getnome() == TipoToken.PCInt) {
					retorno = true;
				}else {
					System.out.println("ERROR: Esperado PCReal ou PCInt na linha " + Integer.toString(arrayDeclaracao.get(0).getlinha()));
					System.exit(1);
				}
			}else {
				System.out.println("ERROR: Esperado Token Delim na linha " + Integer.toString(arrayDeclaracao.get(0).getlinha()));
				System.exit(1);
			}
		}else {
			System.out.println("ERROR: Esperado TipoVar na linha " + Integer.toString(arrayDeclaracao.get(0).getlinha()));
			System.exit(1);
		}
		return retorno;
	}
	
	public boolean listaComandos(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;
		if(arrayListaComandos.isEmpty() || arrayListaComandos.get(0).getnome() == TipoToken.FimArq) return true;
		if(comando(arrayListaComandos)) {
			if(listaComandos(arrayListaComandos))
				retorno = true;
		}
		
		return retorno;
	}
	
	public boolean comando(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;

		if(comandoAtrib(arrayListaComandos)) {
			retorno = true;
			System.out.println("VALIDOU COMANDOATRIB");
		}else if(comandoEntrada(arrayListaComandos)) {
			retorno = true;
			System.out.println("VALIDOU COMANDOENTRADA");
		}else if(comandoSaida(arrayListaComandos)) {
			retorno = true;
			System.out.println("VALIDOU COMANDOSAIDA");
		}else if(comandoCondicao(arrayListaComandos)) {
			retorno = true;
			System.out.println("VALIDOU COMANDOCONDICAO");
		}else if(comandoRepeticao(arrayListaComandos)) {
			retorno = true;
			System.out.println("VALIDOU COMANDOREPETICAO");
		}else if(subAlg(arrayListaComandos)) {
			retorno = true;
			System.out.println("VALIDOU SUBALGORITMO");
		}
		return retorno;
	}
	
	public boolean comandoAtrib(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;
		int linha = arrayListaComandos.get(0).getlinha();
		ArrayList<Token> arrayListaExpressao = new ArrayList<Token>();	
		if(arrayListaComandos.get(0).getnome() == TipoToken.Var) {
			arrayListaComandos.remove(0);
			if(arrayListaComandos.get(0).getnome() == TipoToken.Atrib) {
				arrayListaComandos.remove(0);
				retorno = true;
			}else{
				System.out.println("ERROR: Inesperado Token na linha " + Integer.toString(arrayListaComandos.get(0).getlinha()));
				System.exit(1);
			}
		}
		if(retorno == true) {
			int numIteracoes = 0;
			for(int i = 0; i < arrayListaComandos.size(); i++) {
				if(linha == arrayListaComandos.get(i).getlinha()) {
					arrayListaExpressao.add(arrayListaComandos.get(i));
					numIteracoes++;
				}else break; 
			}
			for(int i = 0; i < numIteracoes; i++) {
				arrayListaComandos.remove(0);
			}
		}else 
			return retorno;
		if(expressaoArit(arrayListaExpressao))
			retorno = true;
		return retorno;
	}
	
	public boolean comandoEntrada(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;
		if(arrayListaComandos.get(0).getnome() == TipoToken.PCLer) {
			arrayListaComandos.remove(0);
			if(arrayListaComandos.get(0).getnome() == TipoToken.Var) {
				arrayListaComandos.remove(0);
				retorno = true;
			}
		}
		return retorno;
	}
	
	public boolean comandoSaida(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;
		if(arrayListaComandos.get(0).getnome() == TipoToken.PCImprimir) {
			arrayListaComandos.remove(0);
			if(arrayListaComandos.get(0).getnome() == TipoToken.Var || arrayListaComandos.get(0).getnome() == TipoToken.Cadeia) {
				arrayListaComandos.remove(0);
				retorno = true;
			}
		}
		return retorno;
	}
	
	public boolean comandoCondicao(ArrayList<Token> arrayListaComandos) {
		
		boolean retorno = false;
		ArrayList<Token> arrayComandoCondicao = new ArrayList<Token>();
		if(arrayListaComandos.get(0).getnome() == TipoToken.PCSe) {
			
			int linha = arrayListaComandos.get(0).getlinha();
			arrayListaComandos.remove(0);
			while(arrayListaComandos.get(0).getnome() != TipoToken.PCEntao) {
				arrayComandoCondicao.add(arrayListaComandos.get(0));
				arrayListaComandos.remove(0);
			}
			if(arrayListaComandos.get(0).getnome() == TipoToken.PCEntao && arrayListaComandos.get(0).getlinha() == linha) arrayListaComandos.remove(0);
			else return false;

			if(expressaoRelacional(arrayComandoCondicao)) {
				if(!arrayComandoCondicao.isEmpty()) {
					System.out.println("ERROR: INESPERADO LEXEMA: " + arrayComandoCondicao.get(0).getnome() + " na linha " + Integer.toString(arrayComandoCondicao.get(0).getlinha()));
					System.exit(1);
				}
				if(arrayListaComandos.isEmpty()) {
					System.out.println("ERROR: É ESPERADO UM COMANDO APÓS ENTAO na linha " + Integer.toString(arrayListaComandos.get(0).getlinha()));
					System.exit(1);
				}
				if(comando(arrayListaComandos)) 
					if(arrayListaComandos.get(0).getnome() == TipoToken.PCSenao && arrayListaComandos.get(0).getlinha() == linha) {
						arrayListaComandos.remove(0);
						if(comando(arrayListaComandos)) 
							retorno = true;
					}else retorno = true;
				
			}
		}
		return retorno;
	}
	
	public boolean comandoRepeticao(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;
		if(arrayListaComandos.get(0).getnome() == TipoToken.PCEnqto) {
			arrayListaComandos.remove(0);
			if(expressaoRelacional(arrayListaComandos)) {
				if(comando(arrayListaComandos)) retorno = true;
			}
		}
		return retorno;
	}
	
	/*
	 * 
	 * ANALISAR
	 * O
	 * ARRAY
	 * DE
	 * SUB
	 * ALGORITMOOOOOOOOOOOS
	 * 
	 * MELHORE
	 * 
	 * */
	
	public boolean subAlg(ArrayList<Token> arrayListaComandos) {

		boolean retorno = false;
		//int linha = arrayListaComandos.get(0).getlinha();
		ArrayList<Token> arraySubAlg = new ArrayList<Token>();
		if(arrayListaComandos.get(0).getnome() == TipoToken.PCIni){
			arrayListaComandos.remove(0);
			while(arrayListaComandos.get(0).getnome() != TipoToken.PCFim ) {
				arraySubAlg.add(arrayListaComandos.get(0));
				arrayListaComandos.remove(0);
			}
			if(arrayListaComandos.get(0).getnome() == TipoToken.PCFim) {
				arrayListaComandos.remove(0);
				if(listaComandos(arraySubAlg))
					retorno = true;
			}else {
				System.out.println("ERROR no SUBALG na linha " + Integer.toString(arrayListaComandos.get(0).getlinha()));
				System.exit(1);
			}
		}
		return retorno;
	}
	
	public boolean expressaoRelacional(ArrayList<Token> arrayListaComandos) {//esvaziar arrayComandoCondicao
		boolean retorno = false;
		if(arrayListaComandos.isEmpty()) return true;
		if(termoRelacional(arrayListaComandos)) {
			if(expressaoRelacionalL(arrayListaComandos)) {
				retorno = true;
				System.out.println("VALIDOU EXPRESSAORELACIONAL LINHA");
			}
		}
		return retorno;
	}
	
	public boolean termoRelacional(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;
		boolean fechaPar = false;
		ArrayList<Token> arrayTermoRelacional = new ArrayList<Token>();
		if(expressaoArit(arrayListaComandos)) {
			if(termoRelacionalL(arrayListaComandos)) retorno = true;
		}else if(arrayListaComandos.get(0).getnome() == TipoToken.AbrePar){
			int linha = arrayListaComandos.get(0).getlinha();
			arrayListaComandos.remove(0);
			while(arrayListaComandos.get(0).getlinha() == linha) {
				if(arrayListaComandos.get(0).getnome() != TipoToken.FechaPar) {
					arrayTermoRelacional.add(arrayListaComandos.get(0));
					arrayListaComandos.remove(0);
				}else {
					fechaPar = true;
					arrayListaComandos.remove(0);
					break;
				}
			}
			if(!fechaPar) return false;
			if(expressaoRelacional(arrayTermoRelacional)) retorno = true;
		}
		return retorno;
	}
	
	public boolean termoRelacionalL(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;
		if(arrayListaComandos.isEmpty()) return true;
		if(operadorRelacional(arrayListaComandos)) {
			if(expressaoArit(arrayListaComandos)) {
				if(termoRelacionalL(arrayListaComandos)) {
					retorno = true;
					System.out.println("VALIDOU TERMORELACIONAL LINHA");
				}
			}
		}else retorno = palavraVazia;
		return retorno; //palavra vazia
	}
	
	public boolean operadorRelacional(ArrayList<Token> arrayListaComandos) {
		boolean retorno = true;
		if(arrayListaComandos.get(0).getnome() == TipoToken.OpRelMaior) {
			arrayListaComandos.remove(0);
		}else if(arrayListaComandos.get(0).getnome() == TipoToken.OpRelMaiorIgual) {
			arrayListaComandos.remove(0);
		}else if(arrayListaComandos.get(0).getnome() == TipoToken.OpRelMenor) {
			arrayListaComandos.remove(0);
		}else if(arrayListaComandos.get(0).getnome() == TipoToken.OpRelMenorIgual) {
			arrayListaComandos.remove(0);
		}else if(arrayListaComandos.get(0).getnome() == TipoToken.OpRelIgual) {
			arrayListaComandos.remove(0);
		}else if(arrayListaComandos.get(0).getnome() == TipoToken.OpRelDif) {
			arrayListaComandos.remove(0);
		}else {
			retorno = false;
		}
		return retorno;
	}
	
	public boolean expressaoRelacionalL(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;
		if(arrayListaComandos.isEmpty()) return true;
		if(operadorBoolean(arrayListaComandos)) {
			if(termoRelacional(arrayListaComandos)) {
				if(expressaoRelacionalL(arrayListaComandos)) retorno = true;
			}
		}else return palavraVazia;
		return retorno;
	}
	
	public boolean operadorBoolean(ArrayList<Token> arrayListaComandos) {
		boolean retorno = false;
		if(arrayListaComandos.get(0).getnome() == TipoToken.OpBoolE) {
			arrayListaComandos.remove(0);
			retorno = true;
		}else if(arrayListaComandos.get(0).getnome() == TipoToken.OpBoolOu) {
			arrayListaComandos.remove(0);
			retorno = true;
		}/*else {
			System.out.println("ERROR: OPBOOL na linha " + Integer.toString(arrayListaComandos.get(0).getlinha()));
			System.exit(1);
		}*/
		return retorno;
	}
	
	public boolean expressaoArit(ArrayList<Token> arrayListaExpressao) {
		boolean retorno = false;
		if(arrayListaExpressao.isEmpty()) return true;
		if(termoArit(arrayListaExpressao)) 
			if(expressaoAritL(arrayListaExpressao)) {
				retorno = true;
				System.out.println("VALIDOU EXPRESSAOARIT");
		}
		return retorno;
	}
	
	public boolean termoArit(ArrayList<Token> arrayListaExpressao) {
		boolean retorno = false;
		if(arrayListaExpressao.isEmpty()) return true;
		if(fatorArit(arrayListaExpressao)) {
			if(termoAritL(arrayListaExpressao))
				retorno = true;
		}
		return retorno;
	}
	
	public boolean fatorArit(ArrayList<Token> arrayListaExpressao) {
		boolean retorno = false;
		boolean fechaPar = false;
		ArrayList<Token> arrayFatorArit = new ArrayList<Token>();
		if(arrayListaExpressao.isEmpty()) return true;
		if(arrayListaExpressao.get(0).getnome() == TipoToken.Var) {
			arrayListaExpressao.remove(0);
			retorno = true;
		}else if (arrayListaExpressao.get(0).getnome() == TipoToken.NumInt || arrayListaExpressao.get(0).getnome() == TipoToken.NumReal) {
			arrayListaExpressao.remove(0);
			retorno = true;
		}else if(arrayListaExpressao.get(0).getnome() == TipoToken.AbrePar) {
			int linha = arrayListaExpressao.get(0).getlinha();
			arrayListaExpressao.remove(0);
			while(!arrayListaExpressao.isEmpty() && arrayListaExpressao.get(0).getlinha() == linha) {
				if(arrayListaExpressao.get(0).getnome() != TipoToken.FechaPar) {
					arrayFatorArit.add(arrayListaExpressao.get(0));
					arrayListaExpressao.remove(0);
				}else {
					fechaPar = true;
					arrayListaExpressao.remove(0);
					break;
				}
			}
			if(!fechaPar) {
				System.out.println("Esperado Token FechaPar na linha " + Integer.toString(linha));
				System.exit(1);
			}
			if(expressaoArit(arrayFatorArit)) retorno = true;
		} else if(arrayListaExpressao.get(0).getnome() == TipoToken.FechaPar) {
			System.out.println("Inesperado Token FechaPar na linha " + Integer.toString(arrayListaExpressao.get(0).getlinha()));
			System.exit(1);
		}
		
		return retorno;
	}
	
	public boolean termoAritL(ArrayList<Token> arrayListaExpressao) {
		if(arrayListaExpressao.isEmpty()) return true;
		if(arrayListaExpressao.get(0).getnome() == TipoToken.OpAritMult) {
			arrayListaExpressao.remove(0);
			if(fatorArit(arrayListaExpressao)) termoAritL(arrayListaExpressao);	
		}else if(arrayListaExpressao.get(0).getnome() == TipoToken.OpAritDiv) {
			arrayListaExpressao.remove(0);
			if(fatorArit(arrayListaExpressao)) termoAritL(arrayListaExpressao);
		}else if(arrayListaExpressao.get(0).getnome() == TipoToken.FechaPar) {
			System.out.println("Inesperado Token FechaPar na linha " + Integer.toString(arrayListaExpressao.get(0).getlinha()));
			System.exit(1);
		}
		return palavraVazia;
	}
	
	public boolean expressaoAritL(ArrayList<Token> arrayListaExpressao) {
		boolean retorno = false;
		if(arrayListaExpressao.isEmpty()) return true;
		if(arrayListaExpressao.get(0).getnome() == TipoToken.OpAritSoma) {
			arrayListaExpressao.remove(0);
			if(termoArit(arrayListaExpressao)) 
				if(expressaoAritL(arrayListaExpressao))
					retorno = true;
		}else if(arrayListaExpressao.get(0).getnome() == TipoToken.OpAritSub) {
			arrayListaExpressao.remove(0);
			if(termoArit(arrayListaExpressao))
				if(expressaoAritL(arrayListaExpressao))
					retorno = true;
		}else {
			retorno = palavraVazia;
		}
		return retorno;
	}
}
