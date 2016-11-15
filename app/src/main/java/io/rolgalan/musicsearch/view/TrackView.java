package io.rolgalan.musicsearch.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rolgalan.musicsearch.R;
import io.rolgalan.musicsearch.model.Track;

/**
 * Currently, a {@link Track} is always displayed using the same fields,
 * formatting and views.
 * <p>
 * This class encapsulates all the data binding from a Track to its Views.
 * <p>
 * Created by Roldán Galán on 14/11/2016.
 */

public class TrackView {
    @BindView(R.id.list_album)
    TextView album;
    @BindView(R.id.list_artist)
    TextView artist;
    @BindView(R.id.list_date)
    TextView date;
    @BindView(R.id.list_genre)
    TextView genre;
    @BindView(R.id.list_length)
    TextView length;
    @BindView(R.id.list_price)
    TextView price;
    @BindView(R.id.list_title)
    TextView title;
    @BindView(R.id.list_image)
    ImageView image;
    @Nullable
    @BindView(R.id.button_share)
    Button shareButton;

    private Track mTrack;

    public TrackView(View view) {
        ButterKnife.bind(this, view);
    }

    public TrackView(Track track, View view) {
        this(view);
        setTrack(track);
    }

    public void setTrack(Track track) {
        this.mTrack = track;
    }

    public void updateViews() {
        album.setText(mTrack.getCollectionName());
        artist.setText(mTrack.getArtistName());
        date.setText(mTrack.getReleaseDateHuman());
        genre.setText(mTrack.getPrimaryGenreName());
        length.setText(mTrack.getTrackTime());
        price.setText(mTrack.getTrackPriceWithCurrency());
        title.setText(mTrack.getTrackName());
        if (shareButton != null) initButton();
    }

    private void initButton() {
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sharingText = "I'm listening " + mTrack.getTrackName() + " from " + mTrack.getArtistName() + ". Do you wanna use this awesome app to explore the iTunes API?";

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Enjoy listening music!");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharingText);

                shareButton.getContext().startActivity(Intent.createChooser(shareIntent, "Share"));
            }
        });
    }


    /**
     * A Track could be displayed in either small or big sizes, so
     * the placeholder used should be different size too.
     *
     * @param context A valid Context for image loading
     * @param small   If this param is true, a small placeholder is used.
     */
    public void loadImage(Context context, boolean small) {
        loadImage(context, small, image, mTrack);
    }

    public static void loadImage(Context context, boolean small, ImageView image, Track track) {
        String imgUrl = track.getArtworkUrl100();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(context).load(imgUrl)
                    .fitCenter()
                    .placeholder(small ? R.drawable.placeholder_64 : R.drawable.placeholder_256)
                    .crossFade()
                    .into(image);
        }
    }

    public View getTitleView() {
        return title;
    }

    public View getImageView() {
        return image;
    }
}
