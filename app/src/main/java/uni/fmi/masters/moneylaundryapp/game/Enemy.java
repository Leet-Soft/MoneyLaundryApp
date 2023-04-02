package uni.fmi.masters.moneylaundryapp.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import uni.fmi.masters.moneylaundryapp.R;

public class Enemy {

    int x;
    int y;

    int maxX;
    int maxY;
    int roadHeight = 200;
    boolean isSpaceship = false;

    Bitmap bitmap;
    Context context;
    Random random = new Random();
    int[] rocks = {
            R.drawable.r1,
            R.drawable.r2,
            R.drawable.r3,
            R.drawable.r4};
    int[] ships = {R.drawable.spaceship};

    Rect collisionMesh;

    public Enemy(Context contex, int sizeX, int sizeY){
        this.context = contex;
        maxX = sizeX;
        maxY = sizeY - roadHeight;

        reuseEnemy();
    }

    public void reuseEnemy(){
        if(random.nextBoolean()){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    ships[0]);
            y = random.nextInt(maxY - 200);
            isSpaceship = true;
        }else{
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    rocks[random.nextInt(rocks.length)]);
            y = maxY - bitmap.getHeight();
            isSpaceship = false;
        }
        x = maxX + random.nextInt(300);

        collisionMesh = new Rect(x,y,
                x + bitmap.getWidth(),
                y + bitmap.getHeight());
    }

    public void update(){

        if(isSpaceship){
            if(random.nextInt(501) > 490){
                GameView.cows.add(new Cow(context, x, y,maxY));
            }
        }

        x -= GameView.speed;

        if(x < -50 - bitmap.getHeight()){
            reuseEnemy();
        }

        collisionMesh.left = x;
        collisionMesh.right = x + bitmap.getWidth();

    }
}
