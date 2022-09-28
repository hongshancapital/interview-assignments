# Carousel for React

> 使用 react hook 制作 <br/>
> 设计思路: 工具类，只提供功能，不处理内容渲染，与业务解耦

### Version / 版本

    1.0

## Install / 安装
   
    npm install 
    or
    yarn

## Run / 运行

    npm run start
    or
    yarn run start

## Use / 使用

    import Carousel from "./components/carousel";

    ...
      <Carousel>
        {/* 你的列表 */}
        {/* eg. */}
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>
    ...

## Options / 配置选项

     width           number，宽度，不设置则为屏幕宽度
     height          number，高度，不设置则为300px
     loop            boolean，是否是循环列表，默认true
     indicator       boolean，是否有指示器，默认true
     controls        boolean，是否有左右按钮，默认true
     autoplay        boolean，是否自动播放，默认true
     duration        number，每一帧停顿时间，基于autoplay为true的时候，默认3000ms

## Plans / 计划展望

> 1、支持基于第一屏自适应显示，支持移动端显示；<br/>
> 2、支持拖拽滚动，支持 touch 滚动；<br/>
> 3、指示器样式、控制按钮样式支持自定义<br/>
