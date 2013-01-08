package edu.vub.at.nfcpoker.smartwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.sonyericsson.extras.liveware.extension.util.widget.WidgetExtension;

public class WePokerWidgetExtension extends WidgetExtension {

	public static final String UPDATE_ACTION = "edu.vub.at.nfcpoker.smartwatch.UPDATE";

	private Runnable mInfoTimeout;
	private Handler mHandler = new Handler();
	private BroadcastReceiver mReceiver;
	private IntentFilter mFilter;

	protected double mProbability = -1;

	public WePokerWidgetExtension(Context context, String hostAppPackageName) {
		super(context, hostAppPackageName);
		mFilter = new IntentFilter();
		mFilter.addAction(UPDATE_ACTION);
		
		mReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("wePoker service", "received intent" + intent);
				mProbability = intent.getDoubleExtra("probability", -1);
				Log.d("wePoker service", "new probability: " + mProbability);
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						updateWidget(true);
					}
				});
			}
		};
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
        mContext.registerReceiver(mReceiver, mFilter);
	}

	@Override
	public void onStopRefresh() {
		mContext.unregisterReceiver(mReceiver);
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
        if (mProbability > 0) {
        	canvas.drawColor(Color.GREEN);        	
        } else {
        	canvas.drawColor(Color.RED);
        }
        
        showBitmap(bitmap);
	}
}
