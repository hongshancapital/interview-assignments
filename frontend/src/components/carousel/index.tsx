import React, { useEffect, useState } from 'react';
import './carousel.css'
import banner1 from '../../assets/airpods.png';
import banner2 from '../../assets/iphone.png';
import banner3 from '../../assets/tablet.png';

function Carousel() {
    const [count, setCount] = useState(0);

    useEffect(() => {
        const timer: NodeJS.Timeout | null = setInterval(() => {
            setCount(n => {
                if (n >= 2) {
                    return 0
                } else {
                    return n + 1
                }
            });

        }, 3000);
        return () => clearInterval(timer);
    }, [count]);
  
    return (
            <div className="container">
                <ul className={`imgList imgList${count}`} >
                    <li>
                        <img src={banner1} />
                    </li>
                    <li>
                        <img src={banner2} />
                    </li>
                    <li>
                        <img src={banner3} />
                    </li>
                </ul>
            
                <ul className="ulSlide">
                    <li className={count === 0 ? 'active' : count > 0 ? 'bg' : ''}><div></div></li>
                    <li className={count === 1 ? 'active' : count > 1 ? 'bg' : ''}><div></div></li>
                    <li className={count === 2 ? 'active' : ''}><div></div></li> 
                </ul> 
            </div>
        )
    }

export default Carousel;
