package chapter31;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Exercise31_9SingleServerController implements Initializable{

    @FXML
    private TextArea taServer;

    @FXML
    private TextField tfServer;

    private int ClientNo =0;
    
    private DataInputStream inputFromClient;
    
    private DataOutputStream outputToClient;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		new Thread(()->{
			try{
				
				ServerSocket ss = new ServerSocket(8000);
				
				Platform.runLater(()-> taServer.appendText("Server started at: " + new Date() + '\n'));
				
				//listen for client
				
				Socket soClient = ss.accept();
				
				ClientNo ++;
				
				Platform.runLater(()->{
					taServer.appendText("Starting thread for client at " + new Date() + '\n');
					
					InetAddress inet = soClient.getInetAddress();
					taServer.appendText("Client no " + ClientNo + "'s IP address is " + inet.getHostAddress() + '\n');
					
			
					});
				
				//create data input and output streams
				
				DataInputStream inputFromClient = new DataInputStream(soClient.getInputStream());
				
				while(true){
					
				String text; 
				
				//recieve text from client
				text = inputFromClient.readUTF().trim();
				taServer.appendText("Client: " + text + '\n');
				//}
				
				//respond
			tfServer.setOnAction(e->{	
				
			DataOutputStream outputToClient;
			
			try {
				outputToClient = new DataOutputStream(soClient.getOutputStream());
			
			
				
				String response = tfServer.getText().trim();
				
				
					outputToClient.writeUTF(response);
					tfServer.clear();
					outputToClient.flush();
					taServer.appendText("Server: " + response + '\n');
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				});
				}//end while
				
			}//end try
		catch(IOException ex){
				ex.printStackTrace();
		}//end catch
	}).start();
		
	}
	
}
