package i2p.bote.android.addressbook;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import i2p.bote.android.R;
import i2p.bote.android.util.BoteHelper;
import i2p.bote.packet.dht.Contact;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mCtx;
    private List<Contact> mContacts;
    private AddressBookFragment.OnContactSelectedListener mListener;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView mPicture;
        TextView mName;

        ContactViewHolder(View itemView) {
            super(itemView);
            mPicture = itemView.findViewById(R.id.contact_picture);
            mName = itemView.findViewById(R.id.contact_name);
        }
    }

    ContactAdapter(Context context, AddressBookFragment.OnContactSelectedListener listener) {
        mCtx = context;
        mListener = listener;
        setHasStableIds(true);
    }

    void setContacts(SortedSet<Contact> contacts) {
        if (contacts != null) {
            mContacts = new ArrayList<>();
            mContacts.addAll(contacts);
        } else
            mContacts = null;

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mContacts == null || mContacts.isEmpty())
            return R.layout.listitem_empty;

        return R.layout.listitem_contact;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        if (viewType == R.layout.listitem_contact) {
            return new ContactViewHolder(v);
        }
        return new SimpleViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.layout.listitem_empty:
                ((TextView) holder.itemView).setText(
                        mCtx.getResources().getString(R.string.address_book_empty));
                break;

            case R.layout.listitem_contact:
                final ContactViewHolder cvh = (ContactViewHolder) holder;
                Contact contact = mContacts.get(position);

                String pic = contact.getPictureBase64();
                if (pic != null && !pic.isEmpty())
                    cvh.mPicture.setImageBitmap(BoteHelper.decodePicture(pic));
                else {
                    ViewGroup.LayoutParams lp = cvh.mPicture.getLayoutParams();
                    cvh.mPicture.setImageBitmap(BoteHelper.getIdenticonForAddress(contact.getBase64Dest(), lp.width, lp.height));
                }

                cvh.mName.setText(contact.getName());

                cvh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onContactSelected(mContacts.get(cvh.getAdapterPosition()));
                    }
                });
                break;

            default:
                break;
        }
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mContacts == null || mContacts.isEmpty())
            return 1;

        return mContacts.size();
    }

    public long getItemId(int position) {
        if (mContacts == null || mContacts.isEmpty())
            return 0;

        Contact contact = mContacts.get(position);
        return contact.getDestination().getHash().hashCode();
    }
}
