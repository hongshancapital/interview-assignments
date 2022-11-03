import React from 'react';

interface itemPProps {
    htmlString?:string
    className?:string
    [index:string]:any
}

const CarouselitemP: React.FC<itemPProps> = (props) => {
    const {htmlString='',className='',role='carousel-item-p'}:{htmlString?:string,className?:string,role?:any} = props;
    const createMarkup = {__html: htmlString}
    return (
        <p className={className} dangerouslySetInnerHTML={createMarkup} role={role} />
    );
}

export default CarouselitemP;