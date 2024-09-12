type Store = {
  long2short: (long: string) => string | undefined;
  short2long: (short: string) => string | undefined;
  save: (long: string, short: string) => void;
}


function createStore() {
  const shortMap: Map<string, string> = new Map();
  const longMap: Map<string, string> = new Map();
  return {
    long2short(long: string) {
      return longMap.get(long);
    },
    short2long(short: string) {
      return shortMap.get(short);
    },
    save(long: string, short: string) {
      shortMap.set(short, long);
      longMap.set(long, short);
    },
  }
}

let store: Store | null = null;

export function getStore() {
  if (store) return store;
  store = createStore();
  return store;
}
