import { IBaseInf } from '../../types/modelTypes';

/** 轮播组件属性 */
export interface CarourselProps {
    //起始帧
    startIndex?: number;
    //切换速度
    switchSpeed?: number;
    //切换等待时间
    switchInterval?: number;
    // 是否自动运行
    autoPlay?: boolean;
    // 点击事件
    onClick?: (data:IBaseInf, index:number) => void;
    // 切换帧事件
    onChange?: (data:IBaseInf,index:number) => void;
    data: IBaseInf[];
}

export interface CarourselLoader {
    className?: string;
    item?: string;
}

/** 轮播内容项属性 */
export interface CarourselItemProps {
    itemData: IBaseInf
}

/** 轮播通用配置 */
export interface CarourselRef {
    timer?: number | undefined;
}