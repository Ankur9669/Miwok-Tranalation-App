package com.example.translator;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Words>
{
    private int mcolorResourceId;
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent)
    {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        Words currentWord = getItem(position);

        TextView defaultView = (TextView) listItemView.findViewById(R.id.defaultTranslation);
        defaultView.setText(currentWord.getDefaultTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView englishView = (TextView) listItemView.findViewById(R.id.miwokTranslation);
        englishView.setText(currentWord.getMiwokTranslation());

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage())
        {
            // Find the ImageView in the list_item.xml layout with the ID list_item_icon
            iconView.setImageResource(currentWord.getResourceId());
            iconView.setVisibility(View.VISIBLE);
            // Get the image resource ID from the current AndroidFlavor object and
            // set the image to iconView
        }
        else
        {
            iconView.setVisibility(View.INVISIBLE);
        }
//         Return the whole list item layout (containing 2 TextViews and an ImageView)
//         so that it can be shown in the ListView
        // Set the theme color for the list item

        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mcolorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        return listItemView;
    }

    public ListAdapter(Context context, ArrayList<Words> resource, int Color)
    {
        super(context, 0, resource);
        mcolorResourceId = Color;
    }


}
