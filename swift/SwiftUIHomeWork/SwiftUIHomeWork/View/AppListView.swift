//
//  AppListView.swift
//  SwiftUIHomeWork
//
//  Created by quanwei chen on 2022/9/4.
//

import SwiftUI

struct AppListView: View {
    @StateObject var viewModel: ApplicationVM = ApplicationVM()
    
    var body: some View {
            if viewModel.total != 0 {
                List{
                    ForEach(viewModel.allAppList,id:\.self){ applicationInfo in
                        ListRowCell(viewModel: viewModel, item: applicationInfo)
                            .frame(height:70)
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.clear)
                    }
                    FooterView
                         .listRowBackground(Color.clear)
                         .listRowSeparator(.hidden)
                         .onAppear{
                                 viewModel.isLoadingMore = true
                                 viewModel.loadMoreData()
                         }
                }
                .refreshable {
                    viewModel.isReloadData = true
                    viewModel.refreshData()
                }
                .listStyle(.plain)
            } else {
                ProgressView()
            }
    }
    
    
    
}


extension AppListView {

    var FooterView: some View {

        HStack(spacing: 5) {
            Spacer()
            if !viewModel.isLoadingMore {
                ProgressView()
            }
            Text(viewModel.isLoadingMore ? "No more data." : "Loading...")
                .foregroundColor(.secondary)
                .font(.callout)
            Spacer()
        }

    }

}

struct ProductListViiew_Previews: PreviewProvider {
    static var previews: some View {
        AppListView()
    }
}
