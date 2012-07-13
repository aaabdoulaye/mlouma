import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;



public class Inscription  implements CommandListener 
{
	
	//les commanndes 
	private Command quitter;
	private Command envoyer;
	
	private Alert alert;
	
	Displayable next;
	Display display; 
	
	//champs de texte
	private TextField nom; 
	private TextField prenom;
	private TextField adresse;
	private TextField region; 
	private TextField mail;
	private TextField localite;
	MessageConnection clientConn;
    private Form compose;
	
	
	String[] list = {"Homme","Femme"};  
    ChoiceGroup group1;

	Personne u1 = new Personne();

	//constructeur
	/** 
	 * 
	 * @param display
	 * @return 
	 */
	public Inscription(Display display)
	{
		this.display = display;
	 
	 
        compose = new Form("Formulaire d'inscription");
        
        nom = new TextField("Nom","",15,TextField.ANY);
        prenom = new TextField("Prenom","",25,TextField.ANY);
	    adresse = new TextField("Adresse","",15,TextField.ANY);
	    region = new TextField("Region","",15,TextField.ANY);
	    mail = new TextField("email","",25,TextField.ANY);
	    localite = new TextField("Localite","",15,TextField.ANY);
	    
	    
        envoyer = new Command("envoyer",Command.SCREEN,1);
        quitter = new Command("quitter",Command.BACK,0);
            
            
	     group1 = new ChoiceGroup("Sexe",ChoiceGroup.EXCLUSIVE,list,null);


        compose.append(nom);
        compose.append(prenom);
	    compose.append(group1);
	    compose.append(adresse);
	    compose.append(region);
	    compose.append(localite);
	    compose.append(mail);
	    
	    compose.addCommand(envoyer);
	    compose.addCommand(quitter);
	    compose.setCommandListener(this);
	    this.display.setCurrent(compose);
	   
	}

	/**
	 * 
	 */
	public void commandAction(Command c, Displayable d) 
	{
			if (c == envoyer)
			{
			 	u1.setNom(nom.getString());
			    u1.setPrenom(prenom.getString());
			    u1.setSexe(group1.getSelectedIndex());
			    u1.setRegion(region.getString()); 
			    u1.setLocalite(localite.getString());
			    u1.setAdresse(adresse.getString());
			    u1.setMail(mail.getString());
			   
			    String donnee ="insj2me*"+u1.toJSON();
			 
			    
			    try  
			    {
                    clientConn=(MessageConnection)Connector.open("sms://22110");
                } catch(Exception e) 
              		{
                    	alert = new Alert("Alert");
                    	alert.setString("Unable to connect to Station because of network problem");
                    	alert.setTimeout(2000);
                    	display.setCurrent(alert);
              		}
              try 
              {
                    TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                    textmessage.setAddress("sms://22110");
                    textmessage.setPayloadText(donnee);
                    clientConn.send(textmessage);
                    new Login(this.display,next);
              } catch(Exception e)
              	{
                    Alert alert=new Alert("Alert","",null,AlertType.INFO);
                    alert.setTimeout(Alert.FOREVER);
                    alert.setString("Unable to send");
                    display.setCurrent(alert);
              	}
			 }
		
			if (c==quitter)
			{
			 new Login(this.display,next);
			}
		
		}

}
