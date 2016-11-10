/*
 * (c) 2016 Novetta
 *
 * Created by mike
 */
package com.github.mike10004.xvfbmanager;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class ListTreeNode<T> implements TreeNode<T> {

    private TreeNode<T> parent;
    private final T label;
    private final List<TreeNode<T>> children;

    public ListTreeNode(T label) {
        this.label = checkNotNull(label, "label must be non-null");
        children = new ArrayList<>();
    }

    @Override
    public T getLabel() {
        return label;
    }

    @Override
    public Iterable<TreeNode<T>> children() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public int getLevel() {
        int level = 0;
        TreeNode<T> node = parent;
        while (node != null) {
            level++;
            node = node.getParent();
        }
        return level;
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode<T> getParent() {
        return parent;
    }

    @Override
    public TreeNode<T> setParent(TreeNode<T> parent) {
        TreeNode<T> oldParent = this.parent;
        this.parent = parent;
        checkState(Iterables.contains(parent.children(), this), "setParent can only be called on child nodes");
        return oldParent;
    }

    @Override
    public ListTreeNode<T> addChild(TreeNode<T> child) {
        children.add(child);
        child.setParent(this);
        return this;
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public Iterator<T> iterator() {
        Function<TreeNode<T>, T> labelGetter = TreeNode.Utils.labelFunction();
        return Iterators.transform(TreeNode.Utils.<T>traverser().breadthFirstTraversal(this).iterator(), labelGetter);
    }

    @Override
    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    @Override
    public String toString() {
        int childCount = getChildCount();
        boolean leaf = childCount == 0;
        return "ListTreeNode{" +
                (isRoot() ? "root" : (leaf ? "leaf" : "node")) +
                ", label=" + label +
                ", children.size=" + childCount +
                '}';
    }
}
