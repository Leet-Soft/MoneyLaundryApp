package uni.fmi.masters.moneylaundryapp.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import uni.fmi.masters.moneylaundryapp.R;

public class Player {
    double fuel = 100;
    public int ySpeed;
    int x;
    int y;

    Context context;
    int maxY;
    int minY;
    Bitmap bitmap;

    Rect collisionMesh;

    public Player(Context context, int sizeY){
        this.minY = 0;

        this.context = context;

        bitmap = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.car);

        this.maxY = sizeY - 200 - bitmap.getHeight();

        x = 50;
        y = maxY;

        collisionMesh = new Rect(x,y,
                x + bitmap.getWidth(),
                y + bitmap.getHeight());
    }

    public void update(){
        if(fuel <= 0 && y < maxY - 300 && ySpeed < 0){
            ySpeed *= -1;
        }
        y += ySpeed;

        if(ySpeed < 0 && y < maxY - 300){
            fuel -= 1;
        }



        if(y > maxY){
            y = maxY;
            ySpeed = 0;
        }else if(y < 200){
            y = 200;
        }

        collisionMesh.top = y;
        collisionMesh.bottom = y + bitmap.getHeight();
    }

}
