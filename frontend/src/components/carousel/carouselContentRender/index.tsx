import React, { FC, isValidElement, useMemo } from 'react';
import { CarouselProps } from "..";
import { CarouselContent } from '../common/carouselContent';
import { CarouselImage } from '../common/carouselImage';
import './index.css';

/**
 * 跑马灯页签
 * @description 
 * 通过配置来新增修改跑马灯，而非通过 children 来配置内容
 * 考量到业务组件应简洁明了，采用配置式开发跑马灯。
 */
export const CarouselContentRender: FC<Pick<CarouselProps, 'pages'> & { renderIndex: number }> = ({
    pages,
    renderIndex
}) => {

    const renderElement = useMemo(() => {
        return pages.map((tempPage, index) => {
            const { type, props, children } = tempPage;
            const baseProps = {
                key: `carousel_image_${index}`,
                renderIndex: index
            }
            switch (type) {

                // 图片类内容渲染
                case 'Image': {
                    return <CarouselImage 
                        {...props} 
                        {...baseProps}
                    ></CarouselImage>;
                }

                // 自定义内容渲染 
                default: {

                    // 判断是否是合法的 ReactElement
                    if (!isValidElement(children)) {
                        console.error('error: 跑马灯第 %d 个组件配置错误', index);
                        return <CarouselContent {...baseProps}>
                            <div style={{ 
                                width: '100%', 
                                height: '100%', 
                                textAlign: 'center', 
                                paddingTop: '10%' 
                            }}>
                                <h1>组件配置错误, 请检查</h1>
                            </div>
                        </CarouselContent>
                    }
                    return <CarouselContent {...baseProps}>{children}</CarouselContent>;
                }
            }

        })
    }, [pages]);

    // 计算每页偏移值
    // memo包裹一下，虽然是简单对象， 但是对象属性里面有模板字符串，避免重复 eval
    const frameStyle: React.CSSProperties = useMemo(() => ({
        transform: `translateX(-${renderIndex * 100}%)`
    }), [renderIndex]);

    return <div className='carousel-frame' style={frameStyle}>
        {renderElement}
    </div>
}