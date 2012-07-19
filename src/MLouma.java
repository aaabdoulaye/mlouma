/**
 * @author Ndjido A BAR
 * @author A. Abdoul Aziz
 */


import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;

public class MLouma extends MIDlet implements CommandListener
{
    private Display _display = null;
    private Command _exitCmd = new Command("Exit", Command.EXIT, 0);
  

    
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
    	if(c==_exitCmd)
    	{
    		destroyApp(true);
    	}
       
    }

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
