import React, { ReactElement } from 'react';

export interface ItemProps {
  id: number;
  img: string;
  title: string;
  fontColor: string;
  describe?: string;
  themeColor: string;
}

interface BannerListProps {
  carouselData: Array<ItemProps>
}

function BannerList(props: BannerListProps): ReactElement {
  const { carouselData } = props;
  console.log('BannerList --- ')

  return (
    <>
      {
        carouselData.map((item: ItemProps) => {
          return (
            <div
              key={item.id}
              className="banner-item"
              style={
                {
                  color: item.fontColor,
                  backgroundColor: item.themeColor,
                  backgroundImage: `url(${item.img})`,
                }
              }
            >
              <div className='banner-item-text'>
                <p className='banner-item-title'>{item.title}</p>
                {
                  !!(item.describe?.trim()) ? 
                    <p className='banner-item-desc'>{item.describe}</p>
                    : null
                }
              </div>
            </div>
          )
        })
      }
    </>
  )
}

const MemoBannerList = React.memo(BannerList);

export default MemoBannerList;