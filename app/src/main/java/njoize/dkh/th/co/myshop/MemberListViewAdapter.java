package njoize.dkh.th.co.myshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MemberListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<NameMemberModel> nameMemberModelArrayList;
    private List<NameMemberModel> nameMemberModelList;

    public MemberListViewAdapter(Context context, List<NameMemberModel> nameMemberModelList) {
        this.context = context;
        this.nameMemberModelList = nameMemberModelList;
        this.layoutInflater = LayoutInflater.from(context);
        this.nameMemberModelArrayList = new ArrayList<NameMemberModel>();
        this.nameMemberModelArrayList.addAll(nameMemberModelList);
    }

    @Override
    public int getCount() {
        return nameMemberModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return nameMemberModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.recycler_member, null);
            holder.name = convertView.findViewById(R.id.txtName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(nameMemberModelList.get(position).getNameMemberString());

        return convertView;
    }

    public class ViewHolder {
        TextView name;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        nameMemberModelList.clear();
        if (charText.length() == 0) {
            nameMemberModelList.addAll(nameMemberModelArrayList);
        } else {
            for (NameMemberModel nameMemberModel : nameMemberModelArrayList) {
                if (nameMemberModel.getNameMemberString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    nameMemberModelList.add(nameMemberModel);
                }
            } // for
        } // if
        notifyDataSetChanged();
    } // filter



}