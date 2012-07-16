/**
 * @author Ndjido A BAR
   @author abdoul A. Abdoul aziz
 */


import java.io.IOException;
import javax.microedition.lcdui.*;


public class Login implements CommandListener
{

    private Display _display;
    private Displayable _next;
    private Image _logo;
    private Command  _loginCmd, _cancelCmd,_inscriptionCmd;
    private Form _form;
    private TextField _login, _pwd;

    public Login(Display display, Displayable next)
    {
        //setting display
        this._display = display;
        this._next = next;

        //loading image logo
        try
        {
            this._logo = Image.createImage("/mlouma.jpeg");

        } catch (IOException ex)
          {
            ex.printStackTrace();
          }

        //textfields
        this._login = new TextField("Login", "", 30, TextField.ANY);
        this._pwd = new TextField("Mot de passe", "", 30, TextField.PASSWORD);
        

        //commandes
        this._cancelCmd = new Command("Sortir", Command.SCREEN,1);
        this._loginCmd = new Command("Connection", Command.SCREEN,0);
        this._inscriptionCmd = new Command("Inscription",Command.SCREEN,0);
        //form
        this._form = new Form("Authentification");

        initLogin();

    }
    
    public void initLogin()
    {

        //adding logo to form
        try
        {
            this._form.append(this._logo);
            
        } catch(Exception e)
          {
            e.printStackTrace();
          }

        //adding textfields to form
        this._form.append(this._login);
        this._form.append(this._pwd);

        //adding commands
        this._form.addCommand(this._loginCmd);
        this._form.addCommand(this._cancelCmd);
        this._form.addCommand(_inscriptionCmd);
        this._form.setCommandListener(this);

       this._display.setCurrent(this._form);

    }

    public void commandAction(Command c, Displayable d)
    {

        String label = c.getLabel();

        if(label.equals("Connection"))
        {
            if(this._login.getString().equals(""))
            {
               Alert errorLogin = new Alert("Erreur","Le nom d'utilisateur est requis!", null, AlertType.WARNING);
                     errorLogin.setTimeout(5000);
            _display.setCurrent(errorLogin);
            
            new Login(this._display,this._next);

            }

             if(this._pwd.getString().equals(""))
            {
               Alert errorLogin = new Alert("Erreur","Le mot de passe est requis!", null, AlertType.WARNING);
                     errorLogin.setTimeout(5000);
            _display.setCurrent(errorLogin);
            
            new Login(this._display,this._next);
            }

            if(this._login.getString().equals("ndjido") && this._pwd.getString().equals("einstein"))
            {
               Alert success = new Alert("Success","Welcome to MLouma platform!", null, AlertType.INFO);
                     success.setTimeout(5000);
                     _display.setCurrent(success);

            new MainMenu(this._display);

            }
            
            else
              {
                Alert error = new Alert("Erreur","Login ou mot de passe incorrect!", null, AlertType.ERROR);
                      error.setTimeout(5000);
                      _display.setCurrent(error);

              }
        }
        if(c==_inscriptionCmd){
        	new Inscription(this._display);
        }
        if(c==_cancelCmd){
        	
        }

    }
 
}
