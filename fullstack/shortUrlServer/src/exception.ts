import { ExceptionFilter, Catch, ArgumentsHost, HttpException, HttpStatus } from '@nestjs/common';
import { ERROR } from './constants';



@Catch()
export class AllExceptionsFilter implements ExceptionFilter {
    catch(exception: any, host: ArgumentsHost) {
        const ctx = host.switchToHttp();
        const response = ctx.getResponse();
        const request = ctx.getRequest();

        let exceptionObj: any = {}, code = '', message = ''
        try {
            exceptionObj = JSON.parse(exception.message)
            code = exceptionObj.code
            message = exceptionObj.message
        } catch (e) {
            // 其他系统异常
            code = ERROR.SYS_ERR.code
            message = e.message
        }

        response.send({
            code,
            message
        });
    }
}
