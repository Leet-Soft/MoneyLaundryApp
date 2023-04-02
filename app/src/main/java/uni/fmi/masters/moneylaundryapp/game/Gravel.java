package uni.fmi.masters.moneylaundryapp.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import uni.fmi.masters.moneylaundryapp.R;

public class Gravel {
    int x;
    int y;
    Context context;
    int maxX;
    int maxY;
    int minY;

    Bitmap bitmap;

    int[] skins = {
            R.drawable.road1,
            R.drawable.road2,
            R.drawable.road3
    };

    Random random = new Random();

    public Gravel(Context context,int sizeX,int sizeY){
        this.context = context;
        this.maxX = sizeX;
        this.maxY = sizeY;
        minY = sizeY - 200;

        bitmap = BitmapFactory.decodeResource(context.getResources(),
                skins[random.nextInt(skins.length)]);
        x = random.nextInt(maxX) + 200;
        y = random.nextInt(201) + minY;
    }

    public void reuseGravel(){
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                skins[random.nextInt(skins.length)]);
        x = maxX + 200;
        y = random.nextInt(201) + minY;
    }

    public void update(){
        x -= GameView.speed;

        if( x < -1 * random.nextInt(100)){
            reuseGravel();
        }
    }



}
