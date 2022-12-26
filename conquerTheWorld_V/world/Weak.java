package world;


/**
 * Write a description of class weak here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Weak extends Route
{
    // instance variables - replace the example below with your own
    //private int x;

    /**
     * Constructor for objects of class Weak
     */
    public Weak(Nation _nation1, Nation _nation2, int _cost)
    {
        super( _nation1,  _nation2,  _cost);
    }
    
    /**
     * Setea el costo de la ruta
     */
    public int setCost(int _newCost)
    {
        return this.cost = _newCost;
    }
}
