import React, { ReactElement, useEffect, useRef } from "react";

type ItemProps = {
  duration?: number;
  autoplay?: boolean;
  children: any;
};

function Carousel(props: ItemProps): ReactElement {
  let { children, autoplay, duration = 2000 } = props;
  if (!Array.isArray(children)) children = [children];
  const number = children.length;
  const wrapper = useRef(null);

  useEffect(() => {
    let timer: any = null;
    let count = 0;
    const dots = Array.from(document.querySelectorAll(".dot-hover"));
    const dotshoverDOM: any = document.querySelector(".dots-hover-wrapper");

    dotshoverDOM.style.width = number * 50 + 15 * (number - 1) + "px";
    dots[0].classList.add("animation");

    if (autoplay) {
      timer = setInterval(() => {
        count++;
        if (count === number) {
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
        {Array(number)
          .fill(1)
          .map((item, index) => (
            <div className="dot" key={index}></div>
          ))}
      </div>
      <div className="dots-hover-wrapper">
        {Array(number)
          .fill(1)
          .map((item, index) => (
            <div className="dot-hover" key={index}></div>
          ))}
      </div>
    </div>
  );
}

export default Carousel;
