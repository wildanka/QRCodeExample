package com.example.dan.qrcodeexample;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class QRScanFromFragment extends Fragment implements OnActivityResultDataChanged{
    private static final String TAG = "QRScanFromFragment";
    private Button btnScan;
    private TextView tvFragmentResult;
    private MainActivity mActivity;

    public QRScanFromFragment() {
        // Required empty public constructor
    }

    //TODO : create interface to be called in MainActivity
    private OnScanQrButtonClicked mOnScanQrButtonClickedListener;  //interface instance
    //the interface
    public interface OnScanQrButtonClicked{
        void triggerScanQr();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mActivity.setOnActivityResultDataChanged(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_qrscan, container, false);
        btnScan = (Button) rootView.findViewById(R.id.btn_invokeQRScan);
        tvFragmentResult = (TextView) rootView.findViewById(R.id.tv_fragment_result);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnScanQrButtonClickedListener.triggerScanQr();
//                IntentIntegrator.forSupportFragment(QRScanFromFragment.this).initiateScan(); // `this` is the current Fragment
//                IntentIntegrator integrator = new IntentIntegrator(getActivity());
//                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//                integrator.setPrompt("Scan a barcode");
//                integrator.setCameraId(0);  // Use a specific camera of the device
//                integrator.setBeepEnabled(false);
//                integrator.setBarcodeImageEnabled(true);
//                integrator.initiateScan();
            }
        });
        return rootView;
    }

    // Get the results:
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String barcode = result.getContents();
        System.out.println(barcode);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("QrFragment",barcode);
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnScanQrButtonClickedListener = (OnScanQrButtonClicked) getActivity();
        }catch(ClassCastException e){
            Log.e(TAG,"onAttach: ClassCastException: "+e.getMessage());
        }


    }

    @Override
    public void onDataReceived(String data) {
        tvFragmentResult.setText(data);
    }
}
