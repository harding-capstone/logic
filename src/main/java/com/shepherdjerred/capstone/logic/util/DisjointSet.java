package com.shepherdjerred.capstone.logic.util;

// TODO use generics
// https://github.com/ShepherdJerred-homework/jungle/
public class DisjointSet {

  int size;
  int[] set;

  DisjointSet(int size) {
    this.size = size;
    set = new int[size];
    for (int i = 0; i < size; i++) {
      set[i] = -1;
    }
  }

  int find(char c) {
    return find(c - 65);
  }

  int size(char c) {
    return size(c - 65);
  }

  void union(char l, char r) {
    union(l - 65, r - 65);
  }

  int find(int i) {
    int currNode = i;
    while (set[currNode] != -1) {
      currNode = set[currNode];
    }
    return currNode;
  }

  int size(int i) {
    int size = 0;
    int currNode = i;
    while (set[currNode] != -1) {
      size += 1;
      currNode = set[currNode];
    }
    return size;
  }

  void union(int l, int r) {
    int leftSize = size(l);
    int rightSize = size(r);

    int lParent = find(l);
    int rParent = find(r);

    if (leftSize < rightSize) {
      // union left -> right
      set[lParent] = rParent;
    } else {
      // union right -> left
      set[rParent] = lParent;
    }
  }
}
