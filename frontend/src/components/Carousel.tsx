import { useEffect, useState } from 'react';
import { ImgList } from '../App';

import './carousel.css';

interface IImgList {
    imgList: ImgList
}
const Carousel = ({ imgList }: IImgList) => {
    const [currentIndex, setCurrentIndex] = useState<number>(0)

    useEffect(() => {
        // 开始切换
        const run = () => {
            let nextIndex: number = 0;

            setCurrentIndex((pre) => {
                nextIndex = pre + 1;
                if (nextIndex >= imgList.length) {
                    nextIndex = 0;
                }
                return nextIndex;
            });

            const currentCarouselItem: Element = document.querySelectorAll('.carousel-board')[0];
            currentCarouselItem.setAttribute('style', `transform: translateX(-${nextIndex * 100}%)`)
        }

        let timer = setInterval(run, 3000)
        return () => clearInterval(timer);
    }, [])


    return (
        <div className='carousel'>
            <ul className="carousel-board">
                {imgList.map((val) => (
                    <li className="carousel-board-item">
                        {val.text}
                        <div className={`carousel-board-item-bg ${val.pngClassName}`} />
                    </li>
                ))}
            </ul>
            <div className="progress-line-box">
                {imgList.map((val, index) => (
                    <div className='progress-line-container'>
                        <div className='progress-line'>
                            <div className='progress-line-bg' style={{ width: currentIndex === index ? 40 : 0, visibility: currentIndex === index ? 'visible' : 'hidden' }}></div>
                        </div>
                    </div>

                ))}
            </div>
        </div>
    )
}

export default Carousel;
