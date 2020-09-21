package com.example.admin.ccb.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import www.ccb.com.common.base.BaseActivity;
import com.example.admin.ccb.utils.ResDatas;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * 列表式视频播放
 */
public class VideoPlayerListActivity extends BaseActivity {

    @Override
    public int getContentViewResource() {
        return R.layout.activity_video_player;
    }

    private RecyclerView rvPlayer;

    @Override
    protected void initView() {
        rvPlayer = findViewById(R.id.rvPlayer);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvPlayer.setLayoutManager(llm);
    }

    @Override
    protected void initData() {
        Jzvd.SAVE_PROGRESS = false; //视频播放器不保存播放进度
        rvPlayer.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_player, ResDatas.getVideoDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                JzvdStd jzvdStd = helper.getView(R.id.videoplayer);
//                jzvdStd.setUp( item,"", Jzvd.SCREEN_WINDOW_LIST,new JZMediaIjk(jzvdStd));
                jzvdStd.setUp( item,"", Jzvd.SCREEN_NORMAL);
                Glide.with(mContext)
                        .load(item)
                        .into(jzvdStd.thumbImageView);
                jzvdStd.positionInList = helper.getAdapterPosition();
            }
        });
    }

    @Override
    protected void initList() {
        rvPlayer.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JzvdStd jzvdStd = view.findViewById(R.id.videoplayer);
                if (jzvdStd != null && JzvdStd.STATE_PLAYING == jzvdStd.state) {
                    JzvdStd.releaseAllVideos();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
