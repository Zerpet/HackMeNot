package com.es.hackmenot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.es.hackmenot.adapter.AndroidApp;
import com.es.hackmenot.adapter.CustomListViewAdapter;
import com.es.hackmenot.database.Permission;
import com.es.hackmenot.database.PermissionsDataSource;

import uc3m.hackmenot.R;

import android.content.Intent;
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

	private List<AndroidApp> rowItems;
	private List<Permission> perms;
	private PermissionsDataSource datasource;
	public static String tab = "tab";
	private int security_level;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		security_level = args.getInt(tab);

		datasource = new PermissionsDataSource(getActivity());
		datasource.open();
		perms = datasource.getAllPermissions();

		PackageManager pm = getActivity().getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);

		rowItems = new ArrayList<AndroidApp>();
		for (ApplicationInfo applicationInfo : packages) {

			ApplicationInfo ai;
			try {
				ai = pm.getApplicationInfo(applicationInfo.packageName, 0);
			} catch (final NameNotFoundException e) {
				ai = null;
			}
			PackageInfo packageInfo;
			try {
				packageInfo = pm.getPackageInfo(applicationInfo.packageName,
						PackageManager.GET_PERMISSIONS);

				String applicationPackage = applicationInfo.packageName;
				// Filtrado de apps del sistema
				if (!applicationPackage.contains("com.android")
						&& !applicationPackage.contains("com.sec")) {

					String applicationName = (String) (ai != null ? pm
							.getApplicationLabel(ai) : "(unknown)");

					Drawable applicationIcon = pm.getApplicationIcon(ai);
					String[] applicationPermissions = packageInfo.requestedPermissions;

					AndroidApp item = new AndroidApp(applicationIcon,
							applicationName, applicationPackage,
							applicationPermissions);

					int app_security_level = getAppThreatLevel(item);
					if (app_security_level == security_level) {
						rowItems.add(item);
					}
				}

			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		CustomListViewAdapter adapter = new CustomListViewAdapter(
				getActivity(), R.layout.list_item, rowItems);
		setListAdapter(adapter);
	}

	private int getAppThreatLevel(AndroidApp app) {
		String[] appPerms = app.getPerms();

		int highsec = 0;
		int medsec = 0;
		int lowsec = 0;
		int nullsec = 0;

		if (appPerms == null) {
			return 1;
		}

		for (int i = 0; i < appPerms.length; i++) {
			Iterator<Permission> iterator = perms.iterator();
			while (iterator.hasNext()) {
				Permission per_aux = iterator.next();
				String permisobbdd = per_aux.getPermission();
				String permisoapp = appPerms[i];
				String[] aux_tag = permisoapp.split("\\.");
				permisoapp = aux_tag[aux_tag.length > 1 ? aux_tag.length - 1: 0];

				if (permisobbdd.equals(permisoapp)) {
					switch (per_aux.getThreatLevel()) {
					case 3:
						highsec++;
						break;
					case 2:
						medsec++;
						break;
					case 1:
						lowsec++;
						break;
					default:
						nullsec++;
						break;
					}
					break;
				}
			}
		}
		
		if(highsec > 3){
			return 3;
		}
		if(highsec > 1){
			return 2;
		}
		return 1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View appList = inflater.inflate(R.layout.fragment_app_list, container,
				false);

		return appList;
	}

	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
		AndroidApp row = (AndroidApp) list.getItemAtPosition(position);
		Intent intent = new Intent(getActivity().getBaseContext(),
				TaskActivity.class);
		intent.putExtra("package", row.getPkg());
		startActivity(intent);

	}
}