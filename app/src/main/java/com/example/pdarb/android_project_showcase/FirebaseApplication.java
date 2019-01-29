package com.example.pdarb.android_project_showcase;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseApplication extends Application {
    public static final String PROJECT_NAME = "pname";
    public static final String PROJECT_INFO = "description";
    public static final String PROJECT_TYPE = "type";
    public static final String PROJECT_RESUME = "resume";

    private static DatabaseReference databaseReference;
    private static FirebaseStorage storage;
    private static StorageReference storageRef;
    private static ArrayList<FirebaseProject> projects;
    private static HashMap<String, ArrayList<String>> members;
    private static HashMap<String, ArrayList<FirebaseContacts>> contacts;


    public void onCreate() {
        super.onCreate();
        // Get database/storage reference and initialize everything

        Log.d("firebase", "entered Application onCreate");

        projects = new ArrayList<>();
        members = new HashMap<String, ArrayList<String>>();
        contacts = new HashMap<String, ArrayList<FirebaseContacts>>();

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("teams");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseProject p = dataSnapshot.getValue(FirebaseProject.class);
                members.put(p.teamName, new ArrayList<String>());
                projects.add(p);
                Log.d("firebase", "adding children"+projects);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FirebaseProject p = dataSnapshot.getValue(FirebaseProject.class);
                for(int i=0; i<projects.size(); i++){
                    if(projects.get(i).teamName==p.teamName) {
                        projects.remove(i);
                        break;
                    }
                }
                projects.add(p);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                FirebaseProject p = dataSnapshot.getValue(FirebaseProject.class);
                for(int i=0; i<projects.size(); i++){
                    if(projects.get(i).teamName==p.teamName) {
                        projects.remove(i);
                        return;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("contacts");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                FirebaseContacts c = dataSnapshot.getValue(FirebaseContacts.class);
                ArrayList<FirebaseContacts> list = contacts.get(c.teamName);
                if(list == null) {
                    list = new ArrayList<FirebaseContacts>();
                }
                list.add(c);
                contacts.put(c.teamName,list);
                Log.d("firebase", "adding contacts"+projects);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FirebaseContacts c = dataSnapshot.getValue(FirebaseContacts.class);
                for(String p: contacts.keySet()){
                    ArrayList contact = contacts.get(p);
                    contact.remove(c.contactName);
                    if(p.equals(c.teamName))
                        contact.add(c.contactName);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                FirebaseContacts c = dataSnapshot.getValue(FirebaseContacts.class);
                for(String p: contacts.keySet()){
                    contacts.get(p).remove(c.contactName);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*for(String s: contacts.keySet()){
            FirebaseContacts c = contacts.get(s);
            ArrayList<String> mems = members.get(c.teamName);
            if(mems!=null) mems.add(s);
        }*/



        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl
                ("gs://project-showcase-28cc7.appspot.com");
    }

    public static ArrayList<FirebaseProject> getProjects() {
        return projects;
    }

    public static DatabaseReference getFirebaseDatabase() {
        return databaseReference;
    }

    public static StorageReference getStorageRef() {
        return storageRef;
    }

    public static ArrayList<FirebaseContacts> getContactsForProject(String p){
        return contacts.get(p);
    }

    public static FirebaseProject getProjectFromName(String name){
        FirebaseProject proj = null;
        for(FirebaseProject p: projects){
            if(p.teamName.equals(name)){
                proj = p;
                break;
            }
        }
        return proj;
    }
}