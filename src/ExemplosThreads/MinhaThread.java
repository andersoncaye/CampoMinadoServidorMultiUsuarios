package ExemplosThreads;
/* @author anderson.caye */
public class MinhaThread extends Thread{
    
    public String id = null;
    
    public  MinhaThread(String id){
        this.id = id;
    }
    
    public void run() {
        try {
            while ( 1 == 1){
                Thread.sleep(1000);
                System.out.println(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
