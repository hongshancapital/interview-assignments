import React, { FC, useEffect, useState } from "react";
import './carousel.css';

interface IPic {
    img: string;
}

interface IProps {
    data: IPic[];
    delay?: number;
}

const Carousel: FC<IProps> = ({ data = [], delay = 2000 }) => {

    const [index, setIndex] = useState<number>(0);

    useEffect(() => {
        if (!data.length) return;

        let timer = window.setTimeout(() => {
            setIndex(index >= data.length - 1 ? 0 : index + 1);
            timer = 0;
        }, delay);
        return () => {
            if (timer) {
                window.clearTimeout(timer);
            }
        }
    }, [data, index, delay]);

    return (
        <div className="carousel-container">
            <div className="pic-list" style={{ transform: `translate3d(-${index * 100}%, 0, 0)` }}>
                {
                    data.map(pic => <div className="pic" key={pic.img}>
                        <img src={pic.img} alt="" />
                    </div>)
                }
            </div>
            <div className="index-container">
                {
                    data.map((pic, i) =>
                        <div className={`index ${index === i ? 'current' : ''}`} key={pic.img}
                            onClick={() => setIndex(i)}
                        >
                            {
                                index === i &&
                                <div className="progress" style={{ animationDuration: delay / 1000 + 's' }}></div>
                            }

                        </div>
                    )
                }
            </div>
        </div>
    );
}

export default Carousel;
