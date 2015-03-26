package com.hydronicalabs.randomizer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {
    private static final String LIST = "saved_list";
    private static final String START_VALUE = "start_value";
    private static final String END_VALUE = "end_value";
    private static final String CURRENT_INDEX = "current_index";

    private int[] orderList;
    private int displayedIndex = 0;
    private EditText editTextStart;
    private EditText editTextEnd;
    private TextView textViewValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int startValue = 0;
        int endValue = 20;

        if (savedInstanceState != null)
        {
            startValue = savedInstanceState.getInt(START_VALUE);
            endValue = savedInstanceState.getInt(END_VALUE);
            orderList = savedInstanceState.getIntArray(LIST);
            displayedIndex = savedInstanceState.getInt(CURRENT_INDEX);
        }

        editTextStart = (EditText)findViewById(R.id.editTextStart);
            editTextStart.setText(Integer.toString(startValue));

        editTextEnd = (EditText)findViewById(R.id.editTextEnd);
            editTextEnd.setText(Integer.toString(endValue));

        textViewValue = (TextView)findViewById(R.id.textViewValue);
            textViewValue.setText("");

        if (orderList != null)
        {
            textViewValue.setText(Integer.toString(orderList[displayedIndex]));
            printArray();
        }



    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putIntArray(LIST,orderList);

        int startValue = Integer.valueOf(editTextStart.getText().toString());
            savedInstanceState.putInt(START_VALUE,startValue);

        int endValue = Integer.valueOf(editTextEnd.getText().toString());
            savedInstanceState.putInt(END_VALUE,endValue);

        savedInstanceState.putInt(CURRENT_INDEX,displayedIndex);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void newList(View view)
    {

        int startValue = Integer.valueOf(editTextStart.getText().toString());
        int endValue = Integer.valueOf(editTextEnd.getText().toString());

        orderList = new int[(endValue + 1) - startValue];
        int arrayValue = startValue;
        for (int i = 0; i < orderList.length;i++)
        {
            orderList[i] = startValue + i;
        }

        orderList = shuffle(orderList);
        displayedIndex = 0;
        printArray();

        textViewValue.setText(Integer.toString(orderList[displayedIndex]));
    }
    public void printArray()
    {
        TextView textViewResult = (TextView)findViewById(R.id.textViewResult);
        String arrayAsString = "";
        for (int i = 0; i < orderList.length; i++)
        {
            if (i == displayedIndex)
                arrayAsString += "<b>" + orderList[i] + " </b>";
            else
                arrayAsString += orderList[i] + " ";
        }
        textViewResult.setText(Html.fromHtml(arrayAsString));
    }
    public void nextValue(View view)
    {
       if (displayedIndex < orderList.length - 1)
           displayedIndex++;

        textViewValue.setText(Integer.toString(orderList[displayedIndex]));

        printArray();
    }

    public void prevValue(View view)
    {
        if (displayedIndex > 0)
            displayedIndex--;

        textViewValue.setText(Integer.toString(orderList[displayedIndex]));
        printArray();
    }

    public int[] shuffle(int[] values)
    {
        Random rnd = new Random();
        int [] result = new int[values.length];
        int swapIndex;
        int startIndex = 0;
        for (int endIndex = values.length; endIndex > 0; endIndex--)
        {
            swapIndex = rnd.nextInt(endIndex);
            result[startIndex] = values[swapIndex];
            values[swapIndex] = values[endIndex-1];
            values[endIndex-1] = result[startIndex];
            startIndex++;
        }

        return result;
    }
}
