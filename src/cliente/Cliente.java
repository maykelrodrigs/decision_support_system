/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author mrodrigues
 */
public class Cliente {
    
    private int porta = 12345;

    public Cliente() {
          
    }
    
    String Enviar(String mensagem) {
        
        ObjectOutputStream saida;
        ObjectInputStream entrada;
        Socket conexao;
        
        try {
            
            conexao = new Socket(InetAddress.getLocalHost().getHostName(), porta);
            System.out.println("Conectado ao servidor...");
 
            // ligando as conexoes de saida e de entrada
            saida = new ObjectOutputStream(conexao.getOutputStream());
            entrada = new ObjectInputStream(conexao.getInputStream());
            saida.flush();
 
            saida.writeObject(mensagem);
            
            mensagem = (String) entrada.readObject();
            System.out.println("Servidor: " + mensagem);
 
            saida.close();
            entrada.close();
            conexao.close();
 
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("erro: " + e.toString());
        }
        
        return mensagem;
    }
    
}
