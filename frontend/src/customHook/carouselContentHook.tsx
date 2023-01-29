import { ReactNode } from "react";

interface childProps {
  children: ReactNode,
  iconName: string,
  index: number,
  activeIndex: number,
} 
function useCarouselContent(props: childProps) {
  return (
    <div
     data-index={props.index}
     className={['contentItem', props.iconName, props.activeIndex === props.index ? 'contentAni' : '', props.index < props.activeIndex ? 'contentLeave' : ''].join(' ')}>
      <div className='contentText'>
        {props.children}
      </div>
      <div className='contentIcon'></div>
    </div>
  );
}

export default useCarouselContent;
