import { RefObject, useEffect, useRef } from 'react'

/**
 * useEventListener 事件监听
 * @param eventName 事件名称
 * @param handler 事件触发函数
 * @param element 事件元素
 */
function useEventListener(
  eventName: keyof HTMLElementEventMap,
  handler: (
    event: HTMLElementEventMap[keyof HTMLElementEventMap] | Event,
  ) => void,
  element?: RefObject<HTMLElement>,
) {
  const savedHandler = useRef(handler)

  // 保存新回调
  useEffect(() => {
    savedHandler.current = handler
  }, [handler])

  useEffect(() => {
    const targetElement = element?.current || window;

    if (!(targetElement && targetElement.addEventListener)) {
      return
    }

    // 重新声明事件函数
    const eventListener: typeof handler = event => savedHandler.current(event)
    targetElement.addEventListener(eventName, eventListener)

    // 移除监听事件
    return () => {
      targetElement.removeEventListener(eventName, eventListener)
    }
  }, [eventName, element])
}

export default useEventListener
