//
//  HomeList.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import SwiftUI

struct HomeList: View {
    
    @EnvironmentObject
    var store:MainStore<AppState, AppReduce>
    
    let tableBackgroundColor = UIColor.systemGray6
    
    init() {
        UITableView.appearance().separatorStyle = .none
        UITableView.appearance().separatorColor = .clear
        UITableView.appearance().backgroundColor = tableBackgroundColor
        UITableViewCell.appearance().backgroundColor = tableBackgroundColor
        UITableViewCell.appearance().selectedBackgroundView = UIView()
    }
    
    var body: some View {
        if let apps = store.state.apps {
            list(apps: apps)
        } else {
            VStack {
                LoadingView()
            }
            .onAppear {
                Task {
                    // 模拟慢网速
                    try? await Task.sleep(nanoseconds: 3_000_000_000)
                    await store.dispatch(action: .refresh)
                }
            }
        }
    }
    
    func list(apps: [AppModel]) -> some View {
        ZStack {
            Color(tableBackgroundColor).edgesIgnoringSafeArea(.all)
            List{
                rows(apps: apps)
                if #available(iOS 15.0, *) {
                    loadingMoreView()
                        .listSectionSeparator(.hidden)
                }else if #available(iOS 14.0, *) {
                    loadingMoreView()
                        .padding(EdgeInsets(top: 0, leading: 8, bottom: 0, trailing: 8))
                        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 50)
                        .listRowInsets(EdgeInsets(top: -1, leading: 0, bottom: 0, trailing: 0))
                        .background(Color(tableBackgroundColor))
                    
                } else {
                    loadingMoreView()
                }
            }
            .listStyle(.plain)
        }
        .pullDownToRefresh {
            // 模拟慢网速
            try? await Task.sleep(nanoseconds: 3_000_000_000)
            await store.dispatch(action: .refresh)
        }
    }
    
    func loadingMoreView() -> some View {
        let row = HStack(alignment: .center) {
            Spacer()
            if !self.store.state.hasMore {
                Text("No more data")
                    .foregroundColor(.gray)
            } else {
                LoadingView()
                    .onAppear {
                        Task {
                            // 模拟慢网速
                            try? await Task.sleep(nanoseconds: 3_000_000_000)
                            await store.dispatch(action: .loadMore)
                        }
                    }
            }
            Spacer()
        }
            .listRowBackground(Color(tableBackgroundColor))
        return row
    }
    
    func rows(apps: [AppModel]) -> some View {
        let rows = ForEach(apps) { app in
            if #available(iOS 15.0, *) {
                listRow(app: app)
                    .listRowSeparator(.hidden)
            }else if #available(iOS 14.0, *) {
                listRow(app: app)
                    .padding(EdgeInsets(top: 0, leading: 8, bottom: 0, trailing: 8))
                    .frame(minWidth: 0, maxWidth: .infinity, minHeight: 90)
                    .listRowInsets(EdgeInsets(top: -1, leading: 0, bottom: 0, trailing: 0))
                    .background(Color(tableBackgroundColor))
                    
            } else {
                listRow(app: app)
            }
        }
        return rows
    }
    
    func listRow(app: AppModel) -> some View {
        let content = VStack {
            HomeRow(model: app)
                .frame(minWidth: 0, maxWidth: .infinity, minHeight: 80)
                .padding(EdgeInsets(top: 0, leading: 8, bottom: 0, trailing: 8))
                .background(Color.white)
                .cornerRadius(10)
        }.listRowBackground(Color(tableBackgroundColor))
        return content
    }
}

struct HomeList_Previews: PreviewProvider {
    static var previews: some View {
        HomeList()
            .environmentObject(MainStore<AppState, AppReduce>())
    }
}
