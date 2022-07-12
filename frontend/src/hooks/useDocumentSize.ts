import { useState, useEffect } from "react";
import throttle from "../utils/throttle";

export default function useDocumentSize() {
  const [width, updateWidth] = useState(document.documentElement.clientWidth);
  const [height, updateHeight] = useState(
    document.documentElement.clientHeight
  );

  useEffect(() => {
    const throttledResizeHandler = throttle(250, () => {
      updateWidth(document.documentElement.clientWidth);
      updateHeight(document.documentElement.clientHeight);
    });

    window.addEventListener("resize", throttledResizeHandler);

    return () => {
      throttledResizeHandler.cancel();
      window.removeEventListener("resize", throttledResizeHandler);
    };
  }, []);

  return [width, height];
}
