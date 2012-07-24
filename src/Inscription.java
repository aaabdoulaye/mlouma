/**
 * @author A. Abdoul Aziz
 */

import java.io.IOException;
import java.io.InterruptedIOException;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.TextMessage;



public class Inscription  implements CommandListener,MessageListener
{
	
	//les commanndes 
	private Command quitter;
	private Command envoyer;
	
	
	Displayable next;
	Display display; 
	
	//champs de texte
	private TextField nom; 
	private TextField prenom;
	private TextField adresse;
	private TextField region; 
	private TextField mail;
	private TextField localite;
	private TextField Tproduction;
	
	MessageConnection clientConn;
    MessageConnection msgConnection;
	MessageListener listener;
	String port="22110";
	 
    private Form compose;
	
	
	String[] list = {"Homme","Femme"};  
    ChoiceGroup group1;

	Personne u1 = new Personne();

	
	public Inscription(Display display)
	{
		this.display = display;
	 
	 
        compose = new Form("Formulaire d'inscription");
        
        nom = new TextField("Nom ","",15,TextField.ANY);
        prenom = new TextField("Prenom:","",25,TextField.ANY);
	    adresse = new TextField("Adresse:","",15,TextField.ANY);
	    region = new TextField("Region:","",15,TextField.ANY);
	    mail = new TextField("email:","",25,TextField.EMAILADDR);
	    localite = new TextField("Localite:","",15,TextField.ANY);
	    Tproduction = new TextField("Type Production:", "", 15, TextField.ANY);
	    
        envoyer = new Command("envoyer",Command.SCREEN,1);
        quitter = new Command("quitter",Command.BACK,0);
            
            
	     group1 = new ChoiceGroup("Sexe:",ChoiceGroup.EXCLUSIVE,list,null);


        compose.append(nom);
        compose.append(prenom);
	    compose.append(group1);
	    compose.append(adresse);
	    compose.append(region);
	    compose.append(localite);
	    compose.append(Tproduction);
	    compose.append(mail);
	    
	    compose.addCommand(envoyer);
	    compose.addCommand(quitter);
	    compose.setCommandListener(this);
	    this.display.setCurrent(compose);
	   
	    
	}

	public void commandAction(Command c, Displayable d) 
	{
			if (c == envoyer)
			{
				//creation de l'utilisateur et insertion des données  
			 	u1.setNom(nom.getString());
			    u1.setPrenom(prenom.getString());
			    u1.setSexe(group1.getSelectedIndex());
			    u1.setRegion(region.getString()); 
			    u1.setLocalite(localite.getString());
			    u1.setAdresse(adresse.getString());
			    String s = mail.getString().replace('@', '-');
			    u1.setMail(s);
			    u1.setProduction(Tproduction.getString());
			    
			    System.out.println(s);
			    // envoit de l'objet jSON
			    String donnee ="insj2me*"+u1.toJSON();
			 
			   new EnvoieSms(donnee);
			   ListenSMS sms = new ListenSMS("22110", this);
			   sms.start();
			   
			 }
		
			if (c==quitter)
			{
			 new Login(this.display,next);
			}
			
		
		}


	public void notifyIncomingMessage(MessageConnection arg0) {
		Message message;
		try {
			message = arg0.receive();
			if (message instanceof TextMessage) {
				TextMessage tMessage = (TextMessage)message;
				System.out.println(tMessage.getPayloadText());
				Alert alert = new Alert("Alert");
		        alert.setString(tMessage.toString());
		        alert.setTimeout(10000);
		        display.setCurrent(alert);
			} else {
				System.out.println("message pas recu");
			}
		} catch (InterruptedIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}
 


class ListenSMS extends Thread {
	private MessageConnection msgConnection;
	private MessageListener listener;
	private String port;
 
	public ListenSMS(String port, MessageListener listener) {
		this.port = port;
		this.listener = listener;
	}
 
	public void run() {
		try {
			msgConnection = (MessageConnection)Connector.open("sms://:"+port);
			msgConnection.setMessageListener(listener);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
}
