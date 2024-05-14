import { useRef, useState } from "react";

export function useApi<F extends (...args: any[]) => PromiseLike<any>>(api: F) {
  const [state, set_state] = useState({
    loading: false,
    error: undefined as any,
    args: undefined as Parameters<F> | undefined,
    res: undefined as Awaited<ReturnType<F>> | undefined,
  });
  const _args = useRef<Parameters<F>>();
  const fn = ((...args: any) => {
    _args.current = args;
    set_state({ ...state, loading: true, error: undefined });
    return api(...args).then(
      (res) => {
        if (_args.current !== args) return new Promise((_) => _);
        set_state({ ...state, loading: false, args, res });
        return res;
      },
      (error) => {
        if (_args.current !== args) return new Promise((_) => _);
        set_state({ ...state, loading: false, args, error });
        throw error;
      }
    );
  }) as any as F;
  return { ...state, fn };
}
