const CarouselItem = ({ children, bgColor }) => {
  return (
    <div className="carousel__item" style={{ backgroundColor: bgColor }}>
      { children }
    </div>
  )
}

export default CarouselItem