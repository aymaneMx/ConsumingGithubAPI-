package lmax.com.cga;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

        downloadData();

        mAdapter = new RepoAdapter(getContext(), 0, streamData);
        gridView.setAdapter(mAdapter);

        return  rootView;
    }

    private void downloadData() {
        if (!mIsDownloadInProgress) {
            mIsDownloadInProgress = true;

            mProgressBar.setVisibility(View.VISIBLE);

            ApiClient.GetDataService service = ApiClient.getRetrofitInstance().create(ApiClient.GetDataService.class);
            Call<ListRepoData> call = service.getRepos( "created:>2017-10-22", "stars", "desc" );

            call.enqueue(new Callback<ListRepoData>() {
                @Override
                public void onResponse(Call<ListRepoData> call, Response<ListRepoData> response) {
                    consumeApiData(response.body().getItems());
                }

                @Override
                public void onFailure(Call<ListRepoData> call, Throwable t) {
                    consumeApiData(null);

                    Toast.makeText(getContext(), "Conversion issue", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void consumeApiData(List<Item> repos) {
        if (repos != null) {
            // Add the found streams to our array to render
            streamData.addAll(repos);

            // Tell the adapter that it needs to rerender
            mAdapter.notifyDataSetChanged();

            // Done loading; remove loading indicator
            mProgressBar.setVisibility( View.GONE);
        }

        mIsDownloadInProgress = false;
    }


}
