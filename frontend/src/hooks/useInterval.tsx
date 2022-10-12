import React, { useRef, useEffect, useState } from "react";

const useInterval = (
  callBack: () => void,
  delay: number
): [boolean, React.Dispatch<boolean>] => {
  const [stop, stopHandle] = useState(false);
  const timer = useRef(callBack);

  useEffect(() => {
    timer.current = callBack;
  }, [callBack]);

  useEffect(() => {
    const id = window.setInterval(() => {
      if (stop) return;
      timer.current();
    }, delay);

    return () => clearInterval(id);
  }, [delay, stop]);

  return [stop, stopHandle];
};

export default useInterval;
