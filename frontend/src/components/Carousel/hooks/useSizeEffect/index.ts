import {DependencyList, useEffect} from "react";
import useDebounceCallback from "../useDebounceCallback";

export default function useSizeEffect(callback: () => void, deeps: DependencyList) {
  const handler = useDebounceCallback(callback, deeps, 500);

  useEffect(() => {
    callback();
    window.addEventListener('resize', handler);
    return () => window.removeEventListener('resize', handler);
  }, []);
}