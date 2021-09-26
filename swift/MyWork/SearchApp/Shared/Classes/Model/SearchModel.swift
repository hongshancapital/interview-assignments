//
//  SearchModel.swift
//  SearchApp
//
//  Created by Changgeng Wang on 2021/9/26.
//

import Foundation
import Combine

/// 查询Model
class SearchModel: ObservableObject {
    var keyword:String = "Dyson"
    {
        didSet{
            self.refreshQuery()
        }
    }
    var pageNum:Int = 0
    var pageSize:Int = 10
    var sub:AnyCancellable?
    @Published
    var itemList:[SectionItem] = []
    @Published
    var hasMore:Bool = true

    init()
    {
        self.refreshQuery()
    }
    
    /// 输入关键字时触发，重置分页状态
    func refreshQuery()
    {
        self.pageNum = 0        
        let template = "http://localhost:8080/query?keyword=%@&pagenum=%d&pagesize=%d"
        let newKeyword = keyword.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
        let url = String(format: template, newKeyword!,self.pageNum, self.pageSize)
        let request = URLRequest(url: URL(string: url)!)
        self.sub = URLSession.shared.dataTaskPublisher(for:request)
            .map { [unowned self] in self.itemsWithData(data: $0.data)}
            .replaceError(with: [])
            .receive(on: DispatchQueue.main)
            .assign(to:\.itemList , on: self)
    }
    
    
    /// 点击加载更多时触发
    func appendQuery()
    {
        self.pageNum = self.pageNum + 1
        let template = "http://localhost:8080/query?keyword=%@&pagenum=%d&pagesize=%d"
        let newKeyword = keyword.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
        let url = String(format: template, newKeyword!,self.pageNum, self.pageSize)
        let request = URLRequest(url: URL(string: url)!)
        self.sub = URLSession.shared.dataTaskPublisher(for:request)
            .map { [unowned self] in self.itemsWithData(data: $0.data)}
            .replaceError(with: [])
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] list in
                if let result = self?.append(from: list, to: self!.itemList) {
                    self?.itemList = result
                }
            })
        
    }
    
    /// 加载更多时数据合并
    /// - Parameters:
    ///   - from: 源数据
    ///   - to: 目标数据
    /// - Returns: 返回结果
    func append(from:[SectionItem], to:[SectionItem]) -> [SectionItem]
    {
        var result = to
        if let last = to.last, let first = from.first {
            if last.name == first.name {
                var newItem = last
                newItem.rows.append(contentsOf: first.rows)
                result[result.count - 1] = newItem
                result.append(contentsOf: from[1..<from.count])
            } else {
                result.append(contentsOf: from)
            }
        } else {
            result.append(contentsOf: from)
        }
        return result
    }
    
    /// 把json数据转换为视图的模型数据
    /// - Parameter data: 返回的json数据
    /// - Returns: 查询列表模型数据
    func itemsWithData(data:Data) -> [SectionItem] {
        var result:[SectionItem] = []
        let jsonObject = try? JSONSerialization.jsonObject(with: data, options: .allowFragments)
        if let jsonArray:NSArray = (jsonObject as! NSArray?)
        {
            DispatchQueue.main.async {
                if jsonArray.count < self.pageSize {
                    self.hasMore = false
                }else {
                    self.hasMore = true
                }
            }
            let rows = jsonArray.map { obj -> RowItem in
                let dictItem = obj as! NSDictionary
                let category = dictItem["category"] as! String
                let product = dictItem["product"] as! String
                let price = dictItem["price"] as! Double
                let stockstatus = dictItem["stockstatus"] as! Int
                let detailName = stockstatus == 0 ? "In-stock" : "Out-of-stock"
                let descString = String(format: "$%.2f", price)
                let rowStyle = stockstatus == 0 ? RowStyle.InStockRow : RowStyle.OutOfStockRow
                let rowItem = RowItem(name: product, detailName: detailName, descString: descString, sectionName: category, style: rowStyle)
                return rowItem
            }
            result = Dictionary(grouping: rows) { row in
                return row.sectionName
            }.map { (key: String, value: [RowItem]) in
                return SectionItem(name: key, rows: value)
            }
        }
        return result
    }
}

extension Array where Element: Hashable {
    func uniqued() -> Array {
        var buffer = Array()
        var added = Set<Element>()
        for elem in self {
            if !added.contains(elem) {
                buffer.append(elem)
                added.insert(elem)
            }
        }
        return buffer
    }
}
