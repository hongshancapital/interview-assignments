

若项目链接不到`MongoDB`，建议将项目目录`express-react-typescript`从空间中复制出去启动项目。  
或者从我的gitHub pull下来执行也行。  
`https://github.com/Blair812/express-react-typescript`  

项目初始化  
1、安装 `MongoDB`   
    修改 `/src/server/configs.ts` 配置文件  
     `mongodb: url: 'IP地址',`
    
2、安装包 `yarn`   
    注：`cd fullstack/express-react-typescript/` 再执行 `yarn`


项目启动  
`npm run dev`    
    若报错的话，可能是 `nodejs` 版本过高。  
    控制台先执行下 `export NODE_OPTIONS=--openssl-legacy-provider` 然后，再执行`npm run dev`

单元测试  
`npm run test`   
    注：需要先执行 `npm run dev`生成js测试文件  


站点目录结构   

├── public  
└── src  
    ├── client  
    │   ├── Components  
    │   ├── Less  
    │   ├── Services  
    │   └── types  
    └── server  
        ├── domain  
        ├── middlewares  
        ├── models  
        ├── route  
        │   └── routes  
        ├── test  
        └── untils  
        