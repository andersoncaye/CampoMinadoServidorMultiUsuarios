package ExemplosThreads;

/* @author anderson.caye */
public class UsaThreads extends Thread{
    
    int i;
    
    public UsaThreads(int i){
        this.i = i;
        this.start();
    }
    
    public void run(){
        try {
            System.out.println("Oi, legal " + i);
            Thread.sleep(1000);
            System.out.println("tchau! " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        for(int i = 1; i <= 5; i++){
            UsaThreads t = new UsaThreads(i);
        }
        System.out.println("Acabou!");
    }
}
