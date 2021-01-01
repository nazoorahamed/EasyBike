package com.example.nazoorahamed.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.nazoorahamed.myapplication.SignUpActivity.*;

/**
 * Created by nazoorahamed on 3/13/18.
 */

public class profile extends android.app.Fragment {
    TextView name,mail,nic,number;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.txtname);
        mail = view.findViewById(R.id.txtprofilemail);
        nic = view.findViewById(R.id.txtNIC);
        number = view.findViewById(R.id.txtnumber);

        name.setText(txt_fullname);
        mail.setText(txt_mail);
        nic.setText(txt_Nic);
        number.setText(txt_phone_number);

        getActivity().setTitle("Profile");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile,container,false);
    }
}
