const CarouselItem = ({ children, bgColor }) => {
  const divStyle = {
    height: '100vh',
    width: '100vw',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: bgColor
  }
  return (
    <div style={divStyle}>
      { children }
    </div>
  )
}

export default CarouselItem