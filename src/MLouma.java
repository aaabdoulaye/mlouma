/**
 * @author Ndjido A BAR
 */



//import java.io.IOException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class MLouma extends MIDlet implements CommandListener
{
    private Display _display = null;
    private Command _exitCmd = new Command("Exit", Command.EXIT, 0);
   // private Command _nextCmd = new Command("Selectionner", Command.SCREEN, 1);
    //private Image _ventes, _achats;
    String[] mainMenuItem = {"Mes ventes", "Mes achats"};
   // private List _mainMenuList;

    
    //constructor
    public MLouma()
    {
        
    }

    //shows splash screen
    public void showSplashScreen(Display display, Displayable next)
    {
        new SplashScreen(display, next);
    }
    
    public void startApp()
    {
        if(this._display==null)
        {
            this._display = Display.getDisplay(this);
            showSplashScreen(this._display, new TrivialFrom());
        }

        /*
        Image[] mainMenuItemImg = {this._ventes, this._achats};
        this._mainMenuList = new List("Menu Principal", List.IMPLICIT, this.mainMenuItem, mainMenuItemImg);

        _mainMenuList.addCommand(_nextCmd);
        _mainMenuList.addCommand(_exitCmd);
        _mainMenuList.setCommandListener(this);
        
        this._display.setCurrent(_mainMenuList);
         */
        //new MainMenu();
        if(this._display!=null)
        new Login(this._display,  new TrivialFrom());
    }

    public void pauseApp() 
    {
    }

    public void destroyApp(boolean unconditional)
    {
        notifyDestroyed();
    }

    public void commandAction(Command c, Displayable d)
    {

       
    }

    //trivial form for splash screen
    class TrivialFrom extends Form
    {

        public TrivialFrom()
        {
            super("MLouma");
            addCommand(_exitCmd);
            setCommandListener(MLouma.this);
        }

    }


   
    
}
