

import java.io.IOException;
import javax.microedition.lcdui.*;


/**
 *
 * @author Administrateur
 */
public class MainMenu implements  CommandListener
{
    private Display _display;
    private Command _exitCmd = new Command("Exit", Command.EXIT, 2);
    private Command _nextCmd = new Command("Selectionner", Command.SCREEN, 2);
    private Image _ventes, _achats;
    String[] mainMenuItem = {"Mes ventes", "Mes achats"};
    private List _mainMenuList;
    private Displayable next;

    public MainMenu(Display display)
    {
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
        this._mainMenuList = new List("Menu Principal", List.IMPLICIT, this.mainMenuItem, mainMenuItemImg);

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
    	
    }

}
