package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GotaBuena extends Gota{
    private Texture gotaBuena;

    public GotaBuena(){
        super();
        gotaBuena = new Texture(Gdx.files.internal("drop.png"));
    }
    public boolean efectoEnTarro(Tarro tarro){
        tarro.sumarPuntos(10);
        return true;
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(gotaBuena, this.getHitbox().x, this.getHitbox().y);
    }
}
