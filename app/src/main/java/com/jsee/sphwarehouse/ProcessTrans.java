package com.jsee.sphwarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by jin.chung on 11/2/2016.
 */
public class ProcessTrans extends AppCompatActivity {
    public String action_mode = "";
    ListView TransListview;
    ArrayList<Transaction> TransList = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        try {
            FileInputStream fis = openFileInput("transJson.data");
            TransactionReader treader= new TransactionReader(fis);
            TransList = treader.readTransFile();
            TransListview = (ListView) findViewById(R.id.transView);
            ArrayAdapter<Transaction> theAdapter = (ArrayAdapter<Transaction>) TransListview.getAdapter();
            theAdapter.clear();
            theAdapter.addAll(TransList);

            theAdapter.notifyDataSetChanged();
        }catch (Exception fex){
            //no file
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent getActionMode = getIntent();

        setContentView(R.layout.transaction_list);

        TextView transTitle = (TextView)
                findViewById(R.id.transtitle);
        Button transNew = (Button)
                findViewById(R.id.btnnewtran);
        TransListview = (ListView) findViewById(R.id.transView);

        action_mode = getActionMode.getExtras().getString("action_mode");


            if( action_mode.equals("check_in")){
                transTitle.setText("CHECK IN TRANSACTION");
                transNew.setText("NEW CHECK IN");
            }else if(action_mode.equals("check_out")){
                transTitle.setText("CHECK OUT TRANSACTION");
                transNew.setText("NEW CHECK OUT");
            }

       //get list
        try {
            FileInputStream fis = openFileInput("transJson.data");
            TransactionReader treader= new TransactionReader(fis);
            TransList = treader.readTransFile();

            TransactionAdapter tradapter = new TransactionAdapter(this,R.layout.trans_itemlayout, TransList);
            TransListview.setAdapter(tradapter);



        }catch (Exception fex){
            //no file
        }









    }

    public void createTrans(View view) {
        Intent callTransform = new Intent(this, NewTransaction.class);
        callTransform.putExtra("action_mode", action_mode);
        startActivity(callTransform);

    }
}
