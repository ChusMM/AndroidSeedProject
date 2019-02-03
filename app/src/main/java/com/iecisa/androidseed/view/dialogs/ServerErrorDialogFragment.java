package com.iecisa.androidseed.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.iecisa.androidseed.R;

public class ServerErrorDialogFragment extends DialogFragment {
    private String mMsg;

    public static ServerErrorDialogFragment newInstance() {
        return new ServerErrorDialogFragment();
    }

    public static ServerErrorDialogFragment newInstance(String rationalMsg) {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        serverErrorDialogFragment.setArgs(rationalMsg);

        return serverErrorDialogFragment;
    }

    public ServerErrorDialogFragment() { }

    private void setArgs(String rationalMsg) {
        this.mMsg = rationalMsg;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(R.string.server_error_dialog_title);

        alertDialogBuilder.setMessage(this.mMsg);

        alertDialogBuilder.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }
        );

        return alertDialogBuilder.create();
    }
}
