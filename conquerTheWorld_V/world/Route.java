package world;

import shapes.*;
import java.util.ArrayList;
import java.awt.geom.*;
/**
 * Clase de route donde se vera una representacion visual de la misma con 
 * la ayuda de java.awt.geom.*
 * @author Yesid Carrillo, Juan David Parroquiano 
 * @version 1.0 (Septiembre 3, 2021)
 */
public abstract class Route
{
    private Nation nation1 ;
    private Nation nation2 ;
    private double x1,x2;
    private double y1,y2;
    protected int cost;
    private boolean isVisible;
    /**
     * Constructor for objects of class Route
     */
    public Route(Nation _nation1, Nation _nation2, int _cost)
    {
        x1 =  _nation1.getCenX() ;
        y1 = _nation1.getCenY();
        x2 = _nation2.getCenX();
        y2 = _nation2.getCenY();
        nation1 = _nation1;
        nation2 = _nation2;
        this.cost = _cost;
        isVisible = false;
        makeVisible();
    }
    
    /**
     * Metodo que valida que exista 2 naciones correspondientes a los
     * colores ingresados 
     * @param _locationA: String,
     * @param _locationB: String,
     */
    public boolean searchRoute(String _locationA, String _locationB)
    {
        if (_locationA.equals(this.getNation1()) && _locationB.equals(this.getNation2())) 
            return true;
        else if (_locationB.equals(this.getNation1()) && _locationA.equals(this.getNation2())) 
            return true;
        return false;
    }
    
    /**
     * Metodo para eliminar una ruta
     * @_routes: ArrayList<Route>, 
     */
    public void delRoute(ArrayList<Route> _routes)
    {
        makeInvisible();
        _routes.remove(this);
    }
    
    /**
     * Metodo para hacer visible una ruta
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Metodo para hacer invisble una ruta
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Metodo para obtener el color de una nacion 1
     */
    public String getNation1()
    {
        return nation1.getColor();
    }
    
    /**
     * Metodo para obtener el color de una nacion 2
     */
    public String getNation2()
    {
        return nation2.getColor();
    }
    
    /**
     * Devuelve el costo de la ruta
     */
    public int getCost()
    {
        return this.cost;
    }
    
    /**
     * Setea el costo de la ruta
     */
    public abstract int setCost(int _newCost);
    
    /**
     * Metodo para dibujar la ruta
     */
    private void draw(){
        Canvas canvas = Canvas.getCanvas();
        canvas.draw(this,"black",new Line2D.Double(x1,y1,x2,y2));
    }
    
    /**
     * Metodo para eliminar la ruta
     */
    private void erase()
    {
        Canvas canvas = Canvas.getCanvas();
        canvas.erase(this);
    }
    
}
