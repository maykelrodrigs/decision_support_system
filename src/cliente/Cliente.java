/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author mrodrigues
 */
public class Cliente {
    
    private int porta = 12345;

    public Cliente() {
          
    }
    
    ArrayList ReceberDados() {
        
        ArrayList dados = new ArrayList();
        
        try {
            
            Socket cliente = new Socket(InetAddress.getLocalHost().getHostName(), porta);
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            dados = (ArrayList) entrada.readObject();
            entrada.close();
            
          }
          catch(IOException | ClassNotFoundException e) {
            System.out.println("Erro servidor: " + e.getMessage());
          }
        
        return dados;
    }

    public static void main(String[] args) {
           
        try {
            
            Socket cliente = new Socket(InetAddress.getLocalHost().getHostName(), 12345);
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            
            ArrayList <String> sintomas = new ArrayList();
            sintomas = (ArrayList<String>) entrada.readObject();
            
            //Date data_atual = (Date)entrada.readObject();
            JOptionPane.showMessageDialog(null,"Data recebida do servidor:" + sintomas.get(1));
            entrada.close();
            
            System.out.println("Conexão encerrada");
            
          }
          catch(Exception e) {
            System.out.println("Erro servidor: " + e.getMessage());
          }

    }
    
}
