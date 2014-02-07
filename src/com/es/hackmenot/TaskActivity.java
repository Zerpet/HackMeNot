package com.es.hackmenot;

import java.util.List;

import com.es.hackmenot.database.Permission;
import com.es.hackmenot.database.PermissionsDataSource;

import uc3m.hackmenot.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        Intent intent = getIntent();
        String package_ = intent.getStringExtra("package");
        
        View linearLayout =  findViewById(R.id.wrapper_task);
        PackageManager pm = getPackageManager();
        ApplicationInfo ai;
		try {
			ai = pm.getApplicationInfo(package_, 0);
		} catch (final NameNotFoundException e) {
			ai = null;
		}
		try {
			PackageInfo packageInfo 				= pm.getPackageInfo(package_, PackageManager.GET_PERMISSIONS);
			String applicationName 					= (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
			Drawable applicationIcon 				= pm.getApplicationIcon(ai);		
			PermissionsDataSource permission_data 	= new PermissionsDataSource(getBaseContext());
			permission_data.open();
			List<Permission> permissions_db 		= permission_data.getAllPermissions();
			
			TextView name = new TextView(this);
			name.setText(applicationName);
			
			ImageView image = new ImageView(this);
			image.setImageDrawable(applicationIcon);
			
			LinearLayout app 			= new LinearLayout(this);
			app.setOrientation(LinearLayout.HORIZONTAL);
			app.addView(image);
			app.addView(name);
			
			((LinearLayout) linearLayout).addView(app);
			
			
			String[] requestedPermissions = packageInfo.requestedPermissions;
			if(requestedPermissions != null) {
				for (int i = 0; i < requestedPermissions.length; i++) {
					String[] aux_tag 		= requestedPermissions[i].split("\\.");
					String permission_tag 	= aux_tag[aux_tag.length > 1 ? aux_tag.length - 1 :  0];
					int id = 0;
					
					for(Permission per :  permissions_db){
						if(per.getPermission().equals(permission_tag)){
							id = per.getId();
						}
					}
					
					TextView name_permission = new TextView(this);
					name_permission.setText(permission_tag);
					
					LinearLayout aux 			= new LinearLayout(this);
					aux.setOrientation(LinearLayout.HORIZONTAL);
					aux.addView(name_permission);
					aux.setId(id);
					aux.setPadding(50, 10, 0, 10);
					aux.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							PermissionsDataSource permission_data = new PermissionsDataSource(v.getContext());
							permission_data.open();
							AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
							builder.setMessage(permission_data.getPermissionById(v.getId()).getDescription())
							       .setTitle(R.string.description);
							AlertDialog dialog = builder.create();		
							dialog.show();
						}
					});
					
					
					((LinearLayout) linearLayout).addView(aux);
				}
			}
			
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public static class DescripcionFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.accept)
                   .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           dialog.dismiss();
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
    
}
