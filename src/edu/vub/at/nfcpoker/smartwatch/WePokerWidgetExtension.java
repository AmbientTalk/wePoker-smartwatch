package edu.vub.at.nfcpoker.smartwatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.sonyericsson.extras.liveware.extension.util.widget.WidgetExtension;

public class WePokerWidgetExtension extends WidgetExtension {

	private Runnable mInfoTimeout;
	private Handler mHandler = new Handler();

	public WePokerWidgetExtension(Context context, String hostAppPackageName) {
		super(context, hostAppPackageName);
	}

	@Override
	public void onStartRefresh() {
        mInfoTimeout = new Runnable() {
            public void run() {
                updateWidget(true);
                mInfoTimeout = null;
            }
        };
        mHandler.postDelayed(mInfoTimeout, 2000);
	}

	@Override
	public void onStopRefresh() {
        if (mInfoTimeout != null) {
            mHandler.removeCallbacks(mInfoTimeout);
            mInfoTimeout = null;
        }
	}	 

	protected void updateWidget(boolean b) {
        Bitmap bitmap = Bitmap.createBitmap(128, 110, Bitmap.Config.ARGB_8888);

        // Set the density to default to avoid scaling.
        bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.YELLOW);
        
        showBitmap(bitmap);
	}
}
