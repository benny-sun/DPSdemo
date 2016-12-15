package com.example.haogood.dpsdemo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    Button fragBtnLogout;
    View view;

    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();

        view = inflater.inflate(R.layout.fragment_logout, container, false);
        fragBtnLogout = (Button)view.findViewById(R.id.fragBtnLogout);
        fragBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
            }
        });
        return view;

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
}
