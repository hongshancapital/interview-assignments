//
//  AppTableView.swift
//  SwiftDemo
//
//  Created by liuyang on 2023/3/5.
//

import SwiftUI

let AppCellHeight = 88.0
let AppCellPadding = 10.0

// 列表
struct AppTableView : View {
    @StateObject var appDataManager = AppDataManager()
    
    @State var isRefreshing = false
    @State var isMoreLoading = false
    
    //是否还有上滑数据加载
    @State var canLoadMore = true

    var body: some View {
        VStack(content: {
            RefreshScrollView(
                offDown: CGFloat(self.appDataManager.appDatas.count) * (AppCellHeight + AppCellPadding),
                listH: ScreenHeight - kNavHeight - kBottomSafeHeight,
                refreshing: $isRefreshing,
                isMoreLoading: $isMoreLoading,
                canLoadMore: self.canLoadMore) {
                    handleRefresh()
            } moreTrigger: {
                handleLoadMore()
            } content: {
                if self.appDataManager.appDatas.count > 0 {
                    VStack(spacing: AppCellPadding) {
                        // 列表
                        ForEach(self.appDataManager.appDatas, id: \.self.id) { appItemModel in
                            AppCell(appItemModel: appItemModel)
                        }
                        if !self.canLoadMore {
                            Text("没有更多数据")
                                .foregroundColor(.secondary)
                                .font(.subheadline)
                        }
                    }
                }
                else {
                    // loading
                    ActivityRep()
                        .padding(EdgeInsets(top: ScreenHeight / 3.0, leading: 0, bottom: 0, trailing: 0))
                }
            }
        })
        .onAppear(perform: {
            //首次加载
            handleRefresh()
        })
        .background(Color(uiColor: .secondarySystemBackground))
    }
    
    // 处理下拉刷新
    func handleRefresh() {
        self.appDataManager.requestAppData {
            self.isRefreshing = false
            self.isMoreLoading = false
            self.canLoadMore = true
        }
    }
    
    // 处理上拉加载更多
    func handleLoadMore () {
        self.appDataManager.requestMoreAppData { canLoadMore in
            self.canLoadMore = canLoadMore
            
            //隐藏loading
            self.isMoreLoading = false
        }
    }
}

// cell
struct AppCell : View {
    @StateObject var appItemModel: AppItemModel
    
    var body: some View {
        HStack(content: {
            //App图标
            AsyncImage(url: URL(string: self.appItemModel.artworkUrl60)) { phase in
                if let image = phase.image {
                    image
                } else if phase.error != nil {
                    //error
                    Color.gray
                } else {
                    //placeholder
                    ActivityRep()
                }
            }
                .frame(width: 60, height: 60)
                .aspectRatio(contentMode: .fit)
                .cornerRadius(8)
                .overlay(
                    RoundedRectangle(cornerRadius: 8, style: .continuous)
                         .stroke(Color(uiColor: .secondarySystemBackground), lineWidth: 1)
                )
                .padding(EdgeInsets(top: 0, leading: 15, bottom: 0, trailing: 5))
                
            //文案
            VStack(alignment: HorizontalAlignment.leading, spacing: 3, content: {
                Text(self.appItemModel.trackName)
                    .fontWeight(.medium)
                    .font(.system(size: 18))
                    .lineLimit(1)
                Text(self.appItemModel.description)
                    .font(.system(size: 14))
                    .lineLimit(2)
            })
            Spacer()
            //点赞
            Image(appItemModel.like ? "like" : "unlike")
                .resizable()
                .frame(width: 25, height: 25)
                .aspectRatio(contentMode: .fit)
                .padding(EdgeInsets(top: 0, leading: 5, bottom: 0, trailing: 15))
                .onTapGesture {
                    appItemModel.like = !(appItemModel.like )
                }
        })
        .frame(height: AppCellHeight)
        .background(Color.white)
        .cornerRadius(10)
        .padding(EdgeInsets(top: 0, leading: 15, bottom: 0, trailing: 15))
    }
}
