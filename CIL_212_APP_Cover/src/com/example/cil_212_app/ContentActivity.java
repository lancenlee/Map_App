package com.example.cil_212_app;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.SurfaceView;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class ContentActivity extends Activity {

	private ImageButton media[]=new ImageButton[3];
	private int media_id[]=new int[]{R.id.pause,R.id.play,R.id.stop};
	private SeekBar seekBar;
	private Player player;
	private SurfaceView surfaceView;
	String video_url="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_basic);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//获取传递的信息....
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		String activity_name=bundle.getString("name");
		int position=bundle.getInt("position");
		
		//获取播放视频的URL....
		getVideoUrl u=new getVideoUrl(activity_name, position);
		video_url=u.reUrl();
		init();
		System.out.println(video_url);
	}

	public void init(){
		for(int j=0;j<3;j++){
			media[j]=(ImageButton) findViewById(media_id[j]);
			media[j].setOnClickListener(new OnClickEvent());
		}
		seekBar=(SeekBar) findViewById(R.id.seekbar);
		seekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		surfaceView=(SurfaceView) findViewById(R.id.surfaceview);
		player=new Player(surfaceView, seekBar);
	}
	
	class OnClickEvent implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v==media[0]){
				player.pause();
			}else if(v==media[1]){
				player.playUrl(video_url);
			}else if(v==media[2]){
				player.stop();
			}
		}	
	}
	
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener{

		int progress;
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			this.progress = progress * player.mediaPlayer.getDuration()/seekBar.getMax();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			player.mediaPlayer.seekTo(progress);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}

}
