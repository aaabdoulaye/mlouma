import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;


public class VuProduit implements CommandListener {
	private Display _display;
	private Command _retour;
	private Form _form;
	private int _idproduit;
	TextField tb ;
	Mabase mb;
	
	public VuProduit(Display display, int i) {
		
		if(MainMenu.G1.isSelected(0))
			mb = new Mabase("achatdb");
		else
			mb = new Mabase("ventedb");
		_form =new Form("details");
		this._display = display;
		this._idproduit=i;
		_retour = new Command("retour",Command.EXIT,0);
		
		String s = mb.ReadStream2(_idproduit); 
		tb = new TextField("details",s, 255, TextField.UNEDITABLE);
		_form.append(tb);
		
		_form.addCommand(_retour);
		
		
		
		
		
		// je me suis arreter ici ; 
		
		
		
		
		_form.setCommandListener(this);
		
		this._display.setCurrent(_form);
	}
	

	public void commandAction(Command c, Displayable d) {
		if(c==_retour)
		{
			new Offre(_display);
		}
		
	}

}
