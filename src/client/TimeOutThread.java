package client;

import exceptions.ServerErrorException;
import static java.lang.System.exit;

/**
 * This is a thread used to create a countdown for when the server doesn't
 * connect within the specified time-limit (15s).
 *
 * @author Olivia
 */
public class TimeOutThread extends Thread {

    /**
     * Default run method.
     */
    @Override
    public void run() {
        exit(0);
    }

    /**
     * Called to start the time out for the server.
     *
     * @throws ServerErrorException when it finishes execution.
     */
    public void sleepy() throws ServerErrorException {
        try {
            sleep(15000);
        } catch (InterruptedException ex) {
            //Logger.getLogger(TimeOutThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
