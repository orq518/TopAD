package com.topad.bean;

import java.util.ArrayList;

/**
 * ${todo}<请描述这个类是干什么的>
 *
 * @author lht
 * @data: on 15/10/29 15:05
 */
public class GroupBean {
    private String title;
    private ArrayList<ChildBean> children;
    private boolean isChecked;

        public GroupBean( String title) {
            this.title = title;
            children = new ArrayList<ChildBean>();
            this.isChecked = false;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

        public void toggle() {
            this.isChecked = !this.isChecked;
        }

        public boolean getChecked() {
            return this.isChecked;
        }

        public String getTitle() {
            return title;
        }

        public void addChildrenItem(ChildBean child) {
            children.add(child);
        }

        public int getChildrenCount() {
            return children.size();
        }

        public ChildBean getChildItem(int index) {
            return children.get(index);
        }
}