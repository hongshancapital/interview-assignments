/* eslint-disable jsx-a11y/alt-text */
import React, { useEffect, useRef, useState } from 'react'
import "./index.css"
export default function Carousel(props: any) {
    const imgdata=props.imgdata;
    
    const clsRef = useRef(['one', 'two', 'three', ])
    const dotsRef = useRef(['change', '', '', ''])

    const [dots, setDots] = useState([''])
    const [cls, setCls] = useState([''])

    useEffect(() => {
        setCls([...clsRef.current])
        setDots([...dotsRef.current])
        const time = setInterval(() => {
            const clsTmp = [...clsRef.current]
            const dotsTmp = [...dotsRef.current]
            let tmp = String(clsTmp.pop())
            clsTmp.unshift(tmp)
            let dotTmp = String(dotsTmp.pop())
            dotsTmp.unshift(dotTmp)
            setCls(clsTmp)
            setDots(dotsTmp)
            clsRef.current = clsTmp
            dotsRef.current = dotsTmp
        }, 2000)
        return () => clearInterval(time)
    }, [])
    return (
        <div className="box">
            <ul className='imgs'>
            {
                imgdata.map((item: any,index: React.Key | null | undefined)=>{
                    return <li key={index} className={cls[index as any]}>
                        <h1>{item.title}</h1>
                        <h3>{item.content}</h3>
                        <img src={item.img} alt="" />
                    </li>
                })
            }
            </ul>

            <ul className="list">
                <li className={dots[0]}></li>
                <li className={dots[1]}></li>
                <li className={dots[2]}></li>
            
            </ul>
        </div>

    )
}
