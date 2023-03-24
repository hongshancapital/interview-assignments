import React, { useState } from "react"
import Pagination from "../Pagination"
import './index.css'

type CarouselProps = {
  time: number;
  dataSource: CarouselDataItem[];
}

export type CarouselDataItem = {
  id: string;
  banner: string;
}


function Carousel ({ dataSource, time }: CarouselProps) {
  const [currentIndex, setCurrentIndex] = useState<number>(0)

  const handleChange = (current: number) => setCurrentIndex(current)

  return (
    <div
      className="carousel-component" 
    >
      <div 
        className="container" 
        style={{ transform: `translateX(-${currentIndex * 100}vw)`}}
      >
        {dataSource.map(carousel => (
          <React.Fragment key={carousel.id}>
            <div className="banner" style={{ backgroundImage: `url(${carousel.banner})` }} />
          </React.Fragment>
        ))}
      </div>
      <div className="pagination">
        <Pagination 
          pagination={dataSource.map(data => data.id)} 
          current={currentIndex} 
          time={time} 
          onChange={handleChange}
        />
      </div>
    </div>
  )
}

export default Carousel