Carousel 轮播默认展示:

```js
    
const pageJson = [
  {
    id: "1",
    titleList: ["xPhone"],
    subTitleList: ["Lots to love.less to spend","Starting at $399."],
    backgroundColor:'black',
    backgroundImage: `url(${require('./assets/iphone.png').default})`,
    color: "white",
  },
  {
    id: "2",
    titleList: ["Table"],
    subTitleList: ["just the right amount of everything."],
    backgroundImage: `url(${require('./assets/tablet.png').default})`,
  },
  {
    id: "3",
    titleList: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    subTitleList: [],
    backgroundImage: `url(${require('./assets/airpods.png').default})`,
  },
];
    <Carousel list={Carousel.buildPartSample(pageJson)} />

```

Carousel 定制轮播内容:

```js
    
const pageJson = [
  {
    id: "1",
    titleList: ["xPhone"],
    subTitleList: ["Lots to love.less to spend","Starting at $399."],
    backgroundColor:'black',
    backgroundImage: `url(${require('./assets/iphone.png').default})`,
    color: "white",
  },
  {
    id: "2",
    titleList: ["Table"],
    subTitleList: ["just the right amount of everything."],
    backgroundImage: `url(${require('./assets/tablet.png').default})`,
  },
  {
    id: "3",
    titleList: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    subTitleList: [],
    backgroundImage: `url(${require('./assets/airpods.png').default})`,
  },
];
const buildParts  = (list) => {
  return list.map((item) => {
    return {
     data: {
        key: item.id,
        style: {
          backgroundImage: item.backgroundImage || "none",
          color: item.color || "none",
          backgroundColor:item.backgroundColor || "none",
          backgroundSize: "auto 100%"
        },
      } as React.Attributes,
      component:<div className="part">
      <div className="container">
      {
          item.titleList.map((title)=>{
            return  <h1 className="title" key={title}>{title}</h1>
          })
        }
      </div>
    </div>,
    };
  });
};
    <Carousel list={buildParts(pageJson)} />

```