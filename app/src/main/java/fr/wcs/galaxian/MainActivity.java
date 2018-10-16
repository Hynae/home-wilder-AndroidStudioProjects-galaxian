package fr.wcs.galaxian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        Button btsend = findViewById(R.id.btSend);
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etpseudo = findViewById(R.id.etPseudo);
                EditText etscore = findViewById(R.id.etScore);
                String pseudo = etpseudo.getText().toString();
                String score = etscore.getText().toString();
                if (pseudo.isEmpty() || score.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    UserScoreModel userScoreModel = new UserScoreModel(pseudo, Integer.parseInt(score));
                    DatabaseReference userRef = database.getReference("userScore");
                    String key = userRef.push().getKey();
                    userRef.child(key).setValue(userScoreModel);
                    Toast.makeText(MainActivity.this, "Le joueur" + userScoreModel.getpseudo() + "a été ajouté", Toast.LENGTH_LONG).show();

                }
            }
        });

        Button btscores = findViewById(R.id.btscores);
        btscores.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseReference userRef = database.getReference("userScore");
                userRef.orderByValue() .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                            UserScoreModel userScore =
                                    studentSnapshot.getValue(UserScoreModel.class);
                            Toast.makeText(MainActivity.this, userScore.getpseudo() + " is the best player with " + userScore.getScore() + " points !" ,
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });


            }
        });
    }
}
