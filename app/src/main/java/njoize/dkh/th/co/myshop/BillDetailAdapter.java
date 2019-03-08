package njoize.dkh.th.co.myshop;

import android.content.Context;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.BillDetailViewHolder> {


    private Context context;
    private ArrayList<String> nameStringArrayList,
            pdetailStringArrayList,
            rdetailStringArrayList,
            weightStringArrayList,
            amountStringArrayList,
            priceStringArrayList,
            discountStringArrayList,
            sumStringArrayList;
    private LayoutInflater layoutInflater;

    public BillDetailAdapter(Context context, ArrayList<String> nameStringArrayList,
                             ArrayList<String> pdetailStringArrayList,
                             ArrayList<String> rdetailStringArrayList,
                             ArrayList<String> weightStringArrayList,
                             ArrayList<String> amountStringArrayList,
                             ArrayList<String> priceStringArrayList,
                             ArrayList<String> discountStringArrayList,
                             ArrayList<String> sumStringArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.nameStringArrayList = nameStringArrayList;
        this.pdetailStringArrayList = pdetailStringArrayList;
        this.rdetailStringArrayList = rdetailStringArrayList;
        this.weightStringArrayList = weightStringArrayList;
        this.amountStringArrayList = amountStringArrayList;
        this.priceStringArrayList = priceStringArrayList;
        this.discountStringArrayList = discountStringArrayList;
        this.sumStringArrayList = sumStringArrayList;
    }

    @NonNull
    @Override
    public BillDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_view_bill_detail, viewGroup, false);
        BillDetailViewHolder billDetailViewHolder = new BillDetailViewHolder(view);

        return billDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailViewHolder billDetailViewHolder, int i) {

        String nameString = nameStringArrayList.get(i);
        String pdetailString = pdetailStringArrayList.get(i);
        String rdetailString = rdetailStringArrayList.get(i);
        String weightString = weightStringArrayList.get(i);
        String amountString = amountStringArrayList.get(i);
        String priceString = priceStringArrayList.get(i);
        String discountString = discountStringArrayList.get(i);
        String sumString = sumStringArrayList.get(i);

        if (nameString != null && !nameString.isEmpty() && !nameString.equals("null")) {
            billDetailViewHolder.nameTextView.setText(nameString);
        } else {
            billDetailViewHolder.nameTextView.setVisibility(View.GONE);
        }

        if (pdetailString != null && !pdetailString.isEmpty() && !pdetailString.equals("null")) {
            billDetailViewHolder.pdetailTextView.setText(pdetailString);
        } else {
            billDetailViewHolder.pdetailTextView.setVisibility(View.GONE);
        }

        if (rdetailString != null && !rdetailString.isEmpty() && !rdetailString.equals("null")) {
            billDetailViewHolder.rdetailTextView.setText(rdetailString);
        } else {
            billDetailViewHolder.rdetailTextView.setVisibility(View.GONE);
        }

        billDetailViewHolder.weightTextView.setText(weightString);
        billDetailViewHolder.amountTextView.setText(amountString);
        billDetailViewHolder.priceTextView.setText(priceString);
        billDetailViewHolder.discountTextView.setText(discountString);
        billDetailViewHolder.sumTextView.setText(sumString);

    }

    @Override
    public int getItemCount() {
        return nameStringArrayList.size();
    }

    class BillDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView,
                pdetailTextView,
                rdetailTextView,
                weightTextView,
                amountTextView,
                priceTextView,
                discountTextView,
                sumTextView;


        public BillDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.txtName);
            pdetailTextView = itemView.findViewById(R.id.txtPDetail);
            rdetailTextView = itemView.findViewById(R.id.txtRDetail);
            weightTextView = itemView.findViewById(R.id.txtWeight);
            amountTextView = itemView.findViewById(R.id.txtAmount);
            priceTextView = itemView.findViewById(R.id.txtPrice);
            discountTextView = itemView.findViewById(R.id.txtDiscount);
            sumTextView = itemView.findViewById(R.id.txtSum);
        }
    } // BillDetailViewHolder Class

} // Main Class
