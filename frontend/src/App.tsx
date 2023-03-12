import Carousel from "./components/carousel";
import Banner, { BannerProps } from "./components/banner";
import airpodsImg from "./assets/airpods-small.jpg";
import iphoneImg from "./assets/iphone-small.jpg";
import tabletImg from "./assets/tablet-small.jpg";
import "./App.scss";

// ## API

// | 参数 | 说明 | 类型 | 默认值 | 版本 |
// | --- | --- | --- | --- | --- |
// | autoplay | 是否自动切换 | boolean | false |  |
// | width | 宽度设置 | number \| string | 100% |  |
// | height | 高度设置 | number \| string | 100% |  |
// | interval | 轮播时间 | number | 3000 |  |
// | transitionTime | 动画过渡时间 | number | 1000 |  |
// | mouseEnterEvent | 走马灯鼠标经过事件(非全屏场景下使用) | boolen | false |  |
// | mouseLeaveEvent | 走马灯鼠标离开事件(非全屏场景下使用) | boolen | false |  |

// ## 方法

// | 名称                           | 描述                                              |
// | ------------------------------ | ------------------------------------------------- |
// | goTo(index) | 切换到指定面板    |
// | next()                         | 切换到下一面板                                    |
// | prev()                         | 切换到上一面板                                    |

// 轮播图数据
const bannerList: BannerProps[] = [
  {
    id: 1,
    title: ["xPhone"],
    subTitle: ["Lots to love.Less to spend.", "Starting at $399"],
    backgroundColor: "rgba(17, 17, 17, 1)",
    fontColor: "rgba(255, 255, 255, 1)",
    imgSrc: iphoneImg,
  },
  {
    id: 2,
    title: ["Tablet"],
    subTitle: ["Just the right amount of everything."],
    backgroundColor: "rgba(250, 250, 250, 1)",
    fontColor: "rgba(0, 0, 0, 1)",
    imgSrc: tabletImg,
  },
  {
    id: 3,
    title: ["Buy a Tablet or xPhone for college.", "get arPods."],
    subTitle: [],
    backgroundColor: "rgb(241, 241, 243, 1)",
    fontColor: "rgba(0, 0, 0, 1)",
    imgSrc: airpodsImg,
  },
];

function App() {
  return (
    <div className="App">
      <Carousel autoplay>
        {bannerList.map((ele: BannerProps) => (
          <Banner {...ele}></Banner>
        ))}
      </Carousel>
      {/* write your component here */}
    </div>
  );
}

export default App;
