package practica1;

import java.util.LinkedList;
import java.util.List;
import material.Position;
import material.tree.LeafIterator;
import material.tree.narytree.LinkedTree;

/**
 *
 * @author mayte
 */
public class Trie {

    private final LinkedTree<Character> tree;

    public Trie() {
        tree = new LinkedTree<>();
    }

    /**
     * This method insert a new word in the Trie. If the prefix exist, adds the
     * remainder of the word.
     *
     * @param w
     */
    private void addRemainder(char[] letters, Position p, int start) {
        Position aux;
        if (p == null) {
            if (tree.isEmpty()) {
                aux = tree.addRoot('*');
            } else {
                aux = tree.root();
            }
        } else {
            aux = p;
        }
        for (int i = start; i < letters.length; i++) {
            aux = tree.add(letters[i], aux);
        }
    }

    public void insert(String w) {
        char[] letters = w.toCharArray();
        int current = 0;
        Position parent = null;
        boolean continueSearch;
        if (!tree.isEmpty()) {
            //search for the letters it contains
            Position aux = tree.root();
            while (!tree.isLeaf(aux)) {
                continueSearch = false;
                for (Object obj : tree.children(aux)) {
                    Position child = (Position) obj;
                    if (child.getElement().equals(letters[current])) {
                        current++;
                        parent = child;
                        aux = child;
                        continueSearch = true;
                        break;
                    }
                }
                if (!continueSearch) {
                    break;
                }
            }
        }
        addRemainder(letters, parent, current);
    }

    /**
     * Receives a prefix and return all the words that the trie knows with this
     * prefix. If the Trie has not any word with this prefix return null.
     *
     * @param pre
     * @return
     */
    public Iterable<String> search(String pre) {
        if (tree.isEmpty()) {
            return null;
        }
        if (pre == null) {
            throw new RuntimeException("Invalid prefix.");
        }
        Position aux = getPrefixPosition(pre);
        return getWordsFrom(pre, aux);
    }

    private Position getPrefixPosition(String pre) {
        Position aux = tree.root();
        char[] letters = pre.toCharArray();

        int current = 0;

        while (!tree.isLeaf(aux) && current < letters.length) {
            boolean continueSearch = false;
            for (Object obj : tree.children(aux)) {
                Position child = (Position) obj;
                if (child.getElement().equals(letters[current])) {
                    aux = child;
                    continueSearch = true;
                    current++;
                    break;
                }
            }
            if (!continueSearch) {
                break;
            }
        }
        return aux;
    }

    private List<String> getWordsFrom(String prefix, Position aux) {
        List<String> words = new LinkedList<>();
        LeafIterator<Position> it = new LeafIterator<>(tree, aux);
        while(it.hasNext()) {
            Position child = it.next();
            String remainder = "";

            while (!child.equals(aux) && !tree.isRoot(child)) {
                remainder = child.getElement() + remainder;
                child = tree.parent(child);
            }
            if (!remainder.equals("")) {
                words.add(prefix + remainder);
            }
        }
        if (words.isEmpty()) {
            return null;
        }
        return words;
    }

    /**
     * Removes every word of the trie that beginning by this prefix.
     *
     * @param pre
     */
    public void delete(String pre) {
        if (tree.isEmpty()) {
            throw new RuntimeException("Invalid Operation. There are no words");
        }
        if (pre == null) {
            throw new RuntimeException("Invalid prefix.");
        }
        Position aux = getPrefixPosition(pre);
        tree.remove(aux);
    }
}
