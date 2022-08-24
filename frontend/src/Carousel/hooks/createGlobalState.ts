import { useEffect, useState } from "react";
import { merge } from "../utils/merge";

/**
 * this method is copied from react-use
 * I'm the author of this method
 */

export function createGlobalState<S = any>(initialState: S) {
  const store: { state: S; setState: (state: Partial<S>) => void; setters: Function[] } = {
    state: initialState,
    setState(state: Partial<S>) {
      store.setters.forEach((setter) => {
        store.state = merge(store.state, state) as S;
        setter(store.state);
      });
    },
    setters: [],
  };

  return () => {
    const [state, setState] = useState<S>(store.state);
    useEffect(() => {
      store.setters.push(setState);

      return () => {
        store.setters = store.setters.filter((setter) => setter !== setState);
      };
    }, []);
    return [state, store.setState] as const;
  };
}
