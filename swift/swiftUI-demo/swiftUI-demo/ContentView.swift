//
//  ContentView.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/18.
//

import SwiftUI
import Kingfisher

let pageSize = 15

struct ContentView: View {
    
    @State var datas: [AppModel] = []
    @State var hasMore = true
    @State var pageIndex = 0
    @State var loading = false
    public var loadMoreBlock: (() -> Void)? = nil
    
    var body: some View {
        NavigationView {
            Group {
                if (self.datas.count == 0) {
                    ProgressView()
                        .offset(x: 0, y: -100)
                } else {
                    TableView(datas: self.$datas, hasMore: self.$hasMore) {
                        self.loadData()
                    } onTapFavorite: { (id, isFavorite) in
                        self.onTapFavorite(id, isFavorite)
                    }

                }
            }
                .navigationTitle("App")
                .navigationViewStyle(.automatic)
        }.onAppear {
            self.loadData()
        }
    }
    
    
    // 点击收藏按钮后，cell内部自行更新状态和UI，并同步到外层，外层只需更新数据即可
    func onTapFavorite(_ id: String, _ isFavorite: Bool) {
        let index = self.datas.firstIndex { item in
            return item.id == id
        }
        guard let index = index else {
            return
        }
        self.datas[index].isFavorite = isFavorite
    }

    func loadData() {
        
        // 过滤请求，请求中状态不再请求
        if (self.loading) {
            return
        }
        self.loading = true
        Network.getAppModelList(self.pageIndex, pageSize) { data, error in
            self.loading = false
            if (error != nil) {
                print("加载失败：", error!)
                return;
            }
            guard let models = data, models.count > 0 else {
                print("加载失败，result 为空")
                self.hasMore = false
                return;
            }
            self.pageIndex = self.pageIndex + 1
            self.datas = (self.datas + models).filterDuplicates { $0.id }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
