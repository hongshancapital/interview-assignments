import React,{useEffect, useRef, useState} from 'react';
import imgArray from '../../assets';
import './index.css'

interface tabProps {
    resource: any[]
}

function Tab(props: tabProps){

    const [count, setCount] = useState(0);
    const botRef: any = useRef(null);
    useEffect(() => {
        setTimeout(()=>{
            const num = props.resource.length - 1
            if(count<num){
                setCount(count+1)
            }else {
                setCount(0)
                let domList = botRef.current.children
                for(let i = 0;i<domList.length;i++) {
                    domList[i].children[0].className = `bot`
                }
            }
        },3000)
    },[count])

    const [transform, setTransform] = useState(0);
    useEffect(() => {
        let left = - 800 * count
        setTransform(left);
        if(botRef.current) {
            let dom = botRef.current.children[count].children[0]
            dom.className = `bot moveBot`
        }
    },[count])

    // 轮播图片
    let showTab = (item: any, index: number) => {
        return (
            <li>
                <div className={`textWrap ${index === 0 ? 'fontWhite' : 'fontBlack'}`}>
                    <h1 key={index} className="itemH">{item.title}</h1>
                    <p key={index} className="itemP">{item.p}</p>
                </div>
                <div className="itemDiv">
                    <img src={imgArray(item.img)} alt="" key={index} />
                </div>
            </li>
        )
    }

    // 轮播滑动条
    let showBot = () => {
        return (
            <li className='botWrap'>
                <div className="bot"></div>
            </li>
        )
    }

    return (
        <div className='itemContent'>
            <ul style={{ left: transform}} className="itemWrap">
                {
                    props.resource.map((item: any,index: number) => {
                        return showTab(item,index)
                    })
                }
            </ul>
            <ul className="botItemWrap" ref={botRef}>
                {
                    props.resource.map((item: any,index: number) => {
                        return showBot()
                    })
                }
            </ul>
        </div>
    )
}

export default Tab;