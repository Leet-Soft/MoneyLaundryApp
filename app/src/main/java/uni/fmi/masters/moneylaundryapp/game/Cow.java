package uni.fmi.masters.moneylaundryapp.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.Toast;

import java.util.Random;

import uni.fmi.masters.moneylaundryapp.R;

public class Cow {
    int x;
    int y;
    int ground;

    int speed;
    Bitmap bitmap;
    Context context;
    Random random = new Random();

    Rect collisionMesh;

    public Cow(Context contex, int x, int y, int ground){
        this.context = contex;
        this.x = x;
        this.y = y;
        this.ground = ground;

        speed = random.nextInt(11);

        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.muu);

        collisionMesh = new Rect(x,y,
                x + bitmap.getWidth(),
                y + bitmap.getHeight());
    }

    public void update(){
        y += speed;
        x -= GameView.speed;

       // if(y >= ground){

            //Toast.makeText(context, "Splash...", Toast.LENGTH_SHORT).show();
       // }

        collisionMesh.top = y;
        collisionMesh.bottom = y + bitmap.getHeight();
        collisionMesh.left = x;
        collisionMesh.bottom = x + bitmap.getWidth();
    }
}
