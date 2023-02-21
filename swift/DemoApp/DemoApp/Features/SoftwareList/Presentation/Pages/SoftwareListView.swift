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
        
    var body: some View {
        List {
            Section {
                ForEach(viewModel.softwares()) { $software in
                    SoftwareRow(software: $software)
                        .onAppear {
                            Task {
                                await viewModel.softwareOnAppear(software: software)
                            }
                        }
                        .buttonStyle(.plain)
                }
            } footer: {
                HStack(alignment: .center, spacing: 10) {
                    Spacer()
                    if self.viewModel.noMore {
                        Text("No more")
                    } else {
                        Text("Loading")
                    }
                    Spacer()
                }
            }
        }
        .refreshable {
            await viewModel.getSoftwareList()
        }
        .task {
            await viewModel.getSoftwareList()
        }
        .environmentObject(viewModel)
    }
}

struct MainListView_Previews: PreviewProvider {
    static var previews: some View {
        SoftwareListView()
    }
}
