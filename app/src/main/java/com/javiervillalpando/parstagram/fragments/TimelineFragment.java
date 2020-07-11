package com.javiervillalpando.parstagram.fragments;

import android.graphics.PostProcessor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javiervillalpando.parstagram.EndlessRecyclerViewScrollListener;
import com.javiervillalpando.parstagram.Post;
import com.javiervillalpando.parstagram.PostsAdapter;
import com.javiervillalpando.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {

    public static final String TAG = "Timeline Fragment";
    RecyclerView timelinePosts;
    private EndlessRecyclerViewScrollListener scrollListener;
    private SwipeRefreshLayout swipeContainer;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timelinePosts = view.findViewById(R.id.timelinePosts);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        timelinePosts.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        timelinePosts.setLayoutManager(linearLayoutManager);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        //Implements endless scrolling
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                queryPosts(page*10);
            }
        };
        timelinePosts.addOnScrollListener(scrollListener);
        queryPosts(0);
    }
    protected void queryPosts(int firstPost){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setSkip(firstPost);
        query.setLimit(10);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "issue loading timeline", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                // save received posts to list and notify adapter of new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }

        });
    }
    //Method for refreshing timeline
    public void fetchTimelineAsync(int page) {
        adapter.clear();
        queryPosts(0);
        swipeContainer.setRefreshing(false);
    }

}

