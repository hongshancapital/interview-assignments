import { FC, ReactElement } from "react";

interface CarouselItemProps {
    children?: ReactElement<any> | ReactElement<any>[],
    className?: string,
}

const CarouselItem: FC<CarouselItemProps> = (props) => {
    const { children, className = '' } = props;

    return (
        <div className={`${className} carousel-item__container`}>
            {children}
        </div>
    )
}

export default CarouselItem;