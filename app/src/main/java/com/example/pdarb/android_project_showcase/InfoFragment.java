package com.example.pdarb.android_project_showcase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.GridView;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.storage.StorageReference;

import android.content.Intent;
import android.provider.MediaStore;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import android.graphics.drawable.Drawable;

import android.support.v4.app.FragmentManager;


public class InfoFragment extends Fragment {

    private TextView ProjectName;
    private TextView ProjectDesc;
    private TextView ContactsHeader;
    private static ArrayList<FirebaseContacts> contacts;
    private static ArrayList<FirebaseProject> projects;
    private static FirebaseProject current;
    private RecyclerView contactsRecyclerView;
    private ContactsAdapter contactsAdapter;
    private RecyclerView.LayoutManager contactsLayoutManager;

    private String projname;
    private String desc;
    private String type;
    private StorageReference storageRef = FirebaseApplication.getStorageRef();
    public InfoFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_info, container, false);
        Bundle args = getArguments();
        projname = args.getString(FirebaseApplication.PROJECT_NAME);
        desc = args.getString(FirebaseApplication.PROJECT_INFO);
        type = args.getString(FirebaseApplication.PROJECT_TYPE);

        ProjectName = (TextView) v.findViewById(R.id.project_details_name);
        ProjectName.setText(projname);

        ProjectDesc = (TextView) v.findViewById(R.id.project_details_information);
        ProjectDesc.setText(desc);

        contactsRecyclerView = (RecyclerView) v.findViewById(R.id.rvContacts);
        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<FirebaseContacts> test = new ArrayList<FirebaseContacts>();
        test.add(new FirebaseContacts("a","b","c","d","e","f"));
        test.add(new FirebaseContacts("1","2","3","4","5","6"));

        contacts = FirebaseApplication.getContactsForProject(projname);
        contactsAdapter = new ContactsAdapter(contacts);
        contactsRecyclerView.setAdapter(contactsAdapter);

        return v;
    }

    public static ArrayList<FirebaseContacts> getContacts() {
        return contacts;
    }

    public class ContactsHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView majorgradyearTextView;
        public TextView emailTextView;

        public ContactsHolder(View v) {
            super(v);
            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            majorgradyearTextView = (TextView) itemView.findViewById(R.id.contact_majorgradyear);
            emailTextView = (TextView) itemView.findViewById(R.id.contact_email);

        }
    }

    public class ContactsAdapter extends RecyclerView.Adapter<ContactsHolder> {


        public ArrayList<FirebaseContacts> mContacts;

        public ContactsAdapter(ArrayList<FirebaseContacts> contacts) {
            mContacts = contacts;
        }

        @Override
        public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contacts_view, parent, false);
            ContactsHolder c = new ContactsHolder(v);
            return c;
        }

        @Override
        public void onBindViewHolder(ContactsHolder viewHolder, int position) {
            FirebaseContacts contact = mContacts.get(position);

            viewHolder.nameTextView.setText(contact.getName());
            viewHolder.majorgradyearTextView.setText(contact.getMajor()+" "+contact.getGradyear());
            viewHolder.emailTextView.setText(contact.getEmail());
        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }
    }
}
