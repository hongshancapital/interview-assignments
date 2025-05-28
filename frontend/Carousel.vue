<template>
  <div>
    <div class="carousel-wrapper" @mouseover="carouselOver"  @mouseout="carouselOut">
        <!-- 图片容器 -->
        <div class="img-content">
          <!-- 
            这里初始化只加载第一张图片，用户点击或定时器再加载其他图片
            这种加载模式也可优化初始化页面加载速度
           -->
          <a><img :src="imgSrc" :width="this.width" :height="this.height" /></a>
        </div>
        <!-- 底部圆形小图标 -->
        <div class="indicator-content" ref="indicator">
          <ul class="indicator-list">
            <li class="indicator-item" v-for="(item, index) in defaultList" :key="item.id">
              <a class="indicator-icon" @mouseover="iconOver(index)" :class="index === pos ? 'active' : '' "></a>
            </li>
          </ul>
        </div>
        <!-- 上一张、下一张按钮 -->
        <div class="control-content">
          <div title="上一张" class="prev" @click="toggleImg('prev')"><</div>
          <div title="下一张" class="next" @click="toggleImg('next')">></div>
        </div>
    </div>     
  </div>
</template>

<script>
const iconWidth = 30;
export default {
  /* 接收父组件的值,轮播图宽度、高度、相邻两张图片切换的间隔时间、轮播图路径数组*/
  props: {
    width: {
      type: Number,
      default: 900
    },
    height: {
      type: Number,
      default: 400
    },
    interval: {
      type: Number,
      default: 2000
    },
    carouselList: {
      type: Array,
      default: function () {
          return []; 
      }
    }
  },
  data() { 
  	return {
      // 子组件中模拟的轮播图数组，实际开发中应由父组件传入
      defaultList: [
        {id: 1, src: 'http://img12.360buyimg.com/da/jfs/t3172/29/7532815266/78514/96c6e177/58ba3348N479cafe1.jpg'},
        {id: 2, src: 'http://img10.360buyimg.com/da/jfs/t5626/206/5387922315/163107/149fda50/595ef2caN399971af.jpg'},
        {id: 3, src: 'http://img13.360buyimg.com/da/jfs/t6481/166/2269199700/58810/742f31c4/595f5a97Nc1170590.jpg'}
      ],
      // 默认显示第一张图片，数组下标为0
      pos: 0,
      // 定时器
      startmove: ''
  	}
  },
  created() {
    // 调用定时器
    this.startmove = setInterval(this.bannerMove, this.interval);
    // 动态计算底部小图标容器的宽度和水平居中
    this.$nextTick(() => {
      this.$refs.indicator.style.width = iconWidth * this.defaultList.length + 'px';  
      this.$refs.indicator.style.marginLeft = -(iconWidth / 2) * this.defaultList.length + 'px';  
    })
  },
  computed: {
    
    iconClass() {
      return '';
    },
  
    imgSrc() {
      return this.defaultList[this.pos].src;
    }
  },
  beforeDestroy() {
   
    clearInterval(this.startmove);  
  },
  methods: {
   // 图片上、下一张切换
    toggleImg(type) {
      if(type === 'prev'){
        this.pos === 0 ? this.pos = this.defaultList.length - 1 : this.pos -- ;
      }else{
        this.pos === this.defaultList.length - 1 ? this.pos = 0 : this.pos ++ ;
      }
    },
    // 小图标鼠标hover事件
    iconOver(index) { 
      if(isNaN(index)) return;
      this.pos = index;
    },
    // 图片自动切换定时器方法
    bannerMove() {
      this.pos === this.defaultList.length - 1 ? this.pos = 0 : this.pos ++ ;
    },
    // 鼠标移入图片区域，清除定时器，防止和用户切换图片事件冲突
    carouselOver() {
      clearInterval(this.startmove);  
    },
     // 鼠标移出图片区域，开启定时器
    carouselOut() {
      this.startmove = setInterval(this.bannerMove, this.interval);
    }
  },
  components: {
   
  }
};
</script>

<style  scoped>
	
  
  .carousel-wrapper{
	position: relative;
	margin-bottom: 50px;
	width: 900px;
	height: 400px;
	&:hover{
		.control-content{
			.prev, .next{
				opacity: 1;
			}
		}
	}
	.img-content{
		position: absolute;
		top: 0;
		left: 0;
	}
	.indicator-content{
		position: absolute;
		bottom: 20px;
		left: 50%;
		/* margin-left: -45px; */
		/* width: 90px; */
		height: 22px;
		background: rgba(223, 223, 223, 0.6);
		border-radius: 11px;
		.indicator-list{
			overflow: hidden;
			.indicator-item{
				float: left;
				width: 30px;
				height: 22px;
				.indicator-icon{
					display: inline-block;
					margin: 3px 0 0 7px;
					width: 16px;
					height: 16px;
					border-radius: 50%;
					background: #fff;
					&.active{
						background: #df3526;
					}
				}
			}
		}
	}
	.control-content{
		.prev, .next{
			position: absolute;
			top: 170px;
			width: 40px;
			height: 60px;
			line-height: 60px;
			text-align: center;
			background: rgba(63, 64, 65, 0.7);
			color: #fff;
			font-size: 40px;
			cursor: pointer;
			opacity: 0.2;
		}
		.prev{
			left: 0;
		}
		.next{
			right: 0;
		}
		
	}
}
</style>