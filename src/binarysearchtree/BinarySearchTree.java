package binarysearchtree;

public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root;

    // Inserção recursiva
    public void insert(T data) {
        root = insert(data, root);
    }

    private Node<T> insert(T data, Node<T> node) {
        if (node == null) {
            return new Node<>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftChild(insert(data, node.getLeftChild()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightChild(insert(data, node.getRightChild()));
        }
        return node;
    }


    //Procura recursivamente
    //Se encontrar o valor → retorna o nó.
    //Se não encontrar → retorna null.

    public boolean search(T data) {
        return search(data, root) != null;
    }

    private Node<T> search(T data, Node<T> node) {
        if (node == null || data.equals(node.getData())) {
            return node;
        }
        if (data.compareTo(node.getData()) < 0) {
            return search(data, node.getLeftChild());
        }
        return search(data, node.getRightChild());
    }


    //Mínimo e Máximo
    //O mínimo está sempre no caminho mais à esquerda.
    //O máximo está sempre no caminho mais à direita.
    public T getMin() {
        if (root == null) return null;
        return getMin(root).getData();
    }

    private Node<T> getMin(Node<T> node) {
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }

    public T getMax() {
        if (root == null) return null;
        return getMax(root).getData();
    }

    private Node<T> getMax(Node<T> node) {
        while (node.getRightChild() != null) {
            node = node.getRightChild();
        }
        return node;
    }

    //Remoção (delete)
    //Aqui estão os 3 casos clássicos:
    //Nó é folha → retorna null.
    //Nó tem um filho → retorna o filho.
    //Nó tem dois filhos → substitui pelo maior da subárvore esquerda e remove o duplicado
    public void delete(T data) {
        root = delete(data, root);
    }

    private Node<T> delete(T data, Node<T> node) {
        if (node == null) return null;

        if (data.compareTo(node.getData()) < 0) {
            node.setLeftChild(delete(data, node.getLeftChild()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightChild(delete(data, node.getRightChild()));
        } else {
            // Caso 1: nó folha
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                return null;
            }
            // Caso 2: um filho
            if (node.getLeftChild() == null) {
                return node.getRightChild();
            }
            if (node.getRightChild() == null) {
                return node.getLeftChild();
            }
            // Caso 3: dois filhos
            Node<T> predecessor = getMax(node.getLeftChild());
            node.setData(predecessor.getData());
            node.setLeftChild(delete(predecessor.getData(), node.getLeftChild()));
        }
        return node;
    }

    // ---------------------------
// Percorre em ordem (In-Order)
// ---------------------------
    public void traverseInOrder() {
        traverseInOrder(root);
        System.out.println();
    }

    private void traverseInOrder(Node<T> node) {
        if (node != null) {
            traverseInOrder(node.getLeftChild());
            System.out.print(node.getData() + " ");
            traverseInOrder(node.getRightChild());
        }
    }

    // ---------------------------
// Percorre em pré-ordem (Pre-Order)
// ---------------------------
    public void traversePreOrder() {
        traversePreOrder(root);
        System.out.println();
    }

    private void traversePreOrder(Node<T> node) {
        if (node != null) {
            System.out.print(node.getData() + " ");
            traversePreOrder(node.getLeftChild());
            traversePreOrder(node.getRightChild());
        }
    }

    // ---------------------------
// Percorre em pós-ordem (Post-Order)
// ---------------------------
    public void traversePostOrder() {
        traversePostOrder(root);
        System.out.println();
    }

    private void traversePostOrder(Node<T> node) {
        if (node != null) {
            traversePostOrder(node.getLeftChild());
            traversePostOrder(node.getRightChild());
            System.out.print(node.getData() + " ");
        }
    }
}