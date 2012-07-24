

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;


public class Region implements CommandListener 
{

	private Display _display;
	private Form _form;
	private Command _envoyer,_retour;
	String[] region = {"Dakar","Thies","St louis","Kaolack","Diourbel","Tambacounda","Fatick","Kolda","Ziguinchor","Matam"};
	String[] rg = {"Dk","Th","STl","Ka","Di","Ta","Fa","Ko","Zi","Ma"};
	ChoiceGroup ch ;
	
	public Region(Display _display)
	{
		this._display = _display;
		_form = new Form("Région"); 
		ch = new ChoiceGroup("Region", ChoiceGroup.EXCLUSIVE,region, null);
		_envoyer = new Command("envoyer", Command.OK, 0);
		_retour = new Command("retour",Command.CANCEL,0);
		_form.append(ch);
		_form.addCommand(_envoyer);
		_form.addCommand(_retour);
		_form.setCommandListener(this);
		this._display.setCurrent(_form);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if(arg0.equals(_envoyer))
		{
			String donnee = "Mlouma "+ListeProduit.GROUP.getString(ListeProduit.GROUP.getSelectedIndex())+" "+rg[ch.getSelectedIndex()]; 
			System.out.println(donnee);
			new EnvoieSms(donnee);
		}
		if(arg0.equals(_retour))
			new ListeProduit(_display, 1);
		
	}

}
