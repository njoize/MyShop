package njoize.dkh.th.co.myshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private Context context;
    private ArrayList<String> nameProductStringArrayList, quantityStringArrayList, amountStringArrayList, priceStringArrayList;
//    private OnClickItem onClickItem;
    private OnClickImage onClickImage;
    private LayoutInflater layoutInflater;

//    public OrderAdapter(Context context, ArrayList<String> nameProductStringArrayList, ArrayList<String> quantityStringArrayList, ArrayList<String> amountStringArrayList, ArrayList<String> priceStringArrayList, OnClickItem onClickItem) {
    public OrderAdapter(Context context, ArrayList<String> nameProductStringArrayList, ArrayList<String> quantityStringArrayList, ArrayList<String> amountStringArrayList, ArrayList<String> priceStringArrayList, OnClickImage onClickImage) {
        this.layoutInflater = LayoutInflater.from(context);
        this.nameProductStringArrayList = nameProductStringArrayList;
        this.quantityStringArrayList = quantityStringArrayList;
        this.amountStringArrayList = amountStringArrayList;
        this.priceStringArrayList = priceStringArrayList;
//        this.onClickItem = onClickItem;
        this.onClickImage = onClickImage;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_order, viewGroup, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(view);

        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder orderViewHolder, int i) {

        String nameProduct = nameProductStringArrayList.get(i);
        String quantity = quantityStringArrayList.get(i);
        String amount = amountStringArrayList.get(i);
        String price = priceStringArrayList.get(i);

        orderViewHolder.nameProductTextView.setText(nameProduct);
        orderViewHolder.quantityTextView.setText(quantity);
        orderViewHolder.amountTextView.setText(amount);
        orderViewHolder.priceTextView.setText(price);

//        orderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickItem.onClickItem(v, orderViewHolder.getAdapterPosition());
//            }
//        });

        orderViewHolder.decImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickImage.onClickImage(v, orderViewHolder.getAdapterPosition(), false);
            }
        });

        orderViewHolder.incImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickImage.onClickImage(v, orderViewHolder.getAdapterPosition(), true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nameProductStringArrayList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView nameProductTextView, quantityTextView, amountTextView, priceTextView;
        private ImageView decImageView, incImageView;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            nameProductTextView = itemView.findViewById(R.id.txtNameProduct);
            quantityTextView = itemView.findViewById(R.id.txtQuantity);
            amountTextView = itemView.findViewById(R.id.txtAmount);
            priceTextView = itemView.findViewById(R.id.txtPrice);
            decImageView = itemView.findViewById(R.id.imvDecrease);
            incImageView = itemView.findViewById(R.id.imvIncrease);

        }
    }
}
