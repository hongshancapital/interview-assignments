//
//  ListContentModel.swift
//  ListDemo
//
//  Created by mac on 2022/3/18.
//

import SwiftUI

class ListContentModel: ObservableObject {
    //总数据
    let totalData: [ListModel] = load("listData.json")
    //list数据
    @Published var listData: [ListModel] = []
    //当前页数
    var page: Int = 0
    //每页条数
    let pageSize = 10
    //是否还有更多
    var hasMore: Bool = true
    
    //下拉刷新
    func refreshData() {
        page = 0
        hasMore = true
        listData.removeAll()
        let firstCount = (totalData.count > pageSize) ? pageSize : totalData.count
        listData.append(contentsOf: totalData.prefix(firstCount))
    }

    //上拉加载
    func loadMore() {
        if hasMore == false {//是否有更多
            return
        }
        if totalData.count <= listData.count {//总数据、当前数据
            return
        }
        let nextCount = (totalData.count - (page + 1) * pageSize > pageSize) ? (page + 1) * pageSize : totalData.count
        listData.append(contentsOf: totalData[page * pageSize..<nextCount])
        if totalData.count > listData.count {
            page += 1
            hasMore = true
        } else {
            hasMore = false
        }
    }
}

//处理json数据
func load<T: Decodable>(_ filename: String) -> T {
    let data: Data
    
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
        else {
            fatalError("Couldn't find \(filename) in main bundle.")
    }
    
    do {
        data = try Data(contentsOf: file)
    } catch {
        fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
    }
    
    do {
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
    }
}
