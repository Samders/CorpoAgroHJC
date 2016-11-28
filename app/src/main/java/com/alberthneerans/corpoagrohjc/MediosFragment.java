package com.alberthneerans.corpoagrohjc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MediosFragment extends Fragment {

    private Button bCitas;

    public MediosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_medios, container, false);
        bCitas = (Button)view.findViewById(R.id.bCitas);

        bCitas.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){

                goCitasActivity();

            }

        });

        return view;
    }




    public void goCitasActivity(){
        Intent  intent = new Intent(getActivity(), CitasActivity.class);
        startActivity(intent);

    }

}
