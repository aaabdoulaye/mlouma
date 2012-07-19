/**
 * @author A. Abdoul Aziz
 */

import java.util.Calendar;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;


public class Depot implements CommandListener
{
	private Display _display;
	int i;
	private TextField _ldp, _quantite, _unite;
	private Form _form;
	private Command _retour,_envoyer ;
	
	DateField date;
    MessageConnection clientConn;
	String[] transport = {"Non","Oui"};
	ChoiceGroup g1;
	Mabase mb;
	
	
	
	public Depot(Display display)
	{
		// ouverture de la base en fonction du type (achat ou vente)
		if(MainMenu.G1.isSelected(0))
		{
			mb = new Mabase("achatdb"); 
		}
		else 
		{
			mb = new Mabase("ventedb");
		}
		
		//initialisation des variables 
		this._display = display;
		_form = new Form("MLouma Depot");
		_ldp = new TextField("Localite:","",25,TextField.ANY);
		_quantite = new TextField("Quantite:","",25,TextField.NUMERIC);
		_unite = new TextField("Unite:","",25,TextField.ANY);
		date = new DateField("Date", DateField.DATE);
		g1 = new ChoiceGroup("Transport:", ChoiceGroup.EXCLUSIVE, transport, null);
		
		_retour = new Command("retour", Command.EXIT, 0);
		_envoyer = new Command("envoyer",Command.OK,0);
		
		//ajout des composants dans l'interface
		_form.append(_ldp);
		_form.append(_quantite);
		_form.append(_unite);
		_form.append(date);
		_form.append(g1);
		
		_form.addCommand(_retour);
		_form.addCommand(_envoyer);
		
		_form.setCommandListener(this);
		
		//affichage 
		this._display.setCurrent(_form);
	}
	
	
	
	

	public void commandAction(Command arg0, Displayable arg1)
	{
		
		if(arg0.equals(_envoyer))
		{
			//recuperation du nombre d'enregistrement
			i = mb.RecordNumber();
			
			//creation d'un objet date qui va recuperer la date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date.getDate());
			
			// mise en formde de la date récuperée
			String date = cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR);
			
			// ecriture dans la base de donnée 
			mb.WriteTestData(ListeProduit.GROUP.getString(ListeProduit.GROUP.getSelectedIndex()),i,date,_ldp.getString(),_quantite.getString(),_unite.getString(),g1.getString(g1.getSelectedIndex()));
			
			
			//fermeture de la base
			mb.CloseRectStore();
			
			// creation d'un objet produit et recuperation des données 
			Produit p1 = new Produit();
			p1.SetDate(date);
			p1.setTransport(g1.getSelectedIndex());
			p1.SetLdp(_ldp.getString());
			p1.SetNom(ListeProduit.GROUP.getString(ListeProduit.GROUP.getSelectedIndex()));
			p1.SetQuantite(_quantite.getString());
			p1.SetUnite(_unite.getString());
			if (MainMenu.G1.isSelected(0))
			{
				p1.SetType(MainMenu.G1.getString(0));
			}
			else 
				p1.SetType(MainMenu.G1.getString(1));
			
			//cration de l'objet JSON et de la donnée à envoyer lors du message
			String donnee = "insj2me*"+p1.toJSON();

		    //System.out.println(donnee);
		    
		    //envoit du message au serveur
			try  
		    {
                clientConn=(MessageConnection)Connector.open("sms://22110");
            } catch(Exception e) 
          		{
                	Alert alert = new Alert("Alert");
                	alert.setString("Impossible de se connecter au serveur. Probleme de réseau");
                	alert.setTimeout(2000);
                	_display.setCurrent(alert);
          		}
          try 
          {
                TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                textmessage.setAddress("sms://22110");
                textmessage.setPayloadText(donnee);
                clientConn.send(textmessage);
                
                Alert alert = new Alert("Alert");
            	alert.setString("votre opération a été envoyée");
            	alert.setTimeout(2000);
            	_display.setCurrent(alert);
            	
                new Offre(_display);
          } catch(Exception e)
          	{
                Alert alert=new Alert("Alert","",null,AlertType.INFO);
                alert.setTimeout(Alert.FOREVER);
                alert.setString("impossible d'envoyer le message");
                _display.setCurrent(alert);
          	}
		 }
		
		
		if(arg0.equals(_retour)){
			new ListeProduit(_display);
		}
		
	}

}
