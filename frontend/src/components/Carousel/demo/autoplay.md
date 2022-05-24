## 自动播放

```tsx
import { Carousel } from "components";

export default () => (
  <Carousel autoplay={true}>
    <div className="foo-item">one</div>
    <div className="foo-item">two</div>
    <div className="foo-item">three</div>
  </Carousel>
);
```

```css
.foo-item {
  height: 200px;
  background-color: #ff7875;
  line-height: 200px;
  text-align: center;
  color: aliceblue;
}
```
