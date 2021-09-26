//
//  SearchAppApp.swift
//  Shared
//
//  Created by Changgeng Wang on 2021/9/26.
//

import SwiftUI
import Swifter
import CoreData

@main
struct SearchAppApp: App {
    let persistenceController = PersistenceController.shared
    var server:HttpServer = HttpServer()
    
    init() {
        setupData()
        startServer()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
    
    /// 启动内部服务器，加载查询接口，请求地址http://localhost:8080/query?keyword=Dyson&pagenum=0&pagesize=10
    /// 接口参数说明 keyword 查询关键字，pagenum 查询分页编号，pagesize每次查询的长度
    /// 内部的Http服务使用了本地的CoreData作为http服务的持久化层，第一次启动时调用setupData初始化产品数据
    func startServer() {
        server["/query"] = { query in
            var keyword = ""
            var pagenum = 0
            var pagesize = 0
            for item in query.queryParams
            {
                if item.0.lowercased() == "keyword" {
                    keyword = item.1
                }
                else if item.0.lowercased() == "pagenum" {
                    pagenum = Int(item.1)!
                }
                else if item.0.lowercased() == "pagesize" {
                    pagesize = Int(item.1)!
                }
            }
            let offset = pagesize * pagenum
            let itemList = self.query(keyword: keyword, fetchLimit: pagesize, fetchOffset: offset)
            let data = try? JSONEncoder.init().encode(itemList)
            if let jsonData = data {
                let jsonString = String(data: jsonData, encoding: .utf8)
                if let text = jsonString {
                    return HttpResponse.ok(.text(text))
                }else {
                    return HttpResponse.ok(.text("[]"))
                }
            }
            else {
                return HttpResponse.ok(.text("[]"))
            }
        }
        try? server.start(8080, forceIPv4: true)
    }
    
    /// CoreData的数据访问方法
    /// - Parameters:
    ///   - keyword: 查询关键字
    ///   - fetchLimit: 查询长度
    ///   - fetchOffset: 查询位移，此处的位移经过换算得到
    /// - Returns: 返回CodeData对象
    /// Item的数据库表结构
    /// brand 品牌
    /// category 产品大类
    /// product 产品型号
    /// price 价格
    /// stockstatus 库存状态
    /// timestamp 时间戳
    func query(keyword:String, fetchLimit:Int, fetchOffset:Int) -> [Item] {
        let viewContext = persistenceController.container.viewContext
        let itemFetch = NSFetchRequest<Item>(entityName: "Item")
        itemFetch.predicate = NSPredicate(format: "brand == %@", keyword)
        itemFetch.sortDescriptors =  [NSSortDescriptor(keyPath: \Item.category, ascending: true),NSSortDescriptor(keyPath: \Item.timestamp, ascending: true)]
        itemFetch.fetchLimit = fetchLimit
        itemFetch.fetchOffset = fetchOffset
        let fetchObjects  = try? viewContext.fetch(itemFetch)
        if let result = fetchObjects {
            return result
        }else {
            return []
        }
    }
    
    /// 初始化数据，随机生成
    func setupData ()
    {
        if !UserDefaults.standard.bool(forKey: "DataInit") {
            let viewContext = persistenceController.container.viewContext
            for i in 0...50 {
                let newItem = Item(context: viewContext)
                newItem.timestamp = Date()
                newItem.brand = "Dyson"
                newItem.category = ["Vacuum","HairDryer","TV","Notebook","Phone","Desktop"][Int.random(in: 0...5)]
                newItem.product = ["V","G","Supersonic","H","S","T","D","O","Z"][Int.random(in: 0...8)] + "\(i)"
                newItem.stockstatus = Int16.random(in: 0...1)
                newItem.price = Double.random(in: 10...10000)
                do {
                    try viewContext.save()
                } catch {
                    let nsError = error as NSError
                    fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
                }
            }
            UserDefaults.standard.set(true, forKey: "DataInit")
        }
    }
}

