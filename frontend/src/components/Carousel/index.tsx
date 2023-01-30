import React, { useEffect, useRef, useState } from "react";

import "./index.css";
type CarouselProp = {
  children: React.ReactNode;
  durning?: number;
  paginationBackColor?: string;
  paginationColor?: string;
};
let i: number = 0;
function Carousel({ children, durning = 3000, paginationBackColor = "#e4e4e4", paginationColor = "white" }: CarouselProp) {
  const childLen = Array.isArray(children) ? children?.length : 1;
  const er: React.RefObject<HTMLDivElement> = useRef<HTMLDivElement>(null);
  const nav: React.RefObject<HTMLDivElement> = useRef<HTMLDivElement>(null);
  const [curIdx, setCurIdx] = useState<number>(0);
  const timer = useRef<number | null>(null);
  const handleInterval = () => {
    timer.current && window.clearTimeout(timer.current)
  }

  useEffect(() => {
    if (childLen > 1 && er.current !== null && nav.current !== null) {
      nav.current.children[curIdx].classList.add('active')
      timer.current = window.setTimeout(() => {
        if(nav.current) {
          nav.current.children[curIdx].classList.remove('active')
        }
        
        let t = curIdx + 1;
        if (t >= childLen) {
          t = 0;
        }
        setCurIdx(t);
        if(er.current) {
          er.current.style.transform = `translateX(-${t * 100}%)`;
        }
        
        if(nav.current) {
          nav.current.children[t].classList.add('active')
        }
        
      }, durning);
    }

    return function() {
      handleInterval()
    }
  }, [er, nav, childLen, children, durning, curIdx]);
  
  return (
    <div className="carousel-wrap" style={{"--durning": `${durning/1000}s`}}>
      <div className="absolute" ref={nav}>
        {
          Array.isArray(children) && children?.map((item: React.ReactNode) => {
            return <nav key={Math.random()} style={{ backgroundColor: paginationBackColor }}>
              <div style={{ backgroundColor: paginationColor }}></div>
            </nav>
          })
        }
      </div>
      <div className="carousel-transform" ref={er}>
        {children}
      </div>
    </div>
  );
}

export default Carousel;
