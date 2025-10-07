package simone.pizzeria.app;


import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import simone.pizzeria.app.models.LoyaltyCard;



public class LoyaltyFragment extends Fragment {

    private LoyaltyCard loyaltyCard;
    private TextView tvPoints, tvTier, tvPizzasOrdered, tvNextReward;
    private ProgressBar progressBar;
    private ImageView ivQrCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loyalty, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPoints = view.findViewById(R.id.tv_points);
        tvTier = view.findViewById(R.id.tv_tier);
        tvPizzasOrdered = view.findViewById(R.id.tv_pizzas_ordered);
        tvNextReward = view.findViewById(R.id.tv_next_reward);
        progressBar = view.findViewById(R.id.progress_bar);
        ivQrCode = view.findViewById(R.id.iv_qr_code);

        loyaltyCard = LoyaltyManager.getLoyaltyCard(requireContext());
        if (loyaltyCard == null) {
            loyaltyCard = new LoyaltyCard("");
        }

        updateUI();
        generateQRCode();

        RecyclerView recyclerView = view.findViewById(R.id.rv_transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new TransactionsAdapter(loyaltyCard.getTransactions()));

        view.findViewById(R.id.btn_add_points).setOnClickListener(v -> {
            loyaltyCard.addPoints(1, "Pizza Margherita");
            LoyaltyManager.saveLoyaltyCard(requireContext(), loyaltyCard);
            updateUI();
            recyclerView.getAdapter().notifyDataSetChanged();
        });
    }

    private void updateUI() {
        tvPoints.setText(String.valueOf(loyaltyCard.getPoints()));
        tvTier.setText(loyaltyCard.getTierEmoji() + " " + loyaltyCard.getTierName());
        tvPizzasOrdered.setText(String.valueOf(loyaltyCard.getTotalPizzasOrdered()));
        tvNextReward.setText(loyaltyCard.getPointsToNextReward() + " punti al prossimo premio");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(loyaltyCard.getProgressPercentage(), true);
        }
    }

    private void generateQRCode() {
        try {
            String qrContent = "LOYALTY:" + loyaltyCard.getUserId();
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix matrix = writer.encode(qrContent, BarcodeFormat.QR_CODE, 500, 500);

            int width = matrix.getWidth();
            int height = matrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            ivQrCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
        private final List<LoyaltyCard.Transaction> transactions;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ITALIAN);

        TransactionsAdapter(List<LoyaltyCard.Transaction> transactions) {
            this.transactions = transactions;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_transaction, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LoyaltyCard.Transaction transaction = transactions.get(position);
            holder.tvDescription.setText(transaction.getDescription());
            holder.tvDate.setText(dateFormat.format(transaction.getDate()));
            holder.tvPoints.setText((transaction.getPoints() > 0 ? "+" : "") + transaction.getPoints());
            holder.tvPoints.setTextColor(transaction.getPoints() > 0 ? 0xFF66BB6A : 0xFFFF5252);
        }

        @Override
        public int getItemCount() {
            return transactions.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvDescription, tvDate, tvPoints;

            ViewHolder(View view) {
                super(view);
                tvDescription = view.findViewById(R.id.tv_description);
                tvDate = view.findViewById(R.id.tv_date);
                tvPoints = view.findViewById(R.id.tv_points);
            }
        }
    }
}
