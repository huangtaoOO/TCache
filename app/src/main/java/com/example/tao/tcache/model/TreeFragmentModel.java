package com.example.tao.tcache.model;

import android.annotation.TargetApi;
import android.os.Build;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.tao.tcache.base.BaseModel;
import com.example.tao.tcache.bean.db.Tree;
import com.example.tao.tcache.bean.model.TreeInfo;
import com.example.tao.tcache.contract.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class TreeFragmentModel extends BaseModel implements Contract.TreeFragmentModel {

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public Observable<List<TreeInfo>> load() {
        Observable<List<TreeInfo>> loadFromLocal = Observable.create(emitter -> {
            List<Tree> list = LitePal.where("parentId=?", "0").find(Tree.class);
            if (list != null && list.size() > 0) {
                List<TreeInfo> trees = new ArrayList<>();
                list.stream().forEach(t -> {
                    TreeInfo model = new TreeInfo();
                    model.parentId = t.parentId;
                    model.treeId = t.treeId;
                    model.name = t.name;
                    model.child = LitePal.where("parentId=?", t.treeId + "").find(Tree.class);
                    trees.add(model);
                });
                emitter.onNext(trees);
            }
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<TreeInfo>> loadFromNet = doLoadFromNet();
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    @Override
    public Observable<List<TreeInfo>> refresh() {
        return doLoadFromNet();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Observable<List<TreeInfo>> doLoadFromNet() {
        return mApi.loadTree().filter(a -> a.getErrorCode() == 0).map(a -> {
            LitePal.deleteAll(Tree.class);
            List<TreeInfo> trees = new ArrayList<>();
            List<Tree> list = new ArrayList<>();
            a.getData().stream().forEach(d -> {
                Tree tree = new Tree();
                tree.treeId = d.getId();
                tree.parentId = d.getParentChapterId();
                tree.name = d.getName();
                list.add(tree);

                TreeInfo model = new TreeInfo();
                model.parentId = d.getParentChapterId();
                model.treeId = d.getId();
                model.name = d.getName();
                List<Tree> child = new ArrayList<>();
                d.getChildren().stream().forEach(c -> {
                    Tree t = new Tree();
                    t.parentId = c.getParentChapterId();
                    t.treeId = c.getId();
                    t.name = c.getName();
                    child.add(t);
                });
                model.child = child;
                trees.add(model);
            });
            LitePal.saveAll(list);
            return trees;
        });
    }
}
