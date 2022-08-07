import React from 'react'
import {CarouselConfigProps} from "../../components/Carousel/type";
import styles from './index.module.css'
import airpods from '../../assets/airpods.png'
import iphone from '../../assets/iphone.png'
import tablet from '../../assets/tablet.png'

interface CommonProductPageProps {
    data: CarouselConfigProps,
}
const CommonProductPage = (props: CommonProductPageProps) => {
    const {
        data: {
            title,
            description,
            img,
            fontColor = 'black',
            bgColor = 'white',
            bgHeight = 400,
        }
    } = props

    // 背景图像分辨率不同，采用背景色+背景图片高度配置
    return <div
        className={styles.productPage}
        style={{ color: fontColor, backgroundColor: bgColor }}
    >
        <div className={styles.bg} style={{ backgroundImage: `url(${img})`, color: fontColor, backgroundSize: `auto ${bgHeight}px` }}/>
        <div className={styles.text}>
            {
                title.map((t: string, index) => {
                    return <div className={styles.productTitle} key={index}>{t}</div>
                })
            }
            {
                description.map((des: string, index) => {
                    return <div className={styles.productDescription} key={index}>{des}</div>
                })
            }
        </div>
    </div>
}

export const DemoData = [{
    title: ['xPhone'],
    description: ['Lots to love. Less to spend.', 'Starting at $399.'],
    img: iphone,
    fontColor: 'white',
    bgColor: '#111111',
    bgHeight: 450,
},{
    title: ['Tablet'],
    description: ['Just the right amount of everything.'],
    img: tablet,
    fontColor: 'black',
    bgColor: '#fafafa',
    bgHeight: 450,
}, {
    title: ['Buy a Tablet or xPhone for college.', 'Get arPods'],
    description: [],
    img: airpods,
    fontColor: 'black',
    bgColor: '#f1f1f3',
    bgHeight: 450,
}]

export default CommonProductPage