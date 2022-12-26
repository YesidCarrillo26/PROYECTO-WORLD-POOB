package world;
import shapes.*;
import java.lang.Math.*;
import java.util.Random;
import java.util.ArrayList;
/**
 * Write a description of class Army here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Army
{
    private ArrayList<Circle> circles = new ArrayList<Circle>(); 
    private int nArmies ;
    private Nation nation;
    private boolean isVisible;
    private int currentArmy;
    private int armiesToDominate;
    private Rectangle visual;
    
    /**
     * Constructor # 1
     * @param _armies: int, numero de ejercitos
     * @param _location: Nation, la nacion donde van a estar esos ejercitos
     */
    public Army(int _armies, Nation _location)
    {
        this.nArmies = _armies;
        this.nation = _location;
        contructArmies();
    }
    
    /**
     * Constructor #2
     * @param _currentArmy: int, numero de ejercito actual
     * @param _armiesToDominate: int, numero de ejercito para dominar una nacion
     * @param _visual: Rectangle, la nacion
     */
    public Army(int _currentArmy, int _armiesToDominate, Rectangle _visual)
    {
        this.currentArmy = _currentArmy;
        this.armiesToDominate = _armiesToDominate;
        this.visual = _visual;
    }
    
    /**
     * Metodo donde se crean las armies
     */
    public void contructArmies()
    {   
       int i = this.nArmies;
       while(i != 0)
        {
            double posiX = Math.random()*(nation.getxPosition()+46);
            double posiY = Math.random()*(nation.getyPosition()+86);
            if(posiX > nation.getxPosition() +11   && 
               posiY > nation.getyPosition()+51)
            {
                Circle army = new Circle((int)posiX, (int)posiY);
                circles.add(army);
                i--;
            }
       }
       isVisible = true;
    }
    
    /**
     * Metodo para eliminar armies
     * @param _armies: ArrayList<Army>, 
     */
    public void delArmy(ArrayList<Army> _armies)
    {
        makeInvisible();
        _armies.remove(this);
    }
    
    /**
     * Metodo para hacer visible las armies
     */
    public void makeVisible()
    {
        //erase();
        for(Circle c: circles)
        {
            c.makeVisible();
        }
        isVisible = true;
    }
       
    /**
     * Metodo para hacer invisble las armies
     */
    public void makeInvisible()
    {
        for(Circle c: circles)
        {
            c.makeInvisible();
        }
        isVisible = false;
        //draw();
    }
    
    /**
     * Metodo para eliminar el Army
     */
    private void erase()
    {
        Canvas canvas = Canvas.getCanvas();
        canvas.erase(this);
    }
}
