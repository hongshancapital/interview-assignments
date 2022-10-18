import React,{useEffect, useMemo, useRef} from 'react'

const Progress:React.FC<{
    count:number,
    activeIndex:number,
    duration:number,
    stopStatus:boolean,
    setProgressPercentage:(num:number) => void
}> = ({count,activeIndex,duration,stopStatus,setProgressPercentage}) => {
    const arr = new Array(count).fill(1)
    const progressRef = useRef<HTMLDivElement>(null)
    const progressItemRef = useRef<HTMLDivElement>(null)

    const renderItem = useMemo(() => {
        if(!arr.length)return null
        return arr.map((item,index) => 
            <div className='progressItem' ref={progressItemRef}>
                {activeIndex === index && 
                    <div 
                        ref={progressRef}
                        style={{
                            animationDuration:`${duration}ms`,
                            animationPlayState:stopStatus?'paused':'running'
                        }} 
                        className='progress' 
                    />
                }
            </div>)
    },[count,activeIndex,stopStatus])

    useEffect(() => {
        if(stopStatus && progressItemRef && progressRef){
            let parentWidth = progressItemRef.current!.offsetWidth
            let width = progressRef.current!.offsetWidth
            const percentage = 1 - width / parentWidth
            setProgressPercentage(percentage)
        }
    },[stopStatus,progressRef,progressItemRef])

    return <div className='progressContainer'>
        {renderItem}
    </div>
}

export default Progress