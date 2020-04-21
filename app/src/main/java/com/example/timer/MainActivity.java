package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;
    Boolean counterisActive=false;
    Button startBtn;
    CountDownTimer countDownTimer;
    public void resetTimer() //when the timer is stopped
    {
        textView.setText("0:30");//default text of text view
        //textView.setText("10:00");
        //textView.setText("20:00");
        seekBar.setProgress(30);
        //seekBar.setProgress(600);
        //seekBar.setProgress(1200);
        seekBar.setEnabled(true); //seek bar will become active
        startBtn.setText("START"); //button on text will be start
        countDownTimer.cancel();  //count down timer will be stopped
        counterisActive=false; //set the counter to inactive
    }
    public void buttonClicked(View view)
{
    if (counterisActive) //if the timer is stopped
    {
       resetTimer();
    }else {
         counterisActive = true; //set counter to active
        seekBar.setEnabled(false); // seekbar will be disable, cannot be moved
        startBtn.setText("STOP"); //text on the button will be changed to stop
        //getting the current position of the seekbar in seconds and add 1 second or 100 milliseconds, so that onFinish() works correctly by the time the countdown timer will go to 0
        countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { //the timer gets updated every second
                updateTimer((int) (millisUntilFinished / 1000)); //converting milliseconds to seconds
            }

            @Override
            public void onFinish() { //on stopping the count down, onFinish will be executed
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn); //connecting to media player
                mediaPlayer.start(); //starting the media player
                resetTimer();
            }
        }.start(); //starts the countdown timer

    }

}

public void updateTimer(int secLeft)
{
    int minutes=secLeft / 60; //converting seconds into minutes
    int seconds=secLeft - minutes* 60; //subtracting the minutes, as only seconds has to be shown
    String secondString=Integer.toString(seconds); //converting time in integer to string
    String minString=Integer.toString(minutes);
    //-------------------APPEND THE SECONDS TO 0 IF SECONDS IS LESS THAN 10---------------------------
    if (seconds < 10)
    {
        secondString="0" + secondString;
    }
    if (minutes < 10)
    {
        minString="0" + minString;
    }
        textView.setText(minString + ":" + secondString); //displaying the minute and secon on text view


}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar=(SeekBar) findViewById(R.id.seekBar);
        textView=(TextView) findViewById(R.id.textView);
        startBtn=(Button) findViewById(R.id.startBtn);
        seekBar.setMax(600); //10 mins or 600sec i.e., 600 will be the maximum value of the seekBar
        //setBar.setMax(1200);
        seekBar.setProgress(30); //default value will be 30
        //seekBar.setProgress(600);
        //seekBar.setProgress(1200);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
