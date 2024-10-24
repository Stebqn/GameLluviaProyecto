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
	private Array<Rectangle> rainDropsPos;
	private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Sound dropSound;
    private Music rainMusic;

	public Lluvia(Texture gotaBuena, Texture gotaMala, Sound ss, Music mm) {
		rainMusic = mm;
		dropSound = ss;
		this.gotaBuena = gotaBuena;
		this.gotaMala = gotaMala;
	}

	public void crear() {
		rainDropsPos = new Array<Rectangle>();
		rainDropsType = new Array<Integer>();
		crearGotaDeLluvia();
	      // start the playback of the background music immediately
	      rainMusic.setLooping(true);
	      rainMusic.play();
	}

	private void crearGotaDeLluvia() {
	      /*Rectangle raindrop = new Rectangle();
	      raindrop.x = MathUtils.random(0, 800-64);
	      raindrop.y = 480;
	      raindrop.width = 64;
	      raindrop.height = 64;
	      rainDropsPos.add(raindrop);*/

	      // ver el tipo de gota

	      if (MathUtils.random(1,10)<5)
	         rainDropsType.add(1);
	      else
	    	 rainDropsType.add(2);
	      lastDropTime = TimeUtils.nanoTime();
          /*Rectangulo sea propio de la clase Gota.
          * Para crear una gota buena o mala:
          * if(MathUtils.random(1,10)<5){
          *     Gota gota = new GotaBuena();
          *     rainDropsType.add(gota);
          * }else{
          * Gota gota = new GotaBuena();
           *     rainDropsType.add(gota);
          * }*/
    }

    //****DOCUMENTAR LO COMENTADO EN EL CODIGO****
    public boolean actualizarMovimiento(Tarro tarro) {
	   // generar gotas de lluvia
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();
	   // revisar si las gotas cayeron al suelo o chocaron con el tarro
	   for (int i=0; i < rainDropsPos.size; i++ ) {
		  Rectangle raindrop = rainDropsPos.get(i); //Variable local que toma la gota en su posicion del arreglo
	      raindrop.y -= 300 * Gdx.graphics.getDeltaTime();//Calcula el movimiento en el eje Y para ver cuando termina de recorrer hasta el final de la pantalla

           eliminarGotaEnSuelo(i,raindrop); //Verifica si la gota cae al suelo y se elimina
           if(raindrop.overlaps(tarro.getArea())){//lógica si colisiona con tarro
               return eventoColisionTarro(tarro,i,raindrop);
           }
	   }
	  return true;
   }

    private boolean eventoColisionTarro(Tarro tarro, int i, Rectangle raindrop) //Modularizacion codigo
    {
        if(rainDropsType.get(i)==1) { // gota dañina
            tarro.dañar();
            if (tarro.getVidas()<=0)
                return false; // si se queda sin vidas retorna falso /game over
            eliminarGota(i);
        }else { // gota a recolectar
            tarro.sumarPuntos(10);
            dropSound.play();
            eliminarGota(i);
        }
       return true;
   }

   private void eliminarGota(int i){ //Modularizacion de codigo
       rainDropsPos.removeIndex(i);
       rainDropsType.removeIndex(i);
   }

   private void eliminarGotaEnSuelo(int i,Rectangle raindrop){ //Modularizacion de codigo
       if(raindrop.y + 64 < 0) {
           eliminarGota(i);
       }
   }

   public void actualizarDibujoLluvia(SpriteBatch batch) {
	  for (int i=0; i < rainDropsPos.size; i++ ) {
		  Rectangle raindrop = rainDropsPos.get(i);
		  if(rainDropsType.get(i)==1) // gota dañina
	         batch.draw(gotaMala, raindrop.x, raindrop.y);
		  else
			 batch.draw(gotaBuena, raindrop.x, raindrop.y);
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
