package com.javiervillalpando.parstagram.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.javiervillalpando.parstagram.Post;
import com.javiervillalpando.parstagram.R;
import com.javiervillalpando.parstagram.TimeFormatter;
import com.parse.ParseFile;

public class PostDetailsFragment extends Fragment {
    TextView postUsername;
    ImageView postImage;
    TextView postDescription;
    TextView postTimestamp;
    Post post;
    public PostDetailsFragment(Post post){
        this.post = post;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_details,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postUsername = getView().findViewById(R.id.postUsername);
        postImage = getView().findViewById(R.id.postImage);
        postDescription = getView().findViewById(R.id.postDescription);
        postTimestamp = getView().findViewById(R.id.timeStamp);

        postUsername.setText(post.getUser().getUsername());
        postDescription.setText(post.getDescription());
        postTimestamp.setText(TimeFormatter.getTimeDifference(post.getKeyTime()));

        ParseFile image = post.getImage();
        if (image != null){
            Glide.with(getContext()).load(image.getUrl()).into(postImage);
        }
    }
}
