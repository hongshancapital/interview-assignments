//
//  RefreshFooter.swift
//  AppListDemo
//
//  Created by arthur on 2022/10/23.
//

import SwiftUI

struct RefreshFooter<T: LoadMoreable>: View {
    
    @StateObject var viewModel: T
    
    var body: some View {
        HStack(alignment: .center, spacing: 10) {
            if viewModel.isNoMoreData {
                Text("No more data.")
                    .foregroundColor(Color(uiColor: .lightGray))
            } else {
                ProgressView()
                    .progressViewStyle(.circular)
                Text("Loading...")
                    .foregroundColor(Color(uiColor: .lightGray))
            }
        }
        .frame(maxWidth: .infinity)
        .task {
            await viewModel.loadMore()
        }
    }
}

struct RefreshFooter_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            RefreshFooter(viewModel: AppListViewModel())
        }
        .padding(.all, 40)
        .background(Color(uiColor: .systemGroupedBackground))
    }
}
