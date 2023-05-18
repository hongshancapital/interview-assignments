import { useRef } from "react";

function useLatest<T extends any>(current: T) {
  const storeValue = useRef(current);

  storeValue.current = current;

  return storeValue.current;
}

export default useLatest;
