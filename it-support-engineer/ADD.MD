## 思路

调用re分别对行进行匹配，获取所需要的内容

## 文件介绍

- main.py 主函数入库
- analysis.py 业务逻辑代码
- main.mac 打包好的MacOS(11.4测试过)二进制文件
- main.exe 打包好的Windows10二进制

## 使用方法

```
Usage: main.py [OPTIONS] COMMAND [ARGS]...

Options:
  -f, --file TEXT  默认目标文件  [default: ./interview_data_set.gz]
  --help           Show this message and exit.

Commands:
  run-analysis
```

```
Usage: main.py run-analysis [OPTIONS]

Options:
  -u, --upload BOOLEAN   是否上传至服务器  [default: True]
  -s, --server_url TEXT  默认上传url  [default: https://foo.com/bar]
  --help                 Show this message and exit.
```



## 存在问题

1. 样例数据缺少年份，代码默认读取进来变成1900年
2. 同个进程断行可能算2个记录
3. 很多进程会多次启动，每次不同pid应该归在一个结构下

