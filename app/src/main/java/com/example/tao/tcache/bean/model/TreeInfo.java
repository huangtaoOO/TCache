package com.example.tao.tcache.bean.model;

import com.example.tao.tcache.bean.db.Tree;

import java.util.List;

/**
 * @author wangyz
 * @time 2019/1/23 10:47
 * @description TreeInfo
 */
public class TreeInfo {

    public int parentId;

    public int treeId;

    public String name;

    public List<Tree> child;

}
