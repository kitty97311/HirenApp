package com.kitty.hiren;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.horizontalcalendar.HorizontalCalendar;
import com.horizontalcalendar.model.CalendarEvent;
import com.horizontalcalendar.utils.CalendarEventsPredicate;
import com.horizontalcalendar.utils.HorizontalCalendarListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HomeActivity extends ComponentActivity implements View.OnClickListener {

    private Button btnA, btnB, btnC, btnD, btnE;
    private View slide;
    private ImageView slideButton;
    private TextView txtSelected;

    private ArrayList<Date> dates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);
        btnE = findViewById(R.id.btnE);
        slide = findViewById(R.id.slide);
        slideButton = findViewById(R.id.slideButton);
        slideButton.setOnClickListener(this);
        txtSelected = findViewById(R.id.txtSelected);

        String todayStr = DateFormat.format("EEEE, MMM d", Calendar.getInstance()).toString();
        txtSelected.setText(todayStr);

        setButtonColor();

        /* start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);

        /* end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);

        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendar)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.WHITE, Color.WHITE)
                .colorTextMiddle(Color.WHITE, Color.parseColor("#0021ff"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
//                .addEvents(new CalendarEventsPredicate() {
//
//                    Random rnd = new Random();
//                    @Override
//                    public List<CalendarEvent> events(Calendar date) {
//                        List<CalendarEvent> events = new ArrayList<>();
//                        int count = rnd.nextInt(6);
//
//                        for (int i = 0; i <= count; i++){
//                            events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
//                        }
//
//                        return events;
//                    }
//                })
                .build();

        Log.i("Default Date", DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString());

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = DateFormat.format("EEEE, MMM d", date).toString();
                txtSelected.setText(selectedDateStr);
//                Toast.makeText(HomeActivity.this, selectedDateStr + " selected!", Toast.LENGTH_SHORT).show();
                Log.i("onDateSelected", selectedDateStr + " - Position = " + position);
            }

        });

    }

    private void setButtonColor() {
        TypedArray colors = getResources().obtainTypedArray(R.array.buttonColorArray);
        btnA.setBackgroundTintList(ColorStateList.valueOf(colors.getColor(Integer.parseInt(btnA.getText().toString()), ContextCompat.getColor(this, R.color.buttonColor5))));
        btnB.setBackgroundTintList(ColorStateList.valueOf(colors.getColor(Integer.parseInt(btnB.getText().toString()), ContextCompat.getColor(this, R.color.buttonColor5))));
        btnC.setBackgroundTintList(ColorStateList.valueOf(colors.getColor(Integer.parseInt(btnC.getText().toString()), ContextCompat.getColor(this, R.color.buttonColor5))));
        btnD.setBackgroundTintList(ColorStateList.valueOf(colors.getColor(Integer.parseInt(btnD.getText().toString()), ContextCompat.getColor(this, R.color.buttonColor5))));
        btnE.setBackgroundTintList(ColorStateList.valueOf(colors.getColor(Integer.parseInt(btnE.getText().toString()), ContextCompat.getColor(this, R.color.buttonColor5))));
    }

    private boolean isShown = false;

    private void toggleSlide() {
        // Slide up
        if (!isShown) {
            slideButton.setImageResource(R.drawable.ic_down);
            slide.animate().translationY(0).setDuration(300).start();
        } else { // Slide down
            slideButton.setImageResource(R.drawable.ic_up);
            slide.animate().translationY(250 * (getResources().getDisplayMetrics().density)).setDuration(300).start();
        }
        isShown = !isShown;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.slideButton) {
            toggleSlide();
        }
    }
}
