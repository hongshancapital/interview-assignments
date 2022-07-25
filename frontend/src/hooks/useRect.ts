import { useRef, useEffect, useState } from 'react'

const useRect = <T extends HTMLElement>() => {
  const [size, setSize] = useState({
    width: 0, height: 0
  });
  const container = useRef<T>(null);

  useEffect(() => {
    const containerDom = container.current
    if(!containerDom) {
      return
    }
    const resizeObserver = new ResizeObserver((entries) => {
      entries.forEach(entry => {
        const { clientWidth, clientHeight } = entry.target
        console.log(clientWidth, clientHeight);
        setSize({
          width: clientWidth,
          height: clientHeight
        })
      })
    })
    resizeObserver.observe(containerDom)
    return () => {
      resizeObserver.disconnect()
    }
  }, []);

  return {
    container,
    size
  }
}

export default useRect;