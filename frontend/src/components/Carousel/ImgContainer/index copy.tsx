import React, {  useRef, useImperativeHandle } from "react";
import { IImgConProps } from "../CommonTypes";

import "./index.css";

const ImgContainer = (props: IImgConProps, ref: any) => {
  const { imgSrcs, duration, imgWidth, imgHeight } = props;
  const imgConRef = useRef<HTMLDivElement>(null);
  const timer = useRef<number>();
  const tick = 16;
  useImperativeHandle(
    ref,
    () => {
      return {
        switchTo,
      };
    },
    []
  );

  //给外部调用
  const switchTo = (index: number) => {
    if (index < 0) {
      index = 0;
    } else if (index > imgSrcs.length - 1) {
      index = imgSrcs.length - 1;
    }

    //根据index 计算最终的marginLeft
    const targetLeft: number = -index * imgWidth;
    var curLeft: number;

    //得到当前的marginLeft
    if (imgConRef.current) {
      curLeft = parseFloat(
        window.getComputedStyle(imgConRef.current).marginLeft
      );
      //console.log('curLeft',curLeft)
      //console.log('targetLeft',targetLeft)

      // //计算运动的次数
      const times = Math.ceil(duration / tick);

      // //需要运动的距离
      const totalDis = targetLeft - curLeft;

      const dis = totalDis / times;

      var curTime = 0;

      if (timer) window.clearInterval(timer.current);

      timer.current = window.setInterval(() => {
        curTime++;
        curLeft += dis;
        imgConRef.current!.style.marginLeft = curLeft + "px";
        if (curTime === times) {
          window.clearInterval(timer.current);
          imgConRef.current!.style.marginLeft = targetLeft + "px";
        }
      }, tick);
    }
  };
  //根据imgSrcs生成banner
  const imgs = imgSrcs.map((item, i) => (
    <div
      className="img--box"
      style={{
        width: imgWidth,
        height: imgHeight,
      }}
      key={i}
    >
      <img className="img" src={item} alt=""></img>
    </div>
  ));
  return (
    <div
      ref={imgConRef}
      className="img--container"
      style={{
        width: imgSrcs.length * imgWidth,
        height: imgHeight,
      }}
    >
      {imgs}
    </div>
  );
};

export default React.forwardRef(ImgContainer);
