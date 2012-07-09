package com.walnutlabs.android;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

public class ProgressHUDDemo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void showSimpleIndeterminate(View v) {
    	TimeConsumingTask t = new TimeConsumingTask();
    	t.execute();
    }
    
    
    public class TimeConsumingTask extends AsyncTask<Void, String, Void> implements OnCancelListener {
    	ProgressHUD mProgressHUD;    	

    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(ProgressHUDDemo.this,"Connecting", true,true,this);
    		super.onPreExecute();
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
			try {
				publishProgress("Connecting");
				Thread.sleep(2000);
				publishProgress("Downloading");
				Thread.sleep(5000);
				publishProgress("Done");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
    	
		@Override
		protected void onProgressUpdate(String... values) {
			mProgressHUD.setMessage(values[0]);
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			mProgressHUD.dismiss();
			super.onPostExecute(result);
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			this.cancel(true);
			mProgressHUD.dismiss();
		}		
    }
    
}
