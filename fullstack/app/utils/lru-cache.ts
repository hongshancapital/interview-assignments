class DLinkedNode {
  key: string;
  value: unknown;
  prev: DLinkedNode;
  next: DLinkedNode;
  constructor(key: string, value: unknown) {
    this.key = key;
    this.value = value;
    this.prev = null as unknown as DLinkedNode;
    this.next = null as unknown as DLinkedNode;
  }
}

class Link {
  prev: DLinkedNode;
  next: DLinkedNode;
  constructor() {
    this.prev = new DLinkedNode('_prev', null);
    this.next = new DLinkedNode('_next', null);
    this.prev.next = this.next;
    this.next.prev = this.prev;
  }

  remove(node: DLinkedNode) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
  }

  insert(node: DLinkedNode) {
    node.prev = this.next.prev;
    node.next = this.next;
    this.next.prev.next = node;
    this.next.prev = node;
  }

  delete() {
    const node = this.prev.next;
    if (node.value) {
      this.prev.next = node.next;
    }
    return node;
  }
}

export default function LRVCache(n: number) {
  const total = n;
  const map: Map<string, DLinkedNode> = new Map();
  const link = new Link();

  const get = (key: string) => {
    const node = map.get(key);
    if (!node) {
      return null;
    }
    link.remove(node);
    link.insert(node);
    return node.value;
  };

  const set = (key: string, value: unknown) => {
    const currentNode = map.get(key);
    if (currentNode) {
      link.remove(currentNode);
      map.delete(key);
    }
    if (map.size === total) {
      const deleteNode = link.delete();
      map.delete(deleteNode.key);
    }
    const node = new DLinkedNode(key, value);
    link.insert(node);
    map.set(key, node);
  };

  const del = (keys: string | string[]): number => {
    let count = 0;
    if (typeof keys === 'string') {
      keys = [keys];
    }
    keys.forEach(key => {
      const currentNode = map.get(key);
      if (currentNode) {
        link.remove(currentNode);
        map.delete(key);
        count++;
      }
    });
    return count;
  };

  const showAll = () => map;
  return {
    get,
    set,
    del,
    showAll,
  };
}
