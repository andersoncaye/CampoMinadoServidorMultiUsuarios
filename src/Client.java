
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static final int PORTA = 6000;
    public static int col = 0;
    public static int row = 0;

    public static void main(String[] args) {
        try {
            Socket cliente = new Socket("127.0.0.1", PORTA);

            PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            //ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            //ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            Scanner tc = new Scanner(System.in);

            String mens = "";
            String aviso = "";
            
            mens = in.readLine();
            System.out.println("Sistema diz, seu simbolo é: "+ mens);

            do {
                aviso = tc.nextLine();
                out.println(aviso);
                //saida.writeObject(aviso);

                //mens = (String) entrada.readObject();
                mens = in.readLine();
                
                if (aviso.equalsIgnoreCase("matriz")) {
                    System.out.println("O servidor disse: \n");
                    
                    if(row == 0){
                        System.out.println(mens+"\n\n");
                    } else {
                        int size = 0;//row * col;
                        for (int i = 0; i < row; i++) {
                            for (int j = 0; j < col; j++) {
                                System.out.print("  " + mens.charAt(size));
                                size++;
                            }
                            System.out.println("");
                        }
                    }
                } else if (aviso.equalsIgnoreCase("mat_x")) {
                    row = Integer.parseInt(mens);
                    System.out.println("O servidor disse: " + mens);
                } else if (aviso.equalsIgnoreCase("mat_y")) {
                    col = Integer.parseInt(mens);
                    System.out.println("O servidor disse: " + mens);
                } else if (aviso.equalsIgnoreCase("placar")) {
                    //code, win, fail
                    System.out.println("O servidor disse: " + mens);
                    
                    String[] placar = mens.split("/");
                    
                    System.out.println(placar.length);
                    for (int i = 0; i < placar.length; i++) {
                        System.out.println(placar[i]);
                    }
                    
                    System.out.println("");
                    
                } else {
                    System.out.println("O servidor disse: " + mens);
                }

            } while (!aviso.equalsIgnoreCase("sair"));

            //entrada.close();
            //saida.close();
            cliente.close();
            System.out.println("Conexão encerrada");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
