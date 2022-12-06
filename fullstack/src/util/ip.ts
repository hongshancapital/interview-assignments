import os from 'os'
//获取本机ip
let getIPAdress = function() {
    let interfaces = os.networkInterfaces();
    for (var devName in interfaces) {
        let iface = interfaces[devName] ;
        if(iface) {
            for (var i = 0; i < iface.length; i++) {
                let alias = iface[i];
                if (alias.family === 'IPv4' && alias.address !== '127.0.0.1' && !alias.internal) {
                    return alias.address;
                }
            }
        }
    }
}
export default getIPAdress;