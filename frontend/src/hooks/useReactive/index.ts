import { useRef } from "react";
import useUpdate from "../useUpdate";

function useReactive<T extends Record<string, any>>(initialState: T) {
  const ref = useRef(initialState);
  const [forceUpdate] = useUpdate();
  const proxyRef = useRef(
    new Proxy(ref.current, {
      get: (target: T, key: string | symbol) => {
        // 获取值
        return Reflect.get(target, key);
      },
      set: (target: T, key: string, value: any) => {
        // 设置值
        const ret = Reflect.set(target, key, value);
        forceUpdate();
        return ret;
      },
    })
  );
  return proxyRef.current;
}

export default useReactive;
