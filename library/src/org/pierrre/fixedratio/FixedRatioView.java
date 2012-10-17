package org.pierrre.fixedratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class FixedRatioView extends FrameLayout {
	private float ratio = 0;
	
	private static final int RESIZE_POLICY_CROP = 1;
	private static final int RESIZE_POLICY_FIT = 2;
	private static final int RESIZE_POLICY_WIDTH = 3;
	private static final int RESIZE_POLICY_HEIGHT = 4;
	private int resizePolicy = FixedRatioView.RESIZE_POLICY_CROP;
	
	public FixedRatioView(Context context) {
		super(context);
	}
	
	public FixedRatioView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.initWithAttributeSet(context, attrs);
	}
	
	public FixedRatioView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		this.initWithAttributeSet(context, attrs);
	}
	
	private void initWithAttributeSet(Context context, AttributeSet attrs) {
		TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.FixedRatioView);
		
		// Ratio
		float ratio = styledAttributes.getFloat(R.styleable.FixedRatioView_ratio, 0);
		if (ratio != 0) {
			this.setRatio(ratio);
		} else {
			int ratioHorizontal = styledAttributes.getInt(R.styleable.FixedRatioView_ratio_horizontal, 0);
			int ratioVertical = styledAttributes.getInt(R.styleable.FixedRatioView_ratio_vertical, 0);
			if ((ratioHorizontal != 0) && (ratioVertical != 0)) {
				this.setRatio(ratioHorizontal, ratioVertical);
			} else {
				throw new IllegalStateException("Ratio is not set");
			}
		}
		
		// Resize policy
		int resizePolicy = styledAttributes.getInt(R.styleable.FixedRatioView_resize_policy, 0);
		if (resizePolicy != 0) {
			this.setResizePolicy(resizePolicy);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (this.ratio == 0) {
			throw new IllegalStateException("Ratio is not set");
		}
		
		int width = View.MeasureSpec.getSize(widthMeasureSpec);
		int height = View.MeasureSpec.getSize(heightMeasureSpec);
		
		if (this.resizePolicy == FixedRatioView.RESIZE_POLICY_CROP || this.resizePolicy == FixedRatioView.RESIZE_POLICY_FIT) {
			if (height != 0) {
				float ratio = (float) width / (float) height;
				
				if (this.resizePolicy == FixedRatioView.RESIZE_POLICY_CROP) {
					if (ratio > this.ratio) {
						width = this.resizeWidth(height);
					} else {
						height = this.resizeHeight(width);
					}
				} else if (this.resizePolicy == FixedRatioView.RESIZE_POLICY_FIT) {
					if (ratio > this.ratio) {
						height = this.resizeHeight(width);
					} else {
						width = this.resizeWidth(height);
					}
				}
			}
		} else if (this.resizePolicy == FixedRatioView.RESIZE_POLICY_WIDTH) {
			width = this.resizeWidth(height);
		} else if (this.resizePolicy == FixedRatioView.RESIZE_POLICY_HEIGHT) {
			height = this.resizeHeight(width);
		}
		
		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
		
		this.setMeasuredDimension(width, height);
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	private int resizeWidth(int height) {
		return (int) (height * this.ratio);
	}
	
	private int resizeHeight(int width) {
		return (int) (width / this.ratio);
	}
	
	public void setRatio(float ratio) {
		if (ratio <= 0) {
			throw new IllegalArgumentException("Ratio should be greater than 0");
		}
		
		this.ratio = ratio;
		
		this.requestLayout();
	}
	
	public void setRatio(int ratioHorizontal, int ratioVertical) {
		if (ratioHorizontal <= 0) {
			throw new IllegalArgumentException("Ratio horizontal should be greater than 0");
		}
		
		if (ratioVertical <= 0) {
			throw new IllegalArgumentException("Ratio vertical should be greater than 0");
		}
		
		this.setRatio((float) ratioHorizontal / ratioVertical);
	}
	
	public void setResizePolicy(int resizePolicy) {
		if (resizePolicy < FixedRatioView.RESIZE_POLICY_CROP || resizePolicy > FixedRatioView.RESIZE_POLICY_HEIGHT) {
			throw new IllegalArgumentException("Invalid resize policy");
		}
		
		this.resizePolicy = resizePolicy;
		
		this.requestLayout();
	}
}
