import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;


public class Offre implements CommandListener{
	
	//private String[] Menu = {"achat","vente"};
	private Display display;
	private Command _sortir,_deposer,_modifier,_supprimer;
	
	Form form;
	
	public Offre(Display display){
		form = new Form("MLouma");
		this.display=display;
		_sortir=new Command("sortir", Command.EXIT, 0);
		_deposer=new Command("deposer",Command.ITEM,1);
		_modifier=new Command("modifier",Command.ITEM,2);
		_supprimer=new Command("supprimer",Command.ITEM,3);
		
		form.addCommand(_deposer);
		form.addCommand(_modifier);
		form.addCommand(_sortir);
		form.addCommand(_supprimer);
		
		form.setCommandListener(this);
		
		this.display.setCurrent(form);
		
	}

	public void commandAction(Command c, Displayable arg1) {
		if(c==_deposer)
		{
			new ListeProduit(display);
		}
		
		if(c==_modifier)
		{
			
		}
		
		if(c==_sortir)
		{
			new MainMenu(display);
		}
		
		if(c==_supprimer)
		{
			
		}
	}
	
	

}
