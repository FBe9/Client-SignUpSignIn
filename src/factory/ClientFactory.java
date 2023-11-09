package factory;

import implementation.ClientSignableImplementation;
import interfaces.Signable;

/**
 * A factory class to return implementations from Signable interface
 *
 * @author Nerea
 */
public class ClientFactory {
    
    private static Signable implementation;

    /**
     * Default constructor for the class ClientFactory.
     */
    public ClientFactory() {
    }

    /**
     * This method returns a new implementation from the Signable interface.
     *
     * @return an object that implements the interface
     */
    public static Signable getImplementation() {
        implementation = new ClientSignableImplementation();
        
        return implementation;
    }
}
