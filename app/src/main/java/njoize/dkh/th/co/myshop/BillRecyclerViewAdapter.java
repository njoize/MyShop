package njoize.dkh.th.co.myshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BillRecyclerViewAdapter extends RecyclerView.Adapter<BillRecyclerViewAdapter.BillViewHolder> {

    public Context context;
    private ArrayList<String>
            channelStringArrayList,
            methodStringArrayList,
            detailLine1StringArrayList,
            detailLine2StringArrayList,
            detailLine3StringArrayList,
            totalPriceStringArrayList;
    private LayoutInflater layoutInflater;
    private OnClickItem onClickItem;

    public BillRecyclerViewAdapter(Context context,
                                   ArrayList<String> channelStringArrayList,
                                   ArrayList<String> methodStringArrayList,
                                   ArrayList<String> detailLine1StringArrayList,
                                   ArrayList<String> detailLine2StringArrayList,
                                   ArrayList<String> detailLine3StringArrayList,
                                   ArrayList<String> totalPriceStringArrayList,
                                   OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.channelStringArrayList = channelStringArrayList;
        this.methodStringArrayList = methodStringArrayList;
        this.detailLine1StringArrayList = detailLine1StringArrayList;
        this.detailLine2StringArrayList = detailLine2StringArrayList;
        this.detailLine3StringArrayList = detailLine3StringArrayList;
        this.totalPriceStringArrayList = totalPriceStringArrayList;
        this.onClickItem = onClickItem;
    } // Constructor

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_view_bill, viewGroup, false);
        BillViewHolder billViewHolder = new BillViewHolder(view);

        return billViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BillViewHolder billViewHolder, int i) {

        String channelString = channelStringArrayList.get(i);
        String methodString = methodStringArrayList.get(i);
        String detailLine1String = detailLine1StringArrayList.get(i);
        String detailLine2String = detailLine2StringArrayList.get(i);
        String detailLine3String = detailLine3StringArrayList.get(i);
        String totalPriceString = totalPriceStringArrayList.get(i);

        billViewHolder.channelTextView.setText(channelString);
        billViewHolder.methodTextView.setText( methodString);
        billViewHolder.detailLine1TextView.setText(detailLine1String);
        billViewHolder.detailLine2TextView.setText(detailLine2String);
        billViewHolder.detailLine3TextView.setText(detailLine3String);
        billViewHolder.totalPriceTextView.setText(totalPriceString);

        billViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickItem(v, billViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return detailLine1StringArrayList.size();
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {

        private TextView channelTextView,
                methodTextView,
                detailLine1TextView,
                detailLine2TextView,
                detailLine3TextView,
                totalPriceTextView;


        public BillViewHolder(@NonNull View itemView) {
            super(itemView);

            channelTextView = itemView.findViewById(R.id.txtChannel);
            methodTextView = itemView.findViewById(R.id.txtMethod);
            detailLine1TextView = itemView.findViewById(R.id.txtDetail1);
            detailLine2TextView = itemView.findViewById(R.id.txtDetail2);
            detailLine3TextView = itemView.findViewById(R.id.txtDetail3);
            totalPriceTextView = itemView.findViewById(R.id.txtTotalPrice);

        }
    } // BillViewHolder Class


} // Main Class
