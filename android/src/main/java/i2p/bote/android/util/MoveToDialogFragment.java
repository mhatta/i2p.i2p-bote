package i2p.bote.android.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import i2p.bote.I2PBote;
import i2p.bote.android.R;
import i2p.bote.folder.EmailFolder;

public class MoveToDialogFragment extends DialogFragment {
    private static final String CURRENT_FOLDER = "current_folder";

    public static MoveToDialogFragment newInstance(EmailFolder currentFolder) {
        MoveToDialogFragment f = new MoveToDialogFragment();
        Bundle args = new Bundle();
        args.putString(CURRENT_FOLDER, currentFolder.getName());
        f.setArguments(args);
        return f;
    }

    public interface MoveToDialogListener {
        void onFolderSelected(EmailFolder newFolder);
    }

    private MoveToDialogListener mListener;
    private List<EmailFolder> mFolders;
    private List<String> mFolderDisplayNames;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (MoveToDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MoveToDialogListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFolders = I2PBote.getInstance().getEmailFolders();
        mFolderDisplayNames = new ArrayList<>();

        assert getArguments() != null;
        String curFolder = getArguments().getString(CURRENT_FOLDER);
        Iterator<EmailFolder> i = mFolders.iterator();
        while (i.hasNext()) {
            EmailFolder folder = i.next();
            if (folder.getName().equals(curFolder) || BoteHelper.isOutbox(folder.getName()))
                i.remove();
            else
                mFolderDisplayNames.add(
                    BoteHelper.getFolderDisplayName(getActivity(), folder));
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.action_move_to)
        .setItems(mFolderDisplayNames.toArray(new String[0]),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onFolderSelected(mFolders.get(which));
            }
        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
