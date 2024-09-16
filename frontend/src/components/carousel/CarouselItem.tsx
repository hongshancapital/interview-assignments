function CarouselItem({ children }: any) {
  return (
    <div
      className="carousel-item"
    >
      {children}
    </div>
  );
}
CarouselItem.displayName = "CarouselItem";
export default CarouselItem;