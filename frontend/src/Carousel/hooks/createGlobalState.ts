import { Dispatch, useEffect, useState } from "react";

/**
 * this method is copied from react-use
 * I'm the author of this method
 */

export function createGlobalState<S = any>(initialState: S) {
  const store: { state: S; setState: (state: S) => void; setters: Function[] } = {
    state: initialState,
    setState(state: S) {
      store.state = state;
      store.setters.forEach((setter) => {
        setter(state);
      });
    },
    setters: [],
  };

  return () => {
    const [state, setState] = useState<S>(store.state);
    useEffect(() => {
      setState(store.state);

      store.setters.push(setState);

      return () => {
        store.setters = store.setters.filter((setter) => setter !== setState);
      };
    }, []);
    return [state, store.setState as Dispatch<S>] as const;
  };
}
