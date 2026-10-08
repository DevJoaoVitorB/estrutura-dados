package src.trees.btree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BTree<T extends Comparable<T>>  {

    private BNode<T> root;
    private int order;

    public BTree(int order) { this.order = order; root = new BNode<>(null, order); }

    // Generic Methods
    public int size() { return size(root); }
    public int size(BNode<T> node) {
        int count = node.getNKeys();
        if (node.isExternal()) return count;

        BNode<T>[] children = node.getChildren();
        for (int i = 0; i <= node.getNKeys(); i++) { count += size(children[i]); }

        return count;
    }

    public boolean isEmpty() { return root.getNKeys() == 0; }

    // Access Methods
    public BNode<T> getRoot() { return root; }

    // Query Methods
    public boolean isRoot(BNode<T> node) { return node == root; }

    public int height(BNode<T> node) {
        if (node.isExternal()) return 0;
        return 1 + height(node.getChild(0));
    }

    public int depth(BNode<T> node) { 
        if (isRoot(node)) return 0;
        return 1 + depth(node.getParent());
    }

    public BNode<T> find(T key) { return find(root, key); }
    private BNode<T> find(BNode<T> node, T key) {
        int i = 0;
        while (i < node.getNKeys() && key.compareTo(node.getKey(i)) > 0) { i++; }

        if (i < node.getNKeys() && key.compareTo(node.getKey(i)) == 0) { return node; }
        if (node.isExternal()) { return null; }

        return find(node.getChild(i), key);
    }

    // Update Methods
    public void insert(T element) {
        if (element == null || find(element) != null) return; // chave nula ou repetida: inserção inválida

        // Raiz cheia (2t-1 chaves): divide e a árvore cresce em altura
        if (root.isFull()) {
            BNode<T> newRoot = new BNode<>(null, order);
            link(newRoot, 0, root);
            split(newRoot, 0);
            root = newRoot;
        }

        // Desce até a folha, dividindo todo filho cheio ANTES de entrar nele
        BNode<T> node = root;
        while (node.isInternal()) {
            int i = indexOf(node, element);

            if (node.getChild(i).isFull()) {
                split(node, i);
                if (element.compareTo(node.getKey(i)) > 0) { i++; } // a mediana subiu: decide o lado
            }

            node = node.getChild(i);
        }

        // Na folha (que sempre tem espaço): desloca as maiores chaves e insere ordenado
        int i = node.getNKeys();
        while (i > 0 && element.compareTo(node.getKey(i - 1)) < 0) { node.setKey(i, node.getKey(i - 1)); i--; }

        node.setKey(i, element);
        node.incrementNKeys();
    }

    public void remove(T key) {
        if (key == null || find(key) == null) return; // chave inexistente: nada a remover

        BNode<T> node = root;
        while (true) {
            int i = indexOf(node, key);
            boolean found = i < node.getNKeys() && key.compareTo(node.getKey(i)) == 0;

            // Caso 1: chave na folha, só remove e desloca
            if (node.isExternal()) {
                for (int j = i; j < node.getNKeys() - 1; j++) { node.setKey(j, node.getKey(j + 1)); }
                node.setKey(node.getNKeys() - 1, null);
                node.decrementNKeys();
                break;
            }

            // Caso 2: chave em nó interno
            if (found) {
                BNode<T> y = node.getChild(i), z = node.getChild(i + 1);

                if (y.getNKeys() >= order) { // 2a: troca pelo antecessor e segue removendo-o em y
                    BNode<T> aux = y;
                    while (aux.isInternal()) { aux = aux.getChild(aux.getNKeys()); }
                    key = aux.getKey(aux.getNKeys() - 1);
                    node.setKey(i, key);
                    node = y;
                } else if (z.getNKeys() >= order) { // 2b: troca pelo sucessor e segue removendo-o em z
                    BNode<T> aux = z;
                    while (aux.isInternal()) { aux = aux.getChild(0); }
                    key = aux.getKey(0);
                    node.setKey(i, key);
                    node = z;
                } else { // 2c: y e z têm t-1 chaves, funde y + x + z e continua em y
                    merge(node, i);
                    node = y;
                }

                continue;
            }

            // Caso 3: a chave está mais abaixo, garante t chaves no filho antes de descer
            if (node.getChild(i).getNKeys() == order - 1) { i = fill(node, i); }
            node = node.getChild(i);
        }

        // Se a fusão esvaziou a raiz, o único filho vira a nova raiz (árvore diminui)
        if (root.getNKeys() == 0 && root.isInternal()) {
            root = root.getChild(0);
            root.setParent(null);
        }
    }

    // Print Tree
    public void printTree() {
        Map<BNode<T>, Integer> ids = new HashMap<>();
        Queue<BNode<T>> queue = new LinkedList<>();

        queue.add(root);
        ids.put(root, 1);

        while (!queue.isEmpty()) {
            BNode<T> node = queue.poll();

            // Chaves do nó
            StringBuilder keys = new StringBuilder();
            for (int i = 0; i < node.getNKeys(); i++) {
                if (i > 0) keys.append(", ");
                keys.append(node.getKey(i));
            }

            // Filhos do nó (recebem o próximo número disponível ao entrar na fila)
            StringBuilder children = new StringBuilder();
            if (node.isInternal()) {
                for (int i = 0; i <= node.getNKeys(); i++) {
                    BNode<T> child = node.getChild(i);
                    ids.put(child, ids.size() + 1);
                    queue.add(child);

                    if (i > 0) children.append(", ");
                    children.append("No").append(ids.get(child));
                }
            } else {
                children.append("-"); // folha não tem filhos
            }

            System.out.println("No" + ids.get(node) + " - Chaves: " + keys + " - Filhos: " + children);
        }

        // Todas as chaves em ordem crescente
        System.out.print("Em ordem: ");
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(BNode<T> node) {
        for (int i = 0; i < node.getNKeys(); i++) {
            if (node.isInternal()) printInOrder(node.getChild(i));
            System.out.print(node.getKey(i) + " ");
        }
        if (node.isInternal()) printInOrder(node.getChild(node.getNKeys()));
    }

    // Helpers
    // Índice da primeira chave >= key (também é o índice do filho por onde descer)
    private int indexOf(BNode<T> node, T key) {
        int i = 0;
        while (i < node.getNKeys() && key.compareTo(node.getKey(i)) > 0) { i++; }
        return i;
    }

    // Liga o filho ao pai nas duas direções (children e parent)
    private void link(BNode<T> parent, int index, BNode<T> child) {
        parent.setChild(index, child);
        if (child != null) child.setParent(parent);
    }

    // Divide o filho cheio parent.children[i] em dois nós com t-1 chaves; a mediana sobe
    private void split(BNode<T> parent, int i) {
        BNode<T> y = parent.getChild(i);             // nó cheio: fica com as t-1 menores chaves
        BNode<T> z = new BNode<>(parent, order);     // nó novo: fica com as t-1 maiores chaves

        for (int j = 0; j < order - 1; j++) {
            z.setKey(j, y.getKey(j + order));
            y.setKey(j + order, null);
        }

        for (int j = 0; j < order; j++) { // filhos (em folha são todos null)
            link(z, j, y.getChild(j + order));
            y.setChild(j + order, null);
        }

        z.setNKeys(order - 1);

        T median = y.getKey(order - 1);
        y.setKey(order - 1, null);
        y.setNKeys(order - 1);

        // Abre espaço no pai e encaixa a mediana e o novo filho z
        for (int j = parent.getNKeys(); j > i; j--) {
            parent.setKey(j, parent.getKey(j - 1));
            parent.setChild(j + 1, parent.getChild(j));
        }

        parent.setKey(i, median);
        link(parent, i + 1, z);
        parent.incrementNKeys();
    }

    // Caso 3: filho i com t-1 chaves. Empresta de um irmão (3a) ou funde com ele (3b)
    // Retorna o índice do filho depois da operação (muda se fundir com o irmão da esquerda)
    private int fill(BNode<T> node, int i) {
        BNode<T> child = node.getChild(i);
        BNode<T> left = i > 0 ? node.getChild(i - 1) : null;
        BNode<T> right = i < node.getNKeys() ? node.getChild(i + 1) : null;

        // 3a: irmão esquerdo tem chave sobrando, gira pelo pai
        if (left != null && left.getNKeys() >= order) {
            for (int j = child.getNKeys(); j > 0; j--) { child.setKey(j, child.getKey(j - 1)); }
            for (int j = child.getNKeys() + 1; j > 0; j--) { child.setChild(j, child.getChild(j - 1)); }

            child.setKey(0, node.getKey(i - 1));                         // chave do pai desce
            link(child, 0, left.getChild(left.getNKeys()));              // último filho do irmão acompanha
            node.setKey(i - 1, left.getKey(left.getNKeys() - 1));        // maior chave do irmão sobe

            left.setKey(left.getNKeys() - 1, null);
            left.setChild(left.getNKeys(), null);
            left.decrementNKeys();
            child.incrementNKeys();
            return i;
        }

        // 3a: irmão direito tem chave sobrando, gira pelo pai
        if (right != null && right.getNKeys() >= order) {
            child.setKey(child.getNKeys(), node.getKey(i));              // chave do pai desce
            link(child, child.getNKeys() + 1, right.getChild(0));        // primeiro filho do irmão acompanha
            node.setKey(i, right.getKey(0));                             // menor chave do irmão sobe

            for (int j = 0; j < right.getNKeys() - 1; j++) { right.setKey(j, right.getKey(j + 1)); }
            for (int j = 0; j < right.getNKeys(); j++) { right.setChild(j, right.getChild(j + 1)); }

            right.setKey(right.getNKeys() - 1, null);
            right.setChild(right.getNKeys(), null);
            right.decrementNKeys();
            child.incrementNKeys();
            return i;
        }

        // 3b: nenhum irmão pode emprestar, funde com um deles
        if (right != null) { merge(node, i); return i; }

        merge(node, i - 1);
        return i - 1;
    }

    // Funde filho i + chave i do pai + filho i+1 (ambos com t-1 chaves) em um nó de 2t-1 chaves
    private void merge(BNode<T> node, int i) {
        BNode<T> y = node.getChild(i), z = node.getChild(i + 1);

        y.setKey(order - 1, node.getKey(i)); // chave do pai desce para y
        for (int j = 0; j < order - 1; j++) { y.setKey(order + j, z.getKey(j)); }
        for (int j = 0; j < order; j++) { link(y, order + j, z.getChild(j)); } // filhos de z passam a ser de y
        y.setNKeys(2 * order - 1);

        // Fecha o buraco no pai (z deixa de existir)
        for (int j = i; j < node.getNKeys() - 1; j++) { node.setKey(j, node.getKey(j + 1)); }
        node.setKey(node.getNKeys() - 1, null);
        for (int j = i + 1; j < node.getNKeys(); j++) { node.setChild(j, node.getChild(j + 1)); }
        node.setChild(node.getNKeys(), null);
        node.decrementNKeys();
    }
}