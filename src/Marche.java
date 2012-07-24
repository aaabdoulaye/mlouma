/**
 * @author A. Abdoul Aziz
 */

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;


public class Marche implements CommandListener 
{

	private Display _display;
	private Command _retour,_envoyer;
	private Form _form;
	String [] choix = {"Les Offres","Les Demande"};
	private ChoiceGroup ch;
	
	public Marche(Display display)
	{
		this._display = display;
		_form = new Form("Mon Marché");
		_retour = new Command("retour", Command.CANCEL, 0);
		_envoyer = new Command("envoyer",Command.OK,0);
		ch = new ChoiceGroup("Choix", ChoiceGroup.EXCLUSIVE, choix, null);
		
		_form.addCommand(_envoyer);
		_form.addCommand(_retour);
		_form.append(ch);
		_form.setCommandListener(this);
		_display.setCurrent(_form);
		
		
	}
	
	public void commandAction(Command c, Displayable d) 
	{
		if(c.equals(_envoyer))
		{
			if(ch.isSelected(0))
			{
				new ListeProduit(_display,1);
			}
			else
				new EnvoieSms("demande");
			}
		if(c.equals(_retour))
		{
			new MainMenu(this._display);
		}
	}

}
