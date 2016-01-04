package com.zrp.filterviewdemo.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zrp.filterviewdemo.R;

/**
 * 自定义的筛选器单个view，自处理选中效果，抛出点击事件处理
 * Created by ZRP on 2015/9/19.
 */
public class FilterTabView extends LinearLayout implements View.OnClickListener {
    private View view;
    private TextView text;
    private boolean isChecked;

    public FilterTabView(Context context) {
        super(context);
        init();
    }

    public FilterTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.custom_filter_tabview, this);
        inflate.setOnClickListener(this);
        view = inflate.findViewById(R.id.view);
        text = (TextView) inflate.findViewById(R.id.text);
    }

    public TextView getText() {
        return text;
    }

    public void setText(String txt) {
        text.setText(txt);
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        view.setSelected(isChecked);
    }

    public boolean isChecked() {
        return isChecked;
    }

    private OnClickListener clickListener;

    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View view) {
        if (clickListener != null) clickListener.onClick(this);
    }

    public interface OnClickListener {
        void onClick(FilterTabView tabView);
    }
}