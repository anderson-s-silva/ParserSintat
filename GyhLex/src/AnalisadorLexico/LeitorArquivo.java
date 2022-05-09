//LeitorArquivo.java reusado do grupo do Cristian Veggian
package AnalisadorLexico;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class LeitorArquivo{

  public LeitorArquivo(){
  }

  public ArrayList<String> lerLinhas(String nome){//metodo

    ArrayList<String> linhas = new ArrayList<String>();
    
    try{
        Scanner scan = new Scanner(new File(nome));//entrada e o arquivo

        while(scan.hasNextLine()){//enquanto tiver uma proxima linha, add array
             linhas.add(scan.nextLine());
        }
      }catch(Exception e){
        System.out.println(e);
      }
        return linhas;
  }
  
}