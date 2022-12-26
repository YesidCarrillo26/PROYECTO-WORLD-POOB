package world;

import shapes.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase de nation donde se vera una representacion visual de la misma con 
 * colores diferentes en forma de rectangulo
 * 
 * @author Yesid Carrillo, Juan David Parroquiano 
 * @version 1.0 (Septiembre 3, 2021)
 */
public abstract class Nation
{
    Rectangle nation ;
    private String color ;
    private boolean isVisible;
    private int xPosition, yPosition;
    private int width = 40;
    private int height = 40;
    protected int nArmies;
    private int nNations;
    private HashMap<Integer, String> colors = new HashMap<Integer, String>();
    private ArrayList<Integer> cen = new ArrayList<Integer>();
    private ArrayList<Route> properRoutes = new ArrayList<Route>();
    protected ArrayList<Army> armies = new ArrayList<Army>();
    private Rectangle view ;
    private Rectangle world;
    private Rectangle visual;
    private Army army;
    private int posX;
    private int posY;
    private String colorRandom;
    private Random r = new Random();
    private boolean haveArmies = false;
    private int conquered = 0; // armies para conquistar la nación
    private int sumArmies = 0; // va agregando armies
  
    /**
     * Constructor de la nacion
     * @param _color: String, color de la nacion
     * @param _x: int, posicion en x de la nacion
     * @param _y: int, posicion en y de la nacion
     */
    
    public Nation(String _color, int _x, int _y, int _armies)
    {
        this.color = _color;
        this.xPosition = _x;
        this.yPosition = _y;
        this.nArmies = _armies;
        nation = new Rectangle();
        nation.changeColor(_color);
        nation.changeSize(width, height);
        nation.moveHorizontal(_x);
        nation.moveVertical(_y);
        isVisible = false;
    }
    
    Integer randColor ;
    /**
     * Segundo constructor de Nación
     * @param _posX , posición en x
     * @param _posY . posición en y
     * @param _randColor, nuevo color aleatorio 
     * @param _currentArmies, armies actuales
     * @param _armiesToDominate, armies necesarias para conquistar la nación 
     */
    public Nation(int _posX ,int _posY, Integer _randColor, int _currentArmies, int _armiesToDominate)
    {
        colors.put(0,"yellow");
        colors.put(1,"blue");
        colors.put(2,"red");
        colors.put(3,"green");
        colors.put(4,"gray");
        colors.put(5,"magenta");
        
        this.xPosition = _posX;
        this.yPosition = _posY; 
        this.randColor = _randColor;
        this.color = colors.get(_randColor);
        this.colorRandom = colors.get(_randColor);
        this.conquered = _armiesToDominate;
        this.nArmies = _currentArmies;
        sumArmies(_currentArmies); // suma _currentArmies a variable sumaArmies.
        
        nation = new Rectangle();
        nation.changeColor(colors.get(_randColor));
        nation.changeSize(width, height);
        nation.moveHorizontal(_posX);//this.posX);
        nation.moveVertical(_posY);//this.posY);
        isVisible = false;
    }
    
    /**
     * Metodo que retorna el valor entero del color de la nacion
     * @param _color de cual se quiere conocer su némuro entero 
     * @return número entero
     */
    public Integer getNumberOfColor(String _color)
    {
        Integer ret = null;
        if(_color == "yellow")
        {
            ret = 0;
        }
        else if (_color == "blue")
        {
            ret = 1;
        }
        else if (_color == "red")
        {
            ret = 2;
        }
        else if (_color == "green")
        {
            ret = 3;
        }
        else if (_color == "gray")
        {
            ret = 4;
        }
        else if (_color == "magenta")
        {
            ret = 5;
        }
        return ret ;
    }
    
    /**
     * Método que retorna el color correspondiente a un número en específico
     * @param _int; número del que se desea conocer su correspondiente color
     * @return color String
     */
    public String getNameColor(int _int)
    {
        return colors.get(_int);
    }
    
    /**
     * Retorna el color de la nación
     */
    public Integer getRandColor()
    {
        return this.randColor;
    }
    
    /**
     * Metodo que retorna las armies necesarias para conquistar esta nación 
     * @return conquered, armas para poder conquistar 
     */
    public int getArmiesToDominate()
    {
        return this.conquered;
    }
    
    public ArrayList<Army> getArmies()
    {
        return this.armies;
    }
    
    /**
     * setea la posición en X
     */
    public void setPosx(int _newx)
    {
        this.xPosition = _newx;
    }
    /**
     * Setea la posición en Y
     */
    public void setPosy(int _newy)
    {
        this.yPosition = _newy;
    }
    
    /**
     * Tercer constructor de nacion 
     * @param color: String, color de la nacion
     * @param _x: int, posicion en x de la nacion
     * @param _y: int, posicion en y de la nacion
     * @param _nArmies: int, numero de ejercitos
     * @param _world: World, el mundo
     */
    
    public Nation(String color, int x, int y, int _nArmies, Rectangle _world)
    {
        this.color = color;
        this.world = _world;
        xPosition = getPositionX(x);
        yPosition = getPositionY(y);
        this.nArmies = nArmies;
        view = new Rectangle(width,height);
        //configureVisual();
    }  
    
    /**
     * Cuarto constructor de nación 
     * @param _ArmiesToDominate: int, numero de armies para dominar una nacion
     * @param _currentArmy: int, numero de armies actual
     * @param _world2: Rectangle, el mundo
     * @param _nations: ArrayList<Nation>, las naciones que tiene el mundo
     * @param _nNations: int, numero de nations
     */
    public Nation(int _armiesToDominate, int _currentArmy, Rectangle _world2, ArrayList<Nation> nations,
                int nNations)
    {
        this.world = _world2;
        isVisible = false;
        putData(nations);
        change();
        visual = new Rectangle(width, height);
        army = new Army(_currentArmy,_armiesToDominate,visual);
        this.nNations = nNations;
        this.conquered = _armiesToDominate;
        //configureVisual();
    }
    
    /**
     * Metodo que cambia las posciones X, Y, el color del mundo y sus naciones
     */
    public void change()
    {
        posX = r.nextInt(400);//this.width);
        posY = r.nextInt(290);//this.height);
        colorRandom = randomColor();
    }
    
    /**
     * Devuelve la posición en x de la Nación
     */
    public int getPosX()
    {
        return this.posX;
    }
    
    /**
     * Devuelve la posición en y de la Nación 
     */
    public int getPosY()
    {
        return this.posY;
    }
    
    /**
     * Retorna el color random de la nación 
     */
    public String getcolorRandom()
    {
        return colorRandom;
    }
    
    /**
     * Metodo que retorna un color random para el mundo
     */
    private String randomColor()
    {
        int R = r.nextInt(255) + 1;
        int G = r.nextInt(255) + 1;
        int B = r.nextInt(255) + 1;
        String RR = Integer.toString(R);
        String GG = Integer.toString(G);
        String BB = Integer.toString(B);
        String res = String.join(",",RR,GG,BB);
        return res;
    }
    
    /**
     * 
     * @param nations: ArrayList<Nations>
     */
    private void putData(ArrayList<Nation> nations)
    {
        this.change();
        while( searchCollisions(posX, posY, nations) )
        {
            this.change();
        }
        this.color = this.colorRandom;
        xPosition = getPositionX(posX);
        yPosition = getPositionY(posY);
    }
    
    /**
     * Busca si alguna Nación se encuentra en la misma posición
     * @param _x , posición en X
     * @param _y, posición en Y
     * @param nations, lista de naciones 
     */
    private boolean searchCollisions(int _x, int _y, ArrayList<Nation> nations)
    {
        for (Nation i: nations)
        {
            if ( (i.getCenX() == _x && i.getCenY() == _y) || this.color == i.getColor())
                return true;
        }
        return false;
    }
    
    /**
     * Retorna si la nación esta conquistada 
     */
    public boolean isConquered()
    {
        if(this.sumArmies >= this.conquered)
            return true ;
        return false;
    }
    
    /**
     * Metodo que suma armies cada vez que se ejecuta el metodo 
     * "moveArmyOneRoute" en la clase World
     */
    public void sumArmies(int _newArmies)
    {
        this.sumArmies += _newArmies;
    }
    
    /**
     * metodo que retorna las armies actuales de la nación 
     * @return sumArmies, armies actuales
     */
    public int getCurrentArmies()
    {
        return this.sumArmies;
    }
    
    /**
     * Methodo que retorna la posición x de la nación 
     */
    public int getxPosition()
    {
        return this.xPosition;
    }   
    
    /**
     * Devuelve el número de naciones
     */
    public int getnNation()
    {
        return this.nNations;
    }
    
    /**
     * Methodo que retorna la posición y de la nación 
     */
    public int getyPosition()
    {
        return this.yPosition;
    }
    
    /**
     * Metodo para obtener el color de una nacion
     */
    public String getColor()
    {
        return this.color;
    }
    
    /**
     * Devuelve si la nación tiene rutas o no 
     */
    public boolean getHaveRoutes()
    {
        if(this.properRoutes.size() > 0)
            return true;
        return false;
    }
    
    /**
     * Método que retorna la lista de rutas de una nación 
     */
    public ArrayList<Route> getProperRoutes()
    {
        return this.properRoutes;
    }
    
    /**
     * Metodo para hacer visible una nación
     */
    public void makeVisible(){
        nation.makeVisibles();
    }
    
    /**
     * Metodo para hacer invisible una nación
     */
    public void makeInvisible()
    {
        nation.makeInvisibles();
    }
    
    /**
     * Metodo para adicionar una ruta 
     * @param _route: Route, ruta
     * @param _routes: ArrayList<Route>, 
     * @param _nation2: Nation
     */
    public void addRoute(Route _route, ArrayList<Route> _routes,Nation _nation2)
    {
        _routes.add(_route);
        properRoutes.add(_route);
        _nation2.addRoute(_route);
    }
    
    /**
     * Metodo 2 para adicionar una ruta
     * @param _routes: Route,
     */
    public void addRoute(Route _route)
    {
        if (!properRoutes.contains(_route))
            properRoutes.add(_route);
    }
    
    /**
     * Metodo para adicionar ejercitos
     */
    public void addArmy(Army _army)
    {
        //nArmies ++;
        this.armies.add(_army);
        this.haveArmies = true;
    }
    
    /**
     * Metodo para eliminar ejercitos
     */
    public abstract void delArmy(ArrayList<Army> _armies);
    /*
    public void delArmy(ArrayList<Army> _armies)//Army _army)
    {
        //nArmies --;
        //_army.delArmy(this.armies);
        //this.armies.remove(_army);
        for(Army a: armies) // recorre las armies de la nación
            a.delArmy(_armies); // elmiina la army "a" de las naciones que esten en mundo "_armies"
        _armies.removeAll(armies); // remueve todas las naciones en el mundo q tenga esta nación 
    }*/
    
    /**
     * Metodo que me indica si la nación cuenta con armies
     */
    public boolean getHaveArmies()
    {
        return this.haveArmies;
    }
    
    /**
     * Metodo para obtener el número de armies
     */
    public int getnArmies()
    {
        return this.nArmies;
    }
    
    /**
     * Metodo para obtener el número de armies según el tipo de nación 
     */
    public abstract int getnArmiesType();
    
    /**
     * Setea el valor al número de armies
     */
    public abstract void setnArmies(int _newArmies);
    /*
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
    }*/
    
    /**
     * Metodo para obtener el centro en la coordenada X de una nacion
     */
    public int getCenX()
    {
        return (this.xPosition + (height/2)) +10;
    }
    
    /**
     * Metodo para obtener el centro en la coordenada Y de una nacion
     */
    public int getCenY()
    {
        return (this.yPosition + (width/2)) +50;
    }
    
    /**
     * Metodo para eliminar una nación
     * @param _routes: ArrayList<Route>
     */
    public void delNation(ArrayList<Route> _routes, ArrayList<Army> _armies)
    {
        this.makeInvisible();
        for(Route i: properRoutes) 
            i.delRoute(_routes);
        _routes.removeAll(properRoutes);
        for(Army a: armies)
            a.delArmy(_armies);
        _armies.removeAll(armies);   
    }
    
    /**
     * Metodo para obtener una posicion en Y
     * @param yPosition: int,
     */
     private int getPositionY(int yPosition)
     {
        int total = world.getHeight();
        if (yPosition == 0) 
            return total - yPosition + world.getYPosition() - height;
            
        return total - yPosition + world.getYPosition();
    }
    
    /**
     * Metodo para obtener una posición en X
     * @param xPosition: int,
     */
    private int getPositionX(int xPosition){
        int total = world.getXPosition();
        if (xPosition == 0) 
            return total + xPosition;
            
        return total + xPosition-width;
    }
}
