import { useEffect } from "react";

export default function useMount(func: Function) {
  useEffect(() => {
    typeof func === "function" && func();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
}
