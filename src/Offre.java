/**
 * @author A. Abdoul Aziz
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;


public class Offre implements CommandListener
{
	
	
	private Display display;
	private Command _sortir,_deposer,_modifier,_supprimer,_renitialiser,_visualiser;
	ChoiceGroup ch ; 
	
	
	String s[];
	String nombase;
	
	Mabase mb;
	
	Form form;
	
	public Offre(Display display)
	{
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
			ch = new ChoiceGroup("demande", ChoiceGroup.EXCLUSIVE, s, null);
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
			new ListeProduit(display,0);
		}
		
		if(c==_modifier)
		{
			
			RecordStore rs = null;
			try
			{
				rs = RecordStore.openRecordStore(nombase, true);
			}
			catch (RecordStoreException e1) 
			{
				e1.printStackTrace();
			}
			
			try
			{
				byte[] rectData = new byte[500];
				 int t=0;
				 String produit="",date="",localite="",quantite="",unite = "",transport="";
				
				 ByteArrayInputStream strmBytes = new ByteArrayInputStream(rectData);
				 
				 DataInputStream strmDataType = new DataInputStream(strmBytes);
				 
				  for (int i = 1; i <= rs.getNumRecords(); i++)
			      {
					  if(i==ch.getSelectedIndex()+1)
					  {
						  rs.getRecord(i, rectData, 0);
						 
						   t=strmDataType.readInt()+1;
						   produit = strmDataType.readUTF();
						   date = strmDataType.readUTF();
						   localite = strmDataType.readUTF();
						   quantite = strmDataType.readUTF();
						   unite = strmDataType.readUTF();
						   transport = strmDataType.readUTF();
								   
						  strmBytes.reset();
					  }
			      }
				  
				  strmBytes.close();
				  strmDataType.close();
				 new ListeProduit(nombase,display,t,produit,date,localite,quantite,unite,transport);
				}
			
				catch(Exception e)
				{
					e.printStackTrace();
				} 
				
		}
		
		if(c.equals(_sortir))
		{
			new MainMenu(display);
		}
		
		if(c.equals(_supprimer))
		{
			mb.openRectStore(nombase);
			mb.deleteRecord(ch.getSelectedIndex());
			mb.CloseRectStore();
			new Offre(display);
		}
		if(c.equals(_renitialiser))
		{
			
			mb.deleteRecStore(nombase);
			mb.CloseRectStore();
			new Offre(display);
		}
		if(c.equals(_visualiser))
		{
			new VuProduit(display,ch.getSelectedIndex()+1);
			mb.CloseRectStore();
		}
	}
	
	

}
