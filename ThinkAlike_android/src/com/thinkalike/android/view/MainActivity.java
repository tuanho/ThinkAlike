/**
* Copyright 2013-2014 Tiancheng Hu
* 
* Licensed under the GNU Lesser General Public License, version 3.0 (LGPL-3.0, the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*     http://opensource.org/licenses/lgpl-3.0.html
*     
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.thinkalike.android.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.thinkalike.R;
import com.thinkalike.android.ThinkAlikeApp;
import com.thinkalike.android.common.UncaughtExceptionHandler;
import com.thinkalike.generic.common.LogTag;
import com.thinkalike.generic.common.Util;

/**
 * This activity has different presentations for handset and tablet-size devices.
 * <p>
 * The activity makes heavy use of fragments. The list of resource is a
 * {@link NodeSelectorFragment} and the work area is a {@link WorkareaFragment}.
 * <p>
 * This activity also implements the required {@link NodeSelectorFragment.FragmentCallbacks}
 * interface to listen for resource drag&drop.
 */
public class MainActivity extends FragmentActivity {

	//-- Constants and Enums ----------------------------------------------
	private final static int DIALOG_ONCLOSE = 99;

	//-- Inner Classes and Structures -------------------------------------
	//-- Delegates and Events ---------------------------------------------
	//-- Instance and Shared Fields ---------------------------------------
	protected UncaughtExceptionHandler _ueh = new UncaughtExceptionHandler(this);
	/**
	 * Whether or not the screen size is large enough to contain a two-pane mode activity. i.e. when running on a tablet device.
	 */
	private boolean _isLargeScreen; //IMPROVE: manage with onSaveInstanceState()

	//-- Properties -------------------------------------------------------
	//-- Constructors -----------------------------------------------------
	//-- Destructors ------------------------------------------------------
	//-- Base Class Overrides ---------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onCreate", getClass().getSimpleName()));
		ThinkAlikeApp.getInstance().registerUIContext(this); //Application.getUIContext() will be used as uiContext by ViewModel in some case, which happens before onResume(). 
		super.onCreate(savedInstanceState);
		_ueh.initialize();
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		if (findViewById(R.id.ll_twopane) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			_isLargeScreen = true;
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	@Override
	protected void onResume() {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onResume", getClass().getSimpleName()));
		ThinkAlikeApp.getInstance().registerUIContext(this);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onDestroy", getClass().getSimpleName()));
		_ueh.restoreOriginalHandler();
		_ueh = null;
		ThinkAlikeApp.getInstance().unregisterUIContext(this);
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBackPressed() {
		this.showDialog(DIALOG_ONCLOSE); //for simplicity. IMPROVE: use DialogFragment instead
		//super.onBackPressed();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog=null;
		switch (id){
		case DIALOG_ONCLOSE:
			new AlertDialog.Builder(this)
			.setMessage(this.getResources().getString(R.string.close_confirm))
			.setCancelable(false)
			.setPositiveButton(R.string.btn_OK, 
		    		new DialogInterface.OnClickListener(){
		             	public void onClick(DialogInterface dialog, int id){
		             		MainActivity.this.finish();
		             	}
		           	})
		    .setNegativeButton(R.string.btn_Cancel, null)
			.show();
            break;
		default:
			break;
		}
		return dialog;
	}

	//-- Public and internal Methods --------------------------------------
	//-- Private and Protected Methods ------------------------------------
	//-- Event Handlers ---------------------------------------------------

}
