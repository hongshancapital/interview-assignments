import React, {
  FC,
  ReactElement,
  ReactNode,
  forwardRef,
  useCallback,
  useImperativeHandle,
  useMemo,
  useRef,
  useState,
} from 'react';

// type Value = string | number | boolean | Value[] | { [k in string]: Value };
// type Values = { [k in string]: Value };
type Value = any;
type Values = any;

interface FormInputProps<V> {
  value: V;
  onChange: (newVal: V) => void;
}

interface FormProps {
  initialValues: Values;
  children: ReactElement<FormItemProps> | ReactElement<FormItemProps>[];
}

interface FormItemProps<V = Value> {
  name: string;
  label: ReactNode;
  value?: V;
  onChange?: (newVal: V) => void;
  validator: (val: V) => string | null | undefined;
  children: ReactElement<FormInputProps<V>>;
}

export type FormInstance<V> = {
  getValues: (
    cb: (err: { [P in string]: string } | null, v: V) => void,
  ) => void;
  setValues: (values: V) => void;
  setFieldValue: (name: keyof V, value: V[keyof V]) => void;
};

const FormItem: FC<FormItemProps> = () => null;

type Errs = {
  [P in string]: string | undefined | null;
};

const FormInner = forwardRef<FormInstance<Values>, FormProps>(
  ({ initialValues, children }, ref) => {
    const containerRef = useRef<HTMLDivElement>(null);
    const [values, setValues] = useState(initialValues);
    const [errs, setErrs] = useState<Errs>({});
    const errsRef = useRef<Errs>({});

    const itemsConf = useMemo(() => {
      const obj: {
        [P in string]: Partial<FormItemProps>;
      } = {};
      React.Children.map(children, (child) => {
        if (child.type !== FormItem) {
          return null;
        }

        const { name, validator } = child.props;
        obj[name] = { validator };
      });
      return obj;
    }, [children]);

    const scrollToErr = useCallback(() => {
      let firstFieldName = '';
      for (let name in itemsConf) {
        if (errsRef.current[name]) {
          firstFieldName = name;
          break;
        }
      }
      if (!firstFieldName) {
        return;
      }

      const firstEle = containerRef.current?.querySelector(
        `[data-name=${firstFieldName}]`,
      );
      firstEle?.scrollIntoView();
    }, [itemsConf]);

    useImperativeHandle(
      ref,
      () => {
        return {
          getValues(cb) {
            const errs: { [P in string]: string } = {};
            for (let name in itemsConf) {
              const { validator } = itemsConf[name];
              if (validator) {
                const err = validator(values[name]);
                if (err && err.length) {
                  errs[name] = err;
                }
              }
            }
            setErrs(errs);
            errsRef.current = errs;
            if (Object.keys(errs).length) {
              debugger;
              scrollToErr();
              cb(errs, null);
            } else {
              cb(null, values);
            }
          },
          setValues(values) {
            setValues(values);
          },
          setFieldValue(name, value) {
            setValues((preState: Values) => {
              return {
                ...preState,
                [name]: value,
              };
            });
          },
        };
      },
      [values, itemsConf, scrollToErr],
    );

    return (
      <div ref={containerRef}>
        {React.Children.map(children, (child) => {
          if (child.type !== FormItem) {
            return null;
          }

          const { name, label, children } = child.props;
          const err = errs[name];
          return (
            <div key={name} style={{ marginBottom: '10px' }} data-name={name}>
              <label>{label}</label>
              <children.type
                value={values[name]}
                onChange={(newVal: Value) => {
                  setValues({
                    ...values,
                    [name]: newVal,
                  });
                }}
              />
              <div style={{ color: 'red' }}>{err}</div>
            </div>
          );
        })}
      </div>
    );
  },
);

type FormType = typeof FormInner & {
  Item: typeof FormItem;
};

const Form = FormInner as FormType;
Form.Item = FormItem;

export default Form;
