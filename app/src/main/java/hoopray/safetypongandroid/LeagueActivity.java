package hoopray.safetypongandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;

import java.lang.ref.WeakReference;

public class LeagueActivity extends AppCompatActivity implements PlusFragmentManager
{
	@Bind(R.id.fab)
	FloatingActionButton fab;
	@Bind(R.id.container)
	ViewPager mViewPager;
	@Bind(R.id.tabs)
	TabLayout tabLayout;

	private WeakReference<PlusFragment> plusFragmentReference = new WeakReference<>(null);
	private ViewPager.OnPageChangeListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_league);
		ButterKnife.bind(this);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager.setAdapter(mSectionsPagerAdapter);
		tabLayout.setupWithViewPager(mViewPager);
		listener = new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{
			}

			@Override
			public void onPageSelected(int position)
			{
				plusFragmentReference = new WeakReference<>((PlusFragment) getSupportFragmentManager()
						.findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem()));
				fab.setImageDrawable(plusFragmentReference.get().getPlusDrawable());
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{
			}
		};

		mViewPager.addOnPageChangeListener(listener);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				plusFragmentReference.get().onPlusClicked();
			}
		});
	}

	@Override
	public void instantiatePlus()
	{
		listener.onPageSelected(0);
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{
		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			switch(position)
			{
				default:
				case 0:
					return new PlayerListFragment();
				case 1:
					return new GameListFragment();
			}
		}

		@Override
		public int getCount()
		{
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			switch(position)
			{
				case 0:
					return getString(R.string.ladder);
				case 1:
					return getString(R.string.games);
			}
			return null;
		}
	}
}
