package play.android.com.sudokusolver;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class PuzzleView extends View {
    Paint line=new Paint();
    Paint sline=new Paint();
    float padding,mazeWidth,cellsize;
    int height,width;
   public ArrayList<ArrayList< Integer > >Matrix=new ArrayList<>();
    int touchx=-1,touchy=-1;

    class Point
    {
        public int x;
        public int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }




    }

    public void setdraw()
    {
        invalidate();
    }

    public PuzzleView(Context context) {
        super(context);

    }

    Point returnBox(int x,int y)
    {
        int actualx=x-(int)padding;
        int actualy=y-(int)(padding*3);
        int coorx=(int)(actualx/cellsize);
        int coory=(int)(actualy/cellsize);

        return new Point(coorx,coory);

    }

    Point returnCoor(Point box)
    {
        int row=box.x;
        int col=box.y;

        int coory=(int)((((row)*cellsize)+padding*3)+(cellsize/2));
        int coorx=(int)((((col)*cellsize)+padding) + (cellsize/2.2));

        return new Point(coory,coorx);


    }

    public PuzzleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        line.setStrokeWidth(6f);
        line.setColor(Color.BLACK);
        line.setTextSize(30f);
        sline.setStrokeWidth(2f);
        sline.setColor(Color.GRAY);
        padding=getResources().getDimension(R.dimen.activity_horizontal_margin);
        for(int i=0;i<9;i++)
        {
            Matrix.add(new ArrayList<>(Collections.nCopies(9,0)));

        }
    }

    public PuzzleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.height=h;
        width=w;
        mazeWidth=width-2*padding;
        cellsize=mazeWidth/9;


        invalidate();

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int)  event.getX();
        int y= (int)  event.getY();
        Point box=returnBox(x,y);
        Toast.makeText(getContext(), ""+box.x+","+box.y, Toast.LENGTH_SHORT).show();
        touchx=x;
        touchy=y;
        invalidate();

        return super.onTouchEvent(event);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(padding,padding*3,width-padding,padding*3,line);
        int sum= (int) padding;
        for(int i=0;i<10;i++)
        {
            if(i%3==0)
            canvas.drawLine(sum,padding*3,sum,(padding*3)+mazeWidth,line);
            else
                canvas.drawLine(sum,padding*3,sum,(padding*3)+mazeWidth,sline);
            sum+=(int)cellsize;
        }
        sum=(int)(padding*3);
        for(int i=0;i<10;i++)
        {
            if(i%3==0)
            canvas.drawLine(padding,sum,width-padding,sum,line);
            else
                canvas.drawLine(padding,sum,width-padding,sum,sline);
            sum+=(int)cellsize;
        }
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                Point p=returnCoor(new Point(i,j));
                canvas.drawText(""+Matrix.get(i).get(j),p.y,p.x,line);

            }


        }
        if(touchx!=-1 && touchy!=-1)
            canvas.drawCircle(touchx,touchy,10f,line);



    }
}
