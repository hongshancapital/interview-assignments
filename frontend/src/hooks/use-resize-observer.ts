import { useRef, useState } from "react";
import { useIsomorphicLayoutEffect } from "./use-isomorphic-layout-effect";

export function useResizeObserver<T extends HTMLElement>() {
  const ref = useRef<T>(null);
  const observer = useRef<ResizeObserver | null>(null);
  const [observerEntry, setObserverEntry] = useState<ResizeObserverEntry>();

  useIsomorphicLayoutEffect(() => {
    if (ref.current) {
      observer.current = new ResizeObserver(([entry]) =>
        setObserverEntry(entry)
      );
      observer.current.observe(ref.current as HTMLElement);
    }

    return () => observer.current?.disconnect();
  }, []);

  return [ref, observerEntry?.contentRect, observerEntry] as const;
}
