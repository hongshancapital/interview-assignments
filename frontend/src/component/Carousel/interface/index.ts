export type UnionOmit<T, K> = T & Omit<K, keyof T>;
