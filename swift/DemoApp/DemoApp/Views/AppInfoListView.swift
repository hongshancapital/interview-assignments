//
//  AppInfoListView.swift
//  DemoApp
//
//  Created by xiongmin on 2022/4/2.
//

import Foundation
import SwiftUI

struct AppInfoListView: View {
    
    @StateObject var viewModel: AppInfoViewModel
    
    var body: some View {
        NavigationView {
            if viewModel.appInfoList.count > 0 {
                List {
                    ForEach(viewModel.appInfoList, id: \.appId) { appInfo in
                        APPInfoCell(appInfo: appInfo)
                    }
                    .listRowSeparator(.hidden)
                    .listRowInsets(.init(top: 8, leading: 0, bottom: 8, trailing: 0))
                    .listRowBackground(Color.clear)
                    // loading & no more data
                    HStack(spacing: 8) {
                        if viewModel.hasMore {
                            ProgressView()
                            Text("Loading...")
                                .foregroundColor(Color.gray)
                        } else {
                            Text("No more data.").foregroundColor(Color.gray)
                        }
                    }
                    .frame(maxWidth: .infinity, alignment: .center)
                    .listRowBackground(Color.clear)
                    .task {
                        do {
                            // 延迟展示，不然看不清loading
                            try await Task.sleep(nanoseconds: 500_000_000)
                            try await viewModel.loadMore()
                        } catch {
                            print(error)
                        }
                    }
                }
                .navigationTitle("App")
                .refreshable {
                    do {
                        try await viewModel.refresh()
                    } catch {
                        print(error)
                    }
                }
                
            } else {
                ProgressView()
                    .frame(alignment: .center)
            }
        }
        .task {
            do {
                try await viewModel.refresh()
            } catch {
                print(error)
            }
        }
    }
}

struct AppInfoListView_Previews: PreviewProvider {
    static var previews: some View {
        let viewModel = AppInfoViewModel()
        AppInfoListView(viewModel: viewModel)
    }
}
