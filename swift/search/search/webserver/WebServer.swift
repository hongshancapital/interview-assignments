//
//  WebServer.swift
//  search
//
//  Created by bc on 2020/9/9.
//  Copyright © 2020 sc. All rights reserved.
//

import Foundation
import Darwin.C

struct MockServer{
    static let DUMMYKEY = "Dyson"
    
    static func run(){
        let zero = Int8(0)
        let transportLayerType = SOCK_STREAM // TCP
        let internetLayerProtocol = AF_INET // IPv4
        let sock = socket(internetLayerProtocol, Int32(transportLayerType), 0)
        var sock_opt_on = Int32(1)
        setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &sock_opt_on, socklen_t(MemoryLayout.size(ofValue: sock_opt_on)))
        
        print("sock: \(sock)")
        let portNumber = UInt16(4000)
        let socklen = UInt8(socklen_t(MemoryLayout<sockaddr_in>.size))
        var serveraddr = sockaddr_in()
        serveraddr.sin_family = sa_family_t(AF_INET)
        serveraddr.sin_port = in_port_t((portNumber << 8) + (portNumber >> 8))
        //        serveraddr.sin_addr = in_addr(s_addr: in_addr_t(0))
        serveraddr.sin_addr.s_addr = inet_addr("127.0.0.1")
        serveraddr.sin_zero = (zero, zero, zero, zero, zero, zero, zero, zero)
        withUnsafePointer(to: &serveraddr) { sockaddrInPtr in
            let sockaddrPtr = UnsafeRawPointer(sockaddrInPtr).assumingMemoryBound(to: sockaddr.self)
            bind(sock, sockaddrPtr, socklen_t(socklen))
        }
        listen(sock, 5)
        print("Server listening on port \(portNumber)")
        repeat {
            let client = accept(sock, nil, nil)
            
            //            var buff_rcvd = CChar()
            //            read(client, &buff_rcvd, 12)
            //            print(NSString(format:"Received: *%c*",buff_rcvd))
            let fileHandle = FileHandle(fileDescriptor: client)
            let data = fileHandle.availableData
            if let query = String(data: data, encoding: .utf8){
                //                print(query)
                var html = "[]"
                do{
                    let get = query.split(separator: "\r\n")[0].split(separator: " ")[1]
                    print(get)
                    let param = String(get.split(separator: "=")[1])
                    print(param)
                    
                    let result: ComparisonResult = param.compare(DUMMYKEY, options: NSString.CompareOptions.caseInsensitive, range: nil, locale: nil)
                    
                    if result == .orderedSame {
                        html = Record.sampleString()
                    }
                }catch{
                    print("Do not lay too complex query on me")
                }
                
                
                let httpResponse: String = """
                HTTP/1.1 200 OK
                server: swift-server
                Content-Type: application/json; charset=utf-8
                content-length: \(html.count)
                
                \(html)
                """
                httpResponse.withCString { bytes in
                    send(client, bytes, Int(strlen(bytes)), 0)
                    close(client)
                }
                
            }
            
            
        } while sock > -1
        print("Http server exited: \(sock)")
    }
}
