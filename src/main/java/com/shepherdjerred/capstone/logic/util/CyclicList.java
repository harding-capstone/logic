package com.shepherdjerred.capstone.logic.util;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CyclicList<T> {

  private Node head;
  private Node tail;
  private Node currentNode;

  public T getCurrent() {
    return currentNode.value;
  }

  public void add(T value) {
    var n = new Node(value, null);
    if (head == null) {
      head = n;
      tail = head;
      currentNode = head;
    } else {
      tail.next = n;
      tail = n;
    }
  }

  public void next() {
    if (currentNode.next == null) {
      currentNode = head;
    } else {
      currentNode = currentNode.next;
    }
  }

  @Data
  @AllArgsConstructor
  public class Node {
    private T value;
    private Node next;
  }
}
