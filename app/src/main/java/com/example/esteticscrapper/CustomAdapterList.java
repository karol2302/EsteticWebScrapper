package com.example.esteticscrapper;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
 import android.widget.TextView;

import com.squareup.picasso.Picasso;

 import java.util.List;

public class CustomAdapterList extends ArrayAdapter<LinkData> {

    List<LinkData> mylist;

    public CustomAdapterList(Context _context, List<LinkData> _mylist) {
        super(_context, R.layout.list_row, _mylist);

        this.mylist = _mylist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = new LinearLayout(getContext());
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
        convertView = vi.inflate(R.layout.list_row, parent, false);
        LinkData linkData = getItem(position);


        //
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setText(Html.fromHtml(linkData.ProductName));

        // show image
        ImageView img = (ImageView)convertView.findViewById(R.id.list_image);

        // download image

        Picasso.with(getContext())
                .load(linkData.Image)
                .into(img);

        return convertView;
    }


}