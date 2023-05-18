import { CSSProperties, FC, useCallback, useEffect, useMemo, useRef, useState } from "react";
import './processBar.css';

interface ProcessBarProps {
    total: number
    nextPageHandler: (idx: number) => void
    processGap: number
}
export const ProcessBar: FC<ProcessBarProps> = ({ total, nextPageHandler, processGap }) => {
    const [animationStyle, setAnimationStyle] = useState<Array<CSSProperties | undefined>>(Array(total).fill({width: '0', transition: '0s'}))
    const [curIdx, setCurIdx] = useState<number>(0)

    const nextAnimation = useCallback((curIdx = 0) => {
        setAnimationStyle(animationStyle.map((_, idx) => idx === curIdx ? {
            width: '50px',
            transition: `${processGap}s width`
        } : {
            width: '0px',
            transition: '0s'
        }
        ))
    }, [animationStyle])

    const transitionEndHanlder = useCallback(() => {
        let idx = (curIdx + 1) % total
        nextAnimation(idx)
        setCurIdx(idx)
        nextPageHandler(idx)
    }, [curIdx, total]) 

    useEffect(() => {
        nextAnimation()
    }, [])

    const mLeft = useMemo(() => {
        const gap = 8
        const barLength = 50
        return - (barLength * total + gap * (total - 1)) / 2 
    }, [total])

    return <div className="process-bar-container" style={{marginLeft: mLeft}}>
        {
            Array(total).fill('item').map((_, idx) => <div key={idx} className="process-bar-item">
                <div key={`content_${idx}`} className="process-bar-content" style={animationStyle[idx]} onTransitionEnd={transitionEndHanlder}/>
            </div>)
        }
    </div>
}