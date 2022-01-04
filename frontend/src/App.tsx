import React from 'react'
import cn from 'classnames'
import style from './App.module.scss'
import Carousel from './components/carousel'
import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'
import airpodsImg from './assets/airpods.png'

function App() {
    return (
        <div className={style.main}>
            <h1 className={style.title}>Bruce FE Homework</h1>
            <h3>使用说明:</h3>
            <p>
                Carousel为自适应大小，如要控制Carousel的大小和展示位置，请提供一个容器来包裹Carousel。
                <br />
                每一个div节点为一个幻灯片，div里面为你要设置的内容。
            </p>
            <pre>
                {`
<Carousel duration={3000}>
    <div>...</div>
    <div>...</div>
    ...
</Carousel>
            `}
            </pre>

            <h4>参数：</h4>
            <p>duration 为可选，类型：number，单位：毫秒，默认值：3000毫秒</p>

            <h4>DEMO:</h4>
            <div className={style.demo}>
                <Carousel>
                    <div className={cn(style.slide, style.bg_black)}>
                        <section className={style.content}>
                            <h2>xPhone</h2>
                            <p>Lots to love. Less to spend.</p>
                            <p>Starting at $399.</p>
                        </section>
                        <section className={style.image}>
                            <img src={iphoneImg} alt="iphone" />
                        </section>
                    </div>
                    <div className={cn(style.slide, style.bg_white)}>
                        <section className={style.content}>
                            <h2>Tablet</h2>
                            <p>Just the right amount of everything.</p>
                        </section>
                        <section className={style.image}>
                            <img src={tabletImg} alt="tablet" />
                        </section>
                    </div>
                    <div className={cn(style.slide, style.bg_gray)}>
                        <section className={style.content}>
                            <h2>Buy a Tablet or xPhone for college.</h2>
                            <h2>Get airPods.</h2>
                        </section>
                        <section className={style.image}>
                            <img src={airpodsImg} alt="airpods" />
                        </section>
                    </div>
                </Carousel>
            </div>
        </div>
    )
}

export default App
