package galerie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
 
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
 
public class GridViewImageAdapter extends BaseAdapter {
 
    private Context context;
    private ArrayList<Integer> images = new ArrayList<Integer>();
    private int imageWidth;
 
    public GridViewImageAdapter(Context context, ArrayList<Integer> images,
            int imageWidth) {
        this.context = context;
        this.images = images;
        this.imageWidth = imageWidth;
    }
 /**
  * gibt die anzahl der bilder in der ArrayList images zurück
  */
    @Override		
    public int getCount() {
        return this.images.size();
    }
 
    @Override
    public Object getItem(int position) {
        return this.images.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	//adapter shizzle
    	//convert recycle entweder alten view verwenden oder neuen View erstellen
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }
        
        //Bild scalieren 
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
                imageWidth));
        	imageView.setImageResource(images.get(position));
        // image view click listener
        imageView.setOnClickListener(new OnImageClickListener(position));
 
        return imageView;
    }
 
    class OnImageClickListener implements OnClickListener {
 
        int _postion;
 
        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }
 
        @Override
        public void onClick(View v) {
            // on selecting grid view image launch full screen activity
            Intent i = new Intent(context.getApplicationContext(), FullScreenViewActivity.class);
            i.putExtra("position", _postion);
            context.startActivity(i);
        }
    }
 
    /*
     * Resizing image size
     */
    public static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
        try { 
        	//file objekt laden, mit dem pfad zu dem bild
            File f = new File(filePath);
            //setze bild lade optionen
            BitmapFactory.Options o = new BitmapFactory.Options();
            //lade nu meta daten von dem bild
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
 
            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            int scale = 1;
            //bild skalieren
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
                    && o.outHeight / scale / 2 >= REQUIRED_HIGHT){
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            //das neue verhältnis in den optionen für das bildladeverfahrn setzen
            o2.inSampleSize = scale;
            //resized bild laden und zurückgeben
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}