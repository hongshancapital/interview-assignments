//
//  LocalHTTPServer.swift
//  HJJSearchUpgrade
//
//  Created by haojiajia on 2021/1/3.
//

import Foundation

class LocalHTTPServer: NSObject {
  
    var goodsURL: URL?
    var isOPen:Bool = false
    
    static let shared = LocalHTTPServer()
    private var httpServer : HTTPServer! = nil

    override init() {
        super.init()
        httpServer = HTTPServer()
        httpServer.setType("_http.tcp")

        //设置http服务器根目录
        let webPath = Bundle.main.resourcePath
        httpServer.setDocumentRoot(webPath)
    }
    
    func startServer() -> Bool {
        if !isOPen{
            do{
                try httpServer.start()
                let ipAddress = "http://\(HTTPHelper.ipAddress() ?? ""):\(httpServer.listeningPort())"
                self.goodsURL = URL(string: ipAddress + "/goods.json")
                print("请打开以下网址: \(ipAddress)")
                isOPen = true
                return true
            }catch{
                print("服务器启动失败")
                return false
            }
        }else{
            //httpServer.stop()
            return false
        }
    }
    
}

class HTTPHelper : NSObject{
    class func ipAddress() -> String? {
        var addresses = [String]()
        
        var ifaddr : UnsafeMutablePointer<ifaddrs>? = nil
        if getifaddrs(&ifaddr) == 0 {
            var ptr = ifaddr
            while (ptr != nil) {
                let flags = Int32(ptr!.pointee.ifa_flags)
                var addr = ptr!.pointee.ifa_addr.pointee
                if (flags & (IFF_UP|IFF_RUNNING|IFF_LOOPBACK)) == (IFF_UP|IFF_RUNNING) {
                    if addr.sa_family == UInt8(AF_INET) || addr.sa_family == UInt8(AF_INET6) {
                        var hostname = [CChar](repeating: 0, count: Int(NI_MAXHOST))
                        if (getnameinfo(&addr, socklen_t(addr.sa_len), &hostname, socklen_t(hostname.count),nil, socklen_t(0), NI_NUMERICHOST) == 0) {
                            if let address = String(validatingUTF8:hostname) {
                                addresses.append(address)
                            }
                        }
                    }
                }
                ptr = ptr!.pointee.ifa_next
            }
            freeifaddrs(ifaddr)
        }
        return addresses.first
    }
}
