//
//  FLUserListView.swift
//  demo
//
//  Created by 张帅 on 2023/4/9.
//

import SwiftUI
import Refresh

struct FLUserListView: View {
    enum Constant {
        static let maxDataCount: CGFloat = 50
        static let pageDataCount: Int = 10
        static let backgroundColor: Color = Color.gray.opacity(0.1)
    }
    
    @EnvironmentObject var viewModel: FLUserViewModel
    @State private var isLoading: Bool = false
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    @State private var noMore: Bool = false
    @State private var pageIndex: Int = 0
    
    var body: some View {
        ScrollView {
            if viewModel.users.count > 0 {
                RefreshHeader(refreshing: $headerRefreshing, action: {
                    refresh()
                }) { progress in
                    if headerRefreshing {
                        SimpleRefreshingView()
                    } else {
                        SimplePullToRefreshView(progress: progress)
                    }
                }
            }
            
            ForEach(viewModel.users, id: \.artistId) { user in
                FLUserRow(user: user).listRowSeparator(.hidden)
                    .listRowBackground(Color.green)
            }
            
            if viewModel.users.count > 0 {
                RefreshFooter(refreshing: $footerRefreshing, action: {
                    loadMore()
                }) {
                    if noMore {
                        Text("No more data !")
                            .foregroundColor(.secondary)
                            .padding()
                    } else {
                        SimpleLoadingMoreView()
                            .padding()
                    }
                }
                .noMore(noMore)
                .preload(offset: Constant.maxDataCount)
            }

        }.enableRefresh()
            .overlay(Group {
                if viewModel.users.count == 0 {
                    ActivityIndicator(style: .medium)
                } else {
                    EmptyView()
                }
            })
            .background(Constant.backgroundColor)
            .onAppear { refresh() }
            .navigationBarTitle("app", displayMode: .inline)
          
    }
    
    func refresh() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            headerRefreshing = false
            noMore = false
            pageIndex = 0
            viewModel.refresh()
        }
    }
    
    func loadMore() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            viewModel.loadMore(pageIndex:pageIndex)
            pageIndex += 1
            footerRefreshing = false
            noMore = viewModel.users.count > Int(Constant.maxDataCount)
        }
    }
}

struct SimpleLoadingMoreView: View {
    var body: some View {
        HStack {
            ActivityIndicator(style: .medium)
            Text("loading...")
        }
    }
}


struct SimpleRefreshingView: View {
    var body: some View {
        ActivityIndicator(style: .medium)
     
    }
}

struct SimpleRefreshingView_Previews: PreviewProvider {
    static var previews: some View {
        SimpleRefreshingView()
    }
}

struct ActivityIndicator: UIViewRepresentable {
    let style: UIActivityIndicatorView.Style
    
    func makeUIView(context: Context) -> UIActivityIndicatorView  {
        return UIActivityIndicatorView(style: style)
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
        uiView.startAnimating()
    }
}

struct SimplePullToRefreshView: View {
    let progress: CGFloat
    
    var body: some View {
        Text("Pull to refresh")
    }
}

struct SimplePullToRefreshView_Previews: PreviewProvider {
    static var previews: some View {
        SimplePullToRefreshView(progress: 0)
    }
}


//struct FLUserListView_Previews: PreviewProvider {
//    static var previews: some View {
//        FLUserListView()
//    }
//}
