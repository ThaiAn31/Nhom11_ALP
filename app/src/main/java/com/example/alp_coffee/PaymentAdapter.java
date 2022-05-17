package com.example.alp_coffee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private ArrayList<OrderDetail> arrayList;
    private Context ctx;
    private onclick abc;

    public PaymentAdapter(ArrayList<OrderDetail> arrayList, Context ctx, onclick abc) {
        this.arrayList = arrayList;
        this.ctx = ctx;
        this.abc = abc;
    }

    public interface onclick {
        void add(OrderDetail orderDetail);
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        OrderDetail orderDetail = arrayList.get(position);
        if (orderDetail == null) {
            return;
        }
        holder.tvName.setText(orderDetail.getId_Coffee().getName());
        holder.tvSL.setText(String.valueOf(orderDetail.getSoLuong()) + "x");
        holder.tvPrice.setText(String.valueOf(orderDetail.getId_Coffee().getPrice()));
        holder.btnX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abc.add(orderDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSL, tvName, tvPrice;
        private Button btnX;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSL = itemView.findViewById(R.id.textView9);
            tvName = itemView.findViewById(R.id.textView11);
            tvPrice = itemView.findViewById(R.id.textView12);
            btnX = itemView.findViewById(R.id.btn_X);

        }
    }
}
