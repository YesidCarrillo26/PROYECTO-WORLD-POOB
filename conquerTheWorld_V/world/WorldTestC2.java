package world;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class WorldTestC2.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class WorldTestC2
{
    private World w1, w2, w3, w4, w5;
    private Nation n1, n2, n3, n4 ;
    private Route r1, r2, r3;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        w1 = new World(100,100);
        w1.addNation("yellow",20,20, 10);
        w1.addNation("blue",30,50,2);
        w1.addNation("magenta",30,50,2);
        w2 = new World(50,70);
        w2.addNation("yellow",20,20, 10);
        w2.addNation("blue",59,50, 1);
        w3 = new World(50,200);
        makeInvisible();
        
    }
    
    public void makeInvisible()
    {
        w1.makeVisible();
        w1.makeInvisible();
        w2.makeVisible();
        w2.makeInvisible();
        /*w3.makeVisible();
        w3.makeInvisible();
        w4.makeVisible();
        w4.makeInvisible();*/
        
        /*n1.makeInvisible();
        n2.makeInvisible();
        n3.makeInvisible();
        n4.makeInvisible();
        r1.makeInvisible();
        r2.makeInvisible();
        r3.makeInvisible();*/
    }
    
    @Test
    public void segunCPdeberiaCrearElWorld() 
    {       
        assertTrue(w1.ok());
    }
    
    @Test
    public void segunCPDeberiaCrearNacion()
    {
        assertTrue(w1.ok());
        makeInvisible();
    }   
    @Test
    public void segunCPNoDeberiaCrearNacion()
    {
        w1.addNation("yellow",20,20, 10);
        w1.addNation("yellow",30,50,2);
        assertFalse(w1.ok());
        makeInvisible();
    } 
    
    @Test
    public void segunCPDeberiaCrearRuta()
    {
        //w1.addNation("yellow",20,20, 10);
        //w1.addNation("blue",30,50,2);
        w1.addRoute("yellow", "blue", 3);
        assertTrue(w1.ok());
    }  
    
    @Test
    public void segunCPNoDeberiaCrearRuta()
    {
        /*w1.addNation("yellow",20,20, 10);
        w1.addNation("red",59,50, 1);
        w1.addRoute("yellow", "red", 3);
        w1.addRoute("yellow", "red", 4);
        */
        w1.addRoute("yellow", "blue", 3);
        w1.addRoute("yellow", "blue", 4);
        assertFalse(w1.ok());
        makeInvisible();
    }
    /*
    @Test
    public void segunCPdeberiaConquistar()
    {
        //w2.addNation("yellow",20,20, 10);
        //w2.addNation("blue",59,50, 1);
        w2.nations.get(0).sumArmies(5);
        w2.nations.get(1).sumArmies(5);
        w2.conquer();
        assertTrue(w2.ok());
        makeInvisible();
    }
    
    @Test
    public void segunCPNodeberiaConquistar()
    {
        //w2.addNation("yellow",20,20, 10);
        //w2.addNation("blue",59,50, 1);
        w2.nations.get(0).sumArmies(-1);
        w2.nations.get(1).sumArmies(-2);
        w2.conquer();
        assertFalse(w2.ok());
        makeInvisible();
    }
    
    @Test
    public void segunCPDeberiaRetornarNacionasConquistadas()
    {
        //w2.addNation("yellow",20,20, 10);
        //w2.addNation("blue",59,50, 1);
        w2.nations.get(0).sumArmies(0);
        w2.nations.get(1).sumArmies(0);
        w2.conqueredNations();
        assertTrue(w2.ok());
        makeInvisible();
    }
    
    @Test
    public void segunCPNoDeberiaRetornarNacionasConquistadas()
    {
        //w2.addNation("yellow",20,20, 10);
        //w2.addNation("blue",59,50, 1);
        w2.nations.get(0).sumArmies(-1);
        w2.nations.get(1).sumArmies(-2);
        w2.conqueredNations();
        assertFalse(w2.ok());
        makeInvisible();
    }
    
    @Test
    public void segunCPdeberiaEliminarNacion()
    {
        //w2.addNation("yellow",20,20, 10);
        w2.delNation("yellow");
        assertTrue(w2.ok());
        makeInvisible();
    }
    
    @Test
    public void segunCPNoDeberiaEliminarNacion()
    {
        //w2.addNation("yellow",20,20, 10);
        w2.delNation("red");
        assertFalse(w2.ok());
        makeInvisible();
    }
    
    @Test 
    public void segunCPdeberiaEliminarRoute()
    {
        w1.addRoute("yellow", "blue", 4);
        w1.delStreet("yellow", "blue");
        assertTrue(w1.ok());
    }
    
    @Test 
    public void segunCPNoDdeberiaEliminarRoute()
    {
        //w1.addRoute("yellow", "blue", 4);
        w1.delStreet("yellow", "blue");
        assertFalse(w1.ok());
    }
    
    
    @Test
    public void segunCPdeberiaHacerVisible()
    {
        w1.makeVisible();
        assertTrue(w1.ok());
    }
    @Test
    public void segunCPNoDeberiaHacerVisible()
    {
        w1.makeVisible();
        w1.makeVisible();
        assertFalse(w1.ok());
    }
    
    @Test
    public void segunCPdeberiaHacerinVisible()
    {
        w3.makeInvisible();
        assertTrue(w3.ok());
    }
    @Test
    public void segunCPNoDeberiaHacerinVisible()
    {
        w3.makeInvisible();
        w3.makeInvisible();
        assertFalse(w3.ok());
    }
    
    @Test
    public void segunCPdeberiaMoverLasArmiesDeLaRuta()
    {
        w1.addRoute("yellow", "blue", 4);
        w1.moveArmyOneRoute("yellow", "blue");
        assertTrue(w1.ok());
    }
    
    @Test
    public void segunCPnoDeberiaMoverLasArmiesDeLaRuta()
    {
        w1.moveArmyOneRoute("yellow", "blue");
        assertFalse(w1.ok());
    }
    
    @Test
    public void segunCPdeberiaRetornarPagos()
    {
        w1.payments();
        assertTrue(w1.ok());
    }
    
    @Test
    public void segunCPnoDeberiaRetornarPagos()
    {
        w1.addRoute("yellow", "blue", -50);
        w1.moveArmyOneRoute("yellow", "blue");
        w1.payments();
        assertFalse(w1.ok());
    }
    
    @Test
    public void segunCPdeberiaPonerArmies()
    {
        w1.putArmy("yellow");
        assertTrue(w1.ok());
    }
    @Test
    public void segunCPnoDeberiaPonerArmies()
    {
        w1.putArmy("red");
        assertFalse(w1.ok());
    }
    
    @Test
    public void segunCPdeberiaRemoverArmies()
    {
        w1.putArmy("yellow");
        w1.removeArmy("yellow");
        assertTrue(w1.ok());
    }
    @Test
    public void segunCPnoDeberiaRemoverArmies()
    {
        w1.removeArmy("red");
        assertFalse(w1.ok());
    }
    
    @Test
    public void segunCPdeberiaHacerRedo()
    {
        w1.undo();
        w1.redo();
        assertTrue(w1.ok());
    }
    @Test
    public void segunCPnoDeberiaHacerRedo()
    {
        w1.redo();
        assertFalse(w1.ok());
    }*/
    
    @Test
    public void segunCPdeberiaMoverLasArmiesPorElMejorCamino()
    {
        w1.moveArmy("yellow","blue");
        assertTrue(w1.ok());
    }
    @Test
    public void segunCPnoDeberiaMoverLasArmiesPorElMejorCamino()
    {
        try
        {
            w1.moveArmy("yellow","yellow");
            assertFalse(w1.ok());
        }
        catch (Exception e)
        {
            assertFalse(w1.ok());
        }
    }
    
    @Test
    public void segunCPdeberiaIntentarConquistarConquistarNacion()
    {
        w1.tryToConquer("magenta"); // azul no tiene armies para conquistar 
        assertTrue(!w1.ok());
    }
    @Test
    public void segunCPnoDeberiaIntentarConquistarConquistarNacion()
    {
        w1.tryToConquer("blue"); // azul no tiene armies para conquistar 
        assertFalse(w1.ok());
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
