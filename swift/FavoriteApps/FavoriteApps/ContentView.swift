//
//  ContentView.swift
//  FavoriteApps
//
//  Created by 刘明星 on 2022/5/6.
//

import SwiftUI
import UIKit

struct ContentView: View {
    @ObservedObject var model = FetchModelAppList()
    
    init(){
        UITableView.appearance().sectionFooterHeight = 0
        UITableView.appearance().sectionHeaderHeight = 20
    }
    
    var body: some View {
        NavigationView {
            VStack {
                if model.appInfos.count == 0{
                    ProgressView()
                        .offset(x: 0, y: -100)
                } else {
                    List {
                        ForEach(model.appInfos) {appInfo in
                            let appInfoIndex = model.appInfos.firstIndex(where: {$0.id == appInfo.id})!
                            if appInfoIndex == model.appInfos.count - 1 {
                                // 如果是当前数组的最后一个数据对应的Section，则添加一个底部视图，当底部视图显示的时候加载数据
                                Section(
                                    footer: HStack {// 上拉加载
                                        Spacer()
                                        PullUpDownloadView(isHaveMoreData: model.isHaveMoreData)
                                        Spacer()
                                        
                                    }.onAppear(perform: {model.fetch()}),
                                    content: {
                                        ListRow(appInfo: appInfo, isFavorite: $model.appInfos[appInfoIndex].isFavorite)
                                            .frame(height: 100)
                                    })
                            } else {
                                Section {
                                    ListRow(appInfo: appInfo, isFavorite: $model.appInfos[appInfoIndex].isFavorite)
                                        .frame(height: 100)
                                }
                            }
                        }
                    }
                    .listStyle(.insetGrouped)
                    .refreshable {// 下拉刷新
                        model.fetch()
                    }
                }
            }
            .navigationTitle(Text("App"))
            .onAppear { model.fetch() }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .previewDevice("iPhone 13 Pro Max")
    }
}
