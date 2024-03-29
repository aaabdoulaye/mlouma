/**
 *
 * @author Ndjido A BAR
 * @author A. Abdoul Aziz
  */



import java.io.IOException;
import java.util.Timer;


import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class SplashScreen extends Canvas
{

    private Display _display = null;
    private Displayable _next = null;
    private Timer _timer = null;
    private Image _image = null;

    public SplashScreen(Display display, Displayable next)
    {
        this._display = display;
        this._next = next;
        this._timer = new Timer();
        this._display.setCurrent(this);
        
        // faire un pause pour montrer le logo
        try 
        {
			Thread.sleep(4000);
		}
        catch (InterruptedException e)
		{
			e.printStackTrace();
		}
        

    }

    protected void keyPressed(int keyCode)
    {
        super.keyPressed(keyCode);
        this.dismiss();
    }

    protected void pointerPressed(int x, int y)
    {
        super.pointerPressed(x, y);
        this.dismiss();
    }

    protected void showNotify()
    {
        super.showNotify();
    }

    protected void paint(Graphics g)
    {

        // get the dimensions of the screen:
        int width = getWidth ();
        int height = getHeight();
        

        //load MLouma logo
        if(SplashScreen.this._image==null)
        {
        	try 
            {
				SplashScreen.this._image = Image.createImage("/mlouma.jpeg");
			
            } catch (IOException e)
              {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
        }
        
        // clear the screen (paint it white):
        g.setColor(0xffffff);

        // The first two args give the coordinates of the top
        // left corner of the rectangle. (0,0) corresponds
        // to the top-left corner of the screen.
        g.fillRect(0, 0, width, height);

         if(this._image!=null)
         {
            g.drawImage(_image, width/2, height/2,Graphics.HCENTER|Graphics.VCENTER);
         
			
         }
         else
         {
            // display the "MLouma" message if appropriate.
                Font font = g.getFont();
                int fontHeight = font.getHeight();
                int fontWidth = font.stringWidth("MLOUMA");

            // set the text color to red:
                g.setColor(0, 250, 0);
                g.setFont(font);

            // write the string in the center of the screen
                g.drawString("MLOUMA", (width - fontWidth)/2,(height - fontHeight)/2,Graphics.TOP|Graphics.LEFT);
         }
        
    }

    public void dismiss()
    {
        this._timer.cancel();
        this._display.setCurrent(_next);
    }
 	
}


