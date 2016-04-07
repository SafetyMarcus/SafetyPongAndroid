package hoopray.safetypongandroid;

import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * @author Marcus Hooper
 */
public abstract class SingleUpdateListener implements ValueEventListener
{
	@Override
	public void onCancelled(FirebaseError firebaseError)
	{
	}
}
