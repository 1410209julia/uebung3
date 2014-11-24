package uebung3;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Die Klasse BinaeryHashTree.
 *
 * @param <T> der generische Typ fuer Schluessel
 * @param <U> der generische Typ fuer Wert
 */
public class BinaeryHashTree<T, U> implements AssociativeArray<T, U>{

	/** die Wurzel */
	public TreeNode root;
	
	/** der Zaehler */
	int counter = 0;
	
	/** der consume */
	BiConsumer<T, U> consume = (key, value) -> this.put(key, value);
	
	/**
	 * Instantiiert einen neuen binaeren Hashbaum
	 */
	public BinaeryHashTree() {
	}
	
	/**
	 *  Instantiiert einen neuen binaeren Hashbaum
	 *
	 * @param root die Wurzel
	 */
	public BinaeryHashTree(TreeNode root) {
		this.root = root;
	}

	/**
	 * zeigt den Knoten des uebergebenen Schluessels im Baum an
	 *
	 * @param key der Schluessel
	 * @return den Knoten
	 */
	public TreeNode getNode(T key) {
		return getNodeRek(key, root);
	}

	/**
	 * gibt den Knoten zurueck (rekursive Methode)
	 *
	 * @param key der Schluessel
	 * @param node der Knoten
	 * @return den Knoten
	 */
	public TreeNode getNodeRek(T key, TreeNode node) {
		if (node != null) {
			if (node.key.hashCode() == key.hashCode()) {
				return node;
			} else if (node.key.hashCode() > key.hashCode()) {
				return getNodeRek(key, node.left);
			} else {
				return getNodeRek(key, node.right);
			}
		}
		return null;
	}

	// ok
	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#clear()
	 */
	@Override
	public void clear() {
		root = null;
	}

 	/* (non-Javadoc)
	  * @see uebung3.AssociativeArray#containsKey(java.lang.Object)
	  */
	 @Override
 	public boolean containsKey(T key) {	
		return containsKey(root, key);
	}
	
	/**
	 * Kontrolle ob Schluessel enthalten.
	 *
	 * @param node der Knoten
	 * @param key der Schluessel
	 * @return true, wenn erfolgreich
	 */
	public boolean containsKey(TreeNode node, T key){
		 if (node != null) {
	            if (node.key.equals(key)) {
	                return true;
	            } else if (containsKey(node.left, key)) {
	                return true;
	            } else if (containsKey(node.right, key)){
	                return true;
	            } 
	        }		    return false;
 	}
 
 	/* (non-Javadoc)
	  * @see uebung3.AssociativeArray#containsValue(java.lang.Object)
	  */
	 @Override
 	public boolean containsValue(U value) {
	
			return containsValue(root, value);
 	}
	
	/**
	 * Kontrolle ob Wert enthalten.
	 *
	 * @param node der Knoten
	 * @param value der Wert
	 * @return true, wenn erfolgreich
	 */
	public boolean containsValue(TreeNode node, U value){
	    if (node != null) {
            if (node.value.equals(value)) {
                return true;
            } else if (containsValue(node.left, value)) {
                return true;            
                } else if (containsValue(node.right, value)){
               return true;
           } 
       }
	    return false;
  }

	// ok
	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#get(java.lang.Object)
	 */
	@Override
	public U get(T key) {
		if (containsKey(key)) {
			return getNode(key).value;
		}
		return null;
	}

	// ok
	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.root == null;
	}

	// ok
	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(T key, U value) {
		TreeNode node = new TreeNode(value, key);
		if (root == null) {
			root = node;
		} else {
			put(root, node);
		}
	}

	/**
	 * setzt Knoten
	 *
	 * @param topnode der oberste Konten/Vaterknoten
	 * @param node der Knoten
	 */
	private void put(TreeNode topnode, TreeNode node) {
		if (topnode.key.hashCode() == node.key.hashCode()) {
			return;
		} else if (topnode.key.hashCode() > node.key.hashCode()) {
			if (topnode.left == null) {
				topnode.setLeft(node);
			} else {
				put(topnode.left, node);
			}
		} else {
			if (topnode.right == null) {
				topnode.setRight(node);
			} else {
				put(topnode.right, node);
			}
		}
	}

	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#putAll(uebung3.BinaeryHashTree)
	 */
	@Override
	public void putAll(BinaeryHashTree<T, U> node) {
		node.forEach(consume);
	}

	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#remove(java.lang.Object)
	 */
	@Override
	public void remove(T key) {
		if(containsKey(key)){
			TreeNode node = getNode(key);
			TreeNode parentNode = node.parent;
			if (parentNode.left == node) {
				parentNode.setLeft(null);
			} else {
				parentNode.setRight(null);
			}
			putAll(new BinaeryHashTree<T, U>(node.right));
			putAll(new BinaeryHashTree<T, U>(node.left));
		}
	}

	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#size()
	 */
	@Override
	public int size() {
		if (root == null) {
			return counter;
		} else {
			counter++;
			return size(root);
		}
	}

	/**
	 * Anzahl der Knoten
	 *
	 * @param node
	 * @return the int
	 */
	public int size(TreeNode node) {
		// int size = 0;
		if (node.left != null) {
			counter++;
			size(node.left);
		}
		if (node.right != null) {
			counter++;
			size(node.right);
		}
		return counter;
	}

	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#update(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void update(T key, U value) {
		if (containsKey(key)) {
			getNode(key).value = value;
		}
	}

	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#forEach(java.util.function.BiConsumer)
	 */
	@Override
	public void forEach(BiConsumer<T, U> consume) {
		forEach(consume, root);
	}

	/**
	 * durchlaeuft jeden Knoten
	 *
	 * @param consume fuer Lambdaberechnung
	 * @param node der Knoten
	 */
	public void forEach(BiConsumer<T, U> consume, TreeNode node) {
		if (node != null) {
			consume.accept(node.key, node.value);
			forEach(consume, node.left);
			forEach(consume, node.right);
		}
	}

	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#extractAll(uebung3.BinaeryHashTree)
	 */
	@Override
	public void extractAll(BinaeryHashTree<T, U> node) {
		node.putAll(this);
	}

	// BiFunction<T, U, R> function = (key, value) -> key + value;

	/* (non-Javadoc)
	 * @see uebung3.AssociativeArray#map(java.util.function.BiFunction)
	 */
	@Override
	public BinaeryHashTree<T, U> map(BiFunction<T, U, U> function) {
		BinaeryHashTree<T, U> newTree = new BinaeryHashTree<T, U>();
		return map(function, root, newTree);
	}

	// kein R mehr, weil wir sonst put nicht aufrufen k�nnen!
	/**
	 * neues assoziatives Array mit ueberschriebenen Schluesseln ( Inhalt: veraenderte Werte)
	 *
	 * @param function die Funktion
	 * @param node der Knoten
	 * @param newTree der neue Baum
	 * @return der binaere HashBaum
	 */
	public BinaeryHashTree<T, U> map(BiFunction<T, U, U> function,
			TreeNode node, BinaeryHashTree<T, U> newTree) {
		if (node != null) {
			newTree.put(node.key, function.apply(node.key, node.value));
			map(function, node.left, newTree);
			map(function, node.right, newTree);
		}
		return newTree;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consume == null) ? 0 : consume.hashCode());
		result = prime * result + counter;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BinaeryHashTree<T, U> other = (BinaeryHashTree<T, U>) obj;
		if (consume == null) {
			if (other.consume != null)
				return false;
		} else if (!consume.equals(other.consume))
			return false;
		if (counter != other.counter)
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toString(root);
	}

	/**
	 * Die ToString Methode, die nach Blatt-Vorgabe ausgibt
	 *
	 * @param node der Knoten
	 * @return den String
	 */
	private String toString(TreeNode node) {
		String str = "";
		if (node == null) {
			return str;
		}
		str += "{KEY=" + node.key.toString() + "; VALUE="
				+ node.value.toString() + "}";
		str += toString(node.left) + " " + toString(node.right);
		return str;
	}

	/**
	 * Die TreeNode Klasse
	 */
	class TreeNode {

		/** Der Wert. */
		U value;
		
		/** Der Schluessel. */
		T key;
		
		/** Der linke Knoten */
		TreeNode left = null;
		
		/** Der rechte Knoten */
		TreeNode right = null;
		
		/** Der Eltern-/Vaterknoten*/
		TreeNode parent = null;

		// Objektwerte werden in einen Knoten reingesetzt
		/**
		 * Instanziiert einen neuen Baumknoten
		 *
		 * @param value der Wert
		 * @param key der Schluessel
		 */
		public TreeNode(U value, T key) {
			this.value = value;
			this.key = key;
		}

		// left und right wird erzeugt
		/**
		 * Instantiiert einen neuen Baumknoten
		 *
		 * @param value der Wert
		 * @param key der Schluessel
		 * @param left der linke Knoten
		 * @param right der rechte Knoten
		 */
		public TreeNode(U value, T key, TreeNode left, TreeNode right) {
			this(value, key);
			this.left = left;
			this.right = right;
		}

		/**
		 * Setzt den linken Knoten
		 *
		 * @param node der Knoten
		 */
		public void setLeft(TreeNode node) {
			left = node;
			if (node != null) {
				node.parent = this;
			}

		}

		/**
		 * Setzt den rechten Knoten
		 *
		 * @param node der rechte Knoten
		 */
		public void setRight(TreeNode node) {
			right = node;
			if (node != null) {
				node.parent = this;
			}
		}

	}

}
