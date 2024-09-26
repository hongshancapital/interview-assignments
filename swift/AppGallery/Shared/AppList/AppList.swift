//
//  AppList.swift
//  Shared
//
//  Created by X Tommy on 2022/8/11.
//

import SwiftUI

struct AppList: View {
    
    @StateObject private var viewModel = AppListViewModel(service: RemoteAppListService(),
                                                          pageSize: 20)
    
    var body: some View {
        NavigationView {
            LoadableList(source: viewModel) { app in
                AppRow(app: app) {
                    viewModel.toggleMark(for: app)
                }
                .padding()
                .background(Color(uiColor: .systemBackground))
                .cornerRadius(10)
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
            }
            .navigationTitle("App")
        }
    }
    
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList()
    }
}
