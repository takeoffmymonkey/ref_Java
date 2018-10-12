package algorithms_datastructures.datastructures;

/* упорядоченные элементы (Nodes)
 * каждый пред элемент имеет
 *      - данные
 *      - ссылку на последующий
 *          - а последний на null
 *
 * - [headNode]->[Node]->[Node]->[Node]->[null]
 *
 * - doubly ended list - есть также ссылка на [tailNode]
 *      - не часто используются
 *
 * - doubly linked node - есть также ссылка на предыдущий <-[Node]-> */

public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.insertAtHead(1);
        list.insertAtHead(2);
        list.insertAtHead(3);
        list.insertAtHead(4);
//        list.deleteFromHead();
        System.out.println(list);
        System.out.println("Length: " + list.length());

        System.out.println(list.find(2));
    }
}


class LinkedList {
    private Node head;

    public void insertAtHead(int data) {
        Node newNode = new Node(data);
        newNode.setNextNode(this.head);
        if (this.head != null) { // на случай, если список еще пустой
            this.head.setPreviousNode(newNode);
        }
        this.head = newNode;
    }

    public void deleteFromHead() { // O(1)
        this.head = this.head.getNextNode();
    }

    public int length() { // O(n)
        int length = 0;
        Node current = this.head;

        while (current != null) {
            length++;
            current = current.getNextNode();
        }
        return length;
    }

    public Node find(int data) { // O(n)
        Node current = this.head;

        while (current != null) {
            if (current.getData() == data) {
                return current;
            }
            current = current.getNextNode();
        }
        return null;
    }

    @Override
    public String toString() {
        String result = "{";
        Node current = this.head;

        while (current != null) {
            result += current.toString() + ",";
            current = current.getNextNode();
        }

        result += "}";
        return result;
    }
}

class Node {
    private int data;
    private Node nextNode;
    private Node previousNode;


    public Node(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    @Override
    public String toString() {
        return "Data: " + this.data;
    }
}