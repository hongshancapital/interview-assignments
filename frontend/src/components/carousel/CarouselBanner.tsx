import "./CarouselBanner.css"

export interface CarouselBannerProps {
  title?: string[]
  subTitle?: string[]
  productImageSrc?: string
  textColor?: string
  backgroundColor?: string
}

const CarouselBanner: React.FC<CarouselBannerProps> = (props) => {
  const {
    title = [],
    subTitle = [],
    productImageSrc,
    textColor = "#000",
    backgroundColor = "#fff",
  } = props
  const mergedTitle = title.join("")
  const mergedSubTitle = subTitle.join("")
  return (
    <div className="carousel-banner" style={{ color: textColor }}>
      <div className="carousel-banner__placeholder-top"></div>
      {mergedTitle &&
        title.map((titleItem) => {
          return <h1 className="carousel-banner__title">{titleItem}</h1>
        })}
      {mergedSubTitle &&
        subTitle.map((subTitleItem) => {
          return <h2 className="carousel-banner__subtitle">{subTitleItem}</h2>
        })}
      {productImageSrc && (
        <img
          className="carousel-banner__image"
          src={productImageSrc}
          alt={mergedTitle || mergedSubTitle || ""}
        />
      )}
      <div
        className="carousel-banner__background"
        style={{ backgroundColor: backgroundColor }}
      ></div>
    </div>
  )
}

export default CarouselBanner
