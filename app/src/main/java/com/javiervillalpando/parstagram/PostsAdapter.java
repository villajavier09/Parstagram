package com.javiervillalpando.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_model,parent,false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    public PostsAdapter(Context context, List posts){
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView postUsername;
        private ImageView postImage;
        private TextView postDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postUsername = itemView.findViewById(R.id.postUsername);
            postImage = itemView.findViewById(R.id.postImage);
            postDescription = itemView.findViewById(R.id.postDescription);
        }
        public void bind(Post post){
            postUsername.setText(post.getUser().getUsername());
            postDescription.setText(post.getDescription());
            ParseFile image = post.getImage();
            if (image != null){
                Glide.with(context).load(image.getUrl()).into(postImage);
            }
        }
    }
}
