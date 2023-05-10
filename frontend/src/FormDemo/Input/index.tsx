import { FC } from 'react';

interface InputProps<V> {
  value?: V;
  onChange?: (newVal: V) => void;
}

const TextInput: FC<InputProps<string>> = ({ value = '', onChange }) => {
  return (
    <input
      value={value}
      onChange={(e) => {
        onChange && onChange(e.target.value);
      }}
    ></input>
  );
};

const RadioInput: FC<InputProps<string>> = ({ value, onChange }) => {
  return (
    <div>
      {[
        { label: '请选择', value: '' },
        { label: '男', value: 'male' },
        { label: '女', value: 'female' },
      ].map((option) => {
        return (
          <div key={option.value}>
            <input
              type="radio"
              value={option.value}
              checked={option.value === value}
              onChange={(e) => {
                if (e.target.checked) {
                  onChange && onChange(option.value);
                }
              }}
            />
            <label>{option.label}</label>
          </div>
        );
      })}
    </div>
  );
};

export { TextInput, RadioInput };
