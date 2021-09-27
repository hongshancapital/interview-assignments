//
//  HTTPServer.swift
//  scdt-homework
//
//  Created by Chr1s on 2021/9/25.
//

import Foundation
import Embassy
import Ambassador
import Combine

// MARK: - HTTP服务器 base on Ambassador
class IOSHttpServer {
    
    static let shared = IOSHttpServer()
    private init() {
        initServer()
    }
    
    var loop: EventLoop?
    var router: Router?
    var server: HTTPServer?
    
    // MARK: - 初始化Server
    func initServer() {
        loop = try! SelectorEventLoop(selector: try! KqueueSelector())
        router = Router()
        guard let loop = loop,
              let router = router else {
            print("Server Init Failed!")
            return
        }
        server = DefaultHTTPServer(eventLoop: loop, port: 8080, app: router.app)
    }
       
    // MARK: - Server监听
    func beginListen()  {
        
        var response: StockInfo = StockInfo()
        
        // Start HTTP server to listen on the port
        router!["/api/v2/users"] = DelayResponse(JSONResponse() { environ -> Any in

            // 获取搜索关键字
            let input = environ["QUERY_STRING"] as! String
            let index = input.firstIndex(of: "=")
            let query = input.subString(from: index!).dropFirst()
  
            // 在Mock数据中搜索
            response = self.searchContent(String(query))
            
            // 转换为Ambassador需要的格式
            var jsonArray: [[String: Any]] = []
            for item in response {
                jsonArray.append(item.dictionary)
            }
            return jsonArray

        }, delay: .delay(seconds: 0.3))

        do {
            try! server!.start()
            loop!.runForever()
        }
    }
    
    // MARK: - 根据关键字返回搜索结果
    func searchContent(_ search: String) -> StockInfo {
       
        var stock: StockInfo = []
        
        _ = mockJSONData.filter { element -> Bool in
            
            var newElement = element
            
            newElement.content = element.content.filter {
                // 判断content中的author项是否包含搜索关键字
                $0.author.lowercased().contains(search.lowercased())
            }
       
            if newElement.content == [] {
                return false
            } else {
                stock.append(newElement)
                return true
            }
        }
        return stock
    }
}

extension String {
    // MARK: - 截取字符串
    func subString(from: String.Index) -> String {
        return String(self[from..<endIndex])
    }
}



