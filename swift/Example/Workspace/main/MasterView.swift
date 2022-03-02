//
//  ContentView.swift
//  swiftui
//
//  Created by 聂高涛 on 2022/3/1.
//

import SwiftUI

//页面
struct MasterView: View {
    //数据
    @State private var dataArray = [CellData]()
    @State private var count: Int = 0 //用count记录下条数，操作100条不再请求更多数据
    //状态
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    @State private var reload = ReloadContexts()
    //网络请求
    let service = APIService()

    init(){
        UITableView.appearance().separatorStyle = .none
        UITableViewCell.appearance().accessoryType = .none
    }
    
    var body: some View {
        NavigationView {
            pullToRefreshScrollBody
                .navigationTitle("App")
                .navigationBarTitleDisplayMode(.large)
                .onAppear(perform: loadData)
        }
    }
    
    var pullToRefreshScrollBody: some View {
        headerFooterRefresh
            .background(Color.init(red: 0.9686, green: 0.9686, blue: 0.9686))
    }
    
    // 下拉刷新、上拉加载
    var headerFooterRefresh: some View {
        ScrollView {
            PullToRefreshView(header: RefreshDefaultHeader(), footer: RefreshDefaultFooter(), content: {
                ListView(dataArray: dataArray)
                if reload.noMore {
                    Text("没有更多数据了")
                        .frame(height: 60)
                        .foregroundColor(Color.init(red: 0.6, green: 0.6, blue: 0.6))
                        .font(.system(size: 14))
                }
            }).environmentObject(reload)
        }
        .addPullToRefresh(isHeaderRefreshing: $headerRefreshing,
                          onHeaderRefresh: reloadData,
                          isFooterRefreshing: $footerRefreshing,
                          onFooterRefresh: loadMoreData,
                          isNOMore: isNOMore)
    }
    
    //是否没有更多数据
    private func isNOMore () -> Bool {
        return self.reload.noMore
    }
    
    //数据请求
    private func loadData() {
        Logger.print("loadData")
        self.dataArray.removeAll()
        self.count = 0
        self.reload.setNoMore(false)
        
        service.get(APIs.list) { wrapped in
            guard wrapped.code == 200 else {
                //请求异常
                return;
            }
            
            if wrapped.code == 200, let dicValue = wrapped.data as? [String:Any]{
                if let resultCount = dicValue["resultCount"] as? Int,
                   let results = dicValue["results"] as? [[String:Any]],
                   resultCount > 0 && results.count > 0 {
                    for dicValue in results {
                        self.count = self.count + 1;
                        
                        let cell = CellData(url: dicValue["artworkUrl60"] as? String ?? "", title: dicValue["trackName"] as? String ?? "", subtitle: dicValue["description"] as? String ?? "", isSelected: false)
                        self.dataArray.append(cell)
                        if self.count >= 100 {
                            reload.setNoMore(true)// 如果已经没有数据，则终止添加
                            break;
                        }
                    }
                }
            }
        }
    }
    
    //下拉刷新
    private func reloadData() {
        Logger.print("reloadData ...\(headerRefreshing)")
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            loadData()
            headerRefreshing = false
            Logger.print("end refresh data ...\(headerRefreshing)")
        }
    }
    
    //上拉加载更多
    private func loadMoreData() {
        if self.reload.noMore  {
            reload.setNoMore(true)
            footerRefreshing = false
            return
        }
        
        Logger.print("loadMoreData ... \(footerRefreshing)")
        
        service.get(APIs.list) { wrapped in
            guard wrapped.code == 200 else {
                //请求异常
                return;
            }
            
            if wrapped.code == 200, let dicValue = wrapped.data as? [String:Any]{
                if let resultCount = dicValue["resultCount"] as? Int,
                   let results = dicValue["results"] as? [[String:Any]],
                   resultCount > 0 && results.count > 0 {
                    for dicValue in results {
                        self.count = self.count + 1;
                        let cell = CellData(url: dicValue["artworkUrl60"] as? String ?? "", title: dicValue["trackName"] as? String ?? "", subtitle: dicValue["description"] as? String ?? "", isSelected: false)
                        self.dataArray.append(cell)
                        if self.count >= 100 {
                            reload.setNoMore(true)// 如果已经没有数据，则终止添加
                        }
                    }
                    
                    footerRefreshing = false
                    Logger.print("end load more data ... \(footerRefreshing)")
                }
            }
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MasterView()
    }
}
