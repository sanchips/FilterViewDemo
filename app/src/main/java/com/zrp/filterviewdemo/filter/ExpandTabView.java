package com.zrp.filterviewdemo.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zrp.filterviewdemo.R;

import java.util.ArrayList;

/**
 * 筛选器，自处理选中效果切换，抛出子view点击事件处理
 * Created by ZRP on 2015/9/19.
 */
public class ExpandTabView extends LinearLayout implements FilterTabView.OnClickListener {

    private FilterTabView selectedView;
    private LinearLayout tab_container, container, view_container;
    private ArrayList<FilterTabView> tabViews = new ArrayList<FilterTabView>();//存储动态创建的tab
    private int position = -1;//当前点击的view为list中的position

    public ExpandTabView(Context context) {
        super(context);
        init();
    }

    public ExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.custom_expand_tabview, this);
        tab_container = (LinearLayout) inflate.findViewById(R.id.tab_container);
        container = (LinearLayout) inflate.findViewById(R.id.container);
        view_container = (LinearLayout) inflate.findViewById(R.id.view_container);
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                closeExpand();// 清空所有选中效果，并重置各状态值
            }
        });
    }

    public void setNameList(ArrayList<String> nameList) {
        if (nameList == null) return;
        LinearLayout.LayoutParams tabParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        LinearLayout.LayoutParams lineParams = new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < nameList.size(); i++) {
            // 创建tab
            FilterTabView tabView = new FilterTabView(getContext());
            tabView.setText(nameList.get(i));
            tabView.setOnClickListener(this);
            tabViews.add(tabView);
            tabView.setLayoutParams(tabParams);
            tab_container.addView(tabView);
            // 创建line
            if (i < nameList.size() - 1) {
                View line = new View(getContext());
                line.setBackgroundColor(getResources().getColor(R.color.divider_color));
                line.setLayoutParams(lineParams);
                tab_container.addView(line);
            }
        }
    }

    /**
     * 添加expand展现布局
     *
     * @param expandView
     */
    public void setExpandView(View expandView) {
        if (expandView == null) return;
        container.setVisibility(View.VISIBLE);
        view_container.removeAllViews();
        view_container.addView(expandView);
    }

    /**
     * 清除expand展现布局并让其消失
     */
    private void clearExpandView() {
        container.setVisibility(View.GONE);
        view_container.removeAllViews();
    }

    /**
     * 外部调用，清除展现的布局，并取消所有tab的选中效果
     */
    public void closeExpand() {
        position = -1;
        selectedView = null;
        clearExpandView();
        if (tabViews == null || tabViews.size() == 0) return;
        for (int i = 0; i < tabViews.size(); i++) {
            tabViews.get(i).setChecked(false);
        }
    }

    @Override
    public void onClick(FilterTabView tabView) {
        FilterTabView[] views = tabViews.toArray(new FilterTabView[tabViews.size()]);
        // 选中的position
        for (int i = 0; i < views.length; i++) {
            if (tabView == views[i]) position = i;
        }
        if (selectedView == null) {
            tabView.setChecked(true);
            selectedView = tabView;
            if (listener != null) listener.onSelected(tabView, position, true);
        } else {
            if (selectedView == tabView) {
                selectedView.setChecked(false);
                clearExpandView();
                selectedView = null;
                if (listener != null) listener.onSelected(tabView, position, false);
            } else {
                for (int i = 0; i < views.length; i++) {
                    views[i].setChecked(views[i] == tabView);
                }
                clearExpandView();
                selectedView = tabView;
                if (listener != null) listener.onSelected(tabView, position, true);
            }
        }
    }

    private OnFilterSelected listener;

    /**
     * 设置顶部tab选中回调
     *
     * @param listener
     */
    public void setOnFilterSelected(OnFilterSelected listener) {
        this.listener = listener;
    }

    public interface OnFilterSelected {
        /**
         * tab选中回调
         *
         * @param tabView     选中的tab
         * @param position    选中tab的position
         * @param singleCheck 是否为单次选中，为true的时候调出选择view，为false的时候隐藏选择view
         */
        void onSelected(FilterTabView tabView, int position, boolean singleCheck);
    }
}