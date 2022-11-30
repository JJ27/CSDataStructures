import java.util.EmptyStackException;
import java.util.List;

public class SuperList<E> {
    ListNode<E> root, end;
    int size;
    public SuperList(){
        root = null;
        end = null;
        size = 0;
    }

    public void add(E data){
        ListNode<E> node = new ListNode<E>(data);
        if(root == null){
            root = node;
            end = node;
        }else{
            end.setNext(node);
            node.setPrevious(end);
            end = node;
        }
        size++;
    }

    public void push(E data){
        add(data);
    }

    public E pop(){
        if(size == 0){
            throw new EmptyStackException();
        }
        E data = end.getValue();
        end = end.getPrevious();
        end.setNext(null);
        size--;
        return data;
    }

    public E poll(){
        if(size == 0){
            return null;
        }
        E data = root.getValue();
        root.setPrevious(null);
        root = root.getNext();
        size--;
        return data;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }
    public void clear(){
        root = null;
        end = null;
        size = 0;
    }

    public E stackPeek(){
        return end.getValue();
    }

    public E queuePeek(){
        return root.getValue();
    }

    @Override
    public String toString(){
        String s = "";
        ListNode<E> node = root;
        while(node != null){
            s += node.getValue() + " ";
            node = node.getNext();
        }
        return s;
    }

    public void add(int index, E data){
        ListNode<E> node = new ListNode<E>(data);
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if(index == size){
            add(data);
            size--;
        } else if(index == 0){
            node.setNext(root);
            root.setPrevious(node);
            root = node;
        } else {
            ListNode<E> temp = root;
            for(int i = 0; i < index; i++){
                temp = temp.getNext();
            }
            node.setNext(temp);
            node.setPrevious(temp.getPrevious());
            temp.getPrevious().setNext(node);
            temp.setPrevious(node);
        }
        size++;
    }

    public E get(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        ListNode<E> node = root;
        for(int i = 0; i < index; i++){
            node = node.next;
        }
        return node.getValue();
    }

    public void set(int index, E data){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        ListNode<E> node = root;
        for(int i = 0; i < index; i++){
            node = node.next;
        }
        node.setValue(data);
    }

    public boolean contains(E data){
        ListNode<E> node = root;
        while(node != null){
            if(node.getValue().equals(data)){
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    public E remove(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        E data;
        if(index == 0){
            data = root.getValue();
            root.setPrevious(null);
            root = root.getNext();
        } else if(index == size - 1){
            data = end.getValue();
            end = end.getPrevious();
            end.setNext(null);
        } else {
            ListNode<E> node = root;
            for(int i = 0; i < index; i++){
                node = node.getNext();
            }
            data = node.getValue();
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
        }
        size--;
        return data;
    }

    public class ListNode<E>{
        ListNode<E> next, previous;
        E value;
        public ListNode(E value){
            this.value = value;
            next = null;
            previous = null;
        }

        public E getValue(){
            return value;
        }
        public ListNode<E> getNext(){
            return next;
        }
        public ListNode<E> getPrevious(){
            return previous;
        }
        public void setNext(ListNode<E> next){
            this.next = next;
        }
        public void setPrevious(ListNode<E> previous){
            this.previous = previous;
        }
        public void setValue(E value){
            this.value = value;
        }
    }
}
