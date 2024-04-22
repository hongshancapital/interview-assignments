//
//  SequoiaListView.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import SwiftUI

struct SequoiaListView: View {
    @Environment(DataManager.self) var managerData
    
    @State private var footerRefreshing: Bool = false
    @State private var noMore: Bool = false

    var body: some View {
        contentView()
    }
    
    private func contentView() -> some View {
        if managerData.appleDatas.results.count == 0 {
            return AnyView(Text("正在请求数据..."))
        } else {
            return AnyView(Group(content: {
                getSplitView()
                RefreshFooter(refreshing: $footerRefreshing, action: {
                    self.loadMore()
                }) {
                    if self.noMore {
                        Text("No more data.")
                            .foregroundColor(.secondary)
                            .padding()
                    } else {
                        refreshingView()
                    }
                }
                .noMore(noMore)
                .preload(offset: 50)
            }))
        }
    }
    
    private func getSplitView() -> some View {
        let bgColor = UIColor(red: 244.0/256.0, green: 244.0/256.0, blue: 247.0/256.0, alpha: 1)
        return NavigationSplitView {
            List {
                ForEach(managerData.appleDatas.results) { appleData in
                    SequoialRow(appleData: appleData)
                        .listRowInsets(EdgeInsets(top: 5, leading: 15, bottom: 5, trailing: 15))
                }
                
                RefreshFooter(refreshing: $footerRefreshing, action: {
                    self.loadMore()
                }) {
                    if self.noMore {
                        Text("No more data.")
                            .foregroundColor(.secondary)
                            .padding()
                    } else {
                        loadingMoreView()
                    }
                }
                .noMore(noMore)
                .preload(offset: 50)
                .background(Color(bgColor))
                .listRowSeparator(.hidden)
            }
            .enableRefresh()
            .refreshable(action: {
                self.reload()
            })
            .navigationTitle("App")
            .listStyle(.plain)
            .background(Color(bgColor))
        } detail: {
            Text("App")
        }
    }
        
    private func reload() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            DataManager.instance.getDataFromNet(loadingMore: false)
            self.noMore = false
        }
    }
    
    private func loadMore() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            DataManager.instance.getDataFromNet(loadingMore: true)
            self.footerRefreshing = false
            self.noMore = true
        }
    }

    private func refreshingView() -> some View {
        return ActivityIndicator(style: .large)
    }
    
    private func loadingMoreView() -> some View {
        return HStack {
            ActivityIndicator(style: .large)
            Text(" Loading...")
        }
    }
}

#Preview {
    SequoiaListView()
}
