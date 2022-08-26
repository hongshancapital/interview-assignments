import {AddressInfo} from "net";
import logger from "../logger";
import dotenv from 'dotenv';
import bodyParse from 'body-parser'
dotenv.config()
const {RouterFactory,Method} = require("../router/routers");
import {Router,Request,Response} from "express";
import config from "../config";
import { MyError } from "../util/MyError";
import { Server } from "http";
// import { MyError } from "../util/MyError";
var express = require('express');
const http = require('http')
// var debug = require('debug')('convert-url-api:server');
const cookieParser = require('cookie-parser')
require('../router/routers')
class ExpressServer {
    init():Server{
        let app = express();
        app.use(bodyParse.urlencoded({extended:false}));
        app.use(bodyParse.json())
        app.use(cookieParser()); 
        this.initRouters(app);
        app.set('port',config.server.port)
        var httpServer:any = http.createServer(app);
        httpServer.listen(config.server.port);
        httpServer.on('error',(error:any)=>{
            if(error.syscall !== 'listen'){
                throw error;
            }
            var bind:string = typeof config.server.port === 'string' ?  'Pipe' + config.server.port: 'Port' + config.server.port;
            switch (error.code){
                case 'EACCES':
                    logger.error(bind + ' requires elevated privileges');
                    // process.exit(1);
                    break;
                case 'EADDRINUSE':
                    logger.error(bind + ' is already in use');
                    // process.exit(1);
                    break;
                default:
                    throw error;
            }
        });
        httpServer.on('listening',()=>{
            var addr:AddressInfo | string | null = httpServer.address();
            if (addr) {
                var bind = typeof addr === 'string'
                    ? 'pipe ' + addr
                    : 'port ' + addr['port'];
                    logger.debug('Listening on ' + bind);
            }
        })
        logger.info('server is created! port:'+config.server.port)
        return app
    }
    initRouters(app:any):void{
        let _this = this;
        if(!app) {
            logger.error("express Object is not example。Please create it and transmit。")
            process.exit(1);
        }
            let routerFactory = new RouterFactory();
            //初始化路由配置
            routerFactory.init();
            let routersCount = routerFactory.getRouters().length;
            if(routersCount===0){
                logger.error('api groups count:[',routersCount,'] ');
            }
            routerFactory.getRouters().forEach((routers:any)=>{
                if(routers.getRouterList().length !== 0 ){
                    logger.info('starting load routers: [',routers.getGroupName(),']title: ',routers.getTitle() ,'api count:[',routers.getRouterList().length,']')
                    routers.getRouterList().forEach((router:any)=>{
                        let uri:string = '';
                        if(router.getRequestMapping().trim().length!==0){
                            uri = uri + routers.getRoot() + router.requestMapping
                            let tmpRouter:any = require('../controller/'+routers.getGroupName()+"/"+router.name)
                            let eRouter:Router = express.Router();
                            if(router.getMethod() === Method.POST){
                                eRouter.post(uri,async (req:any,res:any,next:any)=>{
                                    next
                                    await _this.agentInterface(req,res,tmpRouter);
                                })
                            }
                            app.use(eRouter);
                            app.use('/convert/longToShort',tmpRouter)
                            logger.log('loaded router [',uri,'] method:[' , router.getMethod() , ']')
                        }
                    })
                }
            })
    }
    async agentInterface(req:Request,res:Response,router:any):Promise<void>{
        //TODO 执行方法前校验
        //如果请求为POST并且协议不是JSON格式直接返回错误
        if(req.method.toLowerCase()===Method.POST){
            // @ts-ignore
            if(!req.header('content-type')||req.header('content-type').indexOf('application/json')===-1){
                res.status(403).send({code:'S001',msg:'Only JSON format requests are supported'});
                return;
            }
        }
        try{
        await router(req,res)
        }catch(exception){  // TODO 异常捕获，如为自定义异常则返回业务异常
            if(exception instanceof MyError){
                logger.error('Request Error:',exception.message);
                res.status(415).send({code:'B001',msg:exception.message});
                return;
            }
        }
        //TODO  执行方法后处理 如需要加解密可以使业务代码改成返回值，在 此处做通用返回加密
    };
}
export {ExpressServer}