//
//  ContentView.swift
//  Demo
//
//  Created by 方林威 on 2023/1/29.
//

import SwiftUI
import Refresh

/*
 由于平时在工作中的业务以UIKit开发为主
 SwiftUI, Concurrency技术并没有在真实业务中应用. 所以目前处于简单掌握, 并不熟练
 */

struct ContentView: View {
    
    @StateObject private var viewModel = AppsViewModel()
    
    @State private var footerRefreshing: Bool = false
    
    @State private var page = 0
    
    var body: some View {
        NavigationView {
            ZStack {
                switch viewModel.loadingState {
                case .loading:
                    ProgressView()
                    
                case .success:
                    ScrollView {
                        LazyVStack(alignment: .center, spacing: 10) {
                            ForEach($viewModel.items, id: \.id) { $item in
                                ItemView(item: $item)
                                    .padding(.horizontal, 16)
                            }
                        }
                        
                        if viewModel.items.count > 0 {
                            RefreshFooter(refreshing: $footerRefreshing, action: {
                                Task {
                                    try await self.loadMore()
                                }
                            }) {
                                if self.viewModel.noMore {
                                    Text("No more data !")
                                        .foregroundColor(.secondary)
                                        .padding()
                                } else {
                                    SimpleRefreshingView()
                                        .padding()
                                }
                            }
                            .noMore(viewModel.noMore)
                        }
                    }
                    .enableRefresh()
                    .background(Color("F4F4F7"))
                    
                case .failure:
                    VStack(spacing: 15) {
                        Text("数据加载失败, 点击按钮重试~")
                        Button("重试") {
                            viewModel.load()
                        }
                    }
                }
            }
            .navigationTitle("App")
            .navigationBarTitleDisplayMode(.large)
        }
        .task {
            viewModel.load()
        }
    }
    
    private func loadMore() async throws {
        page += 1
        try await viewModel.loadMoreData(page: page)
        footerRefreshing = false
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

/// App cell 视图
private struct ItemView: View {
    
    @Binding var item: Item
    
    var body: some View {
        
        HStack(spacing: 10) {
            AsyncImage(url: URL(string: item.icon)) { image in
                image.resizable()
                    .clipShape(RoundedRectangle(cornerRadius: 8, style: .continuous))
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50)
            .overlay {
                RoundedRectangle(cornerRadius: 8, style: .continuous)
                    .stroke(Color("E8E8E8"), lineWidth: 0.5)
            }
            
            VStack(alignment: .leading, spacing: 10) {
                Text(item.name)
                    .font(.system(size: 15))
                    .fontWeight(.medium)
                
                Text(item.desc)
                    .font(.caption)
                    .lineLimit(2)
            }
            
            Spacer()
            
            VStack {
                if item.isLike {
                    Image(systemName: "heart.fill")
                        .foregroundColor(.red)
                } else {
                    Image(systemName: "heart")
                        .foregroundColor(Color("949494"))
                }
            }
            .frame(width: 35, height: 35)
            .onTapGesture {
                item.isLike.toggle()
            }
        }
        .padding()
        .background(Color.white)
        .cornerRadius(10)
    }
}
