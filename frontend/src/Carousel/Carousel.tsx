import React, { useState, useEffect, useRef } from "react";
import "./Carousel.css";
import smoothscroll from "smoothscroll-polyfill";
import iphone from "../assets/iphone.png";
import tablet from "../assets/tablet.png";
import airpods from "../assets/airpods.png";

// interface List {
//   id: string,
//   img: string
// }
// type UserInfo = {
//   name: string,
//   age: number,
// }
// 例子：
// const User:React.FC<UserInfo> = (props) => {return <div className="User"><p>{ props.name }</p><p>{ props.age }</p></div>};  
const Carousel = () => {
  // const [ContentList] = useState<List[]>(
  //   [
  //     {
  //       id: 'a1',
  //       img: 'url1'
  //     },
  //     {
  //       id: 'a2',
  //       img: 'url2'
  //     },
  //     {
  //       id: 'a3',
  //       img: 'url3'
  //     },

  //   ]
  // );
  // 获取dom操作
  const imgBox = useRef<HTMLDivElement>(null);
  const GetContentScrollWrap = () => {
    return imgBox.current as HTMLDivElement;
  };
  const process1 = useRef<HTMLDivElement>(null);
  const GetProcess1 = () => {
    return process1.current as HTMLDivElement;
  };
  const process2 = useRef<HTMLDivElement>(null);
  const GetProcess2 = () => {
    return process2.current as HTMLDivElement;
  };
  const process3 = useRef<HTMLDivElement>(null);
  const GetProcess3 = () => {
    return process3.current as HTMLDivElement;
  };

  // 状态
  const [Index, setIndex] = useState<number>(0);
  const [swiperItemWidth, setContentListWidth] = useState<number>(0);
  const setProcess = (key: number) => {
    if (key === 0) {
      GetProcess3().style.width = "0%";
      GetProcess3().style.transition = "0s";

      GetProcess1().style.width = "100%";
      GetProcess1().style.transition = "3s";
    }
    if (key ===  1) {
      GetProcess1().style.width = "0%";
      GetProcess1().style.transition = "0s";

      GetProcess2().style.width = "100%";
      GetProcess2().style.transition = "3s";
    }
    if (key ===  2) {
      GetProcess2().style.width = "0%";
      GetProcess2().style.transition = "0s";

      GetProcess3().style.width = "100%";
      GetProcess3().style.transition = "3s";
    }
  };
  const Cut = (key: number) => {
    setIndex(key);
    // console.log("setProcess==key",key);
    setProcess(key);
  };

  const autoPlay = () => {
    let i: number = 0;
    let timer = setInterval(function () {
      i = i + 1;
      if (i === 3) {
        // 3为轮播图个数
        i = 0;
        Cut(0);
      } else {
        Cut(i);
      }
      // console.log("i的之变化==", i);
    }, 3000); // 循环播放时间
  };

  useEffect(() => {
    smoothscroll.polyfill();
    // swiper元素宽度
    setContentListWidth(
      (GetContentScrollWrap().children[0] as HTMLDivElement).offsetWidth
    );
    setProcess(0);
    autoPlay(); // 自动播放
  }, []); //第二个参数为空数组，则只初始化时执行一次

  useEffect(() => {
    // 控制swiper滚动
    let swiperTo = swiperItemWidth * Index;

    GetContentScrollWrap().style.left = -swiperTo + "px";
    GetContentScrollWrap().style.transition = ".5s"; // .5s是动画时间
  }, [Index]);

  return (
    <div className="out-box">
      {/* css+ts实现基本的轮播动画 */}
      <div className="img-box" ref={imgBox}>
        {/* 因图片大小不一致，所以背景图有点变形 */}
        {/* {ContentList.map((item)=>{<img src={item.img} alt="" />})} */}
        <div className="img-out">
          <div className="txt-box">
            <h2 className="txt-h colorfff">xPhone</h2>
            <p className="txt-p colorfff">lots to love.less to spend.</p>
            <p className="txt-p colorfff">Starting at $399</p>
          </div>

          <img src={iphone} alt="" />
        </div>
        <div className="img-out">
          <div className="txt-box">
            <h2 className="txt-h color0">Table</h2>
            <p className="txt-p color0">Just the right amount of everything.</p>
          </div>

          <img className="img2" src={tablet} alt="" />
        </div>
        <div className="img-out">
          <div className="txt-box">
            <h2 className="txt-h color0">
              Buy a Tablet or xPhone for college.
            </h2>
            <h2 className="txt-h color0">Get arPods.</h2>
          </div>
          <img className="img2" src={airpods} alt="" />
        </div>
      </div>
      <div id="selector">
        {/* {ContentList.map((item) =>
            <div id="line" key={item.id}><div id="loading-status"> <div id="process" ref={process}></div> </div></div>
        )} */}
        <div id="line">
          <div id="loading-status">
            <div id="process" ref={process1}></div>
          </div>
        </div>
        <div id="line">
          <div id="loading-status">
            <div id="process" ref={process2}></div>
          </div>
        </div>
        <div id="line">
          <div id="loading-status">
            <div id="process" ref={process3}></div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Carousel;
