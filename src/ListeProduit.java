/**
 * @author A. Abdoul Aziz
 */

import java.util.Calendar;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.wireless.messaging.MessageConnection;



public class ListeProduit implements CommandListener {
	
	String[] liste = { "Arachides","Aubergine","Tomate","Carotte","Chou","Chou-fleur",
						"Igname","Oignon","Corrosol","Citron","Celeri","Papaye"};
	
	private int _id;
	
	private TextField _Tlocalite,_Tquantite,_Tunite;
	private String _localite , _quantite,_unite,_nombase,_produit,_transport;
	
	private DateField _Ddate;
	
	static ChoiceGroup GROUP , g1;;
	private Display _display;
	private Command _retour,_envoyer,_continuer,_envoyermofif,_envoyerO;
	private Form _form;
	
	MessageConnection clientConn;
	
	//constructeur qui sera appelé lors d'une nouvelle insertion
	public ListeProduit(Display display,int i)
	{
		
		this._display = display;
		if(i==0)
		{
			_form = new Form("Produits");
		
			GROUP = new ChoiceGroup("Liste des Produits", ChoiceGroup.EXCLUSIVE, liste, null);
			_retour = new Command("retour", Command.BACK, 0);
			_envoyer = new Command("suivant",Command.OK,0);
		
			_form.append(GROUP);
			_form.addCommand(_retour);
			_form.addCommand(_envoyer);
			_form.setCommandListener(this);
		
			this._display.setCurrent(_form);
		}
		else
		{
			_form = new Form("Produits");
		
			GROUP = new ChoiceGroup("Liste des Produits", ChoiceGroup.EXCLUSIVE, liste, null);
			_retour = new Command("retour", Command.BACK, 0);
			_envoyerO = new Command("suivant",Command.OK,0);
	
			_form.append(GROUP);
			_form.addCommand(_retour);
			_form.addCommand(_envoyerO);
			_form.setCommandListener(this);
	
			this._display.setCurrent(_form);
		}
	}
	
	
	
	//constructeur qui sera appelé lors d'une modification
	
	public ListeProduit(String nombase,Display display, int t, String produit, String date,
			String localite, String quantite, String unite,String transport) 
	{
		
		_id = t;
		this._produit=produit;
		this._nombase=nombase;
		this._transport = transport;
		_localite = localite;
		_quantite = quantite;
		_unite = unite;
		
		
		this._display = display;
		_form = new Form("Produits");
		
		GROUP = new ChoiceGroup("Liste des Produits", ChoiceGroup.EXCLUSIVE, liste, null);
		
		//recuperation du nom du produit
		for (int b=0;b <liste.length;b++)
		{
			if (GROUP.getString(b).equals(_produit))
				GROUP.setSelectedIndex(b, true);
		}
		
		_continuer = new Command("continuer", Command.OK, 0);
		_retour = new Command("retour", Command.BACK, 0);
		
		_form.addCommand(_continuer);
		_form.addCommand(_retour);
		_form.setCommandListener(this);
		_form.append(GROUP);
		this._display.setCurrent(_form);
	}




	




	public void commandAction(Command c, Displayable d) 
	{
		if (c==_retour)
		{
			new MainMenu(_display);
		}
		
		if(c ==_envoyer)
		{
			new Depot(_display);
		}
		// apres la modifiation du nom du produit
		if (c.equals(_continuer))
		{
			String[] transport = {"Non","Oui"};
			
			g1 = new ChoiceGroup("Transport:", ChoiceGroup.EXCLUSIVE, transport, null);
			
			//recuperation du champ transport
			for (int b=0;b <transport.length;b++)
			{
				if (g1.getString(b).equals(_transport))
					g1.setSelectedIndex(b, true);
			}
			
			
			_form = new Form("Modification");
		
			_envoyermofif = new Command("envoyer", Command.OK, 0);
			
			// creation du formulation de modification et insertion des données précedement entrées
			_Tlocalite = new TextField("localite", _localite, 25, TextField.ANY);
			_Tquantite = new TextField("quantite", _quantite, 25, TextField.NUMERIC);
			_Tunite = new TextField("unite", _unite, 25, TextField.ANY);
			_Ddate = new DateField("date", DateField.DATE);
			_form.addCommand(_retour);
			_form.addCommand(_envoyermofif);
			_form.append(_Tlocalite);
			_form.append(_Tquantite);
			_form.append(_Tunite);
			_form.append(_Ddate);
			_form.append(g1);
			_form.setCommandListener(this);
			
			_display.setCurrent(_form);
		}
		
		// envoit de la modification
		if(c.equals(_envoyermofif))
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(_Ddate.getDate());
			String date = cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR);
			
			//creation d'un objet de la base et ouverture de la base de donnée
			
			Mabase mb = new Mabase(_nombase);
			
			//faire un update des données dans la base de données
			mb.updateRecord(_id, ListeProduit.GROUP.getString(ListeProduit.GROUP.getSelectedIndex()), date,
					_Tlocalite.getString(), _Tquantite.getString(), _Tunite.getString(),g1.getString(g1.getSelectedIndex()));
			//fermeture de l'objet
			mb.CloseRectStore();
			
			//création de l'objet JSON et envoit
			Produit p1 = new Produit();
			p1.SetDate(date);
			p1.setTransport(g1.getSelectedIndex());
			p1.SetLdp(_Tlocalite.getString());
			p1.SetNom(ListeProduit.GROUP.getString(ListeProduit.GROUP.getSelectedIndex()));
			p1.SetQuantite(_Tquantite.getString());
			p1.SetUnite(_Tunite.getString());
			if (MainMenu.G1.isSelected(0))
			{
				p1.SetType(MainMenu.G1.getString(0));
			}
			else 
				p1.SetType(MainMenu.G1.getString(1));
			
			
			String donnee = "modifj2me*"+p1.toJSON();

		    System.out.println(donnee);
		    
		    new EnvoieSms(donnee);
		
		
		 }
		
		if(c.equals(_envoyerO))
		{
			new Region(_display);
		}
		
		}
		
	}


