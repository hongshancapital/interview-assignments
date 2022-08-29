//
//  AppListRootView.swift
//  AppList
//
//  Created by 大洋 on 2022/8/21.
//

import SwiftUI

struct AppListRootView: View {
    @EnvironmentObject var viewModel: AppListViewModel
    
    var body: some View {
        NavigationView {
            Group {
                switch viewModel.settings.loadState {
                case .none:
                    ProgressView()
                default:
                    AppList()
                }
            }
            .navigationTitle("App")
            .onAppear {
                viewModel.loadAppList()
            }
        }
        
    }
}

struct AppListRootView_Previews: PreviewProvider {
    static var previews: some View {
        AppListRootView()
    }
}
