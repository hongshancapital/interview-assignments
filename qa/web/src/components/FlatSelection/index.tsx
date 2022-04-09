import classNames from 'classnames';
import React, { ReactNode, useEffect, useState } from 'react';


import styles from './index.scss';

export type Props<T> = {
  getKey?: (element: T) => string | number;
  value: T;
  defaultValue?: T;
  options: ReadonlyArray<T>;
  optionRender?: (option?: T) => ReactNode;
  onValueChange: (value: T) => void;
  disabled?: boolean;
};

const FlatSection = <T extends unknown>({
  value,
  defaultValue,
  options,
  optionRender,
  onValueChange,
  getKey,
  disabled = false,
}: Props<T>) => {
  const [selected, setSelected] = useState<T | undefined>(defaultValue);

  useEffect(() => {
    // defaultValue to init values
    setSelected(defaultValue);
  }, [defaultValue]);
  useEffect(() => {
    // value to display
    setSelected(value);
  }, [value]);

  return (
    <div className={styles.flatSelectionContainer}>
      {options.map((option) => (
        <div
          className={classNames(
            styles.option,
            option === selected && styles.selected,
            disabled && styles.disabled,
          )}
          key={getKey?.(option) || String(option)}
          onClick={() => {
            setSelected(option);
            onValueChange(option);
          }}
        >
          {optionRender?.(option) ?? String(option)}
        </div>
      ))}
      {!options.length && (
        <div className={classNames(styles.option)}>
          Loading...
        </div>
      )}
    </div>
  );
};

export default FlatSection;
