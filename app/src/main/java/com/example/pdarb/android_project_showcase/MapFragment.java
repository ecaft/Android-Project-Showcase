package com.example.pdarb.android_project_showcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class MapFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

//    for zooming
//    private ScaleGestureDetector scaleGestureDetector;
//    private float scale = 1.0f;
//    private ImageView map;

    private SubsamplingScaleImageView map;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
//        v.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("zoom", "touched"+motionEvent);
//                scaleGestureDetector.onTouchEvent(motionEvent);
//                return true;
//            }
//        });
//        map = (ImageView) v.findViewById(R.id.map);
//        scaleGestureDetector = new ScaleGestureDetector(getActivity(), new ScaleListener());


        map = (SubsamplingScaleImageView) v.findViewById(R.id.map);
        map.setImage(ImageSource.resource(R.drawable.fall_map));
        return v;
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
//
//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
//            scale *= scaleGestureDetector.getScaleFactor();
//            scale = Math.max(0.1f, Math.min(scale, 10.0f));
//            map.setScaleX(scale);
//            map.setScaleY(scale);
//            return true;
//        }
//    }
}
