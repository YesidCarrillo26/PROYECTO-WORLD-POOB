package world;
import java.util.ArrayList;

/**
 * Clase que crea una Nacion de tipo Walled
 * 
 */
public class Walled extends Nation
{
    // instance variables - replace the example below with your own
    //private int x;

     /**
     * Constructor for objects of class NormalNation
     */
    public Walled(String _color, int _x, int _y, int _armies)
    {
       super(_color, _x, _y, _armies);
    }
    
     /**
     * Constructor #2 for objects of class NormalNation
     */
    public Walled(int _posX ,int _posY, Integer _randColor, int _currentArmies, int _armiesToDominate)
    {
       super(_posX , _posY,  _randColor,  _currentArmies,  _armiesToDominate);
    }
    
    /**
     * Metodo para eliminar las armies
     * @param _armies: ArrayList<Army>, elimina las armies del arreglo
     */
    public void delArmy(ArrayList<Army> _armies)//Army _army)
    {
        /*//nArmies --;
        //_army.delArmy(this.armies);
        //this.armies.remove(_army);
        for(Army a: armies) // recorre las armies de la nación
            a.delArmy(_armies); // elmiina la army "a" de las naciones que esten en mundo "_armies"
        _armies.removeAll(armies); // remueve todas las naciones en el mundo q tenga esta nación 
        */
    }
    
    /**
     * Metodo de setea las nArmies
     * @param _newArmies: int, nuevas armies de la nation
     */
    public void setnArmies(int _newArmies)
    {
        this.nArmies = _newArmies;
        if(_newArmies == 0)
        {
            this.armies.clear();// =  null; // vuelve nulo el arreglo de armies 
        }
        else
        {
            this.addArmy(new Army(_newArmies, this));
        }
    }
    
    /**
     * Metodo para obtener el número de armies según nación Walled
     */
    public int getnArmiesType()
    {
        return 0;
    }
}
