import './CarouselCard.css';

export interface CarouselCardProps {
  className?: string;
  title: string;
  desc?: string;
  bgImage: string;
  color: string;
  bgColor: string;
}

const CarouselCard = ({ className, title, desc, bgImage, color, bgColor }: CarouselCardProps) => {
  return (
    <div
      className={['carousel-card', className].filter(Boolean).join(' ')}
      style={{
        color,
        backgroundColor: bgColor,
        backgroundImage: `url(${bgImage})`,
      }}
    >
      <h2 className={'carousel-card__title'}>{title}</h2>
      {desc && <p className={'carousel-card__desc'}>{desc}</p>}
    </div>
  );
};
CarouselCard.displayName = 'CarouselCard';

export default CarouselCard;
