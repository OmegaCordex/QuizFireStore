package com.example.quizfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Categories> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recMainId);
        firebaseFirestore = FirebaseFirestore.getInstance();
        arrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this, arrayList);

        recyclerView.setAdapter(mainAdapter);

        firebaseFirestore.collection("QUIZ").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()){

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d :list){

                                Categories categories = d.toObject(Categories.class);

                                arrayList.add(categories);
                            }

                            mainAdapter.notifyDataSetChanged();

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        mainAdapter.setOnItemClickListener(new MainAdapter.OnListItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(MainActivity.this, arrayList.get(position).getCatName(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(MainActivity.this, SetsActivity.class));
                
            }
        });




    }
}