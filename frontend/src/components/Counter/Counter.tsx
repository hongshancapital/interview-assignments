import React from 'react';
import { useCounter } from '../../hooks';

const Counter: React.FC<{}> = () => {
  const { current } = useCounter({ maxCount: 1000 });

  return <span>{current}</span>;
};

export { Counter };
