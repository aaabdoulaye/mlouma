

import java.io.IOException;
import javax.microedition.lcdui.*;


/**
 *
 * @author A. Abdoul Aziz
 */
public class MainMenu implements  CommandListener
{
    private Display _display;
    private Command _exitCmd = new Command("Exit", Command.EXIT, 2);
    private Command _nextCmd = new Command("Selectionner", Command.SCREEN, 2);
    private Image _ventes, _achats;
     String[] mainMenuItem = {"ventes", "achats"};
    static ChoiceGroup G1; 
    private Form _mainMenuList;
    private Displayable next;

    public MainMenu(Display display)
    {
    	
    	_mainMenuList = new Form("MLouma");
        this._display = display;

        try
        {
            this._ventes = Image.createImage("/panier.jpeg");
            this._achats = Image.createImage("/trollet.jpeg");

        } catch (IOException ex)
          {
            ex.printStackTrace();
          }

        Image[] mainMenuItemImg = {this._ventes, this._achats};
        G1 = new ChoiceGroup("Menu Principal", ChoiceGroup.EXCLUSIVE, mainMenuItem, mainMenuItemImg);
        //this._mainMenuList = new List("Menu Principal", List.IMPLICIT, this.mainMenuItem, mainMenuItemImg);

        _mainMenuList.append(G1);
        _mainMenuList.addCommand(_nextCmd);
        _mainMenuList.addCommand(_exitCmd);
        _mainMenuList.setCommandListener(this);

        this._display.setCurrent(_mainMenuList);

    }
  
    public void commandAction(Command c, Displayable d)
    {
    	if(c==_exitCmd){
    		new Login(this._display,next);
    	}
    	if(c==_nextCmd)
    	{
    		if(G1.isSelected(0))
    		{
    			new Offre(this._display); 
    		}
    		else if(G1.isSelected(1))
    		{
    			
    		}
    		
    	}
    }
    
    
    
    

}
