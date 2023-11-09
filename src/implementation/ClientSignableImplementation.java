package implementation;

import client.Client;
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

    /**
     * Default constructor for the class ClientSignableImplementation.
     */
    public ClientSignableImplementation() {
    }

    /**
     * Sign up a user with the provided user information.
     *
     * @param user The User object containing user details for registration.
     * @return A User object representing the registered user.
     * @throws ServerErrorException If there is a server error during the
     * sign-up process.
     * @throws EmailExistsException If the provided email address already exists
     * in the database.
     */
    @Override
    public User signUp(User user) throws ServerErrorException, EmailExistsException {

        ResponseRequest request = new ResponseRequest();
        ResponseRequest response = new ResponseRequest();
        request.setMessage(Message.SIGNUP);
        request.setUser(user);
        try {
            response = Client.sendRecieveMessage(request);
        } catch (ServerErrorException ex) {
            if (ex.getMessage().equalsIgnoreCase("connect timed out")) {
                throw new ServerErrorException("{name}, the connection has timed out. Our server is taking too long to respond.");
            } else {
                throw new ServerErrorException("{name}, we're experiencing technical difficulties. Please try again later or contact our support team for assistance.");
            }
        }

        User userResponse = null;
        if (response != null) {
            switch (response.getMessage()) {
                case EMAIL_EXITS_ERROR:
                    throw new EmailExistsException("{email} already exists. Please either try a different email or log in if you already have an account.");
                case SERVER_CAPACITY_ERROR:
                    throw new ServerErrorException("{name}, server is at max capacity, please try again later.");
                case SERVER_ERROR:
                    throw new ServerErrorException("{name}, we're experiencing technical difficulties. Please try again later or contact our support team for assistance.");
                case RESPONSE_OK:
                    userResponse = response.getUser();
                    break;

            }
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
     * invalid. during user sign-in.
     */
    @Override
    public User signIn(User user) throws ServerErrorException, LoginCredentialException {
        ResponseRequest rr = new ResponseRequest();
        User serverUser = null;
        Message serverMessage;
        ResponseRequest rrs = null;
        //Request from the Client
        rr.setUser(user);
        rr.setMessage(Message.SIGNIN);

        //Send the Request and recive the Response
        try {
            rrs = Client.sendRecieveMessage(rr);
        } catch (ServerErrorException ex) {
            if (ex.getMessage().equalsIgnoreCase("connect timed out")) {
                throw new ServerErrorException("The connection has timed out. Our server is taking too long to respond.");
            } else {
                throw new ServerErrorException("We're experiencing technical difficulties. Please try again later or contact our support team for assistance.");
            }
        }

        //Response from the Server
        serverMessage = rrs.getMessage();
        if (serverMessage != null) {
            switch (serverMessage) {
                //All is ok
                case RESPONSE_OK:
                    serverUser = rrs.getUser();
                    break;
                //The user does not exist
                case CREDENTIAL_ERROR:
                    throw new LoginCredentialException("Unknown user, please change the login or the password.");
                //Something happens at the server
                case SERVER_CAPACITY_ERROR:
                    throw new ServerErrorException("Server is at max capacity, please try again later.");
                case SERVER_ERROR:
                    throw new ServerErrorException("We're experiencing technical difficulties. Please try again later or contact our support team for assistance.");
            }
        }

        return serverUser;

    }

}
