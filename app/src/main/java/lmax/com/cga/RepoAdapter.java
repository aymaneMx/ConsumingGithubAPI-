package lmax.com.cga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import lmax.com.cga.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RepoAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;

    public RepoAdapter(Context context, int textViewResourceId, List<Item> objects) {
        super(context, textViewResourceId, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder;

        if (view == null) {
            // View doesn't exist so create it and create the holder
            view = mInflater.inflate(R.layout.grid_item, parent, false);

            holder = new Holder();
            holder.repoName         = view.findViewById(R.id.repoName);
            holder.repoDescription  = view.findViewById(R.id.repoDescription);
            holder.repoOwnerAvatar  = view.findViewById(R.id.repoOwnerAvatar);
            holder.repoOwnerName    = view.findViewById(R.id.repoOwnerName);
            holder.repoStarCount    = view.findViewById(R.id.repoStarCount);

            view.setTag(holder);
        } else {
            // Just get our existing holder
            holder = (Holder) view.getTag();
        }

        // Populate via the holder for speed
        Item repo = getItem(position);

        // Populate the item contents
        holder.repoName.setText(repo.getName());
        holder.repoDescription.setText(repo.getDescription());
        holder.repoOwnerName.setText( repo.getOwner().getLogin() );
        holder.repoStarCount.setText(Outils.format( (long) repo.getStargazersCount() ));

        // Load the screen cap image on a background thread
        Picasso.get()
                .load(repo.getOwner().getAvatarUrl())
//                .placeholder(R.drawable.user_placeholder)
//                .error(R.drawable.user_placeholder_error)
                .into(holder.repoOwnerAvatar);



        return view;
    }

    // Holder class used to efficiently recycle view positions
    private static final class Holder {
        public TextView repoName;
        public TextView repoDescription;
        public TextView repoOwnerName;
        public ImageView repoOwnerAvatar;
        public TextView repoStarCount;
    }
}
