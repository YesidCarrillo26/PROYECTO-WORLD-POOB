package world;

import java.util.ArrayList;
import java.lang.Math;
import shapes.*;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
/**
 * Clase de mundo que contiene todos los elementos necesarios 
 * para conquistar el mundo
 * @authors Yesid Carrillo, Juan David Parroquiano 
 * @version 4.0 (Noviembre 1, 2021)
 */
public class World
{
    Rectangle world ;
    Rectangle world2 ;
    public ArrayList<Nation> nations = new ArrayList<Nation>();
    private ArrayList<Nation> deletedNations = new ArrayList<Nation>();
    private ArrayList<Nation> putArmyNation = new ArrayList<Nation>();
    private ArrayList<Route> routes = new ArrayList<Route>();
    private ArrayList<Route> deletedStreet = new  ArrayList<Route>();
    private ArrayList<Army> armies = new ArrayList<Army>();
    private ArrayList<Nation> deletedArmiesNations = new ArrayList<Nation>();
    long matrizCost[][] = new long[6][6];
    private boolean isVisible = false;
    private boolean complete;
    private int costs;
    //private int nNations = 1;
    private Army army;
    //private boolean wasVisible = false;
    //private boolean wasInvisible = false;
    private String cache;
    private int totalCost = 0;
    private boolean haveArmy = false;
    private int undoArmiesA ;
    private int undoArmiesB ;
    private boolean undo = false;
    private boolean redo = false;
    
    
    // ------------------------MiniCiclo 1-----------------------------------
    /**
     * Constructor de mundo
     * @param _length: int, valor del largo del mundo
     * @param _width: int, valor del ancho del mundo
     */
    public World(int _length, int _width)
    {
        world = new Rectangle();
        world.changeColor("black");
        //world.changeSize(_length, _width);
        world.changeSize(320, 470);
        world2 = new Rectangle();
        world2.changeColor("white");
        //world2.changeSize(_length-10, _width-10);
        world2.changeSize(310, 460);
        world2.moveHorizontal(5);
        world2.moveVertical(5);
        makeVisible();
        /*if(_length > 500 || _width > 500)
        {
            complete = false ;
        }
        else
        {
            complete = true;
        }*/
        
        
    }
    
    /**
     * Create the canvas of the world with
     * specific nations, routes and armies
     * @param _nations: int, numero de naciones
     * @param _routes[][]: int, ruta que conecta la nacion a con la b y su costo al pasar las armies
     * @param _armies[][]: int, el numero de armies actuales, el numero de armies necesarias para conquistar 
     */
    public World(int _nations , int _routes[][], int _armies[][])
    {
        world = new Rectangle();
        world.changeColor("black");
        world.changeSize(320, 470);
        world2 = new Rectangle();
        world2.changeColor("white");
        world2.changeSize(310, 460);
        world2.moveHorizontal(5);
        world2.moveVertical(5);
        ArrayList<Nation> nations2 = new ArrayList<Nation>();
        Integer colorRand =(int) (Math.random()*(5));
        int posX = 0;// (int) (Math.random()*(430));
        int posY = 0; //(int) (Math.random()*(300));
        for(int i=0; i<_nations; i++) // Creando naciones 
        { 
            posX = (int) (Math.random()*(410));
            posY = (int) (Math.random()*(280));
                                                 //_randColor,_currentArmies,_armiesToDominate                                                                                                                       
            nations2.add(new Normal(posX , posY, (Integer) i, _armies[i][0], _armies[i][1]));
            nations.add(new Normal(posX , posY, (Integer) i, _armies[i][0], _armies[i][1]));
        }
        int n=1;
        int pX ;
        int pY ;
        while( n < _nations)// Ubicando naciones en lugares que no se choquen con otras 
        {
            int x = nations2.get(n).getxPosition();
            int y = nations2.get(n).getyPosition();
            for(Nation na : nations2)
            {
                if(!nations2.get(n).equals(na))
                {
                    pX = 0;
                    pY = 0;
                    if((na.getxPosition() > x-30 && na.getxPosition() < x+30) ||
                       (na.getyPosition() > y-40 && na.getyPosition() < y+40))
                    {
                        pX = na.getxPosition() < 205 ? na.getxPosition()-40 : na.getxPosition()+40 ;
                        pY = na.getyPosition() < 155 ? na.getxPosition()+40 : na.getxPosition()-40 ;
                        na.setPosx(pX);
                        na.setPosy(pY);
                    }                    
                }
            }
            n ++;
        }
        //[[1,2,5],[3,1,5]]
        String natiA = null;
        String natiB = null;
        for(int a=0; a<= nations2.size()-2; a++) // Creando rutas entre naciones
        {
            natiA = nations2.get(_routes[a][0] -1).getColor();
            natiB = nations2.get(_routes[a][1] -1).getColor();
            // Crear ruta con naciones encontradas
            addRoute(natiA, natiB, _routes[a][2]); 
            natiA = null;
            natiB = null;
        }
        
        for(Nation i: nations2) // Añadiendo armies 
        {
            putArmy(i.getColor());
        }
        costs = 0;
        complete = true;
        
        for(int a=0; a<_nations; a++)
        {
            for(int b=0; b<_nations; b++)
            {
                if(a==b)
                {
                    matrizCost[a][b] = 0;
                }
                else
                {
                    matrizCost[a][b] = 999999999;
                }
            }
        }
        
        for(int i=0; i<_nations; i++)
        {
            for(int j=0; j<_nations; j++)
            {
                for(int k=0; k<_nations-1 ; k++)
                {
                    matrizCost[ _routes[k][0]-1][ _routes[k][1]-1 ] = _routes[k][2];
                    matrizCost[ _routes[k][1]-1][ _routes[k][0]-1 ] = _routes[k][2];
                }
            }
        }
        ArrayList<ArrayList> maAux = new ArrayList<ArrayList>();// Matrix auxiliar para ver matriz de costos
         for(int x=0; x<_nations; x++)
        {        
            ArrayList<Integer> co = new ArrayList<Integer>();
            for(int y=0; y<_nations; y++)
            {
                //co.add(matrizCost[x][y]);
                //System.out.println(((Object)matrizCost[x][y]).getClass().getSimpleName());
            }
            maAux.add(co);
        }
        //System.out.println(maAux);
    }
    
    /**
     * Metodo que adiciona una nacion
     * @param _color: String, color de la nacion
     * @param _x: int, posicion en x de la nacion
     * @param _y: int, posicion en y de la nacion
     * @param _armies: int, numero de ejercitos que tiene una nacion
     */
    public void addNation(String _color, int _x, int _y, int _armies)
    {
        // si la nación no esta en naciones --> crea nación 
        if(!nations.contains( searchNation(_color)) ) 
        {
            Nation nation =  new Normal(_color,_x,_y,_armies);
            nations.add(nation);
            complete = true; 
        }
        // si si esta --> no hace nada 
        else
        {
            complete = false;
        }
        makeVisible();
        cache = "addNation";
    }
    
    /**
     * Metodo que adiciona una nacion según el tipo 
     * @param type: tipo de la nación que se quiere crear 
     * @param _color: String, color de la nacion
     * @param _x: int, posicion en x de la nacion
     * @param _y: int, posicion en y de la nacion
     * @param _armies: int, numero de ejercitos que tiene una nacion
     */
    public void addNation(String type, String _color, int _x, int _y, int _armies)
    {
        // si la nación no esta en naciones --> crea nación 
        if(!nations.contains( searchNation(_color)) ) 
        {
            Nation nation = null;
            if(type.equals("normal"))
            {
                nation =  new Normal(_color,_x,_y,_armies);
            }
            else if(type.equals("walled"))
            {
                nation =  new Walled(_color,_x,_y,_armies);
            }
            else if(type.equals("aggressive"))
            {
                nation =  new Aggressive(_color,_x,_y,_armies);
            }
            if(nation == null)
            {
                complete = false; 
            }
            else
            {
                nations.add(nation);
                complete = true; 
                makeVisible();
            }
            
        }
        // si si esta --> no hace nada 
        else
        {
            complete = false;
        }
        
        cache = "addNation";
    }
    
    /**
     * Metodo que adiciona una ruta
     * @param _locationA: String, color de la nacion 1
     * @param _locationB: String, color de la nacion 2
     * @param _cost: int, costo de utilizar una ruta
     */
    public void addRoute(String _locationA, String _locationB,int _cost)
    {
        if(!routes.contains(searchRoute(_locationA, _locationB)))
        {
            Nation nation1 = searchNation(_locationA);
            Nation nation2 = searchNation(_locationB);
            Route route = new NormalRoute(nation1,nation2,_cost);
            nation1.addRoute(route,routes,nation2);
            complete = true;
        }
        else
        {
            complete = false;
        }  
        cache = "addRoute";
    }   
    
    /**
     * Metodo que adiciona una ruta según su tipo
     * @param type: String tipo de ruta
     * @param _locationA: String, color de la nacion 1
     * @param _locationB: String, color de la nacion 2
     * @param _cost: int, costo de utilizar una ruta
     */
    public void addRoute(String type , String _locationA, String _locationB,int _cost)
    {
        if(!routes.contains(searchRoute(_locationA, _locationB)))
        {
            Nation nation1 = searchNation(_locationA);
            Nation nation2 = searchNation(_locationB);
            Route route = null ;
            if(type == "normal")
            {
                route =  new NormalRoute(nation1,nation2,_cost);
            }
            else if(type == "weak")
            {
                route =  new Weak(nation1,nation2,_cost);
            }
            else if(type == "dealer")
            {
                route =  new Dealer(nation1,nation2,_cost);
            }
            if(route != null)
            {
                nation1.addRoute(route,routes,nation2);
                complete = true;
            }
            else
            {
                complete = false;
            }
            
        }
        else
        {
            complete = false;
        }  
        cache = "addRoute";
    }
    
    /**
     * Metodo que elimina una nación
     * @param _color: String, color de la nacion a eliminar
     */

    public void delNation(String _color)
    {
        if(nations.contains( searchNation(_color) ))
        {
            deletedNations.add(searchNation(_color)); // almacena las naciones eliminadas 
            Nation nationDeleted = searchNation(_color);
            nationDeleted.delNation(routes, armies);
            nations.remove(nationDeleted);
            complete = true;
        }
        else
        {
            complete = false;
        }
        cache = "delNation";
    }
    
    /**
     * Metodo para eliminar una ruta
     * @param _locationA: String, color de la nacion 1
     * @param _locationB: String, color de la nacion 2
     */
    public void delStreet(String _locationA, String _locationB)
    {
        if(routes.contains( searchRoute(_locationA, _locationB) ))
        {
            deletedStreet.add( searchRoute(_locationA, _locationB) ); // antes de eliminar la ruta la almacena en un arreglo 
            Route deletedRoute = searchRoute(_locationA, _locationB);
            deletedRoute.delRoute(routes);
            complete = true;
        }
        else
        {
            complete = false;
        }
        cache = "delStreet";
    }
    
    /**
     * Metodo que pone un ejercito en una nación 
     * @param _location: String, 
     */
    public void putArmy(String _location)
    {
        if (nations.contains(searchNation(_location)))
        {
            putArmyNation.add(searchNation(_location));
            Army army = new NormalArmy(searchNation(_location).getnArmies(), searchNation(_location));
            armies.add(army);// añade armies al mundo !! \(°_°)/
            searchNation(_location).addArmy(army); //<--?
            complete = true;                
        }
        else
        {
            complete = false;            
        }
        cache = "putArmy";
    }
    
    /**
     * Metodo que pone un ejercito de un tipo específico en una nación
     * @param type: tipo de armies
     * @param _location: String, 
     */
    public void putArmy(String type, String _location)
    {
        if (nations.contains(searchNation(_location)))
        {
            putArmyNation.add(searchNation(_location));
            Army army = null;
            if(type == "normal")
            {
                army=  new NormalArmy(searchNation(_location).getnArmies(), searchNation(_location));
            }
            else if(type == "friendly")
            {
                army=  new Friendly(searchNation(_location).getnArmies(), searchNation(_location));
            }
            else if(type == "fearful")
            {
                army=  new Fearful(searchNation(_location).getnArmies(), searchNation(_location));
            }
            if(army != null)
            {
                armies.add(army);// añade armies al mundo !! \(°_°)/
                searchNation(_location).addArmy(army); //<--?
                complete = true;
            }
            else
            {
                complete = false;
            }
        }
        else
        {
            complete = false;            
        }
        cache = "putArmy";
    }
    
    /**
     * Metodo que elimina las armies de una Nacion específica
     * @param _location:String, nación a la cual se le eliminan sus armies 
     */
    public void removeArmy(String _location)
    {        
        if (nations.contains(searchNation(_location)))

        {               
            deletedArmiesNations.add(searchNation(_location));// antes de eliminar añade la nacion al arreglo de naicones a las que se les elimino sus armies
            searchNation(_location).delArmy(armies); //<--?
            complete = true;                
        }
        else
        {
            complete = false;
        } 
        cache = "removeArmy";
    }
    
    /**
     * Metodo que mueve las armies entre dos naciones
     * @param _locationA: String, nacion origen de armies
     * @param _locationB: String, nación destino de armies
     */
    public void moveArmyOneRoute(String _locationA, String _locationB)
    {
        undoArmiesA = searchNation(_locationA).getnArmies(); // almacena las armies originales
        undoArmiesB = searchNation(_locationB).getnArmies(); // almacena las armies originales
   
        if(searchNation(_locationA) != null && searchNation(_locationB) != null &&
            searchRoute(_locationA, _locationB) != null )
        {
            //int nArmies = searchNation(_locationA).getnArmies();
            //int newArmies = searchNation(_locationB).getnArmies() + nArmies;
            int nArmies = searchNation(_locationA).getnArmiesType();
            int newArmies = searchNation(_locationB).getnArmiesType() + nArmies;
            ArrayList<Army> armiesA = searchNation(_locationA).getArmies();
            if(armiesA.get(0) instanceof NormalArmy )
            {                
                if(searchRoute(_locationA, _locationB) instanceof NormalRoute)
                {
                    searchNation(_locationA).delArmy(armies); // elimina las armas de la nación sobre las armas del mundo
                    if(!(searchNation(_locationB) instanceof  Aggressive))
                    {
                        searchNation(_locationB).delArmy(armies);
                        searchNation(_locationB).setnArmies(newArmies);
                    }
                    this.totalCost += searchRoute(_locationA, _locationB).getCost();
                }
                else if(searchRoute(_locationA, _locationB) instanceof Weak)
                {
                    if( !(searchNation(_locationA) instanceof  Walled))
                    {
                        searchNation(_locationA).delArmy(armies);
                        delStreet(_locationA, _locationB);
                    }
                }
                else if(searchRoute(_locationA, _locationB) instanceof Dealer)
                {
                    searchRoute(_locationA, _locationB).setCost(2); // duplica el costo
                    searchNation(_locationA).delArmy(armies); 
                    if(!(searchNation(_locationB) instanceof  Aggressive))
                    {
                        searchNation(_locationB).delArmy(armies);
                        searchNation(_locationB).setnArmies(newArmies);
                    }
                    this.totalCost += searchRoute(_locationA, _locationB).getCost();
                }
                complete = true;
            }
            else if(armiesA.get(0) instanceof Friendly)
            {
                if(armiesA.size() <= 1)
                {
                    if(searchRoute(_locationA, _locationB) instanceof NormalRoute)
                    {
                        searchNation(_locationA).delArmy(armies); // elimina las armas de la nación sobre las armas del mundo
                        if(!(searchNation(_locationB) instanceof  Aggressive))
                        {
                            searchNation(_locationB).delArmy(armies);
                            searchNation(_locationB).setnArmies(newArmies);
                        }
                        this.totalCost += searchRoute(_locationA, _locationB).getCost();
                    }
                    else if(searchRoute(_locationA, _locationB) instanceof Weak)
                    {
                        if( !(searchNation(_locationA) instanceof  Walled))
                        {
                            searchNation(_locationA).delArmy(armies);
                            delStreet(_locationA, _locationB);
                        }
                    }
                    else if(searchRoute(_locationA, _locationB) instanceof Dealer)
                    {
                        searchRoute(_locationA, _locationB).setCost(2); // duplica el costo
                        searchNation(_locationA).delArmy(armies); 
                        if(!(searchNation(_locationB) instanceof  Aggressive))
                        {
                            searchNation(_locationB).delArmy(armies);
                            searchNation(_locationB).setnArmies(newArmies);
                        }
                        this.totalCost += searchRoute(_locationA, _locationB).getCost();
                    }
                }
                complete = true;
            }
            else if(armiesA.get(0) instanceof Fearful)
            {
                if(searchNation(_locationA).getnArmies() >= searchNation(_locationB).getnArmies())
                {
                    if(searchRoute(_locationA, _locationB) instanceof NormalRoute)
                    {
                        searchNation(_locationA).delArmy(armies); // elimina las armas de la nación sobre las armas del mundo
                        if(!(searchNation(_locationB) instanceof  Aggressive))
                        {
                            searchNation(_locationB).delArmy(armies);
                            searchNation(_locationB).setnArmies(newArmies);
                        }
                        this.totalCost += searchRoute(_locationA, _locationB).getCost();
                    }
                    else if(searchRoute(_locationA, _locationB) instanceof Weak)
                    {
                        if( !(searchNation(_locationA) instanceof  Walled))
                        {
                            searchNation(_locationA).delArmy(armies);
                            delStreet(_locationA, _locationB);
                        }
                    }
                    else if(searchRoute(_locationA, _locationB) instanceof Dealer)
                    {
                        searchRoute(_locationA, _locationB).setCost(2); // duplica el costo
                        searchNation(_locationA).delArmy(armies); 
                        if(!(searchNation(_locationB) instanceof  Aggressive))
                        {
                            searchNation(_locationB).delArmy(armies);
                            searchNation(_locationB).setnArmies(newArmies);
                        }
                        this.totalCost += searchRoute(_locationA, _locationB).getCost();
                    }
                }
                complete = true;
            }
            else
            {
                complete = false;
            }
            //this.totalCost += searchRoute(_locationA, _locationB).getCost();
        }      
        else
        {
            complete = false;
        }
        cache = "moveArmyOneRoute";
    }
    
    /**
     * Metodo que desplaza las armies de una nacion A a una nacion B
     * @param Nacion inicial _nationA
     * @param Nacion final _nationB
     */
    public void moveArmy(String _nationA, String _nationB)
    {
        this.totalCost = 0;
        CaminosMinimos camino = new CaminosMinimos();
        ArrayList<Integer> elCamino = new ArrayList<Integer>();
        try
        {
            int from = searchNation(_nationA).getNumberOfColor(_nationA);
            int to = searchNation(_nationB).getNumberOfColor(_nationB);
        
            elCamino = camino.algoritmoFloyd(matrizCost, from+1, to+1); // Arreglo de naciones por donde pasar 
            System.out.println(elCamino);
            int c = 0;
            while( c < elCamino.size()-1 )
            {
                moveArmyOneRoute(getColorOfNumber(elCamino.get(c)-1), getColorOfNumber(elCamino.get(c+1)-1));
                
                c++;
            }
            complete = _nationA == _nationB ? false : true; 
        }
        catch (Exception e)
        {                   
            complete = false;
            Registro registro = new Registro();
            registro.registre(e);
            JOptionPane.showMessageDialog(null,
                                             "Ha ocurrido un error",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metodo que indica si es posible conquistar una nacion
     * complete = true --> es posible 
     * complete = false --> no es posible
     * @param String : nacion a intentar conquistar 
     */
    public void tryToConquer(String _nation)
    {
        complete = false;
        if( searchNation(_nation).getArmiesToDominate() != 0) // significa que no necesita armies para Conquistar
        {        
            for(int i=0; i<nations.size(); i++)
            {
                moveArmy(nations.get(i+1).getColor(), _nation);
                
                if( searchNation(_nation).getnArmies() >= searchNation(_nation).getArmiesToDominate())
                {
                    complete = true;
                    break;
                }
            }
        }
    }
    
    /**
     * Retorna las naciones que estén conquistadas
     * @return String[], lista de naciones conquistadas
     */
    public String[] conqueredNations()
    {
        String[] naciones = new String[nations.size()];
        boolean have = false;
        for(int i=0 ; i<nations.size(); i++)
        {
            if(nations.get(i).isConquered()) // busca naciones q esten conquistadas 
            {
                naciones[i] = nations.get(i).getColor();
                have = true;
            }
        }        
        complete = have ? true : false;
        
        return naciones;
    }
    
    /**
     * Metodo que retorna si todas las naciones están conquistadas lo que indica que 
     * el mundo se ha conquistado 
     */
    public boolean conquer()
    {
        boolean ret = false;
        complete = false;
        int conquiNations = 0;
        for(Nation n: nations)
        {
            if(n.isConquered())
            {
                conquiNations += 1;
            }
        }
        if(conquiNations == this.nations.size())
        {
            ret = true;
            complete = true;
        }
        return ret ;
    }
    
    /**
     * Retorna el monto total de las rutas que se usaron 
     * @return totalCost : int , monto
     */
    public int payments()
    {
        complete = this.totalCost >= 0 ? true : false;
        return this.totalCost;
    }
    
    //----------------------MiniCiclo 3----------------------------
     /**
     * Metodo para hacer visible el mundo y sus elementos
     */
    public void makeVisible()
    {
        if(!isVisible)
        {
            isVisible = true;
            world.makeVisibles();
            world2.makeVisibles();
            for(Nation n: nations)
                n.makeVisible();
            for(Route r: routes)
                r.makeVisible();        
            for(Army a: armies)
                a.makeVisible();
            complete = true;
        }
        else
        {
            complete = false ;
        }
        cache = "makeVisible";
    }
    
    /**
     * Metodo para hacer invisible el mundo y sus elementos
     */
    public void makeInvisible()
    {
        if(isVisible)
        {
            world.makeInvisibles();
            for(Nation n: nations)
                n.makeInvisible();
            for(Route r: routes)
                r.makeInvisible();
            for(Army a: armies)
                a.makeInvisible();
            isVisible = false;
            complete = true;
        }
        else
        {
            complete = false;
        }
        cache = "makeInvisible";
    }
    
    /**
     * Metodo para finalizar con el programa
     */
    public void finish()
    {
        complete = true;
        System.exit(0);
    }
    
    /**
     * Metodo que retorna si la ultima accion se pudo realizar o no
     */
    public boolean ok()
    {
        return complete;
    }
    
    public void undo()
    {
        if(cache == "addNation")
        {
            this.delNation(nations.get(nations.size()-1).getColor());
            //this.putArmy(nations.get(nations.size()-1).getColor());
        }
        else if (cache == "addRoute")
        {
            this.delStreet(routes.get(routes.size()-1).getNation1(), routes.get(routes.size()-1).getNation2());
        }
        else if(cache == "delNation" )
        {
            this.addNation( deletedNations.get(deletedNations.size()-1).getColor(),
                            deletedNations.get(deletedNations.size()-1).getxPosition(),
                            deletedNations.get(deletedNations.size()-1).getyPosition(),
                            deletedNations.get(deletedNations.size()-1).getnArmies() );
            if(deletedNations.get(deletedNations.size()-1).getHaveArmies())
            {
                this.putArmy(deletedNations.get(deletedNations.size()-1).getColor());
            }
            if(deletedNations.get(deletedNations.size()-1).getHaveRoutes())
            {
                this.addRoute( deletedStreet.get(deletedStreet.size()-1).getNation1(),
                            deletedStreet.get(deletedStreet.size()-1).getNation2(), 
                            deletedStreet.get(deletedStreet.size()-1).getCost()   );
            }
            cache = "addNation";
        }
        else if(cache == "delStreet")
        {
            this.addRoute( deletedStreet.get(deletedStreet.size()-1).getNation1(),
                            deletedStreet.get(deletedStreet.size()-1).getNation2(), 
                            deletedStreet.get(deletedStreet.size()-1).getCost()   );
        }
        else if(cache == "putArmy")
        {
            this.removeArmy(putArmyNation.get(putArmyNation.size()-1).getColor());
        }
        else if(cache == "removeArmy" )
        {
            this.putArmy(deletedArmiesNations.get(deletedArmiesNations.size()-1).getColor());
        }
        else if(cache == "makeInvisible")
        {
            this.makeVisible();
        }
        else if(cache == "makeVisible")
        {
            this.makeInvisible();
        }
        
        undo = true;
    }
    
    public void redo()
    {
        if(undo)
        {
            if(cache == "addNation")
            {
                this.delNation(nations.get(nations.size()-1).getColor());
            }
            else if (cache == "addRoute")
            {
                this.delStreet(routes.get(routes.size()-1).getNation1(), routes.get(routes.size()-1).getNation2());
            }
            else if(cache == "delNation" )
            {
                this.addNation( deletedNations.get(deletedNations.size()-1).getColor(),
                                deletedNations.get(deletedNations.size()-1).getxPosition(),
                                deletedNations.get(deletedNations.size()-1).getyPosition(),
                                deletedNations.get(deletedNations.size()-1).getnArmies() );
            }
            else if(cache == "delStreet")
            {
                this.addRoute( deletedStreet.get(deletedStreet.size()-1).getNation1(),
                                deletedStreet.get(deletedStreet.size()-1).getNation2(), 
                                deletedStreet.get(deletedStreet.size()-1).getCost()   );
            }
            else if(cache == "putArmy")
            {
                this.removeArmy(putArmyNation.get(putArmyNation.size()-1).getColor());
            }
            else if(cache == "removeArmy" )
            {
                this.putArmy(deletedArmiesNations.get(deletedArmiesNations.size()-1).getColor());
            }
            else if(cache == "makeInvisible")
            {
                this.makeVisible();
            }
            else if(cache == "makeVisible")
            {
                this.makeInvisible();
            }
            complete = true ;
        }
        else
        {
            complete = false;
        }
    }
    
    // METODOS AUXILIARES
    
    /**
     * Metodo para buscar si existe o no una nacion
     * @param _color: String,
     */
    private Nation searchNation(String _color)
    {
        for (Nation i: nations)
        {
            if (i.getColor().equals(_color)) 
                return i;
        }
        return null;
    }
    
    private Nation searchNation(int _nNation)
    {
        for (Nation i: nations){
            if (i.getnNation() == _nNation) return i;
        }
        return null;
    }
    
    /**
     * Metodo para buscar si una ruta existe o no
     * @param _locationA: String, color de la nacion 1
     * @param _locationB: String, color de la nacion 2
     */
    private Route searchRoute(String _locationA, String _locationB)
    {
        for (Route i: routes)
        {
            if (i.searchRoute(_locationA, _locationB))
                return i;
        }
        return null;
    }
    
    /**
     * Metodo que retorna el color a partir de una número específico 
     * @param _color de cual se quiere conocer su némuro entero 
     * @return número entero
     */
    private String getColorOfNumber(Integer _int)
    {
        String ret = null;
        if(_int == 0)
        {
            ret = "yellow";
        }
        else if (_int == 1)
        {
            ret = "blue";
        }
        else if (_int == 2)
        {
            ret = "red";
        }
        else if (_int == 3)
        {
            ret = "green";
        }
        else if (_int == 4)
        {
            ret = "gray";
        }
        else if (_int == 5)
        {
            ret = "magenta";
        }
        return ret ;
    }
}

/** Retrospectiva Ciclo # 1
 * 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.
 *    Los mini-ciclos elegidos fueron:
 *    1. constructor World, addNation, addRoute, putArmy, delNation,
 *    delStreet, removeArmy
 *    2. removeArmyOneRoute, conqueredNations, payments, conquer
 *    3. makeInvisible(), makeVisible(), finish(), ok()
 *    
 * 2. ¿Cuál es el estado actual del laboratorio en términos de mini-ciclos? ¿por qué?
 * 
 *    En el estado actual del laboatorio logramos hacer la mayor parte del ciclo 1, el ciclo 2 no
 *    logramos implementarlo y el ciclo 3 logramos hacer la mayor parte del ciclo
 *    
 * 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)
 * 
 *    El tiempo total invertido para la entrega del primer ciclo de laboratorio es:
 *    Yesid Carrillo 15 Horas
 *    Juan David Parroquiano 15 Horas
 *    
 * 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?
 * 
 *    El mayor logro es hacer de una manera interactiva la interfaz para así lograr
 *    entender el problema de una mejor manera, entendiendo bien graficamente lo que está sucediendo 
 *    podemos codificar de una buena forma probando y resolviendo lo que se quiere.
 *    
 * 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?
 * 
 *    El mayor problema técnico para nosotros fue la utilizacion de una manera correcta Git, 
 *    pues hemos tenido algunos incovenientes al utilizar las herramientas que a su vez nos 
 *    provee tanto git como java para representar la situacion de manera grafica y resolver el problema 
 * 
 * 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?
 * 
 *     Cumplir con pequeñas reuniones diarias establecidas para avanzar con el proyecto. 
 *    
 * 7. Considerando las prácticas XP del laboratorio. ¿cuál fue la más útil? ¿por qué? 
 * 
 *    La practica más util fue la de Pair Programing ya que nos entendimos y nos complementamos
 *    muy bien  a la hora de la codificacion
 *    
 */


/** Retrospectiva Ciclo # 2
 * 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.
 *    Los mini-ciclos elegidos fueron:
 *    1. constructor World #2, putArmy, removeArmy, removeArmyOneRoute, conqueredNations
 *    2. constructor Nation #3, putData, searchCollisions, claseArmy, claseData
 *    3. pruebas
 *    
 * 2. ¿Cuál es el estado actual del laboratorio en términos de mini-ciclos? ¿por qué?
 * 
 *    En el estado actual del laboatorio logramos hacer la mayor parte del ciclo 1 y 2 
 *    aunque faltaron hacer algunas preubas del mismo y el ciclo 3 logramos pruebas 
 *    para crear el mundo, la nacion y la ruta
 *    
 * 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)
 * 
 *    El tiempo total invertido para la entrega del primer ciclo de laboratorio es:
 *    Yesid Carrillo 14 Horas
 *    Juan David Parroquiano 14 Horas
 *    
 * 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?
 * 
 *    El mayor logro es hacer de una manera interactiva la interfaz para así lograr
 *    entender el problema de una mejor manera, entendiendo bien graficamente lo que está sucediendo 
 *    podemos codificar de una buena forma probando y resolviendo lo que se quiere.
 *    
 * 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?
 * 
 *    El mayor problema técnico para nosotros fue la correcta planificacion de los tiempos para
 *    poder entregar el proyecto en las mejores condiciones posibles
 * 
 * 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?
 * 
 *     Tener una buena comunicacion en terminos de dar ideas y plasmarlas en codigo y 
 *     nos comprometemos a planificar mejor los espacios para desarrollar el proyecto de una mejor manera 
 *    
 * 7. Considerando las prácticas XP del laboratorio. ¿cuál fue la más útil? ¿por qué? 
 * 
 *    La practica más util fue la de Pair Programing ya que nos entendimos y nos complementamos
 *    muy bien  a la hora de la codificacion
 *    
 */