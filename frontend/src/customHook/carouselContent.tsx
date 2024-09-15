import { ReactNode } from "react";

interface IProps {
  children: ReactNode,
  iconName: string,
  index: number,
  activeIndex: number,
} 
function CarouselContent(props: IProps) {
  return (
    <div
     key={`${props.index}_${props.iconName}`}
     data-index={props.index}
     className={['contentItem', props.iconName, props.activeIndex === props.index ? 'contentAni' : '', props.index < props.activeIndex ? 'contentLeave' : ''].join(' ')}>
      <div className='contentText'>
        {props.children}
      </div>
      <div className='contentIcon'></div>
    </div>
  );
}

export default CarouselContent;
