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
        static let noMoreData = "No more data."
    }
    
    @ObservedObject private(set) var viewModel: ViewModel
    @State private var progressViewId: Int = 0

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
        List {
            ForEach(0..<applications.count, id: \.self) { index in
                let application = applications[index]
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
                .listRowSeparator(.hidden)
                .listRowInsets(EdgeInsets(top: 10, leading: 0, bottom: 0, trailing: 0))
                .listRowBackground(Color.clear)
                
                if index == applications.count - 1 {
                    let footerText = !viewModel.isLastPage ? Constant.isLoadingMore : Constant.noMoreData
                    HStack {
                        Spacer()
                        
                        if !viewModel.isLastPage {
                            /// 不加id iOS 16只显示一次
                            ProgressView()
                                .id("\(progressViewId)")
                                .onAppear {
                                    progressViewId = progressViewId + 1
                                    viewModel.loadMore()
                                }
                        }
                        
                        Text(footerText)
                            .padding(EdgeInsets(top: 0, leading: 5, bottom: 0, trailing: 0))
                            .foregroundColor(Color.gray)
                            .font(Font.system(size: 20))
                        
                        Spacer()
                    }
                    .listRowBackground(Color.clear)
                }
            }
        }
        .refreshable {
            await viewModel.refreshList()
        }
    }
}


#if DEBUG
struct HomePageView_Previews: PreviewProvider {
    static var previews: some View {
        HomePageView(viewModel: .init())
    }
}
#endif
