按照自己对题目的理解，最终发送到服务器的内容是去重的。numberOfOccurrence里保存的值是同样的日志在同一小时段内出现的次数。
```json
{
    "timeWindow": "23",
    "deviceName": "BBAOMACBOOKAIR2",
    "processName": "VTDecoderXPCService",
    "processId": "1760",
    "description": "DEPRECATED USE in libdispatch client: Changing target queue hierarchy after xpc connection was activated; set a breakpoint on _dispatch_bug_deprecated to debug",
    "numberOfOccurrence": "1"
}
```
上面这条发送消息表示的是： **在23这个小时段里，同样的错误发生了一次。**

### 运行

```python
python3 main.py path/filename
```