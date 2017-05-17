package com.wooou.component.updateApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wooou.library.updateApp.UpdateAppService;
import com.wooou.library.updateApp.UpdateConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String updateUrl = "http://192.168.88.100:8080/WebService/api/app/checkVersion";

    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateButton = (Button)findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UpdateConfig.Builder builder = new UpdateConfig.Builder(updateUrl).withGrps().thread(5).retry(5).noticeBeforeRetry().xml();
        UpdateAppService.startCheck(this,builder.build());
    }
}
