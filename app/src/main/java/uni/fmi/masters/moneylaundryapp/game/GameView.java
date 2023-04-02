package uni.fmi.masters.moneylaundryapp.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

import uni.fmi.masters.moneylaundryapp.R;

public class GameView extends SurfaceView implements Runnable {

    boolean alive = true;
    Player player;
    int score = 0;
    int lives = 3;

    public static int speed = 10;

    ArrayList<Gravel> gravel = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Valuable> stuff = new ArrayList<>();
    static ArrayList<Cow> cows = new ArrayList<>();

    Paint paint;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    Thread gameThread;

    int screenSizeX;
    int screenSizeY;
    Bitmap hearth;

    public GameView(Context context, int screnSizeX, int screenSizeY) {
        super(context);
        this.screenSizeX = screnSizeX;
        this.screenSizeY = screenSizeY;

        surfaceHolder = getHolder();
        paint = new Paint();

        hearth = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.live);

        player = new Player(context, screenSizeY);

        enemies.add(new Enemy(context,screnSizeX, screenSizeY));

        for(int i = 0; i < 20; i++){
            gravel.add(new Gravel(context, screnSizeX, screenSizeY));
        }

        stuff.add(new Valuable(getContext(),screnSizeX, screenSizeY));

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

        player.update();

        for(int i = 0; i < gravel.size(); i++){
            gravel.get(i).update();
        }

        for(int i = 0; i < stuff.size(); i++){
            stuff.get(i).update();

            if(Rect.intersects(stuff.get(i).collisionMesh, player.collisionMesh)){

                if(stuff.get(i).score == 0){
                    player.fuel += 100;
                }else{
                    score += stuff.get(i).score;
                }
                stuff.get(i).reuseValuable();
            }
        }

        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).update();

            if(Rect.intersects(enemies.get(i).collisionMesh, player.collisionMesh)){
                lives--;
                if(lives < 1){
                    alive = false;
                }
                enemies.get(i).reuseEnemy();
            }
        }

        for(int i = 0; i < cows.size(); i++){
            cows.get(i).update();

            if(Rect.intersects(cows.get(i).collisionMesh, player.collisionMesh)){
                lives--;
                if(lives < 1){
                    alive = false;
                }
                cows.remove(i);
                //Toast.makeText(getContext(), "Watch where you throw burgers!", Toast.LENGTH_SHORT).show();
            }
        }

        score++;

        if(score % 404 == 0 && enemies.size() < 5){
            enemies.add(new Enemy(getContext(),screenSizeX, screenSizeY));
        }

        if(score % 501 == 0){
            speed++;
        }

        if(score % 301 == 0 && stuff.size() < 3){
            stuff.add(new Valuable(getContext(),screenSizeX, screenSizeY));
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


            for(int i = 0; i < enemies.size(); i++){
                canvas.drawBitmap(enemies.get(i).bitmap,
                        enemies.get(i).x, enemies.get(i).y,paint);
            }

            for(int i = 0; i < stuff.size(); i++){
                canvas.drawBitmap(stuff.get(i).bitmap,
                        stuff.get(i).x, stuff.get(i).y,paint);
            }

            for(int i = 0; i < cows.size(); i++){
                canvas.drawBitmap(cows.get(i).bitmap,
                        cows.get(i).x, cows.get(i).y,paint);
            }


            canvas.drawBitmap(player.bitmap, player.x, player.y,paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("Score:" + score, 50, 150, paint);

            for(int i = 0; i < lives; i++){
                canvas.drawBitmap(hearth,
                        300 + (hearth.getWidth() + 10) * i
                        ,150 - hearth.getHeight(),paint);
            }

            int fuelBeginingX = screenSizeX - 600;

            paint.setColor(Color.RED);

            canvas.drawRect(fuelBeginingX, 150,
                    (int)(fuelBeginingX + (player.fuel * 3)),
                    180, paint);

            paint.setColor(Color.WHITE);
            canvas.drawText("Score:" + player.fuel, fuelBeginingX + 100, 150, paint);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:

                player.ySpeed = -10;
                break;
            case MotionEvent.ACTION_UP:

                player.ySpeed = 10;
                break;
        }
        return true;
    }


}
