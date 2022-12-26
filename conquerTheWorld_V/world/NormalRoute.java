package world;


/**
 * Write a description of class NormalRoute here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NormalRoute extends Route
{

    /**
     * Constructor for objects of class NormalRoute
     */
    public NormalRoute(Nation _nation1, Nation _nation2, int _cost)
    {
        super( _nation1,  _nation2,  _cost);
    }
    
    /**
     * Setea el costo de la ruta
     * @param _newCost: int, el nuevo costRuta
     */
    public int setCost(int _newCost)
    {
        return this.cost = _newCost;
    }

}
