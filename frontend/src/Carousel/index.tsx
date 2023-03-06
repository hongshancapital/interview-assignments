import React, { useEffect, useState, useRef } from "react";
import "./index.css";
import banner1 from "../assets/airpods.png";
import banner2 from "../assets/iphone.png";
import banner3 from "../assets/tablet.png";

const Carousel = () => {
  const clsRef = useRef(["one", "two", "three", ]);
  const dotsRef = useRef(["change", "", ""]);

  const [dots, setDots] = useState([""]);
  const [cls, setCls] = useState([""]);

  useEffect(() => {
    setCls([...clsRef.current]);
    setDots([...dotsRef.current]);
    const time = setInterval(() => {
      const clsTmp = [...clsRef.current];
      const dotsTmp = [...dotsRef.current];
      let tmp = String(clsTmp.pop());
      clsTmp.unshift(tmp);
      let dotTmp = String(dotsTmp.pop());
      dotsTmp.unshift(dotTmp);
      setCls(clsTmp);
      setDots(dotsTmp);
      clsRef.current = clsTmp;
      dotsRef.current = dotsTmp;
    }, 3000);
    return () => clearInterval(time);
  }, []);

  return (
    <div className="box">
      <ul className="imgs">
        <li className={cls[0]}>
          <img src={banner1} />
        </li>
        <li className={cls[1]}>
          <img src={banner2} />
        </li>
        <li className={cls[2]}>
          <img src={banner3} />
        </li>
      </ul>

      <ul className="list">
        <li className={dots[0]}></li>
        <li className={dots[1]}></li>
        <li className={dots[2]}></li>
      </ul>
    </div>
  );
};

export default Carousel;
