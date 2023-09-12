type SchemaTypeFor<T> =
  T extends boolean ? 'boolean' :
    T extends Date ? never :
      T extends number ? 'number' | 'integer' | 'enum' :
        T extends string ? 'string' | 'email' :
          T extends any[] ? 'array' :
            T extends { [key: string]: any } ? ('object' | 'union') :
              never;

type ShortSchemaTypeFor<T> =
  T extends boolean ? 'boolean' :
    T extends Date ? never :
      T extends number ? 'number' | 'integer' :
        T extends string ? 'string' | 'email' :
          never;

type SchemaItemCore<T> = {
  readonly t: SchemaTypeFor<T>;
};
type SchemaItemArray<U> = {
  readonly maxItems?: number;
  readonly minItems?: number;
  readonly items: SchemaItem<U> | ShortSchemaTypeFor<U>;
};
type SchemaItemConvertible = {
};
type SchemaItemNumber = {
  readonly t: 'number' | 'integer';
  readonly maximum?: number;
  readonly minimum?: number;
  readonly exclusiveMaximum?: boolean;
  readonly exclusiveMinimum?: boolean;
  readonly const?: number;
};
type SchemaItemString = {
  readonly maxLength?: number;
  readonly minLength?: number;
  readonly pattern?: string;
};
type SchemaItemObject<T extends { [key: string]: any }> = {
  readonly props: SchemaProps<T>;
};
type SchemaItemUnion<T, T2 = any> = {
  readonly t: 'union';
  readonly vals: T2[];
  readonly discriminant: string;
  readonly schemas: SchemaItemUnion<T>[];
};
type SchemaItemEnum<T> = {
  readonly t: 'enum';
  readonly vals: T[];
};
type SchemaItem<T, T2 = T> =
  SchemaItemCore<T> &
  (
    T extends (infer U)[] ? SchemaItemArray<U> :
      T extends boolean ? SchemaItemConvertible :
        T extends Date ? never :
          T extends { [key: string]: any } ? (SchemaItemObject<T> | SchemaItemUnion<T>) :
            T extends string ? SchemaItemString :
              T extends number ? (SchemaItemEnum<T2> | SchemaItemNumber) :
                {});

type SchemaCheck<T> = {
  check?(v: T): void;
};
type SchemaProps<T extends { [key: string]: any }> = {
  readonly [P in keyof T]-?:
  (NonNullable<T[P]> extends infer NNTP ?
    (ShortSchemaTypeFor<NNTP> |
    Readonly<(SchemaItem<NNTP> & (T[P] extends NNTP ? {} : { optional: true }))>) :
    never)
};

export type SchemaFor<T> =
  T extends (infer U)[] ? Readonly<SchemaItemArray<U> & SchemaCheck<T>> :
    T extends { [key: string]: any } ? Readonly<SchemaProps<T> & SchemaCheck<T>> :
      never;
