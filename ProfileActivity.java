package com.example.gm.donate;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    String[] a={"Donate","Collect"};
    private FirebaseAuth  firebaseAuth;
    private TextView textViewusermail;
    int i1;
    private Button buttonlogout;
    //private Switch aSwitch;
    private FirebaseUser firebaseUser;
   private FirebaseDatabase firebaseDatabase;
    private Button choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Main3Activity.class));
        }

        //firebaseDatabase.getApp();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid=user.getUid();


        //firebaseDatabase=FirebaseDatabase.getInstance().getReference(SyncStateContract.Constants.CONTENT_DIRECTORY).child();


        //textViewusermail = (TextView) findViewById(R.id.textViewusermail);
        //textViewusermail.setText(" Welcome " + user.getEmail() + "What would ypu like to do");
        buttonlogout = (Button) findViewById(R.id.buttonlogout);

        buttonlogout.setOnClickListener(this);

        spinner =(Spinner)findViewById(R.id.spinner1);
        choose=(Button)findViewById(R.id.button3);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,a);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               i1 = spinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

            choose.setOnClickListener(new View.OnClickListener() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                public void onClick(View view) {
                    final Intent intent;
                    switch (i1) {
                        case 0:
                            //if (user==null) {

                                intent = new Intent(ProfileActivity.this, MapsActivity.class);
                                startActivity(intent);
                                break;



                        case 1:
                            intent = new Intent(ProfileActivity.this, collect.class);
                            startActivity(intent);
                            break;
                    }
                }

            });




        /*aSwitch=(Switch)findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if(b){
                    Intent myintent=new Intent(ProfileActivity.this,blog_Activity.class);
                    startActivity(myintent );
                }
                else{

                }
            }
        });

    */
    }

    private void newUserScore(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        final DatabaseReference UserId = databaseReference.child(firebaseAuth.getCurrentUser().getUid());

        UserId.child("Score").setValue(30);
        //Toast.makeText(ProfileActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
        Intent main2 = new Intent(ProfileActivity.this,blog_Activity.class);
        main2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main2);

    }






    public void onClick(View view){
        if(view==buttonlogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
