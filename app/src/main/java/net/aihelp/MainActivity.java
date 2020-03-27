package net.aihelp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.aihelp.localization.AIHelpSDK;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AIHelpSDK aiHelpSDK = new AIHelpSDK();

//        final MyTest myTest = new MyTest(MainActivity.this);

        // Example of a call to a native method
        final TextView tv = findViewById(R.id.sample_text);
        tv.setText("stringFromJNI()");

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(aiHelpSDK.getStringFromJNI("101248"));

            }
        });

//        etKey.setText("4358E7FF309345CAE6A7ED601A663A81");
//        etHash.setText("4358E7FF309345CAE6A7ED601A663A81");
//        tvLanguage.setText("zh_cn");

        aiHelpSDK.initFromJNI(this, "4358E7FF309345CAE6A7ED601A663A81",
                "4358E7FF309345CAE6A7ED601A663A81", "zh_cn");

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}
