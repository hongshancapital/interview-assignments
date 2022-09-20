import React, { useRef, useEffect, useState } from "react";

const useInterval = (
  callBack: () => void,
  delay: number
): [boolean, React.Dispatch<boolean>] => {
  const [stop, stopHandle] = useState(false);
  const timer = useRef({ callBack, id: 0 });

  useEffect(() => {
    timer.current.callBack = callBack;
  }, [callBack]);

  useEffect(() => {
    function tick() {
      if (stop) return;
      timer.current.callBack();
    }

    timer.current.id = window.setInterval(tick, delay);

    return () => {
      clearInterval(timer.current.id);
    };
  }, [delay, stop]);

  return [stop, stopHandle];
};

export default useInterval;
