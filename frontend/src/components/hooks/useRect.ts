import { useRef, useEffect, useState } from 'react';

const useRect = <T extends HTMLElement>(deps: React.DependencyList = []) => {
  const [size, setSize] = useState({
    width: 0,
    height: 0,
  });
  const root = useRef<T>(null);

  const changeSize = () => {
    const rect = root.current?.getBoundingClientRect();
    if (rect) {
      setSize({
        width: rect.width,
        height: rect.height,
      });
    }
  };

  useEffect(() => {
    changeSize();
  }, deps);

  return {
    root,
    size,
    changeSize,
  };
};

export default useRect;
