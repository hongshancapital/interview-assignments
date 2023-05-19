import React, { FC, ReactNode, Children, useMemo, useState, CSSProperties } from 'react'
import './carousel.css'
import { ProcessBar } from './processBar'

interface IProps {
    // support Children and CardList as cardLayout array
    cardList?: ReactNode[]
    // unit second
    pageChangeDuration?: number
    // unit second
    processDuration?: number
}

export const Carousel: FC<React.PropsWithChildren<IProps>> = ({children, cardList, pageChangeDuration = 0.8, processDuration = 3}) => {
    const [moveAction, setMoveAction] = useState<CSSProperties | undefined>(undefined)

    const length = useMemo(() => {
        return cardList?.length ? cardList.length : Children.count(children)
    }, [cardList, children])

    const nextPage = (curIdx: number) => {
        setMoveAction({
            transform: `translate3d(${(-curIdx * 100)}vw, 0, 0)`,
            transition: `transform ${pageChangeDuration}s`
        })
    }
    
    return <div className='carousel-container'>
        <div className='carousel-wrapper' style={moveAction}>
            {
                cardList ? cardList : children
            }
        </div>
        <ProcessBar total={length} nextPageHandler={nextPage} processDuration={processDuration}></ProcessBar>
    </div>
}
