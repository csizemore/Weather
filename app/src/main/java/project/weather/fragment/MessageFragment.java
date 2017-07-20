package project.weather.fragment;

/**
 * Created by Crystal on 11/30/2016.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import project.weather.MainActivity;
import project.weather.R;

public class MessageFragment extends DialogFragment {

    private OnMessageFragmentAnswer onMessageFragmentAnswer = null;
    private String cityName = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnMessageFragmentAnswer) {
            onMessageFragmentAnswer = (OnMessageFragmentAnswer) context;
        } else {
            throw new RuntimeException(
                    "This Activity is not implementing the " +
                            "OnMessageFragmentAnswer interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MainActivity.KEY_MSG);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogLayout = inflater.inflate(R.layout.layout_dialog,null);
        final EditText etName = (EditText) dialogLayout.findViewById(R.id.etName);
        alertDialogBuilder.setView(dialogLayout);


        alertDialogBuilder.setTitle("Enter City:");
        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cityName = etName.getText().toString();
                dialogInterface.dismiss();
                onMessageFragmentAnswer.onPositiveSelected(cityName);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onMessageFragmentAnswer.onNegativeSelected();
            }
        });


        return alertDialogBuilder.create();
    }
}