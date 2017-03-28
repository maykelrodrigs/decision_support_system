/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mrodrigues
 */
public class Servidor {
    
    private ArrayList <String> paciente;
    private ArrayList <String> sintomas;
    private ArrayList <String> doenca;
    private ArrayList <String> medico;
    
    private int matriz[][]; // 20 14
    private int atendimentos;
    
    public Servidor() {
        
        this.matriz = new int[20][14];
        this.paciente = new ArrayList<>();
        this.sintomas = new ArrayList<>();
        this.doenca = new ArrayList<>();
        this.medico = new ArrayList<>();
        this.atendimentos = 0;
        
        sintomas.add("Febre");
        sintomas.add("Manchas Cutaneas");
        sintomas.add("Cansaço");
        sintomas.add("Olhos irritados");
        sintomas.add("Dores nas articulaçoes");
        sintomas.add("Vomito");
        sintomas.add("Diarreia");
        sintomas.add("Tosse");
        sintomas.add("Dor de cabeça");
        sintomas.add("Dor de garganta");
        
        doenca.add("Resfriado");
        doenca.add("Gripe");
        doenca.add("Dengue");
        doenca.add("Alergia");
        doenca.add("Virose");
        
    }

    public ArrayList<String> getPaciente() {
        return paciente;
    }

    public ArrayList<String> getSintomas() {
        return sintomas;
    }

    public ArrayList<String> getDoenca() {
        return doenca;
    }

    public ArrayList<String> getMedico() {
        return medico;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public int getAtendimentos() {
        return atendimentos;
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Servidor s = new Servidor();
        
        try {
            
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Servidor ouvindo a porta 12345");
            
            while(true) {
              
              Socket cliente = servidor.accept();
              
              System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
              ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
              
              saida.flush();
              saida.writeObject(s.getSintomas());
              saida.close();
              cliente.close();
              
            }  
        }   
        catch(Exception e) {
           System.out.println("Erro: " + e.getMessage());
        }
        
    }
    
}
