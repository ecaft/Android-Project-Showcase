package com.example.pdarb.android_project_showcase;

import android.app.Application;

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
    public static final String CONTACT_NAME = "cname";
    public static final String CONTACT_EMAIL = "email";
    public static final String CONTACT_MAJOR = "major";
    public static final String CONTACT_GRADYEAR = "gradyear";
    public static final String CONTACT_TEAM = "cteam";
    public static final String CONTACT_TYPE = "ctype";

    private static DatabaseReference databaseReference;
    private static FirebaseStorage storage;
    private static StorageReference storageRef;
    private static ArrayList<FirebaseProject> projects;
    private static HashMap<String, ArrayList<String>> members;
    private static HashMap<String, FirebaseContacts> contacts;


    public void onCreate() {
        super.onCreate();
        // Get database/storage reference and initialize everything
        projects = new ArrayList<>();
        members = new HashMap<String, ArrayList<String>>();
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("teams");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseProject p = dataSnapshot.getValue(FirebaseProject.class);
                members.put(p.name, new ArrayList<String>());
                projects.add(p);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FirebaseProject p = dataSnapshot.getValue(FirebaseProject.class);
                for(int i=0; i<projects.size(); i++){
                    if(projects.get(i).name==p.name) {
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
                    if(projects.get(i).name==p.name) {
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
                members.get(c.team).add(c.name);
                contacts.put(c.name, c);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FirebaseContacts c = dataSnapshot.getValue(FirebaseContacts.class);
                for(String p: members.keySet()){
                    ArrayList contact = members.get(p);
                    contact.remove(c.name);
                    if(p.equals(c.team))
                        contact.add(c.name);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                FirebaseContacts c = dataSnapshot.getValue(FirebaseContacts.class);
                for(String p: members.keySet()){
                    members.get(p).remove(c.name);
                }
                contacts.remove(c.name);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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

    public static ArrayList<FirebaseContacts> getContactsForProject(FirebaseProject p){
        ArrayList<String> contact = members.get(p.name);
        ArrayList<FirebaseContacts> c = new ArrayList<FirebaseContacts>();
        for(String s: contact){
            c.add(contacts.get(s));
        }
        return c;
    }
}
