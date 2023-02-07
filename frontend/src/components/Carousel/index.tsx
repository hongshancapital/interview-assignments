import React, { useEffect, useRef } from "react";

import "./index.css";
type CarouselProp = {
  children: React.ReactNode;
  durning?: number;
  paginationBackColor?: string;
  paginationColor?: string;
};

function Carousel({ children, paginationBackColor = "#e4e4e4", paginationColor = "white" }: CarouselProp) {
  const childLen = Array.isArray(children) ? children?.length : 1;
  const er: React.RefObject<HTMLDivElement> = useRef<HTMLDivElement>(null);
  const nav: React.RefObject<HTMLDivElement> = useRef<HTMLDivElement>(null);
  // const [curIdx, setCurIdx] = useState<number>(0);
  const curIdx = useRef<number>(0);
  const timer = useRef<number | null>(null);
  const handleInterval = () => {
    timer.current && window.clearInterval(timer.current)
  }

  useEffect(() => {
    if (childLen > 1 && er.current !== null && nav.current !== null) {
      nav.current.children[curIdx.current].classList.add('active')
      timer.current = window.setInterval(() => {
        if(nav.current) {
          nav.current.children[curIdx.current].classList.remove('active')
        }
        curIdx.current = (curIdx.current + 1) % childLen;
        if(er.current) {
          er.current.style.transform = `translateX(-${curIdx.current * 100}%)`;
        }
        
        if(nav.current) {
          nav.current.children[curIdx.current].classList.add('active')
        }
        
      }, 3000);
    }

    return function() {
      handleInterval()
    }
  }, [er, nav, childLen, children]);

  return (
    <div className="carousel-wrap">
      <div className="absolute" ref={nav}>
        {
          Array.isArray(children) && children?.map((item: React.ReactElement) => {
            return <nav key={item.key} style={{ backgroundColor: paginationBackColor }}>
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
