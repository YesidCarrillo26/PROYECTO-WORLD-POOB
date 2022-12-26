package world;

import java.lang.Math.*;
import java.util.ArrayList;

/**
 * Clase Grafo encargada de encontrar el camino minimo.
 * 
 * @version 1.0
 */
public class CaminosMinimos
{
     //public String algoritmoFloyd(long[][] mAdy)
     /**
      * Metodo que por algoritmoFloyd haya el camino minimo
      * @param mAdy: long[][]
      * @param _from: int, desde donde parte el rango
      * @param _to: int, hasta donde llega el rango
      */
     public ArrayList<Integer> algoritmoFloyd(long[][] mAdy, int _from, int _to)
     {
         int vertices = mAdy.length;
         long matrizAdyacencia[][] = mAdy;
         String caminos[][] = new String[vertices][vertices];
         String caminosAuxiliares[][] = new String[vertices][vertices];
         String caminoRecorrido="", cadena = "", caminitos = "";
         ArrayList<Integer> caminitos2 = new ArrayList<Integer>();
         float temporal1, temporal2, temporal3, temporal4, minimo;
         int i,j,k;
         int from = _from;
         int to = _to;
         // Inicializando las matrices Caminos y CaminosAuxiliares
         
         for(i=0; i<vertices ; i++)
         {
             for(j=0; j<vertices; j++)
             {
                 caminos[i][j] = "";
                 caminosAuxiliares[i][j] = "";
             }
         }
         
         for (k=0; k<vertices; k++)
         {
             for(i =0; i<vertices; i ++)
             {
                 for(j=0; j<vertices; j++)
                 {
                     temporal1 = matrizAdyacencia[i][j];
                     temporal2 = matrizAdyacencia[i][k];
                     temporal3 = matrizAdyacencia[k][j];
                     temporal4 = temporal2 + temporal3;
                     // Encontrando el mínimo 
                     minimo = Math.min(temporal1,temporal4);
                     if(temporal1 != temporal4)
                     {
                         if(minimo == temporal4)
                         {
                             caminoRecorrido = "";
                             caminosAuxiliares[i][j] = k + "";
                             //System.out.println(caminosAuxiliares[i][j]);
                             caminos[i][j] = caminosR( i, k, caminosAuxiliares, caminoRecorrido) + (k+1);
                             //System.out.println(caminos[i][j]);
                             //System.out.println(((Object)Integer.parseInt(caminos[i][j])).getClass().getSimpleName());                             
                             if(Integer.parseInt(caminos[i][j]) < 9 && !caminitos2.contains(Integer.parseInt(caminos[i][j])))
                             {
                                 //caminitos2.add(Integer.parseInt(caminos[i][j]));
                             }
                         }
                     }
                     matrizAdyacencia[i][j] = (long) minimo;
                 }
             }
         }
         //System.out.println(caminitos2);
         // Agregando el camino a cadena
         for(i=0; i<vertices ; i++)
         {
             for(j=0; j<vertices; j++)
             {
                 cadena += "["+matrizAdyacencia[i][j]+"]";
             }
             cadena = cadena + "\n";
         }
         ///////////////////////////////////////
         for(i=0; i<vertices ; i++)
         {
             for(j=0; j<vertices; j++)
             {
                 if(matrizAdyacencia[i][j] != 1000000000)
                 {
                     //if(i != j) // commented by JParroquiano
                     if(i != j && i == from-1 && j == to-1) // add by Jparroquiano
                     {
                         if(caminos[i][j].equals(""))
                         {
                             caminitos += "De ("+(i+1)+"-->"+(j+1)+") Irse Por...("+(i+1)+"," +(j+1)+")\n";
                             caminitos2.add(i+1);
                             caminitos2.add(j+1);
                         }
                         else
                         {
                             caminitos += "De ("+(i+1)+"-->"+(j+1)+") Irse Por...("+(i+1)+"," +caminos[i][j]+","+(j+1)+")\n";
                             caminitos2.add(i+1);
                             if( Integer.parseInt(caminos[i][j].toString()) > 9 && Integer.parseInt(caminos[i][j].toString()) < 100 )
                             {
                                caminitos2.add(Integer.parseInt(caminos[i][j].toString())/10);
                                caminitos2.add(Integer.parseInt(caminos[i][j].toString())%10);
                             }
                             else if( Integer.parseInt(caminos[i][j].toString()) > 99 )
                             {
                                 caminitos2.add(Integer.parseInt(caminos[i][j].toString())/100);
                                 caminitos2.add((Integer.parseInt(caminos[i][j].toString())/10)%10);
                                 caminitos2.add(Integer.parseInt(caminos[i][j].toString())%10);
                             }
                             else
                             {
                                 caminitos2.add(Integer.parseInt(caminos[i][j].toString()));
                             }
                             caminitos2.add(j+1);
                             //caminitos2[i] = Integer.parseInt(caminos[i][j]);
                         }
                     }
                 }
             }
         }
         //return "La matriz de caminos cortos es:\n"+ cadena +
           //      "\n Los diferentes caminos más cortos son\n"+caminitos;
        return caminitos2 ;
     }
     
     /**
      * Metodo que halla los caminosR
      * @param i; int
      * @param k: int
      * @param caminosAuxiliares: String[][]
      * @param caminoRecorrido: String
      */
     public String caminosR(int i, int k, String[][] caminosAuxiliares, String caminoRecorrido)
     {
         if(caminosAuxiliares[i][k].equals(""))
         {
             return "";
         } 
         else
         {
             // Recursividad al millón
             caminoRecorrido += caminosR(i,Integer.parseInt(caminosAuxiliares[i][k].toString()),caminosAuxiliares,caminoRecorrido) +
                             +(Integer.parseInt(caminosAuxiliares[i][k].toString()) +1);//+ ","; 
             //System.out.println(((Object)caminoRecorrido).getClass().getSimpleName());
             //System.out.println(":::>"+caminoRecorrido);
             return caminoRecorrido;
         }
     }
}
