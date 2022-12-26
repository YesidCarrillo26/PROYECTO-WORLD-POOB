package world;


/**
 * Write a description of class Dealer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dealer extends Route
{
    /**
     * Constructor for objects of class Dealer
     */
    public Dealer(Nation _nation1, Nation _nation2, int _cost)
    {
        super( _nation1,  _nation2,  _cost);
    }
    
    /**
     * Setea el costo de la ruta
     */
    public int setCost(int _newCost)
    {
        return this.cost = this.cost * _newCost;
    }
}
