import { useRef } from 'react';
import Form, { type FormInstance } from './Form';
import { TextInput, RadioInput } from './Input';

type DemoFormValues = {
  name: string;
  gender: string;
};

const Demo = () => {
  const form = useRef<FormInstance<DemoFormValues>>(null);

  return (
    <div
      style={{
        width: 500,
        margin: '30px auto',
        background: '#ffffff',
        padding: '10px 20px',
      }}
    >
      <Form initialValues={{ name: 'mark', gender: 'male' }} ref={form}>
        <Form.Item
          name="name"
          label="姓名"
          validator={(name) => {
            if (!name) {
              return '请输入姓名';
            }
          }}
        >
          <TextInput />
        </Form.Item>
        <Form.Item
          name="gender"
          label="性别"
          validator={(gender) => {
            if (!gender) {
              return '请选择性别';
            }
          }}
        >
          <RadioInput />
        </Form.Item>
      </Form>
      <div>
        <button
          onClick={() => {
            form.current?.getValues((err, values) => {
              if (err) {
                return;
              }

              // TODO
              const obj = values;
            });
          }}
        >
          提交
        </button>
        <button
          style={{ marginLeft: '10px' }}
          onClick={() => {
            form.current?.setValues({ name: 'mark', gender: 'male' });
            // form.current?.setFieldValue('name', [true]);
          }}
        >
          重置
        </button>
        <button
          style={{ marginLeft: '10px' }}
          onClick={() => {
            form.current?.setValues({
              name: '',
              gender: '',
            });
          }}
        >
          清除
        </button>
      </div>
    </div>
  );
};

export default Demo;

// (name: "name" | "gender", value: string) => void”分配给类型“(name: string, value: ValueType) => void
// type FnWithString = (name: string, value: string) => void;
// type FnWithUnion = (name: 'name' | 'gender', value: string) => void;
// const f1: FnWithString = (name: 'name' | 'gender', value: string) => {};
// const f2: FnWithUnion = (name: string, value: string) => {};

// type t1 = 'name' | 'gender' extends string ? true : false;
// type t2 = (
//   a: 'name' | 'gender',
// ) => void extends (a: string) => void ? true : false;

// type BooleanKeys = keyof boolean;
// type NumberKeys = keyof number;
// type SymbolKeys = keyof symbol;

// type u1 = string | number | boolean;
// type u2 = number | string[];
// type t = u1 & u2;
