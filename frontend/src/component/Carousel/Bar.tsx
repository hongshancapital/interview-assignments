import React, {
  useRef,
  useEffect,
  useState,
  ReactNode,
  HTMLAttributes,
} from "react";
import "./style.css";

export interface Props {
  active?: boolean;
  onFinish?(): any;
  attr?: HTMLAttributes<HTMLSpanElement>;
}

function Bar({ attr, active, onFinish }: Props) {
  const el = useRef(null);

  useEffect(() => {
    const { current } = el;

    if (current) {
      const onEnd = () => {
        if (onFinish) onFinish();
      };

      current.addEventListener("animationend", onEnd);

      return () => {
        current.removeEventListener("animationend", onEnd);
      };
    }
  }, [el.current]);

  return (
    <span
      {...attr}
      data-active={active}
      className="carousel-bar-item"
      ref={el}
    />
  );
}

export default Bar;
