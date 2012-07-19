/**
 * @author A. Abdoul Aziz
 */

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;


public class Offre implements CommandListener{
	
	
	private Display display;
	private Command _sortir,_deposer,_modifier,_supprimer,_renitialiser,_visualiser;
	ChoiceGroup ch ; 
	
	
	String s[];
	String nombase;
	
	Mabase mb;
	
	Form form;
	
	public Offre(Display display){
		if (MainMenu.G1.isSelected(0))
		{
			nombase = "achatdb";
			 mb= new Mabase(nombase);
			s= mb.ReadStream(); 
			form = new Form("Mes Offres");
			ch = new ChoiceGroup("achat", ChoiceGroup.EXCLUSIVE, s, null);
			mb.CloseRectStore();
			form.append(ch);
		
		}
		else
		{
			form = new Form("Mes Demandes");
			nombase = "ventedb";
			 mb= new Mabase(nombase);
			s= mb.ReadStream(); 
			ch = new ChoiceGroup("achat", ChoiceGroup.EXCLUSIVE, s, null);
			mb.CloseRectStore();
			form.append(ch);
		}
		
		this.display=display;
		_sortir = new Command("sortir", Command.EXIT, 0);
		_visualiser = new Command("visualiser",Command.ITEM,1);
		_deposer = new Command("deposer",Command.ITEM,2);
		_modifier = new Command("modifier",Command.ITEM,3);
		_supprimer = new Command("supprimer",Command.ITEM,4);
		_renitialiser = new Command("renitialiser", Command.ITEM,5);
		
		
		form.addCommand(_deposer);
		form.addCommand(_modifier);
		form.addCommand(_sortir);
		form.addCommand(_supprimer);
		form.addCommand(_renitialiser);
		form.addCommand(_visualiser);
		
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
			mb.openRectStore(nombase);
			mb.deleteRecord(ch.getSelectedIndex());
			mb.CloseRectStore();
			new Offre(display);
		}
		if(c==_renitialiser)
		{
			
		}
		if(c==_visualiser){
			new VuProduit(display,ch.getSelectedIndex()+1);
			mb.CloseRectStore();
		}
	}
	
	

}
