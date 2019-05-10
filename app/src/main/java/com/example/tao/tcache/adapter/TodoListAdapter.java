package com.example.tao.tcache.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tao.tcache.R;
import com.example.tao.tcache.bean.model.TodoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: tao
 * @time: 2019/5/7
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private Context mContext;

    private List<TodoBean> list;


    public TodoListAdapter(Context mContext, List<TodoBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_todo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TodoBean todoBean = list.get(i);
        viewHolder.tvTodoTitle.setText(todoBean.getTitle());
        viewHolder.tvContent.setText(todoBean.getContent());
        viewHolder.tvCreateTime.setText(mContext.getString(R.string.creattime, todoBean.getDateStr()));
        if (todoBean.getCompleteDateStr() != null && todoBean.getCompleteDateStr().isEmpty()) {
            viewHolder.tvCompleteTime.setVisibility(View.GONE);
        } else {
            viewHolder.tvCompleteTime.setText(mContext.getString(R.string.completetime, todoBean.getCompleteDateStr()));
            viewHolder.tvCompleteTime.setVisibility(View.VISIBLE);
        }
        if (todoBean.getStatus() == 0) {
            viewHolder.tvComplete.setText("未完成");
        } else {
            viewHolder.tvComplete.setText("已完成");
        }
        viewHolder.tvComplete.setOnClickListener(v -> {
            if (listener != null)
                listener.onListener1(todoBean);
        });
        viewHolder.tvDelete.setOnClickListener(v -> {
            if (listener != null)
                listener.onListener2(todoBean);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<TodoBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_todo_title)
        TextView tvTodoTitle;
        @BindView(R.id.dividing_line1)
        View dividingLine1;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.dividing_line2)
        View dividingLine2;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_complete_time)
        TextView tvCompleteTime;
        @BindView(R.id.tv_compete)
        TextView tvComplete;
        @BindView(R.id.container_todo)
        ConstraintLayout containerTodo;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onListener1(TodoBean bean);

        void onListener2(TodoBean bean);
    }

}
