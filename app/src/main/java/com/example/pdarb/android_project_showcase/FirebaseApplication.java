package com.example.pdarb.android_project_showcase;

import android.app.Application;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

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
    private static ArrayList<FirebaseProject> contacts;

    public void onCreate() {
        super.onCreate();
        // Get database/storage reference and initialize everything
        projects = new ArrayList<>();
        contacts = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("teams");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try{
                        FirebaseProject fc = snapshot.getValue(FirebaseProject
                                .class);
                        projects.add(fc);
                    }
                    catch(DatabaseException e){

                    }

                }
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
}
