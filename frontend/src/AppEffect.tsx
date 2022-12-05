import React, { useEffect, useState, useRef } from 'react';
import './App.css';

const fn = (prev: any, curr: any) => {
  return prev && JSON.stringify(prev) !== JSON.stringify(curr);
};

function UseMyEffect(callback: any, props: any) {
  const diffCount = useRef(0);

  const prevProps = useRef(null);

  const currProps = props.splice(1, 1)[0];

  if (fn(currProps, prevProps.current)) {
    diffCount.current++;
  }

  prevProps.current = currProps;

  useEffect(() => {
    const fn = callback();
    return () => {
      fn();
    };
  }, [...props, diffCount.current]);

  return {};
}

const App = () => {
  const [state1, setState1] = useState(0);
  const [state2, setState2] = useState(0);
  const [obj, setObj] = useState({
    a: 1
  });

  UseMyEffect(() => {
    console.log('change', state1, state2, obj);
    return () => {};
  }, [state1, obj, state2]);

  const handleClick1 = () => setState1(prevProp => prevProp + 1);

  const handleClick2 = () => setState2(prevProp => prevProp + 1);

  const handleClick3 = () =>
    setObj((prevProp: any) => {
      const newProp = JSON.parse(JSON.stringify(prevProp));
      // newProp.a++;
      return newProp;
    });

  return (
    <div>
      <button onClick={handleClick1}>{state1}</button>
      <button onClick={handleClick2}>{state2}</button>
      <button onClick={handleClick3}>obj</button>
    </div>
  );
};

export default App;
