package com.tiknil.boilerplate.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.tiknil.boilerplate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstActivity extends BaseActivity {

    @BindView(R.id.test)
    TextView test = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        ButterKnife.bind(this);

        test.setText("Hola");
    }
}
