package com.android.splitpersonality;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class qrCode extends AppCompatActivity {
    ImageView qr;
    TextView date;
    Bitmap bitmap ;
    String qrstring;
    public final static int QRcodeWidth = 500 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        qr=(ImageView)findViewById(R.id.imageView);
        qrstring=("{\"Date\":\""+getIntent().getStringExtra("date")+"\",\"time\":\""+getIntent().getStringExtra("time")+"\"}");
        Toast.makeText(qrCode.this,qrstring,Toast.LENGTH_LONG);
        date=(TextView)findViewById(R.id.textView);
        date.setText(qrstring);
        if(qrstring!=null){
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {BitMatrix bitMatrix=multiFormatWriter.encode(qrstring,BarcodeFormat.QR_CODE,500,500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                bitmap=barcodeEncoder.createBitmap(bitMatrix);
                qr.setImageBitmap(bitmap);


            } catch (WriterException e) {
                e.printStackTrace();
            }

        }}
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );
        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorAccent):getResources().getColor(R.color.colorPrimaryDark);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}


