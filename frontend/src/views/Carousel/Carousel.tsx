import ProgressBar from './ProgressBar'
import {ImageItem } from './CarouselWrap'

const Carousel = ({left, imageList, curIndex}: {left: string, imageList:ImageItem[], curIndex:number}) => <>
  <div className="carousel" style={{left}}>
    {
      imageList.map((item, index) => (
        <div key={index} className="carousel-item" >
          <p className='carousel-title'>{item.title}</p>
          <p className='carousel-describe'>{item.describe}</p>
        </div>
      ))
    }
  </div>
  <ProgressBar length={imageList.length} curIndex={curIndex}/>
</>

export default Carousel