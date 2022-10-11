import React, { useRef, useEffect, FC, useState, useCallback } from "react";
import "./Carousel.css";

interface Types {
  children: React.ReactNode;
  resourceList:Array<any>;
  direction?: "left" | "right";
  timeout?: number;
}



const Carousel: FC<Types> = ({ children, direction = "left", timeout = 3000,resourceList }) => {
    
    let timer: any = null;
let pageWidth: number = 0;
let disX: number = 0;
let X: number = 0;
let page: number = 0;
let startX: number = 0;
let endX: number = 0;


    
    {React.Children.map(children, (element:any, idx) => {
        let el= React.cloneElement(element, { ref: idx });
      })}
  const dragBox = useRef<HTMLDivElement | null>(null);

  const swiper = useRef<HTMLDivElement | null>(null);

  const pagnation = useRef<HTMLDivElement | null>(null);



  const getChildren = () => children as any;

  const getChildrenLength = getChildren().length;

  const getdragPagnation = () => pagnation.current as any;

  const getdragSwiper = () => swiper.current as any;

  const getdragBox = () => dragBox.current as any;

  const [pageIndex, setPageIndex] = useState<number>(0);
  const setDrag = useCallback((X: number) => {
    getdragBox().style.transform = `translate3d(${X - pageWidth}px,0px,0px)`;
  }, []);

  const fnE = useCallback(() => {
    if (page <= -1) {
      page = 4;
      setPageIndex(page);
      getdragBox().style.transition = "none";
      X = -page * pageWidth;
      setDrag(X);
    }
    if (page >= getChildrenLength) {
      page = 0;
      setPageIndex(page);
      getdragBox().style.transition = "none";
      X = -page * pageWidth;
      setDrag(X);
    }
  }, [setDrag, getChildrenLength]);

  const leftDrag = useCallback(
    (index?: number) => {
      getdragBox().style.transition = ".3s ease all";
      page = index ?? page + 1;
      setPageIndex(page);
      X = -page * pageWidth;
      getdragBox().ontransitionend = fnE;
      setDrag(X);
    },
    [fnE, setDrag]
  );
  const rightDrag = useCallback(
    (index?: number) => {
      getdragBox().style.transition = ".3s ease all";
      page = index ?? page - 1;
      setPageIndex(page);
      X = -page * pageWidth;
      getdragBox().ontransitionend = fnE;
      setDrag(X);
    },
    [fnE, setDrag]
  );
  const FnEnd = useCallback(
    (e: MouseEvent) => {
      endX = e.pageX;
      if (Math.abs(startX - endX) > 80) {
        startX > endX ? leftDrag() : rightDrag();
      } else {
        getdragBox().style.transition = ".3s ease all";
        X = -page * pageWidth;
        getdragBox().ontransitionend = fnE;
        setDrag(X);
      }
      getdragBox().onmousemove = null;
      getdragBox().onmouseup = null;
    },
    [fnE, leftDrag, rightDrag, setDrag]
  );

  const FnMove = useCallback(
    (e: MouseEvent) => {
      X = e.pageX - disX;
      setDrag(X);
    },
    [setDrag]
  );

  const fnMove = useCallback(() => {
    timer = setInterval(() => {
      direction === "left" ? leftDrag() : rightDrag();
    }, timeout);
    getdragPagnation().className = "pagnation animation";
  }, [direction, leftDrag, rightDrag, timeout]);

  const FnStart = useCallback(
    (e: MouseEvent) => {
      getdragBox().style.transition = "none";
      startX = e.pageX;
      disX = startX - X;
      getdragBox().onmousemove = FnMove;
      getdragBox().onmouseup = FnEnd;
      return false;
    },
    [FnEnd, FnMove]
  );

  const fnOver = useCallback(() => {
    clearInterval(timer);
    getdragPagnation().className = "pagnation";
  }, []);

  const fnTo = (index: number) => {
    const cur = index;
    pageWidth = getdragSwiper().clientWidth;
    clearInterval(timer);
    direction === "left" ? leftDrag(cur) : rightDrag(cur);
    // fnMove();
  };

  useEffect(() => {
    pageWidth = getdragSwiper().clientWidth;
    getdragBox().style.width = `${pageWidth * (getChildrenLength + 2)}px`;
    getdragBox().style.transform = `translate3d(${-pageWidth}px,0px, 0px)`;
    getdragBox().onmousedown = FnStart;
    getdragBox().onmouseover = fnOver;
    getdragPagnation().onmouseover = fnOver;
    getdragBox().onmouseout = fnMove;
  }, [FnStart, fnOver, fnMove, getChildrenLength]);

  useEffect(() => {
    fnOver();
    fnMove();
  }, [fnOver, fnMove]);

  

  return (
    <div style={{position:'relative'}}>
      <div className="swiper" ref={swiper}>
        <div className="swipers" ref={dragBox}>
          <div className="swipers-item" style={resourceList[getChildrenLength - 1].style}>
            {getChildren()[getChildrenLength - 1]}
          </div>
          {getChildren().map((item: any, index: any) => (
            <div key={index} className="swipers-item" style={resourceList[index].style}>
              {getChildren()[index]}
            </div>
          ))}
          <div className="swipers-item" style={resourceList[0].style}>
            {getChildren()[0]}
          </div>
        </div>
      </div>
      <div className="pagnation" ref={pagnation}>
        {getChildren().map((item: any, index: any) => (
          <div onClick={() => fnTo(index)} key={index} className={pageIndex === index ? "active" : ""}>
            <span style={{animation:pageIndex === index ?`myfirst ${timeout/1000}s linear`:'initial'}}></span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
