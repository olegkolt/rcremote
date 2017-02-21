package home.oleg.rcremote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import home.oleg.rcremote.client.ClientActivity;
import home.oleg.rcremote.server.ServerActivity;

public class SelectModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        Button serverButton = (Button) findViewById(R.id.mode_server);

        serverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectModeActivity.this, ServerActivity.class);
                startActivity(intent);
            }
        });

        Button clientButton = (Button) findViewById(R.id.mode_client);

        clientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectModeActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });
    }
}
