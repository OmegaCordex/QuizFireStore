package com.example.quizfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SetsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SetsAdapter setsAdapter;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Sets> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        recyclerView = findViewById(R.id.recSetsId);
        firebaseFirestore = FirebaseFirestore.getInstance();
        arrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setsAdapter = new SetsAdapter(this, arrayList);

        recyclerView.setAdapter(setsAdapter);

        firebaseFirestore.collection("QUIZ").document("MALARIA")
                .collection("SETS")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()){

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d :list){

                        Sets sets = d.toObject(Sets.class);

                        arrayList.add(sets);
                    }

                    setsAdapter.notifyDataSetChanged();

                }

            }
        });

        setsAdapter.setOnItemClickListener(new SetsAdapter.OnListItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(SetsActivity.this, arrayList.get(position).getSetName(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}