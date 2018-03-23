package com.defence.costomapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.defence.costomapp.R;
import com.defence.costomapp.bean.TuikuanMachineBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by author Sgq
 * on 2018/3/21.
 */

public class TkSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TuikuanMachineBean.ListBean> mList;

    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();

    private boolean mIsSelectable = false;
    String string;


    public TkSelectAdapter(List<TuikuanMachineBean.ListBean> list, String type) {
        if (list == null) {
            throw new IllegalArgumentException("model Data must not be null");
        }
        mList = list;
        string = type;
    }

    //更新adpter的数据和选择状态
    public void updateDataSet(List<TuikuanMachineBean.ListBean> list) {
        this.mList = list;
        mSelectedPositions = new SparseBooleanArray();

    }

    //获得选中条目的结果
    public ArrayList<String> getSelectedItem() {


        ArrayList<String> selectList = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();

        if (string.equals("group")) {
            for (int i = 0; i < mList.size(); i++) {
                if (isItemChecked(i)) {
                    if (mList.get(i).getPrentid().equals("0")) {
                        if (isItemChecked(i)) {
                            strings.add(mList.get(i).getId());
                        }
                    }
                }
            }
            for (int i = 0; i < mList.size(); i++) {
                for (int j = 0; j < strings.size(); j++) {
                    if (strings.get(j).equals(mList.get(i).getPrentid())) {
                        selectList.add(mList.get(i).getMachinenumber());
                    }
                }

            }

        } else if (string.equals("shop")) {
            for (int i = 0; i < mList.size(); i++) {
                if (isItemChecked(i)) {
                    selectList.add(mList.get(i).getGuigeid());
                }
            }
        } else {
            for (int i = 0; i < mList.size(); i++) {
                if (isItemChecked(i)) {
                    selectList.add(mList.get(i).getMachinenumber());
                }
            }

        }

        return selectList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new TkSelectAdapter.ListItemViewHolder(itemView);
    }

    //设置给定位置条目的选择状态

    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    //根据位置判断条目是否可选
    private boolean isSelectable() {
        return mIsSelectable;
    }

    //设置给定位置条目的可选与否的状态
    private void setSelectable(boolean selectable) {
        mIsSelectable = selectable;
    }

    //绑定界面，设置监听
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {


        //设置条目状态
        if (string.equals("machine")) {
            ((ListItemViewHolder) holder).mainTitle.setText(mList.get(i).getDetailedinstalladdress());
        } else if (string.equals("shop")) {
            ((ListItemViewHolder) holder).mainTitle.setText(mList.get(i).getDs());
        } else if (string.equals("group")) {
            List<TuikuanMachineBean.ListBean> l = new ArrayList();
            for (int j = 0; j < mList.size(); j++) {
                if (mList.get(j).getPrentid().equals("0")) {
                    l.add(mList.get(j));
                }
            }
            ((ListItemViewHolder) holder).mainTitle.setText(l.get(i).getName());

        }

        ((ListItemViewHolder) holder).checkBox.setChecked(isItemChecked(i));

        //checkBox的监听
        ((ListItemViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(i)) {
                    setItemChecked(i, false);
                } else {
                    setItemChecked(i, true);
                }
            }
        });

        //条目view的监听
        ((ListItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(i)) {
                    setItemChecked(i, false);
                } else {
                    setItemChecked(i, true);
                }
                notifyItemChanged(i);
            }
        });
    }

    @Override
    public int getItemCount() {
//        if (string.equals("group")) {
//            for (int i = 0; i < mList.size(); i++) {
//                if (!mList.get(i).getPrentid().equals("0")) {
//                    mList.remove(mList.get(i));
//                }
//            }
//            return mList == null ? 0 : mList.size();
//        } else {
        if (string.equals("group")) {
            int size = 0;
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getPrentid().equals("0")) {
                    size++;
                }

            }
            return size;
        } else {
            return mList == null ? 0 : mList.size();
        }

    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder
        CheckBox checkBox;
        TextView mainTitle;

        ListItemViewHolder(View view) {
            super(view);
            this.mainTitle = (TextView) view.findViewById(R.id.text);
            this.checkBox = (CheckBox) view.findViewById(R.id.select_checkbox);

        }
    }
}