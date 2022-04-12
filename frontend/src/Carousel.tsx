import React, { useEffect, useState, useRef } from 'react';
import './carousel.css';

interface IProps {
    list: string[];
}

let timer = null;
let width = 0;
const Carousel: React.FC<IProps> = ({ list }) => {
    const [currentIndex, setCurrentIndex ] = useState(0);
    const ulRef = useRef(null);
    useEffect(() => {
        width = document?.getElementsByClassName('img')[0]?.getClientRects()[0]?.width
        timer = setInterval(autoPlay, 3000);
    }, []); 
    const autoPlay = async () => {
        setCurrentIndex((currentIndex: number) => {
            return currentIndex < list.length - 1 ? currentIndex + 1 : 0;
        });
    }
    return (
        <div className="all" id='all'>
            <div className="screen" id="screen">
                <ul id="ul" style={{ transform: `translateX(-${width * currentIndex}px)`, width:`${width * list.length}px`}} ref={ulRef}>
                    {
                        list.map(item => {
                            return (
                                <li>
                                    <img src={item} className="img"/>
                                </li> 
                            )
                        })
                    }
                </ul>
                <ol id="square">
                    {
                        list.map((item, index) => {
                            return (
                                <li className={ currentIndex === index ? 'active': '' }> </li> 
                            )
                        })
                    }
                </ol>
            </div>
        </div>
    );
}

export default Carousel;
