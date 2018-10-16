package __algorithms_datastructures.datastructures;

/* - сложность log n
 * - имеет 1 рут нод
 * - у нода может быть только 1 родитель
 * - родитель может быть максимум для 2 нодов (поэтому байнари)
 * - родитель может быть рут нодом для поддерева
 * - leaf node - нод без детей
 * - у нода есть дата, и ссылки на своих детей
 *
 * - Binary Search Tree - для каждого нода все наследники справа больше его, а слева - меньше
 * - поиск осуществляется рекурсивно
 *
 * - из-за того, что код удаления очень неудобный, можно просто помечать нод как удаленный
 *
 * - несбалансированное дерево, когда перевес большой на какой-то стороне, и тогда теряются
 * все преимущества
 * - есть разные вариации сбалансированных деревьев, red-black одна из них
 *
 * */

public class BinarySearchTree {
    private TreeNode root;

    public void insert(Integer data) {
        if (root == null) this.root = new TreeNode(data);
        else root.insert(data); // рекурсивный вызов
    }

    public Integer smallest() {
        if (this.root != null) return root.smallest();
        return null;
    }

    public Integer largest() {
        if (this.root != null) return root.largest();
        return null;
    }

    public TreeNode find(Integer data) {
        if (root != null) return root.find(data); // рекурсивный вызов
        return null;
    }

    public void delete(Integer data) {
        TreeNode toDel = find(data);
        toDel.delete();

        /*        // итеративный подход, а не рекурсивный, т.к. нам нужен родитель
        TreeNode current = this.root;
        TreeNode parent = this.root;
        boolean isLeftChild = false;

        // case 1 у нода нет детей
        if (current == null) return;
        while (current != null && current.getData() != data) {
            parent = current;
            if (data < current.getData()) {
                current = current.getLeftChild();
                isLeftChild = true;
            } else {
                current = current.getRightChild();
                isLeftChild = false;
            }
        }

        if (current == null) return;

        if (current.getLeftChild() == null && current.getRightChild() == null) {
            if (current == root ){
                root = null;
            } else {
                if (isLeftChild) parent.setLeftChild(null);
                else parent.setRightChild(null);
            }
        }

        // case 2 - у нода есть 1 ребенок
        else if (current.getRightChild() == null) {
            if (current == root) {
                root = current.getLeftChild();
            } else if (isLeftChild) {
                parent.setLeftChild(current.getLeftChild());
            } else {
                parent.setRightChild(current.getLeftChild());
            }
        }

        else if (current.getLeftChild() == null) {
            if (current == root) {
                root = current.getRightChild();
            } else if (isLeftChild) {
                parent.setLeftChild(current.getRightChild());
            } else {
                parent.setRightChild(current.getRightChild());
            }
        }*/

        // и это еще без самого сложного кейса, когда есть 2 ребенка - нужно менять структуру
    }

}


class TreeNode {

    private Integer data;
    private TreeNode leftChild;
    private TreeNode rightChild;
    private boolean isDeleted;

    public TreeNode(Integer data) {
        this.data = data;
    }

    public Integer smallest() {
        if (this.leftChild == null) return this.data;
        return this.leftChild.smallest();
    }

    public Integer largest() {
        if (this.rightChild == null) return this.data;
        return this.rightChild.largest();
    }

    public TreeNode find(Integer data) {
        if (this.data == data && !isDeleted) return this;
        // рекурсивные вызовы
        if (data < this.data && leftChild != null) return leftChild.find(data);
        if (rightChild != null) return rightChild.find(data);
        return null;
    }

    public void insert(Integer data) {
        if (data >= this.data) {
            if (this.rightChild == null) this.rightChild = new TreeNode(data);
            else this.rightChild.insert(data);// рекурсивный вызов
        } else {
            if (this.leftChild == null) this.leftChild = new TreeNode(data);
            else this.leftChild.insert(data);// рекурсивный вызов
        }
    }

    public void delete() {
        this.isDeleted = true;
    }

    public Integer getData() {
        return data;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }
}