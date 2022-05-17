import { useRef, useEffect, useState } from 'react'

const useRect = <T extends HTMLElement>(deps: React.DependencyList = []) => {
  const [size, setSize] = useState({
    width: 0, height: 0
  });
  const container = useRef<T>(null);

  const changeSize = () => {
    const rect = container.current?.getBoundingClientRect();
    if (rect) {
      setSize({
        width: rect.width,
        height: rect.height
      })
    }
  }

  useEffect(() => {
    changeSize();
  }, deps);

  return {
    container,
    size
  }
}

export default useRect;