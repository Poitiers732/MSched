package com.example.wzdrodowski.msched;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by wzdrodowski on 2018-02-06.
 */

class Adapter extends ArrayAdapter<String> {

    Adapter(Context context, String[] foods) {
        super(context, R.layout.list_item , foods);
    }
}
