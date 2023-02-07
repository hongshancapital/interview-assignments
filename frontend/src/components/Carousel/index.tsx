import React, {useEffect, useRef} from 'react'
import './index.css'

interface Obj {
    img?: string,
    title?: string,
    describe?: string,
    color?: string,
    bgColor?: string
}

interface ImgProps {
    imgUrl?: Obj[],
}

let num: number = 1
let timer: any = null
let viewColor: any = null
const Carousel: React.FC<ImgProps> = (props) => {
    let length = props.imgUrl ? props.imgUrl.length : 3;
    let imgUrl = props.imgUrl;
    let time = 2000;
    //获取网页宽度
    let clientWidth = document.body.clientWidth
    const demo = useRef<HTMLDivElement>(null) as any;
    useEffect(() => {
        demo.current.style.width = `${clientWidth * length}px`;
        demo.current.style.transition = 'all 0.5s';
        viewColor = document.getElementsByClassName('view_color') as any
        viewColor[0].classList.add('animate')
        console.log(viewColor)
        clearInterval(timer)
        timer = move()
        return () => {
            clearInterval(timer)
        }
    }, [])

    function move() {
        setInterval(() => {
            if (num === length) {
                num = 0
            }
            for (let i = 0; i < viewColor.length; i++) {
                const element = viewColor[i];
                element.classList.remove('animate')
            }
            viewColor[num].classList.add('animate')
            demo.current.style.transform = `translateX(-${clientWidth * num}px)`
            num++
        }, time)
    }

    return (
        <div className="carousel_box">
            <div className="img_box" ref={demo}>
                {
                    imgUrl?.map((item, index) => {
                        return (
                            <div style={{
                                background: `url(${item.img}) bottom center no-repeat`,
                                float: "left",
                                width: `${clientWidth}px`,
                                height: '600px',
                                backgroundSize: 'auto 100%',
                                backgroundColor: `${item.bgColor}`
                            }} key={item.img}>
                                <h1 style={{color: `${item.color}`,textAlign: "center", whiteSpace: "pre-wrap",paddingTop: "100px"}}>{item.title}</h1>
                                <h2 style={{color: `${item.color}`,textAlign: "center", whiteSpace: "pre-wrap"}}>{item.describe}</h2>
                            </div>
                        )
                    })
                }
            </div>
            <div className="line_box">
                {
                    imgUrl?.map((item, index) => {
                        return (
                            <div className="view_box" key={item.img}>
                                <div className="bg_color"></div>
                                <div className="view_color"></div>
                            </div>
                        )
                    })
                }
            </div>


        </div>
    )
}

Carousel.defaultProps = {
    imgUrl: [
        {
            img: '',
            title: '',
            describe: '',
            color: '#000',
            bgColor: '#f1f1f3'
        }
    ]
}
export default Carousel