import React from "react";
import "./App.css";
import Carousel from './Carousel'


const data = [
  { url: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fuser-images.githubusercontent.com%2F15868458%2F61338685-3f650f00-a86d-11e9-81da-e8b83280dc16.jpg&refer=http%3A%2F%2Fuser-images.githubusercontent.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652331596&t=06f33822f55c87f2eb8e8166269e438b' },
  { url: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.runoob.com%2Fwp-content%2Fuploads%2F2018%2F03%2F20171106105551_60767.jpg&refer=http%3A%2F%2Fwww.runoob.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652331596&t=8e9de76c0a40a6d808ee50b406da887a' },
  { url: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg-blog.csdnimg.cn%2F20190118165143199.png%3Fx-oss-process%3Dimage%2Fwatermark%2Ctype_ZmFuZ3poZW5naGVpdGk%2Cshadow_10%2Ctext_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDU2ODYxMg%3D%3D%2Csize_16%2Ccolor_FFFFFF%2Ct_70&refer=http%3A%2F%2Fimg-blog.csdnimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652331983&t=0af3df0ea084ee47ade2f3ed7d28e119' },
  { url: 'https://img2.baidu.com/it/u=1540085917,2828805879&fm=253&fmt=auto&app=138&f=JPEG?w=255&h=255' },
  { url: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg2.doubanio.com%2Fview%2Frichtext%2Flarge%2Fpublic%2Fp74961171.jpg&refer=http%3A%2F%2Fimg2.doubanio.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652332800&t=8958dcc963ed4a3966e801cf449c5e74' }
]

// 提交作业啦 。。。

// 时间关系 没写过多的东西 例如无线循环 动画时长 懒加载等等 

// 因为我也才接触react 跟hook 时间不长 代码写的不太优美 目前实现的功能有 1.是否自动 2.面板指示点位置 3.是否显示面板指示点 4.切换滚动动画效果 5.设置自动轮播时间 6.设置初始位置索引值 7.切换面板的回调

// 最初的设想是想分成两个组件写 一个为包裹容器 Carousel  一个为循环的dom节点 CarouselItem 这样可以自定义每个轮播的内容 但是 出题是说写一个组件 所以没按自己的设想来

// 对于单元测试，我只是略微懂一点点 平时也没咋写 所以没写 望见谅

// 对于Figma SASS 以前工作也是用这些 所以都能应用

// 做前端有6年多了 因为待的公司比较多 每个公司应用的技术都不一样 所以前端得3大框架都能用来做项目  但是可能不太精 正常做项目是没问题的 

// 我今年未满28岁 自我评价是学习 熟悉 新事物反应速度很快，就算是没怎么接触的东西 或者不熟的东西 都能很快上手 做事喜欢效率 不喜欢拖拉 性格比较外向 

// 对于UI审美 这边是我搭建的一个小玩意 'http://web-0gsm006t5735440d-1300347897.tcloudbaseapp.com/#' 自我感觉能很高成都上还原设计稿 

// 原来自己是有个博客的 用docsify 写的 因为去年一直做国外的项目就没咋管 托管的服务器到期了 自己的电脑上的源代码也因为系统出问题搞丢了 最后只找到了一个很老版本，只有一点点内容 所以就没托管了

// 对于目前我想钻研 react 跟 hook 这块的的技术 所以一直在找运用相关技术的公司 想把这快的生态链技术学精 所以抱着试试看态度来面试 希望有机会能面试上 谢谢。。。。



function App() {
  return <div className="App">
    <Carousel data={data} initialSwipe={1} dotPosition='bottom' onChange={() => { console.log('切换了') }} />
  </div>;
}

export default App;
