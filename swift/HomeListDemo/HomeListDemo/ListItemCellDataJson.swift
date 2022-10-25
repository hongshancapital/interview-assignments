//
//  ListItemCellDataJson.swift
//  HomeListDemo
//
//  Created by yaojinhai on 2022/9/22.
//
import Combine
import Foundation

struct ListItemCellDataJson: Codable,Identifiable {
    let artworkUrl60: String
    let trackCensoredName: String
    let description: String
    let trackId: Int
    
    var imageURL: URL? {
        URL(string: artworkUrl60)
    }
    
    var id: Int {
        trackId
    }
}


class LoadDataModel: ObservableObject {
    
    private var allList = [ListItemCellDataJson]()
    @Published var dataSourceList = [ListItemCellDataJson]()
    @Published var error: NSError? = nil
    @Published var isHaveMore = true
    

    //TODO: - 模拟分页下载
    func loadDataAction(pageIndex: Int,finishsed: (()->())?)  {
        
        
        if allList.first != nil  {
            
            func loadData(){
                
                defer {
                    finishsed?()
                }
                
                if pageIndex == 1 {
                    self.dataSourceList.removeAll()
                    self.isHaveMore = true
                }
                
                let beginIndex = (pageIndex - 1) * 15
                if beginIndex >= allList.count {
                    isHaveMore = false
                    return
                }
                var endIndex = pageIndex * 15;
                if endIndex >= allList.count {
                    endIndex = allList.count - 1
                }
                let subList = allList[beginIndex..<endIndex]
                dataSourceList.append(contentsOf: subList)

            }
            
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) { 
                loadData()
            }
            return
            
        }
        

        //    https://itunes.apple.com/search?entity=software&limit=50&term=chat
        var urlComp = URLComponents(string: "https://itunes.apple.com/search?")
        var params = [URLQueryItem(name: "entity", value: "software")]
        params.append(.init(name: "limit", value: "50"))
        params.append(.init(name: "term", value: "chat"))
        urlComp?.queryItems = params
        guard let url = urlComp?.url else { 
#if DEBUG
            fatalError("url错误：\(String(describing: urlComp))")
#endif
            DispatchQueue.main.async {
                self.error = NSError(domain: "无效的URL:\(String(describing: urlComp))", code: -1)
                finishsed?()
            }
            return  
            
        } 
        
        URLSession.shared.dataTask(with: url) { result, respose, error in
            
            
            if let error = error as? NSError{
                DispatchQueue.main.async {
                    self.isHaveMore = false
                    self.error = error
                    finishsed?()
                }
                return
            }
            guard let dict = JSONSerialization.jsonDictionary(data: result)else{
                DispatchQueue.main.async {
                    self.isHaveMore = false
                    finishsed?()
                }
                return
            }
            
            if let list = [ListItemCellDataJson].convertModel(any: dict["results"]) {
                self.allList = list
                self.loadDataAction(pageIndex: pageIndex, finishsed: finishsed)
                
            }
            
        }.resume()
    }
}

