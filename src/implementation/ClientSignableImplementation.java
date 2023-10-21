package implementation;

import client.Client;
import exceptions.DatabaseErrorException;
import exceptions.EmailExistsException;
import exceptions.LoginCredentialException;
import exceptions.ServerErrorException;
import interfaces.Signable;
import message.Message;
import message.ResponseRequest;
import models.User;

/**
 * The ClientSignableImplementation implements the methods that are in the
 * Signable interface.
 *
 * @author Leire
 * @author Nerea
 * @author Irati
 * @author Olivia
 */
public class ClientSignableImplementation implements Signable {

    private Client client = new Client();

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

        ResponseRequest request = new ResponseRequest();
        request.setMessage(Message.SIGNUP);
        request.setUser(user);

        ResponseRequest response = client.sendRecieveMessage(request);
        User userResponse = null;
        switch (request.getMessage()) {
            case EMAIL_EXITS_ERROR:
                throw new EmailExistsException("Email already exists. Please either try a different email or log in if you already have an account.");
            case SERVER_CAPACITY_ERROR:
            case DATABASE_ERROR:
            case SERVER_ERROR:
                throw new ServerErrorException("Internal Server Error: We're experiencing technical difficulties. Please try again later or contact our support team for assistance.");
            case RESPONSE_OK:
                userResponse = response.getUser();
                break;

        }
        return userResponse;
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
