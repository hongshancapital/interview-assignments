import React from 'react'
import cn from 'classnames'
import style from './App.module.scss'
import Carousel from './components/carousel'
import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'
import airpodsImg from './assets/airpods.png'

const slides = [
    {
        titles: ['xPhone'],
        contents: ['Lots to love. Less to spend.', 'Starting at $399.'],
        image: iphoneImg,
        alt: 'iphone',
        background: style.bg_black,
    },
    {
        titles: ['Tablet'],
        contents: ['Just the right amount of everything.'],
        image: tabletImg,
        alt: 'tablet',
        background: style.bg_white,
    },
    {
        titles: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
        contents: [],
        image: airpodsImg,
        alt: 'airpods',
        background: style.bg_gray,
    },
]

function App() {
    return (
        <div className={style.main}>
            <Carousel>
                {slides.map((slide, slideKey) => (
                    <div key={slideKey} className={cn(style.slide, slide.background)}>
                        <section className={style.content}>
                            {slide.titles.map((title, key) => (
                                <h2 key={key}>{title}</h2>
                            ))}
                            {slide.contents.map((content, key) => (
                                <p key={key}>{content}</p>
                            ))}
                        </section>
                        <section className={style.image}>
                            <img src={slide.image} alt={slide.alt} />
                        </section>
                    </div>
                ))}
            </Carousel>
        </div>
    )
}

export default App
