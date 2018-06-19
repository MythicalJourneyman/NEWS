package com.mythicaljourneyman.news.views.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mythicaljourneyman.news.R;
import com.mythicaljourneyman.news.databinding.ActivityNewsitemListBinding;
import com.mythicaljourneyman.news.model.ApiModel;
import com.mythicaljourneyman.news.objects.NewsItem;
import com.mythicaljourneyman.news.objects.NewsResults;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * An activity representing a list of NewsItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NewsItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NewsItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    ActivityNewsitemListBinding mBinding;
    private SimpleItemRecyclerViewAdapter mAdapter;
    private int mPage = 1, mLimit = 20;
    private RecyclerView mRecyclerView;
    private RecyclerView.OnScrollListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_newsitem_list);

        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setTitle(getTitle());

        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.newsitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.newsitem_list);
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);
        loadNews();
    }

    private void loadNews() {
        mRecyclerView.clearOnScrollListeners();
        ApiModel.getTopHeadlinesByCountry("us", mPage, mLimit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsResults>() {
                    @Override
                    public void accept(NewsResults newsResults) throws Exception {
                        if (newsResults.getStatus().equalsIgnoreCase("ok")) {
                            if (newsResults.getArticles().size() < mLimit) {
                                mRecyclerView.clearOnScrollListeners();
                            } else {
                                mPage++;
                                mRecyclerView.addOnScrollListener(mListener);
                            }
                            mAdapter.addItems(newsResults.getArticles());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        final LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    if (manager.findLastVisibleItemPosition() == mPage - 1) {
                        loadNews();
                    }
                }
            }
        };
        recyclerView.setLayoutManager(manager);
        mAdapter = new SimpleItemRecyclerViewAdapter(this, new ArrayList<NewsItem>(), mTwoPane);
        recyclerView.setAdapter(mAdapter);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final NewsItemListActivity mParentActivity;
        private final List<NewsItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsItem item = (NewsItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(NewsItemDetailFragment.ARG_ITEM_ID, item);
                    NewsItemDetailFragment fragment = new NewsItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.newsitem_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, NewsItemDetailActivity.class);
                    intent.putExtra(NewsItemDetailFragment.ARG_ITEM_ID, item);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(NewsItemListActivity parent,
                                      List<NewsItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.newsitem_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            NewsItem newsItem = mValues.get(position);
            holder.mIdView.setText(newsItem.getTitle());
//            holder.mContentView.setText(newsItem.getDescription());

            holder.itemView.setTag(newsItem);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void addItems(ArrayList<NewsItem> newsItems) {
            mValues.addAll(newsItems);
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
