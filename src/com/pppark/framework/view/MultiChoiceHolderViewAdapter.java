package com.pppark.framework.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.pppark.framework.data.BasePo;

/**
 * 支持多选模式的HolderViewAdapter
 * @Package com.xunlei.video.framework
 * @ClassName: MultiChoiceHolderViewAdapter
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date May 20, 2014 12:29:38 AM
 */
public class MultiChoiceHolderViewAdapter extends HolderViewAdapter implements IMultiChoiceAdapter {

    private boolean isCheckMode = false;

    public MultiChoiceHolderViewAdapter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        BaseHolderView holderView = (BaseHolderView) super.getView(position, convertView, viewGroup);
        if(holderView instanceof BaseMultiChoiceHolderView){
            BaseMultiChoiceHolderView multiView = (BaseMultiChoiceHolderView)holderView;
            multiView.setChecked(getItem(position).isChecked());
            multiView.onCheckModeChange(isCheckMode);
        }
        return holderView;
    }
    
    public boolean isCheckMode() {
        return isCheckMode;
    }

    public void check(int position){
        getItem(position).setChecked(true);
        notifyDataSetChanged();
    }
    
    public boolean isChecked(int position){
        return getItem(position).isChecked();
    }
    
    public void unCheck(int position){
        getItem(position).setChecked(false);
        notifyDataSetChanged();
    }
    
    public void toggle(int position){
        if(isChecked(position)){
            unCheck(position);
        }else{
            check(position);
        }
    }

    public void checkAll(){
        for(BasePo po : super.data){
            po.setChecked(true);
        }
        notifyDataSetChanged();
    }

    public void unCheckAll(){
        for(BasePo po : super.data){
            po.setChecked(false);
        }
        notifyDataSetChanged();
    }
    
    public void openCheckMode(){
        isCheckMode = true;
        notifyDataSetChanged();
    }

    public void cancelCheckMode(){
        isCheckMode = false;
        unCheckAll();
    }
    
    public List<BasePo> getAllCheckedList(){
        List<BasePo> checkedPo = new ArrayList<BasePo>();
        for(BasePo po : super.data){
            if(po.isChecked()){
                checkedPo.add(po);
            }
        }
        return checkedPo;
    }

}
