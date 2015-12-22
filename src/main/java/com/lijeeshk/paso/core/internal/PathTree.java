package com.lijeeshk.paso.core.internal;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by lijeesh on 21/12/15.
 */
@ToString
public class PathTree<E> {

    @AllArgsConstructor
    @ToString
    class Node {
        final ConcurrentMap<String, Node> children = new ConcurrentHashMap<>();
        boolean data;
        E       element;
    }

    private final Node root;

    public PathTree() {
        root = new Node(false, null);
    }

    public void add(final String key, final E elt) {

        Preconditions.checkArgument(StringUtils.isNotBlank(key));
        Preconditions.checkArgument(elt != null);

        // split the give key to elements
        final String[] keyElts = StringUtils.split(key, "/");
        Node currentNode = root;
        int lastIndex = keyElts.length - 1;

        // iterate through the elements of key through trie
        for (int i = 0; i <= lastIndex; i++) {
            String ke = keyElts[i];
            ke = ke.trim().toLowerCase();
            Node childNode = currentNode.children.get(ke);
            if (childNode == null) {
                // child node empty means.. key element is not present.. add new child node with key element
                childNode = new Node(false, null);

                if (i == lastIndex) {
                    // if key element is last element.. then mark the node as data node and put value
                    childNode.data = true;
                    childNode.element = elt;
                }
                // add child node to current nodes children
                currentNode.children.put(ke, childNode);
            } else if (i == lastIndex) {
                // child node is present and key element is last element
                // replace or put elt
                childNode.data = true;
                childNode.element = elt;
            }
            // move to child node
            currentNode = childNode;
        }
    }

    public E get(final String key) {

        Preconditions.checkArgument(StringUtils.isNotBlank(key));

        // split key to key elements
        final String[] keyElts = StringUtils.split(key, "/");
        Node currentNode = root;
        int lastIndex = keyElts.length - 1;
        // iterate through tree to find matching node

        // last matching data nodes value
        E elt = null;
        for (int i = 0; i <= lastIndex; i++) {
            String ke = keyElts[i];
            ke = ke.trim().toLowerCase();
            Node childNode = currentNode.children.get(ke);

            if (childNode == null) {
                // stop iteration.. and return last matching node
                break;
            } else if (childNode.data) {
                // if this node is data node, make the value of node as elt
                elt = childNode.element;
            }
            currentNode = childNode;
        }
        return elt;
    }
}
