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


public class Depot implements CommandListener{
	private Display _display;
	
	
	private TextField _ldp, _quantite, _unite;
	DateField date;
	
	
    MessageConnection clientConn;

	
	String[] transport = {"Non","Oui"};
	ChoiceGroup g1;
	
	
	private Form _form;
	
	private Command _retour ;
	private Command _envoyer;
	
	public Depot(Display display)
	{
		
		
		this._display = display;
		_form = new Form("MLouma Depot");
		
		_ldp = new TextField("ldp:","",15,TextField.ANY);
		_quantite = new TextField("quantite:","",5,TextField.NUMERIC);
		_unite = new TextField("unite:","",15,TextField.ANY);
		
		date = new DateField("date", DateField.DATE);
		
		
		g1 = new ChoiceGroup("Transport:", ChoiceGroup.EXCLUSIVE, transport, null);
		
		_retour = new Command("retour", Command.EXIT, 0);
		_envoyer = new Command("envoyer",Command.OK,0);
		
		
		
		
		_form.append(_ldp);
		_form.append(_quantite);
		_form.append(_unite);
		_form.append(date);
		_form.append(g1);
		
		
		
		
		_form.addCommand(_retour);
		_form.addCommand(_envoyer);
		
		_form.setCommandListener(this);
		this._display.setCurrent(_form);
	}
	
	
	
	

	public void commandAction(Command arg0, Displayable arg1)
	{
		if(arg0.equals(_envoyer))
		{
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date.getDate());
			String date = cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR);
			
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
			
			
			String donnee = p1.toJSON();

		    System.out.println(donnee);
		    
		    
			try  
		    {
                clientConn=(MessageConnection)Connector.open("sms://22110");
            } catch(Exception e) 
          		{
                	Alert alert = new Alert("Alert");
                	alert.setString("Unable to connect to Station because of network problem");
                	alert.setTimeout(2000);
                	_display.setCurrent(alert);
          		}
          try 
          {
                TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                textmessage.setAddress("sms://22110");
                textmessage.setPayloadText(donnee);
                clientConn.send(textmessage);
                new Offre(_display);
          } catch(Exception e)
          	{
                Alert alert=new Alert("Alert","",null,AlertType.INFO);
                alert.setTimeout(Alert.FOREVER);
                alert.setString("Unable to send");
                _display.setCurrent(alert);
          	}
		 }
		
		
		if(arg0.equals(_retour)){
			new ListeProduit(_display);
		}
		
	}

}
