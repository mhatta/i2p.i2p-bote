package i2p.bote.android.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class DeleteAddressDialogFragment extends DialogFragment {
    public interface DeleteAddressDialogListener {
        void onDeleteAddress();
    }

    private DeleteAddressDialogListener mListener;

    public static DialogFragment newInstance(int message) {
        DialogFragment f = new DeleteAddressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("message", message);
        f.setArguments(args);
        return f;
    }

    private void onAttachToParentFragment(Fragment fragment) {
        // Verify that the host fragment implements the callback interface
        try {
            // Instantiate the DeleteAddressDialogListener so we can send events to the host
            mListener = (DeleteAddressDialogListener) fragment;
        } catch (ClassCastException e) {
            // The fragment doesn't implement the interface, throw exception
            throw new ClassCastException(fragment.toString()
                    + " must implement DeleteAddressDialogListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment(getParentFragment());
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        assert getArguments() != null;
        builder.setMessage(getArguments().getInt("message"))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mListener.onDeleteAddress();
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
