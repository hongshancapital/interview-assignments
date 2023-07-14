import { useContext } from "react"
import { CarouselContext } from "./Context"

const Pagination = () => {
    const { slidesCount, activedIndex, autoPlay, autoPlayInterval } = useContext(CarouselContext)
    return <div className="PaginationContainer" data-testid="carousel-pagination">
        {new Array(slidesCount).fill(null).map((_,index) => <div key={index} className='PaginationItem'>
            {activedIndex === index && <div className={autoPlay ? 'actived autoPlayActived' : 'actived'} style={autoPlay && autoPlayInterval ? { animationDuration: `${autoPlayInterval}s`} : {}}></div>}
        </div>)}
    </div>
}

export { Pagination }