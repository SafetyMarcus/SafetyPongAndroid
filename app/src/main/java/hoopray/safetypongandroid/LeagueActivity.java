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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;

import static hoopray.safetypongandroid.DeepLinkProcessor.LEAGUE_ID;
import static hoopray.safetypongandroid.FirebaseConstants.FIREBASE_PATH;
import static hoopray.safetypongandroid.FirebaseConstants.LEAGUES;
import static hoopray.safetypongandroid.FirebaseConstants.NAME;
import static hoopray.safetypongandroid.FirebaseConstants.PLAYER;

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
    private String name = "";

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

		new Firebase(FIREBASE_PATH).child(LEAGUES).child(SafetyApplication.getInstance().currentLeagueKey).addListenerForSingleValueEvent(new SingleUpdateListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				for(DataSnapshot snapshot : dataSnapshot.getChildren())
				{
					if(NAME.equalsIgnoreCase(snapshot.getKey()))
					{
						name = (String) snapshot.getValue();
						break;
					}
				}

				if(DeepLinkProcessor.result != null && DeepLinkProcessor.result.first == DeepLinkProcessor.LEAGUE_INVITE)
                {
                    FirebaseHelper.addUserToLeague(SafetyApplication.getInstance().currentLeagueKey, name, PLAYER);
                    DeepLinkProcessor.result = null;
                }
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(Menu.NONE, 0, Menu.NONE, R.string.invite).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId() == 0)
		{
            String title = getString(R.string.league_link_title, name);
            String description = getString(R.string.league_link_description, FirebaseHelper.getAuthDisplayName());
			BranchUniversalObject branchUniversalObject = new BranchUniversalObject()
					.setCanonicalIdentifier("league/" + SafetyApplication.getInstance().currentLeagueKey)
					.setTitle(title)
					.setContentDescription(description)
					.addContentMetadata(LEAGUE_ID, SafetyApplication.getInstance().currentLeagueKey);
			LinkProperties properties = new LinkProperties();
			properties.addControlParameter(LEAGUE_ID, SafetyApplication.getInstance().currentLeagueKey);
			branchUniversalObject.showShareSheet(this, properties, new ShareSheetStyle(this, title, description), new Branch.BranchLinkShareListener()
			{
				@Override
				public void onShareLinkDialogLaunched()
				{
				}

				@Override
				public void onShareLinkDialogDismissed()
				{
				}

				@Override
				public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error)
				{
					Log.d("pong", "link shared: " + sharedLink + " channel: " + sharedChannel);
				}

				@Override
				public void onChannelSelected(String channelName)
				{

				}
			});
		}
		return super.onOptionsItemSelected(item);
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
