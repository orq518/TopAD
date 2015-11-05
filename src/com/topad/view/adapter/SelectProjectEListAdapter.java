package com.topad.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.topad.R;
import com.topad.bean.ChildBean;
import com.topad.bean.GroupBean;
import com.topad.view.customviews.CustomExpandableListView;

import java.util.ArrayList;

/**
 * ${todo}<甄选项目适配器>
 *
 * @author lht
 * @data: on 15/10/29 15:02
 */
public class SelectProjectEListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {
    private Context context;
    private ArrayList<GroupBean> groups;
    private CustomExpandableListView listView;

    public SelectProjectEListAdapter(Context context, ArrayList<GroupBean> groups,CustomExpandableListView listView ) {
        this.context = context;
        this.groups = groups;
        this.listView = listView;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChildItem(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getChildrenCount();
    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /** 設定 Group 資料 */
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupBean group = (GroupBean) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fargment_select_project_group_layout, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
        tv.setText(group.getTitle());

        // 重新產生 CheckBox 時，將存起來的 isChecked 狀態重新設定
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbGroup);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        if(groupPosition == 0){
            checkBox.setVisibility(View.VISIBLE);
            icon.setVisibility(View.GONE);
        }else{
            checkBox.setVisibility(View.GONE);
            icon.setVisibility(View.VISIBLE);
        }

        checkBox.setChecked(group.getChecked());

        // 點擊 CheckBox 時，將狀態存起來
        checkBox.setOnClickListener(new Group_CheckBox_Click());


        if(isExpanded){
            icon.setImageResource(R.drawable.select_project_up);
        }else{
            icon.setImageResource(R.drawable.select_project_down);
        }
        return convertView;
    }

    /** 勾選 Group CheckBox 時，存 Group CheckBox 的狀態，以及改變 Child CheckBox 的狀態 */
    class Group_CheckBox_Click implements View.OnClickListener {

        Group_CheckBox_Click() {
        }

        public void onClick(View v) {
            for(int i = 0; i < groups.size(); i++){
                groups.get(i).toggle();

                // 將 Children 的 isChecked 全面設成跟 Group 一樣
                int childrenCount = groups.get(i).getChildrenCount();
                boolean groupIsChecked = groups.get(i).getChecked();
                for (int j = 0; j < childrenCount; j++)
                    groups.get(i).getChildItem(j).setChecked(groupIsChecked);

                // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
                notifyDataSetChanged();
                listView.expandGroup(i);
            }

        }
    }

    /** 設定 Children 資料 */
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildBean child = groups.get(groupPosition).getChildItem(childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fargment_select_project_child_layout, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);
        tv.setText(child.getFullname());

        // 重新產生 CheckBox 時，將存起來的 isChecked 狀態重新設定
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbChild);
        checkBox.setChecked(child.getChecked());

        // 點擊 CheckBox 時，將狀態存起來
        checkBox.setOnClickListener(new Child_CheckBox_Click(groupPosition, childPosition));

        return convertView;
    }

    /**
     * 勾選 Child CheckBox 時，存 Child CheckBox 的狀態
     */
    class Child_CheckBox_Click implements View.OnClickListener {
        private int groupPosition;
        private int childPosition;

        Child_CheckBox_Click(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        public void onClick(View v) {
            handleClick(childPosition, groupPosition);
        }
    }

    public void handleClick(int childPosition, int groupPosition) {
        groups.get(groupPosition).getChildItem(childPosition).toggle();

        // 檢查 Child CheckBox 是否有全部勾選，以控制 Group CheckBox
        int childrenCount = groups.get(groupPosition).getChildrenCount();
        boolean childrenAllIsChecked = true;
        for (int i = 0; i < childrenCount; i++) {
            if (!groups.get(groupPosition).getChildItem(i).getChecked())
                childrenAllIsChecked = false;
        }

        groups.get(groupPosition).setChecked(childrenAllIsChecked);

        // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
        notifyDataSetChanged();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        handleClick(childPosition, groupPosition);
        return true;
    }

}

