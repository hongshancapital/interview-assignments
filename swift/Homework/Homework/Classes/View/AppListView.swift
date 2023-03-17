//
//  AppTableView.swift
//  Homework
//
//  Created by Andy on 2022/9/2.
//  UI->List

import SwiftUI

struct AppListView: View {
    @EnvironmentObject var viewModel: AppListViewModel
    
    var body: some View {
        List {
            ForEach(viewModel.appInfos, id: \.trackId) { appInfo in
                AppCell(appInfo: appInfo)
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.bgGrayColor)
            }
            
            HStack {
                Spacer()
                if viewModel.allDataIsLoaded {
                    Text("No more data.")
                        .foregroundColor(Color.progressViewForeColor)
                } else {
                    ProgressView()
                    Spacer().frame(width: 12)
                    Text("Loading...")
                        .foregroundColor(Color.progressViewForeColor)
                }
                Spacer()
            }
            .onAppear {
                viewModel.loadMoreData()
            }
        }
        .refreshable {
            viewModel.loadData()
        }
        .listStyle(.plain)
        .background(Color.white)
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView().environmentObject(AppListViewModel())
    }
}
