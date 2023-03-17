//
//  ContentView.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/15.
//

import SwiftUI

struct HomePageView: View {
    struct Constant {
        static let navigationTitle = "App"
        static let notRequested = "未请求"
        static let failed = "请求失败"
        static let loading = "加载列表中..."
        static let isLoadingMore = "Loading..."
        static let noMoreDate = "No more data."
    }
    
    @ObservedObject private(set) var viewModel: ViewModel
    
    init(viewModel: ViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        NavigationView {
            self.content
                .navigationBarTitle(Constant.navigationTitle)
        }
    }
    
    @ViewBuilder private var content: some View {
        switch viewModel.applications {
        case .notRequested:
            Text(Constant.notRequested)
        case .isLoading(let last):
            if let last {
                loadedView(last)
            } else {
                ProgressView(Constant.loading)
            }
        case .loaded(let applications):
            loadedView(applications)
        case .failed:
            Text(Constant.failed)
        }
    }
}

// MARK: - Displaying Content
private extension HomePageView {
    func loadedView(_ applications: [Application]) -> some View {
        ScrollView {
            ForEach(0..<applications.count, id: \.self) { index in
                let application = applications[index]
                
                /// 顶部偏移
                let topOffset: CGFloat = index == 0 ? 10 : 0
                
                LazyVStack {
                    HStack {
                        DemoImageView(viewModel: .init(imageURL: application.artworkUrl60))
                            .frame(width: 55, height: 55)
                            .padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 5))
                        
                        VStack(alignment: .leading) {
                            Text(application.trackName).bold()
                            
                            Text(application.description)
                                .lineLimit(2)
                        }
                        
                        Spacer()
                        
                        withAnimation(.easeOut(duration: 0.2)) {
                            Image(systemName: viewModel.isFavourited(for: application) ? "suit.heart.fill" : "suit.heart")
                                .scaleEffect(viewModel.isFavourited(for: application) ? 1.3 : 1)
                                .padding(EdgeInsets(top: 10, leading: 0, bottom: 10, trailing: 10))
                                .foregroundColor(viewModel.isFavourited(for: application) ? Color.red : Color.gray)
                                .onTapGesture {
                                    self.viewModel.collectTapAction(application: application)
                                }
                        }
                        
                    }
                    .background(Color.white)
                    .cornerRadius(10)
                    
                }
                .padding(EdgeInsets(top: topOffset, leading: 20, bottom: 0, trailing: 20))
            }
            
            /// 开源组件 https://github.com/wxxsw/Refresh
            Footer(refreshing: $viewModel.isMoreDataLoading, action: viewModel.loadMore) {
                if self.viewModel.isLastPage {
                    Text(Constant.noMoreDate)
                        .foregroundColor(.secondary)
                } else {
                    HStack {
                        Spacer()
                        
                        ProgressView()
                        
                        Text(Constant.isLoadingMore)
                            .foregroundColor(Color.gray)
                            .font(Font.system(size: 20))
                            .padding(EdgeInsets(top: 0, leading: 5, bottom: 0, trailing: 0))
                        
                        Spacer()
                    }
                }
            }
            .noMore(viewModel.isLastPage)
            .preload(offset: 50)
        }
        .background(Color(red: 243 / 255, green: 242 / 255, blue: 247 / 255))
        .refreshable {
            await viewModel.refreshList()
        }
        .modifier(FooterModifier(enable: true))
    }
}


#if DEBUG
struct HomePageView_Previews: PreviewProvider {
    static var previews: some View {
        HomePageView(viewModel: .init())
    }
}
#endif
