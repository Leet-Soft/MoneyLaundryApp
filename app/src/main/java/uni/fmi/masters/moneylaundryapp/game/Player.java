package uni.fmi.masters.moneylaundryapp.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uni.fmi.masters.moneylaundryapp.R;

public class Player {
    int x;
    int y;

    Context context;
    int maxY;
    int minY;
    Bitmap bitmap;

    public Player(Context context, int sizeY){
        this.minY = 0;
        this.maxY = sizeY - 200;
        this.context = context;

        bitmap = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.car);

        x = 50;
        y = maxY - bitmap.getHeight();
    }

}
