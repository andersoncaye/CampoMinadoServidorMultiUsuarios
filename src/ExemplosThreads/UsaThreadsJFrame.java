package ExemplosThreads;


import javax.swing.JFrame;

/* @author anderson.caye */
public class UsaThreadsJFrame extends JFrame {
    
    public UsaThreadsJFrame(){
        this.setSize(800, 600);
        this.setTitle("Teste");
        
    }
    
    public void run(){
        try {
            System.out.println("Oi, legal ");
            Thread.sleep(1000);
            System.out.println("tchau! ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        MinhaThread t = new MinhaThread("A");
        MinhaThread x = new MinhaThread("B");
        t.start();
        x.start();
        System.out.println("Esperando"); 
   }
}
