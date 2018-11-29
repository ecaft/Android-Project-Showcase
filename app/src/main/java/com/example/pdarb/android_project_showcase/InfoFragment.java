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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    private TextView ProjectName;
    private TextView ProjectDesc;
    private TextView ContactsHeader;
    private TextView Contact1Name;
    private TextView Contact1MajorGradyear;
    private TextView Contact1Email;
    private TextView Contact2Name;
    private TextView Contact2MajorGradyear;
    private TextView Contact2Email;
    private TextView Contact3Name;
    private TextView Contact3MajorGradyear;
    private TextView Contact3Email;

    private String projname;
    private String contactname;
    private String email;
    private String major;
    private String gradyear;
    private String desc;
    private String type;
    private String contactteam;
    private String contactteamtype;
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
        contactname = args.getString(FirebaseApplication.CONTACT_NAME);
        email = args.getString(FirebaseApplication.CONTACT_EMAIL);
        major = args.getString(FirebaseApplication.CONTACT_MAJOR);
        gradyear = args.getString(FirebaseApplication.CONTACT_GRADYEAR);
        contactteam = args.getString(FirebaseApplication.CONTACT_TEAM);
        contactteamtype = args.getString(FirebaseApplication.CONTACT_TYPE);


        if (desc==null||desc.isEmpty()){
            desc = "Check the project's website to learn more.";
        }

        ProjectName = (TextView) v.findViewById(R.id.project_details_name);
        ProjectName.setText(projname);

        ProjectDesc = (TextView) v.findViewById(R.id.project_details_information);
        ProjectDesc.setText(desc);


        return v;
    }
}
