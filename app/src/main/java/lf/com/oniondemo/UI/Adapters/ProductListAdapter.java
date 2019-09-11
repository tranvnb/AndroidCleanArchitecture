package lf.com.oniondemo.UI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.R;
import lf.com.oniondemo.UI.Activities.ProductView;
import lf.com.oniondemo.Utils.RealmHelper;

/**
 * Created by TranVo on 3/15/2017.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    private List<Product> productList;
    private ProductView mListenter;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (productList != null){
            Product product = productList.get(position);
            if (product != null) {
                holder.updateItemContent(product);
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public void setItemClickListener(ProductView listener){
        this.mListenter = listener;
    }

    public void setItemList(List<Product> productList) {
        this.productList = productList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.txtProductName)
        TextView txtProductName;
        @BindView(R.id.txtBrandName)
        TextView txtBrandName;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                if (mListenter != null){
                    Product product = productList.get(getAdapterPosition());
                    mListenter.onProductItemClick(product);
                }
            });
        }

        public void updateItemContent(Product product) {
            icon.setImageResource(RealmHelper.getIconResource(product.getProductIconIndex()));
            txtProductName.setText(product.getName());
            txtBrandName.setText(product.getBrandName());
            ratingBar.setRating((float) product.getAverageRating());
        }
    }
}
