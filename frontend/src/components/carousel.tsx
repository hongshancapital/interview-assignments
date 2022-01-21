import { ImageSet } from "../imageset";
import React, { useEffect, useRef, useState } from "react";
import styles from "./carousel.module.scss";
import clsx from "clsx";

export function Carousel({ images, className, delay }: { images: ImageSet[], className: string, delay: number }) {
  let [current, setCurrent] = useState(0);
  let [cWidth, setCWidth] = useState(""); // 填入不合法的height，让样式不生效
  let [cHeight, setCHeight] = useState("");
  let el = useRef<HTMLDivElement>(null);
  useEffect(() => {
    if (el.current !== null) {
      let cs = window.getComputedStyle(el.current);
      if (cs.width !== "320px") {
        setCWidth(cs.width);
      }
      if (cs.height !== "240px") {
        setCHeight(cs.height);
      }
    }
  }, [cWidth, cHeight]);
  useEffect(() => {
    let timer = setInterval(() => {
      setCurrent((current + 1) % images.length);
    }, delay);
    return () => clearInterval(timer);
  });

  return <div className={clsx(styles.carousel, className)} ref={el} style={{
    height: cHeight, width: cWidth
  }}>
    <div className={styles.scroll} style={{
      transform: `translateX(${-current * 100}vw)`
    }}>
      {
        images.map((img, index) => {
          return <div className={styles.bg} style={{
            backgroundImage: `url(${img.url})`,
            color: img.color,
            transform: `translateX(${index * 100}vw)`,
            height: cHeight, width: cWidth
          }} key={index}>
            <div className={styles.tip}>
              <div className={styles.title}>{img.title?.reduce((p, c) => {
                if (p.length !== 0) {
                  p.push(<br key={p.length}/>);
                }
                p.push(c);
                return p;
              }, [] as (JSX.Element | string)[])}</div>
              <div className={styles.text}>{img.text?.reduce((p, c) => {
                if (p.length !== 0) {
                  p.push(<br key={p.length}/>);
                }
                p.push(c);
                return p;
              }, [] as (JSX.Element | string)[])}</div>
            </div>
          </div>;
        })
      }
    </div>
    <div className={styles.bar}>
      {images.map((_, i) => {
        return <div className={clsx({
          [styles.pg]: true,
          [styles.current]: current === i
        })} key={i}>
          <div className={styles.pgi} style={{ animationDuration: delay + "ms" }}/>
        </div>;
      })}
    </div>
  </div>;
}