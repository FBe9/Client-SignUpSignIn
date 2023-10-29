package client;

import exceptions.ServerErrorException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ResponseRequest;

/**
 * This class represents a client for interacting with a server using socket
 * communication. It provides methods for sending and receiving messages to and
 * from the server.
 *
 * @author Irati
 */
public class Client {

    // The port used for the connection.
    private static final int PORT;
    // IP address of the server.
    private static final String IP;
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    /**
     * Static initialize for PORT and IP
     */
    static {
        ResourceBundle config = ResourceBundle.getBundle("config.Config");
        PORT = Integer.parseInt(config.getString("PORT"));
        IP = config.getString("IP");
    }

    /**
     * Sends a request to the server and receives a response.
     *
     * @param request The request to be sent to the server.
     * @return The response received from the server.
     * @throws exceptions.ServerErrorException
     */
    public static ResponseRequest sendRecieveMessage(ResponseRequest request) throws ServerErrorException {
        Socket server = null;
        ObjectInputStream read;
        ObjectOutputStream write;
        ResponseRequest response = new ResponseRequest();
        try {
            // Receives a socket of the server
            TimeOutThread timeOut = new TimeOutThread();
            timeOut.sleepy();
            server = new Socket(IP, PORT);
            while (timeOut.isAlive()) {
                if (server.isConnected()) {
                    timeOut.interrupt();
                }
            }
            if (!timeOut.isAlive() && !server.isConnected()) {
                throw new ServerErrorException();
            } else if (!timeOut.isAlive() && server.isConnected()) {
                logger.info("Connection established with server");
            } else {
                throw new ServerErrorException();
            }

            //Initialize ObjectInputStream and ObjectOutputStream
            read = new ObjectInputStream(server.getInputStream());
            write = new ObjectOutputStream(server.getOutputStream());

            //Sends the request to the server
            write.writeObject(request);

            try {
                //Receives the response from the server
                response = (ResponseRequest) read.readObject();
                //Close the ObjectInputStream, ObjectOutputStream and the socket
                read.close();
                write.close();
                logger.info("Connection closed");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServerErrorException ex) {
            throw new ServerErrorException();
        } finally {
            try {
                if (server != null) {
                    server.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return response;
    }
}
