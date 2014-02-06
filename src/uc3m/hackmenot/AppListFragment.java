package uc3m.hackmenot;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AppListFragment extends ListFragment {

	private String app_name[];
	private String app_package[];
	private Drawable app_icon[];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PackageManager pm = getActivity().getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		app_name = new String[packages.size()];
		app_package = new String[packages.size()];
		app_icon = new Drawable[packages.size()];
		
		
		int j = 0;
		for (ApplicationInfo applicationInfo : packages) {

			ApplicationInfo ai;
			try {
				ai = pm.getApplicationInfo(applicationInfo.packageName, 0);
			} catch (final NameNotFoundException e) {
				ai = null;
			}

			String applicationName = (String) (ai != null ? pm
					.getApplicationLabel(ai) : "(unknown)");
			String applicationPackage = applicationInfo.packageName;
			Drawable applicationIcon = pm.getApplicationIcon(ai);
			
			app_name[j] = applicationName;
			app_package[j] = applicationPackage;
			app_icon[j] = applicationIcon;
			j++;
			
			//Log.v("test", "App: " + applicationName + " Package: "+ applicationInfo.packageName + " " + packages.size());			
			/*PackageInfo packageInfo = pm.getPackageInfo(
					applicationInfo.packageName,
					PackageManager.GET_PERMISSIONS);

			String[] requestedPermissions = packageInfo.requestedPermissions;
			if (requestedPermissions != null) {
				for (int i = 0; i < requestedPermissions.length; i++) {
					Log.d("test", requestedPermissions[i]);
				}
			}*/
		}

		ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, app_name);
		setListAdapter(listAdapter);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View appList = inflater.inflate(R.layout.fragment_app_list, container, false);
		
		return appList;
	}

	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
		Toast.makeText(getActivity(), app_package[position], Toast.LENGTH_LONG).show();
	
	}
}