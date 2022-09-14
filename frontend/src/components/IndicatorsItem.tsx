import React from 'react';

interface IitemProps {
    activeStyle: React.CSSProperties
    indicatorsClick: any
    indicatorsMouseLeave: any
    [index:string]:any
}

const IndicatorsItem: React.FC<IitemProps> = (props) => {
    const { activeStyle, indicatorsClick, indicatorsMouseLeave, role='indicators-item' } :
          { activeStyle: React.CSSProperties, 
            indicatorsClick: any, 
            indicatorsMouseLeave: any, 
            role?:any 
          } = props;
    return (
        <div className='item' role={role} 
        onClick={indicatorsClick}
        onMouseLeave={indicatorsMouseLeave}>
            <div className='bg-line'></div>
            <div className='active-line' style={activeStyle}></div>
        </div>
    );
}

export default IndicatorsItem;