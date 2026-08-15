package org.ug.dsa.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Custom generic Doubly Linked List with custom bidirectional Iterator.
 *
 * Implements Module M3 custom data structure specifications:
 *   - addFirst, addLast, insertAfter, removeFirst, removeLast, remove(element)
 *   - get(index), set(index, element), size, isEmpty, clear
 *   - Custom Iterator implementation for for-each traversal
 *
 * All operations manage node pointers explicitly without Java collection utilities.
 */
public class CustomLinkedList<T> implements CustomList<T> {

    /**
     * Internal doubly-linked list node.
     */
    public static class Node<T> {
        public T data;
        public Node<T> prev;
        public Node<T> next;

        public Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds an element to the front of the list.
     */
    public void addFirst(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add null element.");
        }
        Node<T> newNode = new Node<>(element, null, head);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * Adds an element to the end of the list.
     */
    public void addLast(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add null element.");
        }
        Node<T> newNode = new Node<>(element, tail, null);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T element) {
        addLast(element);
    }

    /**
     * Inserts an element immediately after a specified target element.
     *
     * @return true if target was found and insertion succeeded, false otherwise
     */
    public boolean insertAfter(T target, T element) {
        if (target == null || element == null) {
            throw new IllegalArgumentException("Target and element must not be null.");
        }
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(target)) {
                if (current == tail) {
                    addLast(element);
                } else {
                    Node<T> newNode = new Node<>(element, current, current.next);
                    current.next.prev = newNode;
                    current.next = newNode;
                    size++;
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Removes and returns the first element.
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty list.");
        }
        T data = head.data;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }

    /**
     * Removes and returns the last element.
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty list.");
        }
        T data = tail.data;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }

    /**
     * Removes the first occurrence of the specified element.
     *
     * @return true if the element was found and removed, false otherwise
     */
    public boolean removeElement(T element) {
        if (element == null || isEmpty()) {
            return false;
        }
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> target = getNode(index);
        T data = target.data;
        unlink(target);
        return data;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).data;
    }

    public T set(int index, T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot set null element.");
        }
        checkIndex(index);
        Node<T> node = getNode(index);
        T old = node.data;
        node.data = element;
        return old;
    }

    public T peekFirst() {
        if (isEmpty()) return null;
        return head.data;
    }

    public T peekLast() {
        if (isEmpty()) return null;
        return tail.data;
    }

    public boolean contains(T element) {
        if (element == null) return false;
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) return true;
            current = current.next;
        }
        return false;
    }

    public int indexOf(T element) {
        if (element == null) return -1;
        int idx = 0;
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) return idx;
            current = current.next;
            idx++;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        Node<T> current = head;
        while (current != null) {
            Node<T> next = current.next;
            current.prev = null;
            current.next = null;
            current.data = null;
            current = next;
        }
        head = null;
        tail = null;
        size = 0;
    }

    private Node<T> getNode(int index) {
        if (index < (size >> 1)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void unlink(Node<T> node) {
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = head.next;
            head.prev = null;
        } else if (node == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}
