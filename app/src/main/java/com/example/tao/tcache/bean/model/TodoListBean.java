package com.example.tao.tcache.bean.model;

import java.util.List;

/**
 * @author: tao
 * @time: 2019/5/7
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class TodoListBean {


    /**
     * curPage : 1
     * datas : [{"completeDate":null,"completeDateStr":"","content":"122121","date":1557072000000,"dateStr":"2019-05-06","id":9916,"priority":0,"status":0,"title":"and","type":1,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"大萨达","date":1557072000000,"dateStr":"2019-05-06","id":9917,"priority":0,"status":0,"title":"大地","type":1,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"1","date":1557072000000,"dateStr":"2019-05-06","id":9923,"priority":0,"status":0,"title":"1","type":0,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"2","date":1557072000000,"dateStr":"2019-05-06","id":9924,"priority":0,"status":0,"title":"2","type":0,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"3","date":1557072000000,"dateStr":"2019-05-06","id":9925,"priority":0,"status":0,"title":"3","type":0,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"4","date":1557072000000,"dateStr":"2019-05-06","id":9926,"priority":0,"status":0,"title":"4","type":0,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"5","date":1557072000000,"dateStr":"2019-05-06","id":9927,"priority":0,"status":0,"title":"5","type":0,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"6","date":1557072000000,"dateStr":"2019-05-06","id":9928,"priority":0,"status":0,"title":"6","type":0,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"7","date":1557072000000,"dateStr":"2019-05-06","id":9929,"priority":0,"status":0,"title":"7","type":0,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"8","date":1557072000000,"dateStr":"2019-05-06","id":9930,"priority":0,"status":0,"title":"8","type":0,"userId":21570},{"completeDate":null,"completeDateStr":"","content":"9","date":1557072000000,"dateStr":"2019-05-06","id":9931,"priority":0,"status":0,"title":"9","type":0,"userId":21570}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 11
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<TodoBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TodoBean> getDatas() {
        return datas;
    }

    public void setDatas(List<TodoBean> datas) {
        this.datas = datas;
    }
}
