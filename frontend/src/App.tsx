import React from 'react';
import Carousel from './components/Carousel';
import './App.scss';

function App() {

  /*
   * Carousel 组件自动轮播，支持点击切换
   * 只有一个 banner 时无滚动效果，且隐藏切换按钮
   * 子节点支持任意标签，如：<div>、<p>、<a>
   * 组件自动为子节点添加 width 属性，width 等于窗口宽度（监听变化）
   */

  return (
    <div className='App'>
      <Carousel>
        <div className='banner iphone'>
          <p className='title'>xPhone</p>
          <p className='desc'>Lots to love. Less to spend.<br />Starting at $399.</p>
        </div>
        <div className='banner tablet'>
          <p className='title'>Tablet</p>
          <p className='desc'>Just the right amount of everything.</p>
        </div>
        <div className='banner airpods'>
          <p className='title'>Buy a Tablet or ×Phone for college.<br />Get arPods.</p>
        </div>
        {/* 测试标签 <p>1</p>
        <a>2</a>
        <em>3</em>
        <div>4</div> */}
      </Carousel>
    </div>
  );
}

export default App;