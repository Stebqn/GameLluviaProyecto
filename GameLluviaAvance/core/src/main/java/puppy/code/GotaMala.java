package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GotaMala extends Gota{
    private Texture gotaMala;
    public GotaMala(){
        super();
        gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
    }
    public boolean efectoEnTarro(Tarro tarro){
        tarro.dañar();
        if (tarro.getVidas() <= 0) {
            tarro.destruir();
            return false;
        }
        return true;
    }
    public void dibujar(SpriteBatch batch){
        batch.draw(gotaMala, this.getHitbox().x, this.getHitbox().y);
    }
}
