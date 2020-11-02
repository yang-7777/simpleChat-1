// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import client.*;
import common.*;

/**
 * Some of the code here is cloned in ClientConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ServerConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the server that created this ServerConsole.
   */
  EchoServer server;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ServerConsole UI.
   *
   * @param port The port to connect on.
   */
  public ServerConsole(int port) 
  {
    server = new EchoServer(port);
    try 
    {
      server.listen();
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating server");
      System.exit(1);
    }
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the ServerConsole's message handler.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true) 
      {
        message = fromConsole.readLine();
        server.handleServerConsole(message);
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    if(message.startsWith("#")){
      return;
    }
    else{
          System.out.println("SERVER MSG> " + message);
    }
  }
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Server UI.
   *
   */
  public static void main(String[] args) 
  {
    int port = 0;  //The port number
    //sets the port to the 1st element of the port and if 
    try{
      port = Integer.parseInt(args[1]);
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT;
    }
    ServerConsole chat= new ServerConsole(port);
    chat.accept();  //Wait for console data
  }
}
//End of ServerConsole