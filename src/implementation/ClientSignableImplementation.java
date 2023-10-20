package implementation;

import exceptions.DatabaseErrorException;
import exceptions.EmailExistsException;
import exceptions.LoginCredentialException;
import exceptions.ServerErrorException;
import interfaces.Signable;
import models.User;

/**
 * The ClientSignableImplementation implements the methods that are in the
 * Signable interface.
 *
 * @author Leire y Nerea (SignIn)/Irati y Olivia (SignUp)
 */
public class ClientSignableImplementation implements Signable {

    /**
     * Sign up a user with the provided user information.
     *
     * @param user The User object containing user details for registration.
     * @return A User object representing the registered user.
     * @throws ServerErrorException If there is a server error during the
     * sign-up process.
     * @throws EmailExistsException If the provided email address already exists
     * in the database.
     * @throws DatabaseErrorException If there is an error with the database
     * during user registration.
     */
    @Override
    public User signUp(User user) throws ServerErrorException, EmailExistsException, DatabaseErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Sign in a user with the provided user credentials.
     *
     * @param user The User object containing user credentials for sign-in.
     * @return A User object representing the signed-in user.
     * @throws ServerErrorException If there is a server error during the
     * sign-in process.
     * @throws LoginCredentialException If the provided login credentials are
     * invalid.
     * @throws DatabaseErrorException If there is an error with the database
     * during user sign-in.
     */
    @Override
    public User signIn(User user) throws ServerErrorException, LoginCredentialException, DatabaseErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
