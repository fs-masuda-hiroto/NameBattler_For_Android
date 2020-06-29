package com.example.namebattler_ver00;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.namebattler_ver00.Fragments.RegisterCharaListFragment;

import java.util.ArrayList;

public class CharaListAdapter extends RecyclerView.Adapter<CharaListAdapter.ViewHolder> {

    private ArrayList<CharaConfig> mCharaStatusList;
    private RegisterCharaListFragment.ICharaClickedListener _charaClickedListener;

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameText;
        private TextView mJobText;
        private TextView mStatusText;

        ViewHolder(View view) {
            super(view);

            mNameText = (TextView) view.findViewById(R.id.chara_name_text);
            mJobText = (TextView) view.findViewById(R.id.chara_job_text);
            mStatusText = (TextView) view.findViewById(R.id.chara_status_text);
        }
    }

    public CharaListAdapter(ArrayList<CharaConfig> CharaStatusList,
                            RegisterCharaListFragment.ICharaClickedListener charaButtonListener) {

        this._charaClickedListener = charaButtonListener;
        this.mCharaStatusList = CharaStatusList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chara_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final String name = mCharaStatusList.get(position).getName();
        final String job = mCharaStatusList.get(position).getJob();
        final String hp = String.valueOf(mCharaStatusList.get(position).getHp());
        final String mp = String.valueOf(mCharaStatusList.get(position).getMp());
        final String str = String.valueOf(mCharaStatusList.get(position).getStr());
        final String def = String.valueOf(mCharaStatusList.get(position).getDef());
        final String agi = String.valueOf(mCharaStatusList.get(position).getAgi());
        final String luck = String.valueOf(mCharaStatusList.get(position).getLuck());

        final String setStatusText = "HP:" + hp + "  MP:" + mp + "  STR:" + str
                + "  DEF:" + def + "  AGI:" + agi + "  LUCK:" + luck;

        holder.mNameText.setText(name);
        holder.mJobText.setText(job);
        holder.mStatusText.setText(setStatusText);

        // クリックイベント
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _charaClickedListener.onCharaClicked(name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCharaStatusList.size();
    }
}
