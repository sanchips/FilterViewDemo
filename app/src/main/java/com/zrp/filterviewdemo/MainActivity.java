package com.zrp.filterviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zrp.filterviewdemo.filter.ExpandTabView;
import com.zrp.filterviewdemo.filter.FilterTabView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ExpandTabView.OnFilterSelected {

    private ExpandTabView expandTabView;
    private ArrayList<String> nameList;//顶部tab条目列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandTabView = (ExpandTabView) findViewById(R.id.expand_tabview);
        expandTabView.setOnFilterSelected(this);

        nameList = new ArrayList<>();
        nameList.add("性别");
        nameList.add("地点");
        expandTabView.setNameList(nameList);
    }

    private ListView getGenderView() {
        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"不限", "男", "女"})));
        return listView;
    }

    private View getAreaView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.frag_area, null);
        return inflate;
    }

    @Override
    public void onSelected(FilterTabView tabView, int position, boolean singleCheck) {
        if (singleCheck) {
            if (position == 0) {
                expandTabView.setExpandView(getGenderView());
            } else {
                expandTabView.setExpandView(getAreaView());
            }
        }
    }
}
