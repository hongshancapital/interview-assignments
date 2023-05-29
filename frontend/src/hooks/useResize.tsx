  import { useLayoutEffect } from 'react';

  /**
   * 监听组件变化并更新宽度
   */

  type UseResizeProps = (
    onResize: (args: number) => void,
    observerRef?: React.RefObject<HTMLDivElement>,
  ) => void;

  // 考虑兼容性的话，可用window.addEventListener('resize', () => {}）代替
  const useResize: UseResizeProps = (onResize, observerRef) => {
    useLayoutEffect(() => {
      const resizeObserverCallback = (entries: ResizeObserverEntry[]) => {
        for (const entry of entries) {
          if (entry.contentBoxSize) {
            const inlineSize = entry.contentBoxSize[0]?.inlineSize;
            if (inlineSize !== undefined) {
              console.log('111 inlineSize', inlineSize)
              onResize(inlineSize);
            }
          }
        }
      };
  
      const resizeObserver = new ResizeObserver(resizeObserverCallback);
  
      if (observerRef?.current) {
        resizeObserver.observe(observerRef.current);
      }
  
      return () => {
        if (observerRef?.current) {
          resizeObserver.unobserve(observerRef.current);
        }
      };
    }, [onResize, observerRef]);
  };
  
  export default useResize;
