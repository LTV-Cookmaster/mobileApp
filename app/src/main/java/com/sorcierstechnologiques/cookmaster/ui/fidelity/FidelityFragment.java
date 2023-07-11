package com.sorcierstechnologiques.cookmaster.ui.fidelity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sorcierstechnologiques.cookmaster.databinding.FragmentFidelityBinding;
import com.sorcierstechnologiques.cookmaster.ui.fidelity.FidelityViewModel;

public class FidelityFragment extends Fragment {

    private FragmentFidelityBinding binding;
    private TextView textView;
    private ImageView qrImageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FidelityViewModel fidelityViewModel =
                new ViewModelProvider(this).get(FidelityViewModel.class);

        binding = FragmentFidelityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textView = binding.textFidelity;
        qrImageView = binding.qrImageView;

        fidelityViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Générer le code QR et l'afficher
        generateQRCode();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void generateQRCode() {
        SharedPreferences settings = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        String userId = settings.getString("user_id", "");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            // Générer la matrice de bits du code QR
            BitMatrix bitMatrix = qrCodeWriter.encode(userId, BarcodeFormat.QR_CODE, 200, 200);

            // Convertir la matrice de bits en image Bitmap
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap qrBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrBitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            // Afficher l'image du code QR dans l'ImageView
            qrImageView.setImageBitmap(qrBitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
