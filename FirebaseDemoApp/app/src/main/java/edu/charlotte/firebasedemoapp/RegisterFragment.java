package edu.charlotte.firebasedemoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        editTextTextEmailAddress = view.findViewById(R.id.editTextTextEmailAddress2);
        editTextTextPassword = view.findViewById(R.id.editTextTextPassword2);

        view.findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextTextEmailAddress.getText().toString();
                String pass = editTextTextPassword.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(getContext(), "Email is empty!", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(getContext(), "Password is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("demo", "User created: " + mAuth.getCurrentUser().getUid());
                                    } else {
                                        Log.d("demo", "Error: " + task.getException().getMessage());
                                    }
                                }
                            });
                }
            }
        });
        return view;
    }
}