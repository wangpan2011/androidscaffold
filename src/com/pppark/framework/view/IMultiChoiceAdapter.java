package com.pppark.framework.view;

import java.util.List;

import com.pppark.framework.data.BasePo;


interface IMultiChoiceAdapter {
    
    /**
     * 是否开启多选模式
     * @Title: isCheckMode
     * @return
     * @return boolean
     * @date May 22, 2014 10:32:46 AM
     */
    public boolean isCheckMode();

    /**
     * 选中某一项
     * @Title: check
     * @param position
     * @return void
     * @date May 20, 2014 12:25:43 AM
     */
    public void check(int position);
    
    /**
     * 取消选中某一项
     * @Title: unCheck
     * @param position
     * @return void
     * @date May 20, 2014 12:25:59 AM
     */
    public void unCheck(int position);

    /**
     * 选中所有
     * @Title: checkAll
     * @return void
     * @date May 20, 2014 12:26:43 AM
     */
    public void checkAll();
    
    /**
     * 取消选中所有
     * @Title: unCheckAll
     * @return void
     * @date May 20, 2014 12:26:52 AM
     */
    public void unCheckAll();
    
    /**
     * 打开多选模式
     * @Title: openCheckMode
     * @return void
     * @date May 20, 2014 5:29:40 PM
     */
    public void openCheckMode();
    
    /**
     * 取消多选模式
     * @Title: cancelCheckMode
     * @return void
     * @date May 20, 2014 12:27:12 AM
     */
    public void cancelCheckMode();
    
    /**
     * 获得所有选中项的BasePo集合
     * @Title: getAllCheckedList
     * @return List<BasePo>
     * @date May 20, 2014 12:27:21 AM
     */
    public List<BasePo> getAllCheckedList();

}
