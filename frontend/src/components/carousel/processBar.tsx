import { CSSProperties, FC, useCallback, useEffect, useMemo, useRef, useState } from "react";
import './processBar.css';
import { getDefaultAnimationStyle } from "../../utils";

interface ProcessBarProps {
    total: number
    nextPageHandler: (idx: number) => void
    processGap: number
}

export const ProcessBar: FC<ProcessBarProps> = ({ total, nextPageHandler, processGap }) => {
    const [animationStyle, setAnimationStyle] = useState<Array<CSSProperties | undefined>>(getDefaultAnimationStyle(total))
    const { current } = useRef<{timmer: NodeJS.Timeout | undefined}>({timmer: undefined})

    const nextAnimation = useCallback((curIdx = 0) => {
        setAnimationStyle(animationStyle.map((_, idx) => idx === curIdx ? {
            width: '50px',
            transition: `${processGap}s width`
        } : {
            width: '0px',
            transition: '0s'
        }
        ))
        current.timmer = setTimeout(() => {
            let idx = (curIdx + 1) % total
            nextAnimation(idx)
            nextPageHandler(idx)
        }, processGap * 1000)
    }, [animationStyle, nextPageHandler, current, processGap, total])

    useEffect(() => {
        nextAnimation()
        return () => {
            clearTimeout(current.timmer)
        }
    }, [current, nextAnimation])

    const mLeft = useMemo(() => {
        const gap = 8
        const barLength = 50
        return - (barLength * total + gap * (total - 1)) / 2 
    }, [total])

    return <div className="process-bar-container" style={{marginLeft: mLeft}}>
        {
            Array(total).fill('item').map((_, idx) => <div key={idx} className="process-bar-item">
                <div key={`content_${idx}`} className="process-bar-content" style={animationStyle[idx]}/>
            </div>)
        }
    </div>
}