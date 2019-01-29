package com.example.pdarb.android_project_showcase;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView checklistRecyclerView;
    private RecyclerView.Adapter checklistAdapter;
    //private RecyclerView.LayoutManager checklistLayoutManager;
    private LinearLayoutManager checklistLayoutManager;
    private DividerItemDecoration divider;

    private boolean[] checked;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *  param1 Parameter 1.
     *  param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);


        getActivity().setTitle("Want to Visit");

        checklistRecyclerView = (RecyclerView) v.findViewById(R.id.checklist_recycler_view);
        checklistRecyclerView.setHasFixedSize(true);
        checklistLayoutManager = new LinearLayoutManager(getActivity());
        checklistRecyclerView.setLayoutManager(checklistLayoutManager);
        divider = new DividerItemDecoration(checklistRecyclerView.getContext(), 0);
        divider.setDrawable(getResources().getDrawable(R.drawable.checklist_background));

        String[] checklist = new String[3];
        checked = new boolean[3];
        for(int i=0; i<checklist.length; i++){
            checklist[i] = "test "+i;
            checked[i]=false;
        }

        checklistAdapter = new ChecklistAdapter(MainActivity.saved());
        checklistRecyclerView.setAdapter(checklistAdapter);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class ChecklistHolder extends RecyclerView.ViewHolder{
        public LinearLayout relLayout;
        public TextView name;
        //public ImageButton checkBox;
        public int index;
        public FirebaseProject project;
        public ChecklistHolder(View v){
            super(v);
            //relLayout = (LinearLayout) v.findViewById(R.id.project_card);
            name = (TextView) v.findViewById(R.id.check_text);
            name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        InfoFragment info = new InfoFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseApplication.PROJECT_NAME, project.teamName);
                        bundle.putString(FirebaseApplication.PROJECT_TYPE, project.teamType);
                        bundle.putString(FirebaseApplication.PROJECT_INFO, project.descrip);

                        info.setArguments(bundle);

                        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(((ViewGroup)(getView().getParent())).getId(), info);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
//            checkBox = (ImageButton) v.findViewById(R.id.check);
//            checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String pName = MainActivity.saved().get(index);
//                    if(MainActivity.isChecked(pName)){
//                        MainActivity.changeChecked(pName, 0);
//                        checkBox.setImageResource(R.drawable.ic_unchecked);
//                        name.setTextColor(0xFF222222);
//                    }
//                    else{
//                        MainActivity.changeChecked(pName, 1);
//                        checkBox.setImageResource(R.drawable.ic_checked);
//                        name.setTextColor(0x80222222);
//                    }
//                }
//            });
        }
    }

    private class ChecklistAdapter extends RecyclerView.Adapter<ChecklistHolder>{
        private List<String> names;
        public ChecklistAdapter(List<String> dataset){
            names = dataset;
        }

        @Override
        public ChecklistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checklist_card_view, parent, false);
            ChecklistHolder c = new ChecklistHolder(v);
            return c;
        }

        @Override
        public void onBindViewHolder(ChecklistHolder p, int position){
            String pName = names.get(position);
            p.name.setText(pName);
            p.index=position;
            p.project = FirebaseApplication.getProjectFromName(pName);
//            p.checkBox.setBackgroundDrawable(null);
//            if(MainActivity.isChecked(pName)){
//                p.checkBox.setImageResource(R.drawable.ic_checked);
//            }
//            else{
//                p.checkBox.setImageResource(R.drawable.ic_unchecked);
//            }
        }

        @Override
        public int getItemCount(){
            return names.size();
        }

    }
}
