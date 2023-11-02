
import java.io.IOException;


import common.ChatIF;

public class ServerConsole implements ChatIF {
	
	public EchoServer server;
	
	public ServerConsole(EchoServer server) {
	    this.server = server;
	}
    
	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);

	}
	
	// created a method to handle user commands - changed for E50 Daniyal Ramooz
	public void processUserInput(String message) {
		String formattedMessage = "SERVER MSG>" + message;
		server.sendToAllClients(formattedMessage);
	}
	
	public void processCommand(String command) throws IOException {
        if (command.startsWith("#quit")) {
            // Quit the server gracefully
            server.quit();
        } else if (command.startsWith("#stop")) {
            // Stop listening for new clients
            server.stopListening();
        } else if (command.startsWith("#close")) {
            // Stop listening and disconnect all existing clients
            server.close();
        } else if (command.startsWith("#setport ")) {
            // Set the port (if the server is closed)
            String[] parts = command.split(" ");
            if (parts.length == 2 && isNumeric(parts[1])) {
                int newPort = Integer.parseInt(parts[1]);
                if (!server.isListening()) {
                    server.setPort(newPort);
                } else {
                    display("Server is already listening. Stop the server first.");
                }
            } else {
                display("Invalid command. Usage: #setport <port>");
            }
        } else if (command.startsWith("#start")) {
            // Start listening for new clients (if the server is stopped)
            if (!server.isListening()) {
                server.listen();
            } else {
                display("Server is already listening.");
            }
        } else if (command.startsWith("#getport")) {
            // Display the current port number
            display("Server is running on port " + server.getPort());
        } else {
            display("Unknown command: " + command);
        }
	}
	
	public boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	public void accept() {
		
		
	}

}
