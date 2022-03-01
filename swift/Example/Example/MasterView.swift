//
//  ContentView.swift
//  swiftui
//
//  Created by 聂高涛 on 2022/3/1.
//

import SwiftUI

struct CellData: Identifiable, Hashable {
    var id = UUID()
    var icon = ""
    var title = ""
    var subtitle = ""
    var isSelected = false
}


struct MasterView: View {
    @State private var dataArray = [CellData]()
    @State private var count: Int = 0
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    @State private var reload = ReloadContexts()
    let service = APINetworking()

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
        headerFooterRefresh.background(Color.init(Color.RGBColorSpace.sRGB, red: 0.9686, green: 0.9686, blue: 0.9686, opacity: 1.0))
    }
    
    // 下拉刷新、上拉加载
    var headerFooterRefresh: some View {
        ScrollView {
            PullToRefreshView(header: RefreshDefaultHeader(), footer: RefreshDefaultFooter(), content: {
                ListView(dataArray: dataArray)
                if reload.noMore {
                    Text("没有更多数据了").frame(height: 60).foregroundColor(Color.init(.sRGB, red: 0.6, green: 0.6, blue: 0.6, opacity: 1.0)).font(.system(size: 14))
                }
            }).environmentObject(reload)
        }
        .addPullToRefresh(isHeaderRefreshing: $headerRefreshing, onHeaderRefresh: reloadData,
                          isFooterRefreshing: $footerRefreshing, onFooterRefresh: loadMoreData, isNOMore: isNOMore)
    }
    
    private func isNOMore () -> Bool {
        return self.reload.noMore
    }
    
    private func loadData() {
        print("loadData")
        self.dataArray.removeAll()
        self.count = 0
        self.reload.setNoMore(false)
        
        service.run(URLRequest(url: URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")!)) { data in
            if let array = data.results as? [[String:Any]], array.count > 0 {
                for dicValue in array {
                    self.count = self.count + 1;
                    self.dataArray.append(CellData(icon: dicValue["artworkUrl60"] as? String ?? "", title: dicValue["trackName"] as? String ?? "", subtitle: dicValue["description"] as? String ?? "", isSelected: false))
                    if self.count >= 100 {
                        reload.setNoMore(true)// 如果已经没有数据，则终止添加
                    }
                }
            }
        }
    }
    
    private func reloadData() {
        print("reloadData ...\(headerRefreshing)")
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            loadData()
            headerRefreshing = false
            print("end refresh data ...\(headerRefreshing)")
        }
    }
    
    private func loadMoreData() {
        if self.reload.noMore  {
            reload.setNoMore(true)
            footerRefreshing = false
            return
        }
        print("loadMoreData ... \(footerRefreshing)")
        service.run(URLRequest(url: URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")!)) { data in
            if let array = data.results as? [[String:Any]], array.count > 0 {
                for dicValue in array {
                    self.count = self.count + 1;
                    self.dataArray.append(CellData(icon: dicValue["artworkUrl60"] as? String ?? "", title: dicValue["trackName"] as? String ?? "", subtitle: dicValue["description"] as? String ?? "", isSelected: false))
                    if self.count >= 100 {
                        reload.setNoMore(true)// 如果已经没有数据，则终止添加
                        break;
                    }
                }
                footerRefreshing = false
                print("end load more data ... \(footerRefreshing)")
            }
        }
    }
}


struct ListView : View {
    let dataArray : [CellData]
    
    var body: some View {
        VStack {
            ForEach(dataArray) { item in
                WrappedView(data:item).background(Color.white).cornerRadius(8).clipped().padding(EdgeInsets(top: 5, leading: 16, bottom: 5, trailing: 16))
            }
        }
    }
}


struct WrappedView : View {
    @State var data : CellData
    @State var networkImage : UIImage? = nil
    let placeholder = UIImage(named: "image")
    var body: some View {
        return HStack.init(alignment: .center, spacing: 10) {
            Image(uiImage: self.networkImage ?? self.placeholder!).resizable().frame(width: 40, height: 40).cornerRadius(4).onAppear(perform: fetchRemoteImage)
            VStack.init(alignment: .leading, spacing: 4) {
                Text(data.title).font(.system(size: 16)).lineLimit(1)
                Text(data.subtitle).font(.system(size: 13)).lineLimit(2)
            }
            Spacer()
            Button(action:{
                self.data.isSelected = !self.data.isSelected;
            }) {
                Image(data.isSelected ? "loveselected":"loveunselected").resizable().frame(width: 28, height: 28).cornerRadius(4)
            }
        }.padding(EdgeInsets(top: 12, leading: 12, bottom: 12, trailing: 12))
    }
    
    
    
    func fetchRemoteImage(){
        //用来下载互联网上的图片
        guard let url = URL(string: data.icon) else { return } //初始化一个字符串常量，作为网络图片的地址
        URLSession.shared.dataTask(with: url){ (data, response, error) in //执行URLSession单例对象的数据任务方法，以下载指定的图片
            if let image = UIImage(data: data!){
                self.networkImage = image //当图片下载成功之后，将下载后的数据转换为图像，并存储在remoteImage属性中
            }
            else{
                print(error ?? "") //如果图片下载失败之后，则在控制台输出错误信息
            }
        }.resume() //通过执行resume方法，开始下载指定路径的网络图片
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MasterView()
    }
}
