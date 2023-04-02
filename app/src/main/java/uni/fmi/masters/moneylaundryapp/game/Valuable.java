package uni.fmi.masters.moneylaundryapp.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import uni.fmi.masters.moneylaundryapp.R;

public class Valuable {
    int x;
    int y;

    int score;

    int maxX;
    int maxY;
    int roadHeight = 200;

    Bitmap bitmap;
    Context context;
    Random random = new Random();


    Rect collisionMesh;

    public Valuable(Context contex, int sizeX, int sizeY){
        this.context = contex;
        maxX = sizeX;
        maxY = sizeY - roadHeight;

        reuseValuable();
    }

    public void reuseValuable(){
        if(random.nextInt(101) > 90){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.diamond);
            score = 150;
        }else if(random.nextInt(101) > 80){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.can);
            score = 0;
        }else{
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.coin);
            score = 15;
        }

        if(score == 0){
            y = maxY - bitmap.getHeight();
        }else{
            y = random.nextInt(maxY - 200);
        }
        x = maxX + random.nextInt(300);

        collisionMesh = new Rect(x,y,
                x + bitmap.getWidth(),
                y + bitmap.getHeight());
    }

    public void update(){
        x -= GameView.speed;

        if(x < -50 - bitmap.getHeight()){
            reuseValuable();
        }

        collisionMesh.left = x;
        collisionMesh.right = x + bitmap.getWidth();
    }
}
