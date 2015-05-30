package com.mygdx.game.world;

import java.util.Scanner;

/**
 *  Map Testing class, visually tests the random generation by printing out the 
 *  map
 *
 *  @author  Nathan Lui
 *  @version May 29, 2015
 *  @author  Period: 4
 *  @author  Assignment: my-gdx-game-core
 *
 *  @author  Sources:
 */
public class MapTextDisplay
{
    /**
     * Runs short testing program for visually checking the map on command line
     * @param arg command line arguments -- not used
     */
    public static void main(String[] arg)
    {
//        Map map1 = new Map( 25,25, true );
//        int x1 = 5;
//        int y1 = 5;
//        int w1 = 5;
//        int h1 = 5;
//        int x2 = 15;
//        int y2 = 10;
//        int w2 = 7;
//        int h2 = 5;
//        int x3 = 8;
//        int y3 = 8;
//        int w3 = 7;
//        int h3 = 7;
//        map1.createRoom( x1, y1, w1, h1 );
//        map1.createRoom( x2, y2, w2, h2 );
//        map1.createRoom( y2, x2, h2, w2 );
//        map1.createRoom( x3, y3, w3, h3 );
//        System.out.println( map1 );
        Scanner scanUser = new Scanner( System.in );
        Map map;
        String s;
        do {
            map = new Map( 25, 25, true );
         map.generate( Map.RANDOM );
        System.out.println( map );
        s = scanUser.nextLine();
        } while ( true );
        
        // testing recursive methods: buggy
//      Map map1 = new Map( 25,25,true);
//      while ( scanUser.hasNext() )
//      {
//            System.out.println( map1 );
//          s= scanUser.next();
//          try {
//              switch( s.charAt( 0 ) ) {
//                  case 'c':
//                      map1.createRoom( scanUser.nextInt(), scanUser.nextInt(), scanUser.nextInt(), scanUser.nextInt() );
//                      break;
//                  case 'p':
//                      map1.hasPath( scanUser.nextInt(), scanUser.nextInt(), scanUser.nextInt(), scanUser.nextInt(), true );
//                      break;
//                  case 's':
//                      System.out.println( map1.sizeOfRoom( scanUser.nextInt(), scanUser.nextInt() ) );
//                      break;
//                  case 'g':
//                      map1.generate( scanUser.nextInt() );
//                      break;
//              }
//          } catch( Exception e )
//          {
//              
//          }
      }
}
