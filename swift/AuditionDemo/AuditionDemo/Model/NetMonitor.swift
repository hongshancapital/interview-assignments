//
//  NetMonitor.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import Foundation
import Network

//MARK: -网络监听管理器
class NetMonitor : ObservableObject{
    //MARK: - 实现单例
    static let sharedInstance = NetMonitor()
    
    //MARK: - 初始化方法，开始监听当前网络情况
    private init(){
        monitor.pathUpdateHandler = { path in
            if path.status == .satisfied {
                print("connected!")
                DispatchQueue.main.async {
                    self.netIsOpen = true
                }
            } else {
                print("No connection.")
                DispatchQueue.main.async {
                    self.netIsOpen = false
                }
            }
            print(path.isExpensive)
        }
        monitor.start(queue: queue)
    }
    
    //MARK: - 属性
    @Published
    var netIsOpen = false
    let monitor = NWPathMonitor()
    let queue = DispatchQueue(label: "NetMonitor")          //在独自工作线程监听
}
