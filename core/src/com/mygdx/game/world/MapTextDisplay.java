package com.mygdx.game.world;

import java.util.Scanner;

public class MapTextDisplay
{
    public static void main(String[] arg)
    {
//      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//      config.title = Malice.TITLE + " v" + Malice.VERSION;
//      config.vSyncEnabled = true;
//      config.width = 1280;
//      config.height = 720;
//      new LwjglApplication( new Malice(), config );
        Scanner scanUser = new Scanner( System.in );
        Map map;
        String s;
        do {
            map = new Map( 25, 25, true );
         map.generate( Map.DUNGEON );
        System.out.println( map );
        s = scanUser.nextLine();
        } while ( s.isEmpty() || s.charAt( 0 ) != 'q' );
        
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
