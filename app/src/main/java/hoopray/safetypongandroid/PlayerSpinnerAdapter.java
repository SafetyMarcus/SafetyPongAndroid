package hoopray.safetypongandroid;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Marcus Hooper
 */
public class PlayerSpinnerAdapter implements SpinnerAdapter
{
	private ArrayList<String> names;
	private ArrayList<String> ids;

	public PlayerSpinnerAdapter(ArrayList<String> names, ArrayList<String> ids)
	{
		this.names = names;
		this.ids = ids;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.label, parent, false);

		((TextView) convertView).setText(names.get(position));
		return convertView;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer)
	{
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer)
	{
	}

	@Override
	public int getCount()
	{
		return names.size();
	}

	@Override
	public String getItem(int position)
	{
		return names.get(position);
	}

	public String getId(int position)
	{
		return ids.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return new View(parent.getContext());
	}

	@Override
	public int getItemViewType(int position)
	{
		return 0;
	}

	@Override
	public int getViewTypeCount()
	{
		return 1;
	}

	@Override
	public boolean isEmpty()
	{
		return names.isEmpty();
	}
}
