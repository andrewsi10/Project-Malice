package com.mygdx.game.Map;

import java.io.IOException;
import java.io.StringWriter;

import com.badlogic.gdx.utils.XmlWriter;

/**
 *  Holder class for code
 *
 *  @author  Nathan Lui
 *  @version May 17, 2015
 *  @author  Period: 4
 *  @author  Assignment: TestGame-core
 *
 *  @author  Sources: TODO
 */
public class Test
{
    public Test()
    {
        StringWriter writer = new StringWriter();
        XmlWriter xml = new XmlWriter(writer);
        try
        {
            xml.element("meow")
                .attribute("moo", "cow")
                .element("child")
                    .attribute("moo", "cow")
                    .element("child")
                        .attribute("moo", "cow")
                        .text("XML is like violence. If it doesn't solve your problem, you're not using enough of it.")
                    .pop()
                .pop()
            .pop();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(writer);
      // create the raindrops array and spawn the first raindrop
//      raindrops = new Array<Rectangle>();
//      spawnRaindrop();
    }

//  private void spawnRaindrop() {
//     Rectangle raindrop = new Rectangle();
//     raindrop.x = MathUtils.random(0, 800-64);
//     raindrop.y = 480;
//     raindrop.width = 64;
//     raindrop.height = 64;
//     raindrops.add(raindrop);
//     lastDropTime = TimeUtils.nanoTime();
//  }
    
    
//  for(Rectangle raindrop: raindrops) {
//  batch.draw(dropImage, raindrop.x, raindrop.y);
//}
    
//  if(Gdx.input.isTouched()) {
//  Vector3 touchPos = new Vector3();
//  touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//  camera.unproject(touchPos);
//  sprite.x = touchPos.x - 64 / 2;
//}
    // check if we need to create a new raindrop
//  if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

  // move the raindrops, remove any that are beneath the bottom edge of
  // the screen or that hit the bucket. In the later case we play back
  // a sound effect as well.
//  Iterator<Rectangle> iter = raindrops.iterator();
//  while(iter.hasNext()) {
//     Rectangle raindrop = iter.next();
//     raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
//     if(raindrop.y + 64 < 0) iter.remove();
//     if(raindrop.overlaps(sprite)) {
////        dropSound.play();
//        iter.remove();
//     }
//  }
}
