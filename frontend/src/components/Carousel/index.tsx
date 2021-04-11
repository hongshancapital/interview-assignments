/**
 * Created by BangJie on 2021/4/10.
 */
import React from "react";
import { useState, useEffect } from "react";
import Img1 from "../../assets/iphone.png";
import Img2 from "../../assets/airpods.png";
import Img3 from "../../assets/tablet.png";
import Styles from "./index.module.scss";

const Carousel: React.FC = () => {
  const SCREEN_WIDTH = window.screen.width;
  const SCREEN_Height = document.documentElement.clientHeight;
  const style = {
    width: SCREEN_WIDTH,
    height: SCREEN_Height,
    backgroundAttachment: "fixed",
    backgroundRepeat: "no-repeat",
    backgroundSize: "100% 100%",
  };
  //设置步长
  const [step, setStep] = useState<number>(0);
  const [status, setStatus] = useState<boolean>(false);
  useEffect(() => {
    autoPlay();
  });
  const autoPlay = () => {
    if (status) {
      return;
    }
    if (step < 2) {
      // 使用setTimeout包裹，避免transitionProperty动画未关闭就切换的闪频
      setTimeout(() => {
        setStep(step + 1);
      }, 3000);
    } else {
      setTimeout(() => {
        setStep(0);
      }, 3000);
    }
  };
  const onClick = (step: number) => {
    setStep(step);
    setStatus(true);
  };
  const onMouseLeave = () => {
    setTimeout(() => {
      setStatus(false);
    }, 20000);
  };
  return (
    <div className={Styles.wrap}>
      <div
        className={Styles.carousel}
        style={{
          width: 3 * SCREEN_WIDTH,
          left: `-${step * 100}%`,
          transitionDuration: `${1}s`,
        }}
      >
        <div
          className={Styles.silder}
          style={{ ...style, ...{ backgroundImage: `url(${Img1})` } }}
        >
          <h2 className={Styles.title1}>xPhone</h2>
          <h3 className={Styles.content1}>Lots to love,less to spend.</h3>
          <h3 className={Styles.content1}>Starting at $399.</h3>
        </div>
        <div
          className={Styles.silder}
          style={{ ...style, ...{ backgroundImage: `url(${Img2})` } }}
        >
          <h2 className={Styles.title2}>Tablet</h2>
          <h3 className={Styles.content2}>Just the right of everything.</h3>
        </div>
        <div
          className={Styles.silder}
          style={{ ...style, ...{ backgroundImage: `url(${Img3})` } }}
        >
          <h2 className={Styles.title2}>Buy a Tablet or xPhone for college.</h2>
          <h2 className={Styles.title3}>Get arPods.</h2>
        </div>
      </div>
      <div className={Styles.btnGroup}>
        <div
          className={Styles.btn}
          style={{ backgroundColor: `${step === 0 ? "gray" : "#000"}` }}
          onClick={() => onClick(0)}
          onMouseLeave={() => onMouseLeave()}
        >
            <div className={!status && step === 0?Styles.bar:''} />
        </div>
        <div
          className={Styles.btn}
          style={{
            backgroundColor: `${step === 0 || step === 1 ? "gray" : "#000"}`,
          }}
          onClick={() => onClick(1)}
          onMouseLeave={() => onMouseLeave()}
        >
            <div className={!status && step === 1?Styles.bar:''} />
        </div>
        <div
          className={Styles.btn}
          style={{
            backgroundColor: `${step === 0 || step === 2 ? "gray" : "#000"}`,
          }}
          onClick={() => onClick(2)}
          onMouseLeave={() => onMouseLeave()}
        >
            <div className={!status && step === 2?Styles.bar:''} />
        </div>
      </div>
    </div>
  );
};

export default Carousel;
