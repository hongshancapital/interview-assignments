import React, { MouseEventHandler, RefObject, useEffect, useMemo } from "react";
import { FC } from "react";
import './index.css';
const excuteNumberRex = /.*?([0-9]+?)/;

export type CarouselStepProps = {
    pages: unknown[],
    renderMs: number,

    /** 步骤条点击回调 */
    onChangeRenderIndex: (index: number) => void,
    renderIndex: number,
}

/**
 * 跑马灯步骤条
 * @returns 
 */
export const CarouselStep: FC<CarouselStepProps> = ({ 
    pages, 
    renderMs, 
    renderIndex, 
    onChangeRenderIndex 
}) => {
    const stepWrapRef = React.useRef<HTMLDivElement>();
    
    /** 样式替换器 */
    const bindReplaceStepStyle = useMemo(() => {
        
        const replaceStepStyle = (style: string) => {
            if (stepWrapRef.current) {
                const renderElement = stepWrapRef.current.children[renderIndex];
                renderElement.children[0].setAttribute('style', style)
            }
        }
        return replaceStepStyle;

    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [renderIndex, renderMs]);

    useEffect(() => {
        
        // 因为要动态控制时间长度
        // 这里样式更新顺带更新时间长度，并进行动画切换代替 class 替换
        // 统一控制变量方便维护
        bindReplaceStepStyle(`transition: width ${renderMs}s linear; width: 100%;`);
        return () => {
            bindReplaceStepStyle(`transition: width ${0}s linear; width: 0%;`);
        }

    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [renderIndex]);

    /**
     * 点击事件处理
     * @param e 
     */
    const onClickRenderIndex: MouseEventHandler<HTMLDivElement> = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
        const handleElement = e.target as HTMLDivElement;
        let id = handleElement.id || handleElement.parentElement!.id!;
        if (id) {
            const matchIds = id.match(excuteNumberRex);
            onChangeRenderIndex(Number(matchIds![1]));
        }
    }   

    //TODO: 位置自定义
    const stepWrapProps = {
        className: 'carousel-step-wrap',
        onClick: onClickRenderIndex,
    }
    return <div ref={stepWrapRef as RefObject<HTMLDivElement>} {...stepWrapProps}>
        {
            pages.map((_, index) => <div id={`carousel-step-${index}`} className={`carousel-step-bg`}>
                <div  className={`carousel-step-item`}></div>
            </div>)
        }
    </div>
}
