package com.green.util.rxview.cardstack.tools;

import android.animation.ObjectAnimator;
import android.view.View;

import com.green.util.rxview.cardstack.RxCardStackView;


/**
 * @author vondear
 * @date 2018/6/11 11:36:40 整合修改
 */
public class RxAdapterAllMoveDownAnimator extends RxAdapterAnimator {

    public RxAdapterAllMoveDownAnimator(RxCardStackView rxCardStackView) {
        super(rxCardStackView);
    }

    @Override
    protected void itemExpandAnimatorSet(final RxCardStackView.ViewHolder viewHolder, int position) {
        final View itemView = viewHolder.itemView;
        itemView.clearAnimation();
        ObjectAnimator oa = ObjectAnimator.ofFloat(itemView, View.Y, itemView.getY(), mRxCardStackView.getScrollY() + mRxCardStackView.getPaddingTop());
        mSet.play(oa);
        int collapseShowItemCount = 0;
        for (int i = 0; i < mRxCardStackView.getChildCount(); i++) {
            int childTop;
            if (i == mRxCardStackView.getSelectPosition()) {
                continue;
            }
            final View child = mRxCardStackView.getChildAt(i);
            child.clearAnimation();
            if (i > mRxCardStackView.getSelectPosition() && collapseShowItemCount < mRxCardStackView.getNumBottomShow()) {
                childTop = mRxCardStackView.getShowHeight() - getCollapseStartTop(collapseShowItemCount) + mRxCardStackView.getScrollY();
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
                collapseShowItemCount++;
            } else {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), mRxCardStackView.getShowHeight() + mRxCardStackView.getScrollY());
                mSet.play(oAnim);
            }
        }
    }

    @Override
    protected void itemCollapseAnimatorSet(RxCardStackView.ViewHolder viewHolder) {
        int childTop = mRxCardStackView.getPaddingTop();
        for (int i = 0; i < mRxCardStackView.getChildCount(); i++) {
            View child = mRxCardStackView.getChildAt(i);
            child.clearAnimation();
            final RxCardStackView.LayoutParams lp =
                    (RxCardStackView.LayoutParams) child.getLayoutParams();
            childTop += lp.topMargin;
            if (i != 0) {
                childTop -= mRxCardStackView.getOverlapGaps() * 2;
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
            } else {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                mSet.play(oAnim);
            }
            childTop += lp.mHeaderHeight;
        }
    }

}
