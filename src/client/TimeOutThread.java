package client;

import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olivia
 */
public class TimeOutThread extends Thread{
    
    
    @Override
    public void run(){
        try {
            sleep(15000);
        } catch (InterruptedException ex) {
            //Logger.getLogger(TimeOutThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        exit(0);
    }
}