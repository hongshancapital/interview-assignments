## 文档
### 题目地址：

[Github](https://github.com/scdt-china/interview-assignments/tree/master/php)

### 说明：

1. 使用laravel 8.0 版本
### 目录及文件

###### 1 路由文件 routes\api.php
添加路由
```

use App\Http\Controllers\RegisterController;
Route::get('register', [RegisterController::class,'index']);
Route::post('register/create', [RegisterController::class,'create']);



```

##### 2 控制器文件 app\Http\Controllers\RegisterController.php

完成接口的安全认证，数据的验证，以及逻辑的处理，引入文件

```

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\DB;


```

##### 3 模板文件 resources\views\register.blade.php

表单显示

##### 4 控制api访问需要调用的中间件，解决CSRF为null值等问题 app\Http\Kernel.php
