import React, {useState,useRef,useEffect} from 'react'
import airpods from '../../assets/airpods.png'
import iphone from '../../assets/iphone.png'
import tablet from '../../assets/tablet.png'
import './index.css'
let timer: NodeJS.Timer | null = null
const Carousel: React.FC = () => {
    const [tit] = useState<any>([airpods, iphone, tablet])
    let [selectIndex, setselectIndex] = useState<number>(0)
    const isMounted = useRef(true)

    //设置显示tab
    const trend = () => {
        setselectIndex((sIndex) => {
            return  sIndex < tit.length-1 ? sIndex + 1 : 0
        })
    }

    /**
     * 
     * @param idx 
     * 根据下表重置
     */
    const changeItem = (idx: number) => {
        setselectIndex(idx)
        if (timer) {
            clearInterval(timer)
        }
        setTimer()
    }

    //设置定时器
    const setTimer = () => {
        timer = setInterval(() => {
            trend()
        },2000)
    }

    useEffect(() => {
        //只执行设置一次定时器
        if (!isMounted.current) {
            setTimer()
        } else {
            isMounted.current = false
        } 
        return () => {
            //组件卸载清除定时器
            if (timer) {
                clearInterval(timer)
            }
        }
    },[])
    
    return (
        
        <div className='rotation-Box'>
            <div className="rotaion-Box-body" style={{ left: selectIndex * -100 + 'vw', transition: selectIndex!==0 ? ".3s all" : '' }}>
                {tit.map((item: string | undefined, index: number) => <div className='rotaion-Box-body-item' key={index}>
                    <img src={item} />
                </div>)}
            </div>
            <div className='edit-list'>
                {tit.map((item: string | undefined, index: number) => <div className='edit-item' key={index} onClick={()=>changeItem(index)}>
                    {
                        <div className={`edit-item-line ${selectIndex === index ?'edit-item-line-active':selectIndex > index?'edit-item-line-pre':''}`}></div>
                    }
                </div>)}
            </div>
        </div>
    )
}
export default Carousel