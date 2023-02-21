//
//  SoftwareListView.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/15.
//

import SwiftUI
import Network

struct SoftwareListView: View {
    /// 创建 viewModel 与依赖注入，可以使用第三方库，例如 Swinject 等来完成
    @StateObject private var viewModel = {
        let networkManager = NetworkManager()
        let cacheManager = CacheManager(userDefaults: UserDefaults.standard)
        let repository = SoftwareListRepository(
            remoteDataSource: SoftwareListRemoteDataSource(networkManager: networkManager),
            localDataSource: SoftwareListLocalDataSource(cache: cacheManager),
            networkManager: networkManager
        )
        return SoftwareListViewModel(
            getSoftwareList: .init(repository: repository)
        )
    }()
        
    var animation: Animation {
        Animation.linear //设置动画的时间曲线为弹性样式
             .repeatForever() //设置动画的播放为无限循环模式
    }

    var body: some View {
        contentView()
        .refreshable {
            await viewModel.getSoftwareList()
        }
        .task {
            await viewModel.getSoftwareList()
        }
        .environmentObject(viewModel)
        .navigationTitle("App")
        .navigationBarTitleDisplayMode(.large)
    }
    
    func contentView() -> some View {
        if self.viewModel.isLoading && self.viewModel.softwareList.isEmpty {
            return AnyView(loadingView())
        } else {
            return AnyView(listView())
        }
    }
    func loadingView() -> some View {
        return ZStack {
            Color.init(red: 0.949, green: 0.949, blue: 0.968)
            CustomProgressView()
        }.edgesIgnoringSafeArea(.all)
    }
    
    func listView() -> some View {
        return List {
            Section {
                ForEach(viewModel.softwares()) { $software in
                    SoftwareRow(software: $software)
                        .onAppear {
                            Task {
                                await viewModel.softwareOnAppear(software: software)
                            }
                        }
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets.init(
                            top: 8,
                            leading: 0,
                            bottom: 8,
                            trailing: 0))
                }
            } footer: {
                HStack(alignment: .center, spacing: 10) {
                    Spacer()
                    if self.viewModel.noMore {
                        Text("No more data")
                            .font(.title3)
                    } else {
                        HStack {
                            /// 有问题：只在第一次加载时显示动画
                            /// https://blog.csdn.net/mydo/article/details/127207968
                            //                            ProgressView()
                            CustomProgressView()
                            Text("Loading...")
                                .font(.title3)
                        }
                    }
                    Spacer()
                }
            }
            .background(.clear)
        }
        
        .refreshable {
            await viewModel.getSoftwareList()
        }
        .task {
            await viewModel.getSoftwareList()
        }
    }
}

struct MainListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            SoftwareListView()
        }
    }
}

struct CustomProgressView: View {    
    var body: some View {
        TimelineView(.periodic(from: .now, by: 0.1)) { timeline in
            Image("indicator")
                .resizable()
                .frame(width: 20, height: 20)
                .scaledToFill()
                .rotationEffect(
                    .degrees(
                        Double(Int(timeline.date.timeIntervalSince1970 * 10) % 8) * 45.0
                    )
                )
        }
    }
}
