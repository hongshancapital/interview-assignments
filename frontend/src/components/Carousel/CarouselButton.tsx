import "./CarouselButton.css";

export interface CarouselButtonProps {
  progress?: number;
}

export default function CarouselButton(props: CarouselButtonProps) {
  const { progress } = props;

  return (
    <div className="CarouselButton">
      {/** for white and black background */}
      <div className="CarouselButtonBackground" />
      <div className="CarouselButtonProgress" style={{ width: `${progress}%` }}></div>
    </div>
  );
}
