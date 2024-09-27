//
//  LoadDataModel.swift
//  RefreshTableDemo
//
//  Created by yaojinhai on 2022/12/14.
//

import Foundation

class CachesData {
    private var cachesList = [Int: Any]()
    static private let shareInstance = CachesData()
    
    private init(){}

    
    static func addItem(data: ItemCellDataJson) {
        shareInstance.cachesList[data.id] = data
    }
    static func removeItem(data: ItemCellDataJson) {
        shareInstance.cachesList.removeValue(forKey: data.id)
    }
    static func contains(data: ItemCellDataJson) -> Bool {
        shareInstance.cachesList[data.id] != nil
    }
}


class LoadDataModel: ObservableObject {
    
    private var allList = [ItemCellDataJson]()
    
    //TODO: - 模拟分页下载
    func loadDataAction(pageIndex: Int,finishsed: (([ItemCellDataJson]?, NSError?)->Void)?){
        
        
        if allList.first != nil  {
            
            func loadData(){
                
                var subList: [ItemCellDataJson]?
                
                defer {
                    finishsed?(subList,nil)
                }
                
                let beginIndex = (pageIndex - 1) * 15
                if beginIndex >= allList.count {
                    subList = []
                    return
                }
                var endIndex = pageIndex * 15
                if endIndex >= allList.count {
                    endIndex = allList.count - 1
                }
                let list = allList[beginIndex..<endIndex]
                subList = []
                subList?.append(contentsOf: list)
                for idx in 0..<subList!.count {
                    subList![idx].isCollection = CachesData.contains(data: subList![idx])
                }
            
            }
            
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) { 
                loadData()
            }
            return
            
        }
        
        
        let model: DataJsonModel = loadData("data.txt")
        if let list = model.results {
            self.allList = list
            self.loadDataAction(pageIndex: pageIndex, finishsed: finishsed)
        }else{
            DispatchQueue.main.async {
                finishsed?(nil,NSError(domain: "获取数据失败", code: 0))
            }

        }
        
        
        
    }
}

func loadData<T: Decodable>(_ filename: String) -> T {
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
