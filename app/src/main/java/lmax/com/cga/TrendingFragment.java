package lmax.com.cga;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lmax.com.cga.model.Item;
import lmax.com.cga.model.ListRepoData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrendingFragment extends Fragment {

    private RepoAdapter mAdapter;
    private ProgressBar mProgressBar;
    private boolean mIsDownloadInProgress = false;

    private static class ActivityState {
        private int nextPage = 1;
        private List<Item> streamData = new ArrayList<>();
    }

    /* Holds the state information for this activity. */
    private ActivityState mState = new ActivityState();


    public TrendingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate( R.layout.fragment_trending, container, false );

        mProgressBar = rootView.findViewById(R.id.progressBar);

        // Create the array adapter and bind it to the GridView
        GridView gridView = rootView.findViewById(R.id.grid);
        gridView.setOnScrollListener(mScrollListener);

        mAdapter = new RepoAdapter(getContext(), 0, mState.streamData);
        gridView.setAdapter(mAdapter);

        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Kick off first download
        if (mState.nextPage == 1) {
            downloadData(mState.nextPage);
        }
    }

    private void downloadData(final int pageNumber) {
        if (!mIsDownloadInProgress) {
            mIsDownloadInProgress = true;

            mProgressBar.setVisibility(View.VISIBLE);

            ApiClient.GetDataService service = ApiClient.getRetrofitInstance().create(ApiClient.GetDataService.class);
            Call<ListRepoData> call = service.getRepos( "created:>2017-10-22", "stars", "desc" , pageNumber );

            call.enqueue(new Callback<ListRepoData>() {
                @Override
                public void onResponse(Call<ListRepoData> call, Response<ListRepoData> response) {
                    consumeApiData(response.body().getItems());
                }

                @Override
                public void onFailure(Call<ListRepoData> call, Throwable t) {
                    consumeApiData(null);

                    Toast.makeText(getContext(), "check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void consumeApiData(List<Item> repos) {
        if (repos != null) {
            // Add the found streams to our array to render
            mState.streamData.addAll(repos);

            // Tell the adapter that it needs to rerender
            mAdapter.notifyDataSetChanged();

            // Done loading; remove loading indicator
            mProgressBar.setVisibility( View.GONE);

            // Keep track of what page to download next
            mState.nextPage++;
        }

        mIsDownloadInProgress = false;
    }


    /**
     * Scroll-handler for the ListView which can auto-load the next page of data.
     */
    private AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // Nothing to do
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // Detect if the ListView is running low on data
            if (totalItemCount > 0 && totalItemCount - (visibleItemCount + firstVisibleItem) <= 5) {
                downloadData(mState.nextPage);
            }
        }
    };


}
