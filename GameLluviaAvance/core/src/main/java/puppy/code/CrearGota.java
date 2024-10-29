package puppy.code;

import com.badlogic.gdx.math.MathUtils;

public class CrearGota {
    public static Gota crearGota(){
        int i = MathUtils.random(1,10);
        if (i < 5)
            return new GotaBuena();
        else
            return new GotaMala();
    }

}
