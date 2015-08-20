package com.dhcc.activity;

import java.util.ArrayList;
import java.util.List;

import com.dhcc.adapter.FragmentAdapter;
import com.dhcc.util.GetBitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
public class MainActivity extends FragmentActivity implements OnClickListener{
	public static final int TAB_CONTACT = 0;
	public static final int TAB_ANALYSIS = 1;
	public static final int TAB_HOME = 2;
	public static final int TAB_PRODUCT = 3;
	public static final int TAB_SETTING = 4;

	private ViewPager viewPager;
	private View main_tab_analysis, main_tab_home,
			main_tab_product, main_tab_setting;
	private View main_tab_contact;
	private int height;
	private View bottmGroup;
	private FragmentAdapter adapter; 
	protected static final String TAG = "MainActivity";
	private List<Fragment> Fragments=new ArrayList<Fragment>();
	ContactActivity contact=new ContactActivity();
	NewsActivity analysis=new NewsActivity();
	HomeActivity home=new HomeActivity();
	ProductActivity Product=new ProductActivity();
	SettingActivity Setting=new SettingActivity();
	 LayoutInflater mLayoutInflater;  
	 private GetBitmap getBitmap=new GetBitmap();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//获取屏幕真是高宽
		DisplayMetrics metric = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metric);
	    height = metric.widthPixels;  // 屏幕宽度（像素）
	    bottmGroup=findViewById(R.id.main_radiogroup);
		LayoutParams Prams_h=bottmGroup.getLayoutParams();
		Prams_h.height=160*height/1100;
		bottmGroup.setLayoutParams(Prams_h);
		
		initView();
		addListener();
	}

	private void initView() {
		 mLayoutInflater = getLayoutInflater();  
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		main_tab_contact= findViewById(R.id.bottom_contact); 
		main_tab_analysis= findViewById(R.id.bottom_analysis); 
		main_tab_home= findViewById(R.id.bottom_home); 
		main_tab_product= findViewById(R.id.bottom_product);  
		main_tab_setting=findViewById(R.id.bottom_setting); 

	
		main_tab_contact.setOnClickListener(this);
		main_tab_analysis.setOnClickListener(this);
		main_tab_home.setOnClickListener(this);
		main_tab_setting.setOnClickListener(this);
		main_tab_product.setOnClickListener(this);

		Fragments.add(contact);
		Fragments.add(analysis);
		Fragments.add(home);
		Fragments.add(Product);
		Fragments.add(Setting);
		
		adapter = new FragmentAdapter(
				getSupportFragmentManager(),viewPager,Fragments);
		viewPager.setOffscreenPageLimit(1);
		viewPager.setAdapter(adapter);
		
		viewPager.setCurrentItem(1);
		main_tab_analysis.setPressed(true);
		clearBottom();
		setBottom(1);
	}

	public void clearBottom() {
		// TODO Auto-generated method stub
		main_tab_contact.setBackgroundResource(R.drawable.contact_n1);
		main_tab_analysis.setBackgroundResource(R.drawable.analysis_n1);
		main_tab_home.setBackgroundResource(R.drawable.home_n1);
		main_tab_product.setBackgroundResource(R.drawable.product_n1);
		main_tab_setting.setBackgroundResource(R.drawable.setting_n1);
	}
	public void setBottom(int i){
		switch (i) {
		case 0:
			main_tab_contact.setBackgroundResource(R.drawable.contact_a1);
			break;
		case 1:
			main_tab_analysis.setBackgroundResource(R.drawable.analysis_a1);		
			break;
		case 2:
			main_tab_home.setBackgroundResource(R.drawable.home_a1);
			break;
		case 3:
			main_tab_product.setBackgroundResource(R.drawable.product_a1);
			break;
		case 4:
			main_tab_setting.setBackgroundResource(R.drawable.setting_a1);
			break;
		default:
			break;
		}
	}
	private void addListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int id) {
				clearBottom();
				switch (id) {
				case TAB_CONTACT:
					setBottom(0);
					break;
				case TAB_ANALYSIS:
					setBottom(1);
					break;
				case TAB_HOME:
					setBottom(2);
					break;
				case TAB_PRODUCT:
					setBottom(3);
					break;
				case TAB_SETTING:
					setBottom(4);
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK )  
        {
			return false;
        }
        return super.onKeyDown(keyCode, event);
    }
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.bottom_contact:
			setBottom(0);
			viewPager.setCurrentItem(TAB_CONTACT);
			break;
		case R.id.bottom_analysis:		
			setBottom(1);
			viewPager.setCurrentItem(TAB_ANALYSIS);
			break;
		case R.id.bottom_home:
			setBottom(2);
			viewPager.setCurrentItem(TAB_HOME);
			break;
		case R.id.bottom_product:
			setBottom(3);
			viewPager.setCurrentItem(TAB_PRODUCT);
			break;
		case R.id.bottom_setting:
			setBottom(4);
			viewPager.setCurrentItem(TAB_SETTING);
			break;

		default:
			break;
		}		
	}

/*
 *   btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment_Two fragment_two = new Fragment_Two();
                ft.replace(R.id.fl_content, fragment_two, MainActivity.TAG);
                ft.commit();
                setButton(v);

            }
        });

 * */
}
