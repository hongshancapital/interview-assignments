//
//  HomeViewModel.swift
//  InterviewDemo
//
//  Created by 陈凝 on 2022/8/1.
//

import Foundation

class HomeViewModel:ObservableObject{
    
    @Published var homeDataList:[Result]?
    
    @Published var pageSize:Int = 10
    
    // MARK: Main Fetch Data Func
    @MainActor
    func fetchHomeDataList(isRefresh:Bool=false) async {
        if isRefresh{
            self.pageSize = 10
        }
        
        let jsonData = Task { () -> DataModel in
            let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=\(pageSize)&term=chat")!
            let (data, _) = try await URLSession.shared.data(from: url)
            return try JSONDecoder().decode(DataModel.self, from: data)
        }
        do {
            guard let newData = try await jsonData.value.results else {fatalError("")}
            self.homeDataList == nil ? self.homeDataList = newData : isRefresh ? self.homeDataList = newData : self.homeDataList!.append(contentsOf: newData[homeDataList!.count..<pageSize])
            self.pageSize+=10
        }
        catch{
            print("load data fail")
        }
    }
    
    // MARK: is now can load more data form server
    func canLoadMore() -> Bool{
        return !(self.pageSize >= 50)
    }
    
    // MARK: load more func
    func loadMore() async {
        if self.canLoadMore(){
            await self.fetchHomeDataList()
        }
    }
    
    // MARK: refresh all listData func ,reset the pageSize also,to load data form begining
    func refresh() async {
        await self.fetchHomeDataList(isRefresh: true)
    }
}

