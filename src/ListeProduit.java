import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;


public class ListeProduit implements CommandListener {
	
	String[] liste = { "Arachides","Aubergine","Tomate","Carotte","Chou","Chou-fleur",
						"Igname","Oignon","Corrosol","Citron","Celeri","Papaye"};
	
	static ChoiceGroup GROUP ;
	private Display _display;
	private Command _retour,_envoyer;
	private Form _form;
	
	public ListeProduit(Display display){
		this._display = display;
		_form = new Form("Produits");
		
		GROUP = new ChoiceGroup("Lsite des Produits", ChoiceGroup.EXCLUSIVE, liste, null);
		_retour = new Command("retour", Command.BACK, 0);
		_envoyer = new Command("suivant",Command.OK,0);
		
		_form.append(GROUP);
		_form.addCommand(_retour);
		_form.addCommand(_envoyer);
		_form.setCommandListener(this);
		
		this._display.setCurrent(_form);
		
	}
	
	
	

	public void commandAction(Command c, Displayable d) {
		if (c==_retour)
		{
			new MainMenu(_display);
		}
		
		if(c==_envoyer)
		{
			new Depot(_display);
		}
		
	}

}
