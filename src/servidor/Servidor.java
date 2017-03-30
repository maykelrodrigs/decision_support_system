/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author mrodrigues
 */
public class Servidor {
    
    private int porta = 12345;
    private int matriz[][]; // 20 14
    private int atendimentos;
    private ArrayList<String> paciente;
    private ArrayList<String> medico;
    private ArrayList<Integer> sintomas;

    
    public Servidor() {
        
        matriz = new int[20][13];
        paciente = new ArrayList();
        medico = new ArrayList();
        sintomas = new ArrayList();
        atendimentos = 0;
        
    }
    
    ArrayList CalculoSuporte() {
      
        ArrayList<Integer> contador = new ArrayList();
        ArrayList<Integer> doenca   = new ArrayList();
        int aux, index;
        
        for (int i = 0; i < atendimentos; i++) {
            aux = 0;            
            
            for (Integer j : sintomas)
                if (matriz[i][j] == 1)
                    aux++;                
            
            if (aux == sintomas.size()) {
                if(doenca.contains(matriz[i][10])) {
                    index = doenca.indexOf(matriz[i][10]);
                    contador.set(index, contador.get(index) + 1);
                
                } else {
                    doenca.add(matriz[i][10]);
                    contador.add(1);
                }                
            } 
        }
                
        for (int i=0; i < contador.size(); i++)
            if ( (double)contador.get(i) / (double)atendimentos < 0.3) {
                doenca.remove(i);
                contador.remove(i);
                i--;
            }
        
        for (Integer j : doenca) {
            
            aux = 0;
            for (int i=0; i < atendimentos; i++)
                if (matriz[i][10] == j)
                    aux++;
            
            double calc = contador.get( doenca.indexOf(j) ) / aux;
            if (calc < 0.7)
                doenca.remove(doenca.indexOf(j));
                
        }
        
        sintomas.clear();
        return doenca;     
        
    }
    
    String CalcularDoenca() {
        
        String msg = "";
        ArrayList<Integer> doenca = CalculoSuporte();
        
        for (Integer i : doenca) {
            switch(i) {
                case 0:
                    msg += "Resfriado";
                    break;
                case 1:
                    msg += "Gripe";
                    break;
                case 2:
                    msg += "Dengue";
                    break;
                case 3:
                    msg += "Alergia";
                    break;
                case 4:
                    msg += "Virose";
                    break;
                default:
                    msg += "Erro!";
                    break;
            }
            msg += " | ";
        }
        
        return msg;        
               
    }
    
    void TratarMensagem( String mensagem ) {
        
        String array[] = new String[4];
        array = mensagem.split(";");
        
        if(!paciente.contains(array[0]))
            paciente.add(array[0]);
        
        if(!medico.contains(array[1]))
            medico.add(array[1]);
                
        // sintomas
        matriz[atendimentos][0] = array[2].charAt(0)  == '1' ? 1 : 0;
        matriz[atendimentos][1] = array[2].charAt(1)  == '1' ? 1 : 0;
        matriz[atendimentos][2] = array[2].charAt(2)  == '1' ? 1 : 0;
        matriz[atendimentos][3] = array[2].charAt(3)  == '1' ? 1 : 0;
        matriz[atendimentos][4] = array[2].charAt(4)  == '1' ? 1 : 0;
        matriz[atendimentos][5] = array[2].charAt(5)  == '1' ? 1 : 0;
        matriz[atendimentos][6] = array[2].charAt(6)  == '1' ? 1 : 0;
        matriz[atendimentos][7] = array[2].charAt(7)  == '1' ? 1 : 0;
        matriz[atendimentos][8] = array[2].charAt(8) == '1' ? 1 : 0;
        matriz[atendimentos][9] = array[2].charAt(9) == '1' ? 1 : 0;
        
        matriz[atendimentos][10] = Integer.parseInt(array[3]); // doença
        System.out.println("Doenca: " + matriz[atendimentos][10]);
        
        matriz[atendimentos][11] = medico.indexOf(array[1]);
        matriz[atendimentos][12] = paciente.indexOf(array[0]);
        
        for(int i=0; i<array[2].length(); i++)
            if(array[2].charAt(i) == '1') {
                sintomas.add(i);       
                System.out.println(i);
            }
        
        atendimentos++;
        
    }
    
    public void Iniciar() {
        
        ObjectOutputStream saida;
        ObjectInputStream entrada;
        
        boolean sair = false;
        String mensagem = "";

        try {
            
            ServerSocket servidor = new ServerSocket(porta);
            Socket conexao;
            
            while (true) {
                
                System.out.println("Ouvindo na porta: " + porta);
                conexao = servidor.accept();
                
                System.out.println("Conexao estabelecida: " + conexao.getInetAddress().getHostAddress());

                saida = new ObjectOutputStream(conexao.getOutputStream());
                entrada = new ObjectInputStream(conexao.getInputStream());
                    
                mensagem = (String) entrada.readObject();
                
                TratarMensagem( mensagem );
                mensagem = CalcularDoenca();
                
                saida.writeObject( mensagem );
                saida.close();
                entrada.close();
                conexao.close();

            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }
    
    public static void main(String[] args) {
        
        Servidor s = new Servidor();
        s.Iniciar();
        
    }
    
}
