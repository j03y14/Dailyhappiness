package com.example.dailyhappiness.databinding;
import com.example.dailyhappiness.R;
import com.example.dailyhappiness.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityWriteReviewBindingImpl extends ActivityWriteReviewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvMission, 1);
        sViewsWithIds.put(R.id.tvReview, 2);
        sViewsWithIds.put(R.id.ratingBar, 3);
        sViewsWithIds.put(R.id.edtInputReview, 4);
        sViewsWithIds.put(R.id.imgPhoto, 5);
        sViewsWithIds.put(R.id.imgBtnOK, 6);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityWriteReviewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private ActivityWriteReviewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[4]
            , (android.widget.ImageButton) bindings[6]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.RatingBar) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.activity == variableId) {
            setActivity((com.example.dailyhappiness.WriteReviewActivity) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setActivity(@Nullable com.example.dailyhappiness.WriteReviewActivity Activity) {
        this.mActivity = Activity;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): activity
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}