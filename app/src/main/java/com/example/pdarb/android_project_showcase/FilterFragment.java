package com.example.pdarb.android_project_showcase;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

//    private RecyclerView filter;
//    private FilterAdapter filterAdapter;
//    private RecyclerView.LayoutManager filterLayoutManager;

    private ExpandableListView expandableListView;
    private FilterAdapter adapter;

    static String[] major = {"All Majors",
            "Aerospace Engineering",
            "Atmospheric Science",
            "Biological Engineering",
            "Biomedical Engineering",
            "Biological and Environmental",
            "Chemical Engineering",
            "Civil Engineering",
            "Computer Science",
            "Electrical and Computer Engineering",
            "Engineering Management",
            "Engineering Physics",
            "Environmental Engineering",
            "Information Science",
            "Materials Science and Engineering",
            "Mechanical Engineering",
            "Operations Research and Information Engineering",
            "Systems Engineering"};


    private List<String> labels;
    private HashMap<String, List<String>> opts;

    static boolean[] majorFilters = BrowseFragment.prevMajorFilters;
    static List<String> selected;


    public FilterFragment() {
        labels = new ArrayList<String>();
        labels.add("Majors");

        opts = new HashMap<String, List<String>>();
        List<String> majors = new ArrayList<String>();
        for(String s: major){
            majors.add(s);
        }

        opts.put("Majors", majors);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_filter, container, false);
        setHasOptionsMenu(true);

        expandableListView = (ExpandableListView) v.findViewById(R.id.expandable_list_view);
        adapter = new FilterAdapter(getContext(),labels,opts);
        expandableListView.setAdapter(adapter);

        getActivity().setTitle("Filter Projects");

        return v;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
        MenuItem submit = menu.findItem(R.id.submit);
        submit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                selected = new ArrayList<String>();
                if(!majorFilters[0]){
                    for(int i=0; i<majorFilters.length; i++){
                        if(majorFilters[i])
                            selected.add(major[i]);
                    }
                }
                BrowseFragment.prevMajorFilters = majorFilters;
                BrowseFragment.chosen = selected;

                getFragmentManager().popBackStack();
                return true;
            }
        });
        MenuItem cancel = menu.findItem(R.id.clear);
        cancel.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getFragmentManager().popBackStack();
                return true;
            }
        });
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

//    private class FilterHolder extends RecyclerView.ViewHolder{
//        public LinearLayout relLayout;
//        public TextView major;
//        //public CheckBox checkbox;
//        public int index;
//        public FilterHolder(View v){
//            super(v);
//            relLayout = v.findViewById(R.id.filter_item);
//            relLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    relLayout.setBackgroundColor(Color.GRAY);
//                }
//            });
//            major = v.findViewById(R.id.major);
//            //checkbox = v.findViewById(R.id.check_major);
//        }
//
//    }

    private class FilterAdapter extends BaseExpandableListAdapter{
        private Context context;
        private List<String> headers;
        private HashMap<String, List<String>> filterOpts;

        public FilterAdapter(Context c, List<String> head, HashMap<String,List<String>> opt){
            context = c;
            headers = head;
            filterOpts = opt;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return filterOpts.get(headers.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = filterOpts.get(headers.get(groupPosition)).get(childPosition);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.filter_item, null);
            }

            CheckBox check = (CheckBox) convertView.findViewById(R.id.item_check_box);
            TextView name = (TextView) convertView.findViewById(R.id.filterName);
            check.setChecked(majorFilters[childPosition]);
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    majorFilters[childPosition] = b;
                    if (b && (!childText.contains("All Majors"))){
                        majorFilters[0] = false;
                        notifyDataSetChanged();
                    }
                    else if(b && childText.contains("All Majors")){
                        for(int i=1; i<majorFilters.length; i++)
                            majorFilters[i] = false;
                        notifyDataSetChanged();
                    }
                }
            });

            name.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return filterOpts.get(headers.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return headers.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return headers.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = headers.get(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.filter_group, null);
            }

            TextView header = (TextView) convertView
                    .findViewById(R.id.filterHeader);
            header.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
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
}
