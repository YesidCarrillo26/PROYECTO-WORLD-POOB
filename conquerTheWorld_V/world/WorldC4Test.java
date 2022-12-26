package world;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class WorldC4Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class WorldC4Test
{
    private World w1, w2, w3, w4, w5;
    private Nation n1, n2, n3, n4 ;
    private Route r1, r2, r3;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        w1 = new World(100,100);
        w1.addNation("normal","yellow",20,20, 10);
        //w1.addNation("walled","blue",30,50,2);
        //w1.addNation("aggressive","magenta",30,50,2);
        makeInvisible();
    }
    
    public void makeInvisible()
    {
        w1.makeVisible();
        w1.makeInvisible();
    }
    
    @Test
    public void deberiaAdicionarNacion()
    {
        w1.addNation("walled","blue",302,100,3);
        assertTrue(w1.ok());
    }
    
    @Test
    public void noDeberiaAdicionarNacion()
    {
        w1.addNation("equis","green",320,110,3);
        assertFalse(w1.ok());
    }
    
    @Test
    public void segunCPdeberiaEliminarNacion()
    {
        //w2.addNation("yellow",20,20, 10);
        w1.delNation("yellow");
        assertTrue(w1.ok());
        //makeInvisible();
    }
    
    @Test
    public void segunCPNoDeberiaEliminarNacion()
    {
        w1.delNation("black");
        assertFalse(w1.ok());
        //makeInvisible();
    }
    
    @Test
    public void segunCPDeberiaAdicionarRoute()
    {
        w1.addNation("yellow",20,20, 10);
        w1.addNation("red",100,100, 10);
        w1.addRoute("weak","yellow","red",5);
        assertTrue(w1.ok());
    }
    
    @Test
    public void segunCPNoDeberiaAdicionarRoute()
    {
        w1.addNation("yellow",20,20, 10);
        w1.addNation("red",100,100, 10);
        w1.addRoute("weak","blue","red",5);
        assertFalse(w1.ok());
    }
    
    @Test
    public void segunCPDeberiaCrearRutaNormal()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.addRoute("normal", "yellow", "blue", 2);
        assertTrue(w1.ok());
        //makeInvisible();
    }
    
    @Test
    public void segunCPnoDeberiaCrearRutaNormal()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.addRoute("normall", "yellow", "blue", 2);
        assertFalse(w1.ok());
        //makeInvisible();
    }
    
    @Test
    public void segunCPDeberiaCrearRutaWeak()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.addRoute("weak", "yellow", "blue", 2);
        assertTrue(w1.ok());
        //makeInvisible();
    }
    
    @Test
    public void segunCPnoDeberiaCrearRutaWeak()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.addRoute("Weeak", "yellow", "blue", 2);
        assertFalse(w1.ok());
        //makeInvisible();
    }
    
    @Test
    public void segunCPDeberiaCrearRutaDealer()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.addRoute("dealer", "yellow", "blue", 2);
        assertTrue(w1.ok());
        //makeInvisible();
    }
    
    @Test
    public void segunCPNoDeberiaCrearRutaDealer()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.addRoute("dealerr", "yellow", "blue", 2);
        assertFalse(w1.ok());
        //makeInvisible();
    }
    
    @Test
    public void segunCPDeberiaCrearArmiesNormal()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.putArmy("normal","blue");
        assertTrue(w1.ok());
    }
    @Test
    public void segunCPnoDeberiaCrearArmiesNormal()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.putArmy("normalll","blue");
        assertFalse(w1.ok());
    }
    
    @Test
    public void segunCPDeberiaCrearArmiesFriendly()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.putArmy("friendly","blue");
        assertTrue(w1.ok());
    }
    @Test
    public void segunCPnoDeberiaCrearArmiesFriendly()
    {
        w1.addNation("normal","blue",20,20, 10);
        w1.putArmy("friendlyy","blue");
        assertFalse(w1.ok());
        //makeInvisible();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
