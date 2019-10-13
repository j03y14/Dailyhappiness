package com.example.dailyhappiness;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.dailyhappiness.databinding.ActivityCreateAccountBindingImpl;
import com.example.dailyhappiness.databinding.ActivityLoginBindingImpl;
import com.example.dailyhappiness.databinding.ActivityMainBindingImpl;
import com.example.dailyhappiness.databinding.ActivityWriteReviewBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCREATEACCOUNT = 1;

  private static final int LAYOUT_ACTIVITYLOGIN = 2;

  private static final int LAYOUT_ACTIVITYMAIN = 3;

  private static final int LAYOUT_ACTIVITYWRITEREVIEW = 4;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(4);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.dailyhappiness.R.layout.activity_create_account, LAYOUT_ACTIVITYCREATEACCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.dailyhappiness.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.dailyhappiness.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.dailyhappiness.R.layout.activity_write_review, LAYOUT_ACTIVITYWRITEREVIEW);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCREATEACCOUNT: {
          if ("layout/activity_create_account_0".equals(tag)) {
            return new ActivityCreateAccountBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_create_account is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYWRITEREVIEW: {
          if ("layout/activity_write_review_0".equals(tag)) {
            return new ActivityWriteReviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_write_review is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(3);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "activity");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(4);

    static {
      sKeys.put("layout/activity_create_account_0", com.example.dailyhappiness.R.layout.activity_create_account);
      sKeys.put("layout/activity_login_0", com.example.dailyhappiness.R.layout.activity_login);
      sKeys.put("layout/activity_main_0", com.example.dailyhappiness.R.layout.activity_main);
      sKeys.put("layout/activity_write_review_0", com.example.dailyhappiness.R.layout.activity_write_review);
    }
  }
}
