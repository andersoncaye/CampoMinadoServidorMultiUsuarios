
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

/* @author anderson.caye */
 /* Servidor para Multiplos Usuários */
public class ServerGame {

    private static final int TOTAL_BOMBS = 7;
    private static final int SIZE_COLUMN = 20;
    private static final int SIZE_ROW = 20;
    private static String[][] field = new String[SIZE_ROW][SIZE_COLUMN];
    public static String[] avatar = {"0", "+", "W", "T", "Y", "U", "A", "H", "^", "X", "V", "M"};
    // Chave = Simbolo; Valor = Vida
    public static TreeMap<String, Boolean> users = new TreeMap();
    // Chave = Simbolo;
    public static TreeMap<String, Integer> usersWin = new TreeMap();
    // Chave = Simbolo;
    public static TreeMap<String, Integer> usersFail = new TreeMap();
    private static String action = ""
            + "Comandos:"
            + "help \t-> mostrar os comandos"
            + ""
            + "mov_cima \t-> mover para cima "
            + "mov_baixo \t-> mover para baixo"
            + "mov_esquerda \t-> mover para a esquerda"
            + "mov_direita \t-> mover para a direita"
            + ""
            + "mat_x - > Total de linhas da "
            + "mat_y"
            + "matriz"
            + ""
            + "sair \t-> sai do sistema";

     public final int SERVER_PORT = 6000;
    
    public ServerGame() {
        fillField();
    }

    public static String[][] getFieldArray(){
        return field;
    }
    
    public static String getField() {
        String matriz = "";
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                matriz += field[i][j];
            }
        }
        return matriz;
    }
    
    public static void setFieldRemoveHero(int row, int col){
        field[row][col] = ".";
    }
    
    public static void removeHero(int row, int col, String simbol){
        setFieldRemoveHero(row, col);
        users.remove(simbol);
        usersWin.remove(simbol);
        usersFail.remove(simbol);
    }
    
    
    public static String getSizeRow() {
        return "" + SIZE_ROW;
    }

    public static String getSizeColumn() {
        return "" + SIZE_COLUMN;
    }
    
    public static String getScoreboard(){
        String score = "";
        for (String s : users.keySet()){
            score += s + ";" + usersWin.get(s) + ";" + usersFail.get(s) + "|";
        }
        return score;
    }

    public static void fillField() {
        field = new String[SIZE_ROW][SIZE_COLUMN];
        //popular espaços
        for (int i = 0; i < SIZE_ROW; i++) {
            for (int j = 0; j < SIZE_COLUMN; j++) {
                field[i][j] = ".";
            }
        }
        //variaveis reutilizaveis
        int column;
        int row;
        String temp;
        boolean valid;

        //sorteia a possição das bombas
        int index = 0;
        do {
            column = (int) ((Math.random() * SIZE_COLUMN));
            row = (int) ((Math.random() * SIZE_ROW));
            temp = field[row][column];
            //verificação impede que mais de uma bomba ocupe a mesma posição
            while (temp.equals("*")) {
                column = (int) ((Math.random() * SIZE_COLUMN));
                row = (int) ((Math.random() * SIZE_ROW));
                temp = field[row][column];
            }
            field[row][column] = "*";
            index++;
        } while (index < TOTAL_BOMBS);

        //sorteia a possição do portal
        do {
            column = (int) ((Math.random() * SIZE_COLUMN));
            row = (int) ((Math.random() * SIZE_ROW));
            temp = field[row][column];
            if (temp.equals(".")) {
                field[row][column] = "#";
                valid = true;
            } else {
                valid = false;
            }
        } while (!valid);

        //reseta a vida dos jogadores
        setAllUsers(true);
        
//        System.out.println(getField());
//        int out = 1;
        for(String s : users.keySet()){
            ramdonAllPositionUser(s);
//            System.out.println("#####################"+out );
//            out++;
        }
        
        System.out.println("Sistema: Campo Criado");

    }
    
    public static void ramdonAllPositionUser(String simbol){
        int column;
        int row;
        String temp;
        boolean valid;
        do {
            column = (int) ((Math.random() * SIZE_COLUMN));
            row = (int) ((Math.random() * SIZE_ROW));
            temp = field[row][column];
            if (temp.equals(".")) {
                field[row][column] = simbol;
//                System.out.println("#############123456 :>" + temp);
                valid = true;
            } else {
                valid = false;
            }
        } while (!valid);
        
//        for (String s : users.keySet()) {
//            System.out.println(s);
//        }
        
    }

    public static String[] ramdonPositionUser(/*String user*/) {
        //variaveis reutilizaveis
        String simbol = null;
        int column;
        int row;
        String temp;
        boolean valid;

        do {
            for (String s : avatar) {
                if (!users.containsKey(s)) {
                    users.put(s, true);
                    usersWin.put(s, 0);
                    usersFail.put(s, 0);
                    simbol = s;
                    break;
                }
            }
        } while (simbol == null);

        do {
            column = (int) ((Math.random() * SIZE_COLUMN));
            row = (int) ((Math.random() * SIZE_ROW));
            temp = field[row][column];
            if (temp.equals(".")) {
                field[row][column] = simbol;
                valid = true;
            } else {
                valid = false;
            }
        } while (!valid);
        
        String[] array = { simbol, row+"", column+"" };
        
        System.out.println("Sistema: Jogador Iniciado - " + simbol);
        
        return array;
    }

    public static void setAllUsers(boolean live) {
        //reseta a vida dos jogadores
        if (users.size() > 0) {
            for (String s : users.keySet()) {
//                users.remove(s);
                users.put(s, live);
            }
        }
    }

    public static String[] moveHero(String simbol, String move, int posR, int posC) {
        move = move.toLowerCase();
        String s = "null";
        if (move.equals("mov_cima")) {
            if (posR > 0) {
                s = managerLive(simbol, posR - 1, posC);
                if (users.get(simbol)) {
                    field[posR][posC] = ".";
                    posR--;
                    field[posR][posC] = simbol;
                } else {
                    field[posR][posC] = ".";
                }
            }
        } else if (move.equals("mov_baixo")) {
            if (posR < SIZE_ROW-1) {
                s = managerLive(simbol, posR + 1, posC);
                if (users.get(simbol)) {
                    field[posR][posC] = ".";
                    posR++;
                    field[posR][posC] = simbol;
                } else {
                    field[posR][posC] = ".";
                }
            }
        } else if (move.equals("mov_esquerda")) {
            if (posC > 0) {
                s = managerLive(simbol, posR, posC - 1);
                if (users.get(simbol)) {
                    field[posR][posC] = ".";
                    posC--;
                    field[posR][posC] = simbol;
                } else {
                    field[posR][posC] = ".";
                }
            }
        } else if (move.equals("mov_direita")) {
            if (posC < SIZE_COLUMN-1) {
                s = managerLive(simbol, posR, posC + 1);
                if (users.get(simbol)) {
                    field[posR][posC] = ".";
                    posC++;
                    field[posR][posC] = simbol;
                } else {
                    field[posR][posC] = ".";
                }
            }
        }
        String[] array = { s, posR+"", posC+"" };
        
        if(s.equals("GANHOU")){
            fillField();
        }
        
        return array;
    }

    public static String managerLive(String simbol, int row, int column) {
        String s = "OK";
        if (field[row][column].equals("*")) {
            s = "PERDEU";
            users.put(simbol, false);
            int fail = usersFail.get(simbol) + 1;
            usersFail.put(simbol, fail);
        } else if (field[row][column].equals("#")) {
            s = "GANHOU";
            //reseta a vida dos jogadores
            setAllUsers(false);
            
            int win = usersWin.get(simbol) + 1;
            usersWin.put(simbol, win);
        }
        return s;
    }
    
    public void initialize() {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            while (true) {
                Socket cli = serverSocket.accept();
                ServerPlayer m = new ServerPlayer(cli);
                m.start();
                System.out.println("New client" + cli.getRemoteSocketAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerGame m = new ServerGame();
        m.initialize();
    }

}
