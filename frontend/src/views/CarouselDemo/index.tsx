import React from "react";
import Carousel from '../../components/Carousel'
import './style.css'

// 轮播测试对象
interface CarouselItem {
  key: string //主键
  bgColor: string //背景色
  imageUrl: string //图像路径
  mainTitle: string //主标题
  subTitle: string //副标题
  mainStyle: string //主标题样式集合
  subStyle: string //副标题样式集合
}

export default function CarouselDemo() {
  const items: Array<CarouselItem> = [
    {
      key: "iphone",
      bgColor: '#111111',
      imageUrl: require('../../assets/iphone.png').default,
      mainTitle: 'xPhone',
      subTitle: 'Lots to love.Less to spend.<br/>Starting at $399.',
      mainStyle: ['main_title', 'light_color'].join(' '),
      subStyle: ['sub_title', 'light_color'].join(' ')
    },
    {
      key: "tablet",
      bgColor: '#fafafa',
      imageUrl: require('../../assets/tablet.png').default,
      mainTitle: 'Tablet',
      subTitle: 'Just the right amount of everything.',
      mainStyle: ['main_title', 'dark_color'].join(' '),
      subStyle: ['sub_title', 'dark_color'].join(' ')
    },
    {
      key: "airpods",
      bgColor: '#f1f1f3',
      imageUrl: require('../../assets/airpods.png').default,
      mainTitle: 'Buy a Tablet or xPhone for collage.',
      subTitle: 'Get arPods.',
      mainStyle: ['main_title', 'dark_color'].join(' '),
      subStyle: ['main_title', 'dark_color'].join(' ')
    }
  ];

  return (
    <Carousel autoplay={true} interval={3000}>
      {items.map((item: any) => <div key={item.key} style={{ background: `${item.bgColor} url(${item.imageUrl}) no-repeat center /auto 100%` }} className={'carousel_wrapper'}>
        <div className={'capation'}>
          <div className={item.mainStyle}>{item.mainTitle}</div>
          <div className={item.subStyle} dangerouslySetInnerHTML={{ __html: item.subTitle }} />
        </div>
      </div>)}
    </Carousel>
  )
}