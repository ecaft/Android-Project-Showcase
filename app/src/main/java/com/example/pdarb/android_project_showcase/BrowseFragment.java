package com.example.pdarb.android_project_showcase;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BrowseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BrowseFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseFragment extends Fragment implements FilterFragment.OnFragmentInteractionListener,
                                                        ProjectInfo.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView projectRecyclerView;
    private ProjectAdapter projectAdapter;
    private RecyclerView.LayoutManager projectLayoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FilterFragment filterFragment;

    private OnFragmentInteractionListener mListener;

    private ArrayList<ArrayList<String>> projects;

    private SearchView search;

    public BrowseFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_browse, container, false);
        setHasOptionsMenu(true);

        MultiStateToggleButton mstb = (MultiStateToggleButton) v.findViewById(R.id.mstb_multi_id);
        mstb.setValue(0);
        mstb.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                Log.d("MSTB", ""+value);
            }
        });

        projectRecyclerView = (RecyclerView) v.findViewById(R.id.project_recycler_view);
        projectRecyclerView.setHasFixedSize(true);
        projectLayoutManager = new LinearLayoutManager(getActivity());
        projectRecyclerView.setLayoutManager(projectLayoutManager);

        //temporary until we get the database
        projects = new ArrayList<ArrayList<String>>();

        for(int i=0; i<10; i++) {
            ArrayList<String> temp = new ArrayList<String>();
            temp.add("test" + i);
            temp.add("description" + i);
            projects.add(temp);
        }
        projectAdapter = new ProjectAdapter(projects);
        projectRecyclerView.setAdapter(projectAdapter);

        getActivity().setTitle("Showcase - MEng");

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.search_menu, menu);
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItem filterItem = menu.findItem(R.id.filter);

        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem){
                filterFragment = new FilterFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), filterFragment);
                transaction.addToBackStack(null);
                Log.d("Filter Page", "CALLING NEW FRAGMENT");
                transaction.commit();

                return true;
            }
        });

        search = (SearchView) searchItem.getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                projectAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                projectAdapter.filter(newText);
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    /*    int id = item.getItemId();

        if (id == R.id.filter) {
            Log.d("toolbar","filter");
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, filterFragment,"tag");
            transaction.addToBackStack(null);
            transaction.commit();
        }*/
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }

    private class ProjectHolder extends RecyclerView.ViewHolder{
        public LinearLayout relLayout;
        public TextView name;
        public TextView description;
        public ImageButton star;
        public int index;
        public ProjectHolder(View v){
            super(v);
            relLayout = (LinearLayout) v.findViewById(R.id.project_card);
            relLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProjectInfo info = new ProjectInfo();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(((ViewGroup)(getView().getParent())).getId(), info);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
            description = (TextView) v.findViewById(R.id.project_description);
            star = (ImageButton) v.findViewById(R.id.save_project);
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(MainActivity.isInFavorites(projects.get(index).get(0))){
                        MainActivity.deleteRow(projects.get(index).get(0));
                        star.setImageResource(R.drawable.ic_fav_empty);
                    }
                    else{
                        MainActivity.addRow(projects.get(index).get(0));
                        star.setImageResource(R.drawable.ic_fav);
                    }
                }
            });
            name = (TextView) v.findViewById(R.id.project_name);
        }
    }

    private class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder>{
        private ArrayList<ArrayList<String>> names;
        public ProjectAdapter(ArrayList<ArrayList<String>> dataset){
            names = dataset;
        }

        @Override
        public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.project_card_view, parent, false);
            ProjectHolder p = new ProjectHolder(v);
            return p;
        }

        @Override
        public void onBindViewHolder(ProjectHolder p, int position){
            p.name.setText(names.get(position).get(0));
            p.description.setText(names.get(position).get(1));
            p.star.setBackgroundDrawable(null);
            p.index=position;
            if(MainActivity.isInFavorites(projects.get(position).get(0))){
                p.star.setImageResource(R.drawable.ic_fav);
            }
            else{
                p.star.setImageResource(R.drawable.ic_fav_empty);
            }
        }

        @Override
        public int getItemCount(){
            return names.size();
        }

        public void filter(String text){
            ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
            if(text.equals("")){
                temp=projects;
            }
            else {
                for (ArrayList<String> a : names) {
                    if (a.get(0).contains(text)) {
                        temp.add(a);
                    }
                }
            }
            names=temp;
            notifyDataSetChanged();
        }

    }


}
