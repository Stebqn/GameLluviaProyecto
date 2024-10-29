package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.lang.annotation.Retention;

public abstract class Gota {
    private Rectangle hitbox;

    public Gota(){
        hitbox = new Rectangle();
        hitbox.x = MathUtils.random(0, 800-64);
        hitbox.y = 480;
        hitbox.width = 64;
        hitbox.height = 64;
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
    public abstract boolean efectoEnTarro(Tarro tarro);
    public abstract void dibujar(SpriteBatch batch);
}
