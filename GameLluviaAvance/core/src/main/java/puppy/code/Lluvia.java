package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<Gota> rainDrops;
    private long lastDropTime;
    private Sound dropSound;
    private Music rainMusic;

	public Lluvia() {
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        crear();
	}

	public void crear() {
        rainDrops= new Array<Gota>();
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
	}

	private void crearGotaDeLluvia() {
        rainDrops.add(CrearGota.crearGota());
        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Tarro tarro) {
	   // generar gotas de lluvia
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();

	   // revisar si las gotas cayeron al suelo o chocaron con el tarro
	   for (int i = 0; i < rainDrops.size ; i++ ) {
		  Rectangle raindrop = rainDrops.get(i).getHitbox(); //Variable local que toma la gota en su posicion del arreglo
	      raindrop.y -= 300 * Gdx.graphics.getDeltaTime();//Calcula el movimiento en el eje Y para ver cuando termina de recorrer hasta el final de la pantalla

           eliminarGotaEnSuelo(i,raindrop); //Verifica si la gota cae al suelo y se elimina
           if(raindrop.overlaps(tarro.getArea())){//lógica si colisiona con tarro
               return eventoColisionTarro(tarro,i);
           }
	   }
	  return true;

   }
    private boolean eventoColisionTarro(Tarro tarro, int i) //Modularizacion codigo
    {
        eliminarGota(i);
        return rainDrops.get(i).efectoEnTarro(tarro);
    }


   private void eliminarGota(int i){ //Modularizacion de codigo
       rainDrops.removeIndex(i);
   }

   private void eliminarGotaEnSuelo(int i,Rectangle raindrop){ //Modularizacion de codigo
       if(raindrop.y + 64 < 0) {
           eliminarGota(i);
       }
   }

   public void actualizarDibujoLluvia(SpriteBatch batch) {
	  for (int i=0; i < rainDrops.size; i++ ) {
            Gota gota = rainDrops.get(i);
            gota.dibujar(batch);
	   }
   }
   public void destruir() {
      dropSound.dispose();
      rainMusic.dispose();
   }
   public void pausar() {
	  rainMusic.stop();
   }
   public void continuar() {
	  rainMusic.play();
   }

}
