// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  int id;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.id=id;
    openConnection();
    sendToServer("#login" + id);
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    if(message.startsWith("#")){
      this.handleCommand(message);}
      else{
        try{
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  }
  
  private void handleCommand(String message) {
    String[] tab = message.split(" ");
    switch(tab[0]) {
    case("#quit"):
      if(this.isConnected()) {
        try {
          sendToServer("#logoff");
          this.quit();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    break;
    case("#logoff"):
      if(this.isConnected()) {
        try {
          sendToServer("#logoff");
          this.closeConnection();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    break;
    case("#sethost"):
      if(!this.isConnected()) {
        if(tab.length > 1) {
          String host = tab[1];
          this.setHost(host);
          System.out.println("New host is " + this.getHost());
        }
        else {
          System.out.println("Missing host argument !");
        }
      }
    break;
    case("#setport"):
      if(!this.isConnected()) {
        if(tab.length > 1) {
          int port = Integer.parseInt(tab[1]);
          this.setPort(port);
          System.out.println("New port is " + this.getPort());
        }
        else {
          System.out.println("Missing port argument !");
        }
      }
    break;
    case("#login"):
      if(!this.isConnected()) {
        try {
          this.openConnection();
          System.out.println("Logged in to server !");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      else {
        System.out.println("You are already logged in !");
      }
    break;
    case("#gethost"):
      System.out.println(this.getHost());
    break;
    case("#getport"):
      System.out.println(this.getPort());
    break;
    default:
      System.out.println("Invalid command !");
      break;
    }
  }

  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  //@Override
  protected void connectionClosed() {
    clientUI.display("The connection has been successfully closed.");
  }

  //@Override
  protected void connectionException(Exception exception) {
    exception.printStackTrace();
    clientUI.display("Connection to server lost.");
    quit();
  }
}
}
//End of ChatClient class
