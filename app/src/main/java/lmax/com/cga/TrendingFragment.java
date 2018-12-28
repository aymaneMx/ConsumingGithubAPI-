package lmax.com.cga;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import lmax.com.cga.model.Item;


public class TrendingFragment extends Fragment {

    private RepoAdapter mAdapter;

    private ProgressBar mProgressBar;

    private List<Item> streamData ;

    public TrendingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_trending, container, false );

        mProgressBar = rootView.findViewById(R.id.progressBar);

        // Create the array adapter and bind it to the GridView
        GridView gridView = rootView.findViewById(R.id.grid);

        streamData = new ArrayList<>();

        mAdapter = new RepoAdapter(getContext(), 0, streamData);
        gridView.setAdapter(mAdapter);

        return  rootView;
    }


}
