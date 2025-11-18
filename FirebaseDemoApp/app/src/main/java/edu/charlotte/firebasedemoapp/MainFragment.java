package edu.charlotte.firebasedemoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MainFragment extends Fragment {
    private FirebaseAuth mAuth;
    ArrayList<User> users = new ArrayList<>();
    ArrayAdapter<User> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main, new LoginFragment())
                        .commit();
            }
        });
        getData();
        // setData();
        ListView listView = view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);

        return view;
    }

    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                users.clear();
                                for (QueryDocumentSnapshot doc : value) {
                                    // users.add(new User(doc.getString("name"), doc.getString("cell")));
                                    User user = doc.toObject(User.class);
                                    users.add(user);

                                }
                                adapter.notifyDataSetChanged();
                            }
                        });

//        db.collection("users").get()
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot doc : task.getResult()) {
//                                users.add(new User(doc.getString("name"), doc.getString("cell")));
//                                Log.d("demo", "data id: " + doc.getId());
//                                Log.d("demo", "data: " + doc.getData());
//                            }
//                            adapter.notifyDataSetChanged();
//                        } else {
//                            Log.d("demo", "error: " + task.getException().getMessage());
//                        }
//                    }
//                });
    }

    private void setData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, Object> user = new HashMap<>();
        user.put("name", "testing!-user");
        user.put("cell", "222-222-2222");

        //adding
//        db.collection("users")
//                .add(user).addOnSuccessListener(getActivity(), new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//
//                    }
//                });

        // adding with specified id
//        db.collection("users")
//                .document("1234567890")
//                .set(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//
//                    }
//                });
        // updating
//        db.collection("users")
//                .document("1234567890")
//                .update(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//
//                    }
//                });
    }
}