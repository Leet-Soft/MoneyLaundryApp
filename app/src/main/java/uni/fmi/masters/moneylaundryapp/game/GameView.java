package uni.fmi.masters.moneylaundryapp.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    boolean alive = true;
    Player player;

    public static int speed = 5;

    ArrayList<Gravel> gravel = new ArrayList<>();

    Paint paint;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    Thread gameThread;

    int screenSizeX;
    int screenSizeY;

    public GameView(Context context, int screnSizeX, int screenSizeY) {
        super(context);
        this.screenSizeX = screnSizeX;
        this.screenSizeY = screenSizeY;

        surfaceHolder = getHolder();
        paint = new Paint();

        player = new Player(context, screenSizeY);


        for(int i = 0; i < 20; i++){
            gravel.add(new Gravel(context, screnSizeX, screenSizeY));
        }

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while(alive){
            update();//Обновява състояните на компонентите
            draw();//да рисува компнентите
            refreshRate();// да забавя играта
        }
    }

    private void update(){
        for(int i = 0; i < gravel.size(); i++){
            gravel.get(i).update();
        }
    }

    private void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.rgb(135,206,235));


            paint.setColor(Color.rgb(58,58,58));
            canvas.drawRect(0,screenSizeY - 200 ,screenSizeX
                    ,screenSizeY - 196, paint);

            paint.setColor(Color.rgb(175,175,60));
            canvas.drawRect(0,screenSizeY - 196,screenSizeX
                    ,screenSizeY -185 ,paint);

            paint.setColor(Color.rgb(166,102,45));
            canvas.drawRect(0,screenSizeY - 185,screenSizeX,
                    screenSizeY,paint);


            for(int i = 0; i < gravel.size(); i++){
                canvas.drawBitmap(gravel.get(i).bitmap,
                        gravel.get(i).x, gravel.get(i).y,paint);
            }

            canvas.drawBitmap(player.bitmap, player.x, player.y,paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void refreshRate(){
        try {
            gameThread.sleep(40);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
