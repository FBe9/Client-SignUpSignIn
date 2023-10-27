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
    public User signUp(User user) throws ServerErrorException, EmailExistsException, DatabaseErrorException {

        ResponseRequest request = new ResponseRequest();
        ResponseRequest response = null;
        request.setMessage(Message.SIGNUP);
        request.setUser(user);
        try{
            response = Client.sendRecieveMessage(request);
        }catch(ServerErrorException ex){
              throw new ServerErrorException("Internal Server Error: We're experiencing technical difficulties. Please try again later or contact our support team for assistance.");
        }
       
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
     * invalid. during user sign-in.
     */
    @Override
    public User signIn(User user) throws ServerErrorException, LoginCredentialException {
        ResponseRequest rr = new ResponseRequest();
        User serverUser = null;
        Message serverMessage;
        
            //Request from the Client
            rr.setUser(user);
            rr.setMessage(Message.SIGNIN);
            
            //Send the Request and recive the Response
            ResponseRequest rrs = Client.sendRecieveMessage(rr);

            //Response from the Server
            serverMessage = rrs.getMessage();
            switch(serverMessage){
                //All is ok
                case RESPONSE_OK:
                    serverUser = rrs.getUser();
                    break;
                //The user does not exist
                case CREDENTIAL_ERROR:
                    throw new LoginCredentialException("Unknown user, plese change the login or the password.");
                //Something happens at the server
                case SERVER_ERROR:
                        throw new ServerErrorException("It occurs an error at the server, plese try again later");
            }


        return serverUser;

    }

}
