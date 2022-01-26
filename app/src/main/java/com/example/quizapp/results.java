package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore data = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_results);

        //TextView text = findViewById(R.id.txt);
        Task<DocumentSnapshot> task = data.collection("data").document("votes").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> map = task.getResult().getData();
                Number bnb = (Number)map.get("bnb");
                Number wng = (Number)map.get("wng");
                loadChart(wng, bnb);
                //Log.d("hello", String.valueOf(k));
            }
        });


    }

    public void loadChart(Number wng, Number bnb) {
        Pie pie = AnyChart.pie();

        List<DataEntry> stats = new ArrayList<>();
        stats.add(new ValueDataEntry(wng + " people say its white and gold", wng));
        stats.add(new ValueDataEntry(bnb + " people say its blue and black", bnb));

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        pie.data(stats);
        pie.animation(true);
        pie.legend().itemsLayout("horizontalExpandable");
        pie.legend().position("top");
        pie.title("What people are saying");
        anyChartView.setChart(pie);

    }
}