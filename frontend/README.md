
# Carousel
Carousel 组件文件路径: /components/carousel
CarouselItem 组件文件路径: /components/carousel-item

# Carousel Attributes
|     参数       |说明|类型 |可选值 |默认值 |
|------   | -------- | --------- | --------- | --------- |
| className | 走马灯wrap 样式 | string |  |  |
| width   | 走马灯的宽度 | number |   |  屏幕宽度|   
| hight   | 走马灯的高度 |    number |   |    屏幕高度|   
| initialIndex | 初始状态激活的幻灯片的索引，从 0 开始 | number | 	 | 0 |
| interval   | 自动切换的时间间隔，单位为毫秒 |    number |    |    3000 |
| indicationWidth   | 指示条 宽度 |    number |     |    50 |
| indicationHeight   | 指示条 高度 |    number |     |    6 |


# Carousel Events
|   事件名称   |     说明  | 回调参数  |   
|   ------    | -------- | --------- |
| onChange	| 幻灯片切换时触发 | oldIndex: 原幻灯片的索引; newIndex: 目前激活的幻灯片的索引  |   



# CarouselItem Attributes
|     参数      |说明|类型 |可选值 |默认值 |
|------   | -------- | --------- | --------- | --------- |
| className | 走马灯Item 样式 | string |  |  |
| index   | 当前索引 |    number |     |     |   
| width   | 宽度 |    number |     |    屏幕高度|   