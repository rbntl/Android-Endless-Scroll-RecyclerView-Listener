package com.example.endlessscrollrecyclerviewlistener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Code-ID on 10/11/2016.
 */

public class RecyclerViewEndlessScrollListener extends RecyclerView.OnScrollListener implements RecyclerViewEndlessScrollListenerCallback
{
    public interface EndlessScrollAdapter {
        void loadPage(int page, RecyclerViewEndlessScrollListenerCallback callback);
    }

    private boolean mLoading = true;
    private EndlessScrollAdapter mAdapter;
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private int mPage = 0;

    public RecyclerViewEndlessScrollListener(EndlessScrollAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager layoutManager = null ;
        try {
            layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        }catch (Exception e) {
            throw new TypeNotPresentException("Cannot Convert To LinearLayoutManager", e);
        }

        if (dy > 0)
        {
            visibleItemCount = layoutManager.getChildCount();
            totalItemCount = layoutManager.getItemCount();
            pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

            if (mLoading)
            {
                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount)
                {
                    mLoading = false;
                    mAdapter.loadPage(++mPage, this);
                }
            }
        }
    }

    @Override
    public void finish(boolean continueNextLoading) {
        if (continueNextLoading) mLoading = true;
    }
}
