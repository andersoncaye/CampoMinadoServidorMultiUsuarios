
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/* @author anderson.caye */
public class ServerPlayer extends Thread {

    public String simbol;
    public int posR;
    public int posC;
    public Socket socket = null;
    public String matrix[][] = null;

    public ServerPlayer(Socket socket) {
        System.out.println("Iniciou a thread");
        this.socket = socket;

    }

    public void initialize() {
        String[] array = ServerGame.ramdonPositionUser();
        this.simbol = array[0];
//        this.posR = Integer.parseInt(array[1]);
//        this.posC = Integer.parseInt(array[2]);
    }
    
    public String move(String move){
        String[] array = ServerGame.moveHero(simbol, move, posR, posC);
        
//        this.posR = Integer.parseInt(array[1]);
//        this.posC = Integer.parseInt(array[2]);
        return array[0];
    }
    
    public void exit(){
        ServerGame.removeHero(posR, posC, simbol);
    }
    
    public void run() {
        System.out.println("Sistema: inicia o Run() - " + simbol);
        boolean repeat = true;

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            initialize();
            
            String data = "";
            String dataSend = "";
            System.out.println("Sistema: Pronto para receber comandos - " + simbol);
            
            out.println(simbol);
            
            while (repeat) {
                
                if(true){
                    String[][] matriz = ServerGame.getFieldArray();
                    for (int i = 0; i < matriz.length; i++) {
                        for (int j = 0; j < matriz[i].length; j++) {
                            if(matriz[i][j].equalsIgnoreCase(simbol)){
                                posR = i;
                                posC = j;
                            }
                        }
                    }
                }
                
                data = in.readLine();
                dataSend = "";
                System.out.println("Client request: " + simbol + " -> " + data);

                if (data.equalsIgnoreCase("sair") || data.equalsIgnoreCase("sair\r\n")) {
                    dataSend = "tchau - " + simbol;
                    repeat = false;
                    exit();
                } else if (data.equalsIgnoreCase("mov_cima") || data.equalsIgnoreCase("mov_cima\r\n")) {
                    dataSend = move(data);
                } else if (data.equalsIgnoreCase("mov_baixo") || data.equalsIgnoreCase("mov_baixo\r\n")) {
                    dataSend = move(data);
                } else if (data.equalsIgnoreCase("mov_esquerda") || data.equalsIgnoreCase("mov_esquerda\r\n")) {
                    dataSend = move(data);
                } else if (data.equalsIgnoreCase("mov_direita") || data.equalsIgnoreCase("mov_direita\r\n")) {
                    dataSend = move(data);
                } else if (data.equalsIgnoreCase("mat_x") || data.equalsIgnoreCase("mat_x\r\n")) {
                    dataSend = ServerGame.getSizeRow();
                } else if (data.equalsIgnoreCase("mat_y") || data.equalsIgnoreCase("mat_y\r\n")) {
                    dataSend = ServerGame.getSizeColumn();
                } else if (data.equalsIgnoreCase("matriz") || data.equalsIgnoreCase("matriz\r\n")) {
                    dataSend = ServerGame.getField();
                } else if (data.equalsIgnoreCase("placar") || data.equalsIgnoreCase("placar\r\n")) {
                    dataSend = ServerGame.getScoreboard();
                }
                
                
                
                out.println(dataSend);
                System.out.println("\n\tSistema: Enviou algo para - " +  simbol + "\n\n" + dataSend);
            }
            
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
            exit();
        }
    }

}
