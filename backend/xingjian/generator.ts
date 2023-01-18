var global_id = 100000000

/*
 * 1.一个很朴素的id generator，有点傻：）;
 * 2.功能仅限于能用，生产环境一般会有专门的微服务模块，并且保证放号的唯一性，后续需要处理的冲突就越少;
 */
export function GetUniqID(): number {
    return global_id++;
}