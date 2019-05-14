package njoize.dkh.th.co.myshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<String> productStringArrayList, productDetailStringArrayList, priceStringArrayList, quantityStringArrayList, remarkStringArrayList;
    private OnClickItem onClickItem;
    private LayoutInflater layoutInflater;

    public ProductRecyclerViewAdapter(Context context,
                                      ArrayList<String> productStringArrayList,
                                      ArrayList<String> productDetailStringArrayList,
                                      ArrayList<String> priceStringArrayList,
                                      ArrayList<String> quantityStringArrayList,
                                      ArrayList<String> remarkStringArrayList,
                                      OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.productStringArrayList = productStringArrayList;
        this.productDetailStringArrayList = productDetailStringArrayList;
        this.priceStringArrayList = priceStringArrayList;
        this.quantityStringArrayList = quantityStringArrayList;
        this.remarkStringArrayList = remarkStringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.recycler_product, viewGroup, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);

        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int i) {

        String productString = productStringArrayList.get(i);
        String productDetailString = productDetailStringArrayList.get(i);
        String priceString = priceStringArrayList.get(i);
        String quantityString = quantityStringArrayList.get(i);
        String remarkString = remarkStringArrayList.get(i);

        productViewHolder.productTextView.setText(productString);

        if (productDetailString != null && !productDetailString.isEmpty() && !productDetailString.equals("null")) {
            productViewHolder.productDetailTextView.setText(productDetailString);
        } else {
            productViewHolder.productDetailTextView.setVisibility(View.GONE);
        }

        productViewHolder.priceTextView.setText(priceString);
        productViewHolder.quantityTextView.setText(quantityString);

        if (remarkString != null && !remarkString.isEmpty() && !remarkString.equals("null")) {
            productViewHolder.remarkTextView.setText(remarkString);
        } else {
            productViewHolder.remarkTextView.setVisibility(View.GONE);
        }

        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickItem(v, productViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productStringArrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView productTextView, productDetailTextView, priceTextView, quantityTextView, remarkTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productTextView = itemView.findViewById(R.id.txtProduct);
            productDetailTextView = itemView.findViewById(R.id.txtProductDetail);
            priceTextView = itemView.findViewById(R.id.txtPrice);
            quantityTextView = itemView.findViewById(R.id.txtQuantity);
            remarkTextView = itemView.findViewById(R.id.txtRemark);
        }
    }

}
