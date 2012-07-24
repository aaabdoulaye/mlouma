/**
 * @author A. Abdoul Aziz
 */

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;


public class EnvoieSms {
	Display _display;
	MessageConnection clientConn;
	
	public EnvoieSms(String s)
	{
		
		
		try  
	    {
            clientConn=(MessageConnection)Connector.open("sms://22110");
        } catch(Exception e) 
      		{
        		
            	Alert alert = new Alert("Alert");
            	alert.setString("Impssible de se connecter au serveur. Probleme de réseau");
            	alert.setTimeout(2000);
            	_display.setCurrent(alert);
      		}
      try 
      {
            TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
            textmessage.setAddress("sms://22110");
            textmessage.setPayloadText(s);
            clientConn.send(textmessage);
            
            Alert alert = new Alert("Alert");
        	alert.setString("Succés");
        	alert.setTimeout(2000);
        	_display.setCurrent(alert);
        	
           // new MainMenu(_display);
      } catch(Exception e)
      	{
            Alert alert=new Alert("Alert","",null,AlertType.INFO);
            alert.setTimeout(Alert.FOREVER);
            alert.setString("Impossible d'envoyer les modifications");
            _display.setCurrent(alert);
      	}
	}

}
