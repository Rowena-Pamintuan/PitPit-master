package tricycle.app.pitpit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlacesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PlaceModel> dataModelArrayList;

    public PlacesAdapter(Context context, ArrayList<PlaceModel> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.places_tile, null, true);

            holder.value = (TextView) convertView.findViewById(R.id.value);
            holder.subtext = (TextView) convertView.findViewById(R.id.subtext);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

//        Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.value.setText(dataModelArrayList.get(position).getValue());
        holder.subtext.setText(dataModelArrayList.get(position).getSubtext());

        return convertView;
    }

    private class ViewHolder {

        protected TextView value, subtext;
//        protected ImageView iv;
    }
}
