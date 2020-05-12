package junseong.k.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView finishBtn;
    TextView numberView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        numberView=findViewById(R.id.lab1_phone_number);
        finishBtn=findViewById(R.id.lab1_remove_icon);

        finishBtn.setOnClickListener(this);

        Intent intent=getIntent();
        String number=intent.getStringExtra("number");
        numberView.setText(number);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}