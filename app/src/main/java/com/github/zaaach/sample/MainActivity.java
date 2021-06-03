package com.github.zaaach.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.github.zaaach.sample.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable, 1000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            binding.percentageView.setPercentage(new Random().nextInt(101) / 100f);
            handler.postDelayed(runnable, 1000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}