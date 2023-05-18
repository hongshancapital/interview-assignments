import React, { FC, ReactNode, Children, useMemo, useState, CSSProperties } from 'react'
import './carousel.css'
import { ProcessBar } from './processBar'

interface IProps {
    // support Children and CardList as cardLayout array
    cardList?: ReactNode[]
    // unit second
    pageChangeGap?: number
    // unit second
    processGap?: number
}

export const Carousel: FC<React.PropsWithChildren<IProps>> = ({children, cardList, pageChangeGap = 1.5, processGap = 3}) => {
    const [moveAction, setMoveAction] = useState<CSSProperties | undefined>(undefined)

    const length = useMemo(() => {
        return cardList?.length ? cardList.length : Children.count(children)
    }, [cardList, children])

    const nextPage = (curIdx: number) => {
        setMoveAction({
            transform: `translate(${(-curIdx * 100)}vw)`,
            transition: `transform ${pageChangeGap}s`
        })
    }
    
    return <div className='carousel-container'>
        <div className='carousel-wrapper' style={moveAction}>
            {
                cardList ? cardList : children
            }
        </div>
        <ProcessBar total={length} nextPageHandler={nextPage} processGap={processGap}></ProcessBar>
    </div>
}