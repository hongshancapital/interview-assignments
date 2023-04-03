

import iphone from "./assets/p1.png";
import tablet from "./assets/p2.png";
import airpods from "./assets/p3.png";

import style from "./carouselInfo.module.scss";
import Carousel, { CarouselItem } from "./Carousel";

// 轮播图数据
const info = [
  {
    id: 1,
    title: "xPhone",
    describe: "Lots to love. Less to spend. Starting at $399.",
    image: iphone,
    boxClass:'boxOne'
  },
  {
    id: 2,
    title: "Tablet",
    describe: "Just the right amount of everything.",
    image: tablet,
    boxClass:'boxTwo'
  },
  {
    id: 3,
    title: "Buy a Tablet or xPhone for college. Get arPods.",
    image: airpods,
    boxClass:'boxThree'
  },
  
];

/**
 * @param {title} title 标题
 * @param {describe} describe 描述
 * @param {image} image 图片
 * @returns 轮播图 主体
 */
 export const CarouselInfo = ({ title = "", describe = "", image = "",boxClass=''  }) => {
  return (
    <div className={ `${style.carousel_info_container} ${style[boxClass]}`} >
      <div className={style.info_wrap}>
        <div className={style.title}>{title}</div>
        <div className={style.describe}>{describe}</div>
      </div>
      <img className={style.info_img} src={image} alt="pic"/>
    </div>
  );
};

const App = () => {
  return (
        <Carousel>
          {info?.map((item) => {
            return (
              <CarouselItem key={item.id} >
                <CarouselInfo title={item.title} boxClass={item.boxClass}  describe={item.describe} image={item.image}/>
              </CarouselItem>
            );
          })}
        </Carousel>
  );
};

export default App;

