package com.example.pdarb.android_project_showcase;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Button;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

    private TextView ProjectName;
    private TextView ProjectDesc;
    private TextView ContactsHeader;
    //private ImageButton star;
    private static ArrayList<FirebaseContacts> contacts;
    private static ArrayList<FirebaseProject> projects;
    private static FirebaseProject current;
    private RecyclerView contactsRecyclerView;
    private ContactsAdapter contactsAdapter;
    private RecyclerView.LayoutManager contactsLayoutManager;

    private String projname;
    private String desc;
    private String type;
    private String resumelink;

    private Button resume;

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
        resumelink = args.getString(FirebaseApplication.PROJECT_RESUME);
        setHasOptionsMenu(true);

        ProjectName = (TextView) v.findViewById(R.id.project_details_name);
        ProjectName.setText(projname);

        ProjectDesc = (TextView) v.findViewById(R.id.project_details_information);
        ProjectDesc.setText(desc);

        resume = (Button) v.findViewById(R.id.resume);
        resume.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Resume res = new Resume();

                Bundle bundle = new Bundle();
                bundle.putString("link", resumelink);

                Intent i = new Intent(getActivity(),Resume.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });


        contactsRecyclerView = (RecyclerView) v.findViewById(R.id.rvContacts);
        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        contacts = FirebaseApplication.getContactsForProject(projname);
        ArrayList<String> contactnames = new ArrayList<>();
        for(int i = 0; i<contacts.size();i++) {
            contactnames.add(contacts.get(i).email);
        }
        Log.d("contactsmap",""+contactnames);
        contactsAdapter = new ContactsAdapter(contacts);
        contactsRecyclerView.setAdapter(contactsAdapter);


        return v;
    }

    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.info_menu, menu);
        final MenuItem favoriteItem = menu.findItem(R.id.favorite);
        if(MainActivity.isInFavorites(projname)){
            favoriteItem.setIcon(R.drawable.ic_fav);
        }
        else{
            favoriteItem.setIcon(R.drawable.ic_fav_empty);
        }
        favoriteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem){
                String n = projname;
                if(MainActivity.isInFavorites(n)){
                    MainActivity.deleteRow(n);
                    favoriteItem.setIcon(R.drawable.ic_fav_empty);
                }
                else{
                    MainActivity.addRow(n);
                    favoriteItem.setIcon(R.drawable.ic_fav);
                }
                return true;
            }
        });
    }

    public static ArrayList<FirebaseContacts> getContacts() {
        return contacts;
    }

    public class ContactsHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView majorgradyearTextView;
        public TextView emailTextView;
        public TextView titleTextView;

        public ContactsHolder(View v) {
            super(v);
            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            majorgradyearTextView = (TextView) itemView.findViewById(R.id.contact_majorgradyear);
            emailTextView = (TextView) itemView.findViewById(R.id.contact_email);
            titleTextView = (TextView) itemView.findViewById(R.id.contact_title);
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
            viewHolder.majorgradyearTextView.setText(contact.getMajor()+" "+contact.getGradYear());
            viewHolder.emailTextView.setText(contact.getEmail());
            viewHolder.titleTextView.setText(contact.getTitle());
        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }
    }
}
