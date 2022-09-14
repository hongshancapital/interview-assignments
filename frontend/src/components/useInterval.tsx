import React, { useState, useEffect, useRef } from 'react';

const useInterval = (callback:React.MutableRefObject<{}>, delay:number|null) => {
    
    const savedCallback = useRef({});
    const [isInit, setIsInit] = useState<boolean>(true);

    useEffect(() => {
        savedCallback.current = callback.current;
    });
  
    useEffect(() => {
        const tick = ():void => {
          if(typeof savedCallback.current === 'function')savedCallback.current();
        }
        if (delay !== null) {
          isInit && tick();
          setIsInit(false);
          const id:NodeJS.Timer = setInterval(tick, delay);
          return () => clearInterval(id);
        }else{
          setIsInit(true);
        }
    }, [isInit,delay]);
}

export default useInterval;