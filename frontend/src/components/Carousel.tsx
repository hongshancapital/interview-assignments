import React, { ReactElement, useEffect, useRef } from "react";

type ItemProps = {
  duration?: number;
  autoplay?: boolean;
  children: any;
};

function Carousel(props: ItemProps): ReactElement {
  let { children, autoplay, duration = 2000 } = props;
  if (!Array.isArray(children)) children = [children];
  const childrenNumber: number = children.length;
  const wrapper = useRef(null);

  useEffect(() => {
    let timer: NodeJS.Timeout | null = null;
    let count: number = 0;
    const dots: Array<HTMLElement> = Array.from(
      document.querySelectorAll(".dot-hover")
    );
    const dotshoverDOM: HTMLElement = document.querySelector(
      ".dots-hover-wrapper"
    ) as HTMLElement;
    dotshoverDOM.style.width =
      childrenNumber * 50 + 15 * (childrenNumber - 1) + "px";
    dots[0].classList.add("animation");

    if (autoplay) {
      timer = setInterval(() => {
        count++;
        if (count === childrenNumber) {
          count = 0;
        }
        dots.forEach((item, index) => {
          item.classList.remove("animation");
          if (index === count) {
            item.classList.add("animation");
          }
        });

        const wrapperDOM: any = wrapper.current;
        wrapperDOM.style.left = -document.body.clientWidth * count + "px";
      }, duration);
    }

    return () => {
      if (timer) clearInterval(timer);
    };
  }, []);

  return (
    <div className="App">
      <div className="item-wrapper" ref={wrapper}>
        {children.map((item: ReactElement) => item)}
      </div>
      <div className="dots-wrapper">
        {Array(childrenNumber)
          .fill(1)
          .map((item, index) => (
            <div className="dot" key={index}></div>
          ))}
      </div>
      <div className="dots-hover-wrapper">
        {Array(childrenNumber)
          .fill(1)
          .map((item, index) => (
            <div className="dot-hover" key={index}></div>
          ))}
      </div>
    </div>
  );
}

export default Carousel;
